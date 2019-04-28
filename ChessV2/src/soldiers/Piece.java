package soldiers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.CandidateMove;
import movement.*;

public abstract class  Piece  {
	
	protected Board board;
	protected  PieceType pieceType ; 
	protected Spot spot;
	protected boolean isFirstMove;
	public PlayerColour playerCoulor;
	protected ArrayList<Move> legalMovements;

	protected LinkedList<Move> candidateMovements;
	
	
	public Piece(PlayerColour playerCoulor, PieceType pieceType, Board board, boolean isFirstMove) {
		legalMovements = new ArrayList<Move>();
		candidateMovements = new LinkedList<Move>();

		this.playerCoulor = playerCoulor;
		this.pieceType = pieceType;
		this.board = board;
		this.isFirstMove = isFirstMove;
		
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
	
	public abstract void setPiecePos(Spot spot);
	
	public abstract void setCandidateMovements();
	
	protected void movement() {
		candidateMovements.clear();
		legalMovements.clear();
		setCandidateMovements();

		
	}
	
	/*
	 In order to filter all illegal moves using the following method.
	*/
	protected void setValidMovements() {
		
	     //Adding all None Attacking movements.		
		while(!candidateMovements.isEmpty() && !candidateMovements.peek().getDestSpot().isOccupied()) {
			legalMovements.add(new NoneAttackMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot(), this));

			}
		
		//Adding all attacking movements
		if(candidateMovements.iterator().hasNext()) {
			if(candidateMovements.peek().getDestSpot().getPiece().getPlayerCoulor() != this.getPlayerCoulor()) {
				legalMovements.add(new AttackMove(this.spot.getSpot(), candidateMovements.peek().getDestSpot(), this, candidateMovements.peek().getDestSpot().getPiece()));
			}
			candidateMovements.pop();
		}
		candidateMovements.clear();
		
	}

	
	public ArrayList<Move> getLegalMovements(){
		movement();
		return legalMovements;
	}

	
	/*
	 The following are adding all the candidate movements for the Rook,Queen, Bishop and King.
	 The rest of the pieces has diffrent unique candidate movements hence using diffrent approach. 	 
	*/
	protected void forwardMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;	
		
		if(!(pieceType == PieceType.KING)) {

			for(++i ; i < board.spots.length; i++)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][currY].getSpot(), this));		
		}
		else {
			if(++i < board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][currY], this));
			
		}
		setValidMovements();		
	}
	
	protected void backwardMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;	
		
		if(!(pieceType == PieceType.KING)) {		
			for(--i ; i >= 0; i--)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][currY].getSpot(), this));
			
		}
		else {
			if(--i >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][currY], this));			
		}
		setValidMovements();	
	}
	
	protected void rightSideMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currY;
		if(!(pieceType == PieceType.KING)) {		
			for(++i; i < board.spots.length; i++)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[currX][i].getSpot(), this));
			
		}
		else {
			if(++i < board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[currX][i].getSpot(), this));		
		}
		setValidMovements();	
	}
	
	protected void leftSideMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currY;
		if(!(pieceType == PieceType.KING)) {		
			
			for(--i; i >= 0; i--)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[currX][i].getSpot(), this));
			
		}
		else {
			if(--i >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[currX][i].getSpot(), this));	
		}
		setValidMovements();		
	}
	
	protected void diagonalBottomRightMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j++;
			i++;
			for(; i < board.spots.length && j < board.spots.length ; i++ , j++) {
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
			}
		}
		else {
			if(++i > board.spots.length && ++j > board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));		
		}
		setValidMovements();
	}
	
	protected void diagonalTopRightMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j++;
			i--;
			for(; i >= 0 && j < board.spots.length ; i-- , j++) {
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
			}
		}
		else {
			if(--i >= 0 && j++ < board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
		}
		setValidMovements();	
	}
	
	protected void diagonalBottomLeftMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j--;
			i++;
			for(; i < board.spots.length && j >= 0 ; i++ , j--) {
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
			}
		}
		else {
			if(++i < board.spots.length && --j >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
		}
		setValidMovements();	
		
	}
	
	protected void diagonalTopLeftMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j--;
			i--;
			for(; i >= 0 && j >= 0 ; i-- , j--) {
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
			}
		}
		else {
			if(--i >= 0 && j >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
		}
		setValidMovements();			
	}
	
	
	public Spot getSpot() {
		return this.spot;
	}
	
	
	@Override
	public String toString() {
		return "Piece:" + pieceType + "Piece Color:" + playerCoulor;
		
	}
		
}


