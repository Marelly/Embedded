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
    // world dimensions must match DroneBackend constants
    private static final double WORLD_W = 900;
    private static final double WORLD_H = 600;

    public void setUiPorts() {
        uiInstance = new DroneUiPortImpl();
        DroneUiPort.setInstance((DroneUiPort) uiInstance);
    }

    public void start(MainRouter mainRouter) {
        this.mainRouter = mainRouter;
        // ensure UI initialization happens on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            createAndShowWindow();
            frame.requestFocusInWindow();
            System.out.println("UI started");
            System.out.println("Calling backend start method via router /team/start ...");
            mainRouter.route("/drone/start", Params.of());
        });
    }

    /**
     * Install keyboard and mouse listeners for joystick-style control.
     */
    private void installInputControls() {
        // right-click context menu for adding objects
        JPopupMenu popup = new JPopupMenu();
        // create menu items with icons loaded from the asset registry
        JMenuItem addTargetItem = new JMenuItem("Add Target");
        try {
            String targetPath = AssetRegistry.get(AssetRegistry.TARGET);
            java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(new java.io.File(targetPath));
            int desiredHeight = 24; // menu icon height
            int scaledWidth = (int) ((double) img.getWidth() / img.getHeight() * desiredHeight);
            java.awt.Image scaled = img.getScaledInstance(scaledWidth, desiredHeight, java.awt.Image.SCALE_SMOOTH);
            addTargetItem.setIcon(new javax.swing.ImageIcon(scaled));
        } catch (Exception ex) {
            System.err.println("Failed to load target icon: " + ex);
        }
        JMenuItem addZoneItem = new JMenuItem("Add No Fly Zone");
        try {
            String zonePath = AssetRegistry.get(AssetRegistry.NO_FLY_ZONE);
            java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(new java.io.File(zonePath));
            int desiredHeight = 24;
            int scaledWidth = (int) ((double) img.getWidth() / img.getHeight() * desiredHeight);
            java.awt.Image scaled = img.getScaledInstance(scaledWidth, desiredHeight, java.awt.Image.SCALE_SMOOTH);
            addZoneItem.setIcon(new javax.swing.ImageIcon(scaled));
        } catch (Exception ex) {
            System.err.println("Failed to load zone icon: " + ex);
        }
        popup.add(addTargetItem);
        popup.add(addZoneItem);

        // state for zone creation
        final boolean[] creatingZone = { false };
        final double[] zoneStart = new double[2];

        addTargetItem.addActionListener(e -> {
            // when triggered, use the last click position stored in zoneStart
            double pixelX = zoneStart[0];
            double pixelY = zoneStart[1];
            double worldX = pixelX * WORLD_W / mapPanel.getWidth();
            double worldY = pixelY * WORLD_H / mapPanel.getHeight();
            mainRouter.route("/drone/model/add/target", Params.of(worldX, worldY));
        });
        addZoneItem.addActionListener(e -> {
            creatingZone[0] = true;
            double pixelX = zoneStart[0];
            double pixelY = zoneStart[1];
            double worldX = pixelX * WORLD_W / mapPanel.getWidth();
            double worldY = pixelY * WORLD_H / mapPanel.getHeight();
            mapPanel.startZone(worldX, worldY);
        });

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
                // store click for context menu and potential zone start
                zoneStart[0] = e.getX();
                zoneStart[1] = e.getY();
                if (SwingUtilities.isRightMouseButton(e)) {
                    popup.show(mapPanel, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                int dx = e.getX() - lastX;
                int dy = lastY - e.getY();
                lastX = e.getX();
                lastY = e.getY();
                if (creatingZone[0]) {
                    double pixelRadius = Math.hypot(e.getX() - zoneStart[0], e.getY() - zoneStart[1]);
                    double worldRadius = pixelRadius * WORLD_W / mapPanel.getWidth();
                    mapPanel.updateZone(worldRadius);
                } else {
                    steer -= dx * 0.005; // dragging right decreases steer (turn right)
                    throttle += dy * 0.005;
                    sendControls();
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (creatingZone[0]) {
                    creatingZone[0] = false;
                    double pixelRadius = Math.hypot(e.getX() - zoneStart[0], e.getY() - zoneStart[1]);
                    mapPanel.finishZone();
                    double pixelX = zoneStart[0];
                    double pixelY = zoneStart[1];
                    double worldX = pixelX * WORLD_W / mapPanel.getWidth();
                    double worldY = pixelY * WORLD_H / mapPanel.getHeight();
                    double worldRadius = pixelRadius * WORLD_W / mapPanel.getWidth();
                    mainRouter.route("/drone/model/add/no-fly-zone", Params.of(worldX, worldY, worldRadius));
                }
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
        // also give UI port a reference to router so it can issue reset commands
        uiInstance.setRouter(mainRouter);

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
        // ensure restart button is hidden at start
        uiInstance.hideRestartButton();

        // start periodic tick timer (200ms)
        tickTimer = new javax.swing.Timer(200, e -> {
            mainRouter.route("/drone/tick", Params.of(0.2));
        });
        tickTimer.start();
    }
}
