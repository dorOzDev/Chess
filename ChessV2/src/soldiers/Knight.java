package soldiers;

import enaum.PlayerColour;
import game.Spot;

public class Knight extends Piece  {

	public Knight(PlayerColour playerCoulor) {
		this.setPlayerCoulor(playerCoulor);
	}

	@Override
	public void setStartPos(Spot spot) {
		this.spot = spot;
		spot.setOccupied(true);
			
	}

	@Override
	public void movement() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setCandidateMovements() {

	}
	
	@Override
	public void setValidMovements() {
		
	}
}


