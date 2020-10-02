package uwu.smsgamer.lwjgltest.gui.block.blocks;

import org.lwjgl.glfw.GLFW;
import uwu.smsgamer.lwjgltest.input.MouseHelper;
import uwu.smsgamer.lwjgltest.stuff.*;
import uwu.smsgamer.lwjgltest.utils.*;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.*;

public class ModuleBlock extends SubBlock {
    int amount;
    ValStuff[] modules;
    String category;
    ValStuff valStuff;

    public ModuleBlock(Block parent, String category, String module) {
        super(parent, blockWidth, blockHeight, 0, 0, module);
        this.category = category;
        modules = Stuff.values.get(category).get(module);
        amount = modules.length;
    }

    public ModuleBlock(Block parent, ValStuff valStuff) {
        super(parent, blockWidth, blockHeight, 0, 0, valStuff.name);
        this.valStuff = valStuff;
        this.modules = this.valStuff.values;
        this.amount = this.modules.length;
    }

    int selected = -1;
    int hover = -1;

    @Override
    public void render() {
        drawRectUnDiv(-1, -1, 1, 1, new Color(0, 0, 0, 150));
        x = -(children() - 1) * 50;
        hover = -1;
        float minX = (x - (width / 2));
        float maxX = (x + (width / 2));
        drawRect(minX, (y - (height * (amount / 2f)) / 2) + edgeRound, maxX,
          (y + (height * (amount / 2f)) / 2), BORDERED_COLOR);
        for (int i = 0; i < amount; i++) {
            float minY = ((y + (height / 2 * (i - amount / 2f))));
            float maxY = (y + (height / 2 * (i - amount / 2f)) + (height / 2));
            if (isHoveringRaw(minX, maxX, minY+edgeRadius/3, maxY-edgeRadius/3)) hover = i;
            Color c = selected == i && !this.modules[i].type.equals(ValStuff.Type.NUMBER) ? SELECTED_COLOR :
              hover == i && child == null && selected == -1 ? HOVER_COLOR : MAIN_COLOR;
            switch (this.modules[i].type) {
                case BOOLEAN:
                    if (selected == i) {
                        c = ((boolean) this.modules[i].value) ? ON_COLOR_CLICK : OFF_COLOR_CLICK;
                    } else {
                        if (hover == i && selected == -1) {
                            c = ((boolean) this.modules[i].value) ? ON_COLOR_HOVER : OFF_COLOR_HOVER;
                        } else {
                            c = ((boolean) this.modules[i].value) ? ON_COLOR : OFF_COLOR;
                        }
                    }
                    break;
                case COLOUR:
                    c = (Color) this.modules[i].value;
            }
            drawRoundBorderedRect(minX, minY, maxX, maxY, edgeRound, edgeRadius, c, BORDERED_COLOR);
            Color stC = Color.BLACK;
            switch (this.modules[i].type) {

                case NUMBER: {
                    double step = this.modules[i].step;
                    double min = this.modules[i].min;
                    double max = this.modules[i].max;
                    if (selected == i) {
                        double value = min +
                          ((MouseHelper.posX - minX - 250 - edgeRadius) / (maxX - minX - edgeRadius * 2)) *
                            (max - min) + step / 2;
                        modules[i].value = MathUtils.roundInc(value, step);
                        if (((double) modules[i].value) > max) modules[i].value = max;
                        else if (((double) modules[i].value) < min) modules[i].value = min;
                        float xX = (float) (minX + edgeRadius + ((maxX - minX - edgeRadius * 2) *
                          (((double) this.modules[i].value - min + (step / 2)) / (max - min))));
                        if (xX > maxX - edgeRadius) xX = maxX - edgeRadius;
                        drawRoundRect(minX + edgeRadius, minY + edgeRadius, xX,
                          maxY - edgeRadius, edgeRound, SLIDER_HINT_AFTER);
                    }
                    drawRoundRect(minX + edgeRadius, minY + edgeRadius, (float) (minX + edgeRadius + ((maxX - minX - edgeRadius * 2) *
                        (((double) this.modules[i].value - min) / (max - min)))),
                      maxY - edgeRadius, edgeRound, this.hover == i && this.selected == -1 ? SLIDER_COLOR_HOVER : SLIDER_COLOR);
                    if (this.selected == i) {
                        float xX = (float) (minX + edgeRadius + ((maxX - minX - edgeRadius * 2) *
                          (((double) this.modules[i].value - min - (step / 2)) / (max - min))));
                        if (xX < minX + edgeRadius) xX = minX + edgeRadius;
                        drawRoundRect(minX + edgeRadius, minY + edgeRadius, xX,
                          maxY - edgeRadius, edgeRound, SLIDER_HINT_BEFORE);
                    }
                    drawString(String.valueOf(modules[i].value), x, minY + (maxY - minY) / 2 - 14, 0.04f, Color.BLACK);
                    break;
                }
                case STRING: {
                    drawString(String.valueOf(modules[i].value), x, minY + (maxY - minY) / 2 - 14, 0.035f, Color.BLACK);
                    break;
                }
                case COLOUR: {
                    Color center = (Color) modules[i].value;
                    stC = (0.2126 * center.getRed() + 0.7152 * center.getGreen() + 0.0722 * center.getBlue() > 128) ? Color.BLACK : Color.WHITE;
                    break;
                }
            }
            drawString(modules[i].name, x, minY + (maxY - minY) / 2 + 12, 0.06f, stC);
        }
        drawTop(height * (amount / 2f) / 2);
    }

    @Override
    public void click() {
        super.click();
        if (this.parent.child == this && this.child == null && this.hover != -1) {
            switch (this.modules[hover].type) {
                case VALUES: {
                    if (this.selected == -1) {
                        this.child = new ModuleBlock(this, this.modules[hover]);
                        this.selected = hover;
                    }
                    break;
                }
                case CHOICE: {
                    if (this.selected == -1) {
                        this.child = new ChoiceBlock(this, this.modules[hover]);
                        this.selected = hover;
                    }
                    break;
                }
                case COLOUR: {
                    if (this.selected == -1) {
                        this.child = new ColourBlock(this, this.modules[hover]);
                        this.selected = hover;
                    }
                    break;
                }
                case STRING:
                    if (this.selected == this.hover) {
                        this.selected = -1;
                        break;
                    }
                case NUMBER:
                case BOOLEAN: {
                    if (this.selected == -1) this.selected = hover;
                }
            }
        }
    }

    @Override
    public void unclick() {
        if (this.parent.child == this && this.child == null) {
            if (this.hover != -1) {
                switch (this.modules[hover].type) {
                    case BOOLEAN: {
                        if (this.selected == this.hover)
                            this.modules[hover].value = !((boolean) this.modules[hover].value);
                    }
                    case NUMBER: {
                        if (this.selected == this.hover) this.selected = -1;
                    }
                }
            }
            if (this.selected != -1) {
                switch (this.modules[selected].type) {
                    case BOOLEAN:
                    case NUMBER:
                        this.selected = -1;
                }
            }
        }
    }

    @Override
    public void close() {
        this.child = null;
        this.selected = -1;
    }

    @Override
    public void charKey(char c) {
        if (this.selected != -1 && this.modules[this.selected].type.equals(ValStuff.Type.STRING)) {
            ValStuff val = this.modules[this.selected];
            String text = (String) val.value;
            if (AllowedChars.isAllowedCharacter(c)) {
                text += c;
            }
            val.value = text;
        }
    }

    @Override
    public void key(int k) {
        if (this.selected != -1 && this.modules[this.selected].type.equals(ValStuff.Type.STRING)) {
            if (k == GLFW.GLFW_KEY_ENTER) {
                this.selected = -1;
                return;
            }
            ValStuff val = this.modules[this.selected];
            String text = (String) val.value;
            if (k == GLFW.GLFW_KEY_BACKSPACE) {
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                }
            }
            val.value = text;
        }
    }
}
