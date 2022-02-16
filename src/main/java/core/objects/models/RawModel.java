package core.objects.models;

import core.collision.AABB;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class RawModel {

    private int vaoID;
    private int vertexCount;
    private Vector3f[] points;

    public RawModel(int vaoID, int vertexCount, Vector3f[] points){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.points = points;
    }

    public Vector3f[] getPoints() {
        return points;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
