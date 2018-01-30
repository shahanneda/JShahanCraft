package com.shahancraft.graphics.shader;

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
        addUniform("sun");
        addUniform("viewMatrix");
    }

    @Override
    public void bindAttributes() {
        bindAtribute(0,"position");
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

