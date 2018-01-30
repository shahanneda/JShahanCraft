package com.shahancraft.graphics.model;

import com.shahancraft.math.Vector3f;
import com.shahancraft.world.Segment;
import com.shahancraft.world.Side;
import com.shahancraft.world.tile.Tile;
import com.shahancraft.world.tile.TileType;

import java.util.ArrayList;

/**
 * Created by shahan on 11/22/2017.
 */
public class WorldModelGenerator {
    /*
        AS WE ARE RUNNING THORUGH THE MEHTOHS 4096 TIMES EVERY SECOND WE MAKE ALL VARIABLE STATIC SO THE MEMEORY IS PREALOCATED BEFORHAND AND TAKES UP LESS TIME

     */
    private  static Vector3f[] normals = {
            new Vector3f(0,1,0),
            new Vector3f(0,-1,0),
            new Vector3f(0,0,-1),
            new Vector3f(0,0,1),
            new Vector3f(-1,0,0),
            new Vector3f(1,0,0)
    };
    //arrays of verticies and indices
    private static ArrayList<WorldVertex> vertices = new ArrayList<WorldVertex>();
    private static ArrayList<Integer> indecies = new ArrayList<Integer>();

    //running varibale
    private static TileType[] mask = new TileType[4096];
    private static  boolean[] done =  new boolean[4096];

    private static Tile tile;
    private static TileType type;
    private static WorldModel model;
    private static int x,y,z;
    private static  int y_,z_;
    private static int k;

    private  static int w=0,h = 0;

    private  static  int sum;
    private static int i,j;
    private static int q;
    private static boolean done_;
    private static  boolean continue_;
    public static void proccesSegmant(Segment seg){
        for (i=0; i <4096; i++){
            tile = seg.getTile(i);//i is tile number

            if (tile == null) mask[i] = TileType.AIR;
            else if (!tile.isVisable()) mask[i] = TileType.AIR;
            else{
                mask[i] = tile.getType();
            }
        }
        for(i=0;i<6;i++){//i is side
            continue_ = true;
            for (j = 0; j<4096;j++){
                if (mask[j] == TileType.AIR ){
                    done[j] = true;
                    continue;
                }
                tile=seg.getTile(i);
                if (!tile.isVisable(i)) done[j] = true;
                else{
                    done[j] = false;
                    continue_=false;
                }
                if (continue_) continue; // go on to the next side
                for (y = 0; y<16;y++){
                    y_ = y << 8;
                    for (z=0;z<16;z++) {
                        z_ = (z<<4) + y_;
                        for (x = 0; x < 16; x++) {
                            sum = x+z;
                            if (done[sum]) continue;
                            type= mask[sum];
                            done_ = false;
                            if (i==Side.TOP || i ==Side.BOT){
                                for (w=0;w<15-x&&mask[sum+1+w] ==type&&!done[sum+1+w]; w++){

                                }
                                for (h=0;h<15-z;h++){
                                    q=sum+16+(h<<4);
                                    for (k =0;k<=w;k++){
                                        if (mask[q+k] != type){
                                            done_ =true;
                                            break;
                                        }
                                    }
                                }
                                for (j=0;j<=h;j++){
                                    q= sum + j <<4;
                                    for (k=0;k<=w;k++){
                                        done[q+k] = true;
                                    }
                                }
                            }else if (i==Side.FRONT || i ==Side.BACK){
                                for (w=0;w<15-x&&mask[sum+1+w] ==type&&!done[sum+1+w]; w++){

                                }
                                for (h=0;h<15-y;h++){
                                    q=sum+256+(h<<8);
                                    for (k =0;k<=w;k++){
                                        if (mask[q+k] != type){
                                            done_ =true;
                                            break;
                                        }
                                    }
                                }
                                for (j=0;j<=h;j++){
                                    q= sum + (j <<8);
                                    for (k=0;k<=w;k++){
                                        done[q+k] = true;
                                    }
                                }
                            }
                            else if (i==Side.LEFT || i ==Side.RIGHT){
                                for (w=0;w<15-z&&mask[sum+6+(w<<4)] ==type&&!done[sum+16+(w<<4)]; w++){

                                }
                                for (h=0;h<15-y;h++){
                                    q=sum+256+(h<<8);
                                    for (k =0;k<=w;k++){
                                        if (mask[q+k] != type){
                                            done_ =true;
                                            break;
                                        }
                                    }
                                }
                                for (j=0;j<=h;j++){
                                    q= sum + j <<8;
                                    for (k=0;k<=w;k++){
                                        done[q+(k<<4)] = true;
                                    }
                                }
                            }
                            addVertices(i,x,y,z,type.getColor());
                        }

                    }
                }


            }
        }
        model = seg.getModel();
        model.bufferVertices(vertices,indecies);

        vertices.clear();
        indecies.clear();

    }

    private static void addVertices(int side,int x, int y ,int z, Vector3f color){
        if (side == Side.TOP){
            vertices.add(new WorldVertex(new Vector3f(x,        y+1,    z       ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x,        y+1,    z+1+h   ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x+1+w,    y+1,    z+1+h   ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x+1+w,    y+1,    z       ),color, normals[side]));
        }
        else if (side == Side.BOT){
            vertices.add(new WorldVertex(new Vector3f(x,        y,      z        ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x+1+w,    y,      z        ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x+1+w,    y,      z+1+h    ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x,        y,      z+1+h    ),color, normals[side]));
        }
        else if (side == Side.FRONT){
            vertices.add(new WorldVertex(new Vector3f(x+1+w,    y+1+h,    z     ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x+1+w,    y,        z     ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x    ,    y,        z     ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x    ,    y+1+h,    z     ),color, normals[side]));
        }
        else if (side == Side.BACK){
            vertices.add(new WorldVertex(new Vector3f(x+1+w,    y+1+h,    z+1   ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x,        y+1+h,    z+1   ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x,        y    ,    z+1   ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x+1+w,    y    ,    z+1   ),color, normals[side]));
        }
        else if (side == Side.LEFT){
            vertices.add(new WorldVertex(new Vector3f(x,        y+1+h,  z+1+w   ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x,        y+1+h,  z       ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x,        y,      z       ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x,        y,      z+1+w   ),color, normals[side]));
        }
        else if (side == Side.RIGHT){
            vertices.add(new WorldVertex(new Vector3f(x+1,      y+1+h,  z+1+w   ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x+1,      y,      z+1+w   ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x+1,      y,      z       ),color, normals[side]));
            vertices.add(new WorldVertex(new Vector3f(x+1,      y+1+h,  z       ),color, normals[side]));
        }
        indecies.add(vertices.size() -4);
        indecies.add(vertices.size() -3);
        indecies.add(vertices.size() -2);

        indecies.add(vertices.size() -2);
        indecies.add(vertices.size() -1);
        indecies.add(vertices.size() -4);
    }



}
