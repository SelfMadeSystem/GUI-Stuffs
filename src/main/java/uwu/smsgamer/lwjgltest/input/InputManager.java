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
import org.lwjgl.glfw.*;
import uwu.smsgamer.lwjgltest.gui.block.BlockManager;
import uwu.smsgamer.lwjgltest.gui.click.ClickGUIManager;
import uwu.smsgamer.lwjgltest.gui.radial.Ring;

import static org.lwjgl.glfw.GLFW.*;
import static uwu.smsgamer.lwjgltest.input.KeyType.k;
import static uwu.smsgamer.lwjgltest.input.KeyType.m;

public class InputManager {
    public static InputType UP = new InputType(k(GLFW_KEY_W), k(GLFW_KEY_UP));
    public static InputType DOWN = new InputType(k(GLFW_KEY_S), k(GLFW_KEY_DOWN));
    public static InputType LEFT = new InputType(k(GLFW_KEY_A), k(GLFW_KEY_LEFT));
    public static InputType RIGHT = new InputType(k(GLFW_KEY_D), k(GLFW_KEY_RIGHT));
    public static InputType SELECT = new InputType(k(GLFW_KEY_ENTER), k(GLFW_KEY_SPACE), m(GLFW_MOUSE_BUTTON_LEFT));
    public static InputType BACK = new InputType(k(GLFW_KEY_ESCAPE), m(GLFW_MOUSE_BUTTON_RIGHT));
//    public static InputType ML = new InputType(m(GLFW_MOUSE_BUTTON_LEFT));
//    public static InputType MR = new InputType(m(GLFW_MOUSE_BUTTON_RIGHT));
    private static final InputType[] loop = new InputType[]{UP, DOWN, LEFT, RIGHT, SELECT, BACK};

    public static void update() {
        MouseHelper.update();
        for (InputType l : loop) {
            l.update();
        }
    }

    public static class CharThingy implements GLFWCharCallbackI {
        /**
         * Will be called when a Unicode character is input.
         *
         * @param window the window that received the event
         * @param c the Unicode code point of the character
         */
        @Override
        public void invoke(long window, int c) {
            Ring.getCurrentRing().charKey((char) c);
            BlockManager.getInstance().charKey((char) c);
            ClickGUIManager.getInstance().charKey((char) c);
        }
    }

    public static class KeyThingy implements GLFWKeyCallbackI {

        /**
         * Will be called when a key is pressed, repeated or released.
         *
         * @param window the window that received the event
         * @param key the keyboard key that was pressed or released
         * @param scancode the system-specific scancode of the key
         * @param action the key action. One of:<br><table><tr><td>{@link GLFW#GLFW_PRESS PRESS}</td><td>{@link GLFW#GLFW_RELEASE RELEASE}</td><td>{@link GLFW#GLFW_REPEAT REPEAT}</td></tr></table>
         * @param mods bitfield describing which modifiers keys were held down
         */
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (action != GLFW_RELEASE) {
                Ring.getCurrentRing().key(key);
                BlockManager.getInstance().key(key);
                ClickGUIManager.getInstance().key(key);
            }
        }
    }
}
