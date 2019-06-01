package movement;

import java.util.ArrayList;
import java.util.Iterator;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Board.*;

import pieces.Piece;
import pieces.PieceFactory;

/***********
 * This class execute the selected move by the player
 * This implemented using the Command Design Pattern, that in order to support do/undo move.
 * 
 */
public interface CommandMove {
	
	public Board doMove();
	

	/*
	 * EXECUTE PROGRESS MOVE
	 * */
	public class ExecuteMove implements CommandMove {
		
		private Move move;
		private Board board;
		boolean isPieceFirstMove;
		boolean isPromotionMove;
		private PieceFactory pieceFactory;
		public Piece promotedPiece;
		ArrayList<Piece> playerBlackPieces;
		ArrayList<Piece> playerWhitePieces;
		
		public ExecuteMove(Move move, Board board){
			this.move = move;
			this.pieceFactory = new PieceFactory();
			this.board = board;
			this.playerBlackPieces = pieceFactory.createImmutableArrayList(board.getPiecesBlack());
			this.playerWhitePieces = pieceFactory.createImmutableArrayList(board.getPiecesWhite());
		}
		
		//@SuppressWarnings("unchecked")
		@Override
		public Board doMove() {								
			//TODO need to provide the client with option to promote the pawn to any other piece type(Queen is default right now).
			Iterator <Piece> itr;
			Piece tempPiece = null;
		
			if(move.getPiece().getPlayerCoulor() == PlayerColour.WHITE){
				itr = playerWhitePieces.iterator();
			}
			else {
				itr = playerBlackPieces.iterator();
			}
			
			while(itr.hasNext()) {
				tempPiece = itr.next();
				if(checkEquality(tempPiece, move.getPiece())) {
					break;
				}
			}
			tempPiece.makeFirstMove();
			if(move.getPiece().isPawnPromotionMove(move.getDestSpot().getX())) {
				promotedPiece = pieceFactory.createPiece(move.getPiece().getPlayerCoulor(), PieceType.QUEEN, move.getDestSpot(), move.getPiece().isFirstMove());
				itr.remove();
				((ArrayList<Piece>) itr).add(promotedPiece);
			}
			else {
				tempPiece.setPiecePos(move.getDestSpot());	
			}

			
		
		return new BoardBuilder(playerBlackPieces, playerWhitePieces).build();
			
	}
		
		private boolean checkEquality(Piece piece1, Piece piece2) {
			return piece1.getX() == piece2.getX() && piece1.getY() == piece2.getY() && piece1.getPieceType() == piece2.getPieceType();
		}

			
		public Move getMove() {
			return this.move;
		}	
	}
	
	
	
	/*
	 * EXECUTE ATTACK MOVE
	 * */
	
	public class ExecuteAttackMove implements CommandMove{
		
		private Move move;
		private Board board;
		private PieceFactory pieceFactory;
		public Piece promotedPiece;
		public Piece attackedPiece;
		private ArrayList<Piece> playerBlackPieces;
		private ArrayList<Piece> playerWhitePieces;
		
		public ExecuteAttackMove(Move move, Board board) {
			this.move = move;
			this.board = board;
			this.pieceFactory = new PieceFactory();
			this.playerBlackPieces = pieceFactory.createImmutableArrayList(board.getPiecesBlack());
			this.playerWhitePieces = pieceFactory.createImmutableArrayList(board.getPiecesWhite());	
		}

		@Override
		public Board doMove() {
			Iterator <Piece>attackingPieceItr;
			Iterator <Piece>attackedPieceItr;
			Piece tempAttakcingPiece = null;
			Piece tempAttackedPiece = null;
			
			if(move.getPiece().getPlayerCoulor() == PlayerColour.WHITE) {
				attackingPieceItr = playerWhitePieces.iterator();
				attackedPieceItr = playerBlackPieces.iterator();
			}
			else {
				attackingPieceItr = playerBlackPieces.iterator();
				attackedPieceItr = playerWhitePieces.iterator();
			}
			
			while(attackingPieceItr.hasNext()) {
				tempAttakcingPiece = attackingPieceItr.next();
				if(checkEquality(tempAttakcingPiece, move.getPiece()))
					break;
			}
			
			while(attackedPieceItr.hasNext()) {
				tempAttackedPiece = attackedPieceItr.next();
				if(checkEquality(tempAttackedPiece, move.getAttackedPiece())) {
					attackedPieceItr.remove();
					board.updateTakenPieceList(tempAttackedPiece);
					break;
				}
			}
					
			//TODO need to provide the client with option to promote the pawn to any other piece type except for king and pawn.
			tempAttakcingPiece.makeFirstMove();
			if(move.getPiece().isPawnPromotionMove(move.getDestSpot().getX())) {
				promotedPiece = pieceFactory.createPiece(move.getPiece().getPlayerCoulor(), PieceType.QUEEN, move.getDestSpot(), move.getPiece().isFirstMove());
				attackingPieceItr.remove();
				if(tempAttakcingPiece.getPlayerCoulor() == PlayerColour.WHITE) {
					playerWhitePieces.add(promotedPiece);
				}
				else {
					playerBlackPieces.add(promotedPiece);
				}
			}
			else {
				tempAttakcingPiece.setPiecePos(move.getDestSpot());
			}
			
			return new BoardBuilder(playerBlackPieces, playerWhitePieces).build();
		}
		
		private boolean checkEquality(Piece piece1, Piece piece2) {
			return piece1.getX() == piece2.getX() && piece1.getY() == piece2.getY() && piece1.getPieceType() == piece2.getPieceType();
		}
		
		public Move getMove() {
			return this.move;
		}
		
	}
	
	
	
	
	/*
	 * EXECUTE CASTLE MOVE
	 * */	
	public class ExecuteCastleMove implements CommandMove{
		
		private Move move;
		private Board board;
		private ArrayList<Piece> playerBlackPieces;
		private ArrayList<Piece> playerWhitePieces;
		private PieceFactory pieceFactory;
		
		public ExecuteCastleMove(Move move, Board board) {
			this.move = move;
			this.board = board;
			this.pieceFactory = new PieceFactory();
			this.playerBlackPieces = pieceFactory.createImmutableArrayList(board.getPiecesBlack());
			this.playerWhitePieces = pieceFactory.createImmutableArrayList(board.getPiecesWhite());		

		}
		
		@Override
		public Board doMove() {
			Iterator <Piece>itr;
			Piece tempIterating = null;
			Piece tempKing = null;
			Piece tempRook = null;
			
			if(move.getPiece().getPlayerCoulor() == PlayerColour.WHITE) {
				itr = playerWhitePieces.iterator();
			}
			else {
				itr = playerBlackPieces.iterator();
			}
			
			while(itr.hasNext()) {
				tempIterating =itr.next();
				if(checkEquality(tempIterating, move.getRook())) {
					tempRook = tempIterating;
				}
				if(checkEquality(tempIterating, move.getPiece())) {
					tempKing = tempIterating;
				}
			}
			
			tempKing.makeFirstMove();
			tempRook.makeFirstMove();
			
			tempKing.setPiecePos(move.getDestSpot());
			tempRook.setPiecePos(move.getRookDestSpot());
			
			return new BoardBuilder(playerBlackPieces, playerWhitePieces).build();
			
		}

		private boolean checkEquality(Piece piece1, Piece piece2) {
			return piece1.getX() == piece2.getX() && piece1.getY() == piece2.getY() && piece1.getPieceType() == piece2.getPieceType();
		}
		
		
	}
	
	public class MoveExecuter{
		private CommandMove commandMove = null;
		PieceType preferedPieceTypePromotion;
		boolean updateGui;
		public MoveExecuter(PieceType preferedPieceTypePromotion) {
		
			this.updateGui = updateGui;
			this.preferedPieceTypePromotion = preferedPieceTypePromotion;
			
			
		}
		
		public Board makeMove(Move move, Board board) {
			if(move.isAttackMove()) {
				commandMove = new ExecuteAttackMove(move, board);
				
			}
			else if(move.isCastleMove()) {
				commandMove = new ExecuteCastleMove(move, board);
			}
			else {
				commandMove = new ExecuteMove(move, board);
			}
			
			return commandMove.doMove();
		}
		
		
		public CommandMove getCommandMove() {
			return this.commandMove;
		}
	}
	
		
}


