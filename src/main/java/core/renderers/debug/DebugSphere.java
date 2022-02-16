package core.renderers.debug;

import core.objects.models.TexturedModel;
import org.joml.Vector3f;

public class DebugSphere {

    private Vector3f position;
    private float scale;

    public static TexturedModel redTexture;
    public static TexturedModel yellowTexture;
    public static TexturedModel blueTexture;

    public TexturedModel currentTexture;

    public DebugSphere(Vector3f position, float scale, int texture) {
        this.position = position;
        this.scale = scale;
        setTexture(texture);
    }

    public void setTexture(int texture){
        switch (texture){
            case 0:
                currentTexture = redTexture;
                break;
            case 1:
                currentTexture = yellowTexture;
                break;
            case 2:
                currentTexture = blueTexture;
                break;
            default:
                assert false: "Enable to choose texture for debug sphere with texture id: " + texture;
                break;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getScale() {
        return scale;
    }
}
