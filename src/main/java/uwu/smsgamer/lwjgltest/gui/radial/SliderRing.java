/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.radial;

import uwu.smsgamer.lwjgltest.input.MouseHelper;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.*;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawCircle;

public class SliderRing extends Ring {
    double min;
    double max;
    double step;
    ValStuff val;
    boolean holding = false;

    public SliderRing(Ring prevRing, ValStuff val/*double min, double max, double step, double dVal*/) {
        super(prevRing);
        this.min = val.min;
        this.step = val.step;
        if (this.step <= 0)
            this.step = Double.MIN_VALUE;
        this.max = val.max;
        this.val = val;
    }

    @Override
    public void refresh() {
        this.min = val.min;
        this.step = val.step;
        if (this.step <= 0)
            this.step = Double.MIN_VALUE;
        this.max = val.max;
        this.holding = false;
    }

    boolean lock = false;
    double prevRot = rot;
    double setRot = rot;

    double val() {
        return (double) val.value;
    }

    @Override
    public void renderStuff() {
        boolean in = this == currentRing;
        float div = in ? 1 : 0;
        if (lastSwitch < switchSpeed) div = in ? (lastSwitch) / switchSpeed : 1 - (lastSwitch) / switchSpeed;
        float add = in ? 0 : (360 - 360 * div);
        float sda = (float) ((val() - min) / (max - min) * 360);
        float sda0 = (float) ((val() - (step / 2) - min) / (max - min) * 360);
        if (in) {
            if (holding) {
                if (prevRot >= 330 && rot <= 30) {
                    lock = true;
                    setRot = 360;
                } else if (prevRot <= 30 && rot >= 330) {
                    lock = true;
                    setRot = 0;
                } else {
                    lock = false;
                    setRot = rot;
                }
            } else lock = false;
            if (mouseDownTime > 2000 && holding && !MouseHelper.left)
                holding = false;
            if (holding) {
                double value = min + (setRot) / 360 * (max - min) + step / 2;
                val.value = MathUtils.roundInc(value, step);
                drawCircle(0, 0, 0.98f, 0.77f, 0,
                  (float) ((val() + (step / 2) - min) / (max - min) * 360), 0.2f * div, sliderHintAfter);
            }
            RenderUtils.drawString(String.valueOf(val()), 0, 0, 0.25f, Color.WHITE);
        }
        Color color = !holding && over == 1 && in ? sliderColorHover : sliderColor;
        drawCircle(0, 0, 0.98f, 0.77f, add, sda * div + add, 0.2f * div, color);
        if (in) {
            if (holding && sda0 > 0) {
                drawCircle(0, 0, 0.98f, 0.77f, 0,
                  sda0, 0.2f * div, sliderHintBefore);
            }
            if (!lock) prevRot = rot;
        }
    }

    @Override
    public void click() {
        if (over == 0 && !holding)
            prevRing();
        else {
            holding = !holding;
        }
    }
}
