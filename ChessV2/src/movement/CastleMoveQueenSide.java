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

	@Override
	public Spot getRookDestSpot() {
		return rookDestspot;
	}

	@Override
	public Spot getRookSourceSpot() {
		return rook.getSpot();
	}

	@Override
	public Piece getRook() {
		return this.rook;
	}
	

	

}
