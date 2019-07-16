package ai;

import enaum.PlayerColour;
import game.Board;
import pieces.Piece;
import player.Player;

public final class StandardBoardEvaluator implements BoardEvaluator {
	
	private static final int CASTLE_BONUS = 50;
	private static int CHECK_BONUS = 50;
	private static int CHECK_MATE_BONUS = 10000;
	private static int DEPTH_BONUS = 100;

	@Override
	public int eveluate(final Board board, final int depth) {
		return scorePlayer(board, board.getWhitePlayer(), depth) - scorePlayer(board, board.getBlackPlayer(), depth);
	}

	private int scorePlayer(final Board board,final  Player player,final  int depth) {
		return pieceValue(player) + mobility(player) + check(board) + checkMate(board, depth) ; //+casled(player);
	}

	/*
	private static int casled(Board board) {
		//TODO add isCasled to player(?) return player.isCasled() ? CASTLE_BONUS : 0;
		return 0;
	}
	*/
	private static int checkMate(final Board board, final int depth) {
		return board.isInCheckMate(getOpponentColour(board)) ? CHECK_MATE_BONUS * depthBonus(depth) : 0 ;
	}

	private static PlayerColour getOpponentColour(Board board) {
		return board.getCurrentPlayerColour() == PlayerColour.WHITE ? PlayerColour.BLACK : PlayerColour.WHITE;
	}

	private static int depthBonus(int depth) {
		return depth == 0 ? 1 : DEPTH_BONUS * depth;
	}

	private static int check(Board board) {
		return board.isInCheck(getOpponentColour(board)) ? CHECK_BONUS : 0;
	}

	private static int mobility(final Player player) {
		return player.getLegalMoves().size();
	}

	private static int pieceValue(final Player player) {
		int pieceValueScore = 0;
		for(final Piece piece : player.getPlayerRemaningPieces()) {
			pieceValueScore += piece.getPieceValue();
		}
		
		return pieceValueScore;
	}
	
	

}
