package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import enaum.PlayerColour;
import game.Board;
import gui.GuiTable.MoveLog;
import movement.Move;
import pieces.Piece;

public class TakenPiecesPanel extends JPanel {
	
	private final JPanel northPanel;
	private final JPanel southPanel;
	private final Board board;
	
	private static final Color PANEL_COLOR = Color.decode("0xFFFFFF");
	private static final String BACKGROUND_COLOR =("0xFFFFFF");
	private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(100, 100); 
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
		board = Board.startNewBoard();
	}
	
	public void redo(final MoveLog moveLog) {
		southPanel.removeAll();
		northPanel.removeAll();
		
		final List<Piece> whiteTakenPieces = new ArrayList<>();
		final List<Piece> blackTakenPieces = new ArrayList<>();
		
		List<Piece> takenPieces = new ArrayList<>();
		takenPieces = board.getTakenPieces();
		//TODO finish this
		/*
		for(final Move move :moveLog.getMoves()) {
			if(move.isAttackMove()) {
				final Piece takenPiece = move.getAttackedPiece();
				if(takenPiece.playerCoulor == PlayerColour.WHITE)
					whiteTakenPieces.add(takenPiece);
				else
					blackTakenPieces.add(takenPiece);
			}
		}
		*/
		
		for(final Piece piece : takenPieces) {
			if(piece.getPlayerCoulor() == PlayerColour.WHITE) {
				whiteTakenPieces.add(piece);
			}
			else {
				blackTakenPieces.add(piece);
			}
		}
		
		//TODO add this, sort the taken piece panel so the stronger taken piece will have greated value.
		/*
		Collections.sort(whiteTakenPieces, new Comparator<Piece>() {

			@Override
			public int compare(Piece o1, Piece o2) {
				
				return ;
			}
			
		});
		*/
		
		for(final Piece takenPiece : whiteTakenPieces) {
			try {
			final BufferedImage image = ImageIO.read(new File(GuiTable.defaultPieceImagePath + takenPiece.getPlayerCoulor().toString().substring(0, 1) + takenPiece.getPieceType().toString() + ".png"));
			final ImageIcon imageIcon = new ImageIcon(image);
			final JLabel imageLabel = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth() , imageIcon.getIconWidth() , Image.SCALE_SMOOTH)));
			this.southPanel.add(imageLabel);
		} 
			catch(final IOException e){
				e.printStackTrace();
			}
	}
		
		for(final Piece takenPiece : blackTakenPieces) {
			try {		
			final BufferedImage image = ImageIO.read(new File(GuiTable.defaultPieceImagePath + takenPiece.getPlayerCoulor().toString().substring(0, 1) + takenPiece.getPieceType().toString() + ".png"));
			final ImageIcon imageIcon = new ImageIcon(image);
			final JLabel imageLabel = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth() , imageIcon.getIconWidth() , Image.SCALE_SMOOTH)));
			this.northPanel.add(imageLabel);
		} 
			catch(final IOException e){
				e.printStackTrace();
			}
	}
		validate();
		
	
	
 }
}
