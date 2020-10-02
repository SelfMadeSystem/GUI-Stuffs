package uwu.smsgamer.lwjgltest.gui.radial;

import uwu.smsgamer.lwjgltest.input.MouseHelper;

import java.awt.*;

import static uwu.smsgamer.lwjgltest.utils.RenderUtils.drawCircle;

public abstract class Ring {
    protected static Ring currentRing;
    public static Ring previousRing;

    public static Ring getCurrentRing() {
        if (currentRing == null) currentRing = new CategoriesRing(null);
        return currentRing;
    }

    public static void newRing(Ring ring) {
        previousRing = currentRing;
        currentRing = ring;
        Ring.lastSwitch = 1;
    }

    public static void prevRing() {
        if (currentRing.prevRing != null) {
            previousRing = currentRing;
            currentRing = currentRing.prevRing;
            currentRing.refresh();
            Ring.lastSwitch = 1;
        }
    }

    public Ring prevRing;

    public Ring(Ring prevRing) {
        this.mouseDown = MouseHelper.left;
        this.justOn = true;
        this.prevRing = prevRing;
    }

    //settings
    protected static float switchSpeed = 1000;

    //main
    protected static Color mainColor = Color.white;
    protected static Color secondColor = Color.gray;
    protected static Color secondColorHighlight = new Color(98, 98, 98);

    //choice
    protected static Color selectedColor = new Color(100, 100, 128);
    protected static Color selectedColorHighlight = new Color(85, 85, 128);

    //slider
    protected static Color sliderColor = Color.cyan;
    protected static Color sliderColorHover = new Color(0, 225, 225);
    protected static Color sliderHintAfter = new Color(200, 255, 255);
    protected static Color sliderHintBefore = new Color(0, 225, 225);

    //toggle
    protected static Color onColor = new Color(0, 180, 0);
    protected static Color offColor = new Color(180, 0, 0);
    protected static Color onColorH = new Color(0, 230, 0);//hover
    protected static Color offColorH = new Color(230, 0, 0);
    protected static Color onColorC = new Color(0, 160, 0);//click
    protected static Color offColorC = new Color(160, 0, 0);

    double dist;
    double rot;
    //0 center, 1 over ring, 2 above ring
    byte over;
    boolean mouseDown;
    boolean justOn;
    long mouseDownTime;

    public static long lastRender;
    public static long lastSwitch;

    public abstract void renderStuff();

    protected Color center = Color.DARK_GRAY;

    public final void render() {
        /*if (RenderUtils.opacity < 1) RenderUtils.opacity += 0.02 * (System.currentTimeMillis() - lastRender);
        if (RenderUtils.opacity > 1) RenderUtils.opacity = 1;*/
        drawCircle(0, 0, 1f, 0, 0, 360, 0f, center);
        drawCircle(0, 0, 1f, 0.75f, 0, 360, 0f, mainColor);
        //drawCircle(0, 0, 1f, 0.75f, 180, 180 + 170, 1.3f, color);
        dist = Math.sqrt((MouseHelper.posX - 250) * (MouseHelper.posX - 250) +
          (MouseHelper.posY - 250) * (MouseHelper.posY - 250)) * 1.2;
        if (dist > 187.5) {
            over = (byte) (dist < 250 ? 1 : 2);
        } else over = 0;
        if (!mouseDown && MouseHelper.left) {
            mouseDownTime = 0;
            justOn = false;
            click();
        }
        if (mouseDown && MouseHelper.left) mouseDownTime += System.currentTimeMillis() - lastRender;
        if (mouseDown && !justOn && !MouseHelper.left) unClick();
        mouseDown = MouseHelper.left;
        rot = Math.atan2(MouseHelper.posY - 250, 250 - MouseHelper.posX) * 180 / Math.PI + 180;
        renderStuff();
        lastRender = System.currentTimeMillis();
    }

    public void click() {
        if (over == 0)
            Ring.prevRing();
    }

    public void unClick() {
    }

    public void charKey(char c) {
    }

    public abstract void refresh();

    public void key(int key) {
    }
}
