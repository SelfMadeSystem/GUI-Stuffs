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
import uwu.smsgamer.lwjgltest.utils.*;

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
        RenderUtils.drawString(String.valueOf(valStuff.name), x + EDGE_RAD + 2, y + HEIGHT / 16 * 11, 14f, -1, Color.WHITE);
        Colour.RGB rgb = new Colour.RGB((Color) valStuff.value);
        RenderUtils.drawString(String.format("#%02x%02x%02x", (int) (rgb.r * 255), (int) (rgb.g * 255), (int) (rgb.b * 255)),
          x + EDGE_RAD + 2, y + HEIGHT / 16 * 5, 8f, -1, Color.WHITE);
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
