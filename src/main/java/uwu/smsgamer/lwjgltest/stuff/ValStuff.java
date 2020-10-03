/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.stuff;

public class ValStuff {
    public enum Type {
        BOOLEAN,
        STRING,
        CHOICE,
        NUMBER,
        COLOUR,
        VALUES
    }

    public Type type;
    public String name;
    public Object value;
    public ValStuff[] values;
    public String[] choices;
    public double min;
    public double max;
    public double step;

    public ValStuff(Type type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public ValStuff(Type type, String name, Object value, String... choices) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.choices = choices;
    }

    public ValStuff(Type type, String name, Object value, double min, double max, double step) {
        this.type = type;
        this.name = name;
        if (value.getClass().equals(Integer.class))
            value = ((Integer) value).doubleValue();
        this.value = value;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public ValStuff(Type type, String name, ValStuff... values) {
        this.type = type;
        this.name = name;
        this.values = values;
        this.value = "more options...";
    }
}
