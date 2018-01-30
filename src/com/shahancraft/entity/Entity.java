package com.shahancraft.entity;

import com.shahancraft.graphics.model.Model;
import com.shahancraft.math.Vector3f;
import com.shahancraft.texture.Texture;

/**
 * Created by shahan on 11/19/2017.
 */
public class Entity {
    public Model getModel() {
        return model;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    private Texture texture;

    public void setModel(Model model) {
        this.model = model;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    private Model model;

    private Vector3f pos;

    public Entity(Vector3f pos,Model model,Texture texture){
        this.model = model;
        this.pos = pos;
        this.texture = texture;

    }


}
