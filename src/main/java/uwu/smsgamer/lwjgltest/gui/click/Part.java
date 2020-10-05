/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.click;

import uwu.smsgamer.lwjgltest.gui.click.parts.CategoryPart;
import uwu.smsgamer.lwjgltest.input.MouseHelper;

import java.awt.*;

public abstract class Part {
    public static int[] topSize = new int[]{160, 20};
    public static int[] mainSize = new int[]{150, 30};
    public static float maxLength = 300;

    public static float edgeRadius = 2;

    //top
    public static Color TOP_COLOR = Color.blue;
    public static Color TOP_COLOR_HOVER = Color.cyan;
    public static Color TOP_BORDER_COLOR = Color.red;
    //main
    public static Color MAIN_COLOR = new Color(156, 156, 156);
    public static Color MAIN_COLOR_HOVER = new Color(128, 128, 128);
    public static Color MAIN_COLOR_SELECT = new Color(100, 100, 100);
    public static Color BORDER_COLOR = new Color(64, 64, 64);
    //slider
    public static Color SLIDER_COLOR = Color.cyan;
    public static Color SLIDER_COLOR_HOVER = new Color(0, 225, 225);
    public static Color SLIDER_HINT_AFTER = new Color(159, 255, 255);
    public static Color SLIDER_HINT_BEFORE = new Color(0, 225, 225);
    //toggle
    public static Color ON_COLOR = new Color(0, 180, 0);
    public static Color OFF_COLOR = new Color(180, 0, 0);
    public static Color ON_COLOR_HOVER = new Color(0, 230, 0);//hover
    public static Color OFF_COLOR_HOVER = new Color(230, 0, 0);
    public static Color ON_COLOR_CLICK = new Color(0, 160, 0);//click
    public static Color OFF_COLOR_CLICK = new Color(160, 0, 0);
    //other shit
    public static Color MORE_BASE_COLOR = new Color(100, 100, 100);
    public static Color MORE_HOVER_COLOR = new Color(80, 80, 80);
    public static Color MORE_OPEN_COLOR = new Color(70, 70, 70);
    public boolean open;
    public String name;
    public CategoryPart category;

    public Part(String name, CategoryPart category) {
        this.name = name;
        this.category = category;
    }

    private float tempPosX, tempPosY;

    public void render(float x, float y, float maxY) {
        this.category.yAdd += getSize()[1];
        this.tempPosX = this.category.x;
        this.tempPosY = this.category.y - this.category.yAdd + (getSize()[1] / 2F);
    }

    public void scroll(double amount) {
    }

    public void click(int button) {
    }

    public void unclick(int button) {
    }

    public void charKey(char c) {
    }

    public void key(int key) {
    }

    public void open() {
        this.open = true;
    }

    public void close() {
        this.open = false;
    }

    public float getY() {
        return this.tempPosY;
    }

    public float getX() {
        return this.tempPosX;
    }

    public boolean hovering() {
        return this.category.hoverRaw(getX() - getSize()[0] / 2F, getX() + getSize()[0] / 2F,
          Math.max(category.y - maxLength, getY() - getSize()[1] / 2F),
          Math.min(category.y - category.getSize()[1]/2F, getY() + getSize()[1] / 2F));
    }

    public int[] getSize() {
        return CategoryPart.mainSize;
    }

    public static double getRangeX(double minX, double maxX) {
        int mouseX = MouseHelper.posX - 250;
        double delta = maxX - minX;
        return Math.min(1, Math.max(0, (mouseX - (minX)) / delta));
    }

    public static double getRangeY(double minY, double maxY) {
        int mouseY = -MouseHelper.posY + 250;
        double delta = maxY - minY;
        return Math.min(1, Math.max(0, (mouseY - (minY)) / delta));
    }

    public boolean notOverridden() {
        return ClickGUIManager.getInstance().inputOverride == null || ClickGUIManager.getInstance().inputOverride.equals(this);
    }
}
