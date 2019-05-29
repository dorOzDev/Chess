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

	public Queen(PlayerColour playerColour, PieceType pieceType, Spot spot, boolean isFirstMove) {
		super(playerColour, pieceType, isFirstMove, 900, spot);
		
	}


	@Override
	public void setCandidateMovements(Board board) {
		forwardMovement(board);		
		backwardMovement(board);
		rightSideMovement(board);
		leftSideMovement(board);
		diagonalBottomRightMovement(board);
		diagonalTopRightMovement(board);
		diagonalBottomLeftMovement(board);
		diagonalTopLeftMovement(board);
		
	}
	
	@Override
	public List<Move> getCastleMovements(Board board) {
		System.out.println("shouldn't reach here, castling not allowed for this type.");
		return null;
		
	}
	
	@Override
	public boolean isPawnPromotionMove(int rowDestinaition) {
		return false;
	}
	
}
