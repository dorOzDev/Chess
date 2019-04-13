package player;

import java.util.ArrayList;

import enaum.PieceType;
import game.Board;
import game.Spot;
import soldiers.*;

public abstract class Player {
	
	protected Board board;
	protected  ArrayList<Piece> remainingPieces;
	protected ArrayList<Spot> legalMoves;
	Player opponent;
	Piece king;
	
	
	Player(){
		
	}
	
	public boolean isInCheck() {
		return false;
	}
	
	public boolean  isInCheckMate() {
		return false;
	}
	
	public boolean isInStaleMate() {
		return false;
	}
	
	
	public  Piece getKing() {
		for(Piece piece: remainingPieces) {
			if(piece.getPieceType() == PieceType.KING){
				return piece;
			}
		}
		throw new RuntimeException("Shouldn't reach here without king!@#$");
	}

}
