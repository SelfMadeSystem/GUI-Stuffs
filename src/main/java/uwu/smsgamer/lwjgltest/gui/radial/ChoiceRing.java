/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.radial;

import uwu.smsgamer.lwjgltest.stuff.ValStuff;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawCircle;
import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawString;

public class ChoiceRing extends Ring {
    ValStuff val;
    int amount;
    int hover = -1;

    public ChoiceRing(Ring prevRing, ValStuff val/*double min, double max, double step, double dVal*/) {
        super(prevRing);
        this.val = val;
        this.amount = val.choices.length;
    }

    @Override
    public void refresh() {
        this.amount = val.choices.length;
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
            if (over == 1) drawString(val.choices[hover], 0, 0, 0.15f, Color.WHITE);
            else {
                drawString(val.name, 0, 0.1f, 0.1f, Color.WHITE);
                drawString((String) val.value, 0, -0.07f, 0.06f, Color.WHITE);
            }
        }
        for (int i = 0; i < amount; i++) {
            Color circleColor = val.value.equals(val.choices[i]) ?
              over == 1 && hover == i && in ? selectedColorHighlight : selectedColor :
              over == 1 && hover == i && in ? secondColorHighlight : secondColor;
            drawCircle(0, 0, 0.98f, 0.77f, (i * spa + 1) * div + add, (i * spa + spa - 1) * div + add, 0.2f * div, circleColor);
        }
    }

    @Override
    public void click() {
        if (over == 0)
            Ring.prevRing();
        else if (over == 1)
            val.value = val.choices[hover];
    }
}
