package pieces;

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
	
	public King(PlayerColour playerColour, PieceType pieceType, Spot spot, boolean isFirstMove) {
		super(playerColour, pieceType, isFirstMove, 10000, spot);
		castleMovements = new ArrayList<Move>();
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
		castleMovements.clear();
		if(!isCordinatedInBoardsBounds(this.getX(), this.getY() + + CASTLE_MOVE_OFFSET) || !(isCordinatedInBoardsBounds(this.getX(), this.getY() - CASTLE_MOVE_OFFSET))) {
			return castleMovements;
		}
		castleMoveKingSide = moveFactory.createMove(this.getSpot(), board.getSpot(this.getX(), this.getY() + CASTLE_MOVE_OFFSET), this, MoveType.CASTLE_MOVE_KING_SIDE, null, board);
		castleMoveQueenSide = moveFactory.createMove(this.getSpot(), board.getSpot(this.getX(), this.getY() - CASTLE_MOVE_OFFSET), this, MoveType.CASTLE_MOVE_QUEEN_SIDE, null, board);
		if(isCastleKingSideAllowed(castleMoveKingSide, board)) {
			castleMovements.add(castleMoveKingSide);
		}
		if(isCastleQueenSideAllowed(castleMoveQueenSide, board)) {		
			castleMovements.add(castleMoveQueenSide);
		}
		
		return castleMovements;
	}
	
	
	// Castle move only allowed when: 
	//1)both rook and king has not made any move yet.
	//2)No pieces between king and the rook.
	//3)"One may not castle out of, through, or into check."
	public boolean isCastleKingSideAllowed(Move move, Board board) {
		//Check for king first move
		if(!this.isFirstMove()) {
			return false;
		}
		
		
		
		// Check if obtained piece is indeed rook of the same colour type.
		if(move.getRook() == null || move.getRook().getPieceType() != PieceType.ROOK || this.getPlayerCoulor() != move.getRook().getPlayerCoulor()) {
			return false;
		}
		
		//Check for rook first move
		if(!move.getRook().isFirstMove()) {
			return false;
		}
		
		for(int i = this.getSpot().getY() + 1; i < move.getRook().getSpot().getY(); i++) {
			if(board.getSpot(this.getSpot().getX(), i).isOccupied()) {
				return false;
			}		
		}
		
		
		if(this.getPlayerCoulor() == PlayerColour.WHITE) {
			
			// If king is in chess, castling is not allowed.
			if(board.isInCheck(PlayerColour.WHITE)) 
				return false;
			// Checking if king lands on chess spot by the end of the rook, if true castle is not allowed.
			if(board.isCastleAllowed(move, PlayerColour.WHITE)) {
				
				return false;
			}
					
		}
		else if(this.getPlayerCoulor() == PlayerColour.BLACK) {
			if(board.isInCheck(PlayerColour.BLACK)) {
				return false;
			}
			if(board.isCastleAllowed(move, PlayerColour.BLACK)) {
				
				return false;
			}
		}
		
		return true;
	}
	
	
	
	// Castle move only allowed when: 
	//1)both rook and king has not made any move yet.
	//2)No pieces between king and the rook.
	//3)"One may not castle out of, through, or into check."
	public boolean isCastleQueenSideAllowed(Move move, Board board) {
		//Check for king first move
		if(!this.isFirstMove()) {
			return false;
		}
			
		
		// Check if obtained piece is indeed rook of the same colour type.
		if(move.getRook() == null || move.getRook().getPieceType() != PieceType.ROOK || this.getPlayerCoulor() != move.getRook().getPlayerCoulor()) {
			return false;
		}
		
		//Check for rook first move
		if(!move.getRook().isFirstMove()) {
			return false;
		}
		
		for(int i = this.getSpot().getY() - 1; i > move.getRook().getSpot().getY(); i--) {
			if(board.getSpot(this.getSpot().getX(), i).isOccupied()) {
				return false;
			}		
		}
		
		
		if(this.getPlayerCoulor() == PlayerColour.WHITE) {
			
			// If king is in chess, castling is not allowed.
			if(board.isInCheck(PlayerColour.WHITE)) 
				return false;
			// Checking if king lands on chess spot by the end of the rook, if true castle is not allowed.
			if(board.isCastleAllowed(move, PlayerColour.WHITE)) {
				
				return false;
			}
					
		}
		else if(this.getPlayerCoulor() == PlayerColour.BLACK) {
			if(board.isInCheck(PlayerColour.BLACK)) {
				return false;
			}
			if(board.isCastleAllowed(move, PlayerColour.BLACK)) {
				
				return false;
			}
		}
		
		return true;
	}
	@Override
	public boolean isPawnPromotionMove(int rowDestinaition) {
		return false;
	}
	
}
