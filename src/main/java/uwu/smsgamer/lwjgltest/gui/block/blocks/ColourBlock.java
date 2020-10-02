package uwu.smsgamer.lwjgltest.gui.block.blocks;

import uwu.smsgamer.lwjgltest.input.MouseHelper;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.*;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawRect;
import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawRectUnDiv;

public class ColourBlock extends SubBlock {
    ValStuff valStuff;

    public ColourBlock(Block parent, ValStuff valStuff) {
        super(parent, blockWidth, blockHeight, 0, 0, valStuff.name);
        this.valStuff = valStuff;
        this.rgb = new Colour.RGB((Color) valStuff.value);
        this.hsv = Colour.rgb2hsv(this.rgb);
    }

    // -1 none
    // 0 hue
    // 1 sat
    // 2 val
    // 3 owo amazing thingy
    // 4 red
    // 5 green
    // 6 blue
    int hover;
    int selected = -1;
    Colour.HSV hsv;
    Colour.RGB rgb;

    @Override
    public void render() {
        drawRectUnDiv(-1, -1, 1, 1, new Color(0, 0, 0, 150));
        x = -(children() - 1) * 50;
        float minX = (x - (width / 2));
        float maxX = (x + (width / 2));
        drawRect(minX, (y - height) + edgeRound, maxX,
          (y + height), Color.BLACK);
        drawTop(height + 10);
        hover = -1;
        if (isHovering(-120, -100, -100, 100)) {
            hover = 0;
        } else if (isHovering(-140, -120, -100, 100)) {
            hover = 1;
        } else if (isHovering(-160, -140, -100, 100)) {
            hover = 2;
        } else if (isHovering(-100, 100, -100, 100)) {
            hover = 3;
        } else if (isHovering(100, 120, -100, 100)) {
            hover = 4;
        } else if (isHovering(120, 140, -100, 100)) {
            hover = 5;
        } else if (isHovering(140, 160, -100, 100)) {
            hover = 6;
        }
        switch (selected) {
            case 0: {
                this.hsv.h = getRangeY(-100, 100) * 360/*Math.max(0, Math.min(360, (mouseY - 55 + this.y) * -1.8))*/;
                hsv2rgb();
                break;
            }
            case 1: {
                this.hsv.s = 1 - getRangeY(-100, 100);
                hsv2rgb();
                break;
            }
            case 2: {
                this.hsv.v = 1 - getRangeY(-100, 100);
                hsv2rgb();
                break;
            }
            case 3: {
                this.hsv.s = getRangeX(-100, 100);
                this.hsv.v = 1 - getRangeY(-100, 100);
                hsv2rgb();
                break;
            }
            case 4: {
                this.rgb.r = 1 - getRangeY(-100, 100);
                rgb2hsv();
                break;
            }
            case 5: {
                this.rgb.g = 1 - getRangeY(-100, 100);
                rgb2hsv();
                break;
            }
            case 6: {
                this.rgb.b = 1 - getRangeY(-100, 100);
                rgb2hsv();
                break;
            }
        }
        drawRect(this.x - 170, this.y - 110, this.x + 170,
          this.y + 110, (Color) (valStuff.value = this.rgb.toColor()));
        Color pointer = (0.2126 * rgb.r + 0.7152 * rgb.g + 0.0722 * rgb.b < 0.5) //hsv.v < 0.5
          ? Color.WHITE : Color.BLACK;
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                Colour.HSV hhh = new Colour.HSV(hsv.h, x / 100f, y / 100f);
                Color c = Colour.hsv2rgb(hhh).toColor();
                c = MathUtils.approxEquals(hhh.s, hsv.s, 0.01) &&
                  MathUtils.approxEquals(hhh.v, hsv.v, 0.01) ?
                  pointer : c;
                drawRect(this.x - 100 + (x * 2), this.y - 100 + (y * 2), this.x - 98 + (x * 2),
                  this.y - 98 + (y * 2), c);
            }
            //HSV//
            //hue
            Color c;
            for (int x1 = 0; x1 <= 1; x1++) {
                if (x == 99 && x1 == 1) break;
                c = x == 99 && hsv.h < 2 || x == 0 && hsv.h > 358 ? pointer :
                  MathUtils.approxEquals(360 - ((x * 2 + x1) * 1.8), hsv.h, 1) ?
                    pointer :
                    Colour.hsv2rgb(new Colour.HSV(360 - ((x * 2 + x1) * 1.8), hsv.s, hsv.v)).toColor();
                drawRect(this.x - 100, this.y - 100 + (x * 2 + x1), this.x - 120,
                  this.y - 98 + (x * 2 + x1), c);
            }
            //sat
            for (int x1 = 0; x1 <= 1; x1++) {
                if (x == 99 && x1 == 1) break;
                double d = (x * 2 + x1);
                c = x == 99 && hsv.s > 0.99 ? pointer :
                  MathUtils.approxEquals(d / 200d, hsv.s, 0.005) ?
                    pointer :
                    Colour.hsv2rgb(new Colour.HSV(hsv.h, d / 200d, hsv.v)).toColor();
                drawRect(this.x - 120, (float) (this.y - 100 + d), this.x - 140,
                  (float) (this.y - 98 + d), c);
            }
            //val
            for (int x1 = 0; x1 <= 1; x1++) {
                if (x == 99 && x1 == 1) break;
                double d = (x * 2 + x1);
                c = x == 99 && hsv.v > 0.99 ? pointer :
                  MathUtils.approxEquals(d / 200d, hsv.v, 0.005) ?
                    pointer :
                    Colour.hsv2rgb(new Colour.HSV(hsv.h, hsv.s, d / 200d)).toColor();
                drawRect(this.x - 140, (float) (this.y - 100 + d), this.x - 160,
                  (float) (this.y - 98 + d), c);
            }
            //RGB//
            //r
            for (int x1 = 0; x1 <= 1; x1++) {
                if (x == 99 && x1 == 1) break;
                Colour.RGB rgb1 = new Colour.RGB((x * 2 + x1) / 200d, rgb.g, rgb.b);
                c = x == 99 && rgb.r > 0.99 ? pointer :
                  MathUtils.approxEquals(rgb1.r, rgb.r, 0.005) ?
                    pointer :
                    rgb1.toColor();
                float f = (x * 2 + x1);
                drawRect(this.x + 100, this.y - 100 + f, this.x + 120,
                  this.y - 98 + f, c);
            }
            //g
            for (int x1 = 0; x1 <= 1; x1++) {
                if (x == 99 && x1 == 1) break;
                Colour.RGB rgb1 = new Colour.RGB(rgb.r, (x * 2 + x1) / 200d, rgb.b);
                c = x == 99 && rgb.g > 0.99 ? pointer :
                  MathUtils.approxEquals(rgb1.g, rgb.g, 0.005) ?
                    pointer :
                    rgb1.toColor();
                float f = (x * 2 + x1);
                drawRect(this.x + 120, this.y - 100 + f, this.x + 140,
                  this.y - 98 + f, c);
            }
            //b
            for (int x1 = 0; x1 <= 1; x1++) {
                if (x == 99 && x1 == 1) break;
                Colour.RGB rgb1 = new Colour.RGB(rgb.r, rgb.g, (x * 2 + x1) / 200d);
                c = x == 99 && rgb.b > 0.99 ? pointer :
                  MathUtils.approxEquals(rgb1.b, rgb.b, 0.005) ?
                    pointer :
                    rgb1.toColor();
                float f = (x * 2 + x1);
                drawRect(this.x + 140, this.y - 100 + f, this.x + 160,
                  this.y - 98 + f, c);
            }
        }
        this.name = String.format("#%02x%02x%02x", (int) (rgb.r * 255), (int) (rgb.g * 255), (int) (rgb.b * 255));
    }

    private void hsv2rgb() {
        this.rgb = Colour.hsv2rgb(this.hsv);
    }

    private void rgb2hsv() {
        this.hsv = Colour.rgb2hsv(this.rgb);
    }

    @Override
    public void click() {
        if (this.selected == -1)
            super.click();
        if (this.parent.child == this && this.child == null) {
            if (this.selected == -1) {
                if (this.hover != -1) this.selected = this.hover;
            } else this.selected = -1;
        }
    }

    @Override
    public void close() {
        this.child = null;
    }
}
