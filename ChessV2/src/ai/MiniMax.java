package ai;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;

import movement.Move;
import movement.CommandMove.MoveExecuter;

public class MiniMax implements MoveStrategy {


	
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
		int highestSeenValue = Integer.MIN_VALUE;
		int lowestSeenValue = Integer.MAX_VALUE;
		int currentValue;

		System.out.println(board.getCurrPlayer() + "Thinking with depth = " + depth);
		
		//int numMoves = board.getCurrPlayer().getLegalMoves().size();
		
		for(final Move move : board.getCurrPlayer().getLegalMoves()) {
			
			 final Board newBoard = board.getCurrPlayer().makeMove(move, board);	
				 currentValue = board.getCurrPlayer().isWhite() ? min(newBoard, this.depth - 1) : max(newBoard, this.depth - 1);
				 if(board.getCurrPlayer().isWhite() && currentValue >= highestSeenValue){
						highestSeenValue = currentValue;
						bestMove = move;
						
					}
				 else if(board.getCurrPlayer().isBlack() && currentValue <= lowestSeenValue){
					   lowestSeenValue = currentValue;
						bestMove = move;
					}
				 
				 if(board.getCurrentPlayerColour() != PlayerColour.BLACK) {
					 board.setCurrentPlayer();
				 }
			}
		

		return bestMove;
		
		}
		
	
	@Override
	public String toString() {
		return "MiniMax";
	}
	
	public int min(final Board board, final int depth) {
		if(depth == 0  || isEndGame(board)) {
			return this.boardEvaluator.eveluate(board, depth);
		}
		
		int lowestSeenValue = Integer.MAX_VALUE;
		
		for(final Move move : board.getCurrPlayer().getLegalMoves()){
			 final Board newBoard = board.getCurrPlayer().makeMove(move, board);
			 final int currentValue = max(newBoard, depth - 1);
			 if(currentValue <= lowestSeenValue) {
				lowestSeenValue = currentValue;
			 }
			
			}
		
		return lowestSeenValue;
	}
	
	
	public int max(Board board, final int depth) {
		if(depth == 0  || isEndGame(board)) {
			return this.boardEvaluator.eveluate(board, depth);
		}
		int highestSeenValue = Integer.MIN_VALUE;
		for(final Move move : board.getCurrPlayer().getLegalMoves()){
			final Board newBoard = board.getCurrPlayer().makeMove(move, board);
			final int currentValue = min(newBoard, depth - 1);
			if(currentValue >= highestSeenValue) {
				highestSeenValue = currentValue;
			}
			
		}
		
		return highestSeenValue;
	}
	
	private static boolean isEndGame(final Board board) {
		return  board.getCurrPlayer().isInCheckMate();
	}
	
 

}
