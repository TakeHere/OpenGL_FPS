package core.toolbox;

import core.collision.AABB;
import core.objects.entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Maths {

    public static Vector3f forwardVector(Vector3f rotation){
        return new Vector3f(
                (float) (Math.cos(Math.toRadians(rotation.x)) * Math.sin(Math.toRadians(rotation.y))),
                (float) -Math.sin(Math.toRadians(rotation.x)),
                (float) -(Math.cos(Math.toRadians(rotation.x)) * Math.cos(Math.toRadians(rotation.y))));
    }

    public static boolean isAabbIntersect(AABB a, AABB b){
        return (a.getPositionMin().x <= b.getPositionMax().x && a.getPositionMax().x >= b.getPositionMin().x) &&
                (a.getPositionMin().y <= b.getPositionMax().y && a.getPositionMax().y >= b.getPositionMin().y) &&
                (a.getPositionMin().z <= b.getPositionMax().z && a.getPositionMax().z >= b.getPositionMin().z);
    }

    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, Vector3f scale){
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(translation).
                rotateX((float)Math.toRadians(rx)).
                rotateY((float)Math.toRadians(ry)).
                rotateZ((float)Math.toRadians(rz)).
                scale(scale);

        return matrix;
    }

    public static Matrix4f getViewMatrix(Camera camera){
        Vector3f pos = camera.getPosition();
        Vector3f rot = new Vector3f(camera.getPitch(), camera.getYaw(), camera.getRoll());
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.rotate((float) Math.toRadians(rot.x), new Vector3f(1,0,0))
                .rotate((float) Math.toRadians(rot.y), new Vector3f(0,1,0))
                .rotate((float) Math.toRadians(rot.z), new Vector3f(0,0,1));
        matrix.translate(-pos.x, -pos.y, -pos.z());

        return matrix;
    }
}
