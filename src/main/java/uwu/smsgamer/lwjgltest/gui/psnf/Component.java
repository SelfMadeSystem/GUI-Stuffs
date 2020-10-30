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

public abstract class Component {
    public static final float WIDTH = 75;
    public static final float HEIGHT = 50;
    public static final float ROUND = 5;
    public static final float EDGE_RAD = 2;

    public float x;
    public float y;
    public boolean selected;

    public int prevOpacity;
    public int opacity;

    public abstract void render();
    public abstract void click();
    public void unclick(){
    }

    public void setOpacity(int o) {
        prevOpacity = opacity;
        opacity = o;
    }

    public int getOpacity() {
        int opc =  (int) (prevOpacity + (mngr().getChange() * (opacity - prevOpacity)));
        if (opc == opacity) prevOpacity = opacity;
        return opc;
    }

    public boolean isActive() {
        return mngr().currentComponent.equals(this);
    }

    public int getReverseOpacity() {
        return 255-getOpacity();
    }

    public PSNFManager mngr() {
        return PSNFManager.getInstance();
    }
}
