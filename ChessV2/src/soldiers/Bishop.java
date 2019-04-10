package soldiers;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;

public  class Bishop extends Piece {

	public Bishop(PlayerColour playerColour, PieceType pieceType) {
		super(playerColour, pieceType);

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
		i= currX;
		j++;
		i++;
		for(; i < board.spots.length && j < board.spots.length ; i++ , j++) {
			candidateMovements.add(board.spots[i][j]);
		}
		setValidMovements();
		
		
		j = currY;
		i= currX;
		j++;
		i--;
		for(; i >= 0 && j < board.spots.length ; i-- , j++) {
			candidateMovements.add(board.spots[i][j]);
		}
		setValidMovements();
		
		
		j = currY;
		i= currX;
		j--;
		i++;
		for(; i < board.spots.length && j >= 0 ; i++ , j--) {
			candidateMovements.add(board.spots[i][j]);
		}
		setValidMovements();
		
		
		j = currY;
		i= currX;
		j--;
		i--;
		for(; i >= 0 && j >= 0 ; i-- , j--) {
			candidateMovements.add(board.spots[i][j]);
		}
		setValidMovements();

	}
	
	

}
