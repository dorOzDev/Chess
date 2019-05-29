package game;

import player.Player;

public class GameState {
	
	Board board;
	Player currPlayer;
	
	public GameState(){
		board = Board.startNewBoard();	
	}
	
	public boolean isEndGame() {
		currPlayer = board.getCurrPlayer();
		return currPlayer.isInCheckMate() || currPlayer.isInStaleMate();
	}
	
	

}
