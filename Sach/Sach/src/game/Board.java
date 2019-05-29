package game;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerColour;
import game.StrategyGameLogic.*;
import movement.AttackMove;
import movement.Move;
import movement.ProgressMove;
import pieces.*;
import player.Player;
import player.PlayerBlack;
import player.PlayerWhite;

public class Board {
	public static final int NUM_ROWS = 8;
	public static final int NUM_COLS = 8;
	private static final int PAWN_SIZE = 8;
	private static final int BISHOP_SIZE = 2;
	private static final int KNIGHT_SIZE = 2;
	private static final int ROOK_SIZE = 2;
	private static final int OFFSET_CLEAN_ROW_COUNT = 8; 
	public static Spot[][] spots;
	protected static ArrayList<Piece> piecesPlayerWhite;
	protected  ArrayList<Piece> piecesPlayerBlack;
	protected ArrayList<Piece> takenPieces;
	private static Board board = null;
	public Move lastMove = null;
	protected ArrayList<Move> legalMovesWhite = new ArrayList<Move>();
	protected ArrayList<Move> legalMovesBlack = new ArrayList<Move>();
	private  PlayerBlack playerBlack;
	private  PlayerWhite playerWhite;
	private Player currPlayer;
	private ContextGameLogic contextGameLogic;
	private PieceFactory pieceFactory;
	
	

	/*******************
	 * Creating the board using singleton design pattern. 
	 *  The board class in responsible for the current state of the board, keeps track of remaining pieces on board, which player's turn is  and so on.
	 *******************/
	private Board() {
		piecesPlayerWhite = new ArrayList<Piece>();
		piecesPlayerBlack = new ArrayList<Piece>();
		takenPieces = new ArrayList<Piece>();
		pieceFactory = new PieceFactory();
		spots = new Spot[NUM_ROWS][NUM_COLS];
		
		for(int i = 0; i < NUM_ROWS ; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				spots[i][j] = new Spot(i, j);
				
			}
		}	
		
		createBlackPieces();
		createWhitePieces();
		
		playerBlack = new PlayerBlack(piecesPlayerBlack, this);
		playerWhite = new PlayerWhite(piecesPlayerWhite, this);
		updateSpots();
		setCurrPlayer();  // Set starting player.
		
}
	public void setLastMove(Move lastMove) {
		this.lastMove = lastMove;
	}
	
	public Move getLastMove() {
		return this.lastMove;
	}
	
	public static  Board startNewBoard() {
		if(board == null) {
			board = new Board();
			
		 }									
		else {	
			//System.out.println("Attempt to create two boards avoided.");
		}


		return board;	
 }
	
	public PlayerColour getCurrentPlayerColour() {
		if(currPlayer.getPlayerColour() == PlayerColour.BLACK) {
			return PlayerColour.BLACK;
		} 
		else if(currPlayer.getPlayerColour() == PlayerColour.WHITE) {
			return PlayerColour.WHITE;
		}
		else {
			System.out.println("Shouldn't reach here");
			return null;
		}
	}
	
	//TODO  need to realize how to operate this two.
	public Player getCurrPlayer() {
		return currPlayer;
		
	}

	public void setCurrPlayer() {
		// Letting white player play first.
		if(currPlayer == null) {
			currPlayer = playerWhite;
		}		
		else {		
			currPlayer = ((currPlayer == playerWhite) ? playerBlack : playerWhite);
		}
		
	}
	
	public Board getUpdatedBoard() {
		return this;
	}

	private void createBlackPieces() {
		piecesPlayerBlack.add(pieceFactory.createPiece(PlayerColour.BLACK, PieceType.KING, this, getSpot(0, 4)));
		piecesPlayerBlack.add(pieceFactory.createPiece(PlayerColour.BLACK, PieceType.QUEEN, this, getSpot(0, 3)));
		piecesPlayerBlack.add(pieceFactory.createPiece(PlayerColour.BLACK, PieceType.BISHOP, this, getSpot(0, 2)));
		piecesPlayerBlack.add(pieceFactory.createPiece(PlayerColour.BLACK, PieceType.BISHOP, this, getSpot(0, 5)));
		piecesPlayerBlack.add(pieceFactory.createPiece(PlayerColour.BLACK, PieceType.KNIGHT, this, getSpot(0, 1)));
		piecesPlayerBlack.add(pieceFactory.createPiece(PlayerColour.BLACK, PieceType.KNIGHT, this, getSpot(0, 6)));
		piecesPlayerBlack.add(pieceFactory.createPiece(PlayerColour.BLACK, PieceType.ROOK, this, getSpot(0, 0)));
		piecesPlayerBlack.add(pieceFactory.createPiece(PlayerColour.BLACK, PieceType.ROOK, this, getSpot(0, 7)));
		
		for(int i = 0; i < PAWN_SIZE; i++) {
			piecesPlayerBlack.add(pieceFactory.createPiece(PlayerColour.BLACK, PieceType.PAWN, this, getSpot(1, i)));
		}
	}
	
	private void createWhitePieces() {
		piecesPlayerWhite.add(pieceFactory.createPiece(PlayerColour.WHITE, PieceType.KING, this, getSpot(7, 4)));
		piecesPlayerWhite.add(pieceFactory.createPiece(PlayerColour.WHITE, PieceType.QUEEN, this, getSpot(7, 3)));
		piecesPlayerWhite.add(pieceFactory.createPiece(PlayerColour.WHITE, PieceType.BISHOP, this, getSpot(7, 2)));
		piecesPlayerWhite.add(pieceFactory.createPiece(PlayerColour.WHITE, PieceType.BISHOP, this, getSpot(7, 5)));
		piecesPlayerWhite.add(pieceFactory.createPiece(PlayerColour.WHITE, PieceType.KNIGHT, this, getSpot(7, 1)));
		piecesPlayerWhite.add(pieceFactory.createPiece(PlayerColour.WHITE, PieceType.KNIGHT, this, getSpot(7, 6)));
		piecesPlayerWhite.add(pieceFactory.createPiece(PlayerColour.WHITE, PieceType.ROOK, this, getSpot(7, 0)));
		piecesPlayerWhite.add(pieceFactory.createPiece(PlayerColour.WHITE, PieceType.ROOK, this, getSpot(7, 7)));
		
		for(int i = 0; i < PAWN_SIZE; i++) {
			piecesPlayerWhite.add(pieceFactory.createPiece(PlayerColour.WHITE, PieceType.PAWN, this, getSpot(6, i)));
		}
	}

	
	public Spot getSpot(int x, int y) {
		return spots[x][y].getSpot();
	}
	public Spot getSpot(Spot spot) {
		
		return spots[spot.getX()] [spot.getY()];
	}
	
	
	public ArrayList<Piece> getPiecesWhite(){
		return this.piecesPlayerWhite;
	}
	
	public ArrayList<Piece> getPiecesBlack(){
		return this.piecesPlayerBlack;
	}
	

	

	public List<Move> getAllLegalWhiteMoves() {
		contextGameLogic = new ContextGameLogic(new OperationWhitePlayer());
		return contextGameLogic.getLegalMoves(playerWhite.getKing());
				
	}
	
	public List<Move> getAllLegalBlackMoves() {
		contextGameLogic = new ContextGameLogic(new OperationBlackPlayer());
		return contextGameLogic.getLegalMoves(playerBlack.getKing());
				
	}

	public String castToBoardCordinate(Spot destSpot) {
		
		char intToAlgebric =(char) (destSpot.getY() + 'a'); // Cast row number to letter
		return intToAlgebric + Integer.toString(Math.abs(destSpot.getX() - OFFSET_CLEAN_ROW_COUNT)) ;
	}
	
	public void addPiece(Piece piece) {
		if(piece.playerCoulor == PlayerColour.WHITE) {
			piecesPlayerWhite.add(piece);
		}
		else {
			piecesPlayerBlack.add(piece);
		}
		getSpot(piece.getSpot()).setPieceOnSpot(piece);
	}
	
	public void removePiece(Piece pieceToRemove, boolean updateGui) {
		
		Iterator<Piece>itr;
		Piece tempPiece;
		if(pieceToRemove.getPlayerCoulor() == PlayerColour.WHITE) {
			itr = piecesPlayerWhite.iterator();
			
		}
		else {
			itr = piecesPlayerBlack.iterator();
		}
		
		
		while(itr.hasNext()) {
			tempPiece = itr.next();
			
			if(pieceToRemove == tempPiece) {
				if(updateGui) {					
					takenPieces.add(tempPiece);
				}
				getSpot(pieceToRemove.getSpot()).setPieceOnSpot(null);
				itr.remove();
				
				break;
			}
		}
	}
	
	public void updateSpots() {
		for(Piece piece : piecesPlayerBlack) {
			getSpot(piece.getSpot()).setPieceOnSpot(piece);
		}
		
		for(Piece piece : piecesPlayerWhite) {
			getSpot(piece.getSpot()).setPieceOnSpot(piece);
		}
	}
	
	public void updatePieceSpot(Piece pieceTomove, Spot sourceSpot, Spot destSpot) {

		getSpot(sourceSpot).setPieceOnSpot(null);
		pieceTomove.setPiecePos(destSpot);
		getSpot(destSpot).setPieceOnSpot(pieceTomove);
		
	}
		

	public ArrayList<Piece> getTakenPieces(){
		return this.takenPieces;
	}
	
	
	public boolean getInCheckStatusWhitePlayer() {
		contextGameLogic = new ContextGameLogic(new OperationWhitePlayer());
		return contextGameLogic.getInCheckStatus(playerWhite.getKing());
	}
	
	public boolean getInCheckStatusBlackPlayer() {
		contextGameLogic = new ContextGameLogic(new OperationBlackPlayer());
		return contextGameLogic.getInCheckStatus(playerBlack.getKing());
	}
	
	public boolean isCastleAllowedBlackPlayer(Move move, Piece king) {
		contextGameLogic = new ContextGameLogic(new OperationBlackPlayer());
		return contextGameLogic.isCastleAllowed(move, king);
	}
	
	public boolean isCastleAllowedWhitePlayer(Move move, Piece king) {
		contextGameLogic = new ContextGameLogic(new OperationWhitePlayer());
		return contextGameLogic.isCastleAllowed(move, king);
	}
	
	
	public boolean isInCheckMateBlackPlayer() {
		contextGameLogic = new ContextGameLogic(new OperationBlackPlayer());
		return contextGameLogic.getInCheckMateStatus(playerBlack.getKing());	
		
	}
	
	public boolean isInCheckMateWhitePlayer() {
		contextGameLogic = new ContextGameLogic(new OperationWhitePlayer());
		return contextGameLogic.getInCheckMateStatus(playerWhite.getKing());			
	}
	
	public boolean isInStaleMateBlackPlayer() {
		contextGameLogic = new ContextGameLogic(new OperationBlackPlayer());
		return contextGameLogic.getInStaleMateStatus(playerBlack.getKing());
	}
	
	public boolean isInStaleMateWhitePlayer() {
		contextGameLogic = new ContextGameLogic(new OperationWhitePlayer());
		return contextGameLogic.getInStaleMateStatus(playerWhite.getKing());
	}


	
	public Player getWhitePlayer() {
		return this.playerWhite;
	}
	
	public Player getBlackPlayer() { 
		return this.playerBlack;
	}
	

	
	
}




