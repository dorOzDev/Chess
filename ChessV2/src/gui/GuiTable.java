package gui;

import game.Board;
import game.Spot;
import movement.AttackMove;
import movement.CandidateMove;
import movement.Move;
import soldiers.Piece;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
public class GuiTable {
	
	private final JFrame frame;
	private final GameHistoryPanel gameHistoryPanel;
	private final TakenPiecesPanel takenPiecesPanel;
	private final JMenuBar menuBar;
	private final BoardPanel boardPanel;
	private boolean highLightLegalMoves = true;
	private Board board;
	private final MoveLog moveLog;
	private Spot sourceSpot;
	private Spot destSpot;
	private Piece humanMovedPiece;
	private Piece potenticalAttackedPiece;
	private Move lastMove;
	
	public static String defaultPieceImagePath = "art/pieces/";
	private static String defaultHighlightImagePath = "art/misc/green_dot.png";

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
		gameHistoryPanel = new GameHistoryPanel();
		takenPiecesPanel = new TakenPiecesPanel();
		frame.setJMenuBar(menuBar);
		frame.setSize(OUTER_FRAME_DIMENSION);
		frame.add(takenPiecesPanel, BorderLayout.WEST);
		frame.add(gameHistoryPanel, BorderLayout.EAST);	
		moveLog = new MoveLog();
		this.boardPanel = new BoardPanel();
		this.frame.add(this.boardPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	
	private JMenuBar createTableMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		menuBar.add(createPreferncesMenu());
		return menuBar;
		
	}
		
		private JMenu createPreferncesMenu() {
			final JMenu preferenceMenu = new JMenu("Preference");
			final JCheckBoxMenuItem legalHighLighterChecBox = new JCheckBoxMenuItem("Highlight Legal Moves", true);			
			legalHighLighterChecBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					highLightLegalMoves = legalHighLighterChecBox.isSelected();
					
					
				}
			});
			preferenceMenu.add(legalHighLighterChecBox);
			return preferenceMenu;
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
			
			public void drawBoard(final Board board) {
				removeAll();
				for(final SpotPanel spotPanel : boardSpots) {
					spotPanel.drawSpot(board);
					add(spotPanel);
				}
				validate();
				repaint();
			}
			
		}
		
		
		public static class MoveLog {
			private final List <Move> moves;
			MoveLog(){
				this.moves = new ArrayList<>();
			}
			
			public List<Move> getMoves(){
				return this.moves;
			}
			
			public void addMove(final Move move) {
				this.moves.add(move);
			}
			
			public int size() {
				return this.moves.size();
			}
			
			public void clear() {
				this.moves.clear();
			}
			
			public Move removeMove(int index) {
				return this.moves.remove(index);
			}
			
			public boolean removeMove(final Move move) {
				return this.moves.remove(move);
			}
			
			
		}
		
		private class SpotPanel extends JPanel{
			private final int xSpotPos;
			private final int ySpotPos;
			
			//TODO check if mouse event listener can be moved out of constructor scope.
			SpotPanel(final BoardPanel boardPanel, final int xSpotPos, final int ySpotPos){
				super(new GridBagLayout());
				this.xSpotPos= xSpotPos;
				this.ySpotPos = ySpotPos;
				setPreferredSize(SPOT_PANEL_DIM);
				assignSpotColor();
				assignPiecePanel(board);
				
				addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(final MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(final MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(final MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(final MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(final MouseEvent event) {
						final Move move;
						//Left click to make a move.
						if(SwingUtilities.isLeftMouseButton(event)) {
							if(sourceSpot == null) {
								sourceSpot = board.getSpot(xSpotPos, ySpotPos);
								humanMovedPiece = sourceSpot.getPiece();
								//System.out.println(humanMovedPiece);
								if(humanMovedPiece == null)
									sourceSpot = null;
							} else {
								destSpot = board.getSpot(xSpotPos, ySpotPos);
								if(destSpot.getPiece() != null) {
									move = new AttackMove(sourceSpot, destSpot, humanMovedPiece, destSpot.getPiece());
								}
								else {
									move = new CandidateMove(sourceSpot, destSpot, humanMovedPiece);
								}
								 final boolean hasMoveSucceed = board.getCurrPlayer().makeMove(move); 
								 
								 if(hasMoveSucceed){
									 
										board = board.getUpdatedBoard(); 
										moveLog.addMove(move);
										board.setCurrPlayer();
									}
									sourceSpot =  null;
									destSpot = null;
									humanMovedPiece = null;		
								 
							}
						}

		 
						
						//Right click cancel any click (in case user regrets the click)
						else if(SwingUtilities.isRightMouseButton(event)) {
							System.out.println("Right clicked");
							sourceSpot =  null;
							destSpot = null;
							humanMovedPiece = null;							
						}
						
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								gameHistoryPanel.redo(board, moveLog);
								takenPiecesPanel.redo(moveLog);
								boardPanel.drawBoard(board);
								
							}
						});
						
					}
				});
				
				validate();
			}
			
			private void highlightLegalMoves(Board board) {
			
				if(highLightLegalMoves) {
					
					for(final Move move : pieceLegalMoves(board)) {
						
						if(move.getDestSpot().getX() == xSpotPos && move.getDestSpot().getY() == ySpotPos) {
							try {
								
								add(new JLabel(new ImageIcon(ImageIO.read(new File(defaultHighlightImagePath)))));
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		
		
			private  ArrayList<Move> pieceLegalMoves(Board board){
				
				ArrayList<Move> legalMoves = new ArrayList<Move>();
				if(humanMovedPiece != null && humanMovedPiece.getPlayerCoulor() == board.getCurrPlayer().getPlayerColour()) {
					legalMoves.addAll((Collection<? extends Move>) humanMovedPiece.getLegalMovements());
				}
				return legalMoves;
			}
			
			public void drawSpot(final Board board) {
				assignSpotColor();
				assignPiecePanel(board);
				highlightLegalMoves(board);
				validate();
				repaint();
			}
			
			private void assignPiecePanel(final Board board) {
				this.removeAll();
				if(board.getSpot(xSpotPos, ySpotPos).isOccupied()) {
					try {
						Piece drawPiece = board.getSpot(xSpotPos, ySpotPos).getPiece();
						final BufferedImage image = ImageIO.read(new File(defaultPieceImagePath  + drawPiece.getPlayerCoulor().toString().substring(0, 1) + drawPiece.getPieceType().toString() + ".png"));
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
