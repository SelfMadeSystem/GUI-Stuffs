/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.block;

import uwu.smsgamer.lwjgltest.gui.block.blocks.*;
import uwu.smsgamer.lwjgltest.input.MouseHelper;

public class BlockManager {
    private static BlockManager instance;

    public static BlockManager getInstance() {
        if (instance == null) instance = new BlockManager();
        return instance;
    }

    private final Block mainBlock = new MainBlock();

    public final void render() {
        render(mainBlock);
        click = MouseHelper.left;
    }

    private boolean click;

    private void render(Block block) {
        block.render();
        if (block.child != null) render(block.child);
        if (click && !MouseHelper.left) block.unclick();
        else if (!click && MouseHelper.left) block.click();
    }

    public void charKey(char c) {
        charKey(mainBlock, c);
    }

    private void charKey(Block b, char c) {
        b.charKey(c);
        if (b.child != null) charKey(b.child, c);
    }

    public void key(int key) {
        key(mainBlock, key);
    }

    private void key(Block b, int key) {
        b.key(key);
        if (b.child != null) key(b.child, key);
    }
}
