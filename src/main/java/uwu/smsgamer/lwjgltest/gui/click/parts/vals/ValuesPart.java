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

package uwu.smsgamer.lwjgltest.gui.click.parts.vals;

import uwu.smsgamer.lwjgltest.gui.click.ValPart;
import uwu.smsgamer.lwjgltest.gui.click.parts.*;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

public class ValuesPart extends ValPart {
    public static Color BASE_COLOR = new Color(100, 100, 100);
    public static Color HOVER_COLOR = new Color(80, 80, 80);
    public static Color OPEN_COLOR = new Color(70, 70, 70);
    public ValPart[] parts;

    public ValuesPart(CategoryPart category, ValStuff valStuff, ModulePart module, int indent) {
        super(category, valStuff, module, indent);
        ValStuff[] valStuffs = valStuff.values;
        this.parts = new ValPart[valStuffs.length];
        for (int i = 0; i < valStuffs.length; i++) {
            ValStuff vs = valStuffs[i]; //not visual studio lol
            switch (vs.type) {
                case VALUES:
                    parts[i] = new ValuesPart(this.category, vs, this.module, indent + 2);
                    break;
                case BOOLEAN:
                    parts[i] = new TogglePart(this.category, vs, this.module, indent + 2);
                    break;
                case NUMBER:
                    parts[i] = new SliderPart(this.category, vs, this.module, indent + 2);
                    break;/*
                case STRING:
                    parts[i] = new StringPart(this.category, vs, this.module, indent + 1);
                    break;
                case CHOICE:
                    parts[i] = new ChoicePart(this.category, vs, this.module, indent + 1);
                    break;*/
                default:
                    parts[i] = new PPart(this.category, vs, this.module, indent + 2);
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

        if (getY() + getSize()[1] / 2F > maxY &&
          getY() - getSize()[1] / 2F < category.y + category.getSize()[1] / 2F) {
            RenderUtils.drawBorderedRect(getX() - getSize()[0] / 2F + indent * 2,
              Math.min(category.y, Math.max(maxY, getY() - getSize()[1] / 2F)),
              getX() + getSize()[0] / 2F, Math.min(category.y, getY() + getSize()[1] / 2F), edgeRadius,
              open ? OPEN_COLOR : hovering() ? HOVER_COLOR : BASE_COLOR, BORDER_COLOR);
            RenderUtils.drawString(this.name, getX() + indent, getY(), new float[]{-5000, maxY + edgeRadius},
              new float[]{5000, category.y}, 0.1F, Color.WHITE);
        }
    }

    @Override
    public void scroll(double amount) {
        super.scroll(amount);
    }

    boolean justOpened;

    @Override
    public void click(int button) {
        super.click(button);
        if (this.justOpened) this.justOpened = false;
        if (hovering()) {
            if (button == 0 || button == 1) {
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
    public void charKey(char c) {
        super.charKey(c);
    }

    @Override
    public void key(int key) {
        super.key(key);
    }

    @Override
    public void open() {
        super.open();
    }

    @Override
    public void close() {
        super.close();
        for (ValPart part : parts) {
            part.close();
        }
    }
}
