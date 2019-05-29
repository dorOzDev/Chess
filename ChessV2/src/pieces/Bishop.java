package pieces;

import java.util.List;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.Move;

public  class Bishop extends Piece {

	public Bishop(PlayerColour playerColour, PieceType pieceType, Spot spot, boolean isFirstMove) {
		super(playerColour, pieceType, isFirstMove, 300, spot);

	}
	@Override
	public void setCandidateMovements(Board board) {
		diagonalBottomLeftMovement(board);
		diagonalBottomRightMovement(board);
		diagonalTopLeftMovement(board);
		diagonalTopRightMovement(board);
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
