package player;

import java.util.ArrayList;
import java.util.Iterator;

import enaum.PieceType;
import enaum.PlayerColour;
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
	protected PlayerColour playerColour;
	
	public Player(ArrayList<Piece> remainingPieces, Board board, PlayerColour playerColor){
		this.remainingPieces = new ArrayList <Piece>();		
		this.legalMoves = new ArrayList<Move>();
		this.legalOpponentMoves = new ArrayList<Move>();
		this.remainingPieces = remainingPieces;
		this.king = getKing();
		this.board = board;
		this.isInCheck = false;
		this.playerColour = playerColor;
	}
	
	protected abstract boolean  isInCheck();
	
	public abstract boolean  isInCheckMate();
	public abstract boolean isInStaleMate();
	
	public PlayerColour getPlayerColour() {
		return this.playerColour;
	}
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
		Piece tempPiece = move.getDestSpot().getPiece();
		move.getSourceSpot().setOccupied(false);	
		move.getPiece().setPiecePos(move.getDestSpot());
		move.getDestSpot().setPieceOnSpot(move.getPiece());
		checkMovement = player.getInCheckStatus();
		move.getPiece().setPiecePos(move.getSourceSpot());	
		move.getSourceSpot().setOccupied(true);
		
		if(tempPiece != null) {
			move.getDestSpot().setPieceOnSpot(tempPiece);
		}
		else {
			move.getDestSpot().setOccupied(false);
		}
		
		return checkMovement;
	}
	
	
	public boolean makeMove(Move move) {
		legalMoves.clear();
		legalMoves.addAll(move.getPiece().getAttackingMoves());
		legalMoves.addAll(move.getPiece().getLegalMovements());
		Spot sourceSpot;
		Spot destSpot;
		
		if(!checkLegalMove(move)) {
			
			System.out.println("illegal move");
			return false;
		}
		else {
			sourceSpot = board.getSpot(move.getSourceSpot());		// setting piece on new positioin
			destSpot = board.getSpot(move.getDestSpot());
			move.getPiece().setPiecePos(destSpot);                 // setting piece's new position
			destSpot.setPieceOnSpot(move.getPiece());
			if(move.getPiece().isFirstMove()) { 				  // If this is the first move of the piece I shall mark the first move as done. Usefull for pawn movement and castling movement.
				move.getPiece().makeFirstMove();
			}
			sourceSpot.setPieceOnSpot(null);
		}
		return true;
	}
	
	protected boolean checkLegalMove(Move move) {
		for(Move legalMove:legalMoves) {
			if(move.getDestSpot() == legalMove.getDestSpot())
				return true;
		}
		return false;
	}
	
	public abstract boolean isWhite();
	public abstract boolean isBlack();

}
