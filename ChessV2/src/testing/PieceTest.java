package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;
import game.Board;
import game.Board.BoardBuilder;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceFactory;
import pieces.Queen;
import pieces.Rook;

class PieceTest {

	@Test
	public void testQueen() {
		//Test queen amount of move in different board scenarios
		
		//Test queen middle of empty board.
		ArrayList<Piece>blackPlayerPieces = new ArrayList<Piece>();
		ArrayList<Piece>whitePlayerPieces = new ArrayList<Piece>();		
		whitePlayerPieces.add(new Queen(PlayerColour.WHITE, PieceType.QUEEN, new Spot(3, 3), true));
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(27, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test queen left bottom pawn is on the way
		whitePlayerPieces.clear();
		whitePlayerPieces.add(new Queen(PlayerColour.WHITE, PieceType.QUEEN, new Spot(7, 0), true));
		whitePlayerPieces.add(new Pawn((PlayerColour.WHITE), PieceType.PAWN, new Spot(6, 1), true));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(14, whitePlayerPieces.get(0).getLegalMovements(board).size());
	}
	
	@Test
	public void testPawn() {
		//Test pawn amount of move in different board scenarios
		
		//Test pawn in first position, no moves has done and is alone in the board
		ArrayList<Piece>blackPlayerPieces = new ArrayList<Piece>();
		ArrayList<Piece>whitePlayerPieces = new ArrayList<Piece>();
		whitePlayerPieces.add(new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(6, 1), true));
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(2, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test pawn after first move was made, empty board
		whitePlayerPieces.clear();
		whitePlayerPieces.add(new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(7, 1), false));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(1, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test pawn in first position, no moves has done yet and has opponent piece to capute
		whitePlayerPieces.clear();
		whitePlayerPieces.add(new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(6, 1), true));
		blackPlayerPieces.add(new Pawn(PlayerColour.BLACK, PieceType.PAWN, new Spot(5, 2), false));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(3, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test pawn when opponent is blocking the way.
		whitePlayerPieces.clear();
		blackPlayerPieces.clear();
		whitePlayerPieces.add(new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(6, 1), true));
		blackPlayerPieces.add(new Pawn(PlayerColour.BLACK, PieceType.PAWN, new Spot(5, 1), false));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(0, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test pawn promotion move(When pawn reaches end of board it shall be promoted)
		//First testing illegal promotion and then test legal promotion
		whitePlayerPieces.clear();
		blackPlayerPieces.clear();
		whitePlayerPieces.add(new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(6, 1), true));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertFalse(whitePlayerPieces.get(0).isPawnPromotionMove(6));
		
		//Now testing legal promotion and then test legal promotion
		whitePlayerPieces.clear();
		whitePlayerPieces.add(new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(7, 1), true));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertTrue(whitePlayerPieces.get(0).isPawnPromotionMove(7));
		
		
	}
	
	@Test
	public void testRook() {
		//Test rook piece amount of moves in different board scenarios
		ArrayList<Piece>blackPlayerPieces = new ArrayList<Piece>();
		ArrayList<Piece>whitePlayerPieces = new ArrayList<Piece>();
		
		
		//Test rook left bottom corner, on empty board
		whitePlayerPieces.add(new Rook(PlayerColour.WHITE, PieceType.ROOK, new Spot(7, 0), true));
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(14, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test rook left bottom corner, one friendly piece is blocking
		whitePlayerPieces.add(new Bishop(PlayerColour.WHITE, PieceType.BISHOP, new Spot(7, 1), true));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(7, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		////Test rook left bottom corner, two friendly piece is blocking
		whitePlayerPieces.add(new Bishop(PlayerColour.WHITE, PieceType.BISHOP, new Spot(6, 0), true));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(0, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test rook left bottom corner, one enemy piece is blocking
		whitePlayerPieces.clear();
		whitePlayerPieces.add(new Rook(PlayerColour.WHITE, PieceType.ROOK, new Spot(7, 0), true));
		blackPlayerPieces.add(new Bishop(PlayerColour.WHITE, PieceType.BISHOP, new Spot(6, 0), false));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(8, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test rook middle board, board is empty.
		whitePlayerPieces.clear();
		blackPlayerPieces.clear();
		whitePlayerPieces.add(new Rook(PlayerColour.WHITE, PieceType.ROOK, new Spot(3, 3), true));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(14, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
	}
	
	@Test
	public void testBishop() {
		//Test bishop piece amount of moves in different board scenarios
		ArrayList<Piece>blackPlayerPieces = new ArrayList<Piece>();
		ArrayList<Piece>whitePlayerPieces = new ArrayList<Piece>();
		
		//Test bishop right bottom corner, empty board
		whitePlayerPieces.add(new Bishop(PlayerColour.WHITE, PieceType.BISHOP, new Spot(7, 7), false));
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(7, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test bishop right bottom corner, friendly piece is blocking the way.
		whitePlayerPieces.add(new Knight(PlayerColour.WHITE, PieceType.KNIGHT, new Spot(6, 6), false));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(0, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		//Test bishop right bottom corner, enemy piece is blocking the way.
		whitePlayerPieces.clear();
		whitePlayerPieces.add(new Bishop(PlayerColour.WHITE, PieceType.BISHOP, new Spot(7, 7), false));
		blackPlayerPieces.add(new Knight(PlayerColour.BLACK, PieceType.KNIGHT, new Spot(6, 6), false));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(1, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test bishop on middle of the board, board is empty.
		whitePlayerPieces.clear();
		blackPlayerPieces.clear();
		whitePlayerPieces.add(new Bishop(PlayerColour.WHITE, PieceType.BISHOP, new Spot(3, 3), true));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(13, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
	}
	
	
	@Test
	public void testKing() {
		//Test king piece amount of moves in different board scenarios
		ArrayList<Piece>blackPlayerPieces = new ArrayList<Piece>();
		ArrayList<Piece>whitePlayerPieces = new ArrayList<Piece>();		
		
		//Test king middle of empty board.
		whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(3, 3), true));
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(8, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test king on starting position, friendly is blocking one way
		whitePlayerPieces.clear();
		whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(7, 4), true));
		whitePlayerPieces.add(new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(6, 4), true));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(4, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		
		//Test king on starting position, enemy is blocking one way
		whitePlayerPieces.clear();
		whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(7, 4), true));
		blackPlayerPieces.add(new Pawn(PlayerColour.BLACK, PieceType.PAWN, new Spot(6, 4), false));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(5, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
	}
	
	
	@Test
	public void testKnight() {
		//Test Knight piece amount of move in different board scenarios
		ArrayList<Piece>blackPlayerPieces = new ArrayList<Piece>();
		ArrayList<Piece>whitePlayerPieces = new ArrayList<Piece>();		
		
		//Test knight in starting position on empty board.
		whitePlayerPieces.add(new Knight(PlayerColour.WHITE, PieceType.KNIGHT, new Spot(7, 1), true));
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(3, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		//Test knight in starting position, one friendly piece is blocking.
		whitePlayerPieces.add(new Pawn(PlayerColour.WHITE, PieceType.KNIGHT, new Spot(6, 3), true));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(2, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		//Test knight in starting position, one enemy piece is blocking.
		whitePlayerPieces.clear();
		whitePlayerPieces.add(new Knight(PlayerColour.WHITE, PieceType.KNIGHT, new Spot(7, 1), true));
		blackPlayerPieces.add(new Pawn(PlayerColour.BLACK, PieceType.KNIGHT, new Spot(6, 3), false));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(3, whitePlayerPieces.get(0).getLegalMovements(board).size());
		
		//Test knight in middle of the board, empty board
		whitePlayerPieces.clear();
		blackPlayerPieces.clear();
		whitePlayerPieces.add(new Knight(PlayerColour.WHITE, PieceType.KNIGHT, new Spot(3, 3), true));
		board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		assertEquals(8, whitePlayerPieces.get(0).getLegalMovements(board).size());				
	}
	
	@Test
	public void testPieceFactory() {
		//Test if piece factory created the desired piece with the right spot
		
		PieceFactory pieceFactory = new PieceFactory();
		Piece testedPiece = pieceFactory.createPiece(PlayerColour.WHITE, PieceType.BISHOP, new Spot(3, 4), false);
		assertEquals(PieceType.BISHOP, testedPiece.getPieceType());
		assertEquals("e5", testedPiece.getSpot().toString());
		assertFalse(testedPiece.isFirstMove());
	}
}
