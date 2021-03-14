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

package uwu.smsgamer.lwjgltest.tests;

import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class StencilTests {
    public static void render() {
        glEnable(GL_STENCIL_TEST);
        glClearStencil(0);
        glClear(GL_STENCIL_BUFFER_BIT);

        glColorMask(false, false, false, false); // Do not draw any pixels on the back buffer
        glEnable(GL_STENCIL_TEST); // Enables testing AND writing functionalities
        glStencilFunc(GL_ALWAYS, 1, 0xFF); // Do not test the current value in the stencil buffer, always accept any value on there for drawing
        glStencilMask(0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_INVERT); // Make every test succeed

        glBegin(GL_TRIANGLES);

        glVertex2d(-1, -1);
        glVertex2d(1, -1);
        glVertex2d(-1, 1);

        glEnd();

        glBegin(GL_TRIANGLES);

        glVertex2d(1, 1);
        glVertex2d(1, -1);
        glVertex2d(-1, -1);

        glEnd();

        glBegin(GL_QUADS);

        glVertex2d(-0.5, -1);
        glVertex2d(-0.5, 1);

        glVertex2d(0.5, 1);
        glVertex2d(0.5, -1);

        glEnd();

        drawMidColor(Color.WHITE);

        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
        glColorMask(true, true, true, true);

        glStencilFunc(GL_EQUAL, 1, 0xFF);

//        RenderUtils.drawRectUnDiv(-1, -1, 1, 1, Color.RED);

        glDisable(GL_STENCIL_TEST);
    }

    public static void drawMidColor(Color color) {
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
        glStencilMask(0x00);
        glColorMask(true, true, true, true);

        glStencilFunc(GL_EQUAL, 1, 0xFF);

        RenderUtils.drawRectUnDiv(-1, -1, 1, 1, color);

        glColorMask(false, false, false, false); // Do not draw any pixels on the back buffer
        glStencilFunc(GL_ALWAYS, 1, 0xFF); // Do not test the current value in the stencil buffer, always accept any value on there for drawing
        glStencilMask(0xFF);
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_INCR); // Make every test succeed
    }
}
