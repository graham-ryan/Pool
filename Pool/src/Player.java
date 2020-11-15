import java.util.ArrayList;
import java.util.List;

// Player class to keep track of who has what balls, which player is solids or stripes, and whose turn it is
public class Player {
	private boolean player1Turn = true;
	private boolean player1won = false;
	private Ball.BallType player1;
	private Ball.BallType player2;

    public ArrayList<Ball> player1balls = new ArrayList<Ball>(); // The balls p1 has sunk
    public ArrayList<Ball> player2balls = new ArrayList<Ball>(); // The balls p2 has sunk
	
	private boolean madeBall = false; // Boolean to keep track of if a player made a shot

	// Returns true if it is player 1's turn. Else, false.
	public boolean isPlayer1Turn() {
		return player1Turn;
	}
	
	// Change's whose turn it is. Also, makes madeBall false since no ball has been made for the next turn yet.
	public void changeTurn() {
		player1Turn = !player1Turn;
		madeBall = false;
	}
	
	// Continues this player's turn. Makes madeBall false since no ball has been made for the next turn yet.
	public void keepTurn() {
		madeBall = false;
	}
	
	// Add balls that fell into holes
	public void addBalls(List<Ball> balls) {
		// If the balls that fell in this tick are empty, then we don't have to do anything.
		if (!balls.isEmpty()) {
			// Check if player's ball types aren't assigned yet. If they aren't, assign them if a solid or stripe was pocketed.
			if (!isBallTypeAssigned()) {
				Ball b = findFirstSolidOrStipe(balls);
				if (b!=null) {
					if (b.isSolid()){
						assignPlayerBallType(Ball.BallType.SOLID);
					} else {
						assignPlayerBallType(Ball.BallType.STRIPE);
					}
				}
			} 
			
			// Now, if ball type is actually assigned, then we can the balls to player's lists accordingly
			if (isBallTypeAssigned() || hasEightBall(balls)) {
				for (Ball b : balls) {
					addOneBall(b);
				}
			}
		}
	}
	
	// Returns true if the list has an eight ball. Else, false.
	private boolean hasEightBall(List<Ball> balls) {
		for (Ball b : balls) {
			if (b.isEightBall()) {
				return true;
			}
		}
		return false;
	}

	// Adds one ball into its respective list (player 1 or player 2's ball lists). Also updates the made ball variable to true.
	private void addOneBall(Ball b) {
		if (b.isSolid()) {
			if (player1 == Ball.BallType.SOLID) {
				player1balls.add(b);
				// If it is player 1's turn, they are solids, and they made a solid. Then they made a ball.
				if (isPlayer1Turn()) {
					madeBall = true;
				}
			} else {
				player2balls.add(b);
				// If it is player 2's turn, they are solids, and they made a solid. Then they made a ball.
				if (!isPlayer1Turn()) {
					madeBall = true;
				}
			}
		} else if (b.isStripe()) {
			if (player1 == Ball.BallType.STRIPE) {
				player1balls.add(b);
				// If it is player 1's turn, they are stripes, and they made a stripe. Then they made a ball.
				if (isPlayer1Turn()) {
					madeBall = true;
				}
			} else {
				player2balls.add(b);
				// If it is player 2's turn, they are stripes, and they made a stripe. Then they made a ball.
				if (!isPlayer1Turn()) {
					madeBall = true;
				}
			}
		}	else if (b.isEightBall()) {
			if (isPlayer1Turn()) {
				player1balls.add(b);
			} else {
				player2balls.add(b);
			}
		}
	}

	// Returns the first solid or stripe ball in the list. If none are found, return null.
	private Ball findFirstSolidOrStipe(List<Ball> balls) {
		for (Ball b : balls) {
			if (b.isSolid() || b.isStripe()) {
				return b;
			}
		}
		return null;
	}

	// Returns true if ball types are assigned already. Else, false.
	private boolean isBallTypeAssigned() {
		return player1!=null && player2!=null;
	}

	// player1==null && player2==null && (balls.get(0).isSolid() || balls.get(0).isStripe())
	
	
	// Assign player ball types. The current player will be assigned the ball type of whoever is passed, the other player will be assigned the other ball type.
	// Shouldn't be called more than once.
	private void assignPlayerBallType(Ball.BallType balltype) {
		if (isPlayer1Turn()) {
			if (balltype == Ball.BallType.SOLID) {
				player1 = Ball.BallType.SOLID;
				player2 = Ball.BallType.STRIPE;
			} else {
				player1 = Ball.BallType.STRIPE;
				player2 = Ball.BallType.SOLID;
			}
		} else {
			if (balltype == Ball.BallType.SOLID) {
				player1 = Ball.BallType.STRIPE;
				player2 = Ball.BallType.SOLID;
			} else {
				player1 = Ball.BallType.SOLID;
				player2 = Ball.BallType.STRIPE;
			}
		}
	}
	
	// Returns true if the player made a ball this turn. Else, false.
	public boolean madeBall() {
		return madeBall;
	}

	// Returns true if the player made all the balls of their type before sinking an 8 ball. Else, false.
	public boolean madeAllBalls() {
		if (isPlayer1Turn()) {
			// Count if player 1 made all 7 of their balls.
			for (int i = 0; i < player1balls.size(); i++) {
				if (player1balls.get(i).isEightBall() && i < 6) {
					// If there is an eight ball in the list before we find 7 other balls, then the player did not make all balls.
					return false;
				} 
			}
			return true;
		} else {
			// Count if player 2 made all 7 of their balls.
			for (int i = 0; i < player1balls.size(); i++) {
				if (player2balls.get(i).isEightBall() && i < 6) {
					// If there is an eight ball in the list before we find 7 other balls, then the player did not make all balls.
					return false;
				} 
			}
			return true;
		}
	}

	// Sets the current player to be the victor.
	public void currentPlayerWins() {
		if (isPlayer1Turn()) {
			player1won = true;
		} else {
			player1won = false;
		}		
	}

	// Sets the current player to be the loser. (i.e., the other player is the victor)
	public void currentPlayerLoses() {
		this.changeTurn();
		this.currentPlayerWins();
	}
	
	// Returns true if player 1 won the game, else false;
	public boolean player1Won() {
		return player1won;
	}
}
