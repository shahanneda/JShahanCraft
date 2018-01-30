package com.shahancraft.graphics.model;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.xmlgraphics.util.dijkstra.Vertex;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * Created by shahan on 11/22/2017.
 */
public class WorldModel extends Model {
    public WorldModel(){
        //TODO update world models between frames
        super(GL15.GL_DYNAMIC_DRAW);
    }
    public  void bufferVertices(ArrayList<WorldVertex> vertices,ArrayList<Integer> indices){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(WorldVertex.SIZE * vertices.size());

        for (WorldVertex vertex:vertices){
            buffer.put(vertex.getPos().x);
            buffer.put(vertex.getPos().y);
            buffer.put(vertex.getPos().z);
            buffer.put(vertex.getColor().x);
            buffer.put(vertex.getColor().y);
            buffer.put(vertex.getColor().z);
            buffer.put(vertex.getNormal().x);
            buffer.put(vertex.getNormal().y);
            buffer.put(vertex.getNormal().z);
        }
        buffer.flip();

        IntBuffer ibuffer = BufferUtils.createIntBuffer(indices.size());//TODO check code here
            for (int i :indices) ibuffer.put(i);
            ibuffer.flip();

            bufferVertices(buffer);
            bufferIndices(ibuffer);

            setSize(indices.size());

    }
}
