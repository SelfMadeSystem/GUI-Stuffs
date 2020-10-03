/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.input;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import uwu.smsgamer.lwjgltest.Main;
import uwu.smsgamer.lwjgltest.gui.click.ClickGUIManager;

import java.nio.DoubleBuffer;
import static org.lwjgl.glfw.GLFW.*;

public class MouseHelper {
    public static int posX, posY;
    public static int deltaX, deltaY;
    public static boolean left, right, middle;
    public static void update() {
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(Main.window(), x, y);
        deltaX = (int) (posX - x.get(0));
        deltaY = (int) (posY - y.get(0));
        posX = (int) x.get(0);
        posY = (int) y.get(0);
        left = 1 == glfwGetMouseButton(Main.window(), 0);
        right = 1 == glfwGetMouseButton(Main.window(), 1);
        middle = 1 == glfwGetMouseButton(Main.window(), 2);
    }

    public static class ScrollCallBack implements GLFWScrollCallbackI {

        /**
         * Will be called when a scrolling device is used, such as a mouse wheel or scrolling area of a touchpad.
         *
         * @param window the window that received the event
         * @param xoffset the scroll offset along the x-axis
         * @param yoffset the scroll offset along the y-axis
         */
        @Override
        public void invoke(long window, double xoffset, double yoffset) {
            ClickGUIManager.getInstance().scroll(yoffset);
        }
    }
}
