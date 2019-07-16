package gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import enaum.PlayerType;
import player.Player;

public class GameSetup extends JDialog {
	
	private PlayerType blackPlayerType;
	private PlayerType whitePlayerType;
	private JSpinner searchDepthSpinner;
	
	private static final String HUMAN_TEXT = "Human";
	private static final String COMPUTER_TEXT = "Computer";
	
	final JRadioButton whiteHumanButton;
	final JRadioButton blackHumanButton;
	final JRadioButton blackComputerButton;
	
	GameSetup(final JFrame frame, final boolean modal){
		super(frame, modal);
		final JPanel myJPanel = new JPanel(new GridLayout(0, 1));
		whiteHumanButton = new JRadioButton(HUMAN_TEXT);
		blackHumanButton = new JRadioButton(HUMAN_TEXT);
		blackComputerButton = new JRadioButton(COMPUTER_TEXT);
		whiteHumanButton.setActionCommand(HUMAN_TEXT);
		final ButtonGroup whiteGroup = new ButtonGroup();
		whiteGroup.add(whiteHumanButton);
		whiteHumanButton.setSelected(true);
		
		final ButtonGroup blackGroup = new ButtonGroup();
		blackGroup.add(blackComputerButton);
		blackGroup.add(blackHumanButton);
		
		blackHumanButton.setSelected(true);
		
		getContentPane().add(myJPanel);
		myJPanel.add(new JLabel("White"));
		myJPanel.add(whiteHumanButton);
		myJPanel.add(new JLabel("Black"));
		myJPanel.add(blackComputerButton);
		myJPanel.add(blackHumanButton);
		
		myJPanel.add(new JLabel("Search"));
		this.searchDepthSpinner = addLabeledSpinner(myJPanel, "Difficult", new SpinnerNumberModel(1, 1, 4, 1));
		
		final JButton cancleButton = new JButton("Cancel");
		final JButton okayButton = new JButton("Okay");
		
		
		okayButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				whitePlayerType = whiteHumanButton.isSelected() ? PlayerType.HUMAN : PlayerType.COMPUTER;
				blackPlayerType = blackHumanButton.isSelected() ? PlayerType.HUMAN : PlayerType.COMPUTER;
				GameSetup.this.setVisible(false);
				
			}
		});
		
		cancleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cancel");
				GameSetup.this.setVisible(false);
				
			}
		});
		
		myJPanel.add(okayButton);
		myJPanel.add(cancleButton);
		
	     setLocationRelativeTo(frame);
	        pack();
	        setVisible(false);
		
	}
	
	public boolean isComputerSelected() {
		return blackComputerButton.isSelected();
	}
	
    void promptUser() {
        setVisible(true);
        
        repaint();
    }
    
    boolean isAIPlayer(final Player player) {
        if(player.isWhite()) {
            return getWhitePlayerType() == PlayerType.COMPUTER;
        }
        return getBlackPlayerType() == PlayerType.COMPUTER;
    }
	
    PlayerType getWhitePlayerType() {
        return this.whitePlayerType;
    }

    PlayerType getBlackPlayerType() {
        return this.blackPlayerType;
    }

    private static JSpinner addLabeledSpinner(final Container c, final String label, final SpinnerModel model) {
        final JLabel l = new JLabel(label);
        c.add(l);
        final JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);
        return spinner;
    }

    int getSearchDepth() {
        return (Integer)this.searchDepthSpinner.getValue();
    }

}
