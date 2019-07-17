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
	public boolean isWhite() {
		return true;
	}

	@Override
	public boolean isBlack() {
		return false;
	}
	
	//This method gets potential move making from the clicked event made by player on the GUI class.
	//Need to validate if the same clicked move is valid or not.
	@Override
	public Board makeMove(Move move, Board board, boolean updateTakenList) {
		boolean foundLegalMove = false;
		legalMoves = board.getAllLegalMoves(PlayerColour.WHITE);
		int index = 0;
		for(; index < legalMoves.size() && !foundLegalMove; index++) {
			foundLegalMove = checkLegalMove(move, legalMoves.get(index));
			if(foundLegalMove) {
				board.setLastMove(legalMoves.get(index));
				board.setCurrentPlayer();
				return executeMove(legalMoves.get(index), board, updateTakenList);
			}
		}
		return board;
	}
	
	
	private Board executeMove(Move move, Board board, boolean updateTakenList) { 
		moveExecuter = new MoveExecuter(preferdPieceTypePormotion);
		board.setLastMove(move);
		return moveExecuter.makeMove(move, board, updateTakenList);
		
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
		return board.getAllLegalMoves(PlayerColour.WHITE);
	}
	
	@Override
	public List<Piece> getPlayerRemaningPieces() {
		return remainingPieces;
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