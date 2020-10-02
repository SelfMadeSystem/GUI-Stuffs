package uwu.smsgamer.lwjgltest.gui.click.parts.buttons;

import uwu.smsgamer.lwjgltest.gui.click.ClickGUIManager;
import uwu.smsgamer.lwjgltest.gui.click.parts.*;
import uwu.smsgamer.lwjgltest.input.MouseHelper;
import uwu.smsgamer.lwjgltest.stuff.ValStuff;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

public abstract class EditPart {
    protected static Color MAIN_COLOR = Color.lightGray;
    protected static Color MAIN_COLOR_HOVER = Color.gray;
    protected static Color MAIN_COLOR_SELECT = Color.darkGray;
    protected static Color BORDER_COLOR = Color.red;
    //slider
    protected static Color SLIDER_COLOR = Color.cyan;
    protected static Color SLIDER_COLOR_HOVER = new Color(0, 225, 225);
    protected static Color SLIDER_HINT_AFTER = new Color(159, 255, 255);
    protected static Color SLIDER_HINT_BEFORE = new Color(0, 225, 225);
    //toggle
    protected static Color ON_COLOR = new Color(0, 180, 0);
    protected static Color OFF_COLOR = new Color(180, 0, 0);
    protected static Color ON_COLOR_HOVER = new Color(0, 230, 0);//hover
    protected static Color OFF_COLOR_HOVER = new Color(230, 0, 0);
    protected static Color ON_COLOR_CLICK = new Color(0, 160, 0);//click
    protected static Color OFF_COLOR_CLICK = new Color(160, 0, 0);

    public ValStuff valStuff;
    public Category category;
    public Module module;
    public int inside = 0; // TODO: 2020-09-28 have it render w/ inside taken into account
    public float x, y, maxY;

    public EditPart(ValStuff valStuff, Category category, Module module, int inside) {
        this.valStuff = valStuff;
        this.category = category;
        this.inside = inside;
        this.module = module;
    }

    public EditPart(ValStuff valStuff, Category category, Module module) {
        this.valStuff = valStuff;
        this.category = category;
        this.module = module;
    }

    public void render(float x, float y, float maxY) {
        this.x = x;
        this.y = y;
        this.maxY = maxY;
    }

    public void close() {
        this.category.moreTabs--;
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

    public boolean hover() {
        return category.hoverRaw(x + inside * 2, x + Category.mainSize[0],
          Math.max(maxY, y), y + Category.mainSize[1]) &&
          (ClickGUIManager.getInstance().inputOverride == null || ClickGUIManager.getInstance().inputOverride.equals(this));
    }
    public double getRangeX(double minX, double maxX) {
        int mouseX = MouseHelper.posX - 250;
        double delta = maxX - minX;
        return Math.min(1, Math.max(0, (mouseX - (minX)) / delta));
    }

    public double getRangeY(double minY, double maxY) {
        int mouseY = MouseHelper.posY - 250;
        double delta = maxY - minY;
        return Math.min(1, Math.max(0, (mouseY - (minY)) / delta));
    }

    public void drawMainBox(float x, float y, Color main, Color border) {
        RenderUtils.drawBorderedRect(x + inside * 2, Math.max(maxY, y),
          x + Category.mainSize[0], y + Category.mainSize[1],
          1, main, border);
    }


    public void drawMainBox(float x, float y, Color c) {
        drawMainBox(x, y, c, BORDER_COLOR);
    }

    public void drawMainBox(float x, float y) {
        drawMainBox(x, y, MAIN_COLOR);
    }
}
