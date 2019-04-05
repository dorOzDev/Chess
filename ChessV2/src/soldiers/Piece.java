package soldiers;

import java.util.ArrayList;
import java.util.Comparator;

import enaum.PlayerColour;
import game.Board;
import game.Spot;

public abstract class  Piece  {
	
	protected Board board;
	
	protected Spot spot;
	public PlayerColour playerCoulor;
	protected ArrayList<Spot> validMovements;
	protected ArrayList<Spot> candidateMovements;
	
	
	public Piece() {
		validMovements = new ArrayList<Spot>();
		candidateMovements = new ArrayList<Spot>();
		
	}
	public Piece(PlayerColour playerCoulor) {
		this.playerCoulor = playerCoulor;
		
	}
	public PlayerColour getPlayerCoulor() {
		return playerCoulor;
	}
	public void setPlayerCoulor(PlayerColour playerCoulor) {
		this.playerCoulor = playerCoulor;
	}
	public abstract void setStartPos(Spot spot);
	public abstract void movement();
	public abstract void setCandidateMovements();
	public abstract void setValidMovements();
	
	public ArrayList<Spot> getMovements(){
		return validMovements;
	}


}
