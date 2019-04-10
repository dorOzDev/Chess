package soldiers;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;

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
		validMovements.clear();
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
				candidateMovements.add(board.spots[i][j]);
			
			j -= 2;
			if(j >= 0)
				candidateMovements.add(board.spots[i][j]);
		}
		i = currX;
		j = currY;
		

		if(++i < board.spots.length) {
			if(i < board.spots.length) {
				j += 2;
				if(j < board.spots.length)
					candidateMovements.add(board.spots[i][j]);
				
				j -= 4;
				if(j >= 0)
					candidateMovements.add(board.spots[i][j]);
			}
		}
		i = currX;
		j = currY;
		
		i -= 2;
		if(i >= 0) {
			
			if(++j < board.spots.length)
				candidateMovements.add(board.spots[i][j]);
			
			j -= 2;
			if(j >= 0)
				candidateMovements.add(board.spots[i][j]);
		}
		i = currX;
		j = currY;
		
		if(--i >= 0 ) {
			if(i < board.spots.length) {
				j += 2;
				if(j < board.spots.length)
					candidateMovements.add(board.spots[i][j]);
				
				j -= 4;
				if(j >= 0)
					candidateMovements.add(board.spots[i][j]);
			}
		}
		
		setValidMovements();

	}
	
	@Override
	public void setValidMovements() {
		
		
		while(candidateMovements.iterator().hasNext()) {
			if(!candidateMovements.peek().getSpot().isOccupied())
				validMovements.add(candidateMovements.pop().getSpot());
		
			else { 
				if(candidateMovements.peek().getPieceBySpot().getPlayerCoulor() != this.getPlayerCoulor()) {
					validMovements.add(candidateMovements.pop().getSpot());
				}else
						candidateMovements.pop();
			}
		}
		
		candidateMovements.clear();
		}
	}



