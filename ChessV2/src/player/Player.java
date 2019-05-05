package player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.Move;
import soldiers.*;

public abstract class Player {
	
	protected Board board;
	protected  ArrayList<Piece> remainingPieces;
	protected List<Move> legalMoves;
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
	public  Piece getKing() {
		
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
	

	
	
	public  abstract boolean makeMove(Move move);
		
	protected boolean checkLegalMove(final Move potentialMove, final Move legalMove) {
		// Check if clicked piece belongs to the current active player.
		if(potentialMove.getPiece().getPlayerCoulor() != board.getCurrentPlayerColour()) {  
			return false;
		}
		
		
		// Check if the clicked move is in the current legal moves for the same piece type.	
		if(potentialMove.getDestSpot() == legalMove.getDestSpot() && potentialMove.getPiece().getPieceType() == legalMove.getPiece().getPieceType())
			return true;
		
		return false;
	}
	
	public abstract boolean isWhite();
	public abstract boolean isBlack();
	
	public abstract void updateCurrentAvailablePieces();

}
