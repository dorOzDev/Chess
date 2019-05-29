package pieces;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.*;

public abstract class  Piece  {
	
	protected  PieceType pieceType ; 
	protected Spot spot;
	protected boolean isFirstMove;
	public PlayerColour playerCoulor;
	protected ArrayList<Move> legalMovements;
	protected MoveFactory moveFactory;
	protected int pieceValue;

	protected LinkedList<Move> candidateMovements;
	
	
	public Piece(final PlayerColour playerCoulor,final PieceType pieceType, boolean isFirstMove,final int pieceValue, Spot spot) {
		legalMovements = new ArrayList<Move>();
		candidateMovements = new LinkedList<Move>();

		this.playerCoulor = playerCoulor;
		this.pieceType = pieceType;
		
		this.isFirstMove = isFirstMove;
		this.moveFactory = new MoveFactory();
		this.pieceValue = pieceValue;
		this.spot = spot;
		
	}
	
	
	public boolean isFirstMove() {
		return isFirstMove;
	}
	
	public void makeFirstMove() {
		isFirstMove = false;
	}
	
	public PlayerColour getPlayerCoulor() {
		return playerCoulor;
	}
	public void setPlayerCoulor(PlayerColour playerCoulor) {
		this.playerCoulor = playerCoulor;
	}
	
	public void setPieceType(PieceType pieceType) {
		this.pieceType = pieceType;
		
	}
	
	public PieceType getPieceType() {
		return this.pieceType;
		
	}
	
	public void setPiecePos(Spot spot) {
		this.spot.setX(spot.getX());
		this.spot.setY(spot.getY());	
	}
	
	public void setFirstMove(boolean isFirstMove) {
		this.isFirstMove = isFirstMove;
	}

	
	public abstract void setCandidateMovements(final Board board);
	
	protected void movement(Board board) {
		candidateMovements.clear();
		legalMovements.clear();
		setCandidateMovements(board);

		
	}
	
	
	 //In order to filter all illegal moves using the following method.
	protected void setValidMovements(final Board board) {
		
	     //Adding all None Attacking movements.		
		while(!candidateMovements.isEmpty() && !candidateMovements.peek().getDestSpot().isOccupied()) {
			legalMovements.add(moveFactory.createMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot(), this, MoveType.PROGRESS_MOVE, null, board));
			
			}
		
		//Adding all attacking movements
		if(candidateMovements.iterator().hasNext()) {
			if(candidateMovements.peek().getDestSpot().getPiece().getPlayerCoulor() != this.getPlayerCoulor()) {
				legalMovements.add(moveFactory.createMove(this.spot.getSpot(), candidateMovements.peek().getDestSpot(), this, MoveType.ATTACK_MOVE, null, board));
				
			}
			candidateMovements.pop();
		}
		candidateMovements.clear();
		
	}

	
	public ArrayList<Move> getLegalMovements(final Board board){
		movement(board);
		return legalMovements;
	}

	
	/*
	 The following are adding all the candidate movements for the Rook,Queen, Bishop and King.
	 The rest of the pieces has different unique candidate movements hence using different approach. 	 
	*/
	protected void forwardMovement(final Board board) {
		candidateMovements.clear();
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;	
		
		if(!(pieceType == PieceType.KING)) {

			for(++i ; i < board.NUM_ROWS; i++)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, currY), this, MoveType.CANDIDATE_MOVE.ATTACK_MOVE, null, board));
			
		}
		else {
			if(++i < board.NUM_ROWS)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, currY), this, MoveType.CANDIDATE_MOVE, null, board));
			
		}
		setValidMovements(board);		
	}
	
	protected void backwardMovement(final Board board) {
		candidateMovements.clear();
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;	
		
		if(!(pieceType == PieceType.KING)) {		
			for(--i ; i >= 0; i--)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, currY), this, MoveType.CANDIDATE_MOVE, null, board));
			
		}
		else {
			if(--i >= 0)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, currY), this, MoveType.CANDIDATE_MOVE, null, board));			
		}
		setValidMovements(board);	
	}
	
	protected void rightSideMovement(final Board board) {
		candidateMovements.clear();
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currY;
		if(!(pieceType == PieceType.KING)) {		
			for(++i; i < board.NUM_ROWS; i++)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(currX, i), this, MoveType.CANDIDATE_MOVE, null, board));
			
		}
		else {
			if(++i < board.NUM_ROWS)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(currX, i), this, MoveType.CANDIDATE_MOVE, null, board));		
		}
		setValidMovements(board);	
	}
	
	protected void leftSideMovement(final Board board) {
		candidateMovements.clear();
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currY;
		if(!(pieceType == PieceType.KING)) {		
			
			for(--i; i >= 0; i--)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(currX, i), this, MoveType.CANDIDATE_MOVE, null, board));
			
		}
		else {
			if(--i >= 0)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(currX, i), this, MoveType.CANDIDATE_MOVE, null, board));	
		}
		setValidMovements(board);		
	}
	
	protected void diagonalBottomRightMovement(final Board board) {
		candidateMovements.clear();
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j++;
			i++;
			for(; i < board.NUM_ROWS && j < board.NUM_COLS ; i++ , j++) {
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
			}
		}
		else {
			if(++i < board.NUM_ROWS && ++j < board.NUM_COLS)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));		
		}
		setValidMovements(board);
	}
	
	protected void diagonalTopRightMovement(final Board board) {
		candidateMovements.clear();
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j++;
			i--;
			for(; i >= 0 && j < board.NUM_COLS ; i-- , j++) {
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
			}
		}
		else {
			if(--i >= 0 && ++j < board.NUM_COLS)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
		}
		setValidMovements(board);	
	}
	
	protected void diagonalBottomLeftMovement(final Board board) {
		candidateMovements.clear();
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j--;
			i++;
			for(; i < Board.NUM_ROWS && j >= 0 ; i++ , j--) {
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
			}
		}
		else {
			if(++i < board.NUM_ROWS && --j >= 0)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
		}
		setValidMovements(board);	
		
	}
	
	protected void diagonalTopLeftMovement(final Board board) {
		candidateMovements.clear();
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j--;
			i--;
			for(; i >= 0 && j >= 0 ; i-- , j--) {
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
			}
		}
		else {
			if(--i >= 0 && --j >= 0)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
		}
		setValidMovements(board);			
	}
	
	
	public Spot getSpot() {
		return this.spot;
	}
	
	
	@Override
	public String toString() {
		return "Piece:" + pieceType + "Piece Color:" + playerCoulor;
	}
	
	public int getX() {
		return spot.getX();
	}
	
	public int getY() {
		return spot.getY();
	}
	public abstract List<Move> getCastleMovements(final Board board);
	public abstract boolean isPawnPromotionMove(int rowDestinaition);
	
	public int getPieceValue() {
		return this.pieceValue;
	}
	
	public void unMakeFirstMove() {
		this.isFirstMove = true;
	}
	
	public boolean isCordinatedInBoardsBounds(int x, int y) {
		return ((x >= 0) && (x <= 7) && (y >= 0) && (y <= 7));
	}
		
}


