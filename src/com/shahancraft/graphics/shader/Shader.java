package com.shahancraft.graphics.shader;

import com.shahancraft.math.Matrix4f;
import com.shahancraft.math.Vector2f;
import com.shahancraft.math.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
public abstract class Shader {

    private int program; // locaton where our shader is stored
    private HashMap<String,Integer> uniforms;
    public Shader(){
        program = glCreateProgram();

        uniforms = new HashMap<String,Integer>();
        if(program == 0){
            System.err.println("Shader Creation Failed! No Valid Memory");
            System.exit(1);
        }


    }
    public abstract void bindAttributes();
    protected void bindAtribute(int atribute,String varableNAme){
        glBindAttribLocation(program,atribute,varableNAme);
    }
    public void bind(){
        glUseProgram(program);//load the shader
    }
    public static void unbind(){
        glUseProgram(0); // unlaod the shader
    }
    protected void addVertexShader(String text){
        createShader(text,GL_VERTEX_SHADER);
    }
    protected void addFragmentShader(String text){
        createShader(text,GL_FRAGMENT_SHADER);
    }

    protected void compileShader(){
        glLinkProgram(program);

        if (glGetProgrami(program,GL_LINK_STATUS) == GL_FALSE){ //if hsader did not link then pprint out the output lof of opengl
            System.err.println(glGetShaderInfoLog(program,1024));
            System.out.print(glGetShaderInfoLog(program,1024));
            System.exit(1);
        }
        glValidateProgram(program);
        if (glGetProgrami(program,GL_VALIDATE_STATUS) == GL_FALSE){ //if hsader did not validate then pprint out the output lof of opengl
            System.err.println(glGetShaderInfoLog(program,1024));
            System.exit(1);
        }
        bindAttributes();
    }
    // THESE LINES set up the uniforms (varibale in saders)
    protected void addUniform(String uniform){
        int uniformLocation = glGetUniformLocation(program,uniform);

        if(uniformLocation == 0xFFFFFFF){
            System.err.println("ERROR: could not find unform" + uniform);
            System.exit(1);
        }
        uniforms.put(uniform,uniformLocation);
    }

    protected void setUniform(String uniformName, float value){
        glUniform1f(uniforms.get(uniformName),value);
    }
    protected void setUniform(String uniformName, Vector2f value){
        glUniform2f(uniforms.get(uniformName),value.x,value.y);
    }
    protected void setUniform(String uniformName, Vector3f value){
        glUniform3f(uniforms.get(uniformName),value.x,value.y,value.z);
    }
    protected void setUniform(String uniformName, Matrix4f value){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4*4);
        for (int i = 0;i <4; i++){
            for (int j = 0; j<4;j++){
                buffer.put(value.get(i,j));
            }
        }
        buffer.flip();
        glUniformMatrix4fv(uniforms.get(uniformName),false,buffer);
    }
    ////////////////////////////////////////////////////////
    private void createShader(String text, int type){ //Adds the shader to the program
        int shader = glCreateShader(type); // create a varaibl eso we have accses to the shader
        if (shader==0){
            System.err.println("Shader Creation Failed! No Valid Memory");
            System.exit(1);
        }
        glShaderSource(shader,text);//snend the text of the shader to the grapphics card
        glCompileShader(shader);//compile the shader

        if (glGetShaderi(shader,GL_COMPILE_STATUS) == 0){ //if hsader did not compile then pprint out the output lof of opengl
            System.err.println(glGetShaderInfoLog(shader,1024));
            System.exit(1);

        }
        glAttachShader(program,shader); // add the shader to the program so we can use the shade later (50% sure)
    }
    public static String loadShader(String fileName){//reads a file and returns a shaer string
        StringBuilder source = new StringBuilder();
        BufferedReader reader = null;
        try{
            reader= new BufferedReader(new FileReader("./res/shaders/" + fileName));
            String line;
            while((line = reader.readLine()) != null){
                source.append(line);
                source.append("\n");
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed in loading file");
            System.exit(1);
        }
        return source.toString();

    }
}
