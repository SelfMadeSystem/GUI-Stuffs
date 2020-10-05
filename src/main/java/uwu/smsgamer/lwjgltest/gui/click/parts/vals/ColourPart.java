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
import uwu.smsgamer.lwjgltest.utils.Colour.*;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawRect;

public class ColourPart extends ValPart {
    public ColourPart(CategoryPart category, ValStuff valStuff, ModulePart module, int indent) {
        super(category, valStuff, module, indent);
        hsv = Colour.rgb2hsv(new RGB((Color) valStuff.value));
    }

    //-1 none 0 big thing 1 hue thing
    int select = -1;
    HSV hsv;

    @Override
    public void render(float x, float y, float maxY) {
        super.render(x, y, maxY);
        RGB rgb = Colour.hsv2rgb(hsv);
        if (select == 0) {
            hsv.s = getRangeX(x - getSize()[0] / 2F, x + getSize()[0] / 2F);
            hsv.v = getRangeY(getY() - getSize()[0] - getSize()[1]/2F, getY() - getSize()[1]/2F);
            valStuff.value = (rgb = Colour.hsv2rgb(hsv)).toColor();
        } else if (select == 1) {
            hsv.h = getRangeX(x - getSize()[0] / 2F, x + getSize()[0] / 2F) * 360;
            valStuff.value = (rgb = Colour.hsv2rgb(hsv)).toColor();
        }
        Color pointer = (0.2126 * rgb.r + 0.7152 * rgb.g + 0.0722 * rgb.b < 0.5) //hsv.v < 0.5
          ? Color.WHITE : Color.BLACK;
        if (open) {
            float owo = getSize()[0] / 101F;
            for (int cx = 0; cx < 100; cx++) {
                {
                    Colour.HSV hhh = new Colour.HSV(cx * 3.6, hsv.s, hsv.v);
                    Color c = Colour.hsv2rgb(hhh).toColor();
                    c = MathUtils.approxEquals(hhh.h, hsv.h, 2) ? pointer : c;
                    drawRect(getX() - getSize()[0] / 2F + (cx * owo),
                      getY() - getSize()[0] - getSize()[1] - getSize()[1]/2F,
                      getX() - getSize()[0] / 2F + owo * 2 + (cx * owo),
                      getY() - getSize()[0] + getSize()[1] / 2F - getSize()[1]/2F, c);
                }
                for (int cy = 0; cy < 100; cy++) {
                    Colour.HSV hhh = new Colour.HSV(hsv.h, cx / 100f, cy / 100f);
                    Color c = Colour.hsv2rgb(hhh).toColor();
                    c = MathUtils.approxEquals(hhh.s, hsv.s, 0.01) &&
                      MathUtils.approxEquals(hhh.v, hsv.v, 0.01) ?
                      pointer : c;
                    drawRect(getX() - getSize()[0] / 2F + (cx * owo),
                      getY() - getSize()[0] + (cy * owo) - owo - getSize()[1]/2F,
                      getX() - getSize()[0] / 2F + owo * 2 + (cx * owo),
                      getY() - getSize()[0] + owo * 2 + (cy * owo) - owo - getSize()[1]/2F, c);
                }
            }
        }

        if (getY() + getSize()[1] / 2F > maxY &&
          getY() - getSize()[1] / 2F < category.y + category.getSize()[1] / 2F) {
            RenderUtils.drawBorderedRect(getX() - getSize()[0] / 2F + indent * 2,
              Math.min(category.y, Math.max(maxY, getY() - getSize()[1] / 2F)),
              getX() + getSize()[0] / 2F, Math.min(category.y, getY() + getSize()[1] / 2F), edgeRadius,
              (Color) valStuff.value, BORDER_COLOR);
            RenderUtils.drawString(this.name, getX() + indent, getY() + getSize()[1] / 6F, new float[]{-5000, maxY + edgeRadius},
              new float[]{5000, category.y}, 0.07F, Color.WHITE);
            RenderUtils.drawString(String.format("#%02x%02x%02x", (int) (rgb.r * 255), (int) (rgb.g * 255), (int) (rgb.b * 255)), getX() + indent, getY() - getSize()[1] / 4F, new float[]{-5000, maxY + edgeRadius},
              new float[]{5000, category.y}, 0.04F, Color.WHITE);
        }
        category.yAdd += open ? getSize()[0] + getSize()[1] : 0;
    }

    @Override
    public void click(int button) {
        super.click(button);
        if (hovering()) {
            open = !open;
        } else if (open) {
            if (category.hoverRaw(getX() - getSize()[0] / 2F, getX() + getSize()[0] / 2F,
              getY() - getSize()[0] - getSize()[1]/2F, getY() - getSize()[1]/2F)) {
                select = 0;
            } else if (category.hoverRaw(getX() - getSize()[0] / 2F, getX() + getSize()[0] / 2F,
              getY() - getSize()[0] - getSize()[1] * 1.5F, getY() - getSize()[0] - getSize()[1]/2F)) {
                select = 1;
            }
        }
    }

    @Override
    public void unclick(int button) {
        super.unclick(button);
        select = -1;
    }
}
