package core.gui;

import core.objects.GameObject;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiSelectableFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

import java.util.ArrayList;
import java.util.List;

public class EntitiesList {

    public static ImInt currentObject = new ImInt(0);

    public static void update(){
        List<String> gameObjectsNames = new ArrayList<>();
        for (GameObject gameObject : GameObject.gameObjects) {
            gameObjectsNames.add(gameObject.getName());
        }


        ImGui.setNextWindowCollapsed(true, ImGuiCond.Once);

        ImGui.begin("Gameobjects list", new ImBoolean(true), ImGuiWindowFlags.AlwaysAutoResize);
        ImGui.setWindowPos(10,10);

        ImGui.labelText(" ", "Gameobjects in the scene");
        ImGui.listBox(" ", currentObject, gameObjectsNames.toArray(new String[0]), 15);

        ImGui.end();
    }
}
