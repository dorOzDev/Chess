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
		legalMovements.clear();
		legalAttackMovements.clear();
		setCandidateMovements();

		
	}
	
	@Override
	public void setCandidateMovements() {
		diagonalBottomLeftMovement();
		diagonalBottomRightMovement();
		diagonalTopLeftMovement();
		diagonalTopRightMovement();
	}
	
	

}
