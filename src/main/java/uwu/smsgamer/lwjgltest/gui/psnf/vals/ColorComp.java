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

package uwu.smsgamer.lwjgltest.gui.psnf.vals;

import uwu.smsgamer.lwjgltest.gui.psnf.Component;
import uwu.smsgamer.lwjgltest.gui.psnf.*;
import uwu.smsgamer.lwjgltest.gui.psnf.components.*;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

public class ColorComp extends ValComp {
    public ColorComp(ValStuff valStuff, Module module, Category category, Component prevComponent) {
        super(valStuff, module, category, prevComponent);
    }

    @Override
    public void render() {
        if (isActive()) unclick();
        RenderUtils.drawRoundBorderedRect(x, y, x + WIDTH, y + HEIGHT, ROUND, EDGE_RAD,
          (Color) valStuff.value, Color.RED);
    }

    @Override
    public void click() {
        unclick();
    }

    @Override
    public void unclick() {
        super.unclick();
    }
}
