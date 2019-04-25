package soldiers;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;
import movement.AttackMove;
import movement.CandidateMove;
import movement.NoneAttackMove;

public class Knight extends Piece  {

	public Knight(PlayerColour playerColour, PieceType pieceType, Board board) {
		super(playerColour, pieceType, board, true);
	}

	@Override
	public void setPiecePos(Spot spot) {
		this.spot = spot;

			
	}

	@Override
	public void setCandidateMovements() {
		int currX = this.spot.getX();
		int currY = this.spot.getY();
		
		int i = currX;
		int j = currY;
		
		i += 2;
		if(i < board.spots.length) {
			
			if(++j < board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
			
			j -= 2;
			if(j >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
		}
		
		i = currX;
		j = currY;
		if(++i < board.spots.length) {
			if(i < board.spots.length) {
				j += 2;
				if(j < board.spots.length)
					candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
				
				j -= 4;
				if(j >= 0)
					candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
			}
		}
		
		i = currX;
		j = currY;		
		i -= 2;
		if(i >= 0) {
			
			if(++j < board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
			
			j -= 2;
			if(j >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
		}
		
		i = currX;
		j = currY;	
		if(--i >= 0 ) {
			if(i < board.spots.length) {
				j += 2;
				if(j < board.spots.length)
					candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
				
				j -= 4;
				if(j >= 0)
					candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot(), this));
			}
		}
		setValidMovements();

	}
	
	@Override
	public void setValidMovements() {
		
		System.out.println(this);
		//Adding all legal none attack movements.
		while(candidateMovements.iterator().hasNext()) {
			
			if(!candidateMovements.peek().getDestSpot().isOccupied())
				legalMovements.add(new NoneAttackMove(this.spot.getSpot(),candidateMovements.pop().getDestSpot(), this));
			
		//Adding all legal attack movements.
			else { 
				//System.out.println(candidateMovements.peek().getDestSpot().getPieceBySpot().getPlayerCoulor());
				
				if(candidateMovements.peek().getDestSpot().getPiece().getPlayerCoulor() != this.playerCoulor) {
					
					legalAttackMovements.add(new AttackMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot(), this));
				}else {
					
						candidateMovements.pop();
				}
			}
		}	
		candidateMovements.clear();
	}
}



