/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.stuff;

import java.awt.*;
import java.util.HashMap;

public class Stuff {
    public static HashMap<String, HashMap<String, ValStuff[]>> values = new HashMap<>();

    static {
        HashMap<String, ValStuff[]> map = new HashMap<>();
        map.put("b", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        map.put("a", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        map.put("c", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        HashMap<String, ValStuff[]> mapA = new HashMap<>();
        mapA.put("b", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        mapA.put("a", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        mapA.put("c", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        HashMap<String, ValStuff[]> mapB = new HashMap<>();
        mapB.put("b", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        mapB.put("a", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        mapB.put("c", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        HashMap<String, ValStuff[]> mapC = new HashMap<>();
        mapC.put("b", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        mapC.put("a", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        mapC.put("c", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        HashMap<String, ValStuff[]> mapD = new HashMap<>();
        mapD.put("b", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        mapD.put("a", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        mapD.put("c", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        HashMap<String, ValStuff[]> map1 = new HashMap<>(); //doing this shit bc shallow cloning for maps only :c
        map1.put("a", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        map1.put("c", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        map1.put("b", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        map1.put("e", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5),
          new ValStuff(ValStuff.Type.STRING, "str", "OwO UwU")});
        HashMap<String, ValStuff[]> map2 = new HashMap<>();
        map2.put("a", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        map2.put("b", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        map2.put("c", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5)});
        map2.put("d", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", -6, -7, -4, 0.5)});
        map2.put("e", new ValStuff[]{new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.NUMBER, "num", -6, -7, -4, 0.5)});
        map2.put("f", new ValStuff[]{new ValStuff(ValStuff.Type.COLOUR, "colour", new Color(200, 30, 120)),
          new ValStuff(ValStuff.Type.NUMBER, "num", 3, 2, 5, 0.5),
          new ValStuff(ValStuff.Type.BOOLEAN, "bool", false),
          new ValStuff(ValStuff.Type.VALUES, "more0",
            new ValStuff(ValStuff.Type.BOOLEAN, "cool0", false),
            new ValStuff(ValStuff.Type.NUMBER, "wow0", 0.5, 0, 1, 0.001),
            new ValStuff(ValStuff.Type.STRING, "amazing0", "OwO UwU")),
          new ValStuff(ValStuff.Type.STRING, "str", "OwO UwU"),
          new ValStuff(ValStuff.Type.CHOICE, "choice", "w0t", "w0t", "ok", "noice", "wow"),
          new ValStuff(ValStuff.Type.VALUES, "more",
            new ValStuff(ValStuff.Type.BOOLEAN, "cool", false),
            new ValStuff(ValStuff.Type.NUMBER, "wow", 0.5, 0, 1, 0.2),
            new ValStuff(ValStuff.Type.VALUES, "moreA",
              new ValStuff(ValStuff.Type.BOOLEAN, "coolA", false),
              new ValStuff(ValStuff.Type.NUMBER, "wowA", 0.5, 0, 1, 0.001),
              new ValStuff(ValStuff.Type.STRING, "amazingA", "OwO UwU")),
            new ValStuff(ValStuff.Type.STRING, "amazing", "OwO UwU"),
            new ValStuff(ValStuff.Type.VALUES, "more1",
              new ValStuff(ValStuff.Type.BOOLEAN, "cool1", false),
              new ValStuff(ValStuff.Type.NUMBER, "wow1", 0.5, 0, 1, 0.001),
              new ValStuff(ValStuff.Type.STRING, "amazing1", "OwO UwU")))});
        values.put("One", map);
        values.put("OneA", mapA);
        values.put("OneB", mapB);
        values.put("OneC", mapC);
        values.put("OneD", mapD);
        values.put("Two", map1);
        values.put("Three", map2);
    }
}
