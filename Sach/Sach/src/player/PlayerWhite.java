package player;

import java.util.ArrayList;
import java.util.List;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;

import movement.Move;
import movement.CommandMove.ExecuteMove;
import movement.CommandMove.MoveExecuter;
import pieces.Piece;

public class PlayerWhite extends Player {
	
	public PlayerWhite(ArrayList<Piece> remainingPieces, Board board){
		super(remainingPieces, board, PlayerColour.WHITE);
		remainingPieces = board.getPiecesWhite();
	}
	
	@Override
	public boolean isInCheck() {
		return board.getInCheckStatusWhitePlayer();
	}
	
	@Override
	public boolean isInCheckMate() {
		return hasKingCaptured() || board.isInCheckMateWhitePlayer();
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
				board.setLastMove(legalMoves.get(index));
			}
		}
		return foundLegalMove;
	}
	
	
	private void executeMove(Move move) { 
		moveExecuter = new MoveExecuter(preferdPieceTypePormotion, true);
		moveExecuter.makeMove(move);
		
	}

	@Override
	public Piece getKing() {
		remainingPieces = board.getPiecesWhite();
		for(Piece piece: remainingPieces) {
			if(piece.getPieceType() == PieceType.KING){
				return piece;
			}
		}
		
		return null;
	}

	@Override
	public List<Move> getLegalMoves() {
		return board.getAllLegalWhiteMoves();
	}
	
	@Override
	public List<Piece> getPlayerRemaningPieces() {
		updateCurrentAvailablePieces();
		return remainingPieces;
	}

	@Override
	public Player getOpponent() {
		return board.getBlackPlayer();
	}
	
	@Override
	public boolean hasKingCaptured() {
		remainingPieces = board.getPiecesWhite();
		for(Piece piece : remainingPieces) {
			if(piece.getPieceType() == PieceType.KING) {
				return false;
			}
		}
		return true;
	}
	
}