package com.shahancraft.graphics;

import com.shahancraft.entity.Entity;
import com.shahancraft.graphics.model.Model;
import com.shahancraft.graphics.shader.BasicShader;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * Created by shahan on 11/19/2017.
 */
public class EntityRenderer {
    private   BasicShader shader;

    public EntityRenderer(BasicShader shader){
        this.shader = shader;
    }
    public void render(HashMap<Model, ArrayList<Entity>> entities){

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
    }



}
