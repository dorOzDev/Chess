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
	public PlayerColour playerCoulor;
	protected ArrayList<Move> legalMovements;
	protected ArrayList<Move> legalAttackMovements;
	protected LinkedList<Move> candidateMovements;
	
	
	public Piece(PlayerColour playerCoulor, PieceType pieceType) {
		legalMovements = new ArrayList<Move>();
		candidateMovements = new LinkedList<Move>();
		legalAttackMovements= new ArrayList<Move>();
		this.playerCoulor = playerCoulor;
		this.pieceType = pieceType;
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
	
	public abstract void setStartPos(Spot spot);
	public abstract void movement();
	public abstract void setCandidateMovements();
	
	
	/*
	 In order to filter all illegal moves using the following method.
	*/
	public void setValidMovements() {
		
		
	     //Adding all None Attacking movements.		
		while(!candidateMovements.isEmpty() && !candidateMovements.peek().getDestSpot().isOccupied()) {
			legalMovements.add(new NoneAttackMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot()));

			}
		
		//Adding all attacking movements
		if(candidateMovements.iterator().hasNext()) {
			if(candidateMovements.peek().getDestSpot().getPieceBySpot().getPlayerCoulor() != this.getPlayerCoulor()) {
				legalAttackMovements.add(new AttackMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot()));
			}
		}
		candidateMovements.clear();
		
	}

	
	public ArrayList<Move> getMovements(){
		return legalMovements;
	}
	
	
	
	/*
	 The following are adding all the candidate movements for the Rook,Queen, Bishop and King.
	 The rest of the pieces has diffrent unique candidate movements hence using diffrent approach. 
	 
	*/
	public void forwardMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;	
		
		if(!(pieceType == PieceType.KING)) {

			for(++i ; i < board.spots.length; i++)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][currY].getSpot()));		
		}
		else {
			if(++i < board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][currY]));
			
		}
		setValidMovements();		
	}
	
	public void backwardMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;	
		
		if(!(pieceType == PieceType.KING)) {		
			for(--i ; i >= 0; i--)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][currY].getSpot()));
			
		}
		else {
			if(--i >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][currY]));			
		}
		setValidMovements();	
	}
	
	public void rightSideMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currY;
		if(!(pieceType == PieceType.KING)) {		
			for(++i; i < board.spots.length; i++)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[currX][i].getSpot()));
			
		}
		else {
			if(++i < board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[currX][i].getSpot()));		
		}
		setValidMovements();	
	}
	
	public void leftSideMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currY;
		if(!(pieceType == PieceType.KING)) {		
			
			for(--i; i >= 0; i--)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[currX][i].getSpot()));
			
		}
		else {
			if(--i >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[currX][i].getSpot()));	
		}
		setValidMovements();		
	}
	
	public void diagonalBottomRightMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j++;
			i++;
			for(; i < board.spots.length && j < board.spots.length ; i++ , j++) {
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
			}
		}
		else {
			if(++i > board.spots.length && ++j > board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));		
		}
		setValidMovements();
	}
	
	public void diagonalTopRightMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j++;
			i--;
			for(; i >= 0 && j < board.spots.length ; i-- , j++) {
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
			}
		}
		else {
			if(--i >= 0 && j++ < board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
		}
		setValidMovements();	
	}
	
	public void diagonalBottomLeftMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j--;
			i++;
			for(; i < board.spots.length && j >= 0 ; i++ , j--) {
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
			}
		}
		else {
			if(++i < board.spots.length && --j >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
		}
		setValidMovements();	
		
	}
	
	public void diagonalTopLeftMovement() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		if(!(pieceType == PieceType.KING)) {
			j--;
			i--;
			for(; i >= 0 && j >= 0 ; i-- , j--) {
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
			}
		}
		else {
			if(--i >= 0 && j >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
		}
		setValidMovements();			
	}
		
}
