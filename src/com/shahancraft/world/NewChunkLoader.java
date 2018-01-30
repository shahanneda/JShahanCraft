package com.shahancraft.world;

import com.shahancraft.graphics.model.Vertex;
import com.shahancraft.math.Vector3f;
import com.shahancraft.world.block.Block;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shahan on 11/26/2017.
 */
public class NewChunkLoader
{
    public LinkedList<Chunk> loadedChunks = new LinkedList<Chunk>();
    public LinkedList<Chunk> genQueue;
    public int genDelay = 1;
    double lastGenTime;
    public static int renderDistance = 5;
    public Vector3f getChunkposOfPlayerChunk(Vector3f ppos){
        return new Vector3f((Math.round(ppos.x/32))*32,(Math.round(ppos.y/32))*32,(Math.round(ppos.z/32))*32);
    }
    public NewChunkLoader() {

        lastGenTime = System.currentTimeMillis();
//        for (int k = 0; k <10; k++) {
//            for (int i = 0; i < 10; i++) {
//                Chunk chunk = new Chunk(new Vector3f(i * 32, 0, k*32));
//               Chunk chunk1 = new Chunk(new Vector3f(i * 32, 32, k*32));
//                Chunk chunk2 = new Chunk(new Vector3f(i * 32, 64, k*32));
//                Chunk chunk3 = new Chunk(new Vector3f(i * 32, 96, k*32));
////                Chunk chunk4 = new Chunk(new Vector3f(i * 32, 128, k*32));
////                Chunk chunk5 = new Chunk(new Vector3f(i * 32, 160, k*32));
////                Chunk chunk6 = new Chunk(new Vector3f(i * 32, 192, k*32));
////                Chunk chunk7 = new Chunk(new Vector3f(i * 32, 192, k*32));
//               loadedChunks.addFirst(chunk);
//               loadedChunks.addFirst(chunk1);
//                loadedChunks.addFirst(chunk2);
//                loadedChunks.addFirst(chunk3);
////                loadedChunks.addFirst(chunk4);
////                loadedChunks.addFirst(chunk5);
////                loadedChunks.addFirst(chunk6);
////                loadedChunks.addFirst(chunk7);
                genQueue = (LinkedList) loadedChunks.clone();
//
//            }
//        }
    }
    public int returnIndexOfChunkIfLoaded(Chunk chunk){
        for (int i = 0; i<loadedChunks.size();i++){
            if (loadedChunks.get(i).getID() == chunk.getID()){
                //System.out.println("found chunk at index value: " + i + "with id" + chunk.getID());
               return i;
            }
        }
        return -1;
    };
    public void loadChunkModel(int id,List<Vertex> modelVertices,List<Integer> modelIndecies){
        Chunk chunk = loadedChunks.get(id);
        if (chunk != null){
         //   System.out.println("Setting Chunk Model for index: " + id  );
            chunk.finalv =modelVertices.toArray(new Vertex[modelVertices.size()]);
            chunk.finalindices = modelIndecies.stream().mapToInt(i->i).toArray();
            chunk.needToupdate = true;
        }
    }
    public void bufferVerteciesOfChunk(Chunk chunk, FloatBuffer buffer, IntBuffer ibuffer,FloatBuffer textBuffer,int size,FloatBuffer nbuffer){
        chunk.shouldUpdateModelBufferData = true;
        chunk.buffer = buffer;
        chunk.ibuffer = ibuffer;
        chunk.textBuffer = textBuffer;
        chunk.size = size;
        chunk.nbuffer = nbuffer;


       //chunk.getModel().bufferVerticesFromBuffer(buffer,ibuffer,textBuffer,size);
    }
    ///////////////////////////////////////////////////////////////
    public Chunk[] GetLoadedChunk(Vector3f playerPos) {

        Chunk c = loadChunk(getChunkposOfPlayerChunk(playerPos));
        loadChunksAround(c,renderDistance);
        for (int i =0;i<loadedChunks.size();i++){
            Chunk chunk = loadedChunks.get(i);
            chunk.updateshitifineedto();
        }
       // System.out.println("last  time gen+" + (lastGenTime-System.currentTimeMillis()));
        handleGeneration();
        return loadedChunks.toArray(new Chunk[loadedChunks.size()]);
    }
    public void loadChunksAround(Chunk chunk ,int numberOfTimes){
        numberOfTimes--;
        if (numberOfTimes <1){
            return;
        }
        Chunk rightChunk = loadChunk(new Vector3f(chunk.getPosition().x+32 , chunk.getPosition().y ,chunk.getPosition().z ));
        Chunk leftChunk  = loadChunk(new Vector3f(chunk.getPosition().x-32 , chunk.getPosition().y ,chunk.getPosition().z ));
        loadChunksAround(rightChunk,numberOfTimes);
        loadChunksAround(leftChunk,numberOfTimes);

        chunk.setSisterChunk(Chunk.RIGHT , rightChunk) ;
        chunk.setSisterChunk(Chunk.LEFT , leftChunk);

        Chunk aboveChunk = loadChunk(new Vector3f(chunk.getPosition().x , chunk.getPosition().y+32 ,chunk.getPosition().z )) ;
        Chunk belowChunk = loadChunk(new Vector3f(chunk.getPosition().x , chunk.getPosition().y-32,chunk.getPosition().z )) ;
        chunk.setSisterChunk(Chunk.ABOVE, aboveChunk);
        chunk.setSisterChunk(Chunk.BELOW, belowChunk);
        loadChunksAround(aboveChunk,numberOfTimes);
        loadChunksAround(belowChunk,numberOfTimes);

        Chunk backwordsChunk = loadChunk(new Vector3f(chunk.getPosition().x , chunk.getPosition().y ,chunk.getPosition().z-32 ));
        Chunk forwardsChunk  = loadChunk(new Vector3f(chunk.getPosition().x , chunk.getPosition().y ,chunk.getPosition().z+32 ));
        chunk.setSisterChunk(Chunk.BACKWORDS,backwordsChunk);
        chunk.setSisterChunk(Chunk.FORWARDS,forwardsChunk);
        loadChunksAround(forwardsChunk,numberOfTimes);
        loadChunksAround(backwordsChunk,numberOfTimes);

        ///loadChunk(new Vector3f(chunk.getPosition().x , chunk.getPosition().y,chunk.getPosition().z));
    }
    ///////////////////////////////////////////////////////////////
    public Chunk loadChunk(Vector3f pos){
        Chunk chunk = returnChunkAtLoacation(pos);
        if (chunk ==null){
            chunk = new Chunk(new Vector3f(pos.x, pos.y,pos.z));
            chunk.fillWithNoise();
            loadedChunks.add(chunk);
            genQueue.add(chunk);
        }
        return chunk;

    }
    private Chunk returnChunkAtLoacation(Vector3f position){
        for (Chunk oldchunk:loadedChunks){

            if(oldchunk.getPosition().x == position.x && oldchunk.getPosition().y == position.y &&oldchunk.getPosition().z == position.z   ){

                return oldchunk;
            }
        }
      //  System.out.println("No chunk found at location");
        return null;
    };
    public void handleGeneration(){
        if (System.currentTimeMillis()- lastGenTime > genDelay){

            Chunk c = genQueue.pollLast();
            if (c != null){
                if (c.getSisterChunk(Chunk.ABOVE) == null || c.getSisterChunk(Chunk.BELOW) == null || c.getSisterChunk(Chunk.RIGHT ) == null || c.getSisterChunk(Chunk.LEFT) == null || c.getSisterChunk(Chunk.FORWARDS) == null || c.getSisterChunk(Chunk.BACKWORDS) ==null)  {
                    genQueue.addFirst(c);
                   return;
                }
                lastGenTime = System.currentTimeMillis();
                c.updateModel();
                c.needToupdate = true;
            }



        }

    }
    public Block getBlockAtWorldPosition(Vector3f pos){
        return  getBlockAtWorldPosition((int)pos.x,(int)pos.y,(int)pos.z);
    }
    public Block getBlockAtWorldPosition(float x,float y,float z){
        return  getBlockAtWorldPosition((int)x,(int)y,(int)z);
    }
    public Block getBlockAtWorldPosition(int x,int y , int z){
        System.out.println("Getting block at:" + x +" " + y + " " + z);
        Chunk c =loadChunk( getChunkposOfPlayerChunk(new Vector3f(x,y,z)));

        if (c != null){
            c.getPosition().print("Found chunk at: ");
            float bx = c.getPosition().x%x;
            float by =c.getPosition().y%y;
            float bz =c.getPosition().z%z;
            Vector3f blockPosinChunk = new Vector3f(bx,by,bz);
            Block b= c.getBlock(blockPosinChunk);

            System.out.println("block Pos In Chunk Should be: " + blockPosinChunk);
            return b;
           // c.getPosition().print("Chunk");
        }
        return  null;
    }
//    public void bufferModel(){
//
//    }
}
