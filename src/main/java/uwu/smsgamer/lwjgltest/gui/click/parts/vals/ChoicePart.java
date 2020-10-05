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

public class ChoicePart extends ValPart {
    public static Color BASE_COLOR = new Color(100, 100, 100);
    public static Color HOVER_COLOR = new Color(80, 80, 80);
    public static Color OPEN_COLOR = new Color(70, 70, 70);
    public ValPart[] parts;

    public ChoicePart(CategoryPart category, ValStuff valStuff, ModulePart module, int indent) {
        super(category, valStuff, module, indent);
        String[] choices = valStuff.choices;
        this.parts = new ValPart[choices.length];
        for (int i = 0; i < choices.length; i++) {
            String s = choices[i]; //not visual studio lol
            parts[i] = new Choice(this.category, this.valStuff, this.module, s, indent + 2);
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
              open ? OPEN_COLOR : notOverridden() && hovering() ? HOVER_COLOR : BASE_COLOR, BORDER_COLOR);
            RenderUtils.drawString(this.name, getX() + indent, getY() + getSize()[1]/6F, new float[]{-5000, maxY + edgeRadius},
              new float[]{5000, category.y}, 0.07F, Color.WHITE);
            RenderUtils.drawString(String.valueOf(this.valStuff.value), getX() + indent, getY() - getSize()[1]/4F, new float[]{-5000, maxY + edgeRadius},
              new float[]{5000, category.y}, 0.04F, Color.WHITE);
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
        if (notOverridden() && hovering()) {
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

    private static class Choice extends ValPart {
        public Choice(CategoryPart category, ValStuff valStuff, ModulePart module, String name, int indent) {
            super(category, valStuff, module, indent);
            this.name = name;
        }

        @Override
        public void render(float x, float y, float maxY) {
            super.render(x, y, maxY);
            if (getY() + getSize()[1] / 2F > maxY &&
              getY() - getSize()[1] / 2F < category.y + category.getSize()[1] / 2F) {
                RenderUtils.drawBorderedRect(getX() - getSize()[0] / 2F + indent * 2,
                  Math.min(category.y, Math.max(maxY, getY() - getSize()[1] / 2F)),
                  getX() + getSize()[0] / 2F - 4, Math.min(category.y, getY() + getSize()[1] / 2F), edgeRadius,
                  this.valStuff.value.equals(this.name) ? MAIN_COLOR_SELECT :
                    notOverridden() && hovering() ? MAIN_COLOR_HOVER : MAIN_COLOR, BORDER_COLOR);
                RenderUtils.drawString(this.name, getX() + indent, getY(), new float[]{-5000, maxY + edgeRadius},
                  new float[]{5000, category.y}, 0.1F, Color.WHITE);
            }
        }

        @Override
        public void click(int button) {
            super.click(button);
            this.valStuff.value = hovering() ? this.name : this.valStuff.value;
        }
    }
}
