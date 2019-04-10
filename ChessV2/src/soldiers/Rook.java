package soldiers;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;

public class Rook extends Piece {

	public Rook(PlayerColour playerColour, PieceType pieceType) {
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
		

	}
	
	
	

}
