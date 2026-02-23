package team.domain;

import shared.ui_ports.TeamUiPort;

public class TeamBackend {

    /**
     * Use teamUiPort() as a function and not a variable to get the UI port
     * to avoid trying to get it before it was set up by the UI
     * (which happens at UI startup, but this backend is constructed at app
     * startup).
     */
    private TeamUiPort teamUiPort() {
        return TeamUiPort.getInstance();
    }

    // Called once at UI startup
    public void start() {
        // Remove the comments and create 3 circles and 2 points
        MyCircle mc1 = new MyCircle(new MyPoint(50, 50), 30);
        MyCircle mc2 = new MyCircle(new MyPoint(70, 50), 30);
        MyCircle mc3 = new MyCircle(new MyPoint(120, 120), 30);
        MyPoint mp1 = new MyPoint(60, 60);
        MyPoint mp2 = new MyPoint(150, 150);

        // Fill in your details
        String student1 = "FullName" + ", " + "ID";
        String student2 = "FullName" + ", " + "ID";
        String student3 = "FullName" + ", " + "ID";

        teamUiPort().showCircle(1, mc1.getCenter().getX(), mc1.getCenter().getY(), mc1.getRadius());
        teamUiPort().showCircle(2, mc2.getCenter().getX(), mc2.getCenter().getY(), mc2.getRadius());
        teamUiPort().showCircle(3, mc3.getCenter().getX(), mc3.getCenter().getY(), mc3.getRadius());
        
        teamUiPort().showPoint(4, mp1.getX(), mp1.getY());
        teamUiPort().showPoint(5, mp2.getX(), mp2.getY());
        
        teamUiPort().showLine(6, mc1.getCenter().getX(), mc1.getCenter().getY(), mc2.getCenter().getX(), mc2.getCenter().getY());
        
        teamUiPort().showText(7, 50, 400, student1);
        teamUiPort().showText(8, 50, 450, student2);
        teamUiPort().showText(9, 50, 500, student3);
    }
    
    // Write here the code that performs all the checks and prints the results
    public void check() {    
        System.out.println("Please implement the checks and print the results" );
    }

    public void showCreativity() {   
        // Add here some shapes, lines, and text to show your creativity. 
        // You can also use the creativity feature to show some extra information about 
        // the circles and points you created in the start() method. 
        System.out.println("Please implement the creativity feature" );
    }

}
