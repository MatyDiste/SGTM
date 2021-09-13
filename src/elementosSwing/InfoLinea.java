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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class InfoLinea extends JPanel implements InfoLineaInterface {
	private JTextField textField;
	private JTextField textField_2;  
	private Color color=Color.black;
	private JCheckBox chckbxNewCheckBox = new JCheckBox("Activa");
	private JRadioButton rdbtnCole = new JRadioButton("Colectivo");
	private JRadioButton rdbtnTren = new JRadioButton("Tren");
	private JRadioButton rdbtnSubte = new JRadioButton("Subterráneo");
	/**
	 * Create the panel.
	 */
	
	public InfoLinea(Linea l) {
		setLayout(new MigLayout("", "[trailing][grow][]", "[][][pref!,grow][][]"));
		
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
		
		JLabel lblNewLabel_4 = new JLabel("Tipo");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel_4, "cell 0 4");
		
		rdbtnCole.setEnabled(false);
		rdbtnCole.setSelected(l.getTipo()==Linea.COLECTIVO);
		add(rdbtnCole, "flowx,cell 1 4");
		
		rdbtnTren.setEnabled(false);
		rdbtnTren.setSelected(l.getTipo()==Linea.TREN);
		add(rdbtnTren, "cell 1 4");
		
		rdbtnSubte.setEnabled(false);
		rdbtnSubte.setSelected(l.getTipo()==Linea.SUBTERRANEO);
		add(rdbtnSubte, "cell 1 4");
		
		ButtonGroup btnGroup=new ButtonGroup();
		btnGroup.add(rdbtnCole);
		btnGroup.add(rdbtnTren);
		btnGroup.add(rdbtnSubte);

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
	@Override
	public Short getTipo() {
		if(rdbtnCole.isSelected()) return Linea.COLECTIVO;
		if(rdbtnTren.isSelected()) return Linea.TREN;
		else return Linea.SUBTERRANEO;
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
