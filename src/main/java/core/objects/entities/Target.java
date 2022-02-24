package core.objects.entities;

import core.objects.models.TexturedModel;
import core.toolbox.RunNextFrame;
import core.toolbox.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Target extends Entity{

    static List<Target> targets = new ArrayList<>();

    public Target(TexturedModel model, Vector3 position, Vector3 rotation, Vector3 scale, String name) {
        super(model, position, rotation, scale, name);

        targets.add(this);
    }

    public static List<Target> getTargets() {
        return targets;
    }

    public void destroy(){
        RunNextFrame.runNextFrame(() -> targets.remove(this));
        super.destroy();
    }
}
