package ai;

import game.Board;
import movement.Move;

public interface MoveStrategy {
	Move execute(Board board);

}
