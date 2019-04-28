package soldiers;

import java.util.ArrayList;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.AttackMove;
import movement.CandidateMove;
import movement.NoneAttackMove;

public class Pawn extends Piece {
	
	public Pawn(PlayerColour playerColour, PieceType pieceType, Board board) {
		super(playerColour, pieceType, board, true);
		
	}

	@Override
	public void setPiecePos(Spot spot) {
		this.spot = spot;

			
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
			candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[--currX][currY].getSpot(), this));
			if(isFirstMove)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(),board.spots[--currX][currY].getSpot(), this));
			
		}

		else {
			
			candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[++currX][currY].getSpot(), this));
			if(isFirstMove)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[++currX][currY].getSpot(), this));
		}
		
		
	}
	
	@Override
	public void setValidMovements() {
		
		while(!candidateMovements.isEmpty()) {
			if(candidateMovements.peek().getDestSpot().isOccupied()) {
				candidateMovements.pop();
				
			}
			else {
				
				legalMovements.add(new NoneAttackMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot(), this));
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
						legalMovements.add(new AttackMove(this.getSpot(), board.getSpot(currX, currY), this, potentialAttackedPiece));
				}
				
			}
			
			//Check diagnoal left attack move
			currY-= 2;
			if(currX >= 0 && currY >= 0) {
				potentialAttackedPiece = board.getSpot(currX, currY).getPiece();
				if(potentialAttackedPiece != null) {
					if(potentialAttackedPiece.getPlayerCoulor() != this.playerCoulor)
						legalMovements.add(new AttackMove(this.getSpot(), board.getSpot(currX, currY), this, potentialAttackedPiece));
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
						legalMovements.add(new AttackMove(this.getSpot(), board.getSpot(currX, currY), this, potentialAttackedPiece));
				}
			}
			
			//Check diagnoal left attack move
			currY-= 2;
			if(currX >= 0 && currY >= 0) {
				potentialAttackedPiece = board.getSpot(currX, currY).getPiece();
				if(potentialAttackedPiece != null) {
					if(potentialAttackedPiece.getPlayerCoulor() != this.playerCoulor)
						legalMovements.add(new AttackMove(this.getSpot(), board.getSpot(currX, currY), this, potentialAttackedPiece));
			
				}
		
			}
		}
	}

}

