package gui;

import game.Board;
import game.Board.BoardBuilder;
import game.FenFormat;
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
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import enaum.PlayerColour;
import enaum.PlayerType;
public class GuiTable extends Observable{
	
	private final JFrame frame;
	private final GameHistoryPanel gameHistoryPanel;
	private final TakenPiecesPanel takenPiecesPanel;
	private final JMenuBar menuBar;
	private final BoardPanel boardPanel;
	private boolean highLightLegalMoves = true;
	private static Board board;
	private final MoveLog moveLog;
	private Spot sourceSpot;
	private Spot destSpot;
	private Piece humanMovedPiece;
	private Move lastMove;
	private MoveFactory moveFactory;
	private final GameSetup gameSetup;
	private Move computerMove;
	private Terminal terminal;



	static final String defaultPieceImagePath = "art/pieces/";
	static final String defaultHighLightMovePath = "art/misc/grey_dot.png";
	static final String defaultTakenPiecePanel = "art/TakenPiece/";


	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(1050, 1050);
	private final static Dimension BOARD_PANAL_DIM = new Dimension(850, 850);
	private final static Dimension SPOT_PANEL_DIM = new Dimension(100, 100);

	
	private final Color lightSpotColor= Color.decode("#FFFACD");
	private final Color darkSpotColor = Color.decode("#705124");

	
	private static final GuiTable guiTable = new GuiTable();
	
	/*******************
	 * Two different design patterns here:
	 * GuiTable is a singleton design pattern.
	 * Also the whole GUI is MVC Design patterns:
	 * The model is the Board class which holds the current state of the game and has access to all the logic of the game.
	 * We have 3 Views: the GameHistoryPanel, GameSetup, TakenPiecesPanel
	 *  Last the very same Singleton Gui table acts as the Controller in the MVC design pattern.
	 * 
	 */

	private GuiTable() {

		this.frame = new JFrame("Chess Game");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startNewBoard();
		this.terminal = new Terminal(board);
		this.frame.setLayout(new BorderLayout());
		this.menuBar = createTableMenuBar();
		this.gameHistoryPanel = new GameHistoryPanel();
		this.takenPiecesPanel = new TakenPiecesPanel(board);
		this.frame.setJMenuBar(menuBar);
		this.frame.setSize(OUTER_FRAME_DIMENSION);
		this.frame.add(takenPiecesPanel, BorderLayout.WEST);
		this.frame.add(gameHistoryPanel, BorderLayout.EAST);	
		this.frame.add(terminal, BorderLayout.SOUTH);
		this.moveLog = new MoveLog();
		this.boardPanel = new BoardPanel();
		addObserver(new ExecuterAIMove());
		addObserver(terminal);
        this.frame.add(boardPanel, BorderLayout.CENTER);
		center(this.frame);
		this.frame.setVisible(true);
		this.moveFactory = new MoveFactory();
		this.gameSetup = new GameSetup(this.frame, true);
	}
	
	private void startNewBoard() {
		board = new BoardBuilder().build();
		board.getTakenPieces().clear();
		board.setStartingPlayer();
	}
	
	private void restartGame() {

		startNewBoard();
		this.terminal.init();
		this.moveLog.clear();
		this.gameHistoryPanel.redo(board, moveLog);
		this.takenPiecesPanel.redo(moveLog);
		this.boardPanel.drawBoard(board);		
	}

	public static GuiTable get() {
		return guiTable;
	}

	
	private void center(JFrame frame) {
		//This method centres the board in the middle of the screen
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
			final JMenuItem startNewGame = new JMenuItem("Start new game");
			final JMenuItem exitMenuItem = new JMenuItem("Exit");
			
			startNewGame.addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					restartGame();					
				}

			});
			fileMenu.add(startNewGame);
			
		
			
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
					GuiTable.get().setupUpdate(GuiTable.get().getGameSetup());
					
				}
			});
			
			optionMenu.add(setupGameMenuItem);
			
			return optionMenu;
		}
		
		private GameSetup getGameSetup() {
			return this.gameSetup;
		}
		
		//Notify observers when playing against computer decision was made
		private void setupUpdate(final GameSetup gameSetup) {
			setChanged();
			notifyObservers(gameSetup);
		}
		
		//Notify observers when new move was made
		private void moveMadeUpdate(final Board board) {

			setChanged();
			notifyObservers(board);		
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
		
		
		private  class ExecuterAIMove implements Observer{		
			@Override
			public void update(final Observable arg0, final Object arg1) {
				// TODO Auto-generated method stub
				if( GuiTable.get().getGameSetup().isAIPlayer(GuiTable.get().getBoard().getCurrPlayer()) && !(GuiTable.get().getBoard().isInCheckMate(board.getCurrentPlayerColour())) && !(GuiTable.get().getBoard().isInStaleMate(board.getCurrentPlayerColour()))) {
					final AIMoveDecider thinkTank = new AIMoveDecider();
					thinkTank.execute();	
				}			
				
			}
			
		}
		
		

		private static class AIMoveDecider extends SwingWorker<Move, String>{
			private AIMoveDecider() {
				
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
					if(!GuiTable.get().getGameSetup().isAIPlayer(GuiTable.get().getBoard().getCurrPlayer())) {						
						board.setCurrentPlayer();
					}			
					final Board destionatioBoard = GuiTable.get().getBoard().getCurrPlayer().makeMove(bestMove, GuiTable.get().getBoard(), true);
					board = destionatioBoard;
					GuiTable.get().getMoveLog().addMove(bestMove);
					GuiTable.get().getGameHistoryPanel().redo(GuiTable.get().getBoard(), GuiTable.get().getMoveLog());
					GuiTable.get().getTakenPiecesPanel().redo(GuiTable.get().getMoveLog());
					GuiTable.get().getBoardPanel().drawBoard(board);
					GuiTable.get().moveMadeUpdate(board);
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
				drawBoard(board);
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
			
			
			public SpotPanel getSpotPanel(int x, int y) {
				Iterator<SpotPanel> itr = boardSpots.iterator();
				SpotPanel tempSpotPanel;
				while(itr.hasNext()) {
					tempSpotPanel = itr.next();
					if(tempSpotPanel.xSpotPos == x && tempSpotPanel.ySpotPos == y) {
						return tempSpotPanel;
					}					
				}
				return null;
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
		
		private  class SpotPanel extends JPanel {
			private final int xSpotPos;
			private final int ySpotPos;
			
			
			
			SpotPanel(final BoardPanel boardPanel, final int xSpotPos, final int ySpotPos){
				super(new GridBagLayout());
				this.xSpotPos= xSpotPos;
				this.ySpotPos = ySpotPos;
				setPreferredSize(SPOT_PANEL_DIM);		
				assignPiecePanel(board);
				assignSpotColor();
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
								move = moveFactory.createMove(sourceSpot, destSpot, humanMovedPiece, MoveType.UNKNOWN, null, board);
								final Board destinationBoard = board.getCurrPlayer().makeMove(move, board, true);								 
								 if(destinationBoard != board){			
										board = destinationBoard;
										moveLog.addMove(move);
										if(!gameSetup.isAIPlayer(board.getCurrPlayer())){
											GuiTable.get().moveMadeUpdate(board);
										}
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
									GuiTable.get().moveMadeUpdate(board);									
								}
								boardPanel.drawBoard(board);												
							}
						});
						
						
					//}
						}});
				

				validate();
				
			}
			

			private void highlightLegalMoves(Board board) {
				if(highLightLegalMoves) {
					for(final Move move : pieceLegalMoves(board)) {
						if(this.xSpotPos == move.getDestSpot().getX() && this.ySpotPos == move.getDestSpot().getY()) {
						try {
							add(new JLabel(new ImageIcon(ImageIO.read(new File(defaultHighLightMovePath)))));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					}
				}
			}
		
		
			private  ArrayList<Move> pieceLegalMoves(Board board){
				List<Move> legalPlayerMoves = new ArrayList<Move>();
				ArrayList<Move> legalPieceMove = new ArrayList<Move>();
				if(humanMovedPiece == null || humanMovedPiece.getPlayerCoulor() != board.getCurrPlayer().getPlayerColour()) {
					return legalPieceMove;
					}
				

				
				legalPlayerMoves = board.getAllLegalMoves(board.getCurrentPlayerColour());
				for(Move move : legalPlayerMoves) {
					if(move.getPiece().getPieceType() == humanMovedPiece.getPieceType() && move.getSourceSpot().getX() == humanMovedPiece.getX() && move.getSourceSpot().getY() == humanMovedPiece.getY()) {
						legalPieceMove.add(move);
					}
				}
	
				
				return legalPieceMove;
			}
			
			private void drawSpot(final Board board) {
				assignPiecePanel(board);
				assignSpotColor();
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
						final JLabel imageLabel = new JLabel(new ImageIcon(image));		
						this.add(imageLabel);
												
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
