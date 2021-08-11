package elementosSwing;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTextField;

class MiTextFieldFactory extends JTextField{
	public MiTextFieldFactory(String label, Boolean editar) {
		super(label);
		this.setEditable(editar);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setAlignmentY(Component.CENTER_ALIGNMENT);
		this.setMaximumSize(new Dimension(275,25));
		this.setEnabled(editar);
		this.setPreferredSize(new Dimension(280,22));
	}
}