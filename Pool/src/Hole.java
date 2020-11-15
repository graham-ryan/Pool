import java.util.ArrayList;

// Hole objects for collision detection on balls
public class Hole {
	int x, y;
	static final int diameter = 35, radius = diameter/2+1;
    public static ArrayList<Hole> holeList = new ArrayList<Hole>();

	// x,y being the top left position where the circle drawing begins
	public Hole (int x, int y) {
		this.x=x;
		this.y=y;
	}
}
