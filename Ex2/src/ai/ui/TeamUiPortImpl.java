package ai.ui;

import my_base.App;
import shared.ui_ports.TeamUiPort;

public class TeamUiPortImpl extends TeamUiPort {
    public TeamUiPortImpl() {
    }

    @Override
    public void showCircle(int id, int x, int y, int radius) {
        App.UI().showCircle(id, x, y, radius);
    }

    @Override
    public void showPoint(int id, int x, int y) {
        App.UI().showPoint(id, x, y);
    }

    @Override
    public void showLine(int id, int x1, int y1, int x2, int y2) {
        App.UI().showLine(id, x1, y1, x2, y2);
    }
    
    @Override
    public void showText(int id, int x, int y, String text) {
        App.UI().showText(id, x, y, text);
    }

    @Override
    public void log(String message) {
        System.out.println(message);
    }

}