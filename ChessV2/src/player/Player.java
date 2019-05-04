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
	

	
	
	public boolean makeMove(Move move) {
		legalMoves.clear();
		legalMoves.addAll(move.getPiece().getLegalMovements());		
		Spot sourceSpot;
		Spot destSpot;
		
		if(!checkLegalMove(move)) {
			return false;
		}
		else {
			sourceSpot = board.getSpot(move.getSourceSpot());		// getting piece new positioin
			destSpot = board.getSpot(move.getDestSpot());
			move.getPiece().setPiecePos(destSpot);                 // setting piece's new position
			destSpot.setPieceOnSpot(move.getPiece());	          
			if(move.getPiece().isFirstMove()) { 				 // If this is the first move of the piece I shall mark the first move as done. Usefull for pawn movement and castling movement.
				move.getPiece().makeFirstMove();
			}
			sourceSpot.setPieceOnSpot(null);			
			if(move.isAttackMove()) {	
				board.removeAttackedPiece(move);
			}
		}
		return true;
	}
		
	protected boolean checkLegalMove(final Move move) {
		// Check if clicked piece belongs to the current active player.
		if(move.getPiece().getPlayerCoulor() != board.getCurrentPlayerColour()) {  
			return false;
		}
		// Check if the clicked move is in the current legal moves for the same piece type.
		for(Move legalMove:legalMoves) {
			if(move.getDestSpot() == legalMove.getDestSpot())
				return true;
		}
		return false;
	}
	
	public abstract boolean isWhite();
	public abstract boolean isBlack();
	
	public abstract void updateCurrentAvailablePieces();

}
