package pieces;

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
	public Pawn(PlayerColour playerColour, PieceType pieceType, Spot spot, boolean isFirstMove) {
		super(playerColour, pieceType, isFirstMove, 100, spot);
		
	}

	@Override
	public void movement(final Board board) {
		candidateMovements.clear();
		legalMovements.clear();
		setCandidateMovements(board);
		setValidMovements(board);
		setPawnAttackMovements(board);
		setEnPassntAttackMovements(board);
	}
	
	private void setEnPassntAttackMovements(Board board) {
		lastMove = board.getLastMove();
		if(lastMove != null) {
		if(lastMove.isPawnJumpMove()) {
				if(Math.abs(this.getY() - lastMove.getDestSpot().getY()) == 1 && lastMove.getDestSpot().getX() == this.getX()) {
					if(this.getPlayerCoulor() == PlayerColour.WHITE) {
						legalMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(this.getX() - 1, lastMove.getPiece().getY()), this, MoveType.EN_PASSANT_MOVE, board.getPiece(lastMove.getDestSpot().getX(), lastMove.getDestSpot().getY()), board));
				}
					else {
						legalMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(this.getX() + 1, lastMove.getPiece().getY()), this, MoveType.EN_PASSANT_MOVE, board.getPiece(lastMove.getDestSpot().getX(), lastMove.getDestSpot().getY()), board));
						}
						
					}
				
			}	
		}
	}
	@Override 
	public void setCandidateMovements(Board board) {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		if(this.getPlayerCoulor() == PlayerColour.WHITE && isCordinatedInBoardsBounds(--currX, currY)) {		
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(currX, currY), this, MoveType.CANDIDATE_MOVE, null, board));
			if(isFirstMove && isCordinatedInBoardsBounds(--currX, currY)){
					candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(currX, currY), this, MoveType.CANDIDATE_MOVE, null, board));			
			}
		
		}

		else if(this.getPlayerCoulor() == PlayerColour.BLACK && isCordinatedInBoardsBounds(++currX, currY)){			
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(currX, currY), this, MoveType.CANDIDATE_MOVE, null, board));
			if(isFirstMove && isCordinatedInBoardsBounds(++currX, currY)) {
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.getSpot(currX, currY), this, MoveType.CANDIDATE_MOVE, null, board));
			}
		}
		
		
		
	}
	
	@Override
	public void setValidMovements(final Board board) {
		int countMoves = 0;
		while(!candidateMovements.isEmpty() && !candidateMovements.peek().getDestSpot().isOccupied()) {
			if(countMoves == 1) {
				legalMovements.add(moveFactory.createMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot(), this, MoveType.PAWN_JUMP, null, board));
			}
			else {
				legalMovements.add(moveFactory.createMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot(), this, MoveType.PROGRESS_MOVE, null, board));
				countMoves++;
			}
				
		}			
	}
		
	
	public void setPawnAttackMovements(final Board board) {
		
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
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE, null, board));
					
				}
				
			}
			
			//Check diagnoal left attack move
			currY-= 2;
			if(currX >= 0 && currY >= 0) {
				potentialAttackedPiece = board.getSpot(currX, currY).getPiece();
				if(potentialAttackedPiece != null) {
					if(potentialAttackedPiece.getPlayerCoulor() != this.playerCoulor)
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE, null, board));
					
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
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE, null, board));
					
				}
			}
			
			//Check diagnoal left attack move
			currY-= 2;
			if(currX >= 0 && currY >= 0) {
				potentialAttackedPiece = board.getSpot(currX, currY).getPiece();
				if(potentialAttackedPiece != null) {
					if(potentialAttackedPiece.getPlayerCoulor() != this.playerCoulor)
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE, null, board));
					
			
				}
		
			}
		}
	}
	
	
	@Override
	public List<Move> getCastleMovements(Board board) {
		System.out.println("shouldn't reach here, castling not allowed for this type.");
		return null;
		
	}

	@Override
	public boolean isPawnPromotionMove(int rowDestinaition) {
		
		if(rowDestinaition == 0 || rowDestinaition == 7) {
			return true;
		}
		return false;
	}


	
}

