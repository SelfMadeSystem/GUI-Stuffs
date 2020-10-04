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
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

@Deprecated
public class ValuesPart extends EditPart {
    public ValStuff[] valStuffs;
    public EditPart[] parts;
    public Module module;
    public boolean open;

    public ValuesPart(ValStuff valStuff, Module module, Category category, int inside) {
        super(valStuff, category, module);
        this.module = module;
        this.valStuffs = valStuff.values;
        this.inside = inside;
        setup();
    }

    private void setup() {
        this.parts = new EditPart[this.valStuffs.length];
        ValStuff[] stuff = this.valStuffs;
        // probably wanna like not do this here if u wanna have ur settings to be like removed or added or stuff lol
        for (int i = 0; i < stuff.length; i++) {
            ValStuff valStuff = stuff[i];
            switch (valStuff.type) {
                case VALUES:
                    parts[i] = new ValuesPart(valStuff, this.module, this.category, inside + 2);
                    break;
                case NUMBER:
                    parts[i] = new SliderPart(valStuff, this.category, this.module);
                    parts[i].inside = inside + 2;
                    break;
                case BOOLEAN:
                    parts[i] = new TogglePart(valStuff, this.category, this.module);
                    parts[i].inside = inside + 2;
                    break;
                case STRING:
                    parts[i] = new StringPart(valStuff, this.category, this.module);
                    parts[i].inside = inside + 2;
                    break;
                case CHOICE:
                    parts[i] = new ChoicePart(valStuff, this.module, this.category, this.inside);
                    parts[i].inside = inside + 2;
                    break;
                default:
                    parts[i] = new ExPart(valStuff, this.category, this.module);
                    parts[i].inside = inside + 2;
            }
        }
    }

    @Override
    public void render(float x, float y, float maxY) {
        super.render(x, y, maxY);
        if (!(y > category.y - Category.mainSize[1])) {
            RenderUtils.drawBorderedRect(x + inside * 2, Math.max(maxY, y),
              x + Category.mainSize[0], y + Category.mainSize[1],
              1, hover() ? Color.BLUE : Color.CYAN, Color.RED);
            RenderUtils.drawString(valStuff.name, x + Category.mainSize[0] / 2f,
              y + Category.mainSize[1] / 2F,
              new float[]{-250, Math.max(maxY, y)}, new float[]{250, y + Category.mainSize[1]},
              0.1f, Color.WHITE);
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
}
