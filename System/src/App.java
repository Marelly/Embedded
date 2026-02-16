import ai.ui.Ui;
import shared.MainRouter;
import shared.routers.Ex3Router;
import team.ex3.Ex3Backend;

public class App {

    private static MainRouter mainRouter = new MainRouter();
    private static Ui ui;
    private static Ex3Backend ex3Backend;
    public static void main(String[] args) throws Exception {
        ui = new Ui();
        ui.setUiPorts();
        ex3Backend = new Ex3Backend();
        registerAll();
        ui.startUI();
        ex3Backend.startScenario();
        //System.out.println("Hello, World!");
    }

    // TODO: Register all routers here
    private static void registerAll() {
        mainRouter.addRouter("ex3", new Ex3Router());
    }
}
