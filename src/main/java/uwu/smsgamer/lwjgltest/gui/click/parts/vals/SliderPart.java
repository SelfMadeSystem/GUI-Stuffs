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
import uwu.smsgamer.lwjgltest.utils.*;

import java.awt.*;

public class SliderPart extends ValPart {
    public SliderPart(CategoryPart category, ValStuff valStuff, ModulePart module, int indent) {
        super(category, valStuff, module, indent);
    }

    @Override
    public void render(float x, float y, float maxY) {
        super.render(x, y, maxY);
        double min = valStuff.min;
        double max = valStuff.max;
        double step = valStuff.step;
        double value = ((double) valStuff.value - min) / (max - min);

        if (clicking) {
            value = getRangeX(x - getSize()[0] / 2F + edgeRadius * 2,
              x + getSize()[0] / 2F - edgeRadius * 2) * (max - min) + min + step/2;
            valStuff.value = MathUtils.roundInc(value, step);
            value = ((double) valStuff.value - min) / (max - min);// FIXME: 2020-10-04 not setting properly
        }

        if (getY() + getSize()[1] / 2F > maxY &&
          getY() - getSize()[1] / 2F < category.y + category.getSize()[1] / 2F) {
            RenderUtils.drawBorderedRect(getX() - getSize()[0] / 2F + indent * 2,
              Math.min(category.y, Math.max(maxY, getY() - getSize()[1] / 2F)),
              getX() + getSize()[0] / 2F, Math.min(category.y, getY() + getSize()[1] / 2F), edgeRadius,
              notOverridden() && hovering() && !clicking ? MAIN_COLOR_HOVER : MAIN_COLOR, BORDER_COLOR);
            if (clicking) {
                RenderUtils.drawRect(getX() - getSize()[0] / 2F + indent * 2 + edgeRadius,
                  Math.min(category.y, Math.max(maxY, getY() - getSize()[1] / 2F)) + edgeRadius,
                  (float) (getX() - (getSize()[0] / 2F - indent * 2) + (getSize()[0] - indent - edgeRadius * 2) *
                    Math.min(1, value + step / (max - min) * 0.5F))
                    + edgeRadius, Math.min(category.y, getY() + getSize()[1] / 2F) - edgeRadius, SLIDER_HINT_AFTER);
            }
            RenderUtils.drawRect(getX() - getSize()[0] / 2F + indent * 2 + edgeRadius,
              Math.min(category.y, Math.max(maxY, getY() - getSize()[1] / 2F)) + edgeRadius,
              (float) (getX() - (getSize()[0] / 2F - indent * 2) + (getSize()[0] - indent - edgeRadius * 2) * value)
                + edgeRadius, Math.min(category.y, getY() + getSize()[1] / 2F) - edgeRadius,
              notOverridden() && hovering() && !clicking ? SLIDER_COLOR_HOVER : SLIDER_COLOR);
            if (clicking) {
                RenderUtils.drawRect(getX() - getSize()[0] / 2F + indent * 2 + edgeRadius,
                  Math.min(category.y, Math.max(maxY, getY() - getSize()[1] / 2F)) + edgeRadius,
                  (float) (getX() - (getSize()[0] / 2F - indent * 2) + (getSize()[0] - indent - edgeRadius * 2) *
                    Math.max(0, value - step / (max - min) * 0.5F))
                    + edgeRadius, Math.min(category.y, getY() + getSize()[1] / 2F) - edgeRadius, SLIDER_HINT_BEFORE);
            }
            RenderUtils.drawString(this.name, getX() + indent, getY() + getSize()[1]/6F, new float[]{-5000, maxY + edgeRadius},
              new float[]{5000, category.y}, 0.07F, Color.WHITE);
            RenderUtils.drawString(String.valueOf(this.valStuff.value), getX() + indent, getY() - getSize()[1]/4F, new float[]{-5000, maxY + edgeRadius},
              new float[]{5000, category.y}, 0.04F, Color.WHITE);
        }
    }

    public boolean clicking;

    @Override
    public void click(int button) {
        super.click(button);
        clicking = notOverridden() && hovering();
    }

    @Override
    public void unclick(int button) {
        super.unclick(button);
        clicking = false;
    }
}
