package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Logic.HighScores;

public class HighscoreScreen extends JDialog {

	private static final long serialVersionUID = 7254320023172953429L;

	private JLabel score1 = new JLabel("score1");
	private JLabel score2 = new JLabel("score2");
	private JLabel score3 = new JLabel("score3");
	private JLabel score4 = new JLabel("score4");
	private JLabel score5 = new JLabel("score5");
	private GenericButton button = new GenericButton("Return");

	//constructor
	public HighscoreScreen(JFrame parent, String title) {
		super(parent, title, true);
		if (parent != null) {
			Dimension parentSize = parent.getSize(); 
			Point p = parent.getLocation(); 
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		getContentPane().add(makeDisplay());
		getContentPane().add(makeButtonPanel(), BorderLayout.SOUTH);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setModal(true);
		pack();
		this.setVisible(true);
	}

	//methods
	public JPanel makeDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1,0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
		retrieveHighScores();
		panel.add(score1);
		panel.add(score2);
		panel.add(score3);
		panel.add(score4);
		panel.add(score5);
		return panel;
	}

	public JPanel makeButtonPanel() {
		JPanel panel = new JPanel();
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//react to hitting the return button
				dispose();
			}
		});
		panel.add(button);
		return panel;
	}

	public void retrieveHighScores() {
		HighScores scoreFile = new HighScores("src/Highscores.txt");
		String[] list = new String[5];
		list = scoreFile.readScores(5);
		String[] textLabels = new String[5];
		for (int i = 0; i < list.length; i++) {
			String[] text = list[i].split(":");
			textLabels[i] = "Commander " + text[2] + " with his ship " + text[1] + " scored " + text[0]
					+ " points on " + text[3] + " !";
		}	
		score1.setText(textLabels[0]);
		score2.setText(textLabels[1]);
		score3.setText(textLabels[2]);
		score4.setText(textLabels[3]);
		score5.setText(textLabels[4]);	
	}
}
