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
import uwu.smsgamer.lwjgltest.stuff.*;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

public class ModulePart extends Part {
    
    ValPart[] parts;

    public ModulePart(String name, CategoryPart category) {
        super(name, category);
        ValStuff[] valStuffs = Stuff.values.get(category.name).get(name);
        this.parts = new ValPart[valStuffs.length];
        for (int i = 0; i < valStuffs.length; i++) {
            ValStuff valStuff = valStuffs[i];
            switch (valStuff.type) {
                /*case VALUES:
                    parts[i] = new ValuesPart(valStuff, this, this.category, 0);
                    break;
                case NUMBER:
                    parts[i] = new SliderPart(valStuff, this.category, this);
                    break;
                case BOOLEAN:
                    parts[i] = new TogglePart(valStuff, this.category, this);
                    break;
                case STRING:
                    parts[i] = new StringPart(valStuff, this.category, this);
                    break;
                case CHOICE:
                    parts[i] = new ChoicePart(valStuff, this, this.category, 0);
                    break;*/
                default:
                    parts[i] = new PPart(this.category, valStuff, this, 0);
            }
        }
    }

    @Override
    public void render(float x, float y, float maxY) {
        super.render(x, y, maxY);
        if (open) {
            for (ValPart part : parts) {
                part.render(x, y, maxY);
            }
        }

        RenderUtils.drawBorderedRect(getX() - getSize()[0] / 2F, getY() - getSize()[1] / 2F,
          getX() + getSize()[0] / 2F, getY() + getSize()[1] / 2F, 2,
          hovering() ? MAIN_COLOR_HOVER : MAIN_COLOR, BORDER_COLOR);
        RenderUtils.drawString(this.name, getX(), getY(), 0.1F, Color.BLACK);
    }

    boolean justOpened;

    @Override
    public void click(int button) {
        super.click(button);
        if (this.justOpened) this.justOpened = false;
        if (hovering()) {
            if (button == 1) {
                if (this.open) close();
                else open();
                return;
            }
        }
        if (open) {
            for (ValPart module : parts) {
                module.click(button);
            }
        }
    }

    @Override
    public void unclick(int button) {
        super.unclick(button);
        if (open && !justOpened) {
            for (ValPart part : parts) {
                part.unclick(button);
            }
        }
    }

    @Override
    public void scroll(double amount) {
        super.scroll(amount);
        if (open) {
            for (ValPart part : parts) {
                part.scroll(amount);
            }
        }
    }

    @Override
    public void charKey(char c) {
        super.charKey(c);
        if (open) {
            for (ValPart part : parts) {
                part.charKey(c);
            }
        }
    }

    @Override
    public void key(int key) {
        super.key(key);
        if (open) {
            for (ValPart part : parts) {
                part.key(key);
            }
        }
    }
}
