package soldiers;

import enaum.PlayerColour;
import game.Spot;

public class Knight extends Piece  {

	public Knight(PlayerColour playerCoulor) {
		this.setPlayerCoulor(playerCoulor);
	}

	@Override
	public void setStartPos(Spot spot) {
		this.spot = spot;
		spot.setOccupied(true);
			
	}

	@Override
	public void movement() {
		candidateMovements.clear();
		validMovements.clear();
		setCandidateMovements();
		
	}
	
	@Override
	public void setCandidateMovements() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		i += 2;
		if(i < board.spots.length) {
			
			if(++j < board.spots.length)
				candidateMovements.add(board.spots[i][j]);
			
			j -= 2;
			if(j >= 0)
				candidateMovements.add(board.spots[i][j]);
		}
		i = currX;
		j = currY;
		

		if(++i < board.spots.length) {
			if(i < board.spots.length) {
				j += 2;
				if(j < board.spots.length)
					candidateMovements.add(board.spots[i][j]);
				
				j -= 4;
				if(j >= 0)
					candidateMovements.add(board.spots[i][j]);
			}
		}
		i = currX;
		j = currY;
		
		i -= 2;
		if(i >= 0) {
			
			if(++j < board.spots.length)
				candidateMovements.add(board.spots[i][j]);
			
			j -= 2;
			if(j >= 0)
				candidateMovements.add(board.spots[i][j]);
		}
		i = currX;
		j = currY;
		
		if(--i >= 0 ) {
			if(i < board.spots.length) {
				j += 2;
				if(j < board.spots.length)
					candidateMovements.add(board.spots[i][j]);
				
				j -= 4;
				if(j >= 0)
					candidateMovements.add(board.spots[i][j]);
			}
		}
		
		setValidMovements();

	}
	
}


