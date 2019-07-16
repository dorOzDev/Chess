package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;

import game.Board;
import game.FenFormat;
import movement.Move;

public class Terminal extends JPanel implements Observer {
	
	private JTextField textField;
	private Board board;
	private JTextArea textArea;
	private FenFormat fenFormat;
	private static int moveNumber;
	private final JScrollPane scrollPane;
	private static EtchedBorder border = new EtchedBorder(EtchedBorder.RAISED);
	private static Dimension TEXT_AREA_SIZE = new Dimension(100, 100);
	
	Terminal(Board board){
		super(new BorderLayout());
		this.board = board;
		this.fenFormat = new FenFormat();
		this.textField = new JTextField(fenFormat.makeFenofBoard(board));
		this.textArea = new JTextArea();
		this.scrollPane = new JScrollPane(textArea);
		this.scrollPane.setPreferredSize(TEXT_AREA_SIZE);
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(textField, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.SOUTH);
		setBorder(border);
		validate();
		setVisible(true);
		init();
		
	}
	
	public void init() {
		textArea.setText("");
		this.moveNumber = 1;
		String initText = new String("Welcome to Chess game project, hopes you have fun :D \n\n");
		textArea.setFont(new Font("Serif", Font.ITALIC, 14));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.append(initText);
	}
	
	public void showCheckMateDialog(Board board) {
		JOptionPane.showMessageDialog(null,"GAME OVER! "+board.getCurrentPlayerColour().toString() + " player is in check mate, for a new game go to File menu","Victory!",1);
	}
	
	public void showStaleMateDialog(Board board) {
		JOptionPane.showMessageDialog(null,"GAME OVER! "+board.getCurrentPlayerColour().toString() + " player is in stale mate, for a new game go to File menu","It's a tie!",1);
	}
	
	public void appendNewMove(Board board) {
		Move lastMove = board.getLastMove();
		textArea.append(moveNumber+")"+ lastMove.getPiece().getPlayerCoulor().toString()+" "+lastMove.getPiece().getPieceType().toString()+" moved from " + lastMove.getSourceSpot().toString() + " to " + lastMove.getDestSpot().toString()+"\n");		
		moveNumber++;
	}
	
	public void showAgainstComputerDialog() {
		 JOptionPane.showMessageDialog(null,"The greater the difficult set is, the greater time it takes to calculate the legal move","Warnning",1);
	}
	
	public void updateFenFormat(Board board) {
		textField.setText(fenFormat.makeFenofBoard(board));
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Board) {

			appendNewMove((Board)arg1);
			updateFenFormat((Board)arg1);
			if(((Board) arg1).isInCheckMate(board.getCurrentPlayerColour())){
				showCheckMateDialog((Board)arg1);
			}
			
			else if(((Board) arg1).isInStaleMate(board.getCurrentPlayerColour())){
				showStaleMateDialog((Board)arg1);
			}
		}
		
		else if(arg1 instanceof GameSetup) {
			if(((GameSetup) arg1).isComputerSelected()) {
				showAgainstComputerDialog();
			}
		}
		
	}
	
	

}