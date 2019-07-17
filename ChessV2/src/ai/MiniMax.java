package ai;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;

import movement.Move;
import movement.CommandMove.MoveExecuter;

public class MiniMax implements MoveStrategy {


	/***********
	 * Mini Max algorithm implemented with alpha beta pruning
	 * 
	************/
	
	private final BoardEvaluator boardEvaluator;
	
	int depth;
	public MiniMax(int depth) {
		boardEvaluator = new  StandardBoardEvaluator();
		this.depth = depth;
	}
	@Override
	public Move execute(Board board) {
		final long startTime = System.currentTimeMillis();	
		Move bestMove = null;
		int beta = Integer.MAX_VALUE;
		int alpha = Integer.MIN_VALUE;
		int currentValue;

		System.out.println(board.getCurrPlayer() + "Thinking with depth = " + depth);		
		
		//int numMoves = board.getCurrPlayer().getLegalMoves().size();	
		for(final Move move : board.getAllLegalMoves(board.getCurrentPlayerColour())) {
			
			 final Board newBoard = board.getCurrPlayer().makeMove(move, board, false);	
				 currentValue = board.getCurrPlayer().isWhite() ? min(newBoard, this.depth - 1, alpha, beta) : max(newBoard, this.depth - 1, alpha ,beta);
				 if(board.getCurrPlayer().isWhite() && currentValue > alpha){
					 	alpha = currentValue;
						bestMove = move;
						
					}
				 else if(board.getCurrPlayer().isBlack() && currentValue < beta){
					 	beta = currentValue;
						bestMove = move;
					}			 

			}

		return bestMove;
		
		}
		
	
	@Override
	public String toString() {
		return "MiniMax";
	}
	
	public int min(final Board board, final int depth, int alpha, int beta) {

		if(depth == 0  || isEndGame(board)) {
			return this.boardEvaluator.evaluate(board, depth);
		}
		
		int lowestSeenValue = beta;
		
		for(final Move move : board.getAllLegalMoves(board.getCurrentPlayerColour())){
			 final Board newBoard = board.getCurrPlayer().makeMove(move, board, false);
			 final int currentValue = max(newBoard, depth - 1, alpha, beta);
			 lowestSeenValue = Math.min(lowestSeenValue, currentValue);
			 if(lowestSeenValue <= alpha) {
				break;
			 }
			
			}
		
		return lowestSeenValue;
	}
	
	
	public int max(Board board, final int depth, int alpha, int beta) {

		if(depth == 0  || isEndGame(board)) {
			return this.boardEvaluator.evaluate(board, depth);
		}
		int highestSeenValue = alpha;
		for(final Move move : board.getAllLegalMoves(board.getCurrentPlayerColour())){
			final Board newBoard = board.getCurrPlayer().makeMove(move, board, false);
			final int currentValue = min(newBoard, depth - 1, alpha, beta);
			highestSeenValue = Math.max(highestSeenValue, currentValue);
			if(beta <= highestSeenValue) {
				break;
			}
			
		}
		
		return highestSeenValue;
	}
	
	private static boolean isEndGame(final Board board) {
		return  board.isInCheckMate(board.getCurrentPlayerColour());
	}
	
 

}
