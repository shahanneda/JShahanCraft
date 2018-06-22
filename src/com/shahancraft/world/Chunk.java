package com.shahancraft.world;

import com.shahancraft.graphics.model.Model;
import com.shahancraft.graphics.model.Vertex;
import com.shahancraft.math.Matrix4f;
import com.shahancraft.math.Transform;
import com.shahancraft.math.Vector2f;
import com.shahancraft.math.Vector3f;
import com.shahancraft.world.block.Block;
import com.shahancraft.world.block.BlockType;
import com.shahancraft.world.block.ChunkModelGenerator;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shahan on 11/24/2017.
 */
public class Chunk {
    //                             X   Y   Z
    Block[][][] blocks = new Block[16][16][16];

    public int getID() {
        return ID;
    }

    private  int ID ;
    public Vector3f getPosition() {
        return position;
    }
    public static final int FORWARDS = 0;
    public static final int RIGHT = 1;
    public static final int BACKWORDS = 2;
    public static final int LEFT = 3;
    public static final int ABOVE = 4;
    public static final int BELOW = 5;
    public Boolean hasTriedToBeGeneratedButFailed = false;
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    private List<Vertex> modelVertices= new ArrayList<Vertex>();
    private List<Integer> modelIndecies= new ArrayList<Integer>();

    private Model chunkModel;

    private Vector3f position;

    private Chunk[] sisterChunks;



    public void setSisterChunk(int id, Chunk chunk){
        sisterChunks[id] = chunk;
    }
    public Chunk getSisterChunk(int id){
        return sisterChunks[id] ;
    }
    public void setSisterChunks(Chunk[] chunks){
        sisterChunks = chunks;
    }



    private final  Vertex[] frontVertices = {//FrontFace
            new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//Botttom left 0
            new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0f,1)),//bottom right 1
            new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0f,0.666f)),//top right 2
            new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0f,0.666f)),//top right 2
            new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0.333f,0.666f)),//top left 3
            new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//Botttom left 0
    };


    //32 vetices
    private final  int[] frontIndicies = {
            0, 1, 2,
            2, 3, 0,

    };

    private final  int[] backIndicies = {
            0, 1, 2,
            3, 4, 5,


    };

    private final  Vertex[] backVetices={
            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.333f,1)), //4
            new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0f,1)),//5
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),//6
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),//6
            new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.333f,0.666f)),//7
            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.333f,1)), //4
    };
    private final Vertex[] bottomV = {

            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
            new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0.333f,0)),
            new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
            new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
            new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,0.333f)),//Botttom left
    };
    private final int[] bottomI = {
            0,1,2,
            3,4,5
    };

    private final  Vertex[] leftV = {

            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.3333f,1)),
            new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,1)),//Botttom left
            new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
            new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
            new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.33f,0.666f)),
            new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.33f,1)),//done
    };

    private final int[] topi ={
            0,1,2,
            3,4,5
    };

    private final Vertex[] rightV = {
            new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//bottom right
            new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f), new Vector2f(0,1)),
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),
            new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.333f,0.666f)),
            new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//done
    };

    private final Vertex[] topV = {
            new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
            new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.3333f,0.6666f)),//front right
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
            new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
            new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0,0.3333f)),//back left
            new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
    };


    public Chunk(Vector3f position){
        this.position = position;
        //System.out.println("Chunk created at:" + position.x + "," + position.y+"," + position.z);
        chunkModel = new Model();
        sisterChunks = new Chunk[6];
        sisterChunks[0] = null;
        sisterChunks[1] = null;
        sisterChunks[2] = null;
        sisterChunks[3] = null;
        sisterChunks[4] = null;
        sisterChunks[5] = null;
        this.ID = (int)((Math.floor(Math.random()*position.x*6667)-(Math.floor(Math.random()*position.y*7895))+(Math.ceil(Math.random()*position.z*554)) ));
        transformation = getTransformation();

//         Vertex[] finalv = modelVertices.toArray(new Vertex[modelVertices.size()]);
//        int[] finalindices = modelIndecies.stream().mapToInt(i->i).toArray();
//        chunkModel.bufferVertices(finalv,finalindices);

    }


    public void setBlock(int x,int y, int z , Block block) {
           // System.out.println("Setting block to " + block.getBlockType());
            blocks[x][y][z] = block;

    }
    public Block getBlock(int x,int y, int z){

        if (x>15||x<0||y>15||y<0||z>15||z<0){
            return null;
        }
        return blocks[x][y][z];
    }
    public Block getBlock(Vector3f posInChunk){
        return getBlock(posInChunk.x,posInChunk.y,posInChunk.z);
    }
    public Block getBlock(float x,float y, float z){
        if (x>15||x<0||y>15||y<0||z>15||z<0){
            return null;
        }
        return blocks[(int)x][(int)y][(int)z];
    }
    //TEMP


    public Model getModel(){
        return chunkModel;
    }
    public void fillWithGrass(){
//        ArrayList<Vertex> vetcies= new ArrayList<Vertex>();
//        ArrayList<Integer> indecis= new ArrayList<Integer>();

        for (int x = 0; x<15;x++) {
            for (int y = 0; y < 15; y++) {
                for (int z = 0; z < 15; z++) {
//                    for (Vertex v : cubevert){
//                        vetcies.add(v);
//                    }
//                    for (Integer i : cubeindicies){
//                        indecis.add(i);
//                    }
                  setBlock(x, y, z, new Block(BlockType.GRASS, new Vector3f(x * 2, y*2, z * 2), this));


                }
            }
        }
     //   System.out.println("UPDATING MODEL");
        //updateModel();
//       Vertex[] av = vetcies.toArray(new Vertex[vetcies.size()]);
//        Integer[] aii = indecis.toArray(new Integer[indecis.size()]);
//       int[] ai = Arrays.stream(aii).mapToInt(Integer::intValue).toArray();
//        chunkModel.bufferVertices(av,ai);
    }
    int u = 0;
    public boolean isBlockVisable(int x, int y, int z){
        Block block = getBlock(x,y,z);
        if (u == 0){
            u = 10;
            return false;
        }
        if (getBlock(x,y,z) != null){
            return false;
        }
//        if (block == null){
//            return false;
//        }
        return true;
    }

    public Vertex[] finalv ;
    public int[] finalindices;
    public Vector3f[] finalNormals;
    public void updateModel(){
        modelVertices.clear();
        modelIndecies.clear();

        ChunkModelGenerator.genModel(blocks,modelVertices,modelIndecies,this);





//        finalv = modelVertices.toArray(new Vertex[modelVertices.size()]);
//        finalindices = modelIndecies.stream().mapToInt(i->i).toArray();
//        needToupdate = true;

    }
    public void fillWithDirt(){
//

        for (int x = 0; x<16;x++){
            for (int z = 0; z<16;z++){
                for (int y = 0; y<16;y++){
                    setBlock(x,y,z,new Block(BlockType.DIRT,new Vector3f(x*2,y*2,z*2),this));
                }
            }
        }
        //new Thread(this::updateModel).start();
        ExecutorService service = Executors.newFixedThreadPool(10);
        service.submit(new Runnable() {
            public void run() {

                //updateModel();
//                Block block = getBlock(0,0,0);
//                addFaceToArrays(topV,new Vector3f(0,0,0),BlockType.GRASS);
//                finalv = modelVertices.toArray(new Vertex[modelVertices.size()]);
//                 finalindices = modelIndecies.stream().mapToInt(i->i).toArray();

                updateModel();

            }
        });
        needToupdate =true;

    }
    public Matrix4f transformation ;
    public Matrix4f getTransformation() {
        return Transform.getTransformation(new Vector3f(position.x, position.y, position.z), 0, 0, 0, 1);
    }
    public void fillWithNoise(){
   // fillWithGrass();
    //**** TEMPPPPPP TODO: Renable noise

        boolean airChunk = position.y>64;
        for (int x = 0; x<16;x++){
                for (int z = 0; z<16;z++){


                    int height = Terrain.getBlockHeight(x+position.x,z+position.z,position.y);
                    Biome biome = Terrain.getBiome(x+position.x,z+position.z,position.y);
                    for (int y = 0; y<16;y++){
                        if (airChunk){
                            setBlock(x, y, z, new Block(BlockType.AIR, new Vector3f(x * 2, y * 2, z * 2), this));
                            continue;
                        }
                        Vector3f worldPosition = new Vector3f(x*2+position.x,y*2+position.y,z*2+position.z);
                        //System.out.println(y<height);

                        if (y<height) {

                            if (biome == Biome.Desert){
                                setBlock(x, y, z, new Block(BlockType.Sand, new Vector3f(x * 2, y * 2, z * 2), this));
                            }else if(biome == Biome.Lake && y > 8){
                                setBlock(x,y,z,new Block(BlockType.AIR,new Vector3f(x*2,y*2,z*2),this));
                            }
                            else if(biome == Biome.Lake ){
                                setBlock(x,y,z,new Block(BlockType.WATER,new Vector3f(x*2,y*2,z*2),this));
                            }
                            else if(worldPosition.y < 20){
                                setBlock(x, y, z, new Block(BlockType.Stone, new Vector3f(x * 2, y * 2, z * 2), this));
                            }
                            else {
                                setBlock(x, y, z, new Block(BlockType.DIRT, new Vector3f(x * 2, y * 2, z * 2), this));
                            }
                            if(y+1>=height){
                                if (biome == Biome.Desert){
                                    setBlock(x, y, z, new Block(BlockType.Sand, new Vector3f(x * 2, y * 2, z * 2), this));
                                } else if (biome == Biome.Lake){
                                    setBlock(x,y,z,new Block(BlockType.AIR,new Vector3f(x*2,y*2,z*2),this));
                                }
                                else {
                                    setBlock(x,y,z,new Block(BlockType.GRASS,new Vector3f(x*2,y*2,z*2),this));
                                }

                                continue;
                            }

                           continue;
                        }
                        setBlock(x,y,z,new Block(BlockType.AIR,new Vector3f(x*2,y*2,z*2),this));
//                        if (y < 5 &&height >0){
//                            setBlock(x,y,z,new Block(BlockType.WATER,new Vector3f(x*2,y*2,z*2),this));
//                        }

                 }
            }
        }


    }
public IntBuffer ibuffer ;
public FloatBuffer textBuffer ;
public FloatBuffer buffer ;
public boolean shouldUpdateModelBufferData;
    public FloatBuffer nbuffer;
public int size;

public boolean needToupdate = false;
    public Vertex[] oldv;
    public int[] oldi;
    public void updateshitifineedto(){

        if (finalv!=null&&finalindices!=null&&needToupdate&&oldv!=finalv&&oldi!=finalindices){
            //System.out.print("doing update model");
            final Chunk threadChunk = this;
            ibuffer = BufferUtils.createIntBuffer(finalindices.length);
            textBuffer = BufferUtils.createFloatBuffer(finalv.length * 2);
             buffer = BufferUtils.createFloatBuffer(finalv.length * Vertex.SIZE);
            nbuffer=BufferUtils.createFloatBuffer(finalv.length*3);
            ChunkModelGenerator.createBuffers(finalv,finalindices,threadChunk,buffer,ibuffer,textBuffer,nbuffer);

//            NewChunkLoader chunkLoader = MasterRenderer.sChunkLoader;
//            int size = finalindices.length;
//            if (chunkLoader!=null){
//                //System.out.println("callimg load chunkmodel for chunk: " + chunk.getPosition().x + "," + chunk.getPosition().y + "," + chunk.getPosition().z);
//                chunkLoader.bufferVerteciesOfChunk(threadChunk,buffer,ibuffer,textBuffer,size);
//            }

            oldv = finalv;
            oldi = finalindices;
            needToupdate =false;
          }
          if (shouldUpdateModelBufferData){
              chunkModel.bufferVerticesFromBuffer(buffer,ibuffer,textBuffer,size,nbuffer);
          }
    }
}
