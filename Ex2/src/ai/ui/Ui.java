package ai.ui;


import base.Params;
import shared.MainRouter;
import shared.ui_ports.TeamUiPort;

import ai.ui.shapes.Circle;
import ai.ui.shapes.Line;
import ai.ui.shapes.Text;

import java.awt.Color;

import javax.swing.JFrame;



public class Ui {
    private MainRouter mainRouter;
    private TeamUiPortImpl uiInstance;

    private JFrame frame;
    private GameCanvas canvas;

    public void setUiPorts() {
        uiInstance = new TeamUiPortImpl();
        TeamUiPort.setInstance((TeamUiPort) uiInstance);
    }

    public void start(MainRouter mainRouter) {
        this.mainRouter = mainRouter;
        createAndShowWindow();
        System.out.println("UI started");
        System.out.println("Calling backend start method via router /team/start ...");
        this.mainRouter.route("/team/start", Params.of());
        this.mainRouter.route("/team/check", Params.of());
        this.mainRouter.route("/team/showCreativity", Params.of());

    }

    private void createAndShowWindow() {
        System.out.println("Creating and showing UI window...");
        // Create a frame window and set its name, size and behavior when clicking the X
        frame = new JFrame("My Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        canvas = new GameCanvas();

        // Add the canvas to the frame and show it
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
    }

    public void showCircle(int id, int x, int y, int radius) {
        Circle c = new Circle("c" + id, x, y, radius);
        c.setColor(Color.BLUE);
        canvas.addShape(c);
    }
    
    public void showPoint(int id, int x, int y) {
        Circle c = new Circle("p" + id, x, y, 2);
        c.setColor(Color.RED);
        c.setIsFilled(true);
        canvas.addShape(c);
    }

    public void showLine(int id, int x1, int y1, int x2, int y2) {
        Line l = new Line("l" + id, x1, y1, x2, y2);
        l.setColor(Color.GREEN);
        canvas.addShape(l);
    }

    public void showText(int id, int x, int y, String text) {
        Text t = new Text("t" + id, text, x, y);
        canvas.addShape(t);
    }
}
