package uwu.smsgamer.lwjgltest.gui.click.parts.buttons;

import org.lwjgl.glfw.GLFW;
import uwu.smsgamer.lwjgltest.gui.click.ClickGUIManager;
import uwu.smsgamer.lwjgltest.gui.click.parts.*;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.*;

import java.awt.*;

public class StringPart extends EditPart {
    public StringPart(ValStuff valStuff, Category category, Module module) {
        super(valStuff, category, module);
    }

    @Override
    public void render(float x, float y, float maxY) {
        super.render(x, y, maxY);
        if (y > category.y - Category.mainSize[1]) return;
        drawMainBox(x, y, editing ? MAIN_COLOR_SELECT : hover() ? MAIN_COLOR_HOVER : MAIN_COLOR);

        RenderUtils.drawString(valStuff.name, x + Category.mainSize[0] / 2f + inside,
          y + Category.mainSize[1] / 1.5F,
          new float[]{-250, Math.max(maxY, y)}, new float[]{250, y + Category.mainSize[1]},
          0.07f, Color.WHITE);
        RenderUtils.drawString(String.valueOf(valStuff.value), x + Category.mainSize[0] / 2f + inside,
          y + Category.mainSize[1] / 4F,
          new float[]{-250, Math.max(maxY, y)}, new float[]{250, y + Category.mainSize[1]},
          0.04f, Color.WHITE);
    }

    boolean editing;

    @Override
    public void click(int button) {
        super.click(button);
        if (hover()) {
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
