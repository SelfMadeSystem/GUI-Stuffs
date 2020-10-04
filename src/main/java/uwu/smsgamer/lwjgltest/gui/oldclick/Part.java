/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.oldclick;

import uwu.smsgamer.lwjgltest.gui.oldclick.parts.Category;


@Deprecated
public abstract class Part {
    public void render() {
    }

    public void scroll(double amount) {
    }

    public void click(int button) {
    }

    public void unclick(int button) {
    }

    public void charKey(char c) {
    }

    public void key(int key) {
    }

    public int[] getSize() {
        return Category.mainSize;
    }
}
