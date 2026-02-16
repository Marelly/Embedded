import shared.MainRouter;
import shared.UiPort;
import team.ex3.Ex3Router;

public class App {

    private MainRouter mainRouter = new MainRouter();
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }

    public static void registerAll(UiPort ui) {
        mainRouter.addRouter("ex3", new Ex3Router(ui));
    }
}
