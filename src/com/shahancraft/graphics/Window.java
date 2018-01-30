package com.shahancraft.graphics;
import org.lwjgl.glfw.GLFWVidMode;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {

    private long window;
    public  static int  windowWidth = 1080;
    public  static int windowHight =720;
    public Window(int width,int height, String title){
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE,GL_FALSE);
        glfwWindowHint(GLFW_VISIBLE,GL_TRUE);
        glfwWindowHint(GLFW_DECORATED,GL_TRUE);
        glfwWindowHint(GLFW_FOCUSED,GL_TRUE);
        window = glfwCreateWindow(width,height,title,NULL,NULL);
        windowWidth = width;
        windowHight = height;

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    public void dispose(){
        glfwDestroyWindow(window);

    }
    public void hide(){
        glfwHideWindow(window);
    }
    public void show(){
        glfwShowWindow(window);
    }
    public void setTitle(String title){
        glfwSetWindowTitle(window,title);
    }
    public void render(){
        glfwSwapBuffers(window);
    }
    public boolean shouldClose(){
        if (glfwWindowShouldClose(window)){
            return true;
        }
        return false;
    }
    public long getWindow(){
        return window;
    }
}
