/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.oldclick.parts.buttons;

import uwu.smsgamer.lwjgltest.gui.oldclick.parts.*;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.*;

import java.awt.*;

@Deprecated
public class SliderPart extends EditPart {

    public SliderPart(ValStuff valStuff, Category category, Module module) {
        super(valStuff, category, module);
    }

    boolean clicked;

    //sloppy code all of this lol
    @Override
    public void render(float x, float y, float maxY) {
        super.render(x, y, maxY);
        if (y > category.y - Category.mainSize[1]) return;
        drawMainBox(x, y);
        double step = this.valStuff.step;
        double min = this.valStuff.min;
        double max = this.valStuff.max;
        if (clicked) {
            double value = min +
              getRangeX(x + inside * 2 + 1, x + Category.mainSize[0] - 1) *
                (max - min) + step / 2;
            valStuff.value = MathUtils.roundInc(value, step);
            if (((double) valStuff.value) > max) valStuff.value = max;
            else if (((double) valStuff.value) < min) valStuff.value = min;
            RenderUtils.drawRect(x + inside * 2 + 1, y + 1,
              (float) (x + inside * 2 + 1 + (Category.mainSize[0] - 2 - inside * 2) *
                (Math.min(max - min, (double) valStuff.value - min + valStuff.step) / (max - min))),
              (y + Category.mainSize[1]) - 1, SLIDER_HINT_AFTER);
        }
        RenderUtils.drawRect(x + inside * 2 + 1, Math.max(maxY, y) + 1,
          (float) (x + inside * 2 + 1 + (Category.mainSize[0] - 2 - inside * 2) *
            (((double) valStuff.value - min) / (max - min))),
          y + Category.mainSize[1] - 1, hover() && !clicked ? SLIDER_COLOR_HOVER : SLIDER_COLOR);
        if (clicked) {
            RenderUtils.drawRect(x + inside * 2 + 1,y + 1,
              (float) (x + inside * 2 + 1 + (Category.mainSize[0] - 2 - inside * 2) *
                (Math.max(0, (double) valStuff.value - min - valStuff.step) / (max - min))),
              (y + Category.mainSize[1]) - 1, SLIDER_HINT_BEFORE);
        }
        RenderUtils.drawString(valStuff.name, x + Category.mainSize[0] / 2f + inside,
          y + Category.mainSize[1] / 1.5F,
          new float[]{-250, Math.max(maxY, y)}, new float[]{250, y + Category.mainSize[1]},
          0.07f, Color.WHITE);
        RenderUtils.drawString(String.valueOf(valStuff.value), x + Category.mainSize[0] / 2f + inside,
          y + Category.mainSize[1] / 4F,
          new float[]{-250, Math.max(maxY, y)}, new float[]{250, y + Category.mainSize[1]},
          0.04f, Color.WHITE);
    }

    @Override
    public void click(int button) {
        super.click(button);
        if (hover())
            clicked = true;
    }

    @Override
    public void unclick(int button) {
        super.unclick(button);
        if (clicked) {
            clicked = false;
        }
    }
}
