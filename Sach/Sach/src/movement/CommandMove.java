package movement;

import enaum.PieceType;
import game.Board;
import pieces.Piece;
import pieces.PieceFactory;

/***********
 * This class execute the selected move by the player
 * This implemented using the Command Design Pattern, that in order to support do/undo move.
 * 
 */
public interface CommandMove {
	
	public void doMove(boolean updateGui);
	public void undoMove();
		
	
	
	/*
	 * EXECUTE PROGRESS MOVE
	 * */
	public class ExecuteMove implements CommandMove {
		
		private Move move;
		private Board board;
		boolean isPieceFirstMove;
		boolean isPromotionMove;
		private PieceFactory pieceFactory;
		public Piece originalPiece;
		public Piece promotedPiece;
		boolean hasMoveExecuted;
		
		public ExecuteMove(Move move){
			this.move = move;
			pieceFactory = new PieceFactory();
			board = Board.startNewBoard();
			isPieceFirstMove = move.getPiece().isFirstMove();		
			originalPiece = pieceFactory.createPiece(move.getPiece().getPlayerCoulor(), move.getPiece().getPieceType(), board, move.getSourceSpot());
			originalPiece.setFirstMove(move.getPiece().isFirstMove());
			hasMoveExecuted = false;
			
			if(move.getPiece().getPieceType() == PieceType.PAWN) {
				isPromotionMove = move.getPiece().isPawnPromotionMove(move.getDestSpot().getX()); 
			}	
		}
		
		@Override
		public void doMove(boolean updateGui) {					
			
			//TODO need to provide the client with option to promote the pawn to any other piece type except for king and pawn(obviously).
			if(isPromotionMove) {
				promotedPiece = pieceFactory.createPiece(move.getPiece().getPlayerCoulor(), PieceType.QUEEN, board, move.getDestSpot());
				board.removePiece(move.getPiece(), updateGui);
				board.addPiece(promotedPiece);
			}
			else {
				board.updatePieceSpot(move.getPiece(), move.getSourceSpot(), move.getDestSpot());	
			}
			
		if(isPieceFirstMove) {
			move.getPiece().makeFirstMove();
		}
		hasMoveExecuted = true;
			
	}
		@Override
		public void undoMove() {
			if(!hasMoveExecuted) {
				System.out.println("No legal prev move exectued was found.");
			}
			else {
				
				if(isPromotionMove) {
					board.removePiece(promotedPiece, false);
					board.addPiece(originalPiece);				
				}
				else {
					board.updatePieceSpot(move.getPiece(), move.getDestSpot(), move.getSourceSpot());
				}
				if(isPieceFirstMove) {
					move.getPiece().unMakeFirstMove();
				}
				hasMoveExecuted = false;
			}
			
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
		boolean isPieceFirstMove;
		boolean isPromotionMove;
		private PieceFactory pieceFactory;
		public Piece originalPiece;
		public Piece promotedPiece;
		public Piece attackedPiece;
		boolean hasMoveExecuted;
		
		public ExecuteAttackMove(Move move) {
			this.move = move;
			board = Board.startNewBoard();
			pieceFactory = new PieceFactory();
			isPieceFirstMove = move.getPiece().isFirstMove();		
			originalPiece = pieceFactory.createPiece(move.getPiece().getPlayerCoulor(), move.getPiece().getPieceType(), board, move.getSourceSpot());
			attackedPiece = pieceFactory.createPiece(move.getAttackedPiece().getPlayerCoulor(), move.getAttackedPiece().getPieceType(), board, move.getAttackedPiece().getSpot());
			originalPiece.setFirstMove(move.getPiece().isFirstMove());
			hasMoveExecuted = false;
			
			if(move.getPiece().getPieceType() == PieceType.PAWN) {
				isPromotionMove = move.getPiece().isPawnPromotionMove(move.getDestSpot().getX()); 
			}	
		}

		@Override
		public void doMove(boolean updateGui) {
			board.removePiece(move.getAttackedPiece(), updateGui);
			
						
			//TODO need to provide the client with option to promote the pawn to any other piece type except for king and pawn.
			if(isPromotionMove) {
				promotedPiece = pieceFactory.createPiece(move.getPiece().getPlayerCoulor(), PieceType.QUEEN, board, move.getDestSpot());
				board.removePiece(move.getPiece(), updateGui);
				board.addPiece(promotedPiece);
			}
			else {
				board.updatePieceSpot(move.getPiece(), move.getSourceSpot(), move.getDestSpot());
			}
				
			if(isPieceFirstMove) {
				move.getPiece().makeFirstMove();
			}
			hasMoveExecuted = true;
		}
		
		

		@Override
		public void undoMove() {
			if(!hasMoveExecuted) {
				System.out.println("No previous move was detected to undo");
			}
			else {
				board.addPiece(attackedPiece);
				if(isPromotionMove) {
					board.removePiece(promotedPiece, false);
					board.addPiece(originalPiece);
				}
				else {	
					board.updatePieceSpot(move.getPiece(), move.getDestSpot(), move.getSourceSpot());
					if(isPieceFirstMove) {
						move.getPiece().unMakeFirstMove();
					}
				}
				hasMoveExecuted = false;
			}
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
		boolean hasMoveExecuted;
		
		public ExecuteCastleMove(Move move) {
			this.move = move;
			board = Board.startNewBoard();
			hasMoveExecuted = false;

		}
		
		@Override
		public void doMove(boolean updateGui) {		
			board.updatePieceSpot(move.getPiece(), move.getSourceSpot(), move.getDestSpot());	
			board.updatePieceSpot(move.getRook(), move.getRookSourceSpot(), move.getRookDestSpot());
			
			move.getPiece().makeFirstMove();
			move.getRook().makeFirstMove();
			hasMoveExecuted = true;
		}

		@Override
		public void undoMove() {
			if(!hasMoveExecuted) {
				System.out.println("No previous move was detected to undo");
			}
			else {
				board.updatePieceSpot(move.getPiece(), move.getDestSpot(), move.getSourceSpot());
				board.updatePieceSpot(move.getRook(), move.getRookDestSpot(), move.getRookSourceSpot());
			
				move.getPiece().unMakeFirstMove();
				move.getRook().unMakeFirstMove();
			}
		}
		
		
	}
	
	public class MoveExecuter{
		private CommandMove commandMove = null;
		PieceType preferedPieceTypePromotion;
		boolean updateGui;
		public MoveExecuter(PieceType preferedPieceTypePromotion, boolean updateGui) {
			this.updateGui = updateGui;
			this.preferedPieceTypePromotion = preferedPieceTypePromotion;
			
		}
		
		public void makeMove(Move move) {
			if(move.isAttackMove()) {
				commandMove = new ExecuteAttackMove(move);
				
			}
			else if(move.isCastleMove()) {
				commandMove = new ExecuteCastleMove(move);
			}
			else {
				commandMove = new ExecuteMove(move);
			}
			
			commandMove.doMove(updateGui);
		}
		
		public void unMakeMove() {
			if(commandMove == null) {
				System.out.println("No legal move was found to unmake");
			}
			else {
				commandMove.undoMove();
			}
		}
		
		public CommandMove getCommandMove() {
			return this.commandMove;
		}
	}
	
		
}


