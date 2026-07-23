package team.control;

import shared.ui_ports.DroneUiPort;
import team.model.Score;
import team.model.Target;
import team.model.Targets;
import team.model.Vec2;

public class TargetsControl {

    private final Targets targets;
    private final Score score;

    public TargetsControl(Targets targets, Score score) {
        this.targets = targets;
        this.score = score;
    }

    public void start(DroneUiPort ui) {
        targets.spawnAllToUi(ui);
    }

    public void reset(DroneUiPort ui) {
        score.reset();
        targets.resetReached(ui);
    }

    public int updateReached(Vec2 dronePosition, DroneUiPort ui) {
        return targets.checkAndUpdateReached(dronePosition, ui);
    }

    public boolean allReached() {
        return targets.allReached();
    }

    public int score() {
        return score.getValue();
    }

    public void onTargetReached(int count) {
        score.onTargetReached(count);
    }

    public void add(double x, double y, DroneUiPort ui) {
        Target target = new Target(Target.nextId, new Vec2(x, y), 28);
        targets.add(target);
        ui.spawnTarget(target.getId(), target.getPos().x, target.getPos().y, target.getRadius());
    }
}