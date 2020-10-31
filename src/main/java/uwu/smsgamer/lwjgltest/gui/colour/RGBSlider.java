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

public class RGBSlider extends ColourComponent {
    public final int type;

    public RGBSlider(int type) {
        this.type = type;
    }

    @Override
    public void render() {
        Colour.RGB rgb = mngr().rgb.clone();
        for (int i = 0; i < HEIGHT; i++) {
            switch (type) {
                case 0:
                    rgb.r = (float) i / HEIGHT;
                    break;
                case 1:
                    rgb.g = (float) i / HEIGHT;
                    break;
                case 2:
                    rgb.b = (float) i / HEIGHT;
                    break;
            }
            if (mngr().rgb.equals(rgb)) {
                RenderUtils.drawRect(-X_OFFSET - WIDTH * (type + 1), Y_OFFSET + i,
                  -X_OFFSET - WIDTH * (type), Y_OFFSET + i + 1, mngr().pointer);
            } else {
                RenderUtils.drawRect(-X_OFFSET - WIDTH * (type + 1), Y_OFFSET + i,
                  -X_OFFSET - WIDTH * (type), Y_OFFSET + i + 1, rgb.toColor());
            }
        }
    }
}
