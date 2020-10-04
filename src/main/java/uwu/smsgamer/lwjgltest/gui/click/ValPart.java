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
import uwu.smsgamer.lwjgltest.stuff.ValStuff;

public abstract class ValPart extends Part {
    public ModulePart module;
    public int indent;
    public ValStuff valStuff;

    public ValPart(CategoryPart category, ValStuff valStuff, ModulePart module, int indent) {
        super(valStuff.name, category);
        this.module = module;
        this.indent = indent;
        this.valStuff = valStuff;
    }
}
