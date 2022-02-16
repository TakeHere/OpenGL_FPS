package core.toolbox;

import core.objects.models.RawModel;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public RawModel loadToVao(float[] positions, float[] textureCoords, float[] normals, int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributesList(0, 3, positions);
        storeDataInAttributesList(1, 2, textureCoords);
        storeDataInAttributesList(2, 3, normals);
        unbindVAO();

        List<Vector3f> positionsVector = new ArrayList<>();
        Vector3f currentVector = new Vector3f(0,0,0);
        int index = 0;

        for (float pos : positions){
            if (index == 0){
                index++;
                currentVector.x = pos;
            }else if (index == 1){
                index++;
                currentVector.y = pos;
            }else if (index == 2){
                index = 0;
                currentVector.z = pos;

                positionsVector.add(new Vector3f(currentVector.x, currentVector.y, currentVector.z));
            }
        }

        return new RawModel(vaoID, indices.length, positionsVector.toArray(new Vector3f[positionsVector.size()]));
    }

    public int loadTexture(String filename){
        try {
            int width, height;
            ByteBuffer buffer;
            try(MemoryStack stack = MemoryStack.stackPush()){
                IntBuffer w = stack.mallocInt(1);
                IntBuffer h = stack.mallocInt(1);
                IntBuffer c = stack.mallocInt(1);

                buffer = STBImage.stbi_load(filename, w, h, c, 4);
                if (buffer == null)
                    throw new Exception("Image file " + filename + " not loaded " + STBImage.stbi_failure_reason());

                width = w.get();
                height = h.get();
            }

            int id = GL11.glGenTextures();
            textures.add(id);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
            STBImage.stbi_image_free(buffer);
            return id;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public void cleanup(){
        for (Integer vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (Integer vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }
        for (Integer texture : textures) {
            GL11.glDeleteTextures(texture);
        }
    }

    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributesList(int attributeNumber, int coordinateSize,float[] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO(){
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
