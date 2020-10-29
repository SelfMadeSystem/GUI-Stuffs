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

import uwu.smsgamer.lwjgltest.gui.psnf.*;
import uwu.smsgamer.lwjgltest.gui.psnf.Component;
import uwu.smsgamer.lwjgltest.input.InputManager;
import uwu.smsgamer.lwjgltest.stuff.Stuff;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;
import java.util.*;

import static uwu.smsgamer.lwjgltest.gui.psnf.PSNFManager.CHANGE_TIME;

public class Category extends Component {
    public final String category;
    public final LinkedList<Module> modules = new LinkedList<>();
    public final int index;

    public Category(String category, int index) {
        this.category = category;
        this.index = index;
        ArrayList<String> strings = new ArrayList<>(Stuff.values.get(category).keySet());
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            modules.add(new Module(this, s, i));
        }
    }
    public int select;
    public int lastSelect;
    public long timeAdd;
    public long changeTime;

    @Override
    public void render() {
        if (mngr().select == index) {
            if (opacity != 255) setOpacity(255);
        } else if (Math.abs(mngr().select - index) == 1) {
            if (opacity != 127) setOpacity(127);
        } else if (Math.abs(mngr().select - index) == 2) {
            if (opacity != 0) setOpacity(0);
        } else { // panik
            opacity = 0;
            prevOpacity = 0;
        }
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            module.selected = (select == i);
            module.x = x + (i - lastSelect - ((select - lastSelect) * getChange()
            )) * PSNFManager.MULT_X;
            module.y = y;
            module.render();
        }
        if (InputManager.LEFT.justPressed()) {
            changeModule(-1);
        } else if (InputManager.LEFT.pressTimeMS() - CHANGE_TIME * 2 > 0 && getChangeTime() <= 0) {
            changeModule(-1, -250);
        }
        if (InputManager.RIGHT.justPressed()) {
            changeModule(1);
        } else if (InputManager.RIGHT.pressTimeMS() - CHANGE_TIME * 2 > 0 && getChangeTime() <= 0) {
            changeModule(1, -250);
        }
//        RenderUtils.drawRoundBorderedRect(x, y, x + WIDTH, y + HEIGHT, ROUND, EDGE_RAD,
//          selected ? new Color(255-getOpacity()/2, 255-getOpacity()/2, 255-getOpacity()/2, getOpacity()) :
//            new Color(127+getReverseOpacity()/2, 127+getReverseOpacity()/2, 127+getReverseOpacity()/2, getOpacity()),
//          new Color(255, 0, 0, getOpacity()));
    }

    @Override
    public void click() {
    }

    public void changeModule(int diff) {
        changeModule(diff, 0);
    }

    public void changeModule(int diff, long timeAdd) {
        lastSelect = select;
        select += diff;
        if (select >= 0 && select < modules.size()) {
            this.timeAdd = timeAdd;
            changeTime = System.currentTimeMillis() + Math.min(CHANGE_TIME - getChangeTime(), CHANGE_TIME) + timeAdd;
        } else if (select >= modules.size()) select = modules.size() - 1;
        else select = 0;
    }

    public float getChange() {
        return (1 - (getChangeTime() / (float) (CHANGE_TIME + timeAdd)));
    }

    public long getChangeTime() {
        return Math.max(0, changeTime - System.currentTimeMillis());
    }
}
