/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.utils;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtils {
    //1.2 for radial gui (bc reasons)
    //250 for box
    public static float div = 250f;
//    public static float div = 1.2f;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public static void drawCircle(float x, float y, float r, float ir, float sta, float spa, float ext, Color color) {
        drawCircle(x, y, r, ir, sta, spa, ext, 5, color);
    }

    /**
     * @param x x
     * @param y y
     * @param r radius
     * @param ir inner radius
     * @param sta start angle
     * @param spa stop angle
     * @param ext extend (outer)
     * @param add amount to add for each turning thingy
     * @param color colour
     */
    public static void drawCircle(float x, float y, float r, float ir, float sta, float spa, float ext, float add, Color color) {
        x /= div;
        y /= div;
        r /= div;
        ir /= div;
        drawCircle0(x, y, r, ir, sta, spa, ext, add, color);
    }

    /**
     * @param x x
     * @param y y
     * @param r radius
     * @param ir inner radius
     * @param sta start angle
     * @param spa stop angle
     * @param ext extend (outer)
     * @param add amount to add for each turning thingy
     * @param color colour
     */
    private static void drawCircle0(float x, float y, float r, float ir, float sta, float spa, float ext, float add, Color color) {
        if (spa == 0 || sta == spa)
            return;
        glBegin(GL_QUADS);
        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        boolean it = true;
        for (float d = sta; d < spa; d += add) {
            glVertex2f(MathUtils.cos(d - ext) * r + x, MathUtils.sin(d - ext) * r + y);
            glVertex2f(MathUtils.cos(d) * ir + x, MathUtils.sin(d) * ir + y);
            if (d + add > spa) {
                float f = spa - d;
                glVertex2f(MathUtils.cos(d + f) * ir + x, MathUtils.sin(d + f) * ir + y);
                glVertex2f(MathUtils.cos(d + f + ext) * r + x, MathUtils.sin(d + f + ext) * r + y);
                it = false;
            } else {
                glVertex2f(MathUtils.cos(d + add) * ir + x, MathUtils.sin(d + add) * ir + y);
                glVertex2f(MathUtils.cos(d + add) * r + x, MathUtils.sin(d + add) * r + y);
            }
        }
        if (it) {
            glVertex2f(MathUtils.cos(spa - add + ext) * r + x, MathUtils.sin(spa - add + ext) * r + y);
            glVertex2f(MathUtils.cos(spa - add) * ir + x, MathUtils.sin(spa - add) * ir + y);
            glVertex2f(MathUtils.cos(spa) * ir + x, MathUtils.sin(spa) * ir + y);
            glVertex2f(MathUtils.cos(spa + ext) * r + x, MathUtils.sin(spa + ext) * r + y);
        }
        glEnd();
    }

    public static void drawBorderedRect(float x1, float y1, float x2, float y2, float edgeRadius, Color main, Color edgeColor) {
        x1 /= div;
        x2 /= div;
        y1 /= div;
        y2 /= div;
        edgeRadius /= div;
        drawRectUnDiv(x1, y1, x2, y2, edgeColor);
        drawRectUnDiv(x1, y1, x2, y2, edgeColor);
        glColor4f(main.getRed() / 255f, main.getGreen() / 255f, main.getBlue() / 255f, main.getAlpha() / 255f);
        drawRectUnDiv(x1 + edgeRadius, y1 + edgeRadius, x2 - edgeRadius, y2 - edgeRadius, main);
        drawRectUnDiv(x1 + edgeRadius, y1 + edgeRadius, x2 - edgeRadius, y2 - edgeRadius, main);
    }

    public static void drawRoundRect(float x1, float y1, float x2, float y2, float round, Color main) {
        if (Math.abs(x1 - x2) <= round || Math.abs(y1 - y2) <= round) return;
        x1 /= div;
        x2 /= div;
        y1 /= div;
        y2 /= div;
        round /= div;
        glColor4f(main.getRed() / 255f, main.getGreen() / 255f, main.getBlue() / 255f, main.getAlpha() / 255f);
        drawRectUnDiv(x1, y1 + round, x2, y2 - round, main);
        drawRectUnDiv(x1 + round, y1, x2 - round, y2, main);
        drawCircle0(x1 + round, y1 + round, round, 0, 180, 270, 0, 10, main);
        drawCircle0(x2 - round, y1 + round, round, 0, 270, 360, 0, 10, main);
        drawCircle0(x2 - round, y2 - round, round, 0, 0, 90, 0, 10, main);
        drawCircle0(x1 + round, y2 - round, round, 0, 90, 180, 0, 10, main);
    }

    public static void drawRoundBorderedRect(float x1, float y1, float x2, float y2, float round, float edgeRadius, Color main, Color edgeColor) {
        x1 /= div;
        x2 /= div;
        y1 /= div;
        y2 /= div;
        round /= div;
        edgeRadius /= div;
        drawRectUnDiv(x1, y1 + round, x2, y2 - round, edgeColor);
        drawRectUnDiv(x1 + round, y1, x2 - round, y2, edgeColor);
        drawCircle0(x1 + round, y1 + round, round, 0, 180, 270, 0, 10, edgeColor);
        drawCircle0(x2 - round, y1 + round, round, 0, 270, 360, 0, 10, edgeColor);
        drawCircle0(x2 - round, y2 - round, round, 0, 0, 90, 0, 10, edgeColor);
        drawCircle0(x1 + round, y2 - round, round, 0, 90, 180, 0, 10, edgeColor);
        glColor4f(main.getRed() / 255f, main.getGreen() / 255f, main.getBlue() / 255f, main.getAlpha() / 255f);
        drawRectUnDiv(x1 + edgeRadius, y1 + edgeRadius + round, x2 - edgeRadius, y2 - edgeRadius - round, main);
        drawRectUnDiv(x1 + edgeRadius + round, y1 + edgeRadius, x2 - edgeRadius - round, y2 - edgeRadius, main);
        drawCircle0(x1 + round + edgeRadius, y1 + round + edgeRadius, round, 0, 180, 270, 0, 10, main);
        drawCircle0(x2 - round - edgeRadius, y1 + round + edgeRadius, round, 0, 270, 360, 0, 10, main);
        drawCircle0(x2 - round - edgeRadius, y2 - round - edgeRadius, round, 0, 0, 90, 0, 10, main);
        drawCircle0(x1 + round + edgeRadius, y2 - round - edgeRadius, round, 0, 90, 180, 0, 10, main);
    }

    public static void drawRect(float x1, float y1, float x2, float y2, Color color) {
        x1 /= div;
        x2 /= div;
        y1 /= div;
        y2 /= div;
        drawRectUnDiv(x1, y1, x2, y2, color);
    }

    public static void drawRectUnDiv(float x1, float y1, float x2, float y2, Color color) {
        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        glBegin(GL_QUADS);
        glVertex2f(x1, y1);
        glVertex2f(x1, y2);
        glVertex2f(x2, y2);
        glVertex2f(x2, y1);
        glEnd();
    }

    public static void drawString(String text, float x, float y, float[] min, float[] max, float size, Color color) {
        x /= div;
        y /= div;
        min[0] /= div;
        min[1] /= div;
        max[0] /= div;
        max[1] /= div;
        drawString0(text, x, y, min, max, size, color);
    }

    public static void drawString(String text, float x, float y, float size, Color color) {
        x /= div;
        y /= div;
        drawString0(text, x, y, new float[]{-2, -2}, new float[]{2, 2}, size, color);
    }

    public static void drawString0(String text, float x, float y, float[] min, float[] max, float size, Color color) {
        Font font = new Font("Monospaced", Font.PLAIN, 1);
        GlyphVector vector = font.createGlyphVector(new FontRenderContext(new AffineTransform(), false, false), text);
        Rectangle2D rect = vector.getVisualBounds();
        y -= rect.getHeight() / 2 * size;
        x -= rect.getWidth() / 2 * size;
        Shape outline = vector.getOutline();
        PathIterator pathIterator = outline.getPathIterator(new AffineTransform());
        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        glBegin(GL_LINE_LOOP);
        float[] points = new float[6];
        while (!pathIterator.isDone()) {
            int code = pathIterator.currentSegment(points);
            switch (code) {
                case PathIterator.SEG_LINETO:
                case PathIterator.SEG_MOVETO: {
                    glVertex2f(Math.min(max[0], Math.max(min[0], points[0] * size + x)),
                      Math.min(max[1], Math.max(min[1], -points[1] * size + y)));
                    break;
                }
                case PathIterator.SEG_QUADTO: {
                    glVertex2f(Math.min(max[0], Math.max(min[0], points[0] * size + x)),
                      Math.min(max[1], Math.max(min[1], -points[1] * size + y)));
                    glVertex2f(Math.min(max[0], Math.max(min[0], points[2] * size + x)),
                      Math.min(max[1], Math.max(min[1], -points[3] * size + y)));
                    //glVertex2f(points[4] / max * size + x, -points[5] / max * size + y);
                    //eps.curveto(points[0] + x, points[1] + y, points[2] + x, points[3] + y, points[4] + x, points[5] + y);
                    break;
                }
                case PathIterator.SEG_CLOSE: {
                    glEnd();
                    glBegin(GL_LINE_LOOP);
                    break;
                }
                default:
                    System.out.println("wtf:" + code);
            }
            pathIterator.next();
        }
        glEnd();
    }
}
