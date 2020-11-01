/*----------------------------------------------------*\
|                                                      |
|    ///////////////////////\\\\\\\\\\\\\\\\\\\\\\\    |
|   //      Copyright (c) 2020 Shoghi Simon       \\   |
|   \\   License: GNU GENERAL PUBLIC LICENSE V3   //   |
|    \\\\\\\\\\\\\\\\\\\\\\\///////////////////////    |
|                                                      |
\*----------------------------------------------------*/
package uwu.smsgamer.lwjgltest.render;

import lombok.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import uwu.smsgamer.lwjgltest.Main;
import uwu.smsgamer.lwjgltest.gui.block.BlockManager;
import uwu.smsgamer.lwjgltest.gui.click.ClickGUIManager;
import uwu.smsgamer.lwjgltest.gui.colour.ColourManager;
import uwu.smsgamer.lwjgltest.gui.psnf.PSNFManager;
import uwu.smsgamer.lwjgltest.gui.radial.Ring;
import uwu.smsgamer.lwjgltest.input.*;
import uwu.smsgamer.lwjgltest.utils.RenderUtils;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

@Getter
@Setter
public class Window {
    private int width, height;
    private String title;
    private long window;
    public Color bgColor;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.bgColor = new Color(0, 0, 0, 0);
    }

    public void start() {
        if (!glfwInit()) {
            throw new InternalError("Could not load GLFW");
        }
        glfwWindowHint(GLFW_STENCIL_BITS, 8);
        glfwWindowHint(GLFW_SAMPLES, 8);
        glfwWindowHint(GLFW_ALPHA_BITS, 256);
        if ((window = glfwCreateWindow(width, height, title, 0, 0)) == 0) {
            throw new InternalError("Could not create window");
        }
        glfwSetCharCallback(window, new InputManager.CharThingy());
        glfwSetKeyCallback(window, new InputManager.KeyThingy());
        glfwSetWindowAttrib(window, GLFW_RESIZABLE, 0);
        glfwSetWindowAttrib(window, GLFW_TRANSPARENT_FRAMEBUFFER, 1);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert vidMode != null;
        glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        glfwShowWindow(window);
        glClear(GL_COLOR_BUFFER_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glfwWindowHint(GLFW_SAMPLES, 4);
        loop();
    }

    long last = System.nanoTime();

    private void loop() {
        glfwSetScrollCallback(window, new MouseHelper.ScrollCallBack());
        while (!glfwWindowShouldClose(window)) {
            //GL11.glClearColor(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), bgColor.getAlpha());
            //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            Main.deltaTime = System.nanoTime() - last;
            last = System.nanoTime();
            glClearColor(bgColor.getRed() / 255f, bgColor.getGreen() / 255f, bgColor.getBlue() / 255f, bgColor.getAlpha() / 255f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glEnable(GL_BLEND);
            glClearColor(bgColor.getRed() / 255f, bgColor.getGreen() / 255f, bgColor.getBlue() / 255f, bgColor.getAlpha() / 255f);
            render();
            glfwSwapBuffers(window);
            glfwPollEvents();
            InputManager.update();
        }
        glfwTerminate();
    }

    private void render() {
        renderPSNF();
    }

    private void renderColour() {
        RenderUtils.div = 250f;
        ColourManager.getInstance().render();
    }

    private void renderPSNF() {
        RenderUtils.div = 250f;
        PSNFManager.getInstance().render();
    }

    private void renderClick() {
        RenderUtils.div = 250f;
        ClickGUIManager.getInstance().render();
    }

    private void renderBlock() {
        RenderUtils.div = 250f;
        BlockManager.getInstance().render();
    }

    private void renderRing() {
        RenderUtils.div = 1.2f;
        Ring.lastSwitch += System.currentTimeMillis() - Ring.lastRender;
        Ring.getCurrentRing().render();
        if (Ring.previousRing != null)
            Ring.previousRing.renderStuff();
    }
}
