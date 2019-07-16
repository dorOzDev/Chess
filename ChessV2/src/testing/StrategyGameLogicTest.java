package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Board.BoardBuilder;
import game.Spot;
import game.StrategyGameLogic.ContextGameLogic;
import movement.CastleMoveQueenSide;
import movement.Move;
import pieces.King;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;


@DisplayName("Testing the logic behind the game")
class StrategyGameLogicTest {
	//This class is responsible for the logic calculation behind the scene
	
	@Test
	public void testLegalMovesStartingBoard() {
		Board board = new Board(new BoardBuilder());
		ContextGameLogic contextGameLogicWhitePlayer = new ContextGameLogic(PlayerColour.WHITE, board);
		ContextGameLogic contextGameLogicBlackPlayer = new ContextGameLogic(PlayerColour.BLACK, board);
		
		//Testing if number of legal moves of a starting chess board matches with expected
		assertEquals(20, contextGameLogicWhitePlayer.getLegalMoves().size());
		assertEquals(20, contextGameLogicBlackPlayer.getLegalMoves().size());
		
		//Test for each piece the number of legal moves of a starting chess board if matches with expected
		int countPawnLegalMoves = 0;
		int countKingLegalMoves = 0;
		int countQueenLegalMoves = 0;
		int countKnightLegalMoves = 0;
		int countBishopLegalMoves = 0;
		int countRookLegalMoves = 0;
		
		for(Move move : contextGameLogicWhitePlayer.getLegalMoves()) {
			if(move.getPiece().getPieceType() == PieceType.PAWN) {
				countPawnLegalMoves++;
			}
			if(move.getPiece().getPieceType() == PieceType.KING) {
				countKingLegalMoves++;
			}
			if(move.getPiece().getPieceType() == PieceType.QUEEN) {
				countQueenLegalMoves++;
			}
			if(move.getPiece().getPieceType() == PieceType.KNIGHT) {
				countKnightLegalMoves++;
			}
			if(move.getPiece().getPieceType() == PieceType.BISHOP) {
				countBishopLegalMoves++;
			}
			if(move.getPiece().getPieceType() == PieceType.ROOK){
				countRookLegalMoves++;
			}		
		}
		
		assertEquals(16, countPawnLegalMoves);
		assertEquals(0, countKingLegalMoves);
		assertEquals(0, countQueenLegalMoves);
		assertEquals(4, countKnightLegalMoves);
		assertEquals(0, countBishopLegalMoves);
		assertEquals(0, countRookLegalMoves);		
	}
	
	@Test
	public void testCheckMechanismStartingBoard() {
		//Test if check mechanism works with a fresh new board.
		Board board = new Board(new BoardBuilder(null, null));
		ContextGameLogic contextGameLogicWhitePlayer = new ContextGameLogic(PlayerColour.WHITE, board);
		ContextGameLogic contextGameLogicBlackPlayer = new ContextGameLogic(PlayerColour.BLACK, board);
		assertFalse(contextGameLogicWhitePlayer.getInCheckStatus());
		assertFalse(contextGameLogicBlackPlayer.getInCheckStatus());
	}
	
	@Test
	public void testCheckMateMechanismStartingBoard() {
		//Test if check mate mechanism works with a fresh new board.
		Board board = new Board(new BoardBuilder(null, null));
		ContextGameLogic contextGameLogicWhitePlayer = new ContextGameLogic(PlayerColour.WHITE, board);
		ContextGameLogic contextGameLogicBlackPlayer = new ContextGameLogic(PlayerColour.BLACK, board);
		assertFalse(contextGameLogicWhitePlayer.getInCheckMateStatus());
		assertFalse(contextGameLogicBlackPlayer.getInCheckMateStatus());
	}
	
	@Test
	public void testStaleMateMechanismStartingBoard() {
		//Test if stale mate mechanism works with a fresh new board.
		Board board = new Board(new BoardBuilder(null, null));
		ContextGameLogic contextGameLogicWhitePlayer = new ContextGameLogic(PlayerColour.WHITE, board);
		ContextGameLogic contextGameLogicBlackPlayer = new ContextGameLogic(PlayerColour.BLACK, board);
		assertFalse(contextGameLogicWhitePlayer.getInStaleMateStatus());
		assertFalse(contextGameLogicBlackPlayer.getInStaleMateStatus());
	}
	
	@Test
	public void testCheckMechanismInRunningBoard() {
		//Test if check mechanism works with a known check scenario.
		//In this scenario the white king should be in check while the black king is not.
		ArrayList<Piece> whitePlayerPieces= new ArrayList<Piece>();
		ArrayList<Piece> blackPlayerPieces = new ArrayList<Piece>();
		whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(7, 4), true));
		whitePlayerPieces.add(new Queen(PlayerColour.WHITE, PieceType.QUEEN, new Spot(7, 0), false));
		blackPlayerPieces.add(new King(PlayerColour.BLACK, PieceType.KING, new Spot(0, 4), true));
		blackPlayerPieces.add(new Queen(PlayerColour.BLACK, PieceType.QUEEN, new Spot(1, 4), false));
		
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		ContextGameLogic contextGameLogicWhitePlayer = new ContextGameLogic(PlayerColour.WHITE, board);
		ContextGameLogic contextGameLogicBlackPlayer = new ContextGameLogic(PlayerColour.BLACK, board);

		assertTrue(contextGameLogicWhitePlayer.getInCheckStatus());
		assertFalse(contextGameLogicBlackPlayer.getInCheckStatus());
		
	}
	
	@Test
	public void testCheckMateMechanismInRunningBoard() {
		//Test if check mate mechanism works with a known check scenario.
		//In this scenario the black king should be in checkmate while the white king is not.
		// source https://en.wikipedia.org/wiki/Checkmate using "Checkmate with a rook" board 
		ArrayList<Piece> whitePlayerPieces= new ArrayList<Piece>();
		ArrayList<Piece> blackPlayerPieces = new ArrayList<Piece>();
		whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(3, 5), false));
		whitePlayerPieces.add(new Rook(PlayerColour.WHITE, PieceType.ROOK, new Spot(7, 7), false));
		blackPlayerPieces.add(new King(PlayerColour.BLACK, PieceType.KING, new Spot(3, 7), false));

		
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		ContextGameLogic contextGameLogicWhitePlayer = new ContextGameLogic(PlayerColour.WHITE, board);
		ContextGameLogic contextGameLogicBlackPlayer = new ContextGameLogic(PlayerColour.BLACK, board);
		
		assertFalse(contextGameLogicWhitePlayer.getInCheckMateStatus());
		assertTrue(contextGameLogicBlackPlayer.getInCheckMateStatus());		
	}
	
	@Test
	public void testStaleMateMechanismInRunningBoard() {
		//Test if check mate mechanism works with a known check scenario.
		//In this scenario the black king should be in stalemate while the white king is not.
		// source https://en.wikipedia.org/wiki/Stalemate using "Diagram3" board 
		ArrayList<Piece> whitePlayerPieces= new ArrayList<Piece>();
		ArrayList<Piece> blackPlayerPieces = new ArrayList<Piece>();
		whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(5, 2), false));
		whitePlayerPieces.add(new Rook(PlayerColour.WHITE, PieceType.ROOK, new Spot(6, 1), false));
		blackPlayerPieces.add(new King(PlayerColour.BLACK, PieceType.KING, new Spot(7, 0), false));

		
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		ContextGameLogic contextGameLogicWhitePlayer = new ContextGameLogic(PlayerColour.WHITE, board);
		ContextGameLogic contextGameLogicBlackPlayer = new ContextGameLogic(PlayerColour.BLACK, board);
		
		assertFalse(contextGameLogicWhitePlayer.getInStaleMateStatus());
		assertTrue(contextGameLogicBlackPlayer.getInStaleMateStatus());		
	}
	
	@Test
	public void testLegalCastleMove() {
		//Testing isCastleAllowed(Move move) method, this method checks if given Castle move is legal or not.
		//I will test both cases one legal move, and one that is not.
		ArrayList<Piece> whitePlayerPieces= new ArrayList<Piece>();
		ArrayList<Piece> blackPlayerPieces = new ArrayList<Piece>();
		whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(7, 4), true));
		whitePlayerPieces.add(new Rook(PlayerColour.WHITE, PieceType.ROOK, new Spot(7, 0), true));
		blackPlayerPieces.add(new King(PlayerColour.BLACK, PieceType.KING, new Spot(5, 7), false));
		
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		ContextGameLogic contextGameLogicWhitePlayer = new ContextGameLogic(PlayerColour.WHITE, board);
		Move legalCastleMove = new CastleMoveQueenSide(board.getSpot(7, 4), board.getSpot(7, 2), board.getPiece(7, 4), board);
		//TODO change this fucking shit
		assertFalse(contextGameLogicWhitePlayer.isCastleNotAllowed(legalCastleMove));
	}
	
	@Test
	public void testIllegalCastleMove() {
		ArrayList<Piece> whitePlayerPieces= new ArrayList<Piece>();
		ArrayList<Piece> blackPlayerPieces = new ArrayList<Piece>();
		whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(7, 4), true));
		whitePlayerPieces.add(new Rook(PlayerColour.WHITE, PieceType.ROOK, new Spot(7, 0), true));
		blackPlayerPieces.add(new King(PlayerColour.BLACK, PieceType.KING, new Spot(6, 2), false));
		
		Board board = new Board(new BoardBuilder(blackPlayerPieces, whitePlayerPieces));
		ContextGameLogic contextGameLogicWhitePlayer = new ContextGameLogic(PlayerColour.WHITE, board);
		Move legalCastleMove = new CastleMoveQueenSide(board.getSpot(7, 4), board.getSpot(7, 2), board.getPiece(7, 4), board);
		//TODO change this fucking shit
		assertTrue(contextGameLogicWhitePlayer.isCastleNotAllowed(legalCastleMove));
	}

}
