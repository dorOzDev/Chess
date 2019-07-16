package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Board.BoardBuilder;
import game.Spot;
import pieces.King;
import pieces.Piece;
import pieces.Queen;

//Testing the Board class
@DisplayName("Board Class")
class BoardTest { 
	
	Board board;	
	@Before
	public void setUp() {
		board = new Board(new BoardBuilder());
	}
	
	@Test
	public void testSettingAndChangingTurnsMechanism() {
		board = new Board(new BoardBuilder());
		
		//Testing starting player color
		board.setStartingPlayer();
		assertEquals(PlayerColour.WHITE, board.getCurrentPlayerColour());	
		
		//Testing changing turns working as expected i.e next turn should be black, the one after should be white and then black etc.
		board.setCurrentPlayer();
		assertEquals(PlayerColour.BLACK, board.getCurrentPlayerColour());
		board.setCurrentPlayer();
		assertEquals(PlayerColour.WHITE, board.getCurrentPlayerColour());	
	}
	
	@Test
	public void testCastingNumericCoordinatesIntoAlgebricNotionCoordinates() {
		//Testing if casting numeric coordinates works as expected for various inputs
		board = new Board(new BoardBuilder());
		Spot testSpot = new Spot(0, 0);
		Spot testSpot1 = new Spot(7, 7);
		Spot testSpot2 = new Spot(5, 4);
		
		assertEquals("a8", board.castToBoardCordinate(testSpot));
		assertEquals("h1", board.castToBoardCordinate(testSpot1));
		assertEquals("e3", board.castToBoardCordinate(testSpot2));
		
	}
	
	
	//Testing the nested class BoardBuilder inside Board class
	@Nested
	@DisplayName("Nested Board Builder Class")
	class BoardBuilderTest{
		
		@Test
		public void testBuildBoardFromScratch() {
			board = new Board(new BoardBuilder());
			
			//Test if number of created pieces matches with expected
			assertEquals(32, board.getPiecesBlack().size() + board.getPiecesWhite().size());
			//Test if king starting position matches with expected
			assertEquals(PieceType.KING, board.getPiece(0, 4).getPieceType());
			assertEquals(PieceType.KING, board.getPiece(7, 4).getPieceType());
			
		}
		
		@Test
		public void testBuildBoardFromActiveGame() {
			//Testing if building board from active game(That has been played) matches with expected results
			ArrayList<Piece>blackPlayerPieces = new ArrayList<Piece>();
			ArrayList<Piece>whitePlayerPieces = new ArrayList<Piece>();
			
			blackPlayerPieces.add(new King(PlayerColour.BLACK, PieceType.KING, new Spot(0, 4), true));
			blackPlayerPieces.add(new Queen(PlayerColour.BLACK, PieceType.KING, new Spot(1, 4), false));
			
			whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(7, 4), true));
			whitePlayerPieces.add(new Queen(PlayerColour.WHITE, PieceType.KING, new Spot(7, 0), false));
			
			Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
			
			assertEquals(4, board.getPiecesBlack().size() + board.getPiecesWhite().size());
		}
	}
	
}
