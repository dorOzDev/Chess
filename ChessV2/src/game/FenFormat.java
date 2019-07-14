
/*
Fen format is a format which describe the current state of the board. Including piece position, castle move availability, en passnt target, current player,
half move clock and full. The last two will not be implemented but rather will be hard coded.
We will make a fen format of current played board for statistics and enable load a fen format to continue previous played board. 
*/
//Example of Fen format of starting point rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
// rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1

package game;

import enaum.PlayerColour;

public class FenFormat {
	
	StringBuilder stringBuilder;
	
	private static final int HEX_RADIX = 10;
	
	public FenFormat() {
		this.stringBuilder = new StringBuilder();

	}
	
	public String makeFenofBoard(Board board) {
		stringBuilder.setLength(0);

		appendPiecePositon(board);
		appendCurrPlayerTurn(board);
		appendCastleAvailability(board);
		appendEnPassntTarget(board);
		appendhalfMoveClock();
		appendFullMoveClock();
		return stringBuilder.toString();
		
	}
	
	private void appendPiecePositon(Board board) {
		int emptySpace;
		char pieceLetter;
		for(int i = 0; i < board.NUM_ROWS; i++) {
			emptySpace = 0;
			for(int j = 0; j < board.NUM_COLS; j++) {
				if(board.getSpot(i, j).isOccupied()) {
					if(emptySpace != 0) {
						stringBuilder.append(Character.forDigit(emptySpace, HEX_RADIX));
					}
					pieceLetter = board.getSpot(i, j).getPiece().getPlayerCoulor() == PlayerColour.BLACK ? board.getSpot(i, j).getPiece().getPieceType().toString().toLowerCase().charAt(0) : board.getSpot(i, j).getPiece().getPieceType().toString().charAt(0);
					stringBuilder.append(pieceLetter);
					emptySpace = 0;
				}
				else {
					emptySpace++;
				}
			}
			
			if(emptySpace != 0 ) {
				stringBuilder.append(Character.forDigit(emptySpace, HEX_RADIX));
			}
			
			if(i != 7) {
				stringBuilder.append('/');
			}
		}
		stringBuilder.append(' ');
		
	}
	
	private void appendCurrPlayerTurn(Board board) {
		stringBuilder.append(board.getCurrentPlayerColour().toString().toLowerCase().charAt(0));
		stringBuilder.append(' ');
	}
	
	private void appendCastleAvailability(Board board) {
		
		boolean castleAvailabilityWhitePlayer = false;
		boolean castleAvailabilityBlackPlayer = false;
		
		if(board.getKingSideCastleCapeableWhite()){
			castleAvailabilityWhitePlayer = true;
			stringBuilder.append("K");
		}
		if(board.getQueenSideCastleCapeableWhite()) {
			castleAvailabilityWhitePlayer = true;
			stringBuilder.append("Q");
		}
		if(!castleAvailabilityWhitePlayer) {
			stringBuilder.append("-");
		}
		
		if(board.getKingSideCastleCapeableBlack()) {
			castleAvailabilityBlackPlayer = true;
			stringBuilder.append("k");
		}
		if(board.getQueenSideCastleCapeableBlack()) {
			castleAvailabilityWhitePlayer = true;
			stringBuilder.append("q");
		}
		if(!castleAvailabilityBlackPlayer) {
			stringBuilder.append("-");
		}
		stringBuilder.append(' ');
	}
	
	private void appendEnPassntTarget(Board board) {
		if(board.getEnPassantTargetAvailability()) {
			stringBuilder.append(board.castToBoardCordinate(board.getLastMove().getDestSpot()));
		}
		else {
			stringBuilder.append("-");
		}
		stringBuilder.append(' ');
	}
	
	
	//Half move clock and Full move clock are hard coded.
	private void appendhalfMoveClock() {
		stringBuilder.append("0 ");
	}
	
	private void appendFullMoveClock() {
		stringBuilder.append("1");
	}
	

	

}