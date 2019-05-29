package movement;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import pieces.Piece;

public class CastleMoveQueenSide extends CastleMove {
	
	private static int ROOK_QUEEN_SIDE_Y_AXIX = 0;
	
	private Piece rook;
	private Piece king;
	private Spot rookDestspot;
	private Spot rookSourceSpot;

	public CastleMoveQueenSide(Spot sourceSpot, Spot destSpot, Piece king, Board board) {
		super(sourceSpot, destSpot, king, board);		
		this.king = king;
		this.rook = board.getSpot(king.getX(), ROOK_QUEEN_SIDE_Y_AXIX).getPiece();
		this.rookDestspot = board.getSpot(king.getX(), ROOK_QUEEN_SIDE_Y_AXIX + 3);
		rookSourceSpot = board.getSpot(piece.getX(),ROOK_QUEEN_SIDE_Y_AXIX );
	}

	@Override
	public Spot getRookDestSpot() {
		return rookDestspot;
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
