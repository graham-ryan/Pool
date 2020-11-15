import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
	// Registers mouse click, triggers a ball shot if we are waiting for one.
	public void mouseClicked(MouseEvent e) {
		if (Main.state == Main.GameState.SHOT_INPUT) {
			Main.state = Main.GameState.BALLS_MOVING;
			Cue.calculateShot(Main.powerGUI.power);
			System.out.println("Shooting...");
		}
		
		if (Main.state == Main.GameState.PLACEMENT_INPUT && Main.cue.isValidLocation()) {
			Ball.ballList.add(Main.cue);
			Main.state = Main.GameState.SHOT_INPUT;
		}
		
		if (Main.state == Main.GameState.END) {
			Main.restartGame();
		}
    }
		
}
