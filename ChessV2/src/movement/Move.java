package movement;

import game.Spot;

public abstract class Move {
	
	protected Spot sourceSpot;
	protected Spot destSpot;
	
	public Move(final Spot sourceSpot,final Spot destSpot){
		this.destSpot = destSpot;
		this.sourceSpot = sourceSpot;
	}
	
	public Spot getSourceSpot() {
		return sourceSpot;
	}
	
	public Spot getDestSpot() {
		return destSpot;
	}


}
