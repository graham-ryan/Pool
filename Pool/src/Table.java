import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

// Main JPanel for the program.
public final class Table extends JPanel {
	
    private static final long serialVersionUID = 1L;
    // Draw everything on the table
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Draw Table. Starts 50 from top of frame, 50 from left of frame
        g2d.setColor(Color.GREEN);
        g2d.fillRect(50, 50, 500, 300);
        g2d.setColor(new Color(102, 51, 0));
        // Table borders. These numbers are useful for wall collisions.
        for (int i = 0; i < 26; i++) {
            g2d.drawRect(50 + i, 50 + i, 500 - 2 * i, 300 - 2 * i);
        }
        g2d.setColor(Color.black);
        g2d.fillOval(450, 190, 10, 10);
        
        // Draw holes. Top left hole starts at 61,61 - the center of the circle alligning with
        // the top left inner part of the table.
        g2d.setColor(Color.black);
        for (Hole h : Hole.holeList) {
        	g2d.fillOval(h.x, h.y, Hole.diameter, Hole.diameter);
        }
        
        // Draw player turn if game is not over.
        if (Main.state != Main.GameState.END) {
            if (Main.player.isPlayer1Turn()) {
                g2d.drawString("Player 1's turn.", 50, 380);
            } else {
                g2d.drawString("Player 2's turn.", 50, 380);
            }        	
        }

        
        // Draw player's balls they have made
        // Tags
        g2d.drawString("P1 balls", 2, 40);
        g2d.drawString("______", 3, 42);
        g2d.drawString("P2 balls", 552, 40);
        g2d.drawString("______", 553, 42);
        
        // Player 1's list
        int x = 15, y = 48;
        for (int i = 0; i < Main.player.player1balls.size(); i++) {
        	Ball b = Main.player.player1balls.get(i);
        	if (b.isSolid()) {
        		g2d.setColor(b.getColor());
            	g2d.fillOval(x, y+i*(Ball.diameter+5), Ball.diameter, Ball.diameter);
            	g2d.setColor(Color.black);
            	g2d.drawOval(x, y+i*(Ball.diameter+5), Ball.diameter, Ball.diameter);
        	} else if (b.isStripe()) {
                g2d.setColor(Color.white);
                g2d.fillOval(x, y+i*(Ball.diameter+5), Ball.diameter, Ball.diameter);
                g2d.setColor(b.getColor());
                g2d.fillRoundRect(x + 1, y+i*(Ball.diameter+5) + 5, Ball.diameter-1, 8,
                        3, 1);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x, y+i*(Ball.diameter+5), Ball.diameter, Ball.diameter);
        	} else if (b.isEightBall()) {
        		 g2d.setColor(Color.black);
                 g2d.fillOval(x, y+i*(Ball.diameter+5), Ball.diameter, Ball.diameter);
                 g2d.setColor(Color.GRAY);
        	}       	
        }
        x = 565;
        // Player 2's list
        for (int i = 0; i < Main.player.player2balls.size(); i++) {
        	Ball b = Main.player.player2balls.get(i);
        	if (b.isSolid()) {
        		g2d.setColor(b.getColor());
            	g2d.fillOval(x, y+i*(Ball.diameter+5), Ball.diameter, Ball.diameter);
            	g2d.setColor(Color.black);
            	g2d.drawOval(x, y+i*(Ball.diameter+5), Ball.diameter, Ball.diameter);
        	} else if (b.isStripe()) {
                g2d.setColor(Color.white);
                g2d.fillOval(x, y+i*(Ball.diameter+5), Ball.diameter, Ball.diameter);
                g2d.setColor(b.getColor());
                g2d.fillRoundRect(x + 1, y+i*(Ball.diameter+5) + 5, Ball.diameter-1, 8,
                        3, 1);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x, y+i*(Ball.diameter+5), Ball.diameter, Ball.diameter);
        	} else if (b.isEightBall()) {
        		 g2d.setColor(Color.black);
                 g2d.fillOval(x, y+i*(Ball.diameter+5), Ball.diameter, Ball.diameter);
                 g2d.setColor(Color.GRAY);
        	}
        }
        
        
        // Draw balls that are on the table
        for (Ball b : Ball.ballList) {
            if (b.isSolid()) {
                g2d.setColor(b.getColor());
                g2d.fillOval((int) b.getX(), (int) b.getY(), Ball.diameter, Ball.diameter);
                g2d.setColor(Color.black);
                g2d.drawOval((int) b.getX(), (int) b.getY(), Ball.diameter, Ball.diameter);
            } else if (b.isEightBall()) {
                g2d.setColor(Color.black);
                g2d.fillOval((int) b.getX(), (int) b.getY(), Ball.diameter, Ball.diameter);
                g2d.setColor(Color.GRAY);
                g2d.drawOval((int) b.getX(), (int) b.getY(), Ball.diameter, Ball.diameter);
            } else if (b.isCue()) {
                g2d.setColor(Color.white);
                g2d.fillOval((int) b.getX(), (int) b.getY(), Ball.diameter, Ball.diameter);
                g2d.setColor(Color.black);
                g2d.drawOval((int) b.getX(), (int) b.getY(), Ball.diameter, Ball.diameter);
            } else {
                g2d.setColor(Color.white);
                g2d.fillOval((int) b.getX(), (int) b.getY(), Ball.diameter, Ball.diameter);
                g2d.setColor(b.getColor());
                g2d.fillRoundRect((int) b.getX() + 1, (int) b.getY() + 5, Ball.diameter-1, 8,
                        3, 1);
                g2d.setColor(Color.BLACK);
                g2d.drawOval((int) b.getX(), (int) b.getY(), Ball.diameter, Ball.diameter);
            }
        }
        
        // Draw cue stick if we are looking for shot input, as well as line projecting to next collision point
        if (Main.state == Main.GameState.SHOT_INPUT) {
            // Cue stick (red square around cue ball)
        	g2d.setColor(Color.RED);
            g2d.fillOval(Cue.stickX-2, Cue.stickY-2, 5, 5);
            // Draw line to next collision, first find out where it is
            // Start c at 0, then stop adding 1 to c when a collision is found and draw the line.
            // Keep adding the unit vector between the ball and stickX/stickY.
            g2d.setColor(Color.darkGray);
            // Constant for counting up distance
            int c = 1;
            int x1 = (int) (Main.cue.x+Ball.radius-Ball.radius*Cue.uvx);
            int y1 = (int) (Main.cue.y+Ball.radius-Ball.radius*Cue.uvy);
            while (!lineColliding(x1,y1,c)) {
            	c++;
            }
            //g2d.fillOval(x1, y1, 3, 3); //Draw oval on start point of collision line
            g2d.drawLine(x1,y1, x1 - (int) (Cue.uvx*c), y1 - (int) (Cue.uvy*c)); 
        } else if (Main.state == Main.GameState.PLACEMENT_INPUT) {
        	// Color ball, it is not in ballList since it is out of play.
            g2d.setColor(Color.white);
            g2d.fillOval((int) Main.cue.x, (int) Main.cue.y, Ball.diameter, Ball.diameter);
            g2d.setColor(Color.black);
            g2d.drawOval((int) Main.cue.x, (int) Main.cue.y, Ball.diameter, Ball.diameter);
            // If the location is valid, tell the user it is. If it isn't, tell the user it is invalid.
            if (Main.cue.isValidLocation()) {
                g2d.drawString("Place the ball anywhere on the table. (THIS IS A VALID LOCATION)", 150, 380);            	
            } else {
                g2d.drawString("Place the ball anywhere on the table. (THIS IS AN INVALID LOCATION)", 150, 380);            	
            }
        } else if (Main.state == Main.GameState.END) {
        	// The game is now over.
        	if (Main.player.player1Won()) {
            	g2d.drawString("Player 1 wins. Click anywhere to restart the game.", 150, 380);
        	} else {
            	g2d.drawString("Player 2 wins. Click anywhere to restart the game.", 150, 380);
        	}
        }

    }
    
	// Check if line is inside any balls or hitting a wall. x1,y1 are the start of the line on the cue ball. c is the distance from
	private boolean lineColliding(int x1, int y1, int c) {
		// Check if line is inside any balls or hitting a wall
		int x2 = x1 - (int) (Cue.uvx*c);
 		int y2 = y1 - (int) (Cue.uvy*c);
	 	for (Ball b : Ball.ballList) {
			 int dx = (int) (b.x+Ball.radius-x2); // x distance from center of current ball minus current collision line and
			 int dy = (int) (b.y+Ball.radius-y2); // y distance from center of current ball minus current collision line
		     double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); // Distance between the two
		     if (Math.round(dist) <= Ball.radius && !b.isCue()) {
		    	 return true;
		     }
		}
		// Check if it is inside any walls
	 	if (y2 <= Hitbox.topy || y2 >= Hitbox.boty || x2 <= Hitbox.leftx || x2 >= Hitbox.rightx) {
	 		return true;
	 	}
	 	
	 	// Check if it is inside any holes
	 	for (Hole h : Hole.holeList) {
	 		int dx = h.x+Hole.radius-x2;
	 		int dy = h.y+Hole.radius-y2;
		    double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); // Distance between the hole and line
	 		if (dist <= Hole.radius) {
	 			return true;
	 		}
	 	}
	 	
	 	return false;
	}

}
