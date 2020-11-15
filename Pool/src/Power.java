import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// A class that creates a GUI with a slider on it that controls the power the ball is shot at. Values range from 0 to 15.
public class Power extends JFrame implements ChangeListener {
	// Power value that the ball will be shot at dependent on the slider
	public int power = 15;
	JPanel panel;
	JLabel label;
	JSlider slider;
	
	// Contstructor
	Power (){
		slider = new JSlider(JSlider.HORIZONTAL, 1, 15, 15);  
		panel = new JPanel();  
		label = new JLabel();
		label.setText("Shot Power");
		panel.add(label);
		add(panel);  
		slider.setMinorTickSpacing(3);  
		slider.setMajorTickSpacing(5);  
		panel.add(slider);  
		slider.addChangeListener(this);
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		power = slider.getValue();
	}
}
