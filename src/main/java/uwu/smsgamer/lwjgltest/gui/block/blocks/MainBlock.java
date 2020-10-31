/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.block.blocks;

import uwu.smsgamer.lwjgltest.stuff.Stuff;

import java.awt.*;
import java.util.HashSet;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.*;

public class MainBlock extends Block {
    int amount;
    String[] categories;

    public MainBlock() {
        super(null, blockWidth, blockHeight, 0, 0);
        HashSet<String> set = new HashSet<>(Stuff.values.keySet());
        set.add("exit");
        categories = set.toArray(new String[0]);
        amount = categories.length;
    }

    int hover = -1;
    int selected = -1;

    @Override
    public void render() {
        hover = -1;
        x = -children() * 50;
        float minX = (x - (width / 2));
        float maxX = (x + (width / 2));
        drawRect(minX, (y - (height * (amount / 2f)) / 2) + edgeRound, maxX,
          (y + (height * (amount / 2f)) / 2) - edgeRound, BORDERED_COLOR);
        for (int i = 0; i < amount; i++) {
            float minY = ((y + (height / 2 * (i - amount / 2f))));
            float maxY = (y + (height / 2 * (i - amount / 2f)) + (height / 2));
            if (child == null && isHoveringRaw(minX, maxX, minY + edgeRadius / 3, maxY - edgeRadius / 3)) hover = i;
            Color c = categories[i].equals("exit") ? hover == i ? new Color(200, 0, 0) : Color.RED : selected == i ? SELECTED_COLOR : hover == i && this.child == null ? HOVER_COLOR : MAIN_COLOR;
            drawRoundBorderedRect(minX, minY, maxX, maxY, edgeRound, edgeRadius, c, BORDERED_COLOR);
            drawString(categories[i], x, minY + (maxY - minY) / 2, 20, 0, Color.BLACK);
        }
    }

    @Override
    public void click() {
        if (this.child == null && this.hover != -1) {
            if (categories[this.hover].equals("exit")) System.exit(0);
            this.child = new CategoryBlock(this, categories[this.hover]);
            this.selected = hover;
        }
    }

    @Override
    public void close() {
        this.child = null;
        this.selected = -1;
    }
}
