package com.shahancraft.world.block;

import com.shahancraft.graphics.MasterRenderer;
import com.shahancraft.graphics.model.Vertex;
import com.shahancraft.math.Vector2f;
import com.shahancraft.math.Vector3f;
import com.shahancraft.texture.Texture;
import com.shahancraft.world.Chunk;
import com.shahancraft.world.NewChunkLoader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shahan on 11/27/2017.
 */

public  class ChunkModelGenerator {

   private static Block[][][] cblocks;
    private static final  Vertex[] frontVertices = {//FrontFace
            new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//Botttom left 0
            new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0f,1)),//bottom right 1
            new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0f,0.666f)),//top right 2
            new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0f,0.666f)),//top right 2
            new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0.333f,0.666f)),//top left 3
            new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//Botttom left 0
    };

    private static final  Vertex[] backVetices={
            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.333f,1)), //4
            new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0f,1)),//5
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),//6
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),//6
            new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.333f,0.666f)),//7
            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.333f,1)), //4
    };
    private static final Vertex[] bottomV = {

            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
            new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0.333f,0)),
            new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
            new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
            new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,0.333f)),//Botttom left
    };
    private static final  Vertex[] leftV = {

            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.3333f,1)),
            new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,1)),//Botttom left
            new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
            new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
            new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.33f,0.666f)),
            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.33f,1)),//done
    };
    private  static final Vertex[] rightV = {
            new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//bottom right
            new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f), new Vector2f(0,1)),
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),
            new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.333f,0.666f)),
            new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//done
    };
    private static  final Vertex[] topV = {
            new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
            new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.3333f,0.6666f)),//front right
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
            new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0,0.3333f)),//back left
            new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
    };

    public static void genModel(Block[][][] blocks, List<Vertex> modelVertices, List<Integer> modelIndecies, Chunk chunk){
        new Thread(new Runnable() {
            public void run() {


        cblocks = blocks;
        for (int x = 0; x<16;x++){
            for (int y = 0; y<16;y++){
                for (int z = 0; z<16;z++){

                    Block block = blocks[x][y][z];
                    Boolean edge = false;
                    if (x>14||x<1||y>14||y<1||z>14||z<1){
                        edge=true;
                    }

                    if (block == null||block.getBlockType() == BlockType.AIR){

                        continue;
                    }
                    if (edge){
                        //block.setBlockType(BlockType.Stone);
                    }
                    //System.out.println("updating model" + block.getBlockType());
                    Vector3f pos = new Vector3f(x * 2, y * 2, z * 2);
                    BlockType type = block.getBlockType();
                    NewChunkLoader chunkLoader = MasterRenderer.sChunkLoader;
                   // System.out.println("is their a air block below : " +chunk.getSisterChunk(Chunk.BELOW).getBlock(x,15,z)== null || chunk.getSisterChunk(Chunk.BELOW).getBlock(x,15,z).getBlockType()== BlockType.AIR);
                    if (getBlock(x,y+1,z)==null|| getBlock(x,y+1,z).getBlockType() == BlockType.AIR){
                        if (y!=15){
                            addFaceToArrays(topV, pos,type,modelVertices,modelIndecies);
                        }
                        if(chunk.getSisterChunk(Chunk.ABOVE).getBlock(x,0,z)== null || chunk.getSisterChunk(Chunk.ABOVE).getBlock(x,0,z).getBlockType()== BlockType.AIR ){
                            addFaceToArrays(topV, pos,type,modelVertices,modelIndecies);
                        }

                    }
                    if(getBlock(x,y-1,z) == null || getBlock(x,y-1,z).getBlockType() == BlockType.AIR){
                        if (y!=0) {
                            addFaceToArrays(bottomV,pos,type,modelVertices,modelIndecies);
                        }
                        if(chunk.getSisterChunk(Chunk.BELOW).getBlock(x,15,z)== null || chunk.getSisterChunk(Chunk.BELOW).getBlock(x,15,z).getBlockType()== BlockType.AIR ) {
                            addFaceToArrays(bottomV,pos,type,modelVertices,modelIndecies);
                        }

                    }

                    if (getBlock(x+1,y,z) == null|| getBlock(x+1,y,z).getBlockType() == BlockType.AIR){
//                        if (!edgeBlock){
//                            addFaceToArrays(rightV, pos,type,modelVertices,modelIndecies);
//                        }
                        if (x!=15){
                            addFaceToArrays(rightV, pos,type,modelVertices,modelIndecies);
                        }
                        if (chunk.getSisterChunk(Chunk.RIGHT).getBlock(0,y,z) == null || chunk.getSisterChunk(Chunk.RIGHT).getBlock(0,y,z).getBlockType() == BlockType.AIR){
                            addFaceToArrays(rightV, pos,type,modelVertices,modelIndecies);
                        }

                    }
                    if (getBlock(x-1,y,z) == null|| getBlock(x-1,y,z).getBlockType() == BlockType.AIR){
                        if (x != 0){
                            addFaceToArrays(leftV, pos, type, modelVertices, modelIndecies);

                        }

                        if (chunk.getSisterChunk(Chunk.LEFT).getBlock(15,y,z) == null || chunk.getSisterChunk(Chunk.LEFT).getBlock(15,y,z).getBlockType() == BlockType.AIR) {
                            addFaceToArrays(leftV, pos, type, modelVertices, modelIndecies);
                        }
                    }

                    if (getBlock(x,y,z+1) == null|| getBlock(x,y,z+1).getBlockType() == BlockType.AIR){
                        if (z!=15){
                            addFaceToArrays(frontVertices, pos, type, modelVertices, modelIndecies);

                        }
                        if (chunk.getSisterChunk(Chunk.FORWARDS).getBlock(x,y,0) == null || chunk.getSisterChunk(Chunk.FORWARDS).getBlock(x,y,0).getBlockType() == BlockType.AIR) {
                            addFaceToArrays(frontVertices, pos, type, modelVertices, modelIndecies);
                        }
                    }
                    if (getBlock(x,y,z-1) == null|| getBlock(x,y,z-1).getBlockType() == BlockType.AIR){

                        if (z!=0){
                            addFaceToArrays(backVetices, pos, type, modelVertices, modelIndecies);
                        }
                       if (chunk.getSisterChunk(Chunk.BACKWORDS).getBlock(x,y,15) == null || chunk.getSisterChunk(Chunk.BACKWORDS).getBlock(x,y,15).getBlockType() == BlockType.AIR) {
                            addFaceToArrays(backVetices, pos, type, modelVertices, modelIndecies);
                        }
                        //System.out.println("Createing block!!");
                    }

                }
            }
        }
        NewChunkLoader chunkLoader = MasterRenderer.sChunkLoader;
        if (chunkLoader!=null){
            //System.out.println("callimg load chunkmodel for chunk: " + chunk.getPosition().x + "," + chunk.getPosition().y + "," + chunk.getPosition().z);
            chunkLoader.loadChunkModel(chunkLoader.returnIndexOfChunkIfLoaded(chunk),modelVertices,modelIndecies);
        }
            }
        }).start();
    }
    public static void  addFaceToArrays(Vertex[] vertices, Vector3f position, BlockType type, List<Vertex> modelVertices, List<Integer> modelIndecies){



        for (Vertex vertex : vertices){
            float x = vertex.getPos().x;
            float y = vertex.getPos().y;
            float z = vertex.getPos().z;

            modelVertices.add(new Vertex(new Vector3f(x+position.x,y+position.y,z+position.z),new Vector2f(vertex.getTexCoord().x/3+ Texture.GetTextureCoord(type),vertex.getTexCoord().y)) );
            if (modelIndecies.size()>0){
                int l = modelIndecies.get(modelIndecies.size() - 1);
                modelIndecies.add(l+1);
            }else{
                modelIndecies.add(0);
            }
        }



    }
    public static Block getBlock(int x,int y, int z){

        if (x>15||x<0||y>15||y<0||z>15||z<0){
            return null;
        }
        return cblocks[x][y][z];
    }

    public static void createBuffers(Vertex[] finalv, int[] finalindices,Chunk threadChunk,FloatBuffer buffer, IntBuffer ibuffer,FloatBuffer textBuffer, FloatBuffer nbuffer){
             new Thread(new Runnable() {
                public void run() {
        //System.out.println(threadChunk.getID() + " started at: " + System.currentTimeMillis());
        Vector3f[] normals = new Vector3f[finalv.length];
        calcNoramls(finalv,finalindices,normals);
        LinkedList<Vector3f> positions = new LinkedList<Vector3f>();
        LinkedList<Vector2f> textCoords = new LinkedList<Vector2f>();
        //   threadChunk.getModel().calcNoramls(finalv,finalindices);
        for (int i = 0; i < finalv.length; i++) {
            positions.addLast(finalv[i].getPos());
            //System.out.println("getting tex cord :" + vertices[i].getTexCoord().x + "," + vertices[i].getTexCoord().y);
            textCoords.addLast(finalv[i].getTexCoord());
        }

        for (int i = 0; i < positions.size(); i++) {//we put each vertices in the buffer
            Vector3f pos = positions.get(i);
            buffer.put(pos.x);
            buffer.put(pos.y);
            buffer.put(pos.z);
        }
        buffer.flip(); // flip it so the graphic card can read it


        ibuffer.put(finalindices);
        ibuffer.flip();





        for (int i = 0; i < textCoords.size(); i++) {//we put each vertices in the buffer
            textBuffer.put(textCoords.get(i).x);
            textBuffer.put(textCoords.get(i).y);
        }
        textBuffer.flip();

        for (int i =0;i<finalv.length; i++){
//            nbuffer.put(1);
//            nbuffer.put(1);
//            nbuffer.put(1);
            nbuffer.put(normals[i].x);
            nbuffer.put(normals[i].y);
            nbuffer.put(normals[i].z);
        }
        nbuffer.flip();
        //System.out.println(threadChunk.getID() + " done at: " + System.currentTimeMillis());
       // System.out.println(threadChunk.getID() + " Thread done!");

        //chunkModel.bufferVerticesFromBuffer(buffer,ibuffer,textBuffer,size);
            NewChunkLoader chunkLoader = MasterRenderer.sChunkLoader;
            int size = finalindices.length;
            if (chunkLoader!=null){
                //System.out.println("callimg load chunkmodel for chunk: " + chunk.getPosition().x + "," + chunk.getPosition().y + "," + chunk.getPosition().z);
                chunkLoader.bufferVerteciesOfChunk(threadChunk,buffer,ibuffer,textBuffer,size,nbuffer);
            }
   } }).start();
    }

    public static void calcNoramls(Vertex[] vertices, int[] indices,Vector3f[] normals){
        for (int i =0;i<indices.length;i+=3){
            int i0 = indices[i];
            int i1 = indices[i+1];
            int i2 = indices[i+2];

            Vector3f v1 =vertices[i1].getPos().sub(vertices[i0].getPos());
            Vector3f v2 =vertices[i2].getPos().sub(vertices[i0].getPos());

            Vector3f normal =  v1.cross(v2).normalized();
            if (normal == null){
                System.out.println("FML");
            }

            normals[i]  = normal;
            normals[i+1]= normal;
            normals[i+2]= normal;
        }

        for (int i = 0; i <vertices.length;i++){
            normals[i]=(normals[i].normalized());
        }
    }
}
