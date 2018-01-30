package com.shahancraft.graphics;

import com.shahancraft.entity.Entity;
import com.shahancraft.graphics.model.EntityVertex;
import com.shahancraft.graphics.model.Model;
import com.shahancraft.graphics.shader.BasicShader;
import com.shahancraft.math.Transform;
import com.shahancraft.math.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * Created by shahan on 11/19/2017.
 */
public class EntityRenderer {
    private   BasicShader shader;

    public EntityRenderer(BasicShader shader){
        this.shader = shader;
    }
    public void render(HashMap<Model, ArrayList<Entity>> entities) {
        for (Model model : entities.keySet()) {
            loadModel(model);//load model in to the vbo
            for (Entity entity : entities.get(model)) {
                loadInstance(entity);//load the position of the enitiy
                glDrawElements(GL_TRIANGLES, model.getSize(), GL_UNSIGNED_INT, 0);//render it
            }

            unloadModel();
            ;
        }
    }
    public void render(EntityVertex[] vertices,int[] indeices){
        EntityModel model = new EntityModel() ;
        model.bufferVertices(vertices,indeices);

        loadModel(model);//load model in to the vbo

        shader.updateWorldMatrix(Transform.getTransformation(new Vector3f(0,0,0),0,0,0,1));
        glDrawElements(GL_TRIANGLES,model.getSize(),GL_UNSIGNED_INT,0);//render it


        unloadModel();
        }
//        for (Entity entity : entities){
//
//            shader.updateWorldMatrix(Transform.getTransformation(entity.getPos(),45,45,45,1));
//
//            glBindBuffer(GL_ARRAY_BUFFER, entity.getModel().getVbo());  //open up the vertices locaiton on the garpich card
//            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, entity.getModel().getIbo());//open up the vertices locaiton on the garpich card
//
//            glEnableVertexAttribArray(0);
//            glVertexAttribPointer(0,3,GL_FLOAT,false, Vertex.SIZE*4,0);
//
//            glDrawArrays(GL_TRIANGLES,0,entity.getModel().getSize());
//            glDrawElements(GL_TRIANGLES,entity.getModel().getSize(),GL_UNSIGNED_INT,0);
//            glDisableVertexAttribArray(0);
//
//            glBindBuffer(GL_ARRAY_BUFFER,0);
//            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);//close the thing
//        }

    private void loadModel(Model model){
            glBindBuffer(GL_ARRAY_BUFFER, model.getVbo());  //open up the vertices locaiton on the garpich card
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, model.getIbo());//open up the indices locaiton on the garpich card

            glEnableVertexAttribArray(0);//tell which data to skip and which not to
            glEnableVertexAttribArray(1);

            glVertexAttribPointer(0,3,GL_FLOAT,false, EntityVertex.SIZE*4,0);//tell open gl how to look at each indiviala atribuatr in the array(what atrribiuate array, what is the size of each element in the array, what is the type o feachelement
            glVertexAttribPointer(1,3,GL_FLOAT,false, EntityVertex.SIZE*4,12);
        // enable normialing or not, how much data to skip to find our data, how much it needs to stary from the begning to find each attribuate )


    }
    private void unloadModel(){
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glBindBuffer(GL_ARRAY_BUFFER,0);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);//close the thing
    }
    private void loadInstance(Entity entity){
        shader.updateWorldMatrix(Transform.getTransformation(entity.getPos(),0,0,0,1));
    }

}
