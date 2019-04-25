package soldiers;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;

public class Rook extends Piece {
	
	

	public Rook(PlayerColour playerColour, PieceType pieceType,Board board) {
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
	}
	
	
	

}
