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

package uwu.smsgamer.lwjgltest.tests;

import uwu.smsgamer.lwjgltest.utils.*;

import java.awt.*;

public class BezierCurves {
    // Thx https://github.com/detel/bezier-curve/blob/master/bezeir-curves.cpp
    public static void render(Vec3f[] pts) {
        // Drawing the control lines
        for (int k = 0; k < pts.length - 1; k++)
            RenderUtils.drawLine(pts[k], pts[k + 1], Color.RED);

//        Vec3f p1 = new Vec3f();
//        /* Draw each segment of the curve.Make t increment in smaller amounts for a more detailed curve.*/
//        for (double t = 0.0; t <= 1.02; t += 0.02) {
//            Vec3f p2 = MathUtils.getBezierGeneralized(pts, t);
//            if (t != 0) RenderUtils.drawLine(p1, p2, Color.WHITE);
//            p1 = p2;
//        }
        Vec3f[] bezierPoints = MathUtils.getBezierPoints(pts);
        Vec3f prevPoint = bezierPoints[0];
        for (int i = 1; i < bezierPoints.length; i++) {
            Vec3f point = bezierPoints[i];
            RenderUtils.drawLine(point, prevPoint, Color.WHITE);
            prevPoint = point;
        }
    }
}
