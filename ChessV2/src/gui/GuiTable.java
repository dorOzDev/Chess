package gui;

import game.Board;
import game.Spot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GuiTable {
	
	private final JFrame frame;
	private final JMenuBar menuBar;
	private final BoardPanel boardPanel;
	private final Board board;
	private static String defaultPieceImagePath = "art/pieces/";

	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(700, 700);
	private final static Dimension BOARD_PANAL_DIM = new Dimension(500, 500);
	private final static Dimension SPOT_PANEL_DIM = new Dimension(50, 50);
	
	private final Color lightSpotColor= Color.decode("#FFFACD");
	private final Color darkSpotColor = Color.decode("#593E1A");
	
	public GuiTable() {
		frame = new JFrame("Chess Game");
		this.frame.setLayout(new BorderLayout());
		board = Board.startNewBoard();
		menuBar = createTableMenuBar();
		frame.setJMenuBar(menuBar);
		frame.setSize(OUTER_FRAME_DIMENSION);
		
		this.boardPanel = new BoardPanel();
		this.frame.add(this.boardPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	
	// Optional
	private JMenuBar createTableMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		return menuBar;
		
	}
		private JMenu createFileMenu() {
			final JMenu fileMenu = new JMenu("File");
			final JMenuItem openPGN = new JMenuItem("Load PGN file");
			final JMenuItem exitMenuItem = new JMenuItem("Exit");
			
			openPGN.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Open opgn file");
					
				}
			});
			fileMenu.add(openPGN);	
			
			exitMenuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
					
				}
			});
			fileMenu.add(exitMenuItem);
			
			return fileMenu;
	}
		
		private class BoardPanel extends JPanel{
			final List<SpotPanel> boardSpots;
			
			BoardPanel(){
				super(new GridLayout(8, 8));
				this.boardSpots = new ArrayList<SpotPanel>();
				for(int i = 0; i < Board.NUM_ROWS; i++) {
					for(int j = 0; j < Board.NUM_COLS; j++) {
						final SpotPanel spotPanel = new SpotPanel(this, i, j);
						this.boardSpots.add(spotPanel); 
						add(spotPanel);
						
					}
					setPreferredSize(BOARD_PANAL_DIM);
					validate();
				}
			}
			
		}
		
		private class SpotPanel extends JPanel{
			private final int xSpotPos;
			private final int ySpotPos;
			
			SpotPanel(final BoardPanel boardPanel, final int xSpotPos, final int ySpotPos){
				super(new GridBagLayout());
				this.xSpotPos= xSpotPos;
				this.ySpotPos = ySpotPos;
				setPreferredSize(SPOT_PANEL_DIM);
				assignSpotColor();
				assignPiecePanel(board);
				validate();
			}
			
			private void assignPiecePanel(final Board board) {
				this.removeAll();
				if(board.getSpot(xSpotPos, ySpotPos).isOccupied()) {
					try {
						System.out.println(defaultPieceImagePath  + board.getSpot(xSpotPos, ySpotPos).getPieceBySpot().getPlayerCoulor().toString().substring(0, 1) + board.getSpot(xSpotPos, ySpotPos).getPieceBySpot().getPieceType().toString() + ".gif");
						final BufferedImage image = ImageIO.read(new File(defaultPieceImagePath  + board.getSpot(xSpotPos, ySpotPos).getPieceBySpot().getPlayerCoulor().toString().substring(0, 1) + board.getSpot(xSpotPos, ySpotPos).getPieceBySpot().getPieceType().toString() + ".gif"));
						add(new JLabel(new ImageIcon(image)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}

			private void assignSpotColor() {
				int row = xSpotPos;
				int col = ySpotPos;
				if(row % 2 == 0){
					setBackground(col % 2 == 0 ? lightSpotColor : darkSpotColor);			
				}
				else {
					setBackground(col % 2 == 0 ? darkSpotColor : lightSpotColor);	
				}
			}
		}
		

}
