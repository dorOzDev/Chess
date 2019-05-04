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
import movement.NoneAttackMove;

public class Pawn extends Piece {
	
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
	}
	
	@Override 
	public void setCandidateMovements() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		if(this.getPlayerCoulor() == PlayerColour.WHITE) {
			candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.spots[--currX][currY].getSpot(), this, MoveType.CANDIDATE_MOVE));
			if(isFirstMove)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.spots[--currX][currY].getSpot(), this, MoveType.CANDIDATE_MOVE));
			
			
			
		}

		else {
			
			candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.spots[++currX][currY].getSpot(), this, MoveType.CANDIDATE_MOVE));
			if(isFirstMove)
				candidateMovements.add(moveFactory.createMove(this.spot.getSpot(), board.spots[++currX][currY].getSpot(), this, MoveType.CANDIDATE_MOVE));
		}
		
		
		
	}
	
	@Override
	public void setValidMovements() {		
		while(!candidateMovements.isEmpty() && !candidateMovements.peek().getDestSpot().isOccupied()) {
				legalMovements.add(moveFactory.createMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot(), this, MoveType.NONE_ATTACK_MOVE));
				
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
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE));
					
				}
				
			}
			
			//Check diagnoal left attack move
			currY-= 2;
			if(currX >= 0 && currY >= 0) {
				potentialAttackedPiece = board.getSpot(currX, currY).getPiece();
				if(potentialAttackedPiece != null) {
					if(potentialAttackedPiece.getPlayerCoulor() != this.playerCoulor)
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE));
					
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
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE));
					
				}
			}
			
			//Check diagnoal left attack move
			currY-= 2;
			if(currX >= 0 && currY >= 0) {
				potentialAttackedPiece = board.getSpot(currX, currY).getPiece();
				if(potentialAttackedPiece != null) {
					if(potentialAttackedPiece.getPlayerCoulor() != this.playerCoulor)
						legalMovements.add(moveFactory.createMove(this.getSpot(), board.getSpot(currX, currY), this, MoveType.ATTACK_MOVE));
					
			
				}
		
			}
		}
	}
	
	
	@Override
	public List<Move> getCastleMovements() {
		System.out.println("shouldn't reach here, castling not allowed for this type.");
		return null;
		
	}
}

