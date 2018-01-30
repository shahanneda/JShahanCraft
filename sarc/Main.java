import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.shahancraft.camera.Camera;
import com.shahancraft.entity.Entity;
import com.shahancraft.graphics.*;
import com.shahancraft.graphics.model.Model;
import com.shahancraft.input.Input;
import com.shahancraft.math.Vector3f;
import com.shahancraft.texture.Texture;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.*;

public class Main {
    private GLFWErrorCallback errorCallback;
    private boolean running = false;
    private MasterRenderer renderer;
    private Camera camera;
    private Input input;
    private float windowwidth = 1200;
    private float windowHeght = 720;
    Window window;


    //TEMP
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    //TEMP

    public Main(){
        init();

        renderer = new MasterRenderer();
        Model cubeModel = Model.loadModel("cube.obj");
        Random random  = new Random();
        input = new Input(window);

        camera = new Camera();
        for (int i = 0; i < 50; i++){


            for (int j = 0; j <50; j++){
                entities.add(new Entity(new Vector3f(i*(2.0f),1,j*(2.0f)),cubeModel));
            }
           // entities.add(new Entity(new Vector3f(random.nextInt(400),random.nextInt(300)-500,300),cubeModel));

        }
//       entities.add(new Entity(new Vector3f(0,0,5),cubeModel));
        window.render();
    }
    private void init(){
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        glfwInit();

        window = new Window(1200,720,"ShahanCraft");

    }
    private void cleanUp(){
        System.out.println("Cleaning Up!");
        renderer.cleanUp();
        window.hide();
        window.dispose();
    }
    public void start(){
        if(!running){running=true;}
        run();
    }
    public void stop(){
        System.out.println("STOPING!");
        running = false;


        }
    private  void run(){
        long lastTime = System.nanoTime();
        long curTime = lastTime;
        long timer = System.currentTimeMillis();
        long diff = 0;

        double ns = 1000000000/60.0;
        double delta = 0.0;

        double dfps = 1000000000/5000.0;
        int fps = 0;
        double d = 0.0;

        int ups = 0;
        while(running){
            //TODO: make fps cap changble
            //shsould call input
            curTime= System.nanoTime();
            diff = curTime - lastTime;
            delta += diff/ns;
            d+=diff/dfps;
            lastTime=curTime;

            while(delta>=1.0){
                input();
                update();
                ups++;
                delta--;
            }

            if (d>= 1.0){
                render();
                fps++;
                d=0.0f;
            }

            if(System.currentTimeMillis() > (timer +1000)){
                timer = System.currentTimeMillis();
                window.setTitle("ShahanCraft -- ups: " + ups + "|| fps: " + fps);
                fps=0;
                ups=0;
            }
        }
        cleanUp();
    }
    private void input(){
        glfwPollEvents();
        if (window.shouldClose()){stop();}
        float mov_speed = 0.5f;
        double rot_speed = 0.1;
        float dx = 0;
        float dy= 0;
        float dz =0;
        //reset teh mouse cureser
        System.out.println("Center should be: " + Window.windowWidth/2);
        System.out.println("Mouse posx is:" + input.frameX);
       // System.out.println("Last Frame input x: " + input.frameX + "delta x: " + ( 1200/2- input.frameX) );
       // if  (input.keys[GLFW_KEY_L])input.setMousePosToCenter();
        double dmousex = (Window.windowWidth/2) - input.frameX;


        double dmousey = (Window.windowHight/2) - input.frameY;
        input.setMousePosToCenter();

        if (input.keys[GLFW_KEY_W]) dz += mov_speed;
        if (input.keys[GLFW_KEY_S]) dz -= mov_speed;
        if (input.keys[GLFW_KEY_A]) dx -= mov_speed;
        if (input.keys[GLFW_KEY_D]) dx += mov_speed;
        if (input.keys[GLFW_KEY_SPACE]) dy += mov_speed;
        if (input.keys[GLFW_KEY_LEFT_SHIFT]) dy -= mov_speed;

        camera.rotateX(-(float)(dmousey*rot_speed));
        camera.rotateY(-(float)(dmousex*rot_speed));
        camera.move(dx,dy,dz);
//        if (input.keys[GLFW_KEY_UP])camera.rotateX(-rot_speed);
//        if (input.keys[GLFW_KEY_DOWN])camera.rotateX(rot_speed);
//        if (input.keys[GLFW_KEY_LEFT])camera.rotateY(-rot_speed);
//        if (input.keys[GLFW_KEY_RIGHT])camera.rotateY(rot_speed);
    }
    private void update(){

    }
    private void render(){

        for (int i =0; i <entities.size(); i++){

            renderer.processEntity(entities.get(i));
        }
        renderer.render(camera,new Vector3f(1,1,0.5f).normalized());
        window.render();
    }
    public static void main(String[] args){


        Main main = new Main();
        main.start();


    }

}
