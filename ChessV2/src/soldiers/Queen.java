package soldiers;

import java.util.ArrayList;
import java.util.Comparator;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;

public class Queen extends Piece{

	public Queen(PlayerColour playerColour, PieceType pieceType, Board board) {
		super(playerColour, pieceType, board, true);
		
	}

	@Override
	public void setPiecePos(Spot spot) {
		this.spot = spot;

			
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
