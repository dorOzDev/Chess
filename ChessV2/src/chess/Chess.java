package chess;

import game.Board;
import game.GamePanel;
import game.Player;
import gui.GuiTable;

public class Chess {
	
	public static void main(String[] args) {

		Board board = Board.startNewBoard();
		new GuiTable();
		
	}

}
