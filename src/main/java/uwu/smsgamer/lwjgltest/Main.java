package uwu.smsgamer.lwjgltest;

import uwu.smsgamer.lwjgltest.render.Window;

public class Main {
    public static long deltaTime;
    public static Window window;

    public static long window() {
        return window.getWindow();
    }

    public static void main(String[] args) {
        window = new Window(500, 500, "wtf");

        window.start();
    }
}
