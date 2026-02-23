package shared.ui_ports;

public abstract class TeamUiPort {

    private static TeamUiPort instance;

    public static void setInstance(TeamUiPort ui) {
        if (ui == null)
            throw new IllegalArgumentException("TeamUiPort instance cannot be null");
        if (instance != null)
            throw new IllegalStateException("TeamUiPort instance already set");
        instance = ui;
    }

    public static TeamUiPort getInstance() {
        if (instance == null)
            throw new IllegalStateException("TeamUiPort instance not set yet");
        return instance;
    }

    // Your UI commands here, for example:
    public abstract void showCircle(int id, int x, int y, int radius);
    public abstract void showPoint(int id, int x, int y);
    public abstract void showLine(int id, int x1, int y1, int x2, int y2);
    public abstract void showText(int id, int x, int y, String text);



    public abstract void log(String message);
}