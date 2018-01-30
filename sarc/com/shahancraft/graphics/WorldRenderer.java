package com.shahancraft.graphics;

import com.shahancraft.entity.Entity;
import com.shahancraft.graphics.model.EntityVertex;
import com.shahancraft.graphics.model.Model;
import com.shahancraft.graphics.shader.WorldShader;
import com.shahancraft.math.Transform;
import com.shahancraft.math.Vector3f;
import com.shahancraft.world.Segment;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * Created by shahan on 11/22/2017.
 */
public class WorldRenderer {
    private WorldShader shader;

    public WorldRenderer(WorldShader shader){
        this.shader= shader;

    }
    public void render(Segment segment){

        WorldModel model = segment.getModel();
        loadModel(model);

        loadInstance(segment);//load it
        glDrawElements(GL_TRIANGLES,model.getSize(),GL_UNSIGNED_INT,0);//render it


        unloadModel();


    }
    private void loadModel(WorldModel model){
        glBindBuffer(GL_ARRAY_BUFFER, model.getVbo());  //open up the vertices locaiton on the garpich card
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, model.getIbo());//open up the indices locaiton on the garpich card

        glEnableVertexAttribArray(0);//tell which data to skip and which not to
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(0,3,GL_FLOAT,false, WorldVertex.SIZE*4,0);//tell open gl how to look at each indiviala atribuatr in the array(what atrribiuate array, what is the size of each element in the array, what is the type o feachelement
        glVertexAttribPointer(1,3,GL_FLOAT,false, WorldVertex.SIZE*4,12);
        glVertexAttribPointer(2,3,GL_FLOAT,false, WorldVertex.SIZE*4,24);
        // enable normialing or not, how much data to skip to find our data, how much it needs to stary from the begning to find each attribuate )


    }
    private void unloadModel(){
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER,0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);//close the thing
    }
    private void loadInstance(Segment segment){
        shader.updateWorldMatrix(Transform.getTransformation(new Vector3f(0,0,0),0,0,0,1));
    }
}
