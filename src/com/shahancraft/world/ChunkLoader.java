package com.shahancraft.world;

import com.shahancraft.math.Vector3f;

import java.util.*;

/**
 * Created by shahan on 11/24/2017.
 */
public class ChunkLoader {
    //private Chunk[] loadedChunks = new Chunk[9];
    private LinkedList<Chunk> loadedChunks;
    private LinkedList<Vector3f> chunkLocations;
    private LinkedList<Vector3f> chunkQueue = new LinkedList<Vector3f>();

    //TODO save this file
    private List<Chunk> allChunks;
    private Chunk lastPlayerChunk;
    public static int Numberofchunkstogeneacahtime = 2;
    public ChunkLoader(){

        loadedChunks  = new LinkedList<>();
        chunkLocations = new LinkedList<Vector3f>();
        allChunks = new ArrayList<Chunk>();

        //loadChunk(new Vector3f(0,0,0));
      //  for (int j = 0; j <5; j++) {
            //for (int k = 0; k < 5; k++) {
//                for (int i = 0; i < 4; i++) {
//                    loadChunk(new Vector3f(1*32, i * 32, 1*32));
//                }
                ;
            //}
       // }
        for (int i = 0; i < loadedChunks.size(); i++){
           Chunk chunk =  loadedChunks.get(i);

        }


    }
    int lastchunk = 32;
    public void TestChunk(Vector3f playerPos){
        Chunk current = returnChunkAtLoacation(getChunkposOfPlayerChunk(playerPos));
        if (current!= null){


                        //current.setBlock(Math.round(playerPos.x-current.getPosition().x)/2,Math.round (playerPos.y-current.getPosition().y)/2, Math.round(playerPos.z-current.getPosition().z)/2, new Block(BlockType.AIR, new Vector3f((Math.round(playerPos.x-current.getPosition().x)), Math.round(playerPos.y-current.getPosition().y), Math.round(playerPos.z-current.getPosition().z)), current));


            current.updateModel();





        }
    }
    private Chunk loadChunk(Vector3f position){
        Chunk chunk = returnChunkAtLoacation(position);
        if (chunk != null){
           // System.out.println("chunk found at" + chunk.getPosition().x);
            if (checkIfChunkIsLoaded(chunk)){
                return chunk;
            }
            loadedChunks.add(chunk);
            return chunk;
        }
        if (chunk == null){
           // System.out.println("adding new chunk at: " + position.x+","+position.y + ","+position.z );
            System.out.println("Adding chunk to queue");
            chunkQueue.addFirst(position);

        }

       // Thread t1 = new Thread(chunk);
       // t1.start();
return null;

    }
    public void HandleNextChunk(){
        Vector3f pos =chunkQueue.pollLast();
        if (pos != null) {
            if (returnChunkAtLoacation(pos) == null){
                System.out.println("creating chunk from queue");
                Chunk chunk = new Chunk(pos);
                chunk.fillWithNoise();
                loadedChunks.add(chunk);
                allChunks.add(chunk);
                return;
            }
            System.out.println("appraintly chunk found at location so no chunk created");

        }
    }
    public Chunk GetPlayerChunk(Vector3f playerPos){

        for (Chunk chunk:loadedChunks){
            Vector3f cpos = chunk.getPosition();
            if (playerPos.x > cpos.x && playerPos.x < cpos.x +30 && playerPos.z > cpos.z&& playerPos.z < cpos.z + 30){
                return chunk;
            }
        }

        //return loadedChunks.get(0);
        return null;
    };
    public Vector3f getChunkposOfPlayerChunk(Vector3f ppos){
        return new Vector3f((Math.round(ppos.x/32))*32,(Math.round(ppos.y/32))*32,(Math.round(ppos.z/32))*32);
    }
    public boolean checkIfChunkIsLoaded(Chunk chunk){
        //System.out.println("checking chunks");


        for (Chunk oldchunk:loadedChunks){

            if(oldchunk.getPosition() == chunk.getPosition()){

               System.out.println("Chunk exists at: " + oldchunk.getPosition().x + ","  + oldchunk.getPosition().z);
                return true;
            }
        }

        return false;
    };
    private Chunk returnChunkAtLoacation(Vector3f position){
        for (Chunk oldchunk:allChunks){

            if(oldchunk.getPosition().x == position.x && oldchunk.getPosition().y == position.y &&oldchunk.getPosition().z == position.z   ){

                return oldchunk;
            }
        }
        System.out.println("No chunk found at location");
        return null;
    };
    public Chunk[] GetLoadedChunk(Vector3f playerPos){
       // Chunk playerChunk = GetPlayerChunk(playerPos);
       // System.out.println(getChunkposOfPlayerChunk(playerPos));
       loadChunk(getChunkposOfPlayerChunk(playerPos));
        for (int i = 0; i<10; i++){
            loadChunk(getChunkposOfPlayerChunk(new Vector3f(playerPos.x+(i*32),playerPos.y,playerPos.z)));
            loadChunk(getChunkposOfPlayerChunk(new Vector3f(playerPos.x,playerPos.y,playerPos.z+(i*30))));
        }
        HandleNextChunk();
        //genChunkssaround(getChunkposOfPlayerChunk(playerPos),ChunkLoader.Numberofchunkstogeneacahtime);

       for (int i =0;i<loadedChunks.size();i++){
           Chunk chunk = loadedChunks.get(i);
           //if (chunk!= null){
               chunk.updateshitifineedto();
           //}


       }

        return loadedChunks.toArray(new Chunk[loadedChunks.size()]);
    }
    private void genChunkssaround(Vector3f pos,int numberoftimes ){
        loadChunk(pos);
        if (numberoftimes >0){
            numberoftimes--;
        }
        if (numberoftimes == 0){
            return;
        }


        loadChunk(new Vector3f(pos.x+32,pos.y,pos.z));
        genChunkssaround(new Vector3f(pos.x+32,pos.y,pos.z),numberoftimes);

        loadChunk(new Vector3f(pos.x-32,pos.y,pos.z));
        genChunkssaround(new Vector3f(pos.x-32,pos.y,pos.z),numberoftimes);
        loadChunk(new Vector3f(pos.x,pos.y+32,pos.z));
        genChunkssaround(new Vector3f(pos.x,pos.y+32,pos.z),numberoftimes);

        loadChunk(new Vector3f(pos.x,pos.y-32,pos.z));
        genChunkssaround(new Vector3f(pos.x,pos.y-32,pos.z),numberoftimes);

        loadChunk(new Vector3f(pos.x,pos.y,pos.z+32));
        genChunkssaround(new Vector3f(pos.x,pos.y,pos.z+32),numberoftimes);

        loadChunk(new Vector3f(pos.x,pos.y,pos.z-32));
        genChunkssaround(new Vector3f(pos.x,pos.y,pos.z-32),numberoftimes);


    }
    private void generateChunksAround(Chunk chunk, int numberOfTimes, boolean genCenter){
        if (numberOfTimes == 0){
            return;
        }
        Chunk centerChunk = chunk;
        if (genCenter) {
             centerChunk = loadChunk(new Vector3f(chunk.getPosition().x, 0, chunk.getPosition().z));

        }
        Chunk rightChunk = loadChunk(new Vector3f(chunk.getPosition().x + 30, 0, chunk.getPosition().z));
        generateChunksAround(rightChunk,numberOfTimes-1,false);



        Chunk leftChunk = loadChunk(new Vector3f(chunk.getPosition().x - 30, 0, chunk.getPosition().z));
        generateChunksAround(leftChunk,numberOfTimes-1,false);


        Chunk forwardChunk = loadChunk(new Vector3f(chunk.getPosition().x, 0, chunk.getPosition().z+30));
        generateChunksAround(forwardChunk,numberOfTimes-1,false);


        Chunk backwordsChunk = loadChunk(new Vector3f(chunk.getPosition().x, 0, chunk.getPosition().z-30));
        generateChunksAround(backwordsChunk,numberOfTimes-1,false);

        centerChunk.setSisterChunk(Chunk.FORWARDS, forwardChunk);
        centerChunk.setSisterChunk(Chunk.BACKWORDS, backwordsChunk);
        centerChunk.setSisterChunk(Chunk.RIGHT, rightChunk);
        centerChunk.setSisterChunk(Chunk.LEFT, leftChunk);

        rightChunk.setSisterChunk(Chunk.LEFT, centerChunk);
        leftChunk.setSisterChunk(Chunk.LEFT, centerChunk);
        forwardChunk.setSisterChunk(Chunk.LEFT, centerChunk);
        backwordsChunk.setSisterChunk(Chunk.LEFT, centerChunk);

        if (centerChunk.getSisterChunk(Chunk.BACKWORDS) != null){
            if (centerChunk.getSisterChunk(Chunk.BACKWORDS).getSisterChunk(Chunk.RIGHT)!= null){
                centerChunk.getSisterChunk(Chunk.BACKWORDS).getSisterChunk(Chunk.RIGHT).setSisterChunk(Chunk.FORWARDS,rightChunk);
            }
        }
        if (centerChunk.getSisterChunk(Chunk.BACKWORDS) != null){
            if (centerChunk.getSisterChunk(Chunk.BACKWORDS).getSisterChunk(Chunk.LEFT)!= null){
                centerChunk.getSisterChunk(Chunk.BACKWORDS).getSisterChunk(Chunk.LEFT).setSisterChunk(Chunk.FORWARDS,leftChunk);
            }
        }

        if (centerChunk.getSisterChunk(Chunk.LEFT) != null){
            if (centerChunk.getSisterChunk(Chunk.LEFT).getSisterChunk(Chunk.FORWARDS)!= null){
                centerChunk.getSisterChunk(Chunk.LEFT).getSisterChunk(Chunk.FORWARDS).setSisterChunk(Chunk.RIGHT,forwardChunk);
            }
        }
        if (centerChunk.getSisterChunk(Chunk.LEFT) != null){
            if (centerChunk.getSisterChunk(Chunk.LEFT).getSisterChunk(Chunk.BACKWORDS)!= null){
                centerChunk.getSisterChunk(Chunk.LEFT).getSisterChunk(Chunk.BACKWORDS).setSisterChunk(Chunk.RIGHT,backwordsChunk);
            }
        }

        if (centerChunk.getSisterChunk(Chunk.FORWARDS) != null){
            if (centerChunk.getSisterChunk(Chunk.FORWARDS).getSisterChunk(Chunk.RIGHT)!= null){
                centerChunk.getSisterChunk(Chunk.FORWARDS).getSisterChunk(Chunk.RIGHT).setSisterChunk(Chunk.BACKWORDS,rightChunk);
            }
        }
        if (centerChunk.getSisterChunk(Chunk.FORWARDS) != null){
            if (centerChunk.getSisterChunk(Chunk.FORWARDS).getSisterChunk(Chunk.LEFT)!= null){
                centerChunk.getSisterChunk(Chunk.FORWARDS).getSisterChunk(Chunk.LEFT).setSisterChunk(Chunk.BACKWORDS,leftChunk);
            }
        }

        if (centerChunk.getSisterChunk(Chunk.RIGHT) != null){
            if (centerChunk.getSisterChunk(Chunk.RIGHT).getSisterChunk(Chunk.FORWARDS)!= null){
                centerChunk.getSisterChunk(Chunk.RIGHT).getSisterChunk(Chunk.FORWARDS).setSisterChunk(Chunk.LEFT,forwardChunk);
            }
        }
        if (centerChunk.getSisterChunk(Chunk.RIGHT) != null){
            if (centerChunk.getSisterChunk(Chunk.RIGHT).getSisterChunk(Chunk.BACKWORDS)!= null){
                centerChunk.getSisterChunk(Chunk.RIGHT).getSisterChunk(Chunk.BACKWORDS).setSisterChunk(Chunk.LEFT,backwordsChunk);
            }
        }

    }
}
