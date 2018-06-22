package com.shahancraft.graphics;

import com.shahancraft.camera.Camera;
import com.shahancraft.entity.Entity;
import com.shahancraft.graphics.model.Model;
import com.shahancraft.graphics.model.Vertex;
import com.shahancraft.graphics.shader.BasicShader;
import com.shahancraft.graphics.shader.Shader;
import com.shahancraft.math.Matrix4f;
import com.shahancraft.math.Transform;
import com.shahancraft.math.Vector2f;
import com.shahancraft.math.Vector3f;
import com.shahancraft.texture.Texture;
import com.shahancraft.world.Chunk;
import com.shahancraft.world.NewChunkLoader;
import com.shahancraft.world.block.Block;
import com.shahancraft.world.block.BlockType;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengles.GLES20.GL_TEXTURE0;

/**
 * Created by shahan on 11/19/2017.
 */
public class MasterRenderer {
   private BasicShader basicShader;
    //private Model model;
    private Model cubeModel;
    private Matrix4f projectionMatrix;
    private EntityRenderer entityRenderer;
    private Entity[] entitys;
    private int numberOfEntitys = 0;
    private  Model model;
    private  Texture grassTexture;
    private  Texture dirtTexture;
    private Light mainLight;
    public static NewChunkLoader sChunkLoader;
    public static boolean needToUpdateChunks = false;
    public boolean randomShit;
    //private ChunkLoader chunkLoader = new ChunkLoader();
    private NewChunkLoader newChunkLoader;
    private ArrayList<Vertex> cubeVertices= new ArrayList<Vertex>();
    private ArrayList<Integer> cubeIndecies= new ArrayList<Integer>();
    public MasterRenderer()  {
        init();
        basicShader = new BasicShader();


        newChunkLoader = new NewChunkLoader();
        MasterRenderer.sChunkLoader = newChunkLoader;
        entitys = new Entity[4096];

        Entity testEntity = new Entity(new Vector3f(0,0,0),cubeModel,grassTexture);
        processEntity(testEntity);
        updateProjection(70,1280,720,0.1f,1000);

//        testchunk=new Chunk(new Vector3f(0,0,0));
//        testchunk.setBlock(0,0,0,new Block(BlockType.GRASS,new Vector3f(0,0,0),testchunk));
//        testchunk.updateModel();
//        testchunk.fillWithDirt();
//        renderChunk(testchunk);



    }
    public void AddChunk(Chunk chunck  ){

    }
    public void callTest(Vector3f playerpos){
       // chunkLoader.TestChunk(playerpos);
        newChunkLoader.Test(playerpos);
    }
    public void render(Camera camera,Vector3f sun){
        Matrix4f viewMatrix = Transform.getViewMatrix(camera);
        prepare();
        basicShader.bind();;
//        testchunk.updateModel();
      //  mainLight.setPosition(camera.getPos());
        basicShader.loadLight(mainLight);
        glActiveTexture(GL_TEXTURE0);
//        renderChunk(testchunk);
        Chunk[] chunks = newChunkLoader.GetLoadedChunk(camera.getPos());
        for(int i=0;i<chunks.length;i++){
            renderChunk(chunks[i]);
        }



        basicShader.updateViewMatrix(viewMatrix);
        for (int i = 0; i < numberOfEntitys; i++) {
            loadModel(entitys[i].getModel());//load model in to the vbo
            Texture texture = entitys[i].getTexture();
            texture.bind(0);
            basicShader.updateWorldMatrix(Transform.getTransformation(entitys[i].getPos(),0,0,0,1));
            glDrawElements(GL_TRIANGLES, entitys[i].getModel().getSize(), GL_UNSIGNED_INT, 0);//render it

        }
//      destroy blocks in 3*3
        if (randomShit) {
            
            Block b = newChunkLoader.getBlockAtWorldPosition(camera.getPos().x, camera.getPos().y - 1, camera.getPos().z);

            if (b != null && b.getBlockType() != BlockType.AIR) {
                Chunk c = b.getChunk();
                for (int x = 0; x<16; x++){
                    for (int y = 0; 0<16;y++){
                        for (int z=0;z<16;z++){
                            Block nb = c.getBlock(x,y,z);
                            if (nb != null){
                                nb.setBlockType(BlockType.AIR);

                            }
                        }
                    }
                }
                b.getPosition().print("Block at: ");
                b.getChunk().updateModel();
                b.getChunk().needToupdate = true;
            }
        }
//        Chunk c = newChunkLoader.loadChunk(newChunkLoader.getChunkposOfPlayerChunk(camera.getPos()));
//        if (c!=null&& MasterRenderer.needToUpdateChunks) {
//            needToUpdateChunks = false;
//            boolean needToChangeSomething = false;
//            c.getPosition().print("Chunk");
//            camera.getPos().print("Camera");
//
//            if (needToChangeSomething){
//                c.updateModel();
//            }
//
//        }
//
        unloadModel();;
        Shader.unbind();

    }
    public void processEntity(Entity entity){
        entitys[numberOfEntitys] = entity;
        numberOfEntitys ++;
    }
    private  void prepare(){
        glClearColor(0.21f,0.788f,0.95f,1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    private void init(){
        glEnable(GL_DEPTH_TEST);
        mainLight = new Light(new Vector3f(0,100,0),new Vector3f(1,1,1));
//        glEnable(GL_CULL_FACE);
//        glCullFace(GL_FRONT_RIGHT);

//        Vertex vertices[] = {
//                // front
//                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//Botttom left 0
//                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0f,1)),//bottom right 1
//                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0f,0.666f)),//top right 2
//                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0.333f,0.666f)),//top left 3
//                //back
//                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.333f,1)), //4
//                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0f,1)),//5
//                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),//6
//                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.333f,0.666f)),//7
//
//
//                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
//                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0.333f,0)),
//                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
//                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
//                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,0.333f)),//Botttom left
//                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
//
//                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.3333f,1)),
//                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,1)),//Botttom left
//                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
//                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
//                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.33f,0.666f)),
//                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.33f,1)),//done
//
//                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//bottom right
//                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f), new Vector2f(0,1)),
//                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),
//                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),
//                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.333f,0.666f)),
//                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//done
//
//                //TOP
//                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
//                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.3333f,0.6666f)),//front right
//                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
//                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
//                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0,0.3333f)),//back left
//                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
//
//                ///////////////////////////////////////////////////
////                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//Botttom left 0
////                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0f,1)),//bottom right 1
////                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0f,0.666f)),//top right 2
////                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0.333f,0.666f)),//top left 3
////                //back
////                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.333f,1)), //4
////                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0f,1)),//5
////                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),//6
////                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.333f,0.666f)),//7
////
////
////                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
////                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0.333f,0)),
////                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
////                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
////                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,0.333f)),//Botttom left
////                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
////
////                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.3333f,1)),
////                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,1)),//Botttom left
////                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
////                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
////                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.33f,0.666f)),
////                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.33f,1)),//done
////
////                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//bottom right
////                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f), new Vector2f(0,1)),
////                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),
////                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),
////                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.333f,0.666f)),
////                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//done
////
////                //TOP
////                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
////                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.3333f,0.6666f)),//front right
////                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
////                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
////                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0,0.3333f)),//back left
////                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
//
//
//
//
//                //BOTTOM
//
//        };
//        int indicies[] = {
//                // front
//                0, 1, 2,
//                2, 3, 0,
//
//                7, 6, 5,
//                5, 4, 7,
//
//                8,9,10,//BOTTOM
//                11,12,13,//BOTTOM
//
//                14,15,16,
//                17,18,19,
//
//                20,21,22,
//                23,24,25,
//
//                26,27,28,
//                29,30,31,
//
//
//
//
//
//
//
//
//        };

        Vertex[] frontVertices = {//FrontFace
                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//Botttom left 0
                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0f,1)),//bottom right 1
                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0f,0.666f)),//top right 2
                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0f,0.666f)),//top right 2
                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0.333f,0.666f)),//top left 3
                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//Botttom left 0
        };


        //32 vetices
        int[] frontIndicies = {
                0, 1, 2,
                2, 3, 0,

        };

        int[] backIndicies = {
                0, 1, 2,
                3, 4, 5,


        };

        Vertex[] backVetices={
                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.333f,1)), //4
                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0f,1)),//5
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),//6
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),//6
                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.333f,0.666f)),//7
                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.333f,1)), //4
        };
        Vertex[] bottomV = {

                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0.333f,0)),
                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,0.333f)),//Botttom left
        };
        int[] bottomI = {
          0,1,2,
          3,4,5
        };

        Vertex[] rightV = {

                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.3333f,1)),
                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,1)),//Botttom left
                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.33f,0.666f)),
                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.33f,1)),//done
        };
        int[] topi ={
                0,1,2,
                3,4,5
        };

        Vertex[] leftV = {
                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//bottom right
                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f), new Vector2f(0,1)),
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0f,0.666f)),
                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.333f,0.666f)),
                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,1)),//done
        };

        Vertex[] topV = {
                                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.3333f,0.6666f)),//front right
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0,0.3333f)),//back left
                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
        };

//        for (int i = 0; i < vertices.length; i++){
//           vertices[i].setTexCoord(new Vector2f(vertices[i].getTexCoord().x/3,vertices[i].getTexCoord().y));
//        }

         cubeModel = new Model();
        Vertex[] finalv = cubeVertices.toArray(new Vertex[cubeVertices.size()]);
        int[] finalindices = cubeIndecies.stream().mapToInt(i->i).toArray();
        //cubeModel.bufferVertices(finalv,finalindices);
        grassTexture = new Texture("grassBlock.png");

    }

    public void addFaceToArrays(Vertex[] vertices){
        for (Vertex vertex : vertices){
            vertex.setTexCoord(new Vector2f(vertex.getTexCoord().x/3,vertex.getTexCoord().y));
            cubeVertices.add(vertex);
            if (cubeIndecies.size()>0){
                int l = cubeIndecies.get(cubeIndecies.size() - 1);
                cubeIndecies.add(l+1);
            }else{
                cubeIndecies.add(0);
            }
        }



    }

    public void cleanUp(){

    }
    private void updateProjection(float fov, float width, float height, float zNear, float zFar){
        projectionMatrix = Transform.getPrespectiveProjection( fov,  width,  height,  zNear,  zFar);
        basicShader.bind();
        basicShader.updateProjectionMatrix(projectionMatrix);
        Shader.unbind();

    }

    private void loadModel(Model model){
        glBindBuffer(GL_ARRAY_BUFFER, model.getVbo());  //open up the vertices locaiton on the garpich card
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, model.getIbo());//open up the indices locaiton on the garpich card


        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0,3,GL_FLOAT,false, 0,0);//tell open gl how to look at each indiviala atribuatr in the array(what atrribiuate array, what is the size of each element in the array, what is the type o feachelement
        //glVertexAttribPointer(1,3,GL_FLOAT,false, Vertex.SIZE*4,12);
        // ACTUAL glVertexAttribPointer(0,3,GL_FLOAT,false, Vertex.SIZE*4,0);



        glBindBuffer(GL_ARRAY_BUFFER,model.getTbo());

        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1,2,GL_FLOAT,false, 0,0);

        glBindBuffer(GL_ARRAY_BUFFER, model.getNbo());
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2,3,GL_FLOAT,false, 0,0);
    }

    private void unloadModel(){
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);//close the thing
    }

    public void renderChunk(Chunk chunk){
        //System.out.print("Rending Chunk");
        if (chunk.getModel().getSize() == 0){
            return;
        }
        loadModel(chunk.getModel());

        grassTexture.bind(0);

        basicShader.updateWorldMatrix(chunk.transformation);
        glDrawElements(GL_TRIANGLES,chunk.getModel().getSize(), GL_UNSIGNED_INT,0);
//        for (int x = 0; x<15;x++){
//            for (int y = 0; y<f15;y++){
//                for (int z = 0; z<15;z++){
//
//                    Block block = chunk.getBlock(x, y, z);
//                    block.updateVisablity();
//                    if (block.isVisable){
//
//
//                        switch (block.getBlockType()){
//                            case GRASS:
//                                grassTexture.bind(0);
//                                break;
//                            case DIRT:
//                                dirtTexture.bind(0);
//                                break;
//                            case AIR:
//                                continue;
//
//
//                        }
//
//                        basicShader.updateWorldMatrix(block.transformation);
//                        glDrawElements(GL_TRIANGLES, cubeModel.getSize(), GL_UNSIGNED_INT,0);
//
//                       //render it
//                    }else  {
//                        //System.out.println("block: " + x+ ""+y +""+z+" not visable");
//                    }
//                }
//            }
//        }



    }

}
