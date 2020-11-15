import java.awt.Color;

public class Cue extends Ball {
	
    public Cue(Color col, BallType type) {
		super(col, type);
	}
    
    // Position of stick for use in painting cue ball stick/cursor.
	public static int stickX, stickY;
	
	// Unit vectors between ball and stick
	public static double uvx = -1,uvy = 0;
	
	// Finds a unit vector between the mouse and cue ball and calculates where the cue is pointing
	public static void calculateCuePos(int mouseX, int mouseY) {
		Cue cue = Main.cue;
		stickX = (int) (mouseX - (cue.getX() + Ball.radius));
		stickY = (int) (mouseY - (cue.getY() + Ball.radius));
		double magnitude = Math.sqrt(Math.pow(stickX, 2)+Math.pow(stickY, 2));
		uvx = stickX/magnitude;
		uvy = stickY/magnitude;
		stickX = (int) (uvx*15 + cue.getX()+Ball.radius);
		stickY = (int) (uvy*15 + cue.getY()+Ball.radius);
	}
	
	// Calculate the velocity vectors that are made when a shot is made and set cue ball velocities
	public static void calculateShot(double power) {
		Main.cue.setVelX(-1*uvx*power);
		Main.cue.setVelY(-1*uvy*power);
	}
	
	public static boolean cueIsStillIn() {
		boolean found = false;
		for (Ball b : Ball.ballList) {
			if (b.isCue()) {
				found = true;
			}
		}
		return found;
	}

	// Checks if the cue's current position is valid for placement. This is true when the cue is both behind the breakline inside the table,
	// and when it is not colliding with a ball or hole.
	public boolean isValidLocation() {
		// Check if it is in the table
	 	if (this.y <= Hitbox.topy || this.y+Ball.diameter >= Hitbox.boty || this.x <= Hitbox.leftx || this.x+Ball.diameter >= Hitbox.rightx) {
	 		return false;
	 	}
	 	
	 	// Check if cue is intersecting any balls
	 	for (Ball b : Ball.ballList) {
	 		if (Hitbox.intersects(b, this)) {
	 			return false;
	 		}
	 	}
	 	
	 	// Check if cue is in any holes
	 	for (Hole h : Hole.holeList) {
	 		if (Hitbox.inHole(h, this)) {
	 			return false;
	 		}
	 	}
	 	
	 	// No issues were found. Location is valid.
	 	return true;
	}
	
}
