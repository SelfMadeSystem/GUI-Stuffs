package uwu.smsgamer.lwjgltest.gui.click;

import lombok.Getter;
import uwu.smsgamer.lwjgltest.gui.click.parts.Category;
import uwu.smsgamer.lwjgltest.gui.click.parts.buttons.EditPart;
import uwu.smsgamer.lwjgltest.input.MouseHelper;
import uwu.smsgamer.lwjgltest.stuff.Stuff;

import java.util.*;

public class ClickGUIManager {
    private static ClickGUIManager instance;

    public static ClickGUIManager getInstance() {
        if (instance == null) instance = new ClickGUIManager();
        return instance;
    }

    public ClickGUIManager() {
        String[] strings = Stuff.values.keySet().toArray(new String[0]);
        categories = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            categories.add(new Category(i, s));
        }
    }

    @Getter
    private final List<Category> categories;
    private final boolean[] click = new boolean[3];
    public EditPart inputOverride;

    public void render() {
        for (Category c : new ArrayList<>(categories)) {
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

    public void scroll(double amount) {
        for (Category c : new ArrayList<>(categories)) {
            if (inputOverride == null || inputOverride.category.equals(c)) {
                c.scroll(amount);
            }
        }
    }

    public void charKey(char c) {
        if (inputOverride == null) {
            for (Category cat /* *𝑚𝑒𝑜𝑤* */: new ArrayList<>(categories))
                cat.charKey(c);
        } else inputOverride.category.charKey(c);
    }

    public void key(int key) {
        if (inputOverride == null) {
            for (Category c : new ArrayList<>(categories))
                c.key(key);
        } else inputOverride.category.key(key);
    }
}
