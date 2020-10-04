/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.click;

import uwu.smsgamer.lwjgltest.gui.click.parts.*;

public class ValPart extends Part {
    public CategoryPart category;
    public ModulePart module;
    public int indent;

    public ValPart(CategoryPart category, ModulePart module, int indent) {
        this.category = category;
        this.module = module;
        this.indent = indent;
    }
}
