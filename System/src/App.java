import ai.ui.Ui;
import shared.MainRouter;
import shared.routers.Ex3Router;

public class App {

    private static MainRouter mainRouter = new MainRouter();
    private static Ui ui;
    
    // TO_DO: Register all routers here
    private static void registerAll() {
        mainRouter.addRouter("ex3", new Ex3Router());
    }
    
    public static void main(String[] args) throws Exception {
        ui = new Ui();
        ui.setUiPorts();
        registerAll();
        ui.start(mainRouter);
    }

}
