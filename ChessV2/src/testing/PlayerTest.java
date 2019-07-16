package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Board.BoardBuilder;
import game.Spot;
import movement.Move;
import movement.ProgressMove;
import pieces.Pawn;
import pieces.Piece;
import player.Player;

class PlayerTest {

	@Test
	public void testPlayerActivePiece() {
		//Test if both players is with the right amount active pieces related to him.
		
		Board board = new Board(new BoardBuilder());
		Player playerWhite = board.getWhitePlayer();
		Player blackPlayer = board.getBlackPlayer();
		assertEquals(32, playerWhite.getPlayerRemaningPieces().size() + blackPlayer.getPlayerRemaningPieces().size());
	}
	
	@Test
	public void testLegalMoveMaking() {
		//Test if move making moves desired piece to correct location
		Board board = new Board(new BoardBuilder());
		board.setStartingPlayer();
		Player playerWhite = board.getWhitePlayer();
		Piece testPiece = new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(6, 0), true);
		Move move = new ProgressMove(testPiece.getSpot(), new Spot(4, 0), testPiece , board);
		Board updatedBoard = playerWhite.makeMove(move, board, true);
		assertEquals(PieceType.PAWN, updatedBoard.getPiece(4, 0).getPieceType());
		assertTrue(updatedBoard.getSpot(4, 0).isOccupied());
		
	}
	
	@Test
	public void testIllegalMoveMaking() {
		//Test illegal move making request
		Board board = new Board(new BoardBuilder());
		board.setStartingPlayer();
		Player playerWhite = board.getWhitePlayer();
		Piece testPiece = new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(6, 0), true);
		Move move = new ProgressMove(testPiece.getSpot(), new Spot(3, 0), testPiece , board);
		Board updatedBoard = playerWhite.makeMove(move, board, true);
		assertFalse(updatedBoard.getSpot(3, 0).isOccupied());
	}

}
