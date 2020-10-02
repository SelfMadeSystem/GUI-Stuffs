package uwu.smsgamer.lwjgltest.gui.block.blocks;

import org.lwjgl.opengl.GL11;
import uwu.smsgamer.lwjgltest.input.MouseHelper;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

public abstract class SubBlock extends Block {
    public String name;

    public SubBlock(Block parent, float width, float height, float x, float y, String name) {
        super(parent, width, height, x, y);
        this.name = name;
        this.y = -topHeight/2f;
    }

    protected boolean overClose = false;

    protected void drawTop(float height) {
        int mouseX = MouseHelper.posX - 250;
        int mouseY = -MouseHelper.posY + 250;
        float minX = x - width / 2;
        float maxX = x + width / 2;
        float minY = y + height;
        float maxY = y + height + topHeight;
        RenderUtils.drawRect(minX, minY, maxX, maxY, TOP_COLOR);

        RenderUtils.drawString(name, minX + (maxX - minX) / 2, minY + (maxY - minY) / 2, 0.04f, Color.BLACK);
        float w = topHeight - 4;
        minX = maxX - w;
        maxX = maxX - 2;
        minY = maxY - w;
        maxY = maxY - 2;
        overClose = (mouseX > minX - 2 && mouseX < maxX - 2 && mouseY > minY - 2 && mouseY < maxY - 2);
        Color c = overClose && this.child == null ? new Color(255, 100, 0) : Color.RED;
        RenderUtils.drawRect(minX, minY, maxX, maxY, c);

        GL11.glBegin(GL11.GL_LINES);
        GL11.glColor4f(0, 0, 0, 0);
        GL11.glVertex2d((maxX-1)/250, (maxY-1)/250);
        GL11.glVertex2d((minX-1)/250, (minY-1)/250);
        GL11.glVertex2d((maxX-1)/250, (minY-1)/250);
        GL11.glVertex2d((minX-1)/250, (maxY-1)/250);
        GL11.glEnd();
    }

    @Override
    public void click() {
        if (overClose && this.child == null) parent.close();
    }
}
