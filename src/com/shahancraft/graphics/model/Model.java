package com.shahancraft.graphics.model;
import com.shahancraft.math.Vector2f;
import com.shahancraft.math.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public  class Model {


    private  int vbo; //will point to where our buffer of vertexs is on the graphics card (vertex buffer object)
    private  int size;
    private  int ibo; // indices
    private  int nbo;
    public int getTbo() {
        return tbo;
    }

    private  int tbo;

    public Model(){
        vbo = glGenBuffers();//getnerate a location for it on the graphic card
        ibo = glGenBuffers();//getnerate a location for it on the graphic card
        tbo = glGenBuffers();
        nbo = glGenBuffers();
        size = 0;


    }

    public int getNbo() {
        return nbo;
    }

    public void OUTDATEDbufferVertices(Vertex[] vertices, int[] indices){

        LinkedList<Vector3f> positions = new LinkedList<Vector3f>();
        LinkedList<Vector2f> textCoords = new LinkedList<Vector2f>();
        for (int i = 0; i < vertices.length;i++){
            positions.addLast(vertices[i].getPos());
            //System.out.println("getting tex cord :" + vertices[i].getTexCoord().x + "," + vertices[i].getTexCoord().y);
            textCoords.addLast(vertices[i].getTexCoord());
        }
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.SIZE); // we create a buffer to store all of the vertices of the model
        for(int i = 0; i < positions.size();i++){//we put each vertices in the buffer
         Vector3f pos = positions.get(i);
           buffer.put(pos.x);
            buffer.put(pos.y);
            buffer.put(pos.z);
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

        FloatBuffer textBuffer = BufferUtils.createFloatBuffer(vertices.length * 2); // we create a buffer to store all of the vertices of the model
        for(int i = 0; i < textCoords.size();i++){//we put each vertices in the buffer
            textBuffer.put(textCoords.get(i).x);
            textBuffer.put(textCoords.get(i).y);
        }
        textBuffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER,tbo);
        glBufferData(GL_ARRAY_BUFFER,textBuffer,GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1,2,GL_FLOAT,false, 0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);

    }
    public void bufferVerticesFromBuffer(FloatBuffer buffer,IntBuffer ibuffer,FloatBuffer textBuffer,int _size,FloatBuffer nbuffer  ){


        glBindBuffer(GL_ARRAY_BUFFER,vbo);//load it up
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW); // add the buffer data
        glBindBuffer(GL_ARRAY_BUFFER,0); //unbind it so we dont actienly lose out stuff


        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,ibuffer,GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);


        size = _size;



        glBindBuffer(GL_ARRAY_BUFFER,tbo);
        glBufferData(GL_ARRAY_BUFFER,textBuffer,GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1,2,GL_FLOAT,false, 0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
//        for(int i = 0; i<nbuffer.capacity();i++){
//            System.out.println(nbuffer.get(i));
//        }


        glBindBuffer(GL_ARRAY_BUFFER,nbo);//load it up
//        System.out.println("Vertex buffer: " + buffer.capacity());
//        System.out.println("Normal Buffer: " + nbuffer.capacity()) ;

        glBufferData(GL_ARRAY_BUFFER,nbuffer,GL_STATIC_DRAW); // add the buffer data
        glBindBuffer(GL_ARRAY_BUFFER,0); //unbind it so we dont actienly lose out stuff
//        for(int i = 0; i<nbuffer.capacity();i++){
//
//            System.out.println("Nbuffer: " + nbuffer.get(i));
//            System.out.println("VBuffer: " + buffer.get(i));
//        }
//

//        glBindBuffer(GL_ARRAY_BUFFER,nbo);
//        glBufferData(GL_ARRAY_BUFFER,nbuffer,GL_STATIC_DRAW);
//        glEnableVertexAttribArray(2);
//        glVertexAttribPointer(2,3,GL_FLOAT,false, 0,0);
//        glBindBuffer(GL_ARRAY_BUFFER,0);

    }

    protected void bufferVertices(FloatBuffer buffer){
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }
    protected void bufferIndices(IntBuffer buffer){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
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


    public static Model OUTDATEDloadModel(String fileName){


        Model res = new Model();

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Integer> indices = new ArrayList<Integer>();
            BufferedReader reader = null;
            try{
                reader= new BufferedReader(new FileReader("./res/models/" + fileName));
                String line;
                while((line = reader.readLine()) != null){
                    if (line.startsWith("v ")){
                        String[] things = line.split(" ");
                        vertices.add(new Vertex(Float.parseFloat(things[1]),Float.parseFloat(things[2]),Float.parseFloat(things[3])));
                    }else if (line.startsWith("f ")){
                        String[] things = line.split(" ");
                       for (int i = 1; i<4; i++){
                           String[] things2 = things[i].split("/");
                           System.out.println(things2[0]);
                          // System.out.println("adding indeci : "+things2[1]+ "UV :" +things2[2]);
                           indices.add(Integer.parseInt(things2[0])-1);

                       }
//                        indices.add(Integer.parseInt(things[2])-1);
//                        indices.add(Integer.parseInt(things[3])-1);

                    }
                }
                reader.close();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Failed in loading file");
                System.exit(1);
            }
            Vertex[] v = new Vertex[vertices.size()];
            for (int i = 0; i<v.length; i++){v[i] = vertices.get(i);}

            int[] i = new int[indices.size()];
            for (int j = 0; j<i.length; j++){i[j] = indices.get(j);}

            //res.bufferVertices(v,i);
            return res;



    }


}
