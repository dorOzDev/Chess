package movement;

import enaum.MoveType;
import enaum.PieceType;
import game.Board;
import game.Spot;
import pieces.Piece;

public class AttackMove extends Move {
	
	private Piece attackedPiece;
	public AttackMove(Spot sourceSpot, Spot destSpot, final Piece piece, final Piece attackedPiece, Board board) {
		super(sourceSpot, destSpot, piece, MoveType.ATTACK_MOVE, board);
		this.attackedPiece = attackedPiece;
		
	}

	@Override
	public boolean isAttackMove() {
		return true;
	}
	
	@Override
	public Piece getAttackedPiece() {
		return attackedPiece;
	}

	@Override
	public boolean isCastleMove() {
		
		return false;
	}
	
	@Override
	public boolean isCastleAllowed() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}

	@Override
	public Spot getRookDestSpot() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}

	@Override
	public Spot getRookSourceSpot() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}

	@Override
	public Piece getRook() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}

	@Override
	public boolean isPawnJumpMove() {
		return false;
	}

	@Override
	public boolean isEnPassntMove() {
		
		return false;
	}
	
	

}
