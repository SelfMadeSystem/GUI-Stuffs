package uwu.smsgamer.lwjgltest.gui.radial;

import uwu.smsgamer.lwjgltest.stuff.*;
import uwu.smsgamer.lwjgltest.utils.MathUtils;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawCircle;
import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawString;

public class ValuesRing extends Ring {
    int amount;
    int hover = -1;
    String module;
    String category;
    ValStuff[] values;
    ValStuff val;

    public ValuesRing(Ring prevRing, String category, String module) {
        super(prevRing);
        this.category = category;
        this.module = module;
        this.values = Stuff.values.get(category).get(module);
        this.amount = values.length;
    }

    public ValuesRing(Ring prevRing, ValStuff val) {
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
        boolean in = this == currentRing;
        float div = in ? 1 : 0;
        if (lastSwitch < switchSpeed) div = in ? (lastSwitch) / switchSpeed : 1 - (lastSwitch) / switchSpeed;
        float add = in ? 0 : (360 - 360 * div);
        float spa = 360f / amount;
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
                    switch (selected.type){
                        case COLOUR: {
                            Color c = (Color) selected.value;
                            drawString(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()), 0, -0.07f, 0.06f, Color.WHITE);
                            break;
                        }
                        default:
                        drawString(String.valueOf(selected.value), 0, -0.07f, 0.06f, Color.WHITE);
                    }
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
                case COLOUR: {
                    Color color = (Color) val.value;
                    drawCircle(0, 0, 1.02f, 1.08f, sta, v, 0, color);
                    break;
                }
            }
        }
    }

    ValStuff current = null;
    int currentH = -1;

    @Override
    public void click() {
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
                            newRing(new ValuesRing(this, values[hover]));
                            break;
                        }
                        case COLOUR: {
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
                            newRing(new ValuesRing(this, values[hover]));
                            break;
                        }
                    }
                } else {
                    currentH = -1;
                    current = null;
                }
                break;
            }
        }
    }
}
