/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.radial;

import uwu.smsgamer.lwjgltest.stuff.ValStuff;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawCircle;
import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawString;

public class ColourRing extends Ring {
    ValStuff val;

    public ColourRing(Ring prevRing, String category, String module) {
        super(prevRing);
    }

    public ColourRing(Ring prevRing, ValStuff val) {
        super(prevRing);
        this.val = val;
    }

    @Override
    public void refresh() {
    }

    int hover = -1;
    //0r, 1b, 2g, -1 none
    int rgbHover = -1;

    @Override
    public void renderStuff() {
        boolean in = this == currentRing;
        float div = in ? 1 : 0;
        if (lastSwitch < switchSpeed) div = in ? (lastSwitch) / switchSpeed : 1 - (lastSwitch) / switchSpeed;
        float add = in ? 0 : (360 - 360 * div);
        center = (Color) val.value;
        for (int i = 0; i < 255; i++) {
            Color c = new Color(i, center.getGreen(), center.getBlue());
            float pos = i * (118 / 255f);
            drawCircle(0, 0, 0.98f, 0.77f, (1 + pos) * div + add, (2 + pos) * div + add, 0.2f * div, c);
        }
        for (int i = 0; i < 255; i++) {
            Color c = new Color(center.getRed(), i, center.getBlue());
            float pos = i * (118 / 255f);
            drawCircle(0, 0, 0.98f, 0.77f, (121 + pos) * div + add, (122 + pos) * div + add, 0.2f * div, c);
        }
        for (int i = 0; i < 255; i++) {
            Color c = new Color(center.getRed(), center.getGreen(), i);
            float pos = i * (118 / 255f);
            drawCircle(0, 0, 0.98f, 0.77f, (241 + pos) * div + add, (242 + pos) * div + add, 0.2f * div, c);
        }
        drawCircle(0, 0, 0.98f, 1.08f, (1 + 118 * center.getRed() / 255f) * div + add, (2 + 118 * center.getRed() / 255f) * div + add, 0.2f * div,
          center);
        drawCircle(0, 0, 0.98f, 1.08f, (121 + 118 * center.getGreen() / 255f) * div + add, (122 + 118 * center.getGreen() / 255f) * div + add, 0.2f * div,
          center);
        drawCircle(0, 0, 0.98f, 1.08f, (241 + 118 * center.getBlue() / 255f) * div + add, (242 + 118 * center.getBlue() / 255f) * div + add, 0.2f * div,
          center);
        if (!in)
            return;
        Color c = (0.2126 * center.getRed() + 0.7152 * center.getGreen() + 0.0722 * center.getBlue() > 128) ? Color.BLACK : Color.WHITE;

        drawString(val.name, 0, 0.1f, 0.1f, c);
        drawString(String.format("#%02x%02x%02x", center.getRed(), center.getGreen(), center.getBlue()), 0, -0.07f, 0.06f, c);
        int spa = 120;
        hover = (int) (rot / spa);
        if (over == 1) {
            if (hover == 3) hover = 0;
        } else hover = -1;
        if (rgbHover != -1) {
            double pos = ((rot - rgbHover * spa) / spa);
            int min = 0;
            int max = 255;
            int value = (int) (pos * (max - min) + min);
            if (value < min) value = min;
            else if (value > max) value = max;
            switch (rgbHover) {
                case 0: {
                    if (rot > 240) value = 0;
                    val.value = new Color(value, center.getGreen(), center.getBlue());
                    break;
                }
                case 1: {
                    val.value = new Color(center.getRed(), value, center.getBlue());
                    break;
                }
                case 2: {
                    if (rot < 120) value = 255;
                    val.value = new Color(center.getRed(), center.getGreen(), value);
                    break;
                }
            }
        }
        //float add = in ? 0 : (360 - 360 * div);
        /*float spa = 360f / amount;
        if (in) {
            hover = (int) (rot / spa);
            if (hover == amount) hover = 0;

            if (current != null) {
                drawString(current.name, 0, 0.1f, 0.1f, Color.WHITE);
                drawString(String.valueOf(current.value), 0, -0.07f, 0.06f, Color.WHITE);
                switch (current.type) {
                    case NUMBER: {
                        double pos = ((rot - currentH * spa) / spa);
                        double value = MathUtils.roundInc(pos * (current.max - current.min) + current.min + current.step / 2, current.step);
                        if (value >= current.min && value <= current.max) {
                            current.value = value;
                        } else {
                            if (value < current.min) current.value = current.min;
                            else current.value = current.max;
                        }
                        break;
                    }
                }
            } else {
                if (over > 0) {
                    ValStuff selected = values[hover];
                    drawString(selected.name, 0, 0.1f, 0.1f, Color.WHITE);
                    drawString(String.valueOf(selected.value), 0, -0.07f, 0.06f, Color.WHITE);
                } else {
                    drawString(module, 0, 0f, 0.15f, Color.WHITE);
                }
            }
        }
        for (int i = 0; i < amount; i++) {
            ValStuff val = values[i];
            Color circleColor = current == null ? over == 1 && hover == i && in ? secondColorHighlight : secondColor :
              val == current ? secondColorHighlight : secondColor;
            final float sta = (i * spa + 1) * div + add;
            final float v = (i * spa + spa - 1) * div + add;
            drawCircle(0, 0, 0.98f, 0.77f, sta, v, 0.2f * div, circleColor);
            switch (val.type) {
                case BOOLEAN: {
                    drawCircle(0, 0, 1.02f, 1.08f, sta, v, 0,
                      ((boolean) val.value) ? onColor : offColor);
                    break;
                }
                case NUMBER: {
                    float max = (float) ((i * spa) + (((double) val.value - val.min) / (val.max - val.min) * spa) - 1);
                    if (((double) val.value) != val.min && i * spa + 1 < max) {
                        drawCircle(0, 0, 1.02f, 1.08f, sta, (max) * div + add, 0,
                          Color.WHITE);
                    }
                    break;
                }
            }
        }*/
    }

    @Override
    public void click() {
        if (rgbHover == -1) {
            if (over == 0) {
                prevRing();
                return;
            }
            rgbHover = hover;
        } else {
            rgbHover = -1;
        }
        /*
        switch (over) {
            case 0: {
                if (currentH == -1) {
                    prevRing();
                } else {
                    currentH = -1;
                    current = null;
                }
                break;
            }
            case 1: {
                if (currentH == -1) {
                    switch (values[hover].type) {
                        case BOOLEAN: {
                            values[hover].value = !((boolean) values[hover].value);
                            //Ring.newRing(new ToggleRing(this, values[hover]));
                            break;
                        }
                        case NUMBER: {
                            currentH = hover;
                            current = values[hover];
                            //Ring.newRing(new SliderRing(this, values[hover]));
                            break;
                        }
                        case STRING: {
                            newRing(new StringRing(this, values[hover]));
                            break;
                        }
                        case CHOICE: {
                            newRing(new ChoiceRing(this, values[hover]));
                            break;
                        }
                        case VALUES: {
                            newRing(new ColourRing(this, values[hover]));
                            break;
                        }
                    }
                    //Ring.newRing(new ValuesRing(this, values[hover]));
                } else {
                    currentH = -1;
                    current = null;
                }
                break;
            }
            case 2: {
                if (currentH == -1) {
                    switch (values[hover].type) {
                        case NUMBER: {
                            newRing(new SliderRing(this, values[hover]));
                            break;
                        }
                        case BOOLEAN: {
                            newRing(new ToggleRing(this, values[hover]));
                            break;
                        }
                        case VALUES: {
                            newRing(new ColourRing(this, values[hover]));
                            break;
                        }
                    }
                } else {
                    currentH = -1;
                    current = null;
                }
                break;
            }
        }*/
    }
}
