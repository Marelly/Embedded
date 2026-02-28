package ai.ui;

import javax.swing.*;

import base.Params;
import shared.MainRouter;
import shared.ui_ports.DroneUiPort;
import shared.AssetRegistry;

public class Ui {
    private MainRouter mainRouter;
    private DroneUiPortImpl uiInstance;
    private MapPanel mapPanel;
    private JFrame frame;
    private javax.swing.Timer tickTimer;
    // joystick state
    private double throttle = 0.0; // forward/back
    private double steer = 0.0; // left/right
    private static final double STEP = 0.1;

    public void setUiPorts() {
        uiInstance = new DroneUiPortImpl();
        DroneUiPort.setInstance((DroneUiPort) uiInstance);
    }

    public void start(MainRouter mainRouter) {
        this.mainRouter = mainRouter;
        createAndShowWindow();
        // after window appears we may want focus for key events
        frame.requestFocusInWindow();
        System.out.println("UI started");
        System.out.println("Calling backend start method via router /team/start ...");
        mainRouter.route("/drone/start", Params.of());
    }

    /**
     * Install keyboard and mouse listeners for joystick-style control.
     */
    private void installInputControls() {
        // keyboard arrow keys - listen on both panel and frame to ensure focus
        java.awt.event.KeyAdapter keyAdapter = new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_UP:
                        throttle += STEP;
                        break;
                    case java.awt.event.KeyEvent.VK_DOWN:
                        throttle -= STEP;
                        break;
                    case java.awt.event.KeyEvent.VK_LEFT:
                        steer += STEP;
                        break;
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        steer -= STEP;
                        break;
                }
                sendControls();
            }
        };
        mapPanel.addKeyListener(keyAdapter);
        if (frame != null) {
            frame.addKeyListener(keyAdapter);
        }

        // simple mouse drag: horizontally adjusts steer, vertically adjusts throttle
        java.awt.event.MouseAdapter mouseAdapter = new java.awt.event.MouseAdapter() {
            private int lastX, lastY;

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }

            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                int dx = e.getX() - lastX;
                int dy = lastY - e.getY();
                lastX = e.getX();
                lastY = e.getY();
                steer -= dx * 0.005; // dragging right decreases steer (turn right)
                throttle += dy * 0.005;
                sendControls();
            }
        };
        mapPanel.addMouseListener(mouseAdapter);
        mapPanel.addMouseMotionListener(mouseAdapter);
    }

    private void sendControls() {
        // clamp values to [-1,1]
        throttle = Math.max(-1, Math.min(1, throttle));
        steer = Math.max(-1, Math.min(1, steer));
        mainRouter.route("/drone/control/throttle", Params.of(throttle));
        mainRouter.route("/drone/control/steer", Params.of(steer));
        if (mapPanel != null) {
            mapPanel.setJoystick(throttle, steer);
        }
    }

    private void createAndShowWindow() {
        System.out.println("Creating and showing UI window...");

        // Create the main frame
        frame = new JFrame("Drone Control System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        // Create the map panel
        mapPanel = new MapPanel();

        // Connect the UI implementation to the map panel
        uiInstance.setMapPanel(mapPanel);

        // Add the map panel to the frame
        frame.add(mapPanel);

        // Pack and show
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);

        // install input listeners for joystick control
        installInputControls();

        // ensure panel can gain focus for key input
        mapPanel.setFocusable(true);
        mapPanel.requestFocusInWindow();

        System.out.println("UI window created and displayed");

        // Load the desert map background
        uiInstance.setBackground(AssetRegistry.MAP_DESERT);

        // start periodic tick timer (200ms)
        tickTimer = new javax.swing.Timer(200, e -> {
            mainRouter.route("/drone/tick", Params.of(0.2));
        });
        tickTimer.start();
    }
}
