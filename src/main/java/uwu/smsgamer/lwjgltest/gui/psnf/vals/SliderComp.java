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
import uwu.smsgamer.lwjgltest.input.InputManager;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

public class SliderComp extends ValComp {
    private static final long CHANGE_TIME = 250L;
    public SliderComp(ValStuff valStuff, Module module, Category category, Component prevComponent) {
        super(valStuff, module, category, prevComponent);
    }

    public long timeAdd;
    public long changeTime;
    public double amount;

    @Override
    public void render() {
        RenderUtils.drawRoundBorderedRect(x, y, x + WIDTH, y + HEIGHT, ROUND, EDGE_RAD,
          isActive() ? Color.DARK_GRAY : Color.GRAY, Color.RED);
        RenderUtils.drawRect(x + EDGE_RAD, y + EDGE_RAD,
          (float) (x + EDGE_RAD + ((WIDTH - EDGE_RAD * 2) * getValue())), y + HEIGHT - EDGE_RAD,
          isActive() ? Color.BLUE : Color.CYAN);
        RenderUtils.drawString(String.valueOf(valStuff.name), x + WIDTH / 2, y + HEIGHT / 16 * 11, 14f, Color.WHITE);
        RenderUtils.drawString(String.valueOf(valStuff.value), x + WIDTH / 2, y + HEIGHT / 16 * 5, 8f, Color.WHITE);
        if (isActive()) {
            if (InputManager.LEFT.justPressed()) {
                changeValue(-1);
                amount = 0;
            } else if (InputManager.LEFT.pressTimeMS() - CHANGE_TIME * 2 > 0 && getChangeTime() <= 0) {
                changeValue((int) -amount, -250);
                amount += 0.6;
            }
            if (InputManager.RIGHT.justPressed()) {
                changeValue(1);
                amount = 0;
            } else if (InputManager.RIGHT.pressTimeMS() - CHANGE_TIME * 2 > 0 && getChangeTime() <= 0) {
                changeValue((int) amount, -250);
                amount+=0.6;
            }
        }
    }

    @Override
    public void click() {
        if (mngr().currentComponent == this) {
            unclick();
        }
    }

    public void changeValue(int diff) {
        changeValue(diff, 0);
    }

    public void changeValue(int diff, long timeAdd) {
        valStuff.value = ((Number) valStuff.value).doubleValue() + valStuff.step * diff;
        if (((Number) valStuff.value).doubleValue() >= valStuff.min && ((Number) valStuff.value).doubleValue() <= valStuff.max) {
            this.timeAdd = timeAdd;
            changeTime = System.currentTimeMillis() + Math.min(CHANGE_TIME - getChangeTime(), CHANGE_TIME) + timeAdd;
        } else if (((Number) valStuff.value).doubleValue() > valStuff.max)
            valStuff.value = valStuff.max;
        else valStuff.value = valStuff.min;
    }

    public float getChange() {
        return (1 - (getChangeTime() / (float) (CHANGE_TIME + timeAdd)));
    }

    public long getChangeTime() {
        return Math.max(0, changeTime - System.currentTimeMillis());
    }

    public double getNumber() {
        return ((Number) valStuff.value).doubleValue();
    }

    public double getValue() {
        return (((Number) valStuff.value).doubleValue() - valStuff.min) / (valStuff.max - valStuff.min);
    }
}
