package pointselector;

import java.nio.IntBuffer;
import pointselector.Util.Direction;
import static pointselector.Util.findNearestPoint;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.system.MemoryStack.stackPush;
import org.lwjgl.system.MemoryUtil;

/**
 *
 * @author Charles
 */
public class PointSelector {
    
    private long window;
    static final int WIDTH = 650, HEIGHT = 650;
    private Node[] nodes;
    private Selector selector;
    
    public PointSelector() {
        initWindow();
        initInput();
        initMapData();
        renderLoop();
    }
    
    private void renderLoop() {
        double lastTime = glfwGetTime();
        
        while(glfwWindowShouldClose(window)== false) {
            double currentTime = glfwGetTime();
            if(currentTime - lastTime >= 1.0)
                lastTime = currentTime;
            
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            {
                for(int i = 0; i < nodes.length; i++)
                    nodes[i].render();
                selector.render();
            }
            glfwSwapBuffers(window);
        }
        glfwTerminate();
    }
    
    private void initMapData() {
        //CHANGE NODES HERE ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        nodes = new Node[23*23];
//        nodes[0] = new Node(0,0);
//        int y = 0;
//        for(int i = 1; i < nodes.length; i++) {
//            y += (i%23==0 ? 1 : 0);
//            int x = i*30-y*30*23;
//            nodes[i] = new Node(x, y*30);
//        }
        nodes = new Node[] {
            new Node(11,400),
            new Node(137, 253),
            new Node(222,222),
            new Node(20, 74),
            new Node(77,86),
            new Node(80, 110),
            new Node(300, 430),
            new Node(478,384),
            new Node(444, 444),
            new Node(354,182),
            new Node(172, 150),
            new Node(45,317),
            new Node(401, 8)
        };
        selector = new Selector(nodes[0]);
    }
    
    private void initInput() {
        GLFWKeyCallback keyCallback;
        
        glfwSetKeyCallback(window, keyCallback = GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
            if(action!=GLFW_RELEASE)
                return;
            Direction dir;
            switch(key) {
                case GLFW_KEY_UP:
                    dir = Direction.UP;
                    break;
                case GLFW_KEY_DOWN:
                    dir = Direction.DOWN;
                    break;
                case GLFW_KEY_LEFT:
                    dir = Direction.LEFT;
                    break;
                case GLFW_KEY_RIGHT:
                    dir = Direction.RIGHT;
                    break;
                default:
                    return;
            }
            selector.setFocusNode(findNearestPoint(nodes, selector.focus, dir));
        }));
    }
    
    private void initWindow() {
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit())
            System.err.println("Falied to init glfw");
        
        glfwDefaultWindowHints();
        window = glfwCreateWindow(WIDTH, HEIGHT, "Map Selector", MemoryUtil.NULL, MemoryUtil.NULL);
        if(window == MemoryUtil.NULL)
            System.err.println("Falied to create windowID");
        try(MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            
            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth.get(0))/2,
                (vidmode.height() - pHeight.get(0))/2
            );
        }
        
        glfwMakeContextCurrent(window);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_FALSE);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        
        GL.createCapabilities();
        glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
    }
    
    public static void main(String[] args) throws Exception {
        PointSelector ms = new PointSelector();
    }
}
