package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import enaum.MoveType;
import enaum.PieceType;
import enaum.PlayerColour;
import movement.Move;
import pieces.Piece;
import pieces.PieceFactory;
import pieces.Queen;

/**********
 * The following class using strategy design pattern in order to
 * Implement all of the calculation algorithms needed to make the game logic in place.
 * 
 ***********/
public interface StrategyGameLogic {
	public List<Move> getLegalMoves(Piece king);
	public boolean getInCheckStatus(Piece king);
	public boolean isCastleAllowed(Move move, Piece king);
	public boolean getInCheckMateStatus(Piece king);
	public boolean getInStaleMate(Piece king);
	

	
	
	
	/*
	 * Operations for black player. 
	 */
	
	public class OperationBlackPlayer implements StrategyGameLogic{
		Board board;
		List<Piece> blackPlayerPieces;
		List<Piece> whitePlayerPieces;
		List<Move> blackPlayerLegalMoves;
		List<Move> whitePlayerLegalMoves;
		PieceFactory pieceFactory;
		
		public OperationBlackPlayer() {
			board = board.startNewBoard();
			blackPlayerPieces = new ArrayList<Piece>();
			whitePlayerPieces = new ArrayList<Piece>();
			blackPlayerLegalMoves = new ArrayList<Move>();
			whitePlayerLegalMoves = new ArrayList<Move>();
			pieceFactory = new PieceFactory();
		}
		
		
		private void calcLegalMoves() {
			blackPlayerLegalMoves.clear();
			blackPlayerPieces = board.getPiecesBlack();
			for(Piece piece : blackPlayerPieces) {
				blackPlayerLegalMoves.addAll(piece.getLegalMovements());
				if(piece.getPieceType() == PieceType.KING) {
					blackPlayerLegalMoves.addAll(piece.getCastleMovements());
				}
			}
			
		}

		@Override
		public List<Move> getLegalMoves(Piece blackKing) {
			calcLegalMoves();
			filterInChessMoves(blackKing);
			return blackPlayerLegalMoves;
		}	
		
		//This method simulate the move on the board if legal move according to chess rules
		//that is you may not make a move that leaves the king in chess.
		
		private boolean simulateAndTestMove(Move move, Piece blackKing) {
			boolean isLegalMove;
			/*********
			 * Simulating Piece on new position.
			 */
			if(move.isCastleMove()) {
				return simulateAndTestCastleMove(move, blackKing);
			}
			if(move.isAttackMove() || move.isEnPassntMove()) {
				board.removePiece(move.getAttackedPiece(), false);
			}

			board.updatePieceSpot(move.getPiece(), move.getSourceSpot(), move.getDestSpot());			
			isLegalMove = getInCheckStatus(blackKing);		
			board.updatePieceSpot(move.getPiece(), move.getDestSpot(), move.getSourceSpot());		
			if(move.isAttackMove() || move.isEnPassntMove()) {
				board.addPiece(move.getAttackedPiece());		
			}	
			return isLegalMove;		
		}

		@Override
		public boolean getInCheckStatus(Piece blackKing) {
			if(blackKing == null) {
				return true;
			}
			calcLegalOppenentMoves();
			for(Move move : whitePlayerLegalMoves) {
				if(move.getDestSpot() == blackKing.getSpot()) {
					return true;
				}		
			}
			return false;
		}
		
		private void calcLegalOppenentMoves() {
			whitePlayerLegalMoves.clear();
			whitePlayerPieces = board.getPiecesWhite();
			for(Piece piece : whitePlayerPieces) {
				whitePlayerLegalMoves.addAll(piece.getLegalMovements());
			}
			
		}
		
		private void filterInChessMoves(Piece blackKing) {
			Move tempMove;
			Iterator<Move>itr = blackPlayerLegalMoves.iterator();
			while(itr.hasNext()){
				tempMove = itr.next();
				if(simulateAndTestMove(tempMove, blackKing)) {
					itr.remove();
				}
			}		
		}
			
		//Simulate and test castle movements.	
		private boolean simulateAndTestCastleMove(Move move, Piece blackKing) {
			boolean isCastleAllowed;
			board.updatePieceSpot(move.getPiece(), move.getSourceSpot(), move.getDestSpot());
			board.updatePieceSpot(move.getRook(), move.getRookSourceSpot(), move.getRookDestSpot());	
			
			isCastleAllowed = getInCheckStatus(blackKing);		
			
			board.updatePieceSpot(move.getPiece(), move.getDestSpot(), move.getSourceSpot());
			board.updatePieceSpot(move.getRook(), move.getRookDestSpot(), move.getRookSourceSpot());

			return isCastleAllowed;
		}


		@Override
		public boolean isCastleAllowed(Move move, Piece blackKing ) {
			return simulateAndTestMove(move, blackKing);
		}


		@Override
		public boolean getInCheckMateStatus(Piece blackKing) {
			if(getInCheckStatus(blackKing) && getLegalMoves(blackKing).isEmpty()) {
				return true;
			}
			return false;
		}


		@Override
		public boolean getInStaleMate(Piece blackKing) {
			if(!getInCheckStatus(blackKing) && getLegalMoves(blackKing).isEmpty()) {
				return true;
			}
			return false;
		}
	
	}
	
	
	
	
	
	/*
	 * Operations for white player class.
	 */
	
	public class OperationWhitePlayer implements StrategyGameLogic{
		
		Board board;
		List<Piece> blackPlayerPieces;
		List<Piece> whitePlayerPieces;
		List<Move> blackPlayerLegalMoves;
		List<Move> whitePlayerLegalMoves;
		PieceFactory pieceFactory;
		
		
		public OperationWhitePlayer() {
			board = board.startNewBoard();
			blackPlayerPieces = new ArrayList<Piece>();
			whitePlayerPieces = new ArrayList<Piece>();
			blackPlayerLegalMoves = new ArrayList<Move>();
			whitePlayerLegalMoves = new ArrayList<Move>();
			pieceFactory = new PieceFactory();
		}
		
		
		
		private void calcLegalMoves() {
			whitePlayerLegalMoves.clear();
			whitePlayerPieces = board.getPiecesWhite();
			for(Piece piece : whitePlayerPieces) {
				whitePlayerLegalMoves.addAll(piece.getLegalMovements());
				if(piece.getPieceType() == PieceType.KING) {
					whitePlayerLegalMoves.addAll(piece.getCastleMovements());
				}
			}
			
		}

		@Override
		public List<Move> getLegalMoves(Piece whiteKing) {
			calcLegalMoves();
			filterInChessMoves(whiteKing);
			return whitePlayerLegalMoves;
		}

		
		
		private boolean simulateAndTestMove(Move move, Piece whiteKing) {
			
			boolean isLegalMove;
			/*********
			 * Simulating Piece on new position.
			 */
			if(move.isCastleMove()) {
				return simulateAndTestCastleMove(move, whiteKing);
			}
			if(move.isAttackMove() || move.isEnPassntMove()) {
				board.removePiece(move.getAttackedPiece(), false);
			}

			board.updatePieceSpot(move.getPiece(), move.getSourceSpot(), move.getDestSpot());			
			isLegalMove = getInCheckStatus(whiteKing);		
			board.updatePieceSpot(move.getPiece(), move.getDestSpot(), move.getSourceSpot());		
			if(move.isAttackMove() || move.isEnPassntMove()) {
				board.addPiece(move.getAttackedPiece());		
			}	
			return isLegalMove;				
		}

		@Override
		public boolean getInCheckStatus(Piece whiteKing) {
			if(whiteKing == null) {
				return true;
			}
			calcLegalOppenentMoves();
			for(Move move : blackPlayerLegalMoves) {
				if(move.getDestSpot() == whiteKing.getSpot())
					return true;
			}
			return false;
		}

		
		private void calcLegalOppenentMoves() {
			blackPlayerLegalMoves.clear();
			blackPlayerPieces = board.getPiecesBlack();
			
			for(Piece piece : blackPlayerPieces) {
				blackPlayerLegalMoves.addAll(piece.getLegalMovements());
			}
		}

		
		private void filterInChessMoves(Piece whiteKing) {
			Move tempMove;
			Iterator<Move>itr = whitePlayerLegalMoves.iterator();
			while(itr.hasNext()){
				tempMove = itr.next();
				if(simulateAndTestMove(tempMove, whiteKing)) {
					itr.remove();
				}
			}
		}

		//Simulate and test castle movements.	
		private boolean simulateAndTestCastleMove(Move move, Piece whiteKing) {
			boolean isCastleAllowed;
			board.updatePieceSpot(move.getPiece(), move.getSourceSpot(), move.getDestSpot());
			board.updatePieceSpot(move.getRook(), move.getRookSourceSpot(), move.getRookDestSpot());
			isCastleAllowed = getInCheckStatus(whiteKing);		
			
			board.updatePieceSpot(move.getPiece(), move.getDestSpot(), move.getSourceSpot());	
			board.updatePieceSpot(move.getRook(), move.getRookDestSpot(), move.getRookSourceSpot());

			return isCastleAllowed;
		}


		@Override
		public boolean isCastleAllowed(Move move, Piece whiteKing) {
			return simulateAndTestMove(move, whiteKing);
		}



		@Override
		public boolean getInCheckMateStatus(Piece whiteKing) {
			if(getInCheckStatus(whiteKing) && getLegalMoves(whiteKing).isEmpty()) {
				return true;
			}
			return false;
		}



		@Override
		public boolean getInStaleMate(Piece whiteKing) {
			if(!getInCheckStatus(whiteKing) && getLegalMoves(whiteKing).isEmpty()) {
				return true;
			}
			return false;
		}

	}	
	
	
	
	

	
	
	
	
	/*
	 * Class that execute the proper algorithm.
	 */
	public class ContextGameLogic{
		private StrategyGameLogic strategy;
		
		public ContextGameLogic(StrategyGameLogic strategy) {
			this.strategy = strategy;
		}
		
		public List<Move> getLegalMoves(Piece king){
			return strategy.getLegalMoves(king);
		}
		
		public boolean getInCheckStatus(Piece king) {
			return strategy.getInCheckStatus(king);
		}
		
		public boolean isCastleAllowed(Move move, Piece king) {
			return strategy.isCastleAllowed(move, king);
		}
		
		public boolean getInCheckMateStatus(Piece king) {
			return strategy.getInCheckMateStatus(king);
		}
		
		public boolean getInStaleMateStatus(Piece king) {
			return strategy.getInStaleMate(king);
		}

		
	}


}