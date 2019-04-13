package game;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import java.util.Vector;

import enaum.PieceType;
import enaum.PlayerColour;
import soldiers.*;

public class Board {
	private static final int NUM_ROWS = 8;
	private static final int NUM_COLS = 8;
	private static final int PAWN_SIZE = 8;
	private static final int BISHOP_SIZE = 2;
	private static final int KNIGHT_SIZE = 2;
	private static final int ROOK_SIZE = 2;
	public static Spot[][] spots;
	protected static ArrayList<Piece> piecesPlayerWhite;
	protected static ArrayList<Piece> piecesPlayerBlack;
	private static Board board = null;

	
	private Board() {
		piecesPlayerWhite = new ArrayList<Piece>();
		piecesPlayerBlack = new ArrayList<Piece>();
		spots = new Spot[NUM_ROWS][NUM_COLS];
	}

	public static Board startNewBoard() {
		if(board == null) {
			board = new Board();
			for(int i = 0; i < NUM_ROWS ; i++) {
				for(int j = 0; j < NUM_COLS; j++) {
					spots[i][j] = new Spot(i, j);
					
				}			
		 }

			
			createPieces(piecesPlayerWhite, PlayerColour.WHITE);
			createPieces(piecesPlayerBlack, PlayerColour.BLACK);
			setPiecesPosition(piecesPlayerWhite, PlayerColour.WHITE);
			setPiecesPosition(piecesPlayerBlack, PlayerColour.BLACK);
			
			
			piecesPlayerWhite.get(4).movement();
			System.out.println(piecesPlayerWhite.get(4).getMovements());
			
			

				
		}
		else			
			System.out.println("Attempt to create two boards avoided.");
				
		return board;
	
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
	
	//Setting pieces positions is depeneded on the order of pieces createing, thus setting the position is in the exact same order as creating.
	private static void setPiecesPosition(ArrayList<Piece> piecesPlayer, PlayerColour playerCoulor) {
		
		int []x = new int[2];
		int []y = new int[2];
		int i = 0;
		int pieceIndex = 0;
		if(playerCoulor.equals(PlayerColour.WHITE)) { // setting X Axi's based on the player color.
			x[0] = 7;
			x[1] = 6;
		}
		else{
			x[0] = 0;
			x[1] = 1;
		}
		
		piecesPlayer.get(pieceIndex).setStartPos(spots [x[0]][4]);// setting King's posistion.
		spots [x[0]][3].setPieceOnSpot(piecesPlayer.get(pieceIndex));
		pieceIndex++;
		
		piecesPlayer.get(pieceIndex).setStartPos(spots [x[0]][3]);// setting Queen's position
		spots [x[0]][4].setPieceOnSpot(piecesPlayer.get(pieceIndex));
		pieceIndex++;
		
		// setting Y axis for Bishop's piece;
		y[0] = 2;
		y[1] = 5;
		for(i = 0; i < BISHOP_SIZE; i++) {
			spots [x[0]][y[i]].setPieceOnSpot(piecesPlayer.get(pieceIndex));
			piecesPlayer.get(pieceIndex++).setStartPos(spots [x[0]] [y[i]]);
			

		}
		// setting Y axis for Knight's piece;
		y[0] = 1;
		y[1] = 6;
		for(i = 0; i < KNIGHT_SIZE; i++) {
			spots [x[0]][y[i]].setPieceOnSpot(piecesPlayer.get(pieceIndex));
			piecesPlayer.get(pieceIndex++).setStartPos(spots [x[0]] [y[i]]);
			

		}
		// setting Y axis for Rook piece;	
		y[0] = 0;
		y[1] = 7;
		for(i = 0 ;i < ROOK_SIZE; i++) {
			spots [x[0]][y[i]].setPieceOnSpot(piecesPlayer.get(pieceIndex));
			piecesPlayer.get(pieceIndex++).setStartPos(spots [x[0]] [y[i]]);		
			

		}
		
		//Setting Pawns position.
		for(i = 0; i < PAWN_SIZE; i++) {
			spots [x[1]][i].setPieceOnSpot(piecesPlayer.get(pieceIndex ));
			piecesPlayer.get(pieceIndex++).setStartPos(spots [x[1]] [i]);
			

		}
		
	}
	
	public ArrayList<Piece> getPiecesWhite(){
		return this.piecesPlayerWhite;
	}
	
	public ArrayList<Piece> getPiecesBlack(){
		return this.piecesPlayerBlack;
	}
	
	
}

