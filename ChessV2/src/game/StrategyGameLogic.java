package game;

import java.util.ArrayList;
import java.util.List;

import enaum.MoveType;
import enaum.PieceType;
import movement.Move;
import soldiers.Piece;

/**********
 * The following class using strategy design pattern in order to
 * Implement all of the calculation algorithms needed to make the game logic in place.
 * 
 ***********/
public interface StrategyGameLogic {
	public List<Move> getLegalMoves(Piece king);
	public boolean getInCheckStatus(Piece king);
	public boolean isCastleAllowed(Piece king, Piece rook, MoveType moveType);
	public boolean getInCheckMateStatus(Piece king);
	public boolean getInStaleMate(Piece king);
	
	
	
	
	
	public class OperationBlackPlayer implements StrategyGameLogic{
		Board board;
		List<Piece> blackPlayerPieces;
		List<Piece> whitePlayerPieces;
		List<Move> blackPlayerLegalMoves;
		List<Move> whitePlayerLegalMoves;
		
		public OperationBlackPlayer() {
			board = board.startNewBoard();
			blackPlayerPieces = new ArrayList<Piece>();
			whitePlayerPieces = new ArrayList<Piece>();
			blackPlayerLegalMoves = new ArrayList<Move>();
			whitePlayerLegalMoves = new ArrayList<Move>();
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
			board.getSpot(move.getSourceSpot()).setPieceOnSpot(null);
			board.getSpot(move.getDestSpot()).setPieceOnSpot(move.getPiece());
			
			isLegalMove = getInCheckStatus(blackKing);
			
			board.getSpot(move.getSourceSpot()).setPieceOnSpot(move.getPiece());
			board.getSpot(move.getDestSpot()).setPieceOnSpot(null);
			
			return isLegalMove;		
		}

		@Override
		public boolean getInCheckStatus(Piece blackKing) {
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
			for(Move move : blackPlayerLegalMoves) {
				if(simulateAndTestMove(move, blackKing)) {
					blackPlayerLegalMoves.remove(move);
				}
			}		
		}
		
		
		//Simulate and test castle movements.	
		private boolean simulateAndTestMove(Piece blackKing, Piece rook, MoveType moveType) {
			boolean isCastleAllowed;
			if(moveType == MoveType.CASTLE_MOVE_KING_SIDE) {
				Spot kingDestSpot = board.getSpot(blackKing.getX(), blackKing.getY() + 2);
				Spot rookDestSpot = board.getSpot(rook.getX(), rook.getY() - 2);
				board.getSpot(blackKing.getSpot()).setPieceOnSpot(null);
				board.getSpot(rook.getSpot()).setPieceOnSpot(null);
				board.getSpot(kingDestSpot).setPieceOnSpot(blackKing);
				board.getSpot(rookDestSpot).setPieceOnSpot(rook);
				
				isCastleAllowed = getInCheckStatus(blackKing);
				
				board.getSpot(kingDestSpot).setPieceOnSpot(null);
				board.getSpot(rookDestSpot).setPieceOnSpot(null);
				board.getSpot(blackKing.getSpot()).setPieceOnSpot(blackKing);
				board.getSpot(rook.getSpot()).setPieceOnSpot(rook);		
				
			}
			
			else {
				Spot kingDestSpot = board.getSpot(blackKing.getX(), blackKing.getY() - 2);
				Spot rookDestSpot = board.getSpot(rook.getX(), rook.getY() + 3);
				board.getSpot(blackKing.getSpot()).setPieceOnSpot(null);
				board.getSpot(rook.getSpot()).setPieceOnSpot(null);
				board.getSpot(kingDestSpot).setPieceOnSpot(blackKing);
				board.getSpot(rookDestSpot).setPieceOnSpot(rook);
				
				isCastleAllowed = getInCheckStatus(blackKing);
				
				board.getSpot(kingDestSpot).setPieceOnSpot(null);
				board.getSpot(rookDestSpot).setPieceOnSpot(null);
				board.getSpot(blackKing.getSpot()).setPieceOnSpot(blackKing);
				board.getSpot(rook.getSpot()).setPieceOnSpot(rook);		
			}
			return isCastleAllowed;
		}


		@Override
		public boolean isCastleAllowed(Piece blackKing, Piece blackRook, MoveType moveType) {
			return simulateAndTestMove(blackKing, blackRook, moveType);
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
	
	
	
	
	
	
	
	public class OperationWhitePlayer implements StrategyGameLogic{
		
		Board board;
		List<Piece> blackPlayerPieces;
		List<Piece> whitePlayerPieces;
		List<Move> blackPlayerLegalMoves;
		List<Move> whitePlayerLegalMoves;
		
		
		public OperationWhitePlayer() {
			board = board.startNewBoard();
			blackPlayerPieces = new ArrayList<Piece>();
			whitePlayerPieces = new ArrayList<Piece>();
			blackPlayerLegalMoves = new ArrayList<Move>();
			whitePlayerLegalMoves = new ArrayList<Move>();
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
			board.getSpot(move.getSourceSpot()).setPieceOnSpot(null);
			board.getSpot(move.getDestSpot()).setPieceOnSpot(move.getPiece());
			
			isLegalMove = getInCheckStatus(whiteKing);
			
			board.getSpot(move.getSourceSpot()).setPieceOnSpot(move.getPiece());
			board.getSpot(move.getDestSpot()).setPieceOnSpot(null);
			
			return isLegalMove;		
		}

		@Override
		public boolean getInCheckStatus(Piece whiteKing) {
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
			for(Move move : whitePlayerLegalMoves) {
				if(simulateAndTestMove(move, whiteKing)) {
					whitePlayerLegalMoves.remove(move);
				}
			}		
		}

		//Simulate and test castle movements.	
		private boolean simulateAndTestMove(Piece whiteKing, Piece rook, MoveType moveType) {
			boolean isCastleAllowed;
			if(moveType == MoveType.CASTLE_MOVE_KING_SIDE) {
				Spot kingDestSpot = board.getSpot(whiteKing.getX(), whiteKing.getY() + 2);
				Spot rookDestSpot = board.getSpot(rook.getX(), rook.getY() - 2);
				board.getSpot(whiteKing.getSpot()).setPieceOnSpot(null);
				board.getSpot(rook.getSpot()).setPieceOnSpot(null);
				board.getSpot(kingDestSpot).setPieceOnSpot(whiteKing);
				board.getSpot(rookDestSpot).setPieceOnSpot(rook);
				
				isCastleAllowed = getInCheckStatus(whiteKing);
				
				board.getSpot(kingDestSpot).setPieceOnSpot(null);
				board.getSpot(rookDestSpot).setPieceOnSpot(null);
				board.getSpot(whiteKing.getSpot()).setPieceOnSpot(whiteKing);
				board.getSpot(rook.getSpot()).setPieceOnSpot(rook);		
				
			}
			
			else {
				Spot kingDestSpot = board.getSpot(whiteKing.getX(), whiteKing.getY() - 2);
				Spot rookDestSpot = board.getSpot(rook.getX(), rook.getY() + 3);
				board.getSpot(whiteKing.getSpot()).setPieceOnSpot(null);
				board.getSpot(rook.getSpot()).setPieceOnSpot(null);
				board.getSpot(kingDestSpot).setPieceOnSpot(whiteKing);
				board.getSpot(rookDestSpot).setPieceOnSpot(rook);
				
				isCastleAllowed = getInCheckStatus(whiteKing);
				
				board.getSpot(kingDestSpot).setPieceOnSpot(null);
				board.getSpot(rookDestSpot).setPieceOnSpot(null);
				board.getSpot(whiteKing.getSpot()).setPieceOnSpot(whiteKing);
				board.getSpot(rook.getSpot()).setPieceOnSpot(rook);		
			}
			return isCastleAllowed;
		}


		@Override
		public boolean isCastleAllowed(Piece whiteKing, Piece whiteRook, MoveType moveType) {
			return simulateAndTestMove(whiteKing, whiteRook, moveType);
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
		
		public boolean isCastleAllowed(Piece king, Piece rook, MoveType moveType) {
			return strategy.isCastleAllowed(king, rook, moveType);
		}
		
		public boolean getInCheckMateStatus(Piece king) {
			return strategy.getInCheckMateStatus(king);
		}
		
		public boolean getInStaleMateStatus(Piece king) {
			return strategy.getInStaleMate(king);
		}
		
	}
}


