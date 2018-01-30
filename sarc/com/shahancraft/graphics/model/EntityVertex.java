package com.shahancraft.graphics.model;

import com.shahancraft.math.Vector3f;

public class EntityVertex {
    public static final int SIZE = 6;

    public Vector3f getPos() {
        return pos;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    private Vector3f normal;
    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    private Vector3f pos;

    public EntityVertex(float x, float y, float z, Vector3f normal){
        this.pos= new Vector3f(x,y,z);
        this.normal = normal;
    }
    public EntityVertex(Vector3f pos){
        this.pos= pos;
    }


}
