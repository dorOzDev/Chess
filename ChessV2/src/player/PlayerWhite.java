package player;

public class PlayerWhite extends Player {
	
	PlayerWhite(){
		super();
		remainingPieces = board.getPiecesWhite();
		king = this.getKing();
	}


	

}
