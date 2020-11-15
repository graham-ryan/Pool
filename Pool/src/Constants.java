import java.awt.Color;

public class Constants {
    // Color list: yellow blue brightRed purple orange green darkRed black
    private static Color col[] = {new Color(250, 229, 42),Color.blue,new Color(255,0,0),new Color(255,0,255),new Color(255, 149, 0),new Color(30,200,30),new Color(149,15,23),new Color(0,0,0)};
    // Color getter for main method
    public static Color getColor(int i) {
    	if (i>7) {
    		i=i-8;
    	}
    	return col[i];
    }
}
