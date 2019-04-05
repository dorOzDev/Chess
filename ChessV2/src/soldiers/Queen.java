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
		candidateMovements.clear();
		
		i = currX;
		i--;
		for(; i >= 0; i--)
			candidateMovements.add(board.spots[i][currY].getSpot());
		setValidMovements();
		candidateMovements.clear();
		
		i = currY;
		i++;
		for(; i < board.spots.length; i++)
			candidateMovements.add(board.spots[currX][i].getSpot());
		setValidMovements();
		candidateMovements.clear();
		
		i = currY;
		i--;
		for(; i >= 0; i--)
			candidateMovements.add(board.spots[currX][i].getSpot());
		setValidMovements();
		candidateMovements.clear();
		
		int j = currY;
		i= currX;
		j++;
		i++;
		for(; i < board.spots.length && j < board.spots.length ; i++ , j++) {
			candidateMovements.add(board.spots[i][j]);
		}
		setValidMovements();
		candidateMovements.clear();
		
		j = currY;
		i= currX;
		j++;
		i--;
		for(; i >= 0 && j < board.spots.length ; i-- , j++) {
			candidateMovements.add(board.spots[i][j]);
		}
		setValidMovements();
		candidateMovements.clear();
		
		j = currY;
		i= currX;
		j--;
		i++;
		for(; i < board.spots.length && j >= 0 ; i++ , j--) {
			candidateMovements.add(board.spots[i][j]);
		}
		setValidMovements();
		candidateMovements.clear();
		
		j = currY;
		i= currX;
		j--;
		i--;
		for(; i >= 0 && j >= 0 ; i-- , j--) {
			candidateMovements.add(board.spots[i][j]);
		}
		setValidMovements();
		candidateMovements.clear();
	}
	
	@Override
	public void setValidMovements() {
		int i = 0;
		if(!candidateMovements.isEmpty()) {
		while(!candidateMovements.get(i).getSpot().isOccupied()) {
			validMovements.add(candidateMovements.get(i).getSpot());
			i++;
			}
		}
	}
}
