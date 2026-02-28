package shared.routers;

import base.Params;
import base.SubRouter;
import my_base.App;
import team.control.DroneBackend;

public class DroneRouter implements SubRouter {

    private final DroneBackend backend;

    public DroneRouter() {
        this.backend = App.content().droneBackend();
    }
    @Override
    public Object route(String subPath, Params p) {

        switch (subPath) {

            case "/start":
                backend.start();
                return null;

            case "/reset":
                backend.reset();
                return null;

            case "/tick": {
                double dt = p.getDouble(0);
                backend.tick(dt);
                return null;
            }

            case "/control/throttle": {
                double t = p.getDouble(0);
                backend.setThrottle(t);
                return null;
            }

            case "/control/steer": {
                double s = p.getDouble(0);
                backend.setSteer(s);
                return null;
            }

            case "/model/add/target": {
                double x = p.getInt(0);
                double y = p.getInt(1);
                backend.addTarget(x, y);
                return null;
            }

            case "/model/add/no-fly-zone": {
                double x = p.getInt(0);
                double y = p.getInt(1);
                double radius = p.getDouble(2);
                backend.addNoFlyZone(x, y, radius);
                return null;
            }

            default:
                throw new RuntimeException("Unknown drone route: " + subPath);
        }
    }
}