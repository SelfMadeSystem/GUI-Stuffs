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

package uwu.smsgamer.lwjgltest.gui.colour;

import uwu.smsgamer.lwjgltest.utils.*;
import uwu.smsgamer.lwjgltest.utils.Colour.*;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.gui.colour.ColourComponent.HEIGHT;

public class ColourManager {
    private static ColourManager instance;
    // 0 Just colour
    // 1 H
    // 2 S
    // 3 V
    // 4 R
    // 5 G
    // 6 B
    public int centerSelect = 6;
    public HSV hsv;
    public RGB rgb;
    public HSVSlider hSlider = new HSVSlider(0);
    public HSVSlider sSlider = new HSVSlider(1);
    public HSVSlider vSlider = new HSVSlider(2);
    public RGBSlider rSlider = new RGBSlider(0);
    public RGBSlider gSlider = new RGBSlider(1);
    public RGBSlider bSlider = new RGBSlider(2);
    public Color pointer;

    public ColourManager() {
        setRGB(new RGB(0.5, 0.5, 0.5));
    }

    public static ColourManager getInstance() {
        if (instance == null) instance = new ColourManager();
        return instance;
    }

    public void setRGB(RGB rgb) {
        this.rgb = rgb;
        this.hsv = Colour.rgb2hsv(rgb);
    }

    public void setHSV(HSV hsv) {
        this.hsv = hsv;
        this.rgb = Colour.hsv2rgb(hsv);
    }

    public void render() {
        pointer = (0.2126 * rgb.r + 0.7152 * rgb.g + 0.0722 * rgb.b < 0.5) //hsv.v < 0.5
          ? Color.WHITE : Color.BLACK;
        RenderUtils.drawRectUnDiv(-1, -1, 1, 1, rgb.toColor());
        hSlider.render();
        sSlider.render();
        vSlider.render();
        rSlider.render();
        gSlider.render();
        bSlider.render();
        HSV hsv0 = hsv.clone();
        RGB rgb0 = rgb.clone();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < HEIGHT; x++) {
                switch (centerSelect) {
                    case 0:
                        break;
                    case 1:
                        hsv0.s = (float) x / HEIGHT;
                        hsv0.v = (float) y / HEIGHT;
                        break;
                    case 2:
                        hsv0.h = (float) x / HEIGHT * 360;
                        hsv0.v = (float) y / HEIGHT;
                        break;
                    case 3:
                        hsv0.h = (float) x / HEIGHT * 360;
                        hsv0.s = (float) y / HEIGHT;
                        break;
                    case 4:
                        rgb0.g = (float) x / HEIGHT;
                        rgb0.b = (float) y / HEIGHT;
                        break;
                    case 5:
                        rgb0.r = (float) x / HEIGHT;
                        rgb0.b = (float) y / HEIGHT;
                        break;
                    case 6:
                        rgb0.r = (float) x / HEIGHT;
                        rgb0.g = (float) y / HEIGHT;
                        break;
                }
                if (centerSelect <= 3 ? hsv0.equals(hsv) : rgb0.equals(rgb)) {
                    RenderUtils.drawRect(x - 1 - HEIGHT / 2F, y - HEIGHT / 2F, x - HEIGHT / 2F,
                      y + 1 - HEIGHT / 2F, pointer);
                } else {
                    RenderUtils.drawRect(x - 1 - HEIGHT / 2F, y - HEIGHT / 2F, x - HEIGHT / 2F,
                      y + 1 - HEIGHT / 2F, centerSelect <= 3 ? hsv0.toColor() : rgb0.toColor());
                }
            }
        }
    }
}
