/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/

package uwu.smsgamer.lwjgltest.tests;

import uwu.smsgamer.lwjgltest.utils.text.TextRenderer;

import java.awt.*;

public class TextTest {
    private static final TextRenderer renderer = new TextRenderer("richardson brand accelerator", 0.2F, 0)
      .setVerticalAnchor(0).setHorizontalAnchor(0);
//      .setDebug_layers(true)
//      .setDebug_no_render(true);

    public static void render() {
        renderer.setHorizontalAnchor(-1);
        renderer.drawString("Left", -1, 0.5F, Color.WHITE);
        renderer.setHorizontalAnchor(1);
        renderer.drawString("Right", 1, 0.5F, Color.WHITE);
        renderer.setHorizontalAnchor(0);
        renderer.setVerticalAnchor(0);
        renderer.drawString("Middle", 0, 0, Color.WHITE);
        renderer.setVerticalAnchor(-1);
        renderer.drawString("Bottom", 0, -1F, Color.WHITE);
        renderer.setVerticalAnchor(1);
        renderer.drawString("Top", 0, 1F, Color.WHITE);
    }
}
