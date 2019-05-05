package player;

import java.util.ArrayList;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;

import movement.Move;
import soldiers.Piece;

public class PlayerWhite extends Player {
	
	public PlayerWhite(ArrayList<Piece> remainingPieces, Board board){
		super(remainingPieces, board, PlayerColour.WHITE);
		remainingPieces = board.getPiecesWhite();
	}
	
	@Override
	protected boolean isInCheck() {
		return board.getInCheckStatusWhitePlayer();
	}
	
	@Override
	public boolean isInCheckMate() {
		return board.isInCheckMateWhitePlayer();
	}
	
	@Override
	public boolean isInStaleMate() {
		return board.isInStaleMateWhitePlayer();
	}

	@Override
	public boolean isWhite() {
		return true;
	}

	@Override
	public boolean isBlack() {
		return false;
	}

	@Override
	public void updateCurrentAvailablePieces() {
		remainingPieces = board.getPiecesWhite();
		
	}
	
	//This method gets potential move making from the clicked event made by player on the GUI class.
	//Need to validate if the same clicked move is valid or not.
	@Override
	public boolean makeMove(Move move) {
		boolean foundLegalMove = false;
		legalMoves = board.getAllLegalWhiteMoves();
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
				board.removePiece(move.getAttackedPiece());
			}
			
			if(move.getPiece().getPieceType() == PieceType.PAWN) {
				if(move.getPiece().isPawnPromotionMove()) {
					board.pawnToPromote(move, move.getPiece().getPlayerCoulor());
				}
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

