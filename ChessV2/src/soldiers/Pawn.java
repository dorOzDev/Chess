package soldiers;

import java.util.ArrayList;
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

public class Pawn extends Piece {
	
	private Move lastMove;
	public Pawn(PlayerColour playerColour, PieceType pieceType, Board board) {
		super(playerColour, pieceType, board, true);
		
	}

	@Override
	public void movement() {
		candidateMovements.clear();
		legalMovements.clear();
		setCandidateMovements();
		setValidMovements();
		setPawnAttackMovements();
		setEnPassntAttackMovements();
	}
	
	private void setEnPassntAttackMovements() {
		lastMove = board.getLastMove();
		if(lastMove != null) {
		if(lastMove.isPawnJumpMove()) {
			
			if(this.getPlayerCoulor() != lastMove.getPiece().getPlayerCoulor()) {
				if(Math.abs(this.getY() - lastMove.getPiece().getY()) == 1 && lastMove.getPiece().getX() == this.getX()) {
					if(this.getPlayerCoulor() == PlayerColour.WHITE) {
						legalMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(this.getX() - 1, lastMove.getPiece().getY()), this, MoveType.EN_PASSANT_MOVE, lastMove.getPiece()));
				}
					else {
						legalMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(this.getX() + 1, lastMove.getPiece().getY()), this, MoveType.EN_PASSANT_MOVE, lastMove.getPiece()));
						}
						
					}
				}
			}
		}
	}
	@Override 
	public void setCandidateMovements() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		if(this.getPlayerCoulor() == PlayerColour.WHITE) {
			candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.spots[--currX][currY].getSpot(), this, MoveType.CANDIDATE_MOVE, null));
			if(isFirstMove)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.spots[--currX][currY].getSpot(), this, MoveType.CANDIDATE_MOVE, null));
		
		}

		else {
			
			candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.spots[++currX][currY].getSpot(), this, MoveType.CANDIDATE_MOVE, null));
			if(isFirstMove)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.spots[++currX][currY].getSpot(), this, MoveType.CANDIDATE_MOVE, null));
		}
		
		
		
	}
	
	@Override
	public void setValidMovements() {
		int countMoves = 0;
		while(!candidateMovements.isEmpty() && !candidateMovements.peek().getDestSpot().isOccupied()) {
			if(countMoves == 1) {
				legalMovements.add(moveFactory.createMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot(), this, MoveType.PAWN_JUMP, null));
			}
			else {
				legalMovements.add(moveFactory.createMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot(), this, MoveType.NONE_ATTACK_MOVE, null));
				countMoves++;
			}
				
		}			
	}
		
	
	public void setPawnAttackMovements() {
		
		int currX = this.getSpot().getX();
		int currY = this.getSpot().getY();
		Piece potentialAttackedPiece;
		
		// Black player pawn attack movement is in diffrent direction to White's player pawn attack move.
		if(this.playerCoulor == PlayerColour.WHITE) {
			
			//Check diagonal right attack move
			--currX;
			++currY;
			if(currX >= 0 && currY < Board.NUM_COLS) {
				
				potentialAttackedPiece = board.getSpot(currX, currY).getPiece();			
				if(potentialAttackedPiece != null) {
					if(potentialAttackedPiece.getPlayerCoulor() != this.playerCoulor)
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE, null));
					
				}
				
			}
			
			//Check diagnoal left attack move
			currY-= 2;
			if(currX >= 0 && currY >= 0) {
				potentialAttackedPiece = board.getSpot(currX, currY).getPiece();
				if(potentialAttackedPiece != null) {
					if(potentialAttackedPiece.getPlayerCoulor() != this.playerCoulor)
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE, null));
					
				}
			}
			
		}
		
		else {
			//Check diagonal right attack move
			++currX;
			++currY;
			if(currX >= 0 && currY < Board.NUM_COLS) {
				potentialAttackedPiece = board.getSpot(currX, currY).getPiece();
				if(potentialAttackedPiece != null) {
					if(potentialAttackedPiece.getPlayerCoulor() != this.playerCoulor)
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE, null));
					
				}
			}
			
			//Check diagnoal left attack move
			currY-= 2;
			if(currX >= 0 && currY >= 0) {
				potentialAttackedPiece = board.getSpot(currX, currY).getPiece();
				if(potentialAttackedPiece != null) {
					if(potentialAttackedPiece.getPlayerCoulor() != this.playerCoulor)
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE, null));
					
			
				}
		
			}
		}
	}
	
	
	@Override
	public List<Move> getCastleMovements() {
		System.out.println("shouldn't reach here, castling not allowed for this type.");
		return null;
		
	}

	@Override
	public boolean isPawnPromotionMove() {
		
		if(this.getX() == 0 || this.getX() == 7) {
			return true;
		}
		return false;
	}
	
	
}

