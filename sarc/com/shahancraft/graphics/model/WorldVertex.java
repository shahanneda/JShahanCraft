package com.shahancraft.graphics.model;

import com.shahancraft.math.Vector3f;

/**
 * Created by shahan on 11/22/2017.
 */
public class WorldVertex {
    public static final int SIZE = 9;
    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    private Vector3f pos;
    private  Vector3f color;
    private  Vector3f normal;

    public WorldVertex(Vector3f pos,Vector3f color,Vector3f normal){
        this.pos = pos;
        this.color = color;
        this.normal = normal;
    }


}
