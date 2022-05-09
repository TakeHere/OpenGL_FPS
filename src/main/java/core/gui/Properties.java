package core.gui;

import core.Consts;
import core.objects.GameObject;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;

public class Properties {

    public static void update(){
        if (Consts.DEBUG){
            GameObject currentObject = GameObject.gameObjects.get(EntitiesList.currentObject.get());

            String objectName = currentObject.getName();
            float[] x = new float[] {currentObject.getPosition().x};
            float[] y = new float[] {currentObject.getPosition().y};
            float[] z = new float[] {currentObject.getPosition().z};

            float[] rotX = new float[] {currentObject.getRotation().x};
            float[] rotY = new float[] {currentObject.getRotation().y};
            float[] rotZ = new float[] {currentObject.getRotation().z};


            ImGui.setNextWindowCollapsed(true, ImGuiCond.Once);

            ImGui.begin("Properties", new ImBoolean(true), ImGuiWindowFlags.AlwaysAutoResize);
            //ImGui.setWindowPos(250,10);
            ImGui.setWindowPos(730,10);

            ImGui.labelText("Name", objectName);

            if(ImGui.collapsingHeader("Position")){
                ImGui.dragFloat("x position", x, 0.1f);
                ImGui.dragFloat("y position", y, 0.1f);
                ImGui.dragFloat("z position", z, 0.1f);

                currentObject.getPosition().x = x[0];
                currentObject.getPosition().y = y[0];
                currentObject.getPosition().z = z[0];
            }

            if(ImGui.collapsingHeader("Rotation")){
                ImGui.dragFloat("x rotation", rotX, 0.1f);
                ImGui.dragFloat("y rotation", rotY, 0.1f);
                ImGui.dragFloat("z rotation", rotZ, 0.1f);

                currentObject.getRotation().x = rotX[0];
                currentObject.getRotation().y = rotY[0];
                currentObject.getRotation().z = rotZ[0];
            }

            ImGui.end();
        }
    }
}
