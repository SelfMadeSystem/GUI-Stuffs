/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.input;

import org.lwjgl.glfw.GLFW;
import uwu.smsgamer.lwjgltest.Main;

public class KeyType {
    public static KeyType k(int keyCode) { //key
        return new KeyType(keyCode);
    }
    public static KeyType m(int mouseCode) { //mouse
        return new KeyType(mouseCode, true);
    }
    private final boolean mouse;
    private final int code;
    public KeyType() {
        this.mouse = false;
        this.code = -1;
    }
    public KeyType(int keyCode) {
        this.mouse = false;
        this.code = keyCode;
    }
    public KeyType(int keyCode, boolean isMouse) {
        this.code = keyCode;
        this.mouse = isMouse;
    }
    public boolean isDown() {
        if (mouse) return GLFW.glfwGetMouseButton(Main.window(), code) == 1;
        return GLFW.glfwGetKey(Main.window(), code) == 1;
    }
}
