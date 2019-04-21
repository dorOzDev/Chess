package soldiers;

import java.util.ArrayList;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;
import movement.CandidateMove;
import movement.NoneAttackMove;

public class Pawn extends Piece {
	
	boolean isFirstMove;
	public Pawn(PlayerColour playerColour, PieceType pieceType) {
		super(playerColour, pieceType);
		isFirstMove = true;
	}

	@Override
	public void setPiecePos(Spot spot) {
		this.spot = spot;

			
	}

	@Override
	public void movement() {
		candidateMovements.clear();
		legalMovements.clear();
		legalAttackMovements.clear();
		setCandidateMovements();
		setValidMovements();
	}
	
	@Override 
	public void setCandidateMovements() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		if(this.getPlayerCoulor() == PlayerColour.WHITE) {
			candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[--currX][currY].getSpot(), this));
			if(isFirstMove)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(),board.spots[--currX][currY].getSpot(), this));
			
		}

		else {
			
			candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[++currX][currY].getSpot(), this));
			if(isFirstMove)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[++currX][currY].getSpot(), this));
		}
		
		
	}
	
	@Override
	public void setValidMovements() {
		
		while(!candidateMovements.isEmpty()) {
			if(candidateMovements.peek().getDestSpot().isOccupied()) {
				candidateMovements.pop();
				
			}
			else {
				
				legalMovements.add(new NoneAttackMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot(), this));
			}
			
		}
		
	}
	
	
	//TODO add pawn attacking movements.
	public void setPawnAttackMovements() {
		
	}
}



