package gui;

import game.Board;
import game.GameState;
import game.Spot;
import movement.AttackMove;
import movement.CandidateMove;
import movement.CommandMove.MoveExecuter;
import movement.Move;
import movement.MoveFactory;
import pieces.Piece;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import ai.MiniMax;
import ai.MoveStrategy;
import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerType;
public class GuiTable extends Observable{
	
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
	private MoveFactory moveFactory;
	private final GameSetup gameSetup;
	private Move computerMove;
	private GameState gameState;
	private static MoveExecuter moveExecuter;

	System.

	public static String defaultPieceImagePath = "art/pieces/";
	private static String defaultHighlightImagePath = "art/misc/Glow_Bluev2.png";
	private static String defaultHighlightAttackImagePath = "art/misc/Glow_Redv2.png";

	

	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(800, 800);
	private final static Dimension BOARD_PANAL_DIM = new Dimension(700, 700);
	private final static Dimension SPOT_PANEL_DIM = new Dimension(50, 50);
	private final static Integer PIECE_LAYER = new Integer(3);
	private final static Integer HIGHLIGHT_LAYER = new Integer(2);
	
	private final Color lightSpotColor= Color.decode("#FFFACD");
	private final Color darkSpotColor = Color.decode("#705124");
	
	private final String darkSpotTilePath = "art/Tile/Black_Tile.png";
	private final String lightSpotTilePath = "art/Tile/White_Tile.png";
	
	private static final GuiTable guiTable = new GuiTable();
	
	/*******************
	 * Two different design patterns here:
	 * GuiTable is a singleton design pattern.
	 * Also the whole GUI is MVC Design patterns:
	 * The model is the Board class which holds the current state of the game.
	 * We have 3 Views: the GameHistoryPanel, GameSetup, TakenPiecesPanel
	 *  Last the very same Singleton Gui table acts as the Controller in the MVC design pattern.
	 * 
	 */
	
	
	
	
	private GuiTable() {
		frame = new JFrame("Chess Game");
		this.frame.setLayout(new BorderLayout());
		board = Board.startNewBoard();
		menuBar = createTableMenuBar();
		gameHistoryPanel = new GameHistoryPanel();
		takenPiecesPanel = new TakenPiecesPanel();
		gameState = new GameState();
		frame.setJMenuBar(menuBar);
		frame.setSize(OUTER_FRAME_DIMENSION);
		frame.add(takenPiecesPanel, BorderLayout.WEST);
		frame.add(gameHistoryPanel, BorderLayout.EAST);	
		moveLog = new MoveLog();
		this.boardPanel = new BoardPanel();
		addObserver(new TableGameAIWatcher());
        frame.add(boardPanel, BorderLayout.CENTER);
		center(this.frame);
		frame.setVisible(true);
		moveFactory = new MoveFactory();
		gameSetup = new GameSetup(this.frame, true);
	
	}
	
	public static GuiTable get() {
		return guiTable;
	}

	
	private void center(JFrame frame) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;
        frame.setLocation(x, y);
		
	}


	private JMenuBar createTableMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		menuBar.add(createPreferncesMenu());
		menuBar.add(createOptionsMenu());
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
		
		private Board getBoard() {
			return this.board;
		}
		
		private JMenu createOptionsMenu() {
			final JMenu optionMenu = new JMenu("Options");
			
			final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
			setupGameMenuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					GuiTable.get().getGameSetup().promptUser();
					
				}
			});
			
			optionMenu.add(setupGameMenuItem);
			
			return optionMenu;
		}
		
		private GameSetup getGameSetup() {
			return this.gameSetup;
		}
		
		
		private void setupUpdate(final GameSetup gameSetup) {
			setChanged();
			notifyObservers(gameSetup);
		}
		
		private static class TableGameAIWatcher implements Observer{
			
			// TODO consider change this.
			@Override
			public void update(final Observable arg0,final Object arg1) {
				// TODO Auto-generated method stub
				if(GuiTable.get().getGameSetup().isAIPlayer(GuiTable.get().getBoard().getCurrPlayer()) && !(GuiTable.get().getBoard().getCurrPlayer().isInCheckMate()) && !(GuiTable.get().getBoard().getCurrPlayer().isInStaleMate())) {
					final AIThinkTank thinkTank = new AIThinkTank();
					thinkTank.execute();
					
				}
				
				if(GuiTable.get().getBoard().getCurrPlayer().isInCheckMate()) {
					System.out.println("Game over current player checked mate" );
				}
				if(GuiTable.get().getBoard().getCurrPlayer().isInStaleMate()) {
					System.out.println("Game over game in statemate" );
				}
				
			}
			
		}
		
		public void updateComputerMove(Move move) {
			this.computerMove = move;
		}
		
		private MoveLog getMoveLog() {
			return this.moveLog;
		}
		
		private GameHistoryPanel getGameHistoryPanel() {
			return this.gameHistoryPanel;
		}
		
		private TakenPiecesPanel getTakenPiecesPanel() {
			return this.takenPiecesPanel;
		}
		
		private BoardPanel getBoardPanel() {
			return this.boardPanel;
		}
		
		private void moveMadeUpdate(final PlayerType playerType) {
			setChanged();
			notifyObservers(playerType);
			
		}
		
		private static class AIThinkTank extends SwingWorker<Move, String>{
			private AIThinkTank() {
				
			}

			@Override
			protected Move doInBackground() throws Exception {
				
				int depthThinking = GuiTable.get().getGameSetup().getSearchDepth();
				final MoveStrategy miniMax = new MiniMax(depthThinking);
				final Move bestMove = miniMax.execute(GuiTable.get().getBoard());
				return bestMove;
				
			}
			
			@Override 
			public void done() {
				
				try {
					
					final Move bestMove = get();
					GuiTable.get().updateComputerMove(bestMove);
					moveExecuter = new MoveExecuter(PieceType.QUEEN, true);	
					moveExecuter.makeMove(bestMove);
					GuiTable.get().getBoard().setCurrPlayer();
					GuiTable.get().getMoveLog().addMove(bestMove);
					GuiTable.get().getGameHistoryPanel().redo(GuiTable.get().getBoard(), GuiTable.get().getMoveLog());
					GuiTable.get().getTakenPiecesPanel().redo(GuiTable.get().getMoveLog());
					GuiTable.get().getBoardPanel().drawBoard(GuiTable.get().getBoard());
					GuiTable.get().moveMadeUpdate(PlayerType.COMPUTER);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		private class BoardPanel extends JPanel{
			final List<SpotPanel> boardSpots;
			 
			
			BoardPanel(){
				super(new GridLayout(8, 8));
				setPreferredSize(BOARD_PANAL_DIM);
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
		
		private  class SpotPanel extends JPanel{
			private final int xSpotPos;
			private final int ySpotPos;
			
			
			
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
						Move move;
						//Left click to make a move.
						if(SwingUtilities.isLeftMouseButton(event)) {
							if(sourceSpot == null) {
								sourceSpot = board.getSpot(xSpotPos, ySpotPos);
								humanMovedPiece = sourceSpot.getPiece();
								if(humanMovedPiece == null)
									sourceSpot = null;
							} else {
								
								destSpot = board.getSpot(xSpotPos, ySpotPos);
								
								move = moveFactory.createMove(sourceSpot, destSpot, humanMovedPiece, MoveType.UNKNOWN, null);
			
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
								if(gameSetup.isAIPlayer(board.getCurrPlayer())) {
									GuiTable.get().moveMadeUpdate(PlayerType.HUMAN);
								}
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
							if(!move.isAttackMove()) {
							try {
								final BufferedImage image = ImageIO.read(new File(defaultHighlightImagePath));
								final ImageIcon imageIcon = new ImageIcon(image);
								final JLabel imageLabel = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth() , imageIcon.getIconWidth() , Image.SCALE_SMOOTH)));
	
								add(imageLabel);
								
							}
							catch(Exception e) {
								e.printStackTrace();
							 	}
							}
							else {
								try {
									final BufferedImage image = ImageIO.read(new File(defaultHighlightAttackImagePath));
									final ImageIcon imageIcon = new ImageIcon(image);
									final JLabel imageLabel = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth() , imageIcon.getIconWidth(), Image.SCALE_SMOOTH)));
									add(imageLabel);
								}
								catch(Exception e) {
									e.printStackTrace();
								 	}
							}
						}
					}
				}
			}
		
		
			private  ArrayList<Move> pieceLegalMoves(Board board){
				
				ArrayList<Move> legalMoves = new ArrayList<Move>();
				if(humanMovedPiece != null && humanMovedPiece.getPlayerCoulor() == board.getCurrPlayer().getPlayerColour()) {
					legalMoves.addAll((Collection<? extends Move>) humanMovedPiece.getLegalMovements());
					if(humanMovedPiece.getPieceType() == PieceType.KING) {
						legalMoves.addAll(humanMovedPiece.getCastleMovements());
					}
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
						System.out.println(defaultPieceImagePath  + drawPiece.getPlayerCoulor().toString().substring(0, 1) + drawPiece.getPieceType().toString() + ".png");
						final BufferedImage image = ImageIO.read(new File(defaultPieceImagePath  + drawPiece.getPlayerCoulor().toString().substring(0, 1) + drawPiece.getPieceType().toString() + ".png"));
						final JLabel imageLabel =new JLabel(new ImageIcon(image));		
						add(imageLabel);
						
						
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
