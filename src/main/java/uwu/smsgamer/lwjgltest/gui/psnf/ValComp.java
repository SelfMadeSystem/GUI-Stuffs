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

package uwu.smsgamer.lwjgltest.gui.psnf;

import uwu.smsgamer.lwjgltest.gui.psnf.components.*;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;

public abstract class ValComp extends Component {
    public final ValStuff valStuff;
    public final Module module;
    public final Category category;
    public final Component prevComponent;
    public ValComp(ValStuff valStuff, Module module, Category category, Component prevComponent) {
        this.valStuff = valStuff;
        this.module = module;
        this.category = category;
        this.prevComponent = prevComponent;
    }

    @Override
    public void unclick() {
        mngr().currentComponent = prevComponent;
    }
}
