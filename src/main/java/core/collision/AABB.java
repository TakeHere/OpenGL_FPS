package core.collision;

import core.objects.entities.Entity;
import core.renderers.MasterRenderer;
import core.renderers.debug.DebugSphere;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class AABB {

    private Vector3f positionMin;
    private Vector3f positionMax;
    private Entity entity;

    public AABB(Entity entity) {
        this.entity = entity;
        updateAABB();
    }

    public void updateAABB(){
        Vector3f[] points = entity.getModel().getRawModel().getPoints();

        Matrix4f rotationMatrix = new Matrix4f();
        rotationMatrix.identity().
                rotateX((float)Math.toRadians(entity.getRotation().x)).
                rotateY((float)Math.toRadians(entity.getRotation().y)).
                rotateZ((float)Math.toRadians(entity.getRotation().z));

        float minX = 0;
        float minY = 0;
        float minZ = 0;

        float maxX = 0;
        float maxY = 0;
        float maxZ = 0;

        for (Vector3f point : points) {
            if (point.x < minX) minX = point.x;
            if (point.y < minY) minY = point.y;
            if (point.z < minZ) minZ = point.z;

            if (point.x > maxX) maxX = point.x;
            if (point.y > maxY) maxY = point.y;
            if (point.z > maxZ) maxZ = point.z;
        }

        Vector3f minLocation = new Vector3f(minX, minY, minZ);
        Vector3f maxLocation = new Vector3f(maxX, maxY, maxZ);

        minLocation = minLocation.mul(entity.getScale()).add(entity.getPosition());
        maxLocation = maxLocation.mul(entity.getScale()).add(entity.getPosition());

        MasterRenderer.addDebugSphere(new DebugSphere(minLocation,  1, 0));
        MasterRenderer.addDebugSphere(new DebugSphere(maxLocation,  1, 1));

        positionMin = minLocation;
        positionMax = maxLocation;
    }

    public Vector3f getPositionMin() {
        return positionMin;
    }

    public Vector3f getPositionMax() {
        return positionMax;
    }
}
