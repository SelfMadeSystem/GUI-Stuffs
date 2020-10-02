package uwu.smsgamer.lwjgltest.gui.block.blocks;

import uwu.smsgamer.lwjgltest.input.MouseHelper;

import java.awt.*;

public abstract class Block {
    protected static Color MAIN_COLOR = Color.WHITE;
    protected static Color HOVER_COLOR = Color.LIGHT_GRAY;
    protected static Color SELECTED_COLOR = Color.DARK_GRAY;
    protected static Color BORDERED_COLOR = Color.GRAY;
    //top
    protected static Color TOP_COLOR = Color.DARK_GRAY;
    //slider
    protected static Color SLIDER_COLOR = Color.cyan;
    protected static Color SLIDER_COLOR_HOVER = new Color(0, 225, 225);
    protected static Color SLIDER_HINT_AFTER = new Color(200, 255, 255);
    protected static Color SLIDER_HINT_BEFORE = new Color(0, 225, 225);
    //toggle
    protected static Color ON_COLOR = new Color(0, 180, 0);
    protected static Color OFF_COLOR = new Color(180, 0, 0);
    protected static Color ON_COLOR_HOVER = new Color(0, 230, 0);//hover
    protected static Color OFF_COLOR_HOVER = new Color(230, 0, 0);
    protected static Color ON_COLOR_CLICK = new Color(0, 160, 0);//click
    protected static Color OFF_COLOR_CLICK = new Color(160, 0, 0);

    protected static float blockWidth = 200, blockHeight = 100;
    protected static int topHeight = 40;
    protected static float edgeRadius = 10, edgeRound = 5;

    public Block parent;
    public Block child;
    public float width;
    public float height;
    public float x;
    public float y;

    public Block(Block parent, float width, float height, float x, float y) {
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public int children() {
        Block block = child;
        int i = 0;
        while (block != null) {
            i++;
            block = block.child;
        }
        return i;
    }

    public abstract void render();

    public abstract void click();

    public void unclick() {
    }

    public abstract void close();

    public void charKey(char c) {
    }

    public void key(int key) {
    }

    public boolean isHovering(double minX, double maxX, double minY, double maxY) {
        return isHoveringX(minX, maxX) && isHoveringY(minY, maxY);
    }

    public boolean isHoveringX(double minX, double maxX) {
        int mouseX = MouseHelper.posX - 250;
        return (mouseX > this.x + minX && mouseX < this.x + maxX);
    }

    public boolean isHoveringY(double minY, double maxY) {
        int mouseY = -MouseHelper.posY + 250;
        return (mouseY > this.y + minY && mouseY < this.y + maxY);
    }

    public boolean isHoveringRaw(double minX, double maxX, double minY, double maxY) {
        return isHoveringXRaw(minX, maxX) && isHoveringYRaw(minY, maxY);
    }

    public boolean isHoveringXRaw(double minX, double maxX) {
        int mouseX = MouseHelper.posX - 250;
        return (mouseX > minX && mouseX < maxX);
    }

    public boolean isHoveringYRaw(double minY, double maxY) {
        int mouseY = -MouseHelper.posY + 250;
        return (mouseY > minY && mouseY < maxY);
    }

    //-1 left/down : 0 on : 1 right/up
    public byte[] getHoverType(double minX, double maxX, double minY, double maxY) {
        int mouseX = MouseHelper.posX - 250;
        int mouseY = -MouseHelper.posY + 250;
        return new byte[]{(byte) (mouseX > this.x + minX ?
          (mouseX < this.x + maxX ? 0 : 1) : -1),
          (byte) (mouseY > this.y + minY ?
            (mouseY < this.y + maxY ? 0 : 1) : -1)};
    }

    public double getRangeX(double minX, double maxX) {
        int mouseX = MouseHelper.posX - 250;
        double delta = maxX - minX;
        return Math.min(1, Math.max(0, (mouseX - (minX + this.x)) / delta));
    }

    public double getRangeY(double minY, double maxY) {
        int mouseY = MouseHelper.posY - 250;
        double delta = maxY - minY;
        return Math.min(1, Math.max(0, (mouseY - (minY - this.y)) / delta));
    }
}
