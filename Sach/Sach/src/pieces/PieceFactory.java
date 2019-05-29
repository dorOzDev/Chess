package pieces;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Board;
import game.Spot;


/*****
 * Piece Factory for creating valid chess pieces.
 * Implemented by Factory Design Pattern
 * 
 */
public class PieceFactory {
	
	public PieceFactory() {
	}
	//PlayerColour playerColour, PieceType pieceType,Board board
	public Piece createPiece(PlayerColour playerColour ,PieceType pieceType, Board board, Spot spot) {
		
		if(pieceType == PieceType.PAWN) {
			return new Pawn(playerColour, pieceType, board, spot);
		}
		else if(pieceType == PieceType.KNIGHT) {
			return new Knight(playerColour, pieceType, board, spot);
		}
		else if(pieceType == PieceType.BISHOP) {
			return new Bishop(playerColour, pieceType, board, spot);
		}
		else if(pieceType == PieceType.ROOK) {
			return new Rook(playerColour, pieceType, board, spot);
		}
		else if(pieceType == PieceType.QUEEN) {
			return new Queen(playerColour, pieceType, board, spot);
		}
		else if (pieceType == PieceType.KING) {
			return new King(playerColour, pieceType, board, spot);
		}
		
		else {

			throw new RuntimeException("No way reached here with no valid PieceType!@#$");
		}
			
	}
	
}
