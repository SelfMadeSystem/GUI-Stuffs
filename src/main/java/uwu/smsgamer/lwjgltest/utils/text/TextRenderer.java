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

package uwu.smsgamer.lwjgltest.utils.text;

import uwu.smsgamer.lwjgltest.utils.*;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;

import static org.lwjgl.opengl.GL11.*;

public class TextRenderer {
    Colour.HSV hsv = new Colour.HSV(0, 1, 1);
    private Font font;
    private boolean isAntiAliased = false;
    private boolean usesFractionalMetrics = true;
    private int verticalAnchor = -1;
    private int horizontalAnchor = -1;
    private float mSize;
    private boolean debug_layers = false;
    private boolean debug_no_render = false;
    private float xOffset = 0;
    private float yOffset = 0;

    public TextRenderer(float mSize) {
        this.mSize = mSize;
    }

    public TextRenderer(String fontName, float mSize) {
        this(fontName, mSize, 0);
    }

    public TextRenderer(String fontName, float mSize, int style) {
        this(fontName, mSize, style, 1);
    }

    public TextRenderer(String fontName, float mSize, int style, int size) {
        this(mSize);
        setFont(fontName, style, size);
    }

    public TextRenderer(Font font, float mSize) {
        this(mSize);
        setFont(font);
    }

    public TextRenderer setFontStyle(int style) {
        return this.setFont(new Font(font.getName(), style, font.getSize()));
    }

    public TextRenderer setFontName(String fontName) {
        return this.setFont(new Font(fontName, font.getStyle(), font.getSize()));
    }

    public TextRenderer setFont(String fontName, int style, int size) {
        return this.setFont(new Font(fontName, style, size));
    }

    public TextRenderer setFont(Font font) {
        this.font = font;
        return this;
    }

    public boolean isAntiAliased() {
        return isAntiAliased;
    }

    public TextRenderer setAntiAliased(boolean antiAliased) {
        isAntiAliased = antiAliased;
        return this;
    }

    public boolean isUsesFractionalMetrics() {
        return usesFractionalMetrics;
    }

    public TextRenderer setUsesFractionalMetrics(boolean usesFractionalMetrics) {
        this.usesFractionalMetrics = usesFractionalMetrics;
        return this;
    }

    public int getVerticalAnchor() {
        return verticalAnchor;
    }

    public TextRenderer setVerticalAnchor(int verticalAnchor) {
        this.verticalAnchor = verticalAnchor;
        return this;
    }

    public int getHorizontalAnchor() {
        return horizontalAnchor;
    }

    public TextRenderer setHorizontalAnchor(int horizontalAnchor) {
        this.horizontalAnchor = horizontalAnchor;
        return this;
    }

    public float getmSize() {
        return mSize;
    }

    public TextRenderer setmSize(float mSize) {
        this.mSize = mSize;
        return this;
    }

    public boolean isDebug_layers() {
        return debug_layers;
    }

    public TextRenderer setDebug_layers(boolean debug_layers) {
        this.debug_layers = debug_layers;
        return this;
    }

    public boolean isDebug_no_render() {
        return debug_no_render;
    }

    public TextRenderer setDebug_no_render(boolean debug_no_render) {
        this.debug_no_render = debug_no_render;
        return this;
    }

    public void drawString(String text, float x, float y, Color color) {
        hsv = new Colour.HSV(0, 1, 1);
        xOffset = x;
        yOffset = y;
        startStencil();
        drawStencils(getPathIterator(text));
        endStencil();
        if (!debug_no_render) paintAllCanvas(color);
        endDrawing();
    }

    private void setColor() {
        Color color = hsv.toColor();
        glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 0.25f);
        hsv.h += 10;
        if (hsv.h > 360) hsv.h = 0;
    }

    private void startStencil() {
        glEnable(GL_STENCIL_TEST);
        glClearStencil(0);
        glClear(GL_STENCIL_BUFFER_BIT);

        if (!debug_layers) glColorMask(false, false, false, false);
        glEnable(GL_STENCIL_TEST);
        glStencilFunc(GL_ALWAYS, 1, 0xFF);
        glStencilMask(0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_INCR);
    }


    private void endStencil() {
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
        glColorMask(true, true, true, true);
    }

    private void endDrawing() {
        glDisable(GL_STENCIL_TEST);
    }

    private PathIterator getPathIterator(String text) {
        GlyphVector vector = font.createGlyphVector(new FontRenderContext(new AffineTransform(), true, true), text);
        Rectangle2D rect = vector.getVisualBounds();
        setOffsets(rect);
        Shape outline = vector.getOutline();
        return outline.getPathIterator(new AffineTransform());
    }

    private void setOffsets(Rectangle2D rect) {
        switch (horizontalAnchor) {
            case 0:
                xOffset -= rect.getWidth() * mSize / 2;
                break;
            case 1:
                xOffset -= rect.getWidth() * mSize;
        }
        switch (verticalAnchor) {
            case 0:
                yOffset -= rect.getHeight() * mSize / 2;
                break;
            case 1:
                yOffset -= rect.getHeight() * mSize;
        }
    }

    private void drawStencils(PathIterator pathIterator) {
        setColor();
        glBegin(GL_POLYGON);
        float[] points = new float[8];
        Vec3f current = new Vec3f();
        while (!pathIterator.isDone()) {
            int code = pathIterator.currentSegment(points);
            switch (code) {
                case PathIterator.SEG_MOVETO:
                    glEnd();
                    setColor();
                    glBegin(GL_POLYGON);
                case PathIterator.SEG_LINETO: {
                    glVertex2f(points[0] * mSize + xOffset, -points[1] * mSize + yOffset);
                    current = new Vec3f(points[0] * mSize + xOffset, -points[1] * mSize + yOffset);
                    break;
                }
                case PathIterator.SEG_QUADTO: {
                    RenderUtils.bezierPoses(new Vec3f[]{current, new Vec3f(points[0] * mSize + xOffset, -points[1] * mSize + yOffset),
                      new Vec3f(points[2] * mSize + xOffset, -points[3] * mSize + yOffset)});
                    current = new Vec3f(points[2] * mSize + xOffset, -points[3] * mSize + yOffset);
                    break;
                }
                case PathIterator.SEG_CUBICTO: {
                    RenderUtils.bezierPoses(new Vec3f[]{current, new Vec3f(points[0] * mSize + xOffset, -points[1] * mSize + yOffset),
                      new Vec3f(points[2] * mSize + xOffset, -points[3] * mSize + yOffset),
                      new Vec3f(points[4] * mSize + xOffset, -points[5] * mSize + yOffset)});
                    current = new Vec3f(points[4] * mSize + xOffset, -points[5] * mSize + yOffset);
                    break;
                }
                case PathIterator.SEG_CLOSE: {
                    glEnd();
                    setColor();
                    glBegin(GL_POLYGON);
                    current = null;
                    break;
                }
                default:
                    System.out.println("wtf:" + code);
            }
            pathIterator.next();
        }
        glEnd();
    }

    // Pls hmu if u know any better way of doing this.
    private void paintAllCanvas(Color color) {
        for (int i = 0; i < 500; i++) {
            glStencilFunc(GL_EQUAL, i * 2 + 1, 0xFF);

            RenderUtils.drawRectUnDiv(-1, -1, 1, 1, color);
        }
    }
}
