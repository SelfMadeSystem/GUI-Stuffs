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

public class ChoicePart extends EditPart {
    public String[] options;
    public EditPart[] parts;
    public Module module;
    public boolean open;

    public ChoicePart(ValStuff valStuff, Module module, Category category, int inside) {
        super(valStuff, category, module);
        this.module = module;
        this.options = valStuff.choices;
        this.inside = inside;
        setup();
    }

    private void setup() {
        this.parts = new EditPart[this.options.length];
        String[] strings = this.options;
        for (int i = 0; i < strings.length; i++) {
            String choice = strings[i];
            this.parts[i] = new Choice(this.valStuff, choice, this.category, this.module, this.inside + 1);
        }
    }

    @Override
    public void render(float x, float y, float maxY) {
        super.render(x, y, maxY);
        if (!(y > category.y - Category.mainSize[1])) {
            RenderUtils.drawBorderedRect(x + inside * 2, Math.max(maxY, y),
              x + Category.mainSize[0], y + Category.mainSize[1],
              1, hover() ? Color.DARK_GRAY : Color.GRAY, Color.RED);
            RenderUtils.drawString(valStuff.name, x + Category.mainSize[0] / 2f + inside,
              y + Category.mainSize[1] / 1.5F,
              new float[]{-250, Math.max(maxY, y)}, new float[]{250, y + Category.mainSize[1]},
              0.07f, Color.WHITE);
            RenderUtils.drawString(String.valueOf(valStuff.value), x + Category.mainSize[0] / 2f + inside,
              y + Category.mainSize[1] / 4F,
              new float[]{-250, Math.max(maxY, y)}, new float[]{250, y + Category.mainSize[1]},
              0.04f, Color.WHITE);
        }
        if (open) {
            float kk = y + category.yAdd;
            for (EditPart part : parts) {
                category.yAdd += Category.mainSize[1];
                float owoY = kk - category.yAdd;
                if (owoY <= maxY - Category.mainSize[1]) {
                    break;
                }
                part.render(x, owoY, maxY);
            }
            //category.yAdd += Category.mainSize[1] * parts.length;
        }
    }

    @Override
    public void close() {
        super.close();
        this.open = false;
        for (EditPart part : parts)
            part.close();
    }

    @Override
    public void scroll(double amount) {
        super.scroll(amount);
        if (open) {
            for (EditPart editPart : parts) {
                editPart.scroll(amount);
            }
        }
    }

    @Override
    public void click(int button) {
        super.click(button);
        if (hover()) {
            if (open) close();
            else {
                open = true;
                this.category.moreTabs += parts.length;
                return;
            }
        }
        if (open) {
            for (EditPart editPart : parts) {
                editPart.click(button);
            }
        }
    }

    @Override
    public void unclick(int button) {
        super.unclick(button);
        if (open) {
            for (EditPart editPart : parts) {
                editPart.unclick(button);
            }
        }
    }

    static class Choice extends EditPart {
        String choice;

        public Choice(ValStuff valStuff, String choice, Category category, Module module, int inside) {
            super(valStuff, category, module, inside);
            this.choice = choice;
        }

        @Override
        public void render(float x, float y, float maxY) {
            super.render(x, y, maxY);
            if (y > category.y - Category.mainSize[1]) return;
            drawMainBox(x, y, this.valStuff.value.equals(choice) ? MAIN_COLOR_SELECT : hover() ? MAIN_COLOR_HOVER : MAIN_COLOR);
            RenderUtils.drawString(choice, x + Category.mainSize[0] / 2f + inside,
              y + Category.mainSize[1] / 2F,
              new float[]{-250, Math.max(maxY, y)}, new float[]{250, y + Category.mainSize[1]},
              0.1f, Color.WHITE);
        }

        @Override
        public void click(int button) {
            super.click(button);
            if (button == 0 && hover()) {
                this.valStuff.value = choice;
            }
        }
    }
}
