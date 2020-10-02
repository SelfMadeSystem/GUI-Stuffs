package uwu.smsgamer.lwjgltest.gui.radial;

import uwu.smsgamer.lwjgltest.stuff.ValStuff;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawCircle;
import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawString;

public class ToggleRing extends Ring {
    public ValStuff value;

    public ToggleRing(Ring prevRing, ValStuff val) {
        super(prevRing);
        this.value = val;
    }

    @Override
    public void refresh() {
    }

    boolean value() {
        return (boolean) value.value;
    }

    @Override
    public void renderStuff() {
        boolean in = this == currentRing;
        float div = in ? 1 : 0;
        if (lastSwitch < switchSpeed) div = in ? (lastSwitch) / switchSpeed : 1 - (lastSwitch) / switchSpeed;
        float add = in ? 0 : (360 - 360 * div);
        if (in) {
            drawString(value.name, 0, 0.1f, 0.1f, Color.WHITE);
            drawString(String.valueOf(value.value), 0, -0.07f, 0.06f, Color.WHITE);
            if (mouseDown && over == 1) {
                drawCircle(0, 0, 0.98f, 0.77f, add, 360 * div + add, 0f, value() ? onColorC : offColorC);
            } else {
                if (over == 1) {
                    drawCircle(0, 0, 0.98f, 0.77f, add, 360 * div + add, 0f, value() ? onColorH : offColorH);
                } else {
                    drawCircle(0, 0, 0.98f, 0.77f, add, 360 * div + add, 0f, value() ? onColor : offColor);
                }
            }
        } else {
            drawCircle(0, 0, 0.98f, 0.77f, add, 360 * div + add, 0f, value() ? onColor : offColor);
        }
    }

    @Override
    public void click() {
        if (over == 0)
            prevRing();
    }

    @Override
    public void unClick() {
        if (over == 1) {
            value.value = !value();
        }
    }
}
