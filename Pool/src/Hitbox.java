import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Hitbox {
    public static int x = 76, topy = 75, boty = 324, leftx = 75, rightx = 524;

    // Checks if any collisions are made at this point in time.
    public static void checkCollisions() {
        // Ball collisions
        for (int i = 0; i < Ball.ballList.size(); i++) {
            for (int j = i + 1; j < Ball.ballList.size(); j++) {
                if (i != j && intersects(Ball.ballList.get(i), Ball.ballList.get(j))) {
                    System.out
                            .println("Intersection between " + i + " and " + j);
                    Ball bi = Ball.ballList.get(i);
                    Ball bj = Ball.ballList.get(j);
                    // Find distance between balls
                    double dx = bj.x - bi.x;
                    double dy = bj.y - bi.y;
                    double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                    // Find normal vectors between the balls of length 1
                    double uvx = dx / dist;
                    double uvy = dy / dist;
                    // Now we must find velocity difference
                    double kx = bi.velX - bj.velX;
                    double ky = bi.velY - bj.velY;
                    // Calculate dot product between normal and velocity vectors
                    double dot = kx * uvx + ky * uvy;
                    // If dot product is less than 0, then the velocity vectors
                    // of the balls are no longer facing each other, and thus
                    // their velocities don't need to be changed
                    if (dot > 0) {
                        // Calculate impulse, the projection of
                        // the velocity vector (given by kx ky) onto the normal vector.
                        // Remember all the force comes from the contact of the
                        // balls given by the normal vector
                        double impulseX = uvx * dot;
                        double impulseY = uvy * dot;
                        bi.velX -= impulseX;
                        bi.velY -= impulseY;
                        bj.velX += impulseX;
                        bj.velY += impulseY;
                    }
                }
            }
        }
        
    	// Check wall collisions
    	for (Ball b : Ball.ballList) {
            if ((b.velY + b.y) <= Hitbox.topy) { // Top wall
                b.velX *= .80;
                b.velY *= -.80;
                b.y = Hitbox.topy;
                Main.table.repaint();
            } else if ((b.velY + b.y + Ball.diameter) >= Hitbox.boty) { // Bottom wall
                b.velX *= .80;
                b.velY *= -.80;
                b.y = Hitbox.boty-Ball.diameter;
                Main.table.repaint();
            } else if ((b.velX + b.x) <= Hitbox.leftx) { // Left wall
                b.velX *= -.80;
                b.velY *= .80;
                b.x = Hitbox.leftx;
                Main.table.repaint();
            } else if ((b.velX + b.x + Ball.diameter) >= Hitbox.rightx) { // Right wall
                b.velX *= -.80;
                b.velY *= .80;
                b.x = Hitbox.rightx-Ball.diameter;
                Main.table.repaint();
            }
    	}
    	
    	// Check if any balls are in the hole. If they are, remove them.
    	ListIterator<Ball> iter = Ball.ballList.listIterator();
    	List<Ball> toRemove = new ArrayList<Ball>();
    	while (iter.hasNext()) {
			Ball b = iter.next();
    		for (Hole h : Hole.holeList) {
    			if (inHole(h,b)) {
    				toRemove.add(b);
    			}
    		}
    	}
    	// Put all balls in an arbitrary position off the screen, and remove them from the list
    	for (Ball b : toRemove) {
    		b.x=-100;
    		b.y=-100;
    		Ball.ballList.remove(b);
    	}
    	
    	// Add balls to player's ball lists
    	Main.player.addBalls(toRemove);
    	
    }

    // Checks if ballList i and j intersect
    public static boolean intersects(Ball bi, Ball bj) {
    	// No need to adjust for comparing distance between ball centers.
        double x = bj.x - bi.x;
        double y = bj.y - bi.y;
        double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return dist <= Ball.diameter;
    }
    
    // Check if ball is in the hole
    public static boolean inHole(Hole h, Ball b) {
    	// Values are added by their radiuses, because we want to make sure we compare the distance
    	// between the center of each object.
    	double x = h.x+Hole.diameter/2 - (b.x+Ball.radius);
    	double y = h.y+Hole.diameter/2 - (b.y+Ball.radius);
        double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return dist <= Hole.diameter-10;
    }
    

}
