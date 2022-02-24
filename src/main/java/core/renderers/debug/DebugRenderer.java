package core.renderers.debug;

import core.objects.entities.Entity;
import core.objects.models.ModelTexture;
import core.objects.models.RawModel;
import core.objects.models.TexturedModel;
import core.renderers.MasterRenderer;
import core.shaders.StaticShader;
import core.toolbox.Maths;
import core.toolbox.Vector3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;
import java.util.Map;

public class DebugRenderer {

    private StaticShader shader;

    public DebugRenderer(StaticShader shader, Matrix4f projectionMatrix){
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(List<DebugSphere> debugSpheres){
        for (DebugSphere debugSphere : debugSpheres) {
            prepareTexturedModel(debugSphere.currentTexture);
            prepareInstance(debugSphere);
            GL11.glDrawElements(GL11.GL_TRIANGLES, debugSphere.currentTexture.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        }

        unbindTexturedModel();
    }

    private void prepareTexturedModel(TexturedModel model){
        RawModel rawModel = model.getRawModel();
        ModelTexture texture = model.getTexture();

        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        shader.loadNumberOfRows(texture.getNumberOfRows());
        if (texture.isHasTransparency())
            MasterRenderer.disableCulling();
        shader.loadFakeLighting(texture.isUseFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
    }

    private void unbindTexturedModel(){
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(DebugSphere sphere){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(sphere.getPosition().toJomlVector(), Vector3.ZERO.toJomlVector(), new Vector3f(sphere.getScale(), sphere.getScale(), sphere.getScale()));
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadOffset(0,0);
    }
}
