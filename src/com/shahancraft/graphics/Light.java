package com.shahancraft.graphics;

import com.shahancraft.math.Vector3f;

/**
 * Created by shahan on 11/30/2017.
 */
public class Light {
    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    private Vector3f position;
    private Vector3f color;

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
