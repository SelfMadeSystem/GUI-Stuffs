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
import java.util.LinkedList;

import static uwu.smsgamer.lwjgltest.gui.psnf.PSNFManager.*;

public class ChoiceComp extends ValComp {
    public LinkedList<ValComp> components = new LinkedList<>();

    public ChoiceComp(ValStuff valStuff, Module module, Category category, Component prevComponent) {
        super(valStuff, module, category, prevComponent);
        for (String s : valStuff.choices) {
            components.add(new Choice(valStuff, module, category, this, s));
        }
    }

    public int select;
    public int lastSelect;
    public long timeAdd;
    public long changeTime;

    @Override
    public void render() {
        RenderUtils.drawRoundBorderedRect(x, y, x + WIDTH, y + HEIGHT, ROUND, EDGE_RAD,
          selected ? Color.GRAY : Color.BLUE, Color.RED);
        if (selected) {
            mngr().cursorY = (lastSelect + 1 + ((select - lastSelect) * getChange()
            )) * -MULT_Y;
            mngr().cursorX = x - OFFSET_X + MULT_X;
            for (int i = 0; i < components.size(); i++) {
                ValComp component = this.components.get(i);
                component.x = x + MULT_X;
                component.y = OFFSET_Y - (1 + i) * MULT_Y;
                component.render();
            }
        }
        RenderUtils.drawString(String.valueOf(valStuff.name), x + EDGE_RAD + 2, y + HEIGHT / 16 * 11, 14f, -1, Color.WHITE);
        RenderUtils.drawString(String.valueOf(valStuff.value), x + EDGE_RAD + 2, y + HEIGHT / 16 * 5, 8f, -1, Color.WHITE);
        if (isActive()) {
            if (InputManager.UP.justPressed()) {
                changeComponent(-1);
            } else if (InputManager.UP.pressTimeMS() - CHANGE_TIME * 2 > 0 && getChangeTime() <= 0) {
                changeComponent(-1, -250);
            }
            if (InputManager.DOWN.justPressed()) {
                changeComponent(1);
            } else if (InputManager.DOWN.pressTimeMS() - CHANGE_TIME * 2 > 0 && getChangeTime() <= 0) {
                changeComponent(1, -250);
            }
        }
    }

    @Override
    public void click() {
        if (mngr().currentComponent != this) return;
        this.components.get(select).click();
    }

    @Override
    public void unclick() {
        super.unclick();
        select = 0;
        lastSelect = 0;
    }

    public void changeComponent(int diff) {
        changeComponent(diff, 0);
    }

    public void changeComponent(int diff, long timeAdd) {
        lastSelect = select;
        select += diff;
        if (select >= 0 && select < components.size()) {
            this.timeAdd = timeAdd;
            changeTime = System.currentTimeMillis() + Math.min(CHANGE_TIME - getChangeTime(), CHANGE_TIME) + timeAdd;
        } else if (select >= components.size()) select = components.size() - 1;
        else select = 0;
    }

    public float getChange() {
        return (1 - (getChangeTime() / (float) (CHANGE_TIME + timeAdd)));
    }

    public long getChangeTime() {
        return Math.max(0, changeTime - System.currentTimeMillis());
    }

    private static class Choice extends ValComp {

        public final String choice;

        public Choice(ValStuff valStuff, Module module, Category category, Component prevComponent, String choice) {
            super(valStuff, module, category, prevComponent);
            this.choice = choice;
        }

        @Override
        public void render() {
            RenderUtils.drawRoundBorderedRect(x, y, x + WIDTH, y + HEIGHT, ROUND, EDGE_RAD, Color.LIGHT_GRAY, Color.RED);
            RenderUtils.drawString(String.valueOf(choice), x + EDGE_RAD + 2, y + HEIGHT / 2, 16f, -1, Color.WHITE);
        }

        @Override
        public void click() {
            unclick();
            valStuff.value = choice;
        }
    }
}
