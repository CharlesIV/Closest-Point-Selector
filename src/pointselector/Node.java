
package pointselector;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Charles
 */
public class Node {
    int x, y;
    float r = 0, g = 1, b = 1;
    final static int SIZE = 16;
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public void resetColor() {
        r = 0;
        g = 1;
        b = 1;
    }
    
    public void render() {
        glBegin(GL_QUADS);
        glColor4f(r,g,b,0);
        glVertex2f(0+x, SIZE+y);
        glVertex2f(SIZE+x, SIZE+y);
        glVertex2f(SIZE+x, 0+y);
        glVertex2f(0+x, 0+y);
        glEnd();
    }
}
