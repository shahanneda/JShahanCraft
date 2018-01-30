package com.shahancraft.world.tile;

/**
 * Created by shahan on 11/21/2017.
 */
public  abstract  class Tile {
    public TileType getType() {
        return type;
    }

    private TileType type ;

    public boolean isVisable() {
        return visable;
    }

    private boolean visable;

    public boolean isVisable(int side) {
        return visibility[side];
    }

    private boolean[] visibility;

    public Tile(TileType type){
        this.type = type;
        visable = true;
        visibility = new boolean[6];
        for (int i = 0; i<6;i++){
            visibility[i] = true;
        }
    }
    public  void setVisibility(int side, boolean visable){
        visibility[side] = visable;
    }
    public void setVisibility(boolean[] visibility){
        this.visibility = visibility;
        updateVisibility();
    }
    private void updateVisibility(){
        visable = false;
        for (int i = 0; i<6;i++){
            if (visibility[i] ){
                visable = true;
                return;
            }
        }

    }

}
