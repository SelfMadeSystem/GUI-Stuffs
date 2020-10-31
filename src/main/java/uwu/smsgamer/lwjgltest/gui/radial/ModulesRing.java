/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.radial;

import uwu.smsgamer.lwjgltest.stuff.Stuff;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawCircle;
import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawString;

public class ModulesRing extends Ring {
    int amount;
    int hover = -1;
    String category;
    String[] modules;

    public ModulesRing(Ring prevRing, String category) {
        super(prevRing);
        this.category = category;
        this.modules = Stuff.values.get(category).keySet().toArray(new String[0]);
        this.amount = modules.length;
    }

    @Override
    public void refresh() {
        this.modules = Stuff.values.get(category).keySet().toArray(new String[0]);
        this.amount = modules.length;
    }

    @Override
    public void renderStuff() {
        boolean in = this == currentRing;
        float div = in ? 1 : 0;
        if (lastSwitch < switchSpeed) div = in ? (lastSwitch) / switchSpeed : 1 - (lastSwitch) / switchSpeed;
        float add = in ? 0 : (360 - 360 * div);
        float spa = 360f / amount;
        if (in) {
            hover = (int) (rot / spa);
            if (hover == amount) hover = 0;
            if (over == 1) drawString(modules[hover], 0, 0, 0.15f, 0, Color.WHITE);
            else drawString(category, 0, 0, 0.15f, 0, Color.WHITE);
        }
        for (int i = 0; i < amount; i++) {
            Color circleColor = over == 1 && hover == i ? secondColorHighlight : secondColor;
            drawCircle(0, 0, 0.98f, 0.77f,
              (i * spa + 1) * div + add, (i * spa + spa - 1) * div + add,
              0.2f * div, circleColor);
        }
    }

    @Override
    public void click() {
        switch (over) {
            case 0: {
                Ring.prevRing();
                break;
            }
            case 1: {
                Ring.newRing(new ValuesRing(this, category, modules[hover]));
                break;
            }
        }
    }
}
