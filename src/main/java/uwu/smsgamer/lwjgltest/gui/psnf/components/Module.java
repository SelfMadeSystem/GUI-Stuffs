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

package uwu.smsgamer.lwjgltest.gui.psnf.components;

import uwu.smsgamer.lwjgltest.gui.psnf.Component;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

public class Module extends Component {
    public final Category category;
    public final String module;
    public final int index;

    public Module(Category category, String module, int index) {
        this.category = category;
        this.module = module;
        this.index = index;
    }

    @Override
    public void render() {
        RenderUtils.drawRoundBorderedRect(x, y, x + WIDTH, y + HEIGHT, ROUND, EDGE_RAD,
          selected ? new Color(255-category.getOpacity()/2, 255-category.getOpacity()/2,
            255-category.getOpacity()/2, category.getOpacity()) :
            new Color(127+category.getReverseOpacity()/2, 127+category.getReverseOpacity()/2,
              127+category.getReverseOpacity()/2, category.getOpacity()),
          new Color(255, 0, 0, category.getOpacity()));
    }

    @Override
    public void click() {
    }
}
