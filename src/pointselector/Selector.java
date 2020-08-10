package pointselector;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 *
 * @author Charles
 */
public class Selector {
    int x, y;
    Node focus;
    final static int SIZE = 10;
    final static int OFFSET = (Node.SIZE-SIZE)/2;
    public Selector(Node focus) {
        this.focus = focus;
        x = focus.x;
        y = focus.y;
    }
    
    public void render() {
        glBegin(GL_QUADS);
        glColor4f(1,.2f,0,0);
        glVertex2f(0+OFFSET+x, SIZE+OFFSET+y);
        glVertex2f(SIZE+OFFSET+x, SIZE+OFFSET+y);
        glVertex2f(SIZE+OFFSET+x, 0+OFFSET+y);
        glVertex2f(0+OFFSET+x, 0+OFFSET+y);
        glEnd();
    }
    
    public void setFocusNode(Node n) {
        focus = n;
        x = n.x;
        y = n.y;
    }
}
