import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

public class Main {
    public enum GameState {
        SHOT_INPUT, BALLS_MOVING, PLACEMENT_INPUT, END;
    }

    public static int width = 600;
    public static int height = 400;
    public static GameState state;
    public static Player player = new Player();
    public static Cue cue = new Cue(Color.white, Ball.BallType.CUE); 
    public static Table table = new Table();
    public static Power powerGUI = new Power();

    public static void main(String[] args) {
        // Generates JFrame, and table JPanel
        table.setBackground(new Color(255, 204, 51));
        table.setSize(width, height);
        table.addMouseListener(new Mouse());
        JFrame jf = new JFrame();
        jf.setTitle("Poooooooooooooool");
        jf.setSize(width + 6, height + 29);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        powerGUI.setLocation(jf.getLocation().x, jf.getLocation().y-60);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(table);
        	
        // Create ball objects
        Ball.BallType type = Ball.BallType.SOLID;
        for (int i = 0; i < 15; i++) {
            if (i == 7) {
                type = Ball.BallType.EIGHT;
            } else if (i > 7) {
                type = Ball.BallType.STRIPE;
            }
            Ball.ballList.add(
                    new Ball(Constants.getColor(i), type));
        }
        Ball.ballList.add(cue);
        
        // Create hole hitboxes
        Hole.holeList.add(new Hole(58,58));
        Hole.holeList.add(new Hole(58,308));
        Hole.holeList.add(new Hole(506,58));
        Hole.holeList.add(new Hole(506,308));
        Hole.holeList.add(new Hole(280,53));
        Hole.holeList.add(new Hole(280,313));

        // Place balls in proper position
        placeBalls();
        
        // Game loop
        state = Main.GameState.SHOT_INPUT;
        run();
    }
    
    public static void restartGame() {
    	// Reset player object (destroys list of balls they had)
    	player = new Player();
    	// Empty the ball list, add new balls.
    	Ball.ballList.clear();
        // Create ball objects
        Ball.BallType type = Ball.BallType.SOLID;
        for (int i = 0; i < 15; i++) {
            if (i == 7) {
                type = Ball.BallType.EIGHT;
            } else if (i > 7) {
                type = Ball.BallType.STRIPE;
            }
            Ball.ballList.add(
                    new Ball(Constants.getColor(i), type));
        }
        Ball.ballList.add(cue);
    	// Replace balls
    	placeBalls();
    	// Change game state
    	state = GameState.SHOT_INPUT;
    }
    
    // Places balls. Cue is placed at <445,185>, and 8 ball is placed at <182,184>. Other balls placed randomly in typical pool fashion. 
    private static void placeBalls() {
		// Place cue and 8 balls
    	Ball b = Ball.ballList.get(7);
    	b.x=182;
    	b.y=184;
    	b = Ball.ballList.get(15);
    	b.x=450;
    	b.y=185;
    	
    	// Shuffle the other balls (but first remove cue and 8 balls)
    	List<Ball> shuffled = new ArrayList<Ball>(Ball.ballList);
    	shuffled.remove(15);
    	shuffled.remove(7);
    	Collections.shuffle(shuffled);
    	
    	// Place the other balls. We will start x at 409, y at 149. This will be the top left of the pool rack on the screen.
    	// Place first column of balls from the left (5 balls)
    	int x=152;
    	int y=150;
    	for (int i=0;i<5;i++) {
    		Ball temp = shuffled.remove(0);
    		temp.x=x;
    		temp.y=y+(Ball.diameter+1)*i;
    	}
    	// Second column (4 balls)
    	x+=Ball.diameter-1;
    	y+=Ball.diameter/2+1;
    	for (int i=0;i<4;i++) {
    		Ball temp = shuffled.remove(0);
    		temp.x=x;
    		temp.y=y+(Ball.diameter+1)*i;
    	}
    	// Third column (Avoid placing on same place as 8 ball)
    	x+=Ball.diameter-1;
    	y+=Ball.diameter/2;
    	Ball temp = shuffled.remove(0);
    	temp.x=x;
    	temp.y=y;
    	y+=(Ball.diameter+1)*2;
    	temp = shuffled.remove(0);
    	temp.x=x;
    	temp.y=y;
    	// Fourth column (2 balls)
    	x+=Ball.diameter-1;
    	y-=Ball.diameter/2+1;
    	temp = shuffled.remove(0);
    	temp.x=x;
    	temp.y=y;
    	y-=(Ball.diameter+1);
    	temp = shuffled.remove(0);
    	temp.x=x;
    	temp.y=y;
    	// Fifth column (1 ball)
    	temp = shuffled.remove(0);
    	x+=Ball.diameter-1;
    	y+=Ball.diameter/2+1;
    	temp.x=x;
    	temp.y=y;
	}

	private static void run() {
        double lastFrameTime = 0.0;
        while (true) {
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;
            update(deltaTime);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static void update(double dt) {
        //System.out.format("FPS: %f\n", 1/dt);
        switch (state) {
            case SHOT_INPUT:
                // Find position of the mouse to find the pool stick position.
            	try {
            		Cue.calculateCuePos(table.getMousePosition().x,table.getMousePosition().y);
            	} catch (NullPointerException e) {
            		
            	}
                break;

            case BALLS_MOVING:
                // Check if anything will collide, if it is recalculate
                Hitbox.checkCollisions();

                // Update position and decrease velocity due to friction
                for (Ball b : Ball.ballList) {
                    b.updatePos();
                }

                // Check if balls are still moving (i.e. below a certain speed)
                if (!Ball.isMoving()) {
                    // If they aren't, change game state and player turns
                	processGameState();
                    // Change stickX stickY so cursor doesn't show in the previous spot
                    Cue.stickX = -10;
                    Cue.stickY = -10;
                    // Set ball velocities to 0
                    for (Ball b : Ball.ballList) {
                        b.stop();
                    }
                }
                break;
                
            case PLACEMENT_INPUT:
            	// For ball placement on scratches. Place ball behind where you break. Keep cue ball on cursor, print "Place ball"
            	try {
                    cue.x = table.getMousePosition().x-Ball.radius;
                    cue.y = table.getMousePosition().y-Ball.radius;         
            	} catch (NullPointerException e) {
            		
            	}

                break;

            case END:
            	
                break;

            default:
                break;
        }

        table.repaint();
    }

	private static void processGameState() {
    	if (Ball.eightBallIsStillIn()) {
    		if (Cue.cueIsStillIn()) {
    			if (player.madeBall()) {
    				// If the player made one of their balls, they keep their turn.
    				player.keepTurn();
    				state = GameState.SHOT_INPUT;
    			} else {
    				// If the player did not make one of their balls, change turns.
    				player.changeTurn();
    				state = GameState.SHOT_INPUT;
    			}
    		} else {
    			// Cue ball is out, so change turns and get placement input.
    			player.changeTurn();
				state = GameState.PLACEMENT_INPUT;
    		}
    	} else {
    		if (Cue.cueIsStillIn()) {
    			if (player.madeAllBalls()) {
    				// If the current player made all of the balls of their type before sinking the eight ball, they win
    				player.currentPlayerWins();
    				state = GameState.END;
    			} else {
    				// If the current player did not make all of the balls of their type before sinking the eight ball, they lose
    				player.currentPlayerLoses();
    				state = GameState.END;
    			}
    		} else {
    			// Current player sunk the eight ball and the cue ball at the same time, so they lose.
				player.currentPlayerLoses();
				state = GameState.END;
    		}
    	}
	}


}
