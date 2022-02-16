package core.objects.entities;

import core.collision.AABB;
import core.objects.GameObject;
import core.objects.models.TexturedModel;
import core.toolbox.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Entity extends GameObject {

    private TexturedModel model;
    private int textureIndex = 0;
    private Matrix4f transformationMatrix;
    private AABB aabb;

    public static final float GRAVITY = -5;

    public boolean useGravity = false;

    public static List<Entity> entities = new ArrayList<>();

    public Entity(TexturedModel model, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(position, rotation, scale);
        this.model = model;
        updateTransformationMatrix();
        this.aabb = new AABB(this);

        entities.add(this);
    }

    public void updateAABB(){
        aabb.updateAABB();
    }

    public void updateTransformationMatrix(){
        this.transformationMatrix = Maths.createTransformationMatrix(getPosition(), getRotation().x, getRotation().y, getRotation().z, getScale());
    }

    public float getTextureXOffset(){
        int column = textureIndex%model.getTexture().getNumberOfRows();
        return (float) column/(float) model.getTexture().getNumberOfRows();
    }

    public float getTextureYOffset(){
        int row = textureIndex/model.getTexture().getNumberOfRows();
        return (float) row/(float)model.getTexture().getNumberOfRows();
    }

    public Matrix4f getTransformationMatrix() {
        return transformationMatrix;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public AABB getAabb() {
        return aabb;
    }
}
