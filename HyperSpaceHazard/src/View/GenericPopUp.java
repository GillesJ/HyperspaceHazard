package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GenericPopUp extends JDialog {

	private static final long serialVersionUID = -8753989167597490456L;

	private String content;
	private JLabel readout = new JLabel("text");
	private final GenericButton button = new GenericButton("OK");

	//constructor
	public GenericPopUp(JFrame parent, String title, String content) {
		super(parent, title, true);
		if (parent != null) {
			Dimension parentSize = parent.getSize(); 
			Point p = parent.getLocation(); 
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		this.setContent(content);
		getContentPane().add(makeDisplay());
		getContentPane().add(makeButtonPanel(), BorderLayout.SOUTH);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		pack();
		this.setVisible(true);
	}

	//methods
	public JPanel makeDisplay() {
		/** This method makes the JLabel and adds the necessary content to it, 
		 * namely the content specified in the constructor */
		JPanel panel = new JPanel();
		display(this.content);
		panel.add(readout);
		return panel;
	}

	public JPanel makeButtonPanel() {
		/** This method makes the button panel, 
		 * which in this case only contains one button, namely the OK button */
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//react to hitting the OK button
				dispose();
			}
		});
		panel.add(button);
		return panel;
	}

	private void display(String content) {
		/** This method adds the content to the label, while checking that the content is not zero */
		if (content.equals("")) {
			readout.setText(" ");
		} else {
			readout.setText(content);
		}
	}

	//setter
	public void setContent(String content) {
		this.content = content;
	}

	//getter
	public String getContent() {
		return this.content;
	}
}
