package ai;

import game.Board;

public interface BoardEvaluator {
	
	int evaluate(Board board, int depth);

}
