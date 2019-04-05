package game;

import java.util.ArrayList;

import game.Board;
import soldiers.Piece;

public class Spot {
	
	char identifier;
	private int x;
	private int y;
	Piece piece;
	private boolean isOccupied;
	
	public Spot(int x, int y) {
		
		this.x = x;
		this.y = y;
		this.isOccupied = false;
		
	}
	public Spot() {
		
	}

	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Spot getSpot() {
		return this;
	}
	public void setPieceOnSpot(Piece piece) {
		this.piece = piece;
	}
	
	public Piece getPieceBySpot() {
		return this.piece;
		
	}
	@Override
	public String toString() {
		return "Spot [x=" + x + ", y=" + y + "]";
	}
	
	
	

}