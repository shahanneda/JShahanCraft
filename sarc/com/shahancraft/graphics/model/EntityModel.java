package com.shahancraft.graphics.model;

import com.shahancraft.math.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by shahan on 11/22/2017.
 */
public class EntityModel extends Model {
    public EntityModel(){
        super(GL15.GL_STATIC_DRAW);
    }
    public void bufferVertices(EntityVertex[] vertices, int[] indices){
        bufferVertices(vertices,indices,false);
    }
    public void bufferVertices(EntityVertex[] vertices, int[] indices, boolean calcNormals){
        if (calcNormals){
            calcNormals(vertices,indices);
        }
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * EntityVertex.SIZE); // we create a buffer to store all of the vertices of the model

        for(EntityVertex entityVertex : vertices){//we put each vertices in the buffer
            buffer.put(entityVertex.getPos().x);
            buffer.put(entityVertex.getPos().y);
            buffer.put(entityVertex.getPos().z);
            buffer.put(entityVertex.getNormal().x);
            buffer.put(entityVertex.getNormal().y);
            buffer.put(entityVertex.getNormal().z);
        }
        buffer.flip(); // flip it so the graphic card can read it

        IntBuffer ibuffer = BufferUtils.createIntBuffer(indices.length);
        ibuffer.put(indices);
        ibuffer.flip();
        bufferVertices(buffer);
        bufferIndices(ibuffer);

        setSize(indices.length);


    }
    private  void calcNormals(EntityVertex[] vetices, int[] indices){
        for (int i =0; i<indices.length;i+=3){//plus 3 each time becuase we are going to be calculating the lighing for every single tirangle
            int i0 = indices[i];//first vertex of triangle
            int i1 = indices[i+1];//second vertex of triangle
            int i2 = indices[i+2];//thord vertex of triangle

            Vector3f v1 = vetices[i1].getPos().sub(vetices[i0].getPos());
            Vector3f v2 = vetices[i2].getPos().sub(vetices[i0].getPos());

            Vector3f normal =v1.cross(v2).normalized();

            vetices[i0].setNormal(vetices[i0].getNormal().add(normal));
            vetices[i1].setNormal(vetices[i1].getNormal().add(normal));
            vetices[i2].setNormal(vetices[i2].getNormal().add(normal));
        }

        for (int i = 0; i <vetices.length;i++){
            vetices[i].setNormal(vetices[i].getNormal().normalized());
        }
    }


}
