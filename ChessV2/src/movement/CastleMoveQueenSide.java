package movement;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;
import soldiers.Piece;

public class CastleMoveQueenSide extends CastleMove {
	
	private static int ROOK_QUEEN_SIDE_Y_AXIX = 0;
	
	private Piece rook;
	private Piece king;
	private Spot rookDestspot;
	public CastleMoveQueenSide(Spot sourceSpot, Spot destSpot, Piece king) {
		super(sourceSpot, destSpot, king);		
		this.king = king;
		this.rook = board.getSpot(king.getX(), ROOK_QUEEN_SIDE_Y_AXIX).getPiece();
		this.rookDestspot = board.getSpot(rook.getX(), ROOK_QUEEN_SIDE_Y_AXIX + 3);
	}
	
	// Castle move only allowed when: 
	//1)both rook and king has not made any move yet.
	//2)No pieces between king and the rook.
	//3)"One may not castle out of, through, or into check."
	@Override
	public boolean isCastleAllowed() {
		//Checking if the picked piece is rook of the proper colour.
		if(rook == null || rook.getPieceType() != PieceType.ROOK|| king.getPlayerCoulor() != rook.getPlayerCoulor()) {
			return false;
		}
		if(king.isFirstMove() == false) {
			return false;
		}
		else if(rook.isFirstMove() == false) {
			return false;
		}
		
		for(int i = king.getSpot().getY() - 1; i > rook.getSpot().getY(); i--) {
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
