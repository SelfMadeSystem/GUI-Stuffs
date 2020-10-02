package uwu.smsgamer.lwjgltest.gui.click.parts;

import uwu.smsgamer.lwjgltest.gui.click.*;
import uwu.smsgamer.lwjgltest.input.MouseHelper;
import uwu.smsgamer.lwjgltest.stuff.Stuff;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;
import java.util.*;

public class Category extends Part {

    public static final int[] mainSize = new int[]{150, 30};
    public static final int maxItems = 10; //includes top thingy
    public float x, y;
    public String name;
    public boolean clicked;
    public boolean opened;
    public Module[] modules;
    public float scroll;
    public int hoverModule;
    public float yAdd;
    public int moreTabs;
    public boolean justOpened = false;

    public Category(int at, String name) {
        this.x = -240;
        this.y = 240 - at * 40;
        this.name = name;
        ArrayList<String> stringList = new ArrayList<>(Stuff.values.get(name).keySet());
        Collections.sort(stringList);
        this.modules = new Module[stringList.size()];
        for (int i = 0; i < stringList.size(); i++) {
            String s = stringList.get(i);
            this.modules[i] = new Module(name, s, this);
        }
    }

    public int getMaxItems() {
        if (moreTabs < 0) moreTabs = 0;
        return Math.min(maxItems, modules.length + 1 + moreTabs);
    }

    @Override
    public void render() {
        if (clicked) {
            x -= MouseHelper.deltaX;
            y += MouseHelper.deltaY;
            if (x > (250 - mainSize[0]) || x < -250)
                y -= MouseHelper.deltaY / 2F;
            if (y > (250) || y < -250 + mainSize[0])
                x -= MouseHelper.deltaX / 2F;
            x = Math.min(250 - mainSize[0], Math.max(-250, x));
            y = Math.min(250, Math.max(-250 + mainSize[1], y));
        }
        hoverModule = -1;
        yAdd = 0;
        if (opened) {
            for (int i = 0; i < modules.length; i++) {
                float minY = Math.max(0, mainSize[1] * (i + 1) - scroll + yAdd);
                float maxY = Math.min(mainSize[1] * (i + 2) - scroll + yAdd, getMaxItems() * mainSize[1]);
                if (minY >= getMaxItems() * mainSize[1]) break;
                if (maxY <= 0) {
                    if (modules[i].open) {
                        modules[i].render(y - mainSize[1] * (i + 2) + scroll - yAdd);
                        yAdd += mainSize[1] * (modules[i].valStuff.length);
                    }
                    continue;
                }

                Module module = modules[i];
                boolean hovered = hover(0, mainSize[0], -maxY, -minY) && ClickGUIManager.getInstance().inputOverride == null;
                if (hovered) hoverModule = i;
                RenderUtils.drawBorderedRect(x, y - maxY, x + mainSize[0], y - minY,
                  1, hovered ? Color.LIGHT_GRAY : Color.GRAY, Color.RED); // TODO: 2020-09-29 CUSTOM COLOURS!!
                RenderUtils.drawString(module.name, x + mainSize[0] / 2f,
                  y - mainSize[1] * (i + 2) + (mainSize[1] / 2f) + scroll - yAdd,
                  new float[]{-250, y - maxY + 1}, new float[]{250, y - 1},
                  0.1f, Color.WHITE);
                if (modules[i].open) {
                    modules[i].render(y - mainSize[1] * (i + 2) + scroll - yAdd);
                    yAdd += mainSize[1] * (modules[i].valStuff.length);
                }
            }
        }
        RenderUtils.drawBorderedRect(x, y - mainSize[1], x + mainSize[0], y, 1,
          (hoveringTop() && !MouseHelper.left  && ClickGUIManager.getInstance().inputOverride == null) || clicked ? Color.GRAY : Color.DARK_GRAY, Color.RED);
        RenderUtils.drawString(name, x + mainSize[0] / 2f, y - (mainSize[1] / 2f), 0.1f, Color.WHITE);
    }

    public boolean hoveringTop() {
        return hover(0, mainSize[0], -mainSize[1], 0);
    }

    public boolean hoveringModules() {
        return opened && hover(0, mainSize[0], -mainSize[1] * getMaxItems(), -mainSize[1]);
    }

    public boolean hover(double minX, double maxX, double minY, double maxY) {
        return hoverRaw(this.x + minX, this.x + maxX, this.y + minY, this.y + maxY);
    }

    public boolean hoverRaw(double minX, double maxX, double minY, double maxY) {
        int mouseX = MouseHelper.posX - 250;
        int mouseY = -MouseHelper.posY + 250;
        return (mouseX > minX && mouseX < maxX) && (mouseY > minY && mouseY < maxY);
    }

    private long lastScroll = 0;

    @Override
    public void scroll(double amount) {
        super.scroll(amount);
        if (hoveringModules() && ClickGUIManager.getInstance().inputOverride == null) {
            scroll = (float) (scroll + amount * (230 + Math.max(-200, lastScroll - System.currentTimeMillis())) / 30);
            float limit = mainSize[1] * (modules.length - getMaxItems() + 1) + yAdd;
            if (scroll > limit) scroll = limit;
            else if (scroll < 0) scroll = 0;
            lastScroll = System.currentTimeMillis();
        }
    }


    @Override
    public void click(int button) {
        super.click(button);
        if (justOpened) justOpened = false;
        if (ClickGUIManager.getInstance().inputOverride == null) {
            if (button == 0) {
                if (clicked = hoveringTop()) {
                    ClickGUIManager.getInstance().getCategories().remove(this);
                    ClickGUIManager.getInstance().getCategories().add(this);
                }
            } else if (button == 1) {
                if (hoveringTop()) {
                    if (opened) {
                        for (Module module : modules) module.close();
                        scroll = 0;
                    } else justOpened = true;
                    opened = !opened;
                } else if (hoverModule != -1) {
                    modules[hoverModule].toggle();
                }
            }
        }
        if (opened && !justOpened) {
            if (ClickGUIManager.getInstance().inputOverride == null)
                for (Module module : modules) {
                    module.click(button);
                }
            else ClickGUIManager.getInstance().inputOverride.module.click(button);
        }
    }

    @Override
    public void unclick(int button) {
        super.unclick(button);
        if (button == 0) {
            clicked = false;
        }
        if (!justOpened && opened)
            if (ClickGUIManager.getInstance().inputOverride == null)
                for (Module module : modules) {
                    module.unclick(button);
                }
            else ClickGUIManager.getInstance().inputOverride.module.unclick(button);
    }

    @Override
    public void charKey(char c) {
        super.charKey(c);
        if (opened) {
            if (ClickGUIManager.getInstance().inputOverride == null)
                for (Module module : modules) {
                    module.charKey(c);
                }
            else ClickGUIManager.getInstance().inputOverride.module.charKey(c);
        }
    }

    @Override
    public void key(int key) {
        super.key(key);
        if (opened) {
            if (ClickGUIManager.getInstance().inputOverride == null)
                for (Module module : modules) {
                    module.key(key);
                }
            else ClickGUIManager.getInstance().inputOverride.module.key(key);
        }
    }
}
