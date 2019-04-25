package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import gui.GuiTable.MoveLog;
import movement.Move;
import soldiers.Piece;

public class TakenPiecesPanel extends JPanel {
	
	private final JPanel northPanel;
	private final JPanel southPanel;
	
	private static final Color PANEL_COLOR = Color.decode("0xFDE6");
	private static final String BACKGROUND_COLOR =("0xFD5E6");
	private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(10, 10); 
	private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
	
	public TakenPiecesPanel() {
		super(new BorderLayout());
		setBackground(Color.decode(BACKGROUND_COLOR));
		setBorder(PANEL_BORDER);
		this.northPanel = new JPanel(new GridLayout(8, 2)); // At maximum 16 pieces may be taken out of a player.
		this.southPanel = new JPanel(new GridLayout(8, 2));
		this.northPanel.setBackground(PANEL_COLOR);
		this.southPanel.setBackground(PANEL_COLOR);
		add(this.northPanel, BorderLayout.NORTH);
		add(this.southPanel, BorderLayout.SOUTH);
		setPreferredSize(TAKEN_PIECES_DIMENSION);
	}
	
	public void redo(final MoveLog moveLog) {
		southPanel.removeAll();
		northPanel.removeAll();
		
		final List<Piece> whiteTakenPieces = new ArrayList<>();
		final List<Piece> blackTakenPieces = new ArrayList<>();
		
		//TODO finish this
		for(final Move move :moveLog.getMoves()) {
			//if(move.is)
		}
	}

}
