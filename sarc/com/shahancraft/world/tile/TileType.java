package com.shahancraft.world.tile;

import com.shahancraft.math.Vector3f;

/**
 * Created by shahan on 11/21/2017.
 */
public enum TileType {

    AIR             (0,"Air",new Vector3f(0,0,0)),
    GRASS           (1,"Grass",new Vector3f(0,1,0)),
    SAND            (2,"Sand",new Vector3f(1,1,0)),
    WATER           (3,"Water",new Vector3f(0,0,1)),
    STONE           (4,"Stone",new Vector3f(0.5f,0.5f,0.5f));


    private Vector3f color;

    public Vector3f getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private  int id;
    private String  name;

    TileType(int id, String name, Vector3f color){
        this.id= id;
        this.name = name;
        this.color = color;
    }


}
