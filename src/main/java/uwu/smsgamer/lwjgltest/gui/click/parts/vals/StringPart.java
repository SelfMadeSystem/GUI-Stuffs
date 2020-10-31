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

import org.lwjgl.glfw.GLFW;
import uwu.smsgamer.lwjgltest.gui.click.*;
import uwu.smsgamer.lwjgltest.gui.click.parts.*;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.*;

import java.awt.*;

public class StringPart extends ValPart {
    public StringPart(CategoryPart category, ValStuff valStuff, ModulePart module, int indent) {
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
              editing ? MAIN_COLOR_SELECT : notOverridden() && hovering() ? MAIN_COLOR_HOVER : MAIN_COLOR, BORDER_COLOR);
            RenderUtils.drawString(this.name, getX() - (mainSize[0]) / 2F + edgeRadius + 2 + indent * 2, getY() + getSize()[1] / 6F, new float[]{-5000, maxY + edgeRadius},
              new float[]{5000, category.y}, 0.07F, -1, Color.WHITE);
            RenderUtils.drawString(String.valueOf(this.valStuff.value), getX() - (mainSize[0]) / 2F + edgeRadius + 2 + indent * 2, getY() - getSize()[1] / 4F, new float[]{-5000, maxY + edgeRadius},
              new float[]{5000, category.y}, 0.04F, -1, Color.WHITE);
        }
    }

    boolean editing;

    @Override
    public void click(int button) {
        super.click(button);
        if (notOverridden() && hovering()) {
            if (editing) {
                ClickGUIManager.getInstance().inputOverride = null;
            } else {
                ClickGUIManager.getInstance().inputOverride = this;
            }
            editing = !editing;
        }
    }

    @Override
    public void charKey(char c) {
        if (editing) {
            String text = (String) valStuff.value;
            if (AllowedChars.isAllowedCharacter(c)) {
                text += c;
            }
            valStuff.value = text;
        }
    }

    @Override
    public void key(int k) {
        if (editing) {
            if (k == GLFW.GLFW_KEY_ENTER) {
                editing = false;
                ClickGUIManager.getInstance().inputOverride = null;
                return;
            }
            String text = (String) valStuff.value;
            if (k == GLFW.GLFW_KEY_BACKSPACE) {
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                }
            }
            valStuff.value = text;
        }
    }
}
