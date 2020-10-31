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
import java.util.Objects;

//thx https://stackoverflow.com/questions/3018313/algorithm-to-convert-rgb-to-hsv-and-hsv-to-rgb-in-range-0-255-for-both
public class Colour {
    public static class RGB {
        public double r;       // a fraction between 0 and 1
        public double g;       // a fraction between 0 and 1
        public double b;       // a fraction between 0 and 1

        public RGB() {
        }

        public RGB(Color c) {
            this(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f);
        }

        public RGB(double r, double g, double b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public Color toColor() {
            try {
                return new Color((int) (r * 255), (int) (g * 255), (int) (b * 255));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage() + "; " + r + " " + g + " " + b);
            }
        }

        @Override
        public RGB clone() {
            return new RGB(r, g, b);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RGB rgb = (RGB) o;
            return MathUtils.approxEquals(rgb.r, r, 0.002) &&
              MathUtils.approxEquals(rgb.g, g, 0.002) &&
              MathUtils.approxEquals(rgb.b, b, 0.002);
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, g, b);
        }
    }

    public static class HSV {
        public double h;       // angle in degrees
        public double s;       // a fraction between 0 and 1
        public double v;       // a fraction between 0 and 1

        public HSV() {
        }

        public HSV(double h, double s, double v) {
            this.h = h;
            this.s = s;
            this.v = v;
        }

        public Color toColor() {
            return hsv2rgb(this).toColor();
        }

        @Override
        public HSV clone() {
            return new HSV(h, s, v);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HSV hsv = (HSV) o;
            return MathUtils.approxEquals(hsv.h, h, 1) &&
              MathUtils.approxEquals(hsv.s, s, 0.002) &&
              MathUtils.approxEquals(hsv.v, v, 0.002);
        }

        @Override
        public int hashCode() {
            return Objects.hash(h, s, v);
        }
    }

    public static HSV rgb2hsv(RGB in) {
        HSV out = new HSV();
        double min, max, delta;

        min = Math.min(in.r, Math.min(in.g, in.b));

        max = Math.max(in.r, Math.max(in.g, in.b));

        out.v = max;                                // v
        delta = max - min;
        if (delta < 0.00001) {
            out.s = 0;
            out.h = 0; // undefined, maybe nan?
            return out;
        }
        if (max > 0.0) { // NOTE: if Max is == 0, this divide would cause a crash
            out.s = (delta / max);                  // s
        } else {
            // if max is 0, then r = g = b = 0
            // s = 0, h is undefined
            out.s = 0.0;
            out.h = 0.0;                            // its now undefined
            return out;
        }
        if (in.r >= max)                           // > is bogus, just keeps compiler happy
            out.h = (in.g - in.b) / delta;        // between yellow & magenta
        else if (in.g >= max)
            out.h = 2.0 + (in.b - in.r) / delta;  // between cyan & yellow
        else
            out.h = 4.0 + (in.r - in.g) / delta;  // between magenta & cyan

        out.h *= 60.0;                              // degrees

        if (out.h < 0.0)
            out.h += 360.0;

        return out;
    }


    public static RGB hsv2rgb(HSV in) {
        double hh, p, q, t, ff;
        int i;
        RGB out = new RGB();

        if (in.s <= 0.0) {       // < is bogus, just shuts up warnings
            out.r = in.v;
            out.g = in.v;
            out.b = in.v;
            return out;
        }
        hh = in.h;
        if (hh >= 360.0) hh = 0.0;
        hh /= 60.0;
        i = (int) hh;
        ff = hh - i;
        p = in.v * (1.0 - in.s);
        q = in.v * (1.0 - (in.s * ff));
        t = in.v * (1.0 - (in.s * (1.0 - ff)));

        switch (i) {
            case 0:
                out.r = in.v;
                out.g = t;
                out.b = p;
                break;
            case 1:
                out.r = q;
                out.g = in.v;
                out.b = p;
                break;
            case 2:
                out.r = p;
                out.g = in.v;
                out.b = t;
                break;

            case 3:
                out.r = p;
                out.g = q;
                out.b = in.v;
                break;
            case 4:
                out.r = t;
                out.g = p;
                out.b = in.v;
                break;
            case 5:
            default:
                out.r = in.v;
                out.g = p;
                out.b = q;
                break;
        }
        return out;
    }
}
