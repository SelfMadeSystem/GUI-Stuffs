/*
 ----------------------------------------------------*\
 |                                                      |
 |    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
 |   //      Copyright (c) 2020 Shoghi Simon       \\   |
 |   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
 |    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
 |                                                      |
 \*----------------------------------------------------
 */

package uwu.smsgamer.lwjgltest.gui.colour;

public abstract class ColourComponent {
    public static final int WIDTH = 500 / 9;
    public static final int HEIGHT = 500 / 3;
    public static final int X_OFFSET = -250;
    public static final int Y_OFFSET = -250 / 3;

    public abstract void render();

    public ColourManager mngr() {
        return ColourManager.getInstance();
    }
}
