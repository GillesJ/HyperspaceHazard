package View;

import javax.swing.JButton;

public class GenericButton extends JButton {

	private static final long serialVersionUID = -7946982448355071282L;

	//constructor
	public GenericButton(String button) {
		super(button);
		this.setActionCommand(button);
	}
}
