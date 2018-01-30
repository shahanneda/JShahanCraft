package com.shahancraft.graphics.model;
import com.shahancraft.math.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;import java.io.FileReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;
public abstract class Model {


    private  int vbo; //will point to where our buffer of vertexs is on the graphics card (vertex buffer object)
    private  int size;
    private  int ibo; // indices

    private  int usage;
    public Model(int usage){
        vbo = glGenBuffers();//getnerate a location for it on the graphic card
        ibo = glGenBuffers();//getnerate a location for it on the graphic card
        size = 0;

        this.usage = usage;
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
        glBindBuffer(GL_ARRAY_BUFFER,vbo);//load it up
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW); // add the buffer data
        glBindBuffer(GL_ARRAY_BUFFER,0); //unbind it so we dont actienly lose out stuff

        IntBuffer ibuffer = BufferUtils.createIntBuffer(indices.length);
        ibuffer.put(indices);
        ibuffer.flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,ibuffer,GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);

        size = indices.length;

    }
    protected void bufferVertices(FloatBuffer buffer){
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glBufferData(GL_ARRAY_BUFFER,buffer,usage);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }
    protected void bufferIndices(IntBuffer buffer){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,usage);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
    }
    protected void setSize(int size){
        this.size = size;
    }

    public int getIbo(){
        return ibo;
    }

    public int getVbo() {
        return vbo;
    }

    public int getSize() {
        return size;
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
    public static Model loadModel(String fileName){


        EntityModel res = new EntityModel();
        ArrayList<EntityVertex> vertices = new ArrayList<EntityVertex>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
            BufferedReader reader = null;
            try{
                reader= new BufferedReader(new FileReader("./res/models/" + fileName));
                String line;
                while((line = reader.readLine()) != null){
                    if (line.startsWith("v ")){
                        String[] things = line.split(" ");
                        vertices.add(new EntityVertex(Float.parseFloat(things[1]),Float.parseFloat(things[2]),Float.parseFloat(things[3]),new Vector3f(0,0,0)));
                    }else if (line.startsWith("f ")){
                        String[] things = line.split(" ");
                        indices.add(Integer.parseInt(things[1])-1);
                        indices.add(Integer.parseInt(things[2])-1);
                        indices.add(Integer.parseInt(things[3])-1);
                    }
                }
                reader.close();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Failed in loading file");
                System.exit(1);
            }
            EntityVertex[] v = new EntityVertex[vertices.size()];
            for (int i = 0; i<v.length; i++){v[i] = vertices.get(i);}

            int[] i = new int[indices.size()];
            for (int j = 0; j<i.length; j++){i[j] = indices.get(j);}

            res.bufferVertices(v,i,true);
            return res;



    }


}
