import com.shahancraft.camera.Camera;
import com.shahancraft.entity.Entity;
import com.shahancraft.graphics.MasterRenderer;
import com.shahancraft.graphics.Window;
import com.shahancraft.graphics.model.Vertex;
import com.shahancraft.input.Input;
import com.shahancraft.math.Vector2f;
import com.shahancraft.math.Vector3f;
import com.shahancraft.texture.Texture;
import com.shahancraft.world.NewChunkLoader;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    private GLFWErrorCallback errorCallback;
    private boolean running = false;
    private MasterRenderer renderer;
    private Camera camera;
    private Input input;
    private float windowwidth = 1200;
    private float windowHeght = 720;
    public static boolean updateChunk;
    Window window;
    Texture dirtTexture;
    Texture grassTexture;

    //TEMP
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    public Main() {
        init();
        dirtTexture = new Texture("dirtBlock.png");
        grassTexture = new Texture("grassBlock.png");
        renderer = new MasterRenderer();

//        Vertex vertices[] = {
//                // front
//                new Vertex(new Vector3f(0.5f, 0.5f, 0.0f), new Vector2f(1, 1)),//Botttom left
//                new Vertex(new Vector3f(0.5f, -0.5f, 0.0f), new Vector2f(1, 0)),//bottom right
//                new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector2f(0, 0)),//top right
//                new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f), new Vector2f(0, 1))//top left
//
//        };
        Vertex vertices[] = {
                // front
                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(1,1)),//Botttom left 0
                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.666f,1)),//bottom right 1
                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.666f,0.666f)),//top right 2
                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(1,0.666f)),//top left 3
                //back
                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(1,1)), //4
                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0.666f,1)),//5
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.666f,0.666f)),//6
                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(1,0.666f)),//7


                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),
                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0.333f,0)),
                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(0.333f,0.333f)),//bottom right
                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,0.333f)),//Botttom left
                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0,0)),

                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.3333f,1)),
                new Vertex(new Vector3f(-1.0f, -1.0f,  1.0f),new Vector2f(0,1)),//Botttom left
                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.666f)),//top left
                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0.33f,0.666f)),
                new Vertex(new Vector3f(-1.0f, -1.0f, -1.0f),new Vector2f(0.33f,1)),//done

                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(1,1)),//bottom right
                new Vertex(new Vector3f( 1.0f, -1.0f,-1.0f),new Vector2f(0.666f,1)),
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.666f,0.666f)),
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.666f,0.666f)),
                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(1,0.666f)),
                new Vertex(new Vector3f( 1.0f, -1.0f,  1.0f),new Vector2f(1,1)),

                //TOP
                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left
                new Vertex(new Vector3f( 1.0f,  1.0f,  1.0f),new Vector2f(0.3333f,0.6666f)),//front right
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
                new Vertex(new Vector3f( 1.0f,  1.0f, -1.0f),new Vector2f(0.3333f,0.3333f)),//back right
                new Vertex(new Vector3f(-1.0f,  1.0f, -1.0f),new Vector2f(0,0.3333f)),//back left
                new Vertex(new Vector3f( -1.0f, 1.0f,  1.0f),new Vector2f(0,0.6666f)),//front left





                //BOTTOM

        };

        int indicies[] = {
                // front
                0, 1, 2,
                2, 3, 0,

                8,9,10,//BOTTOM
                11,12,13,//BOTTOM

                14,15,16,
                17,18,19,

                20,21,22,
                23,24,25,

                26,27,28,
                29,30,31,

//                // right
//                1, 5, 6,
//                6, 2, 1,



                // front
                7, 6, 5,
                5, 4, 7,
                // left
//                4, 0, 3,
//                3, 7, 4
                // bottom
//                4, 5, 1,
//                1, 0, 4,
                // top
//                3, 2, 6,
//                6, 7, 3
    };

//        int indicies[] = {
//                // front
//            0, 1, 3,
//            1, 2, 3
//
//        };
       // Model cubeModel = new Model();
       //cubeModel = Model.loadModel("grass.obj");
        //cubeModel.bufferVertices(vertices,indicies);

//
//       Entity grass1 =  new Entity(new Vector3f(0,0,5),cubeModel,grassTexture);
//       Entity dirt1 =  new Entity(new Vector3f(0,0,3),cubeModel,dirtTexture);

//       renderer.processEntity(grass1);
//       renderer.processEntity(dirt1);
       input = new Input(window);
       camera = new Camera();

//        for (int i = 0; i < 16; i++){
//
//
//            for (int j = 0; j <16; j++){
//                renderer.processEntity(new Entity(new Vector3f(i*(2.0f),10,j*(2.0f)),cubeModel,grassTexture));
//            }
//            for (int j = 0; j <16; j++){
//                renderer.processEntity(new Entity(new Vector3f(i*(2.0f),8,j*(2.0f)),cubeModel,dirtTexture));
//            }
//            for (int j = 0; j <16; j++){
//                renderer.processEntity(new Entity(new Vector3f(i*(2.0f),6,j*(2.0f)),cubeModel,dirtTexture));
//            }
//            for (int j = 0; j <16; j++){
//                renderer.processEntity(new Entity(new Vector3f(i*(2.0f),4,j*(2.0f)),cubeModel,dirtTexture));
//            }
//           // entities.add(new Entity(new Vector3f(random.nextInt(400),random.nextInt(300)-500,300),cubeModel));
//
//        }

        //renderer.processEntity(new Entity(new Vector3f(0,0,0),cubeModel,grassTexture));

        window.render();
    }
    private void init(){
        //sglEnable(GL_TEXTURE_2D);
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

        double ns = 1000000000/60;
        double delta = 0.0;

        double dfps = 1000000000/50000.0;
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
        //System.out.println("Center should be: " + Window.windowWidth/2);
        //System.out.println("Mouse posx is:" + input.frameX);
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

        if (input.keys[GLFW_KEY_M]) NewChunkLoader.renderDistance ++;
        if (input.keys[GLFW_KEY_N]) NewChunkLoader.renderDistance--;
        if (input.keys[GLFW_KEY_ESCAPE]) System.exit(969);
        camera.rotateX(-(float)(dmousey*rot_speed));
        camera.rotateY(-(float)(dmousex*rot_speed));
        camera.move(dx,dy,dz);
       if (input.keys[GLFW_KEY_R])renderer.callTest(camera.getPos());
        if (input.keys[GLFW_KEY_L])renderer.randomShit = !renderer.randomShit;
//        if (input.keys[GLFW_KEY_DOWN])camera.rotateX(rot_speed);
//        if (input.keys[GLFW_KEY_LEFT])camera.rotateY(-rot_speed);
//        if (input.keys[GLFW_KEY_RIGHT])camera.rotateY(rot_speed);
    }
    private void update(){

    }
    private void render(){

        renderer.render(camera,new Vector3f(1,1,0.5f).normalized());
        window.render();
    }
    public static void main(String[] args){


        Main main = new Main();
        main.start();

        System.exit(0);
    }

}
