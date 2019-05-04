package soldiers;

import java.util.List;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.Move;

public class Rook extends Piece {
	
	

	public Rook(PlayerColour playerColour, PieceType pieceType,Board board) {
		super(playerColour, pieceType, board, true);
		
		
	}
	
	@Override
	public void setCandidateMovements() {	
		forwardMovement();
		backwardMovement();
		rightSideMovement();
		leftSideMovement();
	}
	
	@Override
	public List<Move> getCastleMovements() {
		System.out.println("shouldn't reach here, castling not allowed for this type.");
		return null;
		
	}
	
}
