package com.shahancraft.math;

import com.shahancraft.camera.Camera;

public class Transform {
    public static Matrix4f getPrespectiveProjection(float fov, float width, float height, float zNear, float zFar){
        return new Matrix4f().initPerspective( fov,  width,  height,  zNear,  zFar);

    }
    public static Matrix4f getTransformation(Vector3f translation, float rx, float ry, float rz, float scale){
        Matrix4f translationMatrix = new Matrix4f().initTranslation(translation);
        Matrix4f rotationMatrix = new Matrix4f().initRotation(rx,ry,rz);
        Matrix4f scaleMatrix = new Matrix4f().initScale(scale);


        return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
    }

    public static Matrix4f getViewMatrix(Camera camera){
        Vector3f pos = camera.getPos();

        Matrix4f translationMatrix = new Matrix4f().initTranslation(-pos.x,-pos.y,-pos.z);
        Matrix4f rotationMatrix = new Matrix4f().initRotation(camera.getForward(),camera.getUp());
        return rotationMatrix.mul(translationMatrix);
    }

}
