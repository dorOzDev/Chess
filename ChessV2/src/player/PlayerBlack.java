package player;

import java.util.ArrayList;
import java.util.Iterator;

import enaum.PlayerColour;
import game.Board;
import movement.Move;
import soldiers.Piece;

public class PlayerBlack extends Player {
	
	public PlayerBlack(ArrayList<Piece> remainingPieces,Board board){
		super(remainingPieces, board, PlayerColour.BLACK);
		remainingPieces = board.getPiecesBlack();
	}

	@Override
	protected boolean isInCheck() {	
		return board.getInCheckStatusBlackPlayer();
		
	}

	@Override
	public boolean isInCheckMate() {
		
		return board.isInCheckMateBlackPlayer();
	}

	@Override
	public boolean isInStaleMate() {
		return board.isInStaleMateBlackPlayer();
	}

	@Override
	public boolean isWhite() {
		return false;
	}

	@Override
	public boolean isBlack() {
		
		return true;
	}

	@Override
	public void updateCurrentAvailablePieces() {
		remainingPieces = board.getPiecesBlack();
		
	}
	
	//This method gets potential move making from the clicked event made by player on the GUI class.
	//Need to validate if the same clicked move is valid or not.
	@Override
	public boolean makeMove(Move move) {
		boolean foundLegalMove = false;
		legalMoves = board.getAllLegalBlackMoves();
		int index = 0;
		for(; index < legalMoves.size() && !foundLegalMove; index++) {
			foundLegalMove = checkLegalMove(move, legalMoves.get(index));
			if(foundLegalMove) {
				executeMove(legalMoves.get(index));
				
			}
		}
		return foundLegalMove;
	}

	private void executeMove(Move move) {
		if(!move.isCastleMove()) {
			board.getSpot(move.getSourceSpot()).setPieceOnSpot(null);
			board.getSpot(move.getDestSpot()).setPieceOnSpot(move.getPiece());
			
			move.getPiece().setPiecePos(move.getDestSpot());
			
			if(move.isAttackMove()) {
				board.removeAttackedPiece(move);
			}
		}
		
		else if(move.isCastleMove()){
			board.getSpot(move.getSourceSpot()).setPieceOnSpot(null);
			board.getSpot(move.getRookSourceSpot()).setPieceOnSpot(null);
			
			board.getSpot(move.getDestSpot()).setPieceOnSpot(move.getPiece());
			board.getSpot(move.getRookDestSpot()).setPieceOnSpot(move.getRook());
			
			move.getPiece().setPiecePos(move.getDestSpot());
			move.getRook().setPiecePos(move.getRookDestSpot());
		}
		move.getPiece().makeFirstMove();
		
	}
	

}
 