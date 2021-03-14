/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.utils;

public class MathUtils {
    private static final float[] SIN_TABLE = new float[65536];
    public static final double PI = Math.PI;

    static {
        for (int i = 0; i < 65536; ++i) {
            SIN_TABLE[i] = (float) Math.sin(i * Math.PI * 2.0D / 65536.0D);
        }
    }

    /**
     * sin looked up in a table
     */
    public static float sin(float v) {
        return (float) Math.sin(Math.toRadians(v));
        //return SIN_TABLE[(int) (Math.toRadians(v) * 10430.378F) & 65535];
    }

    /**
     * cos looked up in the sin table with the appropriate offset
     */
    public static float cos(float v) {
        return (float) Math.cos(Math.toRadians(v));
        //return SIN_TABLE[(int) (Math.toRadians(v) * 10430.378F + 16384.0F) & 65535];
    }

    /**
     * sin looked up in a table
     */
    public static float sinr(float v) {
        return SIN_TABLE[(int) (v * 10430.378F) & 65535];
    }

    /**
     * cos looked up in the sin table with the appropriate offset
     */
    public static float cosr(float v) {
        return SIN_TABLE[(int) (v * 10430.378F + 16384.0F) & 65535];
    }

    public static float wrapAngle180(double f) {
        return wrapAngle180((float) f);
    }

    public static float wrapAdd(float f, float wrap) {
        return wrap(f, wrap) + wrap / 2;
    }

    public static float wrap(float f, float wrap) {
        f %= wrap;
        if (f >= wrap / 2) {
            f -= wrap;
        }
        if (f < -wrap / 2) {
            f += wrap;
        }
        return f;
    }

    public static float wrapAngle180(float f) {
        f %= 360.0F;
        if (f >= 180.0F) {
            f -= 360.0F;
        }
        if (f < -180.0F) {
            f += 360.0F;
        }
        return f;
    }

    public static double roundInc(double val, double inc) {
        val = val - inc / 2;
        return Math.round(val * (1d / inc)) / (1d / inc);
    }

    public static float getAngleDifference(float a, float b) {
        return ((((a - b) % 360F) + 540F) % 360F) - 180F;
    }

    public static boolean approxEquals(double a, double b, double diff) {
        return Math.abs(a - b) < diff;
    }

    //Thx https://github.com/detel/bezier-curve/blob/master/bezeir-curves.cpp
    public static int factorial(int n) {
        if (n <= 1) return 1;
        else n *= factorial(n - 1);
        return n;
    }

    public static float binomialCoff(float n, float k) {
        float ans;
        ans = factorial((int) n) / (float) (factorial((int) k) * factorial((int) (n - k)));
        return ans;
    }

    public static Vec3f getBezier(Vec3f[] pts, double t) {
        Vec3f v = new Vec3f();
        v.x = (float) (Math.pow((1 - t), 3) * pts[0].x + 3 * t * Math.pow((1 - t), 2) * pts[1].x + 3 * (1 - t) * Math.pow(t, 2) * pts[2].x + Math.pow(t, 3) * pts[3].x);
        v.y = (float) (Math.pow((1 - t), 3) * pts[0].y + 3 * t * Math.pow((1 - t), 2) * pts[1].y + 3 * (1 - t) * Math.pow(t, 2) * pts[2].y + Math.pow(t, 3) * pts[3].y);

        return v;
    }

    public static Vec3f getBezierGeneralized(Vec3f[] pts, double t) {
        Vec3f v = new Vec3f();
        v.x = 0;
        v.y = 0;
        int clicks = pts.length;
        for (int i = 0; i < clicks; i++) {
            double pow = Math.pow((1 - t), (clicks - 1 - i));
            v.x = (float) (v.x + binomialCoff((float) (clicks - 1), (float) i) * Math.pow(t, i) * pow * pts[i].x);
            v.y = (float) (v.y + binomialCoff((float) (clicks - 1), (float) i) * Math.pow(t, i) * pow * pts[i].y);
        }
        return v;
    }

    public static Vec3f[] getBezierPoints(Vec3f[] pts) {
        Vec3f[] result = new Vec3f[50];
        for (int i = 0; i < 50; i++) result[i] = getBezierGeneralized(pts, i / 50F);
        return result;
    }
}
