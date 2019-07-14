package game;

import java.util.ArrayList;

import javax.naming.ldap.SortControl;

import game.Board;
import pieces.Piece;

public class Spot {
	
	
	private int x;
	private int y;
	Piece piece;
	private boolean isOccupied;
	
	public Spot(int x, int y) {
		
		this.x = x;
		this.y = y;
		this.isOccupied = false;
		this.piece = null;
		
	}
	
	public Spot(Spot spot) {
		this.x = spot.x;
		this.y = spot.y;
		this.isOccupied = spot.isOccupied;
		this.piece = piece;
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
		if(piece != null)
			setOccupied(true);
		else
			setOccupied(false);
		this.piece = piece;
		
	}
	
	public Piece getPiece() {
		return this.piece;
		
	}
	@Override
	public String toString() {
		char intToAlgebric =(char) (this.y + 'a'); // Cast row number to letter
		return intToAlgebric + Integer.toString(Math.abs(this.x - Board.OFFSET_CLEAN_ROW_COUNT)) ;
	}
	
	
	

}
