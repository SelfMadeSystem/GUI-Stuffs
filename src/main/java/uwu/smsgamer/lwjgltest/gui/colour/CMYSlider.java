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

public class CMYSlider extends ColourComponent {
    public final int type;
    boolean clicking = false;
    long lastClick = 0;

    public CMYSlider(int type) {
        this.type = type;
    }

    @Override
    public void render() {
        int mouseX = MouseHelper.posX;
        int mouseY = 500 - MouseHelper.posY;
        if (!clicking && (mouseX >= -X_OFFSET - WIDTH * (type + 4) + 250 && mouseY >= 0 && mouseX <= -X_OFFSET - WIDTH * (type + 3) + 250 && mouseY <= HEIGHT)) {
            clicking = InputManager.ML.justPressed();
        }

        if (clicking) {
            clicking = InputManager.ML.isDown();
            if (clicking) {
                int y = Math.min(HEIGHT, Math.max(0, mouseY));
                switch (type) {
                    case 0:
                        mngr().cmy.c = (float) y / HEIGHT;
                        break;
                    case 1:
                        mngr().cmy.m = (float) y / HEIGHT;
                        break;
                    case 2:
                        mngr().cmy.y = (float) y / HEIGHT;
                        break;
                }
                mngr().setCMY(mngr().cmy);
            } else {
                long now = System.currentTimeMillis();
                if (now - lastClick < 350) mngr().centerSelect = 7 + type;
                lastClick = now;
            }
        }
        Colour.CMY cmy = mngr().cmy.clone();
        for (int i = 0; i < HEIGHT; i++) {
            switch (type) {
                case 0:
                    cmy.c = (float) i / HEIGHT;
                    break;
                case 1:
                    cmy.m = (float) i / HEIGHT;
                    break;
                case 2:
                    cmy.y = (float) i / HEIGHT;
                    break;
            }
            if (mngr().cmy.equals(cmy)) {
                RenderUtils.drawRect(-X_OFFSET - WIDTH * (type + 4), Y_OFFSET + i,
                  -X_OFFSET - WIDTH * (type + 3), Y_OFFSET + i + 1, mngr().cursor);
            } else {
                RenderUtils.drawRect(-X_OFFSET - WIDTH * (type + 4), Y_OFFSET + i,
                  -X_OFFSET - WIDTH * (type + 3), Y_OFFSET + i + 1, cmy.toColor());
            }
        }
    }
}
