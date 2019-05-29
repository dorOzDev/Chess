package player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.CommandMove;
import movement.CommandMove.ExecuteMove;
import movement.CommandMove.MoveExecuter;
import pieces.*;
import movement.Move;

public abstract class Player {
	
	protected Board board;
	protected  ArrayList<Piece> remainingPieces;
	protected List<Move> legalMoves;
	//Player opponent;
	//protected final Piece king;
	protected ArrayList<Move> legalOpponentMoves;
	protected boolean isInCheck;
	protected PlayerColour playerColour;
	protected MoveExecuter moveExecuter;
	protected PieceType preferdPieceTypePormotion;
	
	public Player(ArrayList<Piece> remainingPieces, Board board, PlayerColour playerColor){
		this.remainingPieces = new ArrayList <Piece>();		
		this.legalMoves = new ArrayList<Move>();
		this.legalOpponentMoves = new ArrayList<Move>();
		this.remainingPieces = remainingPieces;
		this.board = board;
		this.isInCheck = false;
		this.playerColour = playerColor;
		
		//Default piece promotion shall be Queen
		this.preferdPieceTypePormotion = PieceType.QUEEN;
	}
	
	public void setPreferedPieceTypePromotion(PieceType pieceType) {
		this.preferdPieceTypePormotion = pieceType;
	}
	public abstract boolean  isInCheck();
	
	public abstract boolean  isInCheckMate();
	public abstract boolean isInStaleMate();
	
	public PlayerColour getPlayerColour() {
		return this.playerColour;
	}
	public abstract  Piece getKing();
	


	public abstract List<Move> getLegalMoves();
	
	public  abstract Board makeMove(Move move, Board board);
		
	protected boolean checkLegalMove(final Move potentialMove, final Move legalMove) {
		// Check if clicked piece belongs to the current active player.
		if(potentialMove.getPiece().getPlayerCoulor() != board.getCurrentPlayerColour()) {  
			return false;
		}
		
		// Check if the clicked move is in the current legal moves for the same piece type.	
		if(legalMove.getDestSpot().getX() == potentialMove.getDestSpot().getX() && legalMove.getDestSpot().getY() == potentialMove.getDestSpot().getY() && legalMove.getPiece().getPieceType() == potentialMove.getPiece().getPieceType()) {
			return true;
		}
		
		return false;
	}
	
	public abstract boolean isWhite();
	public abstract boolean isBlack();
	
	public abstract void updateCurrentAvailablePieces();
	
	public abstract List<Piece> getPlayerRemaningPieces();
	
	public abstract Player getOpponent ();
	
	public abstract boolean hasKingCaptured();
}
