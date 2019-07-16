package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Board.BoardBuilder;
import game.Spot;
import movement.AttackMove;
import movement.CastleMoveKingSide;
import movement.CastleMoveQueenSide;
import movement.CommandMove;
import movement.CommandMove.MoveExecuter;
import movement.Move;
import movement.ProgressMove;
import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Rook;


//Testing if move executer class is executing the move properly
class CommandMoveTest {
	

	@Nested
	class MoveExecuterTest{
	
	@Test
	public void testProgressMoveExecutingToProperLocation() {
		//Testing if normal progress move is working as expected
		MoveExecuter moveExecuter = new MoveExecuter(PieceType.QUEEN);
		Board standardBoard = new Board(new BoardBuilder());
		Piece pieceToTest = new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(6, 5), true);
		Move move = new ProgressMove(pieceToTest.getSpot(), new Spot(5, 5), pieceToTest, standardBoard);
		Board updatedBoard = moveExecuter.makeMove(move, standardBoard, true);
		assertTrue(updatedBoard.getSpot(5, 5).isOccupied());
		assertFalse(updatedBoard.getSpot(6, 5).isOccupied());
		}
	
	@Test
	public void testAttackMoveExecuting() {
		//Testing if attacking move(A.K.A capture move) is workign as expected
		MoveExecuter moveExecuter = new MoveExecuter(PieceType.QUEEN);
		ArrayList<Piece>whitePlayerPieces = new ArrayList<Piece>();
		ArrayList<Piece>blackPlayerPieces = new ArrayList<Piece>();
		whitePlayerPieces.add(new Pawn(PlayerColour.WHITE, PieceType.PAWN, new Spot(6, 4), true));
		blackPlayerPieces.add(new Pawn(PlayerColour.BLACK, PieceType.PAWN, new Spot(5, 3), false));
		Board customMadeBoard = new BoardBuilder(blackPlayerPieces, whitePlayerPieces).build();
		Move attackMove = new AttackMove(whitePlayerPieces.get(0).getSpot(), blackPlayerPieces.get(0).getSpot(), whitePlayerPieces.get(0), blackPlayerPieces.get(0), customMadeBoard);
		
		Board updatedBoard = moveExecuter.makeMove(attackMove, customMadeBoard, true);
		assertTrue(updatedBoard.getSpot(5, 3).isOccupied());
		assertFalse(updatedBoard.getSpot(6, 4).isOccupied());
		assertEquals(PlayerColour.WHITE, updatedBoard.getPiece(5, 3).getPlayerCoulor());
		}
	
	@Test
	public void testKingSideCastleMoveExecuting() {
		//Testing if castling movement is working as expected
		MoveExecuter moveExecuter = new MoveExecuter(PieceType.QUEEN);
		ArrayList<Piece>whitePlayerPieces = new ArrayList<Piece>();
		ArrayList<Piece>blackPlayerPieces = new ArrayList<Piece>();
		whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(7, 4), true));
		whitePlayerPieces.add(new Rook(PlayerColour.WHITE, PieceType.ROOK, new Spot(7, 7), true));
		Board customMadeBoard = new BoardBuilder(blackPlayerPieces, whitePlayerPieces).build();
		Move kingSideCastle = new CastleMoveKingSide(whitePlayerPieces.get(0).getSpot(), new Spot(7, 6), whitePlayerPieces.get(0), customMadeBoard);
		
		Board updatedBoard = moveExecuter.makeMove(kingSideCastle, customMadeBoard, true);
		assertFalse(updatedBoard.getSpot(7, 4).isOccupied());
		assertFalse(updatedBoard.getSpot(7, 7).isOccupied());
		assertEquals(PieceType.KING, updatedBoard.getSpot(7, 6).getPiece().getPieceType());
		assertEquals(PieceType.ROOK, updatedBoard.getSpot(7, 5).getPiece().getPieceType());
		}
	
	@Test
	public void testQueenSideCastleMoveExecuting() {
		//Testing if castling movement is working as expected
		MoveExecuter moveExecuter = new MoveExecuter(PieceType.QUEEN);
		ArrayList<Piece>whitePlayerPieces = new ArrayList<Piece>();
		ArrayList<Piece>blackPlayerPieces = new ArrayList<Piece>();
		whitePlayerPieces.add(new King(PlayerColour.WHITE, PieceType.KING, new Spot(7, 4), true));
		whitePlayerPieces.add(new Rook(PlayerColour.WHITE, PieceType.ROOK, new Spot(7, 0), true));
		Board customMadeBoard = new BoardBuilder(blackPlayerPieces, whitePlayerPieces).build();
		Move queenSideCastle = new CastleMoveQueenSide(whitePlayerPieces.get(0).getSpot(), new Spot(7, 2), whitePlayerPieces.get(0), customMadeBoard);
		
		Board updatedBoard = moveExecuter.makeMove(queenSideCastle, customMadeBoard, true);
		assertFalse(updatedBoard.getSpot(7, 4).isOccupied());
		assertFalse(updatedBoard.getSpot(7, 0).isOccupied());
		assertEquals(PieceType.KING, updatedBoard.getSpot(7, 2).getPiece().getPieceType());
		assertEquals(PieceType.ROOK, updatedBoard.getSpot(7, 3).getPiece().getPieceType());
		}
	}
}
