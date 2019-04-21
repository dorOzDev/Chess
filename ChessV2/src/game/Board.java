package game;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import enaum.PieceType;
import enaum.PlayerColour;
import movement.Move;
import player.Player;
import player.PlayerBlack;
import player.PlayerWhite;
import soldiers.*;

public class Board {
	public static final int NUM_ROWS = 8;
	public static final int NUM_COLS = 8;
	private static final int PAWN_SIZE = 8;
	private static final int BISHOP_SIZE = 2;
	private static final int KNIGHT_SIZE = 2;
	private static final int ROOK_SIZE = 2;
	public static Spot[][] spots;
	protected static ArrayList<Piece> piecesPlayerWhite;
	protected  ArrayList<Piece> piecesPlayerBlack;
	private static Board board = null;
	protected ArrayList<Move> legalMovesWhite = new ArrayList<Move>();
	protected ArrayList<Move> legalMovesBlack = new ArrayList<Move>();
	private  PlayerBlack playerBlack;
	private  PlayerWhite playerWhite;
	

	
	/*Creating a Singletone board 
	  */
	private Board() {
		piecesPlayerWhite = new ArrayList<Piece>();
		piecesPlayerBlack = new ArrayList<Piece>();
		spots = new Spot[NUM_ROWS][NUM_COLS];
		
		for(int i = 0; i < NUM_ROWS ; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				spots[i][j] = new Spot(i, j);
				
			}
		}	
		createPieces(piecesPlayerWhite, PlayerColour.WHITE);
		createPieces(piecesPlayerBlack, PlayerColour.BLACK);
		setPiecesPosition(piecesPlayerWhite, PlayerColour.WHITE);
		setPiecesPosition(piecesPlayerBlack, PlayerColour.BLACK);
		
		playerBlack = new PlayerBlack(piecesPlayerBlack, this);
		playerWhite = new PlayerWhite(piecesPlayerWhite, this);
		
		System.out.println(playerWhite.getInCheckStatus());

	
}
	public static  Board startNewBoard() {
		if(board == null) {
			board = new Board();	
		 }									
		else			
			System.out.println("Attempt to create two boards avoided.");
		return board;	
 }
		
	public void calcLegalBlackMoves () {
		//Calc legal moves when not in chess.
		legalMovesBlack.clear();
		for(Piece piece : piecesPlayerBlack) {			
			legalMovesBlack.addAll(piece.getAttackingMoves());
			legalMovesBlack.addAll(piece.getLegalMovements());

			}
		
	}
	public void calcLegalWhiteMoves () {
		//Calc legal moves when not in chess.
		legalMovesWhite.clear();
		//System.out.print(piecesPlayerWhite);
		System.out.println();
		for(Piece piece : piecesPlayerWhite) {
			legalMovesWhite.addAll(piece.getAttackingMoves());
			legalMovesWhite.addAll(piece.getLegalMovements());		
		}		
	}
	

	public ArrayList<Move> getAllLegalWhiteMoves() {
		calcLegalWhiteMoves();
		return legalMovesWhite;
				
	}
	
	public ArrayList<Move> getAllLegalBlackMoves() {
		calcLegalBlackMoves();
		return legalMovesBlack;
				
	}
	



	private static void createPieces(ArrayList<Piece> piecesPlayer , PlayerColour playerCoulor) {
		
		piecesPlayer.add(new King(playerCoulor,PieceType.KING));
		piecesPlayer.add(new Queen(playerCoulor,PieceType.QUEEN));
			
		for(int i = 0; i < BISHOP_SIZE; i++) {
			piecesPlayer.add(new Bishop(playerCoulor,PieceType.BISHOP));
		}
		for(int i = 0; i < KNIGHT_SIZE; i++) {
			piecesPlayer.add(new Knight(playerCoulor, PieceType.KNIGHT));
		}
		for(int i = 0; i < ROOK_SIZE; i++) {
			piecesPlayer.add(new Rook(playerCoulor, PieceType.ROOK));
		}
		for(int i = 0; i < PAWN_SIZE; i++) {
			piecesPlayer.add(new Pawn(playerCoulor,PieceType.PAWN));
		}
		
					
	}
	
	//Setting start piece position. Setting them for both player black and white. Using xPos array to determine white/block position on board. 
	private static void setPiecesPosition(ArrayList<Piece> piecesPlayer, PlayerColour playerCoulor) {
		
		int []xPos = new int[2];
		int []yPos = new int[2];
		int i = 0;
		int pieceIndex = 0;
		if(playerCoulor.equals(PlayerColour.WHITE)) { // setting X Axi's based on the player color.
			xPos[0] = 7;
			xPos[1] = 6;
		}
		else{
			xPos[0] = 0;
			xPos[1] = 1;
		}
		
		// setting King's posistion.
		if(playerCoulor.equals(PlayerColour.WHITE)){
		piecesPlayer.get(pieceIndex).setPiecePos(spots [2][2]);
		spots [2][2].setPieceOnSpot(piecesPlayer.get(pieceIndex));
		pieceIndex++;
		}
		else 
		{
			piecesPlayer.get(pieceIndex).setPiecePos(spots [3][0]);
			spots [xPos[0]][3].setPieceOnSpot(piecesPlayer.get(pieceIndex));
			pieceIndex++;
		}
		
		// setting Queen's position
		piecesPlayer.get(pieceIndex).setPiecePos(spots [xPos[0]][3]);
		spots [xPos[0]][4].setPieceOnSpot(piecesPlayer.get(pieceIndex));
		pieceIndex++;
		
		// setting Y axis for Bishop's piece;
		yPos[0] = 2;
		yPos[1] = 5;
		for(i = 0; i < BISHOP_SIZE; i++) {
			spots [xPos[0]][yPos[i]].setPieceOnSpot(piecesPlayer.get(pieceIndex));
			piecesPlayer.get(pieceIndex++).setPiecePos(spots [xPos[0]] [yPos[i]]);
			

		}
		// setting Y axis for Knight's piece;
		yPos[0] = 1;
		yPos[1] = 6;
		for(i = 0; i < KNIGHT_SIZE; i++) {
			spots [xPos[0]][yPos[i]].setPieceOnSpot(piecesPlayer.get(pieceIndex));
			piecesPlayer.get(pieceIndex++).setPiecePos(spots [xPos[0]] [yPos[i]]);
			

		}
		// setting Y axis for Rook piece;	
		yPos[0] = 0;
		yPos[1] = 7;
		for(i = 0 ;i < ROOK_SIZE; i++) {
			spots [xPos[0]][yPos[i]].setPieceOnSpot(piecesPlayer.get(pieceIndex));
			piecesPlayer.get(pieceIndex++).setPiecePos(spots [xPos[0]] [yPos[i]]);		
			

		}
		
		//Setting Pawns position.
		for(i = 0; i < PAWN_SIZE; i++) {
			spots [xPos[1]][i].setPieceOnSpot(piecesPlayer.get(pieceIndex ));
			piecesPlayer.get(pieceIndex++).setPiecePos(spots [xPos[1]] [i]);
			

		}
		
	}
	
	public ArrayList<Piece> getPiecesWhite(){
		return this.piecesPlayerWhite;
	}
	
	public ArrayList<Piece> getPiecesBlack(){
		return this.piecesPlayerBlack;
	}
	
	
}

