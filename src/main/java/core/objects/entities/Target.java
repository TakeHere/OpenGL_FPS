package core.objects.entities;

import core.animations.Animation;
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

        String scaleFormula = "abs(x/4 + cos(x*0.3) * 2) + 5";

        Animation animation = new Animation(this, 0,50, 0.2f,
                String.valueOf(this.getPosition().x),
                String.valueOf(this.getPosition().y),
                String.valueOf(this.getPosition().z),

                String.valueOf(this.getRotation().x),
                String.valueOf(this.getRotation().y),
                String.valueOf(this.getRotation().z),

                scaleFormula,
                scaleFormula,
                scaleFormula
        );
    }

    public static List<Target> getTargets() {
        return targets;
    }

    public void destroy(){
        RunNextFrame.runNextFrame(() -> targets.remove(this));
        super.destroy();
    }
}
