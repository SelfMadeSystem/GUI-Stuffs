package uwu.smsgamer.lwjgltest.gui.radial;

import uwu.smsgamer.lwjgltest.stuff.Stuff;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawCircle;
import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawString;

public class CategoriesRing extends Ring {
    int amount;
    int hover = -1;
    String[] categories;

    public CategoriesRing(Ring prevRing) {
        super(prevRing);
        this.categories = Stuff.values.keySet().toArray(new String[0]);
        this.amount = categories.length;
    }

    @Override
    public void refresh() {
        this.categories = Stuff.values.keySet().toArray(new String[0]);
        this.amount = categories.length;
    }

    @Override
    public void renderStuff() {
        boolean in = this == currentRing;
        float div = in ? 1 : 0;
        if (lastSwitch < switchSpeed) div = in ? (lastSwitch) / switchSpeed : 1 - (lastSwitch) / switchSpeed;
        float add = in ? 0 : (360 - 360 * div);
        float spa = 360f / amount;
        if (in) {
            hover = (int) (rot / spa);
            if (hover == amount) hover = 0;
            if (over == 1) drawString(categories[hover], 0, 0, 0.15f, Color.WHITE);
            else drawString("Categories", 0, 0, 0.11f, Color.WHITE);
        }
        for (int i = 0; i < amount; i++) {
            Color circleColor = over == 1 && hover == i && in ? secondColorHighlight : secondColor;
            drawCircle(0, 0, 0.98f, 0.77f,
              (i * spa + 1) * div + add, (i * spa + spa - 1) * div + add,
              0.2f * div, circleColor);
        }
    }

    @Override
    public void click() {
        switch (over) {
            case 0: {
                Ring.prevRing();
                break;
            }
            case 1: {
                Ring.newRing(new ModulesRing(this, categories[hover]));
                break;
            }
        }
    }
}
