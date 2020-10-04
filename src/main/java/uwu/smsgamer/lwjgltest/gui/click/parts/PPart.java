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

package uwu.smsgamer.lwjgltest.gui.click.parts;

import uwu.smsgamer.lwjgltest.gui.click.ValPart;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

public class PPart extends ValPart {
    public PPart(CategoryPart category, ValStuff valStuff, ModulePart module, int indent) {
        super(category, valStuff, module, indent);
    }

    @Override
    public void render(float x, float y, float maxY) {
        super.render(x, y, maxY);

        RenderUtils.drawBorderedRect(getX() - getSize()[0] / 2F, getY() - getSize()[1] / 2F,
          getX() + getSize()[0] / 2F, getY() + getSize()[1] / 2F, 2,
          category.hoverRaw(getX() - getSize()[0] / 2F, getX() + getSize()[0] / 2F,
            getY() - getSize()[1] / 2F, getY() + getSize()[1] / 2F) ? Color.BLUE : Color.GREEN, Color.YELLOW);
        RenderUtils.drawString(this.name, getX(), getY(), 0.1F, Color.BLACK);
    }
}
