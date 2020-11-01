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

import uwu.smsgamer.lwjgltest.input.*;
import uwu.smsgamer.lwjgltest.utils.*;
import uwu.smsgamer.lwjgltest.utils.Colour.*;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.gui.colour.ColourComponent.WIDTH;

public class ColourManager {
    private static ColourManager instance;
    // 0 Just colour
    // 1 H   HSV
    // 2 S
    // 3 V
    // 4 R   RGB
    // 5 G
    // 6 B
    // 7 C   CMY
    // 8 M
    // 9 Y
    public int centerSelect = 1;
    public HSV hsv;
    public RGB rgb;
    public CMY cmy;
    public HSVSlider hSlider = new HSVSlider(0);
    public HSVSlider sSlider = new HSVSlider(1);
    public HSVSlider vSlider = new HSVSlider(2);
    public RGBSlider rSlider = new RGBSlider(0);
    public RGBSlider gSlider = new RGBSlider(1);
    public RGBSlider bSlider = new RGBSlider(2);
    public CMYSlider cSlider = new CMYSlider(0);
    public CMYSlider mSlider = new CMYSlider(1);
    public CMYSlider ySlider = new CMYSlider(2);
    public Color cursor;
    boolean clicking = false;

    public static ColourManager getInstance() {
        if (instance == null) instance = new ColourManager();
        return instance;
    }

    public void setRGB(RGB rgb) {
        this.rgb = rgb.clone();
        this.hsv = Colour.rgb2hsv(rgb);
        this.cmy = new CMY(rgb);
    }

    public void setHSV(HSV hsv) {
        this.hsv = hsv.clone();
        this.rgb = Colour.hsv2rgb(hsv);
        this.cmy = new CMY(hsv);
    }

    public void setCMY(CMY cmy) {
        this.cmy = cmy.clone();
        this.rgb = cmy.toRGB();
        this.hsv = cmy.toHSV();
    }

    public ColourManager() {
        setRGB(new RGB(1, 0, 0));
    }

    public void render() {
        cursor = (0.2126 * rgb.r + 0.7152 * rgb.g + 0.0722 * rgb.b < 0.5) //hsv.v < 0.5
          ? Color.WHITE : Color.BLACK;
        RenderUtils.drawRectUnDiv(-1, -1, 1, 1, rgb.toColor());
        hSlider.render();
        sSlider.render();
        vSlider.render();
        rSlider.render();
        gSlider.render();
        bSlider.render();
        cSlider.render();
        mSlider.render();
        ySlider.render();
        int size = 500 - WIDTH * 20;
        int mouseOffset = -WIDTH * 10;

        int mouseX = MouseHelper.posX + mouseOffset;
        int mouseY = 500 - MouseHelper.posY + mouseOffset;
        if (!clicking && (mouseX >= 0 && mouseY >= 0 && mouseX <= size && mouseY <= size)) {
            clicking = InputManager.ML.justPressed();
        }

        if (clicking) {
            clicking = InputManager.ML.isDown();
            if (clicking) {
                int x = Math.min(size, Math.max(0, mouseX));
                int y = Math.min(size, Math.max(0, mouseY));
                switch (centerSelect) {
                    case 0:
                        break;
                    case 1:
                        hsv.s = (float) x / size;
                        hsv.v = (float) y / size;
                        setHSV(hsv);
                        break;
                    case 2:
                        hsv.h = (float) x / size * 360;
                        hsv.v = (float) y / size;
                        setHSV(hsv);
                        break;
                    case 3:
                        hsv.h = (float) x / size * 360;
                        hsv.s = (float) y / size;
                        setHSV(hsv);
                        break;
                    case 4:
                        rgb.g = (float) x / size;
                        rgb.b = (float) y / size;
                        setRGB(rgb);
                        break;
                    case 5:
                        rgb.r = (float) x / size;
                        rgb.b = (float) y / size;
                        setRGB(rgb);
                        break;
                    case 6:
                        rgb.r = (float) x / size;
                        rgb.g = (float) y / size;
                        setRGB(rgb);
                        break;
                    case 7:
                        cmy.m = (float) x / size;
                        cmy.y = (float) y / size;
                        setCMY(cmy);
                        break;
                    case 8:
                        cmy.c = (float) x / size;
                        cmy.y = (float) y / size;
                        setCMY(cmy);
                        break;
                    case 9:
                        cmy.c = (float) x / size;
                        cmy.m = (float) y / size;
                        setCMY(cmy);
                        break;
                }
            }
        }

        HSV hsv0 = hsv.clone();
        RGB rgb0 = rgb.clone();
        CMY cmy0 = cmy.clone();
        for (int y = 0; y <= size; y++) {
            for (int x = 0; x <= size; x++) {
                switch (centerSelect) {
                    case 0:
                        break;
                    case 1:
                        hsv0.s = (float) x / size;
                        hsv0.v = (float) y / size;
                        break;
                    case 2:
                        hsv0.h = (float) x / size * 360;
                        hsv0.v = (float) y / size;
                        break;
                    case 3:
                        hsv0.h = (float) x / size * 360;
                        hsv0.s = (float) y / size;
                        break;
                    case 4:
                        rgb0.g = (float) x / size;
                        rgb0.b = (float) y / size;
                        break;
                    case 5:
                        rgb0.r = (float) x / size;
                        rgb0.b = (float) y / size;
                        break;
                    case 6:
                        rgb0.r = (float) x / size;
                        rgb0.g = (float) y / size;
                        break;
                    case 7:
                        cmy0.m = (float) x / size;
                        cmy0.y = (float) y / size;
                        break;
                    case 8:
                        cmy0.c = (float) x / size;
                        cmy0.y = (float) y / size;
                        break;
                    case 9:
                        cmy0.c = (float) x / size;
                        cmy0.m = (float) y / size;
                        break;
                }
                if (centerSelect > 0 && (centerSelect <= 3 ? hsv0.equals(hsv) :
                  centerSelect <= 6 ? rgb0.equals(rgb) : cmy0.equals(cmy))) {
                    RenderUtils.drawRect(x - size / 2F, y - size / 2F, x + 1 - size / 2F,
                      y + 1 - size / 2F, cursor);
                } else {
                    RenderUtils.drawRect(x - size / 2F, y - size / 2F, x + 1 - size / 2F,
                      y + 1 - size / 2F, centerSelect <= 3 ? hsv0.toColor() :
                        centerSelect <= 6 ? rgb0.toColor() : cmy0.toColor());
                }
            }
        }

        boolean click = InputManager.ML.justPressed();
        boolean clicked = false;

        hsv0 = hsv.clone();
        hsv0.h = MathUtils.wrapAngle180(hsv0.h + 45) + 180;
        RenderUtils.drawRect(-size / 4F * 3F, size / 2F, -size / 2F, 0, hsv0.toColor());

        if (mouseX >= -size / 4F && mouseX <= 0 && click && mouseY >= size / 2F && mouseY <= size) {
            hsv = hsv0.clone();
            clicked = true;
        }

        hsv0.h = MathUtils.wrapAngle180(hsv0.h + 45) + 180;
        RenderUtils.drawRect(-size / 2F, size / 4F * 3F, 0, size / 2F, hsv0.toColor());

        if (mouseX >= 0 && mouseX <= size / 2F && click && mouseY >= size && mouseY <= size / 4F * 5F) {
            hsv = hsv0.clone();
            clicked = true;
        }

        hsv0.h = MathUtils.wrapAngle180(hsv0.h + 45) + 180;
        RenderUtils.drawRect(size / 2F, size / 2F, size / 4F * 3F, 0, hsv0.toColor());

        if (mouseX >= size && mouseX <= size / 4F * 5F && click && mouseY >= size / 2F && mouseY <= size) {
            hsv = hsv0.clone();
            clicked = true;
        }

        hsv0.h = MathUtils.wrapAngle180(hsv0.h + 45) + 180;
        RenderUtils.drawRect(-size / 2F, -size / 4F * 3F, size / 2F, -size / 2F, hsv0.toColor());

        if (mouseX >= 0 && mouseX <= size && click && mouseY >= -size / 4F && mouseY <= 0) {
            hsv = hsv0.clone();
            clicked = true;
        }

        hsv0.h = MathUtils.wrapAngle180(hsv0.h + 45) + 180;
        RenderUtils.drawRect(-size / 4F * 3F, 0, -size / 2F, -size / 2F, hsv0.toColor());

        if (mouseX >= -size / 4F && mouseX <= 0 && click && mouseY >= 0 && mouseY <= size / 2F) {
            hsv = hsv0.clone();
            clicked = true;
        }

        hsv0.h = MathUtils.wrapAngle180(hsv0.h + 45) + 180;
        RenderUtils.drawRect(0, size / 4F * 3F, size / 2F, size / 2F, hsv0.toColor());

        if (mouseX >= size / 2F && mouseX <= size && click && mouseY >= size && mouseY <= size / 4F * 5F) {
            hsv = hsv0.clone();
            clicked = true;
        }

        hsv0.h = MathUtils.wrapAngle180(hsv0.h + 45) + 180;
        RenderUtils.drawRect(size / 2F, 0, size / 4F * 3F, -size / 2F, hsv0.toColor());

        if (mouseX >= size && mouseX <= size / 4F * 5F && click && mouseY >= 0 && mouseY <= size / 2F) {
            hsv = hsv0.clone();
            clicked = true;
        }


        RenderUtils.drawString(String.format("RGB: #%02x%02x%02x", (int) (rgb.r * 255), (int) (rgb.g * 255), (int) (rgb.b * 255)),
          0, 230, 20, 30, 0, cursor);

        RenderUtils.drawString(String.format("HSV: #%02x%02x%02x", (int) (hsv.h / 360 * 255), (int) (hsv.s * 255), (int) (hsv.v * 255)),
          0, 200, 20, 30, 0, cursor);
        if (clicked)
            setHSV(hsv);
    }
}
