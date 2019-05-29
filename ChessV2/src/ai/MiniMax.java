package ai;

import enaum.PieceType;
import game.Board;

import movement.Move;
import movement.CommandMove.MoveExecuter;

public class MiniMax implements MoveStrategy {

	@Override
	public Move execute(Board board) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	private final BoardEvaluator boardEvaluator;
	
	int depth, minn, maxx, middle;
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
		minn = 0;
		maxx = 0;
		middle = 0;
		
		System.out.println(board.getCurrPlayer() + "Thinking with depth = " + depth);
		
		//int numMoves = board.getCurrPlayer().getLegalMoves().size();
		
		for(final Move move : board.getCurrPlayer().getLegalMoves()) {
			middle++;
			 final MoveExecuter moveExectuter = new MoveExecuter(PieceType.QUEEN, false);
			moveExectuter.makeMove(move);
			board.setCurrPlayer();
			
				 currentValue = board.getCurrPlayer().isWhite() ? min(board.getUpdatedBoard(), this.depth - 1) : max(board.getUpdatedBoard(), this.depth - 1);
				 if(board.getCurrPlayer().isWhite() && currentValue >= highestSeenValue){
						highestSeenValue = currentValue;
						bestMove = move;
						
					}
				 else if(board.getCurrPlayer().isBlack() && currentValue <= lowestSeenValue){
					   lowestSeenValue = currentValue;
						bestMove = move;
					}
			moveExectuter.unMakeMove();
			board.setCurrPlayer();
			
			}
		
		//System.out.println("Min = " + minn + ", Max = "+ maxx + " , Midle = " + middle);
		return bestMove;
		
		}
		
		
	
	
	@Override
	public String toString() {
		return "MiniMax";
	}
	
	public int min(final Board board, final int depth) {
		minn++;
		if(depth == 0  || isEndGame(board)) {
			return this.boardEvaluator.eveluate(board, depth);
		}
		
		int lowestSeenValue = Integer.MAX_VALUE;
		
		for(final Move move : board.getCurrPlayer().getLegalMoves()){
			 final MoveExecuter moveExectuter = new MoveExecuter(PieceType.QUEEN, false);
			 moveExectuter.makeMove(move);
			 board.setCurrPlayer();
			 final int currentValue = max(board.getUpdatedBoard(), depth - 1);
			 if(currentValue <= lowestSeenValue) {
				lowestSeenValue = currentValue;
			 }
			moveExectuter.unMakeMove();
			board.setCurrPlayer();
			}
		
		return lowestSeenValue;
	}
	
	
	public int max(Board board, final int depth) {
		maxx++;
		if(depth == 0  || isEndGame(board)) {
			System.out.println("1");
			return this.boardEvaluator.eveluate(board, depth);
		}
		
		int highestSeenValue = Integer.MIN_VALUE;
		
		for(final Move move : board.getCurrPlayer().getLegalMoves()){
			 final MoveExecuter moveExectuter = new MoveExecuter(PieceType.QUEEN, false);
			 moveExectuter.makeMove(move);
			 board.setCurrPlayer();
			final int currentValue = min(board.getUpdatedBoard(), depth - 1);
			if(currentValue >= highestSeenValue) {
				highestSeenValue = currentValue;
			}
			moveExectuter.unMakeMove();
			board.setCurrPlayer();
		}
		
		return highestSeenValue;
	}
	
	private static boolean isEndGame(final Board board) {
		return  board.getCurrPlayer().isInCheckMate();
	}
	
 */	

}
