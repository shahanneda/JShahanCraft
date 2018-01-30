package com.shahancraft.world;

import com.shahancraft.world.tile.Tile;

/**
 * Created by shahan on 11/21/2017.
 */
public class Segment {
    private Tile[] tiles = new Tile[4096];//
    private int x;//Where this segamnt is compared to other segments
    private  int y;
    private int z;

    public WorldModel getModel() {
        return model;
    }

    private WorldModel model;

    public Segment(int x,int y, int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    private void updateTileVisiblity(int x,int y, int z){
        model = new WorldModel();
        Tile tile = getTile(x,y,z);

        Tile top = getTile(x,y+1,z);
        Tile bot = getTile(x,y-1,z);
        Tile front = getTile(x,y,z-1);
        Tile back = getTile(x,y,z+1);
        Tile left = getTile(x-1,y,z);
        Tile right = getTile(x+1,y,z);

        if (tile == null){
            if (top !=null){
                top.setVisibility(Side.BOT,true);
            }
            if (bot !=null){
                bot.setVisibility(Side.TOP,true);
            }
            if (front !=null){
                front.setVisibility(Side.BACK,true);
            }
            if (back !=null){
                back.setVisibility(Side.FRONT,true);
            }
            if (left !=null){
                left.setVisibility(Side.RIGHT,true);
            }
            if (right !=null){
                right.setVisibility(Side.LEFT,true);
            }
            return;

        }
        boolean[] sides = new boolean[6];
        if (top == null){
            sides[Side.TOP]  = true;
        }else{
            sides[Side.TOP] = false;
            top.setVisibility(Side.BOT,false);
        }
        if (bot == null){
            sides[Side.BOT]  = true;
        }else{
            sides[Side.BOT] = false;
            bot.setVisibility(Side.TOP,false);
        }
        if (front == null){
            sides[Side.FRONT]  = true;
        }else{
            sides[Side.FRONT] = false;
            front.setVisibility(Side.BACK,false);
        }
        if (back == null){
            sides[Side.BACK]  = true;
        }else{
            sides[Side.BACK] = false;
            back.setVisibility(Side.FRONT,false);
        }
        if (left == null){
            sides[Side.LEFT]  = true;
        }else{
            sides[Side.LEFT] = false;
            left.setVisibility(Side.RIGHT,false);
        }
        if (right == null){
            sides[Side.RIGHT]  = true;
        }else{
            sides[Side.RIGHT] = false;
            right.setVisibility(Side.LEFT,false);
        }
        tile.setVisibility(sides);

    }
    public Tile getTile(int i){
        return tiles[i];
    }
    public Tile getTile(int x,int y,int z){
        if(x<0 || x>15 || y<0||y>15 || z<0||z>15) return null;
        return tiles[x+(z<<4)+(y<<8)];
    }
//    public void setTile(int i,Tile tile){
//        tiles[i] = tile;
//    }
    public void setTile(int x,int y, int z,Tile tile){
        tiles[x+(z<<4)+(y<<8)] = tile;
        updateTileVisiblity(x, y, z);
    }
}
