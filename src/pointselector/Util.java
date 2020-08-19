package pointselector;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Charles
 */
public class Util {
    
    private static float angleWeight = 5f;
    private static float distanceWeightX = .2f;
    private static float distanceWeightY = .2f;
    public enum Direction{UP, DOWN, LEFT, RIGHT};
    
    /**
     * Takes a list of points and finds the next "nearest" point. Using angleWeight, you can 
     * specify the importance of the angle of the next node being closer to 0 degrees.
     * 
     * @param points an array of Node (a renderable point) objects that have a x and y value.
     * @param focus the current node that is selected
     * @param dir the direction the next node should be in
     * @return 
     */
    public static Node findNearestPoint(Node[] points, Node focus, Direction dir) {
        List<Node> sorted = new ArrayList<>();
        switch(dir) {
            case UP:
                for (Node point : points) {
                    point.resetColor();
                    if (point.y > focus.y)
                        sorted.add(point);
                }
                break;
            case DOWN:
                for (Node point : points) {
                    point.resetColor();
                    if (point.y < focus.y) 
                        sorted.add(point);
                }
                break;
            case LEFT:
                for (Node point : points) {
                    point.resetColor();
                    if (point.x < focus.x)
                        sorted.add(point);
                }
                break;
            case RIGHT:
                for(Node point : points) {
                    point.resetColor();
                    if(point.x > focus.x)
                        sorted.add(point);
                }
                break;
        }
        focus.resetColor();
        if(sorted.isEmpty())
            return focus;
        double smallestLength = calculateCloseness(focus, sorted.get(0), dir);
        Node closestPoint = sorted.get(0);
        //used to color points for visual effect; needs adjustment based on weights
//        closestPoint.setColor(((float)smallestLength/230)/255, (float)(300-smallestLength/230)/255, smallestLength/230>255? (float)(smallestLength-255)/128 : .2f);
        for(int i = 1; i < sorted.size(); i++) {
            double length = calculateCloseness(focus, sorted.get(i), dir);
            if(length < smallestLength) {
                smallestLength = length;
                closestPoint = sorted.get(i);
            }
            //used to color points for visual effect; needs adjustment based on weights
//            sorted.get(i).setColor(((float)length/230)/255, (float)(300-length/230)/255, length/230>255 ? (float)(length-255)/128 : .2f);
        }
        return closestPoint;
    }
    
    /**
     * Takes two points and a direction and calculates a value of "closeness"
     * using the distance between the two points and angle.
     * 
     * @param point1 the first point
     * @param point2 the second point
     * @param dir the direction from the first point
     * @return 
     */
    private static double calculateCloseness(Node point1, Node point2, Direction dir) {
        int x = point2.x-point1.x;
        int y = point2.y-point1.y;
        double angle = 180*Math.atan2(y, x)/Math.PI;
        switch(dir) {
            case UP:
                angle = Math.abs(angle-90); //switch to +90 for top left 0,0 coordinates
                break;
            case DOWN:
                angle = Math.abs(angle+90);//switch to -90 for top left 0,0 coordinates
                break;
            case LEFT:
                if(angle >= 0)
                    angle = Math.abs(angle-180);
                else
                    angle = Math.abs(angle+180);
                break;
            case RIGHT:
                angle = Math.abs(angle);
                break;
        }
        return angleWeight*angle*angle + distanceWeightX*x*x+distanceWeightY*y*y;
    }
}
