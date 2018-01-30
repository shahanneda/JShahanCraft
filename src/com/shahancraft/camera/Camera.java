package com.shahancraft.camera;

import com.shahancraft.math.Vector3f;

/**
 * Created by shahan on 11/21/2017.
 */
public class Camera {
    private  static final Vector3f yAixis = new Vector3f(0,1,0);

    public Vector3f getForward() {
        return forward;
    }

    public Vector3f getUp() {
        return up;
    }

    private  Vector3f forward;
    private Vector3f up;
    public Vector3f getPos() {
        return pos;
    }
    public  Camera(Vector3f position){
        pos =position;

        forward = new Vector3f(0,0,1);
        up = new Vector3f(0,1,0);
    }
    public Camera(){
        this(new Vector3f(0,0,0));
    }
    public void setPos(Vector3f pos) {
        this.pos = pos;
    }
    public void move(float dx,float dy,float dz){
//        pos.x+= dx;
//        pos.y += dy;
//        pos.z+= dz;
        Vector3f relXAxis = yAixis.cross(forward).normalized();
        Vector3f relZAxis = relXAxis.cross(yAixis).normalized();

        float x = relXAxis.x * dx+ relZAxis.x * dz;
        float y = dy;
        float z = relXAxis.z * dx +relZAxis.z * dz;
        pos.x += x;
        pos.z += z;
        pos.y += y;

    }
    private Vector3f pos;



    public void rotateY(float angle){
        Vector3f horAixis = yAixis.cross(forward).normalized();
        forward = forward.rotate(yAixis,angle);
        up = forward.cross(horAixis).normalized();
    }
    public void rotateX(float angle){
        Vector3f horAixis = yAixis.cross(forward).normalized();
        forward = forward.rotate(horAixis,angle);
        up = forward.cross(horAixis).normalized();
    }

}

