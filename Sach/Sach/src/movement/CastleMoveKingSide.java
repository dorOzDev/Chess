package movement;

import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;
import pieces.Piece;

public class CastleMoveKingSide extends CastleMove {
	
	private static int ROOK_KING_SIDE_Y_AXIX = 7;
	
	private Piece rook;
	private Piece king;
	private Spot rookDestSpot;
	private Spot rookSourceSpot;

	public CastleMoveKingSide(Spot sourceSpot, Spot destSpot, Piece king) {
		super(sourceSpot, destSpot, king);
		moveType = MoveType.CASTLE_MOVE_KING_SIDE;
		this.king = king;
		this.rook = board.getSpot(king.getX(), ROOK_KING_SIDE_Y_AXIX).getPiece(); // Get potential rook piece on board.
		this.rookDestSpot = board.getSpot(piece.getX(), ROOK_KING_SIDE_Y_AXIX - 2);
		this.rookSourceSpot = board.getSpot(piece.getX(), ROOK_KING_SIDE_Y_AXIX);
	}
	
	@Override
	public Spot getRookDestSpot() {
		return rookDestSpot;
	}
	


	@Override
	public Spot getRookSourceSpot() {
		return rookSourceSpot;
	}

	@Override
	public Piece getRook() {
		return this.rook;
	}
}
