/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.click.parts;

import uwu.smsgamer.lwjgltest.gui.click.*;
import uwu.smsgamer.lwjgltest.input.MouseHelper;
import uwu.smsgamer.lwjgltest.stuff.Stuff;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;
import java.util.*;

public class CategoryPart extends Part {
    public ModulePart[] modules;
    public float x, y;
    public float yAdd;
    public float scroll;

    public CategoryPart(int index, String name) {
        super(name, null);
        this.category = this;
        this.x = -250 + (getSize()[0] / 2F + 5);
        this.y = 250 - ((getSize()[1] / 2F + 5) + (getSize()[1] * 1.5F) * index);
        ArrayList<String> stringList = new ArrayList<>(Stuff.values.get(this.name).keySet());
        Collections.sort(stringList);
        this.modules = new ModulePart[stringList.size()];
        for (int i = 0; i < stringList.size(); i++) {
            String s = stringList.get(i);
            this.modules[i] = new ModulePart(s, this);
        }
    }

    public void render() {
        if (this.clicking) {
            this.x -= MouseHelper.deltaX;
            this.y += MouseHelper.deltaY;
            if (this.x > (250 - getSize()[0] / 2F) || this.x < -250 + getSize()[0] / 2F)
                this.y -= MouseHelper.deltaY * 0.8F;
            if (y > (250 - getSize()[1] / 2F) || this.y < -250 + getSize()[1] / 2F)
                this.x += MouseHelper.deltaX * 0.8F;
            this.x = Math.min(250 - getSize()[0] / 2F, Math.max(-250 + getSize()[0] / 2F, this.x));
            this.y = Math.min(250 - getSize()[1] / 2F, Math.max(-250 + getSize()[1] / 2F, this.y));
        }
        this.yAdd = getSize()[1] / 2F - scroll;
        if (this.open) {
            for (ModulePart module : this.modules) {
                module.render(this.x, this.y, this.y - maxLength);
            }
        }
        RenderUtils.drawRoundBorderedRect(this.x - (getSize()[0] / 2F), this.y - (getSize()[1] / 2F),
          this.x + (getSize()[0] / 2F), this.y + (getSize()[1] / 2F), 0, 2,
          notOverridden() && hoveringTop() || clicking ? TOP_COLOR_HOVER : TOP_COLOR, TOP_BORDER_COLOR);
        RenderUtils.drawString(this.name, this.x, this.y, 0.09F, 0.06F, Color.BLACK);
    }

    boolean justOpened = false;
    boolean clicking = false;

    @Override
    public void open() {
        super.open();
        this.justOpened = true;
    }

    @Override
    public void close() {
        super.close();
        this.scroll = 0;
        for (ModulePart module : modules) {
            module.close();
        }
    }

    @Override
    public void click(int button) {
        super.click(button);
        if (this.justOpened) this.justOpened = false;
        if (notOverridden() && hoveringTop()) {
            ClickGUIManager.getInstance().getCategories().remove(this);
            ClickGUIManager.getInstance().getCategories().add(this);
            if (button == 1) {
                if (this.open) close();
                else open();
                return;
            } else if (button == 0) {
                this.clicking = true;
                return;
            }
        }
        if (open) {
            for (ModulePart module : modules) {
                module.click(button);
            }
        }
    }

    @Override
    public void unclick(int button) {
        super.unclick(button);
        if (this.clicking && button == 0) {
            this.clicking = false;
            return;
        }
        if (open && !justOpened) {
            for (ModulePart module : modules) {
                module.unclick(button);
            }
        }
    }

    private long lastScroll = 0;

    @Override
    public void scroll(double amount) {
        super.scroll(amount);
        if (hoveringModules()) {
            //float limit = this.yAdd - scroll;
            scroll = (float) (scroll + amount * (230 + Math.max(-200, lastScroll - System.currentTimeMillis())) / 30);
            //System.out.println(limit + " " + scroll);
            //if (scroll > limit) scroll = limit;
            /*else*/ if (scroll < 0) scroll = 0;
            lastScroll = System.currentTimeMillis();
        }
        /*if (open) {
            for (ModulePart module : modules) {
                module.scroll(amount);
            }
        }*/
    }

    @Override
    public void charKey(char c) {
        super.charKey(c);
        if (open) {
            for (ModulePart module : modules) {
                module.charKey(c);
            }
        }
    }

    @Override
    public void key(int key) {
        super.key(key);
        if (open) {
            for (ModulePart module : modules) {
                module.key(key);
            }
        }
    }

    @Override
    public int[] getSize() {
        return topSize;
    }

    public boolean hoveringTop() {
        return hover(-getSize()[0] / 2F, getSize()[0] / 2F,
          -getSize()[1] / 2F, getSize()[1] / 2F);
    }

    public boolean hoveringModules() {
        return open && hover(-getSize()[0] / 2F, getSize()[0] / 2F,
          -getSize()[1] / 2F - Math.min(maxLength, this.yAdd), -getSize()[1] / 2F);
    }

    public boolean hover(double minX, double maxX, double minY, double maxY) {
        return hoverRaw(this.x + minX, this.x + maxX, this.y + minY, this.y + maxY);
    }

    public boolean hoverRaw(double minX, double maxX, double minY, double maxY) {
        int mouseX = MouseHelper.posX - 250;
        int mouseY = -MouseHelper.posY + 250;
        return (mouseX > minX && mouseX < maxX) && (mouseY > minY && mouseY < maxY);
    }
}
