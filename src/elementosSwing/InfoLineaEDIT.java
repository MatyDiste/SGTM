package elementosSwing;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import objetos.Linea;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class InfoLineaEDIT extends InfoLinea {
	private JTextField textField;
	private JTextField textField_2;
	private JCheckBox chckbxNewCheckBox = new JCheckBox("Activa");

	/**
	 * Create the panel.
	 */
	public InfoLineaEDIT(Linea l) {
		setLayout(new MigLayout("", "[][grow][]", "[][grow][]"));
		
		JLabel lblNewLabel = new JLabel("Nombre");
		add(lblNewLabel, "cell 0 0,alignx trailing");
		
		textField = new JTextField(l.getNombre());
		textField.setEditable(false);
		add(textField, "cell 1 0,alignx left");
		textField.setColumns(20);
		
		JLabel lblNewLabel_1 = new JLabel("Color");
		add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		JPanel panel = new CuadroColor(l.getColor());
		add(panel, "flowx,cell 1 1,alignx left,aligny top");
		
		JLabel lblNewLabel_2 =new JLabel("Estado");
		add(lblNewLabel_2, "cell 0 2,alignx trailing");
		
		textField_2 = new JTextField(l.estado());
		textField_2.setEditable(false);
		add(textField_2, "flowx,cell 1 2,alignx left");
		textField_2.setColumns(20);
		
		chckbxNewCheckBox.setEnabled(false);
		chckbxNewCheckBox.setSelected(l.estado().equals("ACTIVA"));
		add(chckbxNewCheckBox, "cell 1 2");
		
		JButton btnNewButton = new JButton("Elegir color");
		btnNewButton.setEnabled(false);
		add(btnNewButton, "cell 1 1");

	}

}

class CuadroColor extends JPanel{
	public Color color;
	public CuadroColor(Color c) {
		super();
		color=c;
	}
	public void setColor(Color c) {
		color=c;
		repaint();
	}
	protected void paintComponent(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(color);
		g2d.fillRect(0, 0, 25, 25);
	}
}
