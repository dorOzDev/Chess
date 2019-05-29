package pieces;

import java.util.ArrayList;

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
	public Piece createPiece(PlayerColour playerColour ,PieceType pieceType, Spot spot, boolean isFirstMove) {
		
		if(pieceType == PieceType.PAWN) {
			return new Pawn(playerColour, pieceType, new Spot(spot), isFirstMove);
		}
		else if(pieceType == PieceType.KNIGHT) {
			return new Knight(playerColour, pieceType, new Spot(spot), isFirstMove);
		}
		else if(pieceType == PieceType.BISHOP) {
			return new Bishop(playerColour, pieceType, new Spot(spot), isFirstMove);
		}
		else if(pieceType == PieceType.ROOK) {
			return new Rook(playerColour, pieceType, new Spot(spot), isFirstMove);
		}
		else if(pieceType == PieceType.QUEEN) {
			return new Queen(playerColour, pieceType, new Spot(spot), isFirstMove);
		}
		else if (pieceType == PieceType.KING) {
			return new King(playerColour, pieceType, new Spot(spot), isFirstMove);
		}
		
		else {

			throw new RuntimeException("No way reached here with no valid PieceType!@#$");
		}
			
	}
	
	public ArrayList<Piece> createImmutableArrayList(ArrayList<Piece> piecesToClone){
		ArrayList<Piece> immutablePieceList = new ArrayList<Piece>();
		for(Piece piece : piecesToClone) {
			immutablePieceList.add(createPiece(piece.getPlayerCoulor(), piece.getPieceType(), new Spot(piece.getSpot()),  piece.isFirstMove));
		}
		return immutablePieceList;
	}
	
}
