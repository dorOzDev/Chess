package player;

public class PlayerBlack extends Player {
	
	PlayerBlack(){
		super();
		remainingPieces = board.getPiecesBlack();
		king = this.getKing();
		
	}
	

}
 