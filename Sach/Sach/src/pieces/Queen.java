package pieces;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.Move;

public class Queen extends Piece{

	public Queen(PlayerColour playerColour, PieceType pieceType, Board board, Spot spot) {
		super(playerColour, pieceType, board, true, 900, spot);
		
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
	
	@Override
	public List<Move> getCastleMovements() {
		System.out.println("shouldn't reach here, castling not allowed for this type.");
		return null;
		
	}
	
	@Override
	public boolean isPawnPromotionMove(int rowDestinaition) {
		throw new RuntimeException("Shouldn't reach here, not a pawn piece");
	}
	
}
