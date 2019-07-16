package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import game.Board;
import game.Board.BoardBuilder;
import game.FenFormat;

@DisplayName("Test FEN format")
class FenFormatTest {

	@Test
	public void testStartingBoardFenFormat() {
		//Test if FEN format of a scartch Board match expected.
		//Expected FEN format of the starting board is as "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"'
		//For more information regard FEN format: https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
		String expectedFen = new String("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		Board board = new Board(new BoardBuilder());
		board.setStartingPlayer();
		FenFormat fenFormat = new FenFormat();
		assertEquals(expectedFen, fenFormat.makeFenofBoard(board));
	}

}
