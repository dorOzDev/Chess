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
	private static int ROOK_KING_SIDE_Y_AXIX = 7;
	private static int ROOK_QUEEN_SIDE_Y_AXIX = 0;
	
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
		if(isCastleKingSideAllowed()) {
			castleMoveKingSide = moveFactory.createMove(this.getSpot(), board.getSpot(this.getX(), this.getY() + CASTLE_MOVE_OFFSET), this, MoveType.CASTLE_MOVE_KING_SIDE);
			castleMovements.add(castleMoveKingSide);
		}
		if(isCastleQueenSideAllowed()) {
			castleMoveQueenSide = moveFactory.createMove(this.getSpot(), board.getSpot(this.getX(), this.getY() - CASTLE_MOVE_OFFSET), this, MoveType.CASTLE_MOVE_QUEEN_SIDE);
			castleMovements.add(castleMoveQueenSide);
		}
		
		return castleMovements;
	}
	
	
	// Castle move only allowed when: 
	//1)both rook and king has not made any move yet.
	//2)No pieces between king and the rook.
	//3)"One may not castle out of, through, or into check."
	private boolean isCastleKingSideAllowed() {
		//Check for king first move
		if(!this.isFirstMove()) {
			return false;
		}
		
		
		Piece potentialRook = board.getSpot(this.getX(), ROOK_KING_SIDE_Y_AXIX).getPiece();
		// Check if obtained piece is indeed rook of the same colour type.
		if(potentialRook == null || potentialRook.getPieceType() != PieceType.ROOK || this.getPlayerCoulor() != potentialRook.getPlayerCoulor()) {
			return false;
		}
		
		//Check for rook first move
		if(!potentialRook.isFirstMove()) {
			return false;
		}
		
		for(int i = this.getSpot().getY() + 1; i < potentialRook.getSpot().getY(); i++) {
			if(board.getSpot(this.getSpot().getX(), i).isOccupied()) {
				return false;
			}		
		}
		
		
		if(this.getPlayerCoulor() == PlayerColour.WHITE) {
			
			// If king is in chess, castling is not allowed.
			if(board.getInCheckStatusWhitePlayer()) 
				return false;
			// Checking if king lands on chess spot by the end of the rook, if true castle is not allowed.
			if(board.isCastleAllowedWhitePlayer(this, potentialRook, MoveType.CASTLE_MOVE_KING_SIDE)) {
				
				return false;
			}
					
		}
		else if(this.getPlayerCoulor() == PlayerColour.BLACK) {
			if(board.getInCheckStatusBlackPlayer()) {
				return false;
			}
			if(board.isCastleAllowedBlackPlayer(this, potentialRook, MoveType.CASTLE_MOVE_KING_SIDE)) {
				
				return false;
			}
		}
		
		return true;
	}
	
	
	
	// Castle move only allowed when: 
	//1)both rook and king has not made any move yet.
	//2)No pieces between king and the rook.
	//3)"One may not castle out of, through, or into check."
	private boolean isCastleQueenSideAllowed() {
		//Check for king first move
		if(!this.isFirstMove()) {
			return false;
		}
			
		Piece potentialRook = board.getSpot(this.getX(), ROOK_QUEEN_SIDE_Y_AXIX).getPiece();
		// Check if obtained piece is indeed rook of the same colour type.
		if(potentialRook == null || potentialRook.getPieceType() != PieceType.ROOK || this.getPlayerCoulor() != potentialRook.getPlayerCoulor()) {
			return false;
		}
		
		//Check for rook first move
		if(!potentialRook.isFirstMove()) {
			return false;
		}
		
		for(int i = this.getSpot().getY() - 1; i > potentialRook.getSpot().getY(); i--) {
			if(board.getSpot(this.getSpot().getX(), i).isOccupied()) {
				return false;
			}		
		}
		
		
		if(this.getPlayerCoulor() == PlayerColour.WHITE) {
			
			// If king is in chess, castling is not allowed.
			if(board.getInCheckStatusWhitePlayer()) 
				return false;
			// Checking if king lands on chess spot by the end of the rook, if true castle is not allowed.
			if(board.isCastleAllowedWhitePlayer(this, potentialRook, MoveType.CASTLE_MOVE_QUEEN_SIDE)) {
				
				return false;
			}
					
		}
		else if(this.getPlayerCoulor() == PlayerColour.BLACK) {
			if(board.getInCheckStatusBlackPlayer()) {
				return false;
			}
			if(board.isCastleAllowedBlackPlayer(this, potentialRook, MoveType.CASTLE_MOVE_QUEEN_SIDE)) {
				
				return false;
			}
		}
		
		return true;
	}
	@Override
	public boolean isPawnPromotionMove() {
		throw new RuntimeException("Shouldn't reach here, not a pawn piece");
	}
	
}
