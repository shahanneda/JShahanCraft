package com.shahancraft.world.block;

import com.shahancraft.math.Matrix4f;
import com.shahancraft.math.Vector3f;
import com.shahancraft.world.Chunk;

/**
 * Created by shahan on 11/24/2017.
 */
public class Block {
    public Chunk getChunk() {
        return chunk;
    }

    private Chunk chunk;
    public BlockType getBlockType() {
        return blockType;
    }
    public Matrix4f transformation;

    public boolean isVisable() {
        return isVisable;
    }

    public void setVisable(boolean visable) {
        isVisable = visable;
    }

    private boolean isVisable = true;


    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    private BlockType blockType;

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    private Vector3f  position;


    public Block(BlockType type, Vector3f pos,Chunk chunk){
        blockType = type;
        position = pos;
        this.chunk = chunk;



    }


}
