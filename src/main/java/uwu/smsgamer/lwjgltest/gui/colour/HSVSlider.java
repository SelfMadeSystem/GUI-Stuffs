/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/

package uwu.smsgamer.lwjgltest.gui.colour;

import uwu.smsgamer.lwjgltest.input.*;
import uwu.smsgamer.lwjgltest.utils.*;

public class HSVSlider extends ColourComponent {
    public final int type;

    public HSVSlider(int type) {
        this.type = type;
    }

    boolean clicking = false;
    long lastClick = 0;

    @Override
    public void render() {
        int mouseX = MouseHelper.posX;
        int mouseY = 500 - MouseHelper.posY;
        if (!clicking && (mouseX >= WIDTH * type && mouseY >= 0 && mouseX <= WIDTH * (type + 1) && mouseY <= HEIGHT)) {
            clicking = InputManager.ML.justPressed();
        }

        if (clicking) {
            clicking = InputManager.ML.isDown();
            if (clicking) {
                int y = Math.min(HEIGHT, Math.max(0, mouseY));
                switch (type) {
                    case 0:
                        mngr().hsv.h = (float) y / HEIGHT * 360;
                        break;
                    case 1:
                        mngr().hsv.s = (float) y / HEIGHT;
                        break;
                    case 2:
                        mngr().hsv.v = (float) y / HEIGHT;
                        break;
                }
                mngr().setHSV(mngr().hsv);
            } else {
                long now = System.currentTimeMillis();
                if (now - lastClick < 350) mngr().centerSelect = 1 + type;
                lastClick = now;
            }
        }
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
                  X_OFFSET + WIDTH * (type + 1), Y_OFFSET + i + 1, mngr().cursor);
            } else {
                RenderUtils.drawRect(X_OFFSET + WIDTH * type, Y_OFFSET + i,
                  X_OFFSET + WIDTH * (type + 1), Y_OFFSET + i + 1, hsv.toColor());
            }
        }
    }
}
