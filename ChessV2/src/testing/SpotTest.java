package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import enaum.PieceType;
import enaum.PlayerColour;
import game.Spot;
import pieces.King;
import pieces.Piece;

@DisplayName("Test Spot Class")
class SpotTest {

	@Test
	public void testSettingPieceOnSpot() {
		//Test if setting/removing piece on a given spot mark this spot as occupied/unoccupied
		
		Spot testSpot = new Spot(0, 0);
		//Test occupied status before setting piece on
		assertEquals(false, testSpot.isOccupied());
		
		Piece testPiece = new King(PlayerColour.BLACK, PieceType.KING, testSpot, true);
		testSpot.setPieceOnSpot(testPiece);
		assertEquals(true, testSpot.isOccupied());
		
		//Test remove piece
		testSpot.setPieceOnSpot(null);
		assertEquals(false, testSpot.isOccupied());
	}

}
