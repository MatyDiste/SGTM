package elementosSwing;

import java.awt.Dimension;

import javax.swing.JTextField;

class MiTextFieldFactory extends JTextField{
	public MiTextFieldFactory(String label, Boolean editar) {
		super(label);
		this.setEditable(editar);
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setAlignmentY(CENTER_ALIGNMENT);
		this.setEnabled(editar);
		this.setPreferredSize(new Dimension(280,22));
	}
}