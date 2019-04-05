package soldiers;

import java.util.ArrayList;

import enaum.PlayerColour;
import game.Spot;

public class Pawn extends Piece {
	
	boolean isFirstMove;
	public Pawn(PlayerColour playerColour) {
		this.setPlayerCoulor(playerColour);
		isFirstMove = true;
	}

	@Override
	public void setStartPos(Spot spot) {
		this.spot = spot;
		spot.setOccupied(true);
			
	}

	@Override
	public void movement() {
		setCandidateMovements();
		setValidMovements();
	}
	
	@Override 
	public void setCandidateMovements() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		if(this.getPlayerCoulor() == PlayerColour.WHITE) {
			candidateMovements.add(board.spots[--currX][currY]);
			if(isFirstMove)
				candidateMovements.add(board.spots[--currX][currY]);
		}
		
		else {
			candidateMovements.add(board.spots[++currX][currY]);
			if(isFirstMove)
				candidateMovements.add(board.spots[++currX][currY]);
		}
	}
	
	@Override
	public void setValidMovements() {
		for(int i = 0; i < candidateMovements.size(); i++) {
			if(candidateMovements.get(i).isOccupied()) {
				candidateMovements.remove(i);			
			}
			else {
				validMovements.add(candidateMovements.get(i));
			}
		}
		
	}
}



