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

public class TogglePart extends ValPart {
    public TogglePart(CategoryPart category, ValStuff valStuff, ModulePart module, int indent) {
        super(category, valStuff, module, indent);
    }

    @Override
    public void render(float x, float y, float maxY) {
        super.render(x, y, maxY);
        if (getY() + getSize()[1] / 2F > maxY &&
          getY() - getSize()[1] / 2F < category.y + category.getSize()[1] / 2F) {
            RenderUtils.drawBorderedRect(getX() - getSize()[0] / 2F + indent * 2,
              Math.min(category.y, Math.max(maxY, getY() - getSize()[1] / 2F)),
              getX() + getSize()[0] / 2F, Math.min(category.y, getY() + getSize()[1] / 2F), edgeRadius,
              clicking ? (((boolean) valStuff.value) ? ON_COLOR_CLICK : OFF_COLOR_CLICK) :
                (notOverridden() && hovering() ? (((boolean) valStuff.value) ? ON_COLOR_HOVER : OFF_COLOR_HOVER) :
                  (((boolean) valStuff.value) ? ON_COLOR : OFF_COLOR)), BORDER_COLOR);
            RenderUtils.drawString(this.name, getX() + indent, getY(), new float[]{-5000, maxY + edgeRadius},
              new float[]{5000, category.y}, 0.1F, Color.WHITE);
        }
    }

    boolean clicking;

    @Override
    public void click(int button) {
        super.click(button);
        if (notOverridden() && hovering()) clicking = true;
    }

    @Override
    public void unclick(int button) {
        super.unclick(button);
        if (notOverridden() && hovering() && clicking) {
            valStuff.value = !(boolean) valStuff.value;
        }
        clicking = false;
    }
}
