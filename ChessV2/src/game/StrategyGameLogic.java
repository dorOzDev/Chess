package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerColour;
import game.Board.BoardBuilder;
import movement.Move;
import pieces.King;
import pieces.Piece;
import pieces.PieceFactory;
import pieces.Queen;

/**********
 * The following class using strategy design pattern in order to
 * Implement all of the calculation algorithms needed to make the game logic in place.
 * 
 ***********/
public interface StrategyGameLogic {
	public List<Move> getLegalMoves();
	public boolean getInCheckStatus(Board board);
	public boolean isCastleNotAllowed(Move move);
	public boolean getInCheckMateStatus();
	public boolean getInStaleMate();
	public boolean getKingSideCastleCapeable();
	public boolean getQueenSideCastleCapeable();
	
	/*
	 * Operations for black player. 
	 */
	
	public class OperationBlackPlayer implements StrategyGameLogic{
		Board board;
		
		BoardBuilder boardBuilder;
		List<Move> blackPlayerLegalMoves;
		List<Move> whitePlayerLegalMoves;
		PieceFactory pieceFactory;
		ArrayList<Piece> whitePlayerPiecesClonedList;
		ArrayList<Piece> blackPlayerPiecesClonedList;	
		
		public OperationBlackPlayer(Board board) {
			this.board = board;
			blackPlayerLegalMoves = new ArrayList<Move>();
			whitePlayerLegalMoves = new ArrayList<Move>();
			pieceFactory = new PieceFactory();
			
		}
	
		private void calcLegalMoves() {
			blackPlayerLegalMoves.clear();
			for(Piece piece : board.getPiecesBlack()) {
				blackPlayerLegalMoves.addAll(piece.getLegalMovements(board));
				if(piece.getPieceType() == PieceType.KING) {
					blackPlayerLegalMoves.addAll(piece.getCastleMovements(board));
				}
			}
			
		}

		@Override
		public List<Move> getLegalMoves() {
			calcLegalMoves();
			filterInChessMoves();
			return blackPlayerLegalMoves;
		}	
		
		//This method simulate the move on the board if legal move according to chess rules
		//that is you may not make a move that leaves the king in chess.
		
		private boolean simulateAndTestMove(Move move) {
			boolean isLegalMove;
			whitePlayerPiecesClonedList = pieceFactory.createImmutableArrayList(board.getPiecesWhite());
			blackPlayerPiecesClonedList = pieceFactory.createImmutableArrayList(board.getPiecesBlack());
			if(move.isCastleMove()) {
				return simulateAndTestCastleMove(move);
			}
			
			if(move.isAttackMove() || move.isEnPassntMove()) {
				Iterator <Piece>itr = whitePlayerPiecesClonedList.iterator();
				Piece tempPiece;
				while(itr.hasNext()) {
					tempPiece = itr.next();
					if(checkEquality(move.getAttackedPiece(), tempPiece)) {
						itr.remove();
					}	
				}
			}
			
			Iterator <Piece> itr = blackPlayerPiecesClonedList.iterator();
			while(itr.hasNext()) {
				Piece tempPiece = itr.next();
				if(checkEquality(move.getPiece(), tempPiece)) {
					tempPiece.setPiecePos(move.getDestSpot());
					break;
				}
			}	
			isLegalMove = getInCheckStatus(new BoardBuilder(blackPlayerPiecesClonedList, whitePlayerPiecesClonedList).build());
			return isLegalMove;		
		}

		@Override
		public boolean getInCheckStatus(Board board) {
			Piece blackKing = board.getBlackPlayer().getKing();
			if(blackKing == null) {
				return true;
			}
		
			calcLegalOppenentMoves(board);
			for(Move move : whitePlayerLegalMoves) {
				if(move.getDestSpot().getX() == blackKing.getX() && move.getDestSpot().getY() == blackKing.getY()) {
					return true;
				}		
			}
			return false;
		}
		
		private void calcLegalOppenentMoves(Board board) {
			whitePlayerLegalMoves.clear();
			for(Piece piece : board.getPiecesWhite()) {
				whitePlayerLegalMoves.addAll(piece.getLegalMovements(board));
			}
			
		}
		
		private void filterInChessMoves() {
			Move tempMove;
			Iterator<Move>itr = blackPlayerLegalMoves.iterator();
			while(itr.hasNext()){
				tempMove = itr.next();
				if(simulateAndTestMove(tempMove)) {
					itr.remove();
				}
			}		
		}
			
		//Simulate and test castle movements.	
		private boolean simulateAndTestCastleMove(Move move) {
			boolean isCastleAllowed;
			blackPlayerPiecesClonedList = pieceFactory.createImmutableArrayList(board.getPiecesBlack());
			Iterator<Piece> itr = blackPlayerPiecesClonedList.iterator();
			
			while(itr.hasNext()) {
				Piece tempPiece = itr.next();
				if(checkEquality(move.getPiece(), tempPiece)){
					tempPiece.setPiecePos(move.getDestSpot());
				}
				
				if( checkEquality(move.getRook(), tempPiece)) {
					tempPiece.setPiecePos(move.getRookDestSpot());
				}
			}		
			isCastleAllowed = getInCheckStatus(new BoardBuilder(blackPlayerPiecesClonedList, board.getPiecesWhite()).build());		
			return isCastleAllowed;
		}
		
		private boolean checkEquality(Piece comparePiece, Piece comparePiece2) {
			if(comparePiece == null || comparePiece2 == null) {
				return false;
			}
			return comparePiece.getX() == comparePiece2.getX() && comparePiece.getY() == comparePiece2.getY() && comparePiece.getPieceType() == comparePiece2.getPieceType();
		}

		@Override
		public boolean isCastleNotAllowed(Move move) {
			return simulateAndTestMove(move);
		}


		@Override
		public boolean getInCheckMateStatus() {
			if(getInCheckStatus(board) && getLegalMoves().isEmpty()) {
				return true;
			}
			return false;
		}


		@Override
		public boolean getInStaleMate() {
			if(!getInCheckStatus(board) && getLegalMoves().isEmpty()) {
				return true;
			}
			return false;
		}

		@Override
		public boolean getKingSideCastleCapeable() {
			if(getInCheckStatus(board)) {
				return false;
			}
			return checkCastleCapeabilityBlack(board.getPiece(0, 4), board.getPiece(0, 7));
		}

		@Override
		public boolean getQueenSideCastleCapeable() {
			if(getInCheckStatus(board)) {
				return false;
			}
			return checkCastleCapeabilityBlack(board.getPiece(0, 4), board.getPiece(0, 0));
		}

	
		public boolean checkCastleCapeabilityBlack(Piece potentialKing, Piece potentialRook) {
			if(potentialKing == null || potentialRook == null) {
				return false;
			}
			if(potentialKing.getPieceType() != PieceType.KING || potentialRook.getPieceType() != PieceType.ROOK) {
				return false;
			}
			if(!potentialKing.isFirstMove() || !potentialRook.isFirstMove()) {
				return false;
			}
			return true;
		}	
	}
	
		
	
	/*
	 * Operations for white player class.
	 */
	
	public class OperationWhitePlayer implements StrategyGameLogic{
		
		Board board;
		List<Move> blackPlayerLegalMoves;
		List<Move> whitePlayerLegalMoves;
		PieceFactory pieceFactory;
		ArrayList<Piece> whitePlayerPiecesClonedList;
		ArrayList<Piece> blackPlayerPiecesClonedList;
		
		
		
		public OperationWhitePlayer(Board board) {
			this.board = board;
			blackPlayerLegalMoves = new ArrayList<Move>();
			whitePlayerLegalMoves = new ArrayList<Move>();
			pieceFactory = new PieceFactory();

		}
		
		
		
		private void calcLegalMoves() {
			whitePlayerLegalMoves.clear();
			for(Piece piece : board.getPiecesWhite()) {
				whitePlayerLegalMoves.addAll(piece.getLegalMovements(board));
				if(piece.getPieceType() == PieceType.KING) {
					whitePlayerLegalMoves.addAll(piece.getCastleMovements(board));
				}
			}
			
		}

		@Override
		public List<Move> getLegalMoves() {
			calcLegalMoves();
			filterInChessMoves();
			return whitePlayerLegalMoves;
		}

		
		private boolean simulateAndTestMove(Move move) {
			boolean isLegalMove;
			whitePlayerPiecesClonedList = pieceFactory.createImmutableArrayList(board.getPiecesWhite());
			blackPlayerPiecesClonedList = pieceFactory.createImmutableArrayList(board.getPiecesBlack());
			if(move.isCastleMove()) {
				return simulateAndTestCastleMove(move);
			}
			
			if(move.isAttackMove() || move.isEnPassntMove()) {
				Iterator <Piece>itr = blackPlayerPiecesClonedList.iterator();
				Piece tempPiece;
				while(itr.hasNext()) {
					tempPiece = itr.next();
					if(checkEquality(move.getAttackedPiece(), tempPiece)) {
						itr.remove();
					}	
				}
			}
			
			Iterator <Piece> itr = whitePlayerPiecesClonedList.iterator();
			while(itr.hasNext()) {
				Piece tempPiece = itr.next();
				if(checkEquality(move.getPiece(), tempPiece)) {
					tempPiece.setPiecePos(move.getDestSpot());
					break;
				}
			}	
			isLegalMove = getInCheckStatus(new BoardBuilder(blackPlayerPiecesClonedList, whitePlayerPiecesClonedList).build());
			return isLegalMove;					
		}
		
		private boolean checkEquality(Piece comparePiece, Piece comparePiece2) {
			if(comparePiece == null || comparePiece2 == null) {
				return false;
			}
			return comparePiece.getX() == comparePiece2.getX() && comparePiece.getY() == comparePiece2.getY() && comparePiece.getPieceType() == comparePiece2.getPieceType();
		}

		@Override
		public boolean getInCheckStatus(Board board) {
			Piece whiteKing = board.getWhitePlayer().getKing();
			if(whiteKing == null) {
				return true;
			}
			calcLegalOppenentMoves(board);
			for(Move move : blackPlayerLegalMoves) {
				if(move.getDestSpot().getX() == whiteKing.getX() && move.getDestSpot().getY() == whiteKing.getY())
					return true;
			}
			return false;
		}

		
		private void calcLegalOppenentMoves(Board board) {
			
			blackPlayerLegalMoves.clear();		
			for(Piece piece : board.getPiecesBlack()) {
				blackPlayerLegalMoves.addAll(piece.getLegalMovements(board));
			}
		}

		
		private void filterInChessMoves() {
			Move tempMove;
			Iterator<Move>itr = whitePlayerLegalMoves.iterator();
			while(itr.hasNext()){
				tempMove = itr.next();
				if(simulateAndTestMove(tempMove)) {
					itr.remove();
				}
			}
		}

		//Simulate and test castle movements.	
		private boolean simulateAndTestCastleMove(Move move) {
			boolean isCastleAllowed;
			whitePlayerPiecesClonedList = pieceFactory.createImmutableArrayList(board.getPiecesWhite());
			Iterator<Piece> itr = whitePlayerPiecesClonedList.iterator();
			
			while(itr.hasNext()) {
				Piece tempPiece = itr.next();
				if(checkEquality(move.getPiece(), tempPiece)){
					tempPiece.setPiecePos(move.getDestSpot());
				}
				
				if( checkEquality(move.getRook(), tempPiece)) {
					tempPiece.setPiecePos(move.getRookDestSpot());
				}
			}		
			isCastleAllowed = getInCheckStatus(new BoardBuilder(board.getPiecesBlack(), whitePlayerPiecesClonedList).build()); 
			return isCastleAllowed;
		}


		@Override
		public boolean isCastleNotAllowed(Move move) {
			return simulateAndTestMove(move);
		}



		@Override
		public boolean getInCheckMateStatus() {
			if(getInCheckStatus(board) && getLegalMoves().isEmpty()) {
				return true;
			}
			return false;
		}



		@Override
		public boolean getInStaleMate() {
			if(!getInCheckStatus(board) && getLegalMoves().isEmpty()) {
				return true;
			}
			return false;
		}

		@Override
		public boolean getKingSideCastleCapeable() {
			if(getInCheckStatus(board)) {
				return false;
			}
			return checkCastleCapeabilityWhite(board.getPiece(7, 4), board.getPiece(7, 7));
		}

		@Override
		public boolean getQueenSideCastleCapeable() {
			
			if(getInCheckStatus(board)) {
				return false;
			}
			return checkCastleCapeabilityWhite(board.getPiece(7, 4), board.getPiece(7, 0));
		}
		
		public boolean checkCastleCapeabilityWhite(Piece potentialKing, Piece potetianlRook) {
			if(potentialKing == null || potetianlRook == null) {
				return false;
			}
			if(potentialKing.getPieceType() != PieceType.KING || potetianlRook.getPieceType() != PieceType.ROOK) {
				return false;
			}
			if(!potentialKing.isFirstMove() || !potetianlRook.isFirstMove()) {
				return false;
			}
			return true;
		}

	}	
	
	
	
	/*
	 * Class that execute the proper algorithm.
	 */
	public class ContextGameLogic{
		private StrategyGameLogic strategy;
		private Board board;
		public ContextGameLogic(PlayerColour playerColour, Board board) {
			this.board = board;
			if(playerColour == PlayerColour.WHITE) {
				this.strategy = new OperationWhitePlayer(board);
			}
			else {
				this.strategy = new OperationBlackPlayer(board);
			}
			
		}
		
		public List<Move> getLegalMoves(){
			return strategy.getLegalMoves();
		}
		
		public boolean getInCheckStatus() {
			return strategy.getInCheckStatus(this.board);
		}
		
		public boolean isCastleNotAllowed(Move move) {
			return strategy.isCastleNotAllowed(move);
		}
		
		public boolean getInCheckMateStatus() {
			return strategy.getInCheckMateStatus();
		}
		
		public boolean getInStaleMateStatus() {
			return strategy.getInStaleMate();
		}
		
		public boolean getKingSideCastleCapeable() {
			return strategy.getKingSideCastleCapeable();
		}
		
		public boolean getQueenSideCastleCapeable() {
			return strategy.getQueenSideCastleCapeable();
		}
		
	}


}