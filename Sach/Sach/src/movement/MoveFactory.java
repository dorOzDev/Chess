package movement;

import enaum.MoveType;
import game.Spot;
import pieces.Piece;

//Abstract factory design pattern for the move abstract class.
public class MoveFactory {
	
	public  MoveFactory() {
		
	}
	
	public Move createMove(Spot sourceSpot, Spot destSpot, Piece movedPiece, MoveType moveType, Piece enPassntAttacedPiece) {
		
		if(moveType == MoveType.ATTACK_MOVE) {
			return new AttackMove(sourceSpot, destSpot, movedPiece, destSpot.getPiece());
		}
		else if(moveType == MoveType.EN_PASSANT_MOVE) {
			return new EnPassntAttack(sourceSpot, destSpot, movedPiece, enPassntAttacedPiece);
		}
		else if(moveType == MoveType.PROGRESS_MOVE) {
			return new ProgressMove(sourceSpot, destSpot, movedPiece);
		}
		
		else if(moveType == MoveType.CASTLE_MOVE_KING_SIDE) {
			return new CastleMoveKingSide(sourceSpot, destSpot, movedPiece);
		}
		
		else if (moveType == MoveType.CASTLE_MOVE_QUEEN_SIDE){
			
			return new CastleMoveQueenSide(sourceSpot, destSpot, movedPiece);
		}
		else if(moveType == MoveType.PAWN_JUMP) {
			return new PawnJump(sourceSpot, destSpot, movedPiece);
		}
		else {
			return new CandidateMove(sourceSpot, destSpot, movedPiece);
		}
	
	}


}
