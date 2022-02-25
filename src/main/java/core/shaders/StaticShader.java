package core.shaders;

import core.objects.entities.Camera;
import core.objects.entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;

public class StaticShader extends ShaderProgram{

    private static final int MAX_LIGHTS = 4;

    private static final String VERTEX_FILE = "res/shaders/vertex.vs";
    private static final String FRAGMENT_FILE = "res/shaders/fragment.fs";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    private int location_lightPosition[];
    private int location_lightColor[];
    private int location_attenuation[];

    private int location_shineDamper;
    private int location_reflectivity;

    private int location_useFakeLighting;

    private int location_skyColor;

    private int location_numberOfRows;
    private int location_offset;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");

        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");

        location_useFakeLighting = super.getUniformLocation("useFakeLighting");

        location_skyColor = super.getUniformLocation("skyColor");

        location_numberOfRows = super.getUniformLocation("numberOfRows");
        location_offset = super.getUniformLocation("offset");

        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColor = new int[MAX_LIGHTS];
        location_attenuation = new int[MAX_LIGHTS];

        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
            location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }
    }

    public void loadNumberOfRows(int numberOfRows){
        super.loadFloat(location_numberOfRows, numberOfRows);
    }

    public void loadOffset(float x, float y){
        super.loadVector(location_offset, new Vector2f(x, y));
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix4f){
        super.loadMatrix(location_projectionMatrix, matrix4f);
    }

    public void loadViewMatrix(Camera camera){
        super.loadMatrix(location_viewMatrix, camera.getViewMatrix());
    }

    public void loadLights(List<Light> lights){
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i<lights.size()){
                super.loadVector(location_lightPosition[i], lights.get(i).getPosition().toJomlVector());
                super.loadVector(location_lightColor[i], lights.get(i).getColor().toJomlVector());
                super.loadVector(location_attenuation[i], lights.get(i).getAttenuation().toJomlVector());
            }else {
                super.loadVector(location_lightPosition[i], new Vector3f(0,0,0));
                super.loadVector(location_lightColor[i], new Vector3f(0,0,0));
                super.loadVector(location_attenuation[i], new Vector3f(1,0,0));
            }
        }
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadFakeLighting(boolean useFake){
        super.loadBoolean(location_useFakeLighting, useFake);
    }

    public void loadSkyColor(float r, float g, float b){
        super.loadVector(location_skyColor, new Vector3f(r,g,b));
    }
}
