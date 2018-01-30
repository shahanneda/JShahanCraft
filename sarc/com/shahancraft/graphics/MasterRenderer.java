package com.shahancraft.graphics;

import static org.lwjgl.opengl.GL11.*;

import com.shahancraft.camera.Camera;
import com.shahancraft.entity.Entity;
import com.shahancraft.graphics.model.Model;

import com.shahancraft.graphics.shader.BasicShader;
import com.shahancraft.graphics.shader.Shader;
import com.shahancraft.graphics.shader.WorldShader;
import com.shahancraft.math.Matrix4f;
import com.shahancraft.math.Transform;
import com.shahancraft.math.Vector3f;
import com.shahancraft.texture.Texture;
import com.shahancraft.world.Segment;
import com.shahancraft.world.tile.GrassTile;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shahan on 11/19/2017.
 */
public class MasterRenderer {
   private BasicShader basicShader;
    //private Model model;
    private EntityModel model;
    private Matrix4f projectionMatrix;
    private EntityRenderer entityRenderer;
    private HashMap<Model, ArrayList<Entity>> enteties= new HashMap<Model, ArrayList<Entity>>();

    private WorldShader worldShader;
    private WorldRenderer worldRenderer;
    Texture dirtTexture;


    //temp
    private Segment segment;
    public MasterRenderer(){
        init();
        basicShader = new BasicShader();
        worldShader = new WorldShader();



        entityRenderer = new EntityRenderer(basicShader);
        worldRenderer = new WorldRenderer(worldShader);
        updateProjection(70,1280,720,0.1f,1000);
        //model.bufferVertices(vertices,indicies);
       // model = EntityModel.loadModel("cube.obj");

        //temp
        segment = new Segment(0,0,0);

        for (int x = 0; x<16;  x ++){
            for (int z = 0;z<16;z++){
                segment.setTile(x,0,z,new GrassTile());
            }

        }

      // WorldModelGenerator.proccesSegmant(segment);

    }
    public void render(Camera camera,Vector3f sun){
        Matrix4f viewMatrix = Transform.getViewMatrix(camera);
        prepare();
        basicShader.bind();;
        basicShader.updateSun(new Vector3f(0,1,0));
        basicShader.updateViewMatrix(viewMatrix);
        entityRenderer.render(enteties);
        worldShader.bind();
        worldShader.updateViewMatrix(viewMatrix);
        worldShader.updateSun(sun);
        //worldRenderer.render(segment);
        Shader.unbind();
        // block shader.bind
        //chucnk render render
        //block shader unbind
        enteties.clear();
    }
    public void processEntity(Entity entity){


        Model model = entity.getModel();
        ArrayList<Entity> batch = enteties.get(model);
        if (batch == null){
            batch = new  ArrayList<Entity>();
            enteties.put(model,batch);
        }

        batch.add(entity);
    }
    private  void prepare(){
        glClearColor(0.21f,0.788f,0.95f,1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    private void init(){
        glEnable(GL_DEPTH_TEST);
    }
    public void cleanUp(){

    }
    private void updateProjection(float fov, float width, float height, float zNear, float zFar){
        projectionMatrix = Transform.getPrespectiveProjection( fov,  width,  height,  zNear,  zFar);

        basicShader.bind();
        basicShader.updateProjectionMatrix(projectionMatrix);

        worldShader.bind();;
        worldShader.updateProjectionMatrix(projectionMatrix);

        Shader.unbind();

    }
}
