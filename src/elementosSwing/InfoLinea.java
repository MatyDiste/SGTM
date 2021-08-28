package elementosSwing;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import objetos.Linea;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class InfoLinea extends JPanel implements InfoLineaInterface {
	private JTextField textField;
	private JTextField textField_2;  
	private Color color=Color.black;
	JCheckBox chckbxNewCheckBox = new JCheckBox("Activa");
	/**
	 * Create the panel.
	 */
	
	public InfoLinea(Linea l) {
		setLayout(new MigLayout("", "[][grow][]", "[][][pref!,grow][]"));
		
		JLabel lblNewLabel_3 = new JLabel("L\u00CDNEA");
		lblNewLabel_3.setFont(new Font(null,Font.BOLD, 16));
		lblNewLabel_3.setIcon(new ImageIcon("./assets/linea_icon.png"));
		add(lblNewLabel_3, "cell 1 0");
		
		
		JLabel lblNewLabel = new JLabel("Nombre");
		add(lblNewLabel, "cell 0 1,alignx trailing");
		textField = new JTextField(l.getNombre());
		textField.setEditable(false);
		add(textField, "cell 1 1,alignx left");
		textField.setColumns(20);
		
		JLabel lblNewLabel_1 = new JLabel("Color");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		add(lblNewLabel_1, "cell 0 2,alignx trailing,aligny center");
		
		JLabel lblNewLabel_2 =new JLabel("Estado");
		add(lblNewLabel_2, "cell 0 3,alignx trailing");
		
		textField_2 = new JTextField(l.estado());
		textField_2.setEditable(false);
		add(textField_2, "flowx,cell 1 3,alignx left");
		textField_2.setColumns(20);
		
		chckbxNewCheckBox.setEnabled(false);
		chckbxNewCheckBox.setSelected(l.estado().equals("ACTIVA"));
		add(chckbxNewCheckBox, "cell 1 3");
		
		JButton btnNewButton = new JButton("Elegir color");
		btnNewButton.setEnabled(false);
		add(btnNewButton, "flowx,cell 1 2,alignx left,aligny top");
		
		JPanel panel = new CuadradoColor(l.getColor());
		add(panel, "cell 1 2,alignx left,aligny center");

	}

	@Override
	public String getNombre() {
		return textField.getText();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public Boolean getEstado() {
		return chckbxNewCheckBox.isSelected();
	}
	

}

class CuadradoColor extends JPanel{
	private Color color;
	public CuadradoColor(Color c) {
		super();
		this.setSize(new Dimension(20,20));
		this.setMinimumSize(new Dimension(20,20));
		color=c;
	}
	protected void paintComponent(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(color);
		g2d.fillRect(0, 0, 50, 50);
	}
}
