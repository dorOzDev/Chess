package ai;

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
		return pieceValue(player) + mobility(player) + check(player) + checkMate(player, depth) + casled(player);
	}

	private static int casled(Player player) {
		//TODO add isCasled to player(?) return player.isCasled() ? CASTLE_BONUS : 0;
		return 0;
	}

	private static int checkMate(final Player player, final int depth) {
		return player.getOpponent().getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0 ;
	}

	private static int depthBonus(int depth) {
		return depth == 0 ? 1 : DEPTH_BONUS * depth;
	}

	private static int check(Player player) {
		return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
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
