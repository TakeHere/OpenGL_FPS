package core.renderers.debug;

import core.objects.models.TexturedModel;
import core.toolbox.Vector3;

public class DebugSphere {

    private Vector3 position;
    private float scale;

    public static TexturedModel redTexture;
    public static TexturedModel yellowTexture;
    public static TexturedModel blueTexture;

    public TexturedModel currentTexture;

    public DebugSphere(Vector3 position, float scale, int texture) {
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

    public Vector3 getPosition() {
        return position;
    }

    public float getScale() {
        return scale;
    }
}
