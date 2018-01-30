package com.shahancraft.graphics.model;

import com.shahancraft.math.Vector2f;
import com.shahancraft.math.Vector3f;

public class Vertex {
    public static final int SIZE = 3;

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

    public Vector2f getTexCoord() {
        return texCoord;
    }


    public void setTexCoord(Vector2f texCoord) {
        this.texCoord = texCoord;
    }

    private Vector2f texCoord;
    public Vertex(float x, float y, float z){
        this.pos= new Vector3f(x,y,z);
        this.texCoord = new Vector2f(0,0);
    }
    public Vertex(Vector3f pos, Vector2f texCoord){
        this.pos= pos;
        this.texCoord = texCoord;
    }
    public Vertex(Vector3f pos){
        this.pos= pos;
        this.texCoord = new Vector2f(4.0f,4.0f);
    }


}
