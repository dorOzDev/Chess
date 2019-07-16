package player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import movement.Move;
import movement.CommandMove;
import movement.CommandMove.ExecuteMove;
import movement.CommandMove.MoveExecuter;
import pieces.Piece;

public class PlayerBlack extends Player {
	
	public PlayerBlack(ArrayList<Piece> remainingPieces,Board board){
		super(remainingPieces, board, PlayerColour.BLACK);
		remainingPieces = board.getPiecesBlack();
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
	public Board makeMove(Move move, Board board, boolean updateTakenList) {
		boolean foundLegalMove = false;
		legalMoves = board.getAllLegalMoves(PlayerColour.BLACK);
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
		remainingPieces = board.getPiecesBlack();
		for(Piece piece: remainingPieces) {
			if(piece.getPieceType() == PieceType.KING){
				return piece;
			}
		}
		return null;
		
	}

	@Override
	public List<Move> getLegalMoves() {
		return board.getAllLegalMoves(PlayerColour.BLACK);
	}

	@Override
	public List<Piece> getPlayerRemaningPieces() {
		updateCurrentAvailablePieces();
		return remainingPieces;
	}

	@Override
	public Player getOpponent() {
		return board.getWhitePlayer();
	}

	@Override
	public boolean hasKingCaptured() {
		remainingPieces = board.getPiecesBlack();
		for(Piece piece : remainingPieces) {
			if(piece.getPieceType() == PieceType.KING) {
				return false;
			}
		}
		return true;
	}
	
	
	

}