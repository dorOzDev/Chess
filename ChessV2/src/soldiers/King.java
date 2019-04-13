package soldiers;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;

public class King extends Piece {
	
	private boolean isFirstMove;
	public King(PlayerColour playerColour, PieceType pieceType) {
		super(playerColour, pieceType);
		isFirstMove = true;
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
		forwardMovement();
		backwardMovement();
		rightSideMovement();
		leftSideMovement();
		diagonalBottomRightMovement();
		diagonalTopRightMovement();
		diagonalBottomLeftMovement();
		diagonalTopLeftMovement();
	}
	
}
