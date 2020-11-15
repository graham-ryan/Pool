import java.awt.Color;
import java.util.ArrayList;

public class Ball {

    public enum BallType {
        SOLID, STRIPE, EIGHT, CUE;
    }
    
    // Much of this program functions under the assumption that diameter is 18, but still uses the diameter variable for clarity.
    // If you're curious, try changing the diameter to 30 and playing! Collisions will still work, but graphics will be wonky and wholes will be too small!
    public static final int numBalls = 15, diameter = 16;
    public static final int radius = diameter/2;
    public static ArrayList<Ball> ballList = new ArrayList<Ball>(); // List of balls that are in at any time


    public double x=0, y=0;
    public float velX = 0, velY = 0;
    private BallType type;
    private Color col;

    // Constructor
    public Ball(Color col, BallType type) {
        this.col = col;
        this.type = type;
    }

    private boolean moving() {
        return (Math.abs(this.velX) > .05 || Math.abs(this.velY) > .05);
    }

    // Checks if any balls are still moving on the table.
    public static boolean isMoving() {
        boolean moving = false;
        for (Ball b : ballList) {
            if (b.moving()) {
                moving = true;
                break;
            }
        }
        return moving;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public float getVelX() {
        return this.velX;
    }

    public float getVelY() {
        return this.velY;
    }

    protected void setVelX(double v) {
        this.velX = (float) v;
    }

    protected void setVelY(double v) {
        this.velY = (float) v;
    }

    public Color getColor() {
        return this.col;
    }

    // Returns true if ball is solid
    public boolean isSolid() {
        return this.type.equals(BallType.SOLID);
    }

    // Returns true if ball is eight ball
    public boolean isEightBall() {
        return this.type.equals(BallType.EIGHT);
    }

    // Update position and decrease velocities.
    public void updatePos() {
        this.x += this.velX;
        this.y += this.velY;
        this.velX = (float) (this.velX * .97);
        this.velY = (float) (this.velY * .97);
    }

    // Set ball velocity to 0
    public void stop() {
        this.velX = 0;
        this.velY = 0;
    }

    public boolean isCue() {
        return this.type == BallType.CUE;
    }

	public boolean isStripe() {
		return this.type == BallType.STRIPE;
	}

	// Returns true if eight ball is still in
	public static boolean eightBallIsStillIn() {
		for (Ball b : Ball.ballList) {
			if (b.isEightBall()) {
				return true;
			}
		}
		return false;
	}
}
