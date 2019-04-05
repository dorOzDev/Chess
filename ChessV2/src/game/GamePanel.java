package game;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private final int wight = 500;
	private final int hight = 500;
	private final int scale = 2;
	
	
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(wight * scale, hight *scale));
		setVisible(true);
	}

}
