package soldiers;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;

public  class Bishop extends Piece {

	public Bishop(PlayerColour playerColour, PieceType pieceType, Board board) {
		super(playerColour, pieceType, board, true);

	}

	@Override
	public void setPiecePos(Spot spot) {
		this.spot = spot;
			
	}
/*
	@Override
	public void movement() {
		candidateMovements.clear();
		hmLegalMoves.clear();
		//legalMovements.clear();
		//legalAttackMovements.clear();
		setCandidateMovements();

		
	}
	*/
	@Override
	public void setCandidateMovements() {
		diagonalBottomLeftMovement();
		diagonalBottomRightMovement();
		diagonalTopLeftMovement();
		diagonalTopRightMovement();
	}
	
	

}
