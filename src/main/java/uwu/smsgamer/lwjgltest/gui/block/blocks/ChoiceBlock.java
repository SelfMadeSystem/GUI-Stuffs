/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.block.blocks;

import uwu.smsgamer.lwjgltest.stuff.ValStuff;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.*;

public class ChoiceBlock extends SubBlock {
    ValStuff valStuff;
    String[] choices;
    int amount;

    public ChoiceBlock(Block parent, ValStuff valStuff) {
        super(parent, blockWidth, blockHeight, 0, 0, valStuff.name);
        this.valStuff = valStuff;
        this.choices = valStuff.choices;
        this.amount = this.choices.length;
    }

    int hover;

    @Override
    public void render() {
        drawRectUnDiv(-1, -1, 1, 1, new Color(0, 0, 0, 150));
        x = -(children() - 1) * 50;
        hover = -1;
        float minX = (x - (width / 2));
        float maxX = (x + (width / 2));
        drawRect(minX, (y - (height * (amount / 2f)) / 2) + edgeRound, maxX,
          (y + (height * (amount / 2f)) / 2), BORDERED_COLOR);
        drawTop(height * (amount / 2f) / 2);
        for (int i = 0; i < amount; i++) {
            float minY = ((y + (height / 2 * (i - amount / 2f))));
            float maxY = (y + (height / 2 * (i - amount / 2f)) + (height / 2));
            if (isHoveringRaw(minX, maxX, minY+edgeRadius/3, maxY-edgeRadius/3)) hover = i;
            Color c = valStuff.value.equals(choices[i]) ? SELECTED_COLOR : hover == i && child == null ? HOVER_COLOR : MAIN_COLOR;
            drawRoundBorderedRect(minX, minY, maxX, maxY, edgeRound, edgeRadius, c, BORDERED_COLOR);
            drawString(choices[i], x, minY + (maxY - minY) / 2, 20, 0, Color.BLACK);
        }
    }

    @Override
    public void click() {
        super.click();
        if (this.parent.child == this && this.child == null && this.hover != -1) {
            valStuff.value = choices[hover];
        }
    }

    @Override
    public void close() {
        this.child = null;
    }
}
