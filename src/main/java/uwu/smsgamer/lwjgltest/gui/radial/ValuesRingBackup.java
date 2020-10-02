/*
package uwu.smsgamer.lwjgltest.gui;

import uwu.smsgamer.lwjgltest.stuff.*;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawCircle;
import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawString;

public class ValuesRingBackup extends Ring {
    int amount;
    int hover = -1;
    String module;
    String category;
    ValStuff[] values;
    ValStuff val;

    public ValuesRingBackup(Ring prevRing, String category, String module) {
        super(prevRing);
        this.category = category;
        this.module = module;
        this.values = Stuff.values.get(category).get(module);
        this.amount = values.length;
    }

    public ValuesRingBackup(Ring prevRing, ValStuff val) {
        super(prevRing);
        this.val = val;
        this.values = val.values;
        this.amount = values.length;
        this.module = val.name;
    }

    @Override
    public void refresh() {
        if (this.val == null) {
            this.values = Stuff.values.get(category).get(module);
            this.amount = values.length;
        } else {
            this.values = val.values;
            this.amount = values.length;
            this.module = val.name;
        }
    }

    @Override
    public void renderStuff() {
        float spa = 360f / amount;
        hover = (int) (rot / spa);
        if (hover == amount) hover = 0;
        if (over == 1) {
            ValStuff selected = values[hover];
            drawString(selected.name, 0, 0.1f, 0.1f, Color.WHITE);
            drawString(String.valueOf(selected.value), 0, -0.07f, 0.06f, Color.WHITE);
        } else {
            drawString(module, 0, 0f, 0.15f, Color.WHITE);
        }
        for (int i = 0; i < amount; i++) {
            ValStuff val = values[i];
            Color circleColor = over == 1 && hover == i ? secondColorHighlight : secondColor;
            drawCircle(0, 0, 0.98f, 0.77f, i * spa + 1, i * spa + spa - 1, 0.2f, circleColor);
            switch (val.type) {
                case BOOLEAN: {
                    drawCircle(0, 0, 1.02f, 1.08f, i * spa + 1, i * spa + spa - 1, 0,
                      ((boolean) val.value) ? onColor : offColor);
                    break;
                }
                case NUMBER: {
                    drawCircle(0, 0, 1.02f, 1.08f, i * spa + 1, (float) (i * spa + (((double) val.value - val.min) / (val.max - val.min) * spa) - 1), 0,
                      Color.WHITE);
                    break;
                }
            }
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
                switch (values[hover].type) {
                    case BOOLEAN: {
                        values[hover].value = !((boolean) values[hover].value);
                        //Ring.newRing(new ToggleRing(this, values[hover]));
                        break;
                    }
                    case NUMBER: {
                        Ring.newRing(new SliderRing(this, values[hover]));
                        break;
                    }
                    case STRING: {
                        Ring.newRing(new StringRing(this, values[hover]));
                        break;
                    }
                    case CHOICE: {
                        Ring.newRing(new ChoiceRing(this, values[hover]));
                        break;
                    }
                    case VALUES: {
                        Ring.newRing(new ValuesRingBackup(this, values[hover]));
                        break;
                    }
                }
                //Ring.newRing(new ValuesRing(this, values[hover]));
                break;
            }
        }
    }
}
*/
