package shared;

import shared.UiPort;
//import student.geo.GeoRouter;

public class RoutersRegistry {
    public static void registerAll(UiPort ui) {
        MainRouter.addRouter("geo", new GeoRouter(ui));
    }
}