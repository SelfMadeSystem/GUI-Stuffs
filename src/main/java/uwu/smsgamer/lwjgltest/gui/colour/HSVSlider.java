/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/

package uwu.smsgamer.lwjgltest.gui.colour;

import uwu.smsgamer.lwjgltest.utils.*;

public class HSVSlider extends ColourComponent {
    public final int type;

    public HSVSlider(int type) {
        this.type = type;
    }

    @Override
    public void render() {
        Colour.HSV hsv = mngr().hsv.clone();
        for (int i = 0; i < HEIGHT; i++) {
            switch (type) {
                case 0:
                    hsv.h = (float) i / HEIGHT * 360;
                    break;
                case 1:
                    hsv.s = (float) i / HEIGHT;
                    break;
                case 2:
                    hsv.v = (float) i / HEIGHT;
                    break;
            }
            if (mngr().hsv.equals(hsv)) {
                RenderUtils.drawRect(X_OFFSET + WIDTH * type, Y_OFFSET + i,
                  X_OFFSET + WIDTH * (type + 1), Y_OFFSET + i + 1, mngr().pointer);
            } else {
                RenderUtils.drawRect(X_OFFSET + WIDTH * type, Y_OFFSET + i,
                  X_OFFSET + WIDTH * (type + 1), Y_OFFSET + i + 1, hsv.toColor());
            }
        }
    }
}
