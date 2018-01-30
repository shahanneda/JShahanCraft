package com.shahancraft.graphics.shader;

import com.shahancraft.graphics.Light;
import com.shahancraft.math.Matrix4f;
import com.shahancraft.math.Vector3f;

public class BasicShader extends Shader {
    private static final String VERTEX_FILE = "basicVertex.vs";
    private static final String FRAGMENT_FILE = "basicFragment.fs";

    public BasicShader() {
        super();
        addVertexShader(Shader.loadShader(VERTEX_FILE));
        addFragmentShader(Shader.loadShader(FRAGMENT_FILE));
        compileShader();
        addUniform("worldMatrix");
        addUniform("projectionMatrix");
        addUniform("viewMatrix");
        addUniform("texture_sampler");
        addUniform("lightColor");
        addUniform("lightPos");
    }

    @Override
    public void bindAttributes() {
        bindAtribute(0,"position");
        bindAtribute(1,"textCoord");
        bindAtribute(2,"normal");
    }

    public void updateWorldMatrix(Matrix4f aworldmatrix){
        setUniform("worldMatrix",aworldmatrix);
        setUniform("texture_sampler", 0);
    }
    public void updateLightColor(Vector3f color){
        setUniform("lightColor",color);
    }
    public void updateLightPosition(Vector3f lightPos){
        setUniform("lightPos",lightPos);
    }
    public void loadLight(Light light){
        updateLightColor(light.getColor());
        updateLightPosition(light.getPosition());
    }
    public void updateProjectionMatrix(Matrix4f worldMatrix){
        setUniform("projectionMatrix",worldMatrix);
    }
    public void updateViewMatrix(Matrix4f viewMatrix){
        setUniform("viewMatrix", viewMatrix);
    }
}

