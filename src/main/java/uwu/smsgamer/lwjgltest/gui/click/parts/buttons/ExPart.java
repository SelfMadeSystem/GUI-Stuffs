/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.click.parts.buttons;

import uwu.smsgamer.lwjgltest.gui.click.parts.*;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

public class ExPart extends EditPart {
    public ExPart(ValStuff valStuff, Category category, Module module) {
        super(valStuff, category, module);
    }

    @Override
    public void render(float x, float y, float maxY) {
        super.render(x, y, maxY);
        if (y > category.y - Category.mainSize[1]) return;
        RenderUtils.drawBorderedRect(x + inside * 2, Math.max(maxY, y),
          x + Category.mainSize[0], y + Category.mainSize[1],
          1, Color.BLUE, Color.RED);
        RenderUtils.drawString(valStuff.name, x + Category.mainSize[0] / 2f + inside,
          y + Category.mainSize[1]/2F,
          new float[]{-250, Math.max(maxY, y)}, new float[]{250, y + Category.mainSize[1]},
          0.1f, Color.WHITE);
    }
}
