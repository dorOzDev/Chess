package player;

import java.util.ArrayList;
import java.util.Iterator;

import enaum.PieceType;
import game.Board;
import game.Spot;
import movement.Move;
import soldiers.*;

public abstract class Player {
	
	protected Board board;
	protected  ArrayList<Piece> remainingPieces;
	protected ArrayList<Move> legalMoves;
	//Player opponent;
	protected final Piece king;
	protected ArrayList<Move> legalOpponentMoves;
	protected boolean isInCheck;
	
	public Player(ArrayList<Piece> remainingPieces, Board board){
		this.remainingPieces = new ArrayList <Piece>();		
		this.legalMoves = new ArrayList<Move>();
		this.legalOpponentMoves = new ArrayList<Move>();
		this.remainingPieces = remainingPieces;
		this.king = getKing();
		this.board = board;
		this.isInCheck = false;
	}
	
	protected abstract boolean  isInCheck();
	
	public abstract boolean  isInCheckMate();
	public abstract boolean isInStaleMate();
	
	
	protected  Piece getKing() {
		
		for(Piece piece: remainingPieces) {
			if(piece.getPieceType() == PieceType.KING){
				return piece;
			}
		}
		throw new RuntimeException("Shouldn't reach here without king!@#$");
	}
	
	public boolean getInCheckStatus() {
		isInCheck = isInCheck();
		return isInCheck;
	}
	
	//While in chess only legal moves are ones that escapes the king
	protected void filterInChessMoves(ArrayList<Move> legalMoves) {
		Iterator<Move> moveIterator = legalMoves.iterator();
		if(this.getInCheckStatus()) {
			while(moveIterator.hasNext()) {
				if(!testMove(moveIterator.next(), this)) {
					moveIterator.remove();
				}	
			}
		}
	}
	
	// Return true if this move escapes the king from chess.	
	//TODO Keep checking this when GUI is installed, the king might get in chess falsely
	//Possible solution to check only if getting out of threatening piece chess.
	private boolean testMove(Move move, Player player) {
		boolean checkMovement;
		Piece tempPiece = move.getDestSpot().getPieceBySpot();
		move.getSourceSpot().setOccupied(false);	
		move.getPieceToMove().setPiecePos(move.getDestSpot());
		move.getDestSpot().setPieceOnSpot(move.getPieceToMove());
		checkMovement = player.getInCheckStatus();
		move.getPieceToMove().setPiecePos(move.getSourceSpot());	
		move.getSourceSpot().setOccupied(true);
		
		if(tempPiece != null) {
			move.getDestSpot().setPieceOnSpot(tempPiece);
		}
		else {
			move.getDestSpot().setOccupied(false);
		}
		
		return checkMovement;
	}


}
