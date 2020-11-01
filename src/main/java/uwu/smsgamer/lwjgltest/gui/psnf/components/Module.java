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
import uwu.smsgamer.lwjgltest.gui.psnf.*;
import uwu.smsgamer.lwjgltest.gui.psnf.vals.*;
import uwu.smsgamer.lwjgltest.input.InputManager;
import uwu.smsgamer.lwjgltest.stuff.*;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;
import java.util.LinkedList;

import static uwu.smsgamer.lwjgltest.gui.psnf.PSNFManager.CHANGE_TIME;
import static uwu.smsgamer.lwjgltest.gui.psnf.PSNFManager.MULT_Y;

public class Module extends Component {
    public final Category category;
    public final String module;
    public final int index;

    public LinkedList<ValComp> components = new LinkedList<>();

    public Module(Category category, String module, int index) {
        this.category = category;
        this.module = module;
        this.index = index;
        for (ValStuff stuff : Stuff.values.get(category.category).get(module)) {
            switch (stuff.type) {
                case VALUES:
                    components.add(new ValuesComp(stuff, this, category, this));
                    break;
                case CHOICE:
                    components.add(new ChoiceComp(stuff, this, category, this));
                    break;
                case STRING:
                    components.add(new StringComp(stuff, this, category, this));
                    break;
                case NUMBER:
                    components.add(new SliderComp(stuff, this, category, this));
                    break;
                case BOOLEAN:
                    components.add(new BoolComp(stuff, this, category, this));
                    break;
                case COLOUR:
                    components.add(new ColorComp(stuff, this, category, this));
                    break;
                default:
                    components.add(new DComp(stuff, this, category, this));
            }
        }
    }

    public int select = -1;
    public int lastSelect = -1;
    public long timeAdd;
    public long changeTime;

    @Override
    public void render() {
        RenderUtils.drawRoundBorderedRect(x, y, x + WIDTH, y + HEIGHT, ROUND, EDGE_RAD,
          selected ? new Color(255 - category.getOpacity() / 2, 255 - category.getOpacity() / 2,
            255 - category.getOpacity() / 2, category.getOpacity()) :
            new Color(127 + category.getReverseOpacity() / 2, 127 + category.getReverseOpacity() / 2,
              127 + category.getReverseOpacity() / 2, category.getOpacity()),
          new Color(255, 0, 0, category.getOpacity()));
        RenderUtils.drawString(String.valueOf(module), x + EDGE_RAD + 2, y + HEIGHT / 2, 16f, -1, new Color(255, 255, 255, category.getOpacity()));
        if (mngr().selected && selected) {
            mngr().cursorY = (lastSelect + 1 + ((select - lastSelect) * getChange()
            )) * -MULT_Y;
            mngr().cursorX = 0;
            for (int i = 0; i < components.size(); i++) {
                ValComp component = components.get(i);
                component.x = x;
                component.y = y - (1 + i) * PSNFManager.MULT_Y;
                component.render();
            }
        }
        if (mngr().selected && selected && isActive()) {
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
        if (mngr().selected) {
            if (select == -1) {
                unclick();
                return;
            }
            Component component = components.get(select);
            component.click();
            mngr().currentComponent = component;
            component.selected = true;
        } else {
            mngr().selected = true;
            mngr().currentComponent = this;
            selected = true;
        }
    }

    @Override
    public void unclick() {
        mngr().selected = false;
        mngr().cursorY = 0;
        select = -1;
        lastSelect = -1;
    }

    public void changeComponent(int diff) {
        changeComponent(diff, 0);
    }

    public void changeComponent(int diff, long timeAdd) {
        lastSelect = select;
        select += diff;
        if (select >= -1 && select < components.size()) {
            this.timeAdd = timeAdd;
            changeTime = System.currentTimeMillis() + Math.min(CHANGE_TIME - getChangeTime(), CHANGE_TIME) + timeAdd;
        } else if (select >= components.size()) select = components.size() - 1;
        else select = -1;
    }

    public float getChange() {
        return (1 - (getChangeTime() / (float) (CHANGE_TIME + timeAdd)));
    }

    public long getChangeTime() {
        return Math.max(0, changeTime - System.currentTimeMillis());
    }
}
