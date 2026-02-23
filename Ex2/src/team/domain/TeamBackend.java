package team.domain;

import shared.ui_ports.TeamUiPort;

public class TeamBackend {

    private TeamUiPort teamUiPort() {
        return TeamUiPort.getInstance();
    }

    /**
     * YOUR CODE STARTS HERE
     */
    // Called once at UI startup
    public void start() {
        // Remove the nulls and create 3 circles and 2 points
        MyCircle mc1 = null;
        MyCircle mc2 = null;
        MyCircle mc3 = null;
        MyPoint mp1 = null;
        MyPoint mp2 = null;

        // Fill in your details
        String student1 = "FullName" + ", " + "ID";
        String student2 = "FullName" + ", " + "ID";
        String student3 = "FullName" + ", " + "ID";


        // The errors should disappear after you create the circles and points and fill in your details.
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
