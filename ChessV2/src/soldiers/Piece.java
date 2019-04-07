package soldiers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import enaum.PlayerColour;
import game.Board;
import game.Spot;

public abstract class  Piece  {
	
	protected Board board;
	
	protected Spot spot;
	public PlayerColour playerCoulor;
	protected ArrayList<Spot> validMovements;
	protected LinkedList<Spot> candidateMovements;
	
	
	public Piece() {
		validMovements = new ArrayList<Spot>();
		candidateMovements = new LinkedList<Spot>();
		
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
	
	public void setValidMovements() {
		
		while(!candidateMovements.isEmpty() && !candidateMovements.peek().getSpot().isOccupied()) {
			validMovements.add(candidateMovements.pop().getSpot());

			}
		candidateMovements.clear();
		
	}

	
	public ArrayList<Spot> getMovements(){
		return validMovements;
	}


}
