package soldiers;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;
import movement.AttackMove;
import movement.CandidateMove;
import movement.NoneAttackMove;

public class Knight extends Piece  {

	public Knight(PlayerColour playerColour, PieceType pieceType) {
		super(playerColour, pieceType);
	}

	@Override
	public void setStartPos(Spot spot) {
		this.spot = spot;
		spot.setOccupied(true);
			
	}

	@Override
	public void movement() {
		candidateMovements.clear();
		legalMovements.clear();
		legalAttackMovements.clear();
		setCandidateMovements();
		
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
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
			
			j -= 2;
			if(j >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
		}
		
		i = currX;
		j = currY;
		if(++i < board.spots.length) {
			if(i < board.spots.length) {
				j += 2;
				if(j < board.spots.length)
					candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
				
				j -= 4;
				if(j >= 0)
					candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
			}
		}
		
		i = currX;
		j = currY;		
		i -= 2;
		if(i >= 0) {
			
			if(++j < board.spots.length)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
			
			j -= 2;
			if(j >= 0)
				candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
		}
		
		i = currX;
		j = currY;	
		if(--i >= 0 ) {
			if(i < board.spots.length) {
				j += 2;
				if(j < board.spots.length)
					candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
				
				j -= 4;
				if(j >= 0)
					candidateMovements.add(new CandidateMove(this.spot.getSpot(), board.spots[i][j].getSpot()));
			}
		}
		
		setValidMovements();

	}
	
	@Override
	public void setValidMovements() {
		
		
		//Adding all legal none attack movements.
		while(candidateMovements.iterator().hasNext()) {
			if(!candidateMovements.peek().getDestSpot().isOccupied())
				legalMovements.add(new NoneAttackMove(this.spot.getSpot(),candidateMovements.pop().getDestSpot()));
			
		//Adding all legal attack movements.
			else { 
				if(candidateMovements.peek().getDestSpot().getPieceBySpot().getPlayerCoulor() != this.getPlayerCoulor()) {
					legalAttackMovements.add(new AttackMove(this.spot.getSpot(), candidateMovements.pop().getDestSpot()));
				}else
						candidateMovements.pop();
			}
		}	
		candidateMovements.clear();
	}
}



