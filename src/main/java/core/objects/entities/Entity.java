package core.objects.entities;

import core.collision.AABB;
import core.objects.GameObject;
import core.objects.models.TexturedModel;
import core.toolbox.Maths;
import core.toolbox.RunNextFrame;
import core.toolbox.Vector3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Entity extends GameObject {

    private TexturedModel model;
    private int textureIndex = 0;
    private Matrix4f transformationMatrix;
    private AABB aabb;

    public boolean useGravity = false;

    public static List<Entity> entities = Collections.synchronizedList(new ArrayList<>());

    public Entity(TexturedModel model, Vector3 position, Vector3 rotation, Vector3 scale, String name) {
        super(position, rotation, scale, name);
        this.model = model;
        updateTransformationMatrix();
        this.aabb = new AABB(this);

        entities.add(this);
    }

    public void updateAABB(){
        aabb.updateAABB();
    }

    public void updateTransformationMatrix(){
        this.transformationMatrix = Maths.createTransformationMatrix(getPosition().toJomlVector(), getRotation().toJomlVector(), getScale().toJomlVector());
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

    public void destroy(){
        RunNextFrame.runNextFrame(() -> entities.remove(this));
        super.destroy();
    }
}
