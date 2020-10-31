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

public class RGBSlider extends ColourComponent {
    public final int type;

    public RGBSlider(int type) {
        this.type = type;
    }

    boolean clicking = false;
    long lastClick = 0;

    @Override
    public void render() {
        int mouseX = MouseHelper.posX;
        int mouseY = 500 - MouseHelper.posY;
        if (!clicking && (mouseX >= -X_OFFSET - WIDTH * (type + 1) + 250 && mouseY >= 0 && mouseX <= -X_OFFSET - WIDTH * (type) + 250 && mouseY <= HEIGHT)) {
            clicking = InputManager.ML.justPressed();
        }

        if (clicking) {
            clicking = InputManager.ML.isDown();
            if (clicking) {
                int y = Math.min(HEIGHT, Math.max(0, mouseY));
                switch (type) {
                    case 0:
                        mngr().rgb.r = (float) y / HEIGHT;
                        break;
                    case 1:
                        mngr().rgb.g = (float) y / HEIGHT;
                        break;
                    case 2:
                        mngr().rgb.b = (float) y / HEIGHT;
                        break;
                }
                mngr().setRGB(mngr().rgb);
            } else {
                long now = System.currentTimeMillis();
                if (now - lastClick < 350) mngr().centerSelect = 4 + type;
                lastClick = now;
            }
        }
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
                  -X_OFFSET - WIDTH * (type), Y_OFFSET + i + 1, mngr().cursor);
            } else {
                RenderUtils.drawRect(-X_OFFSET - WIDTH * (type + 1), Y_OFFSET + i,
                  -X_OFFSET - WIDTH * (type), Y_OFFSET + i + 1, rgb.toColor());
            }
        }
    }
}
