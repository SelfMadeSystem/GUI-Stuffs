/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.click.parts;

import uwu.smsgamer.lwjgltest.gui.click.Part;

public class CategoryPart extends Part {
    public static int[] topSize = new int[]{150, 20};
    public static int[] mainSize = new int[]{150, 30};

    public float x, y;

    public CategoryPart(int index, String name) {

    }

    @Override
    public int[] getSize() {
        return topSize;
    }
}
