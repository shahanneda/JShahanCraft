package com.shahancraft.graphics.shader;

import com.shahancraft.math.Matrix4f;
import com.shahancraft.math.Vector3f;
import sun.security.provider.SHA;

/**
 * Created by shahan on 11/22/2017.
 */
public class WorldShader extends Shader {

    private static final String VERTEX_FILE = "worldVertex.vs";
    private static final String FRAGMENT_FILE = "worldFragment.fs";

    public WorldShader(){
        super();

        addVertexShader(Shader.loadShader(VERTEX_FILE));
        addFragmentShader(Shader.loadShader(FRAGMENT_FILE));
        compileShader();
        addUniform("worldMatrix");
        addUniform("projectionMatrix");

        addUniform("viewMatrix");

        addUniform("sun");

    }

    @Override
    public void bindAttributes() {
        bindAtribute(0,"position");
        bindAtribute(1,"color");
        bindAtribute(2,"normal");
    }


    public void updateWorldMatrix(Matrix4f aworldmatrix){
        setUniform("worldMatrix",aworldmatrix);
    }

    public void updateProjectionMatrix(Matrix4f worldMatrix){
        setUniform("projectionMatrix",worldMatrix);
    }
    public void updateSun(Vector3f sun){
        setUniform("sun",sun);
    }
    public void updateViewMatrix(Matrix4f viewMatrix){
        setUniform("viewMatrix", viewMatrix);
    }
}



