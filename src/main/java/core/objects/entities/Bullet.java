package core.objects.entities;

import core.objects.models.TexturedModel;
import core.toolbox.RunNextFrame;
import core.toolbox.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Bullet extends Entity{

    private static List<Bullet> bullets = new ArrayList<>();

    public Bullet(TexturedModel model, Vector3 position, Vector3 rotation, Vector3 scale, String name) {
        super(model, position, rotation, scale, name);

        bullets.add(this);
    }

    public static List<Bullet> getBullets() {
        return bullets;
    }

    public void destroy(){
        RunNextFrame.runNextFrame(() -> {
            if (getAudioSource() != null)
                getAudioSource().delete();
            bullets.remove(this);
        });
        super.destroy();
    }
}
