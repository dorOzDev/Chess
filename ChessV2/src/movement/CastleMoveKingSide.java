package movement;

import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;
import soldiers.Piece;

public class CastleMoveKingSide extends CastleMove {
	
	private static int ROOK_KING_SIDE_Y_AXIX = 7;
	
	private Piece rook;
	private Piece king;
	private Spot rookDestSpot;
	public CastleMoveKingSide(Spot sourceSpot, Spot destSpot, Piece king) {
		super(sourceSpot, destSpot, king);
		moveType = MoveType.CASTLE_MOVE_KING_SIDE;
		this.king = king;
		this.rook = board.getSpot(king.getX(), ROOK_KING_SIDE_Y_AXIX).getPiece(); // Get potential rook piece on board.
		this.rookDestSpot = board.getSpot(rook.getX(), ROOK_KING_SIDE_Y_AXIX - 2);
	}
	
	public Spot getRookDestSpot() {
		return rookDestSpot;
	}
	
	// Castle move only allowed when: 
	//1)both rook and king has not made any move yet.
	//2)No pieces between king and the rook.
	//3)"One may not castle out of, through, or into check."
	@Override
	public boolean isCastleAllowed() {
		// Check if obtained piece is indeed rook of the same colour type.
		if(rook == null || rook.getPieceType() != PieceType.ROOK || king.getPlayerCoulor() != rook.getPlayerCoulor()) {
			return false;
		}
		
		if(!king.isFirstMove()) {
			return false;
		}
		else if(!rook.isFirstMove()) {
			return false;
		}
		
		for(int i = king.getSpot().getY() + 1; i < rook.getSpot().getY(); i++) {
			if(board.getSpot(king.getSpot().getX(), i).isOccupied()) {
				return false;
			}		
		}
		
		if(piece.getPlayerCoulor() == PlayerColour.WHITE) {
			
			if(board.getInCheckStatusWhitePlayer()) // If king is in chess, castling is not allowed.
				return false;		
			if(!board.isCastleAllowedWhitePlayer(king, rook, moveType)) {
				return false;
			}
					
		}
		else if(piece.getPlayerCoulor() == PlayerColour.BLACK) {
			if(board.getInCheckStatusBlackPlayer()) {
				return false;
			}
			if(!board.isCastleAllowedBlackPlayer(king, rook, moveType)) {
				return false;
			}
		}
		
		return true;
	}
	

	

}
