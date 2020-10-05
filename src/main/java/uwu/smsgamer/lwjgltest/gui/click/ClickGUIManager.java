/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.gui.click;

import lombok.Getter;
import uwu.smsgamer.lwjgltest.gui.click.parts.*;
import uwu.smsgamer.lwjgltest.input.MouseHelper;
import uwu.smsgamer.lwjgltest.stuff.*;

import java.util.*;

import static uwu.smsgamer.lwjgltest.gui.click.Part.*;

public class ClickGUIManager {
    private static ClickGUIManager instance;
    public ValStuff[] colours = new ValStuff[]{
      new ValStuff(ValStuff.Type.VALUES, "Top",
        new ValStuff(ValStuff.Type.COLOUR, "COLOR", TOP_COLOR),
        new ValStuff(ValStuff.Type.COLOUR, "COLOR_HOVER", TOP_COLOR_HOVER),
        new ValStuff(ValStuff.Type.COLOUR, "BORDER_COLOR", TOP_BORDER_COLOR))};
    public ValStuff[] size = new ValStuff[]{
      new ValStuff(ValStuff.Type.VALUES, "TopSize",
        new ValStuff(ValStuff.Type.NUMBER, "X", 160, 0, 300, 1),
        new ValStuff(ValStuff.Type.NUMBER, "Y", 20, 0, 300, 1)),
      new ValStuff(ValStuff.Type.VALUES, "MainSize",
        new ValStuff(ValStuff.Type.NUMBER, "X", 150, 30, 300, 1),
        new ValStuff(ValStuff.Type.NUMBER, "Y", 30, 10, 300, 1)),
      new ValStuff(ValStuff.Type.NUMBER, "MaxLength", 300, 50, 600, 1)};

    public static ClickGUIManager getInstance() {
        if (instance == null) instance = new ClickGUIManager();
        return instance;
    }

    public ClickGUIManager() {
        String[] strings = Stuff.values.keySet().toArray(new String[0]);
        categories = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            categories.add(new CategoryPart(i, s));
        }
        CategoryPart settings;
        categories.add(settings = new CategoryPart("Settings", categories.size()));
        settings.modules = new ModulePart[]{new ModulePart("colour", settings, colours),
          new ModulePart("size", settings, size)};
    }

    @Getter
    private final List<CategoryPart> categories;
    private final boolean[] click = new boolean[3];
    public ValPart inputOverride;

    public void render() {
        setSettings();
        for (CategoryPart c : new ArrayList<>(categories)) {
            c.render();
            if (inputOverride == null || inputOverride.category.equals(c)) {
                if (click[0] && !MouseHelper.left) c.unclick(0);
                else if (!click[0] && MouseHelper.left) c.click(0);
                if (click[1] && !MouseHelper.right) c.unclick(1);
                else if (!click[1] && MouseHelper.right) c.click(1);
                if (click[2] && !MouseHelper.middle) c.unclick(2);
                else if (!click[2] && MouseHelper.middle) c.click(2);
            }
        }
        click[0] = MouseHelper.left;
        click[1] = MouseHelper.right;
        click[2] = MouseHelper.middle;
    }

    public void setSettings() {
        Part.topSize[0] = ((Double) size[0].values[0].value).intValue();
        Part.topSize[1] = ((Double) size[0].values[1].value).intValue();
        Part.mainSize[0] = ((Double) size[1].values[0].value).intValue();
        Part.mainSize[1] = ((Double) size[1].values[1].value).intValue();
        Part.maxLength = ((Double) size[2].value).intValue();
    }

    public void scroll(double amount) {
        for (CategoryPart c : new ArrayList<>(categories)) {
            if (inputOverride == null || inputOverride.category.equals(c)) {
                c.scroll(amount);
            }
        }
    }

    public void charKey(char c) {
        if (inputOverride == null) {
            for (CategoryPart cat /*𝑚𝑒𝑜𝑤*/: new ArrayList<>(categories))
                cat.charKey(c);
        } else inputOverride.category.charKey(c);
    }

    public void key(int key) {
        if (inputOverride == null) {
            for (CategoryPart c : new ArrayList<>(categories))
                c.key(key);
        } else inputOverride.category.key(key);
    }
}
