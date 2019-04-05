package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GuiTable {
	
	private final JFrame frame;
	private final JMenuBar menuBar;
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(500, 700);
	
	public GuiTable() {
		frame = new JFrame("Chess Game");	
		menuBar = new JMenuBar();
		
		popMenuBar(menuBar);
		frame.setJMenuBar(menuBar);
		frame.setSize(OUTER_FRAME_DIMENSION);
		frame.setVisible(true);
	}


	private void popMenuBar(JMenuBar menuBar2) {// Optional
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openPGN = new JMenuItem("Load PGN file");
		
		menuBar.add(fileMenu);
		openPGN.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open opgn file");
				
			}
		});
		fileMenu.add(openPGN);
	}
	
	
	
	
	
	

}
