package soldiers;

import java.util.ArrayList;
import java.util.Comparator;

import enaum.PlayerColour;
import game.Spot;

public class Queen extends Piece{

	public Queen(PlayerColour playerCoulor) {
		this.setPlayerCoulor(playerCoulor);
		validMovements = new ArrayList();
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
		i++;
		for( ; i < board.spots.length; i++)
			candidateMovements.add(board.spots[i][currY].getSpot());
		setValidMovements();
		
		
		i = currX;
		i--;
		for(; i >= 0; i--)
			candidateMovements.add(board.spots[i][currY].getSpot());
		setValidMovements();
		
		
		i = currY;
		i++;
		for(; i < board.spots.length; i++)
			candidateMovements.add(board.spots[currX][i].getSpot());
		setValidMovements();
		
		
		i = currY;
		i--;
		for(; i >= 0; i--)
			candidateMovements.add(board.spots[currX][i].getSpot());
		setValidMovements();
		
		
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
