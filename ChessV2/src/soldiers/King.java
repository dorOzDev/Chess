package soldiers;

import java.util.ArrayList;
import java.util.List;

import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.CastleMoveKingSide;
import movement.CastleMoveQueenSide;
import movement.Move;

public class King extends Piece {
	
	private static final int CASTLE_MOVE_OFFSET = 2;
	
	List<Move>castleMovements;
	Move castleMoveKingSide;
	Move castleMoveQueenSide;
	
	public King(PlayerColour playerColour, PieceType pieceType, Board board) {
		super(playerColour, pieceType, board, true);
		castleMovements = new ArrayList<Move>();
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
		castleMovements.clear();
		castleMoveKingSide = moveFactory.createMove(this.getSpot(), board.getSpot(this.getX(), this.getY() + CASTLE_MOVE_OFFSET), this, MoveType.CASTLE_MOVE_KING_SIDE);
		castleMoveQueenSide = moveFactory.createMove(this.getSpot(), board.getSpot(this.getX(), this.getY() - CASTLE_MOVE_OFFSET), this, MoveType.CASTLE_MOVE_KING_SIDE);
		if(castleMoveKingSide.isCastleAllowed()) {
			castleMovements.add(castleMoveKingSide);
		}
		if(castleMoveQueenSide.isCastleAllowed()) {
			castleMovements.add(castleMoveQueenSide);
		}

		return castleMovements;
	}
	
	
	
}
