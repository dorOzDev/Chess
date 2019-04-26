package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import enaum.PlayerColour;
import game.Board;
import gui.GuiTable.MoveLog;
import movement.Move;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;


public class GameHistoryPanel extends JPanel {
	
	private final DataModel model;
	private final JScrollPane scrollPane;
	
	private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100, 400);
	
	public GameHistoryPanel() {
		
		this.setLayout(new BorderLayout());
		this.model = new DataModel();
		final JTable table = new JTable(model);
		table.setRowHeight(15);
		this.scrollPane = new JScrollPane(table);
		scrollPane.setColumnHeaderView(table.getTableHeader());
		scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
		this.add(scrollPane, BorderLayout.CENTER);
		this.setVisible(true);
		
	}
	
	void redo(final Board board, final MoveLog moveLog) {
		int currRow = 0;
		this.model.clear();
		for(final Move move : moveLog.getMoves()) {
			final String moveText = move.toString();
			if(move.getPiece().getPlayerCoulor() == PlayerColour.WHITE) {
				this.model.setValueAt(moveText, currRow, 0);
			}
			else if(move.getPiece().getPlayerCoulor() == PlayerColour.BLACK) {
				this.model.setValueAt(moveText, currRow, 1);
				currRow++;
			}
				
		}
		
		if(moveLog.getMoves().size() > 0) {
			final Move lastMove = moveLog.getMoves().get(moveLog.size() - 1);
			final String moveText = lastMove.toString();
			
			//**Optional** Add a + sign to the move log when the last move that was made puts the other player in chess.
			//TODO make the magics work.
			/*
			if(lastMove.getPiece().getPieceType() == PlayerColour.WHITE) {
				this.model.setValueAt(moveText + calcCheckAndCheckMate(board), currRow , 0);
			}
			else if(lastMove.getPiece().getPieceType() == PlayerColour.BLACK){
				this.model.setValueAt(moveText + calcCheckAndCheckMate(board), currRow - 1, 1);
			}
			*/
		}
		
		final JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
	
	//TODO check if magic works.
	/*
	private String calcCheckAndCheckMate(Board board) {
		if(board.getCurrPlayer().isInCheckMate()) {
			return "#";
		}
		if(board.getCurrPlayer().isInCheck()) {
			return "+";
		}
		return "";
	}
	*/
	
	private static class DataModel extends DefaultTableModel {
		private final List<Row> values;
		private static final String[] NAMES = {"White", "Black"}; 
		
		DataModel(){
			this.values = new ArrayList<>();
		}
		
		public void clear() {
			this.values.clear();
			setRowCount(0);
		}
		
		@Override
		public int getRowCount() {
			if(values == null) {
				return 0;
			}
			return values.size();
		}
		
		@Override
		public int getColumnCount() {
			return NAMES.length;
		}
		
		@Override
		public Object getValueAt(final int row, final int column) {
			final Row currRow = this.values.get(row);
			if(column == 0) {
				return currRow.getWhiteMove();
			}
			else if(column == 1) {
				return currRow.getBlackMove();
			}
			return null;
		}
		
		@Override
		public void setValueAt(final Object aValue, final int row, final int column) {
			final Row currRow;
			if(values.size() <= row) {
				currRow = new Row();
				values.add(currRow);
			} 
			else {
				currRow = values.get(row);
			}
			if(column == 0) {
				currRow.setWhiteMove((String) aValue);
				fireTableRowsInserted(row, row);
			}
			else if(column == 1) {
				currRow.setBlackMove((String)aValue );
				fireTableCellUpdated(row, column);
			}
		}
		
		@Override
		public Class<?>getColumnClass(final int column){
			return Move.class;
		}
		
		@Override
		public String getColumnName(final int column) {
			return NAMES[column];
		}
		
	}
	
	private static class Row{
		private String whiteMove;
		private String blackMove;
		
		Row(){
			
		}
		
		public String getWhiteMove() {
			return whiteMove;
		}
		
		public String getBlackMove() {
			return blackMove;			
		}
		
		public void setWhiteMove(final String whiteMove) {
			this.whiteMove = whiteMove;
		}
		
		public void setBlackMove(final String blackMove) {
			this.blackMove = blackMove;
		}
	

	}
}