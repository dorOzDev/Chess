package soldiers;

import java.util.ArrayList;
import java.util.Comparator;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;

public class Queen extends Piece{

	public Queen(PlayerColour playerColour, PieceType pieceType) {
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
