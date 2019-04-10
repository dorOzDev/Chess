package soldiers;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;

public class King extends Piece {

	public King(PlayerColour playerColour, PieceType pieceType) {
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
		
		if(++i < board.spots.length)
			candidateMovements.add(board.spots[i][j]);
		setValidMovements();
		
		i = currX;
		if(--i >= 0)
			candidateMovements.add(board.spots[i][j]);
		setValidMovements();
		
		i = currX;
		if(++j < board.spots.length)
			candidateMovements.add(board.spots[i][j]);
		setValidMovements();
		
		j = currY;
		if(--j >= 0)
			candidateMovements.add(board.spots[i][j]);
		setValidMovements();
		
		j = currY;
		if(++i > board.spots.length && ++j > board.spots.length)
			candidateMovements.add(board.spots[i][j]);
		setValidMovements();
		
		i = currX;
		j = currY;
		if(--i >= 0 && j++ < board.spots.length)
			candidateMovements.add(board.spots[i][j]);
		setValidMovements();
		
		i = currX;
		j = currY;
		if(++i < board.spots.length && --j >= 0)
			candidateMovements.add(board.spots[i][j]);
		setValidMovements();
		
		i = currX;
		j = currY;
		if(--i >= 0 && j >= 0)
			candidateMovements.add(board.spots[i][j]);
		setValidMovements();			
	}
	
}
