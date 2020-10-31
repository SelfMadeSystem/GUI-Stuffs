/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.radial;

import org.lwjgl.glfw.GLFW;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.*;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawCircle;

public class StringRing extends Ring {
    ValStuff val;

    public StringRing(Ring prevRing, ValStuff val/*double min, double max, double step, double dVal*/) {
        super(prevRing);
        this.val = val;
    }

    @Override
    public void refresh() {
    }

    int hover;
    int timer;
    float r;

    @Override
    public void renderStuff() {
        boolean in = this == currentRing;
        float div = in ? 1 : 0;
        if (lastSwitch < switchSpeed) div = in ? (lastSwitch) / switchSpeed : 1 - (lastSwitch) / switchSpeed;
        float add = in ? 0 : (360 - 360 * div);
        float amount = 5;
        float spa = 1;
        if (in) {
            timer++;
            r = MathUtils.cos(timer / 2f) * 90;
            spa = 360 / amount;
            hover = (int) ((MathUtils.wrapAngle180(rot - r + 180) + 180) / spa);
            if (hover == amount) hover = 0;
            RenderUtils.drawString(String.valueOf(val.value), 0, 0, 0.08f, 0, Color.WHITE);
        } else r = 0;
        drawCircle(0, 0, 1.01f, 0.74898f, add, (360 * div) + add, 0, new Color(10, 10, 10));
        for (int i = 0; i < amount; i++) {
            Color circleColor = over == 1 && hover == i && in ? new Color(25, 25, 25) : new Color(20, 20, 20);
            drawCircle(0, 0, 0.98f, 0.77f,
              (r + i * spa + 1) * div + add, (r + i * spa + spa - 1) * div + add,
              0.2f * div, circleColor);
        }//all of the above is just fanciness lol
    }

    @Override
    public void charKey(char c) {
        String text = (String) val.value;
        if (AllowedChars.isAllowedCharacter(c)) {
            text += c;
        }
        val.value = text;
    }

    @Override
    public void key(int k) {
        String text = (String) val.value;
        if (k == GLFW.GLFW_KEY_BACKSPACE) {
            if (text.length() > 0) {
                text = text.substring(0, text.length() - 1);
            }
        }
        val.value = text;
    }

    @Override
    public void click() {
        if (over == 0) {
            prevRing();
        }
    }
}
