package pieces;

import java.util.List;

import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.AttackMove;
import movement.CandidateMove;
import movement.Move;
import movement.ProgressMove;

public class Knight extends Piece  {

	public Knight(PlayerColour playerColour, PieceType pieceType, Spot spot, boolean isFirstMove) {
		super(playerColour, pieceType, isFirstMove, 300, spot);
	}


	@Override
	public void setCandidateMovements(final Board board) {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		i += 2;
		if(isCordinatedInBoardsBounds(i, j)) {
			
			if(isCordinatedInBoardsBounds(i, ++j))
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
			
			j -= 2;
			if(isCordinatedInBoardsBounds(i, j))
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
		}
		
		i = currX;
		j = currY;
		if(++i < board.NUM_ROWS) {
			if(i < board.NUM_ROWS) {
				j += 2;
				if(j < board.NUM_COLS)
					candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
				
				j -= 4;
				if(j >= 0)
					candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
			}
		}
		
		i = currX;
		j = currY;		
		i -= 2;
		if(i >= 0) {
			
			if(++j < board.NUM_COLS)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
			
			j -= 2;
			if(j >= 0)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
		}
		
		i = currX;
		j = currY;	
		if(--i >= 0 ) {
			if(i < board.NUM_ROWS) {
				j += 2;
				if(j < board.NUM_COLS)
					candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
				
				j -= 4;
				if(j >= 0)
					candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(i, j), this, MoveType.CANDIDATE_MOVE, null, board));
			}
		}
		setValidMovements(board);

	}
	
	@Override
	public void setValidMovements(final Board board) {
		
		//Adding all legal none attack movements.
		while(candidateMovements.iterator().hasNext()) {
			if(!candidateMovements.peek().getDestSpot().isOccupied()) {
				legalMovements.add(moveFactory.createMove(this.getSpot(),candidateMovements.pop().getDestSpot(), this, MoveType.PROGRESS_MOVE, null, board));
			}
		//Adding all legal attack movements.
			else { 					
				if(candidateMovements.peek().getDestSpot().getPiece().getPlayerCoulor() != this.playerCoulor) {
					legalMovements.add(moveFactory.createMove(this.getSpot(), candidateMovements.peek().getDestSpot(), this, MoveType.ATTACK_MOVE, null, board));
				}
				candidateMovements.pop();		
			}
		
		}
		candidateMovements.clear();
	}
	
	@Override
	public List<Move> getCastleMovements(Board board) {
		System.out.println("shouldn't reach here, castling not allowed for this type.");
		return null;
		
	}
	
	@Override
	public boolean isPawnPromotionMove(int rowDestinaition) {
		return false;
	}









	
	
}



