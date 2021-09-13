package elementosSwing;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import objetos.Linea;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.formdev.flatlaf.extras.components.FlatButton;

import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class InfoLineaEDIT extends JPanel implements InfoLineaInterface {
	private JTextField textField;
	private JTextField textField_2;
	private JCheckBox chckbxNewCheckBox = new JCheckBox("Activa");
	private Color color;
	private JPanel panel;
	private JRadioButton rdbtnCole = new JRadioButton("Colectivo");
	private JRadioButton rdbtnTren = new JRadioButton("Tren");
	private JRadioButton rdbtnSubte = new JRadioButton("Subterráneo");
	/**
	 * Create the panel.
	 */
	public InfoLineaEDIT(Linea l) {
		setLayout(new MigLayout("", "[trailing][][]", "[][][pref!,grow,baseline][][]"));
		
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
		add(lblNewLabel_1, "cell 0 2,alignx trailing,aligny center");
		
		JLabel lblNewLabel_2 =new JLabel("Estado");
		add(lblNewLabel_2, "cell 0 3,alignx trailing");
		
		textField_2 = new JTextField(l.estado());
		textField_2.setEditable(false);
		add(textField_2, "flowx,cell 1 3,alignx left");
		textField_2.setColumns(20);
		
		chckbxNewCheckBox.setEnabled(true);
		chckbxNewCheckBox.setSelected(l.estado().equals("ACTIVA"));
		chckbxNewCheckBox.addChangeListener(c -> {
			textField_2.setText(chckbxNewCheckBox.isSelected()? "ACTIVA" : "INACTIVA");
		});
		add(chckbxNewCheckBox, "cell 1 3");
		
		JButton btnNewButton = new JButton("Elegir color");
		btnNewButton.setEnabled(true);
		btnNewButton.addActionListener(e -> {
			JFrame frameColor=new JFrame("Elegir color");
			JColorChooser jcc=new JColorChooser();
			FlatButton can=new FlatButton();
			FlatButton ac=new FlatButton();
			frameColor.setLayout(new BorderLayout());
			frameColor.setBounds(150,150,700,500);
			ac.setText("Aceptar");
			can.setText("Cancelar");
			ac.setPreferredSize(new Dimension(100,25));
			ac.setMinimumSize(new Dimension(100,25));
			ac.setMaximumSize(new Dimension(100,25));
			can.setPreferredSize(new Dimension(100,25));
			can.setMinimumSize(new Dimension(100,25));
			can.setMaximumSize(new Dimension(100,25));
			ac.addActionListener(d -> {
				setColor(jcc.getColor());
				frameColor.dispose();
			});
			can.addActionListener(d -> {
				frameColor.dispose();
			});
			frameColor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			frameColor.add(jcc, BorderLayout.NORTH);
			frameColor.add(can, BorderLayout.EAST);
			frameColor.add(ac,  BorderLayout.CENTER);
			frameColor.setVisible(true);
		});
		add(btnNewButton, "flowx,cell 1 2,alignx left,aligny center");
		
		JLabel lblNewLabel_4 = new JLabel("Tipo");
		add(lblNewLabel_4, "cell 0 4");
		
		

		rdbtnSubte.setSelected(l.getTipo()==Linea.SUBTERRANEO);
		
		add(rdbtnCole, "flowx,cell 1 4");
		
		rdbtnTren.setSelected(l.getTipo()==Linea.TREN);
		add(rdbtnTren, "cell 1 4");
		
		rdbtnCole.setSelected(l.getTipo()==Linea.COLECTIVO);
		add(rdbtnSubte, "cell 1 4");
		
		ButtonGroup btnGroup=new ButtonGroup();
		btnGroup.add(rdbtnCole);
		btnGroup.add(rdbtnTren);
		btnGroup.add(rdbtnSubte);
		
		panel = new CuadroColor(l.getColor());
		add(panel, "cell 1 2,alignx left,aligny center");
		setColor(l.getColor());
	}
	
	@Override
	public String getNombre() {
		return textField.getText();
	}

	@Override
	public Color getColor() {
		return color;
	}
	public void setColor(Color c) {
		color=c;
		remove(panel);
		revalidate();
		panel=new CuadroColor(c);
		add(panel, "cell 1 2, alignx left");
		
		repaint();
	}

	@Override
	public Boolean getEstado() {
		return chckbxNewCheckBox.isSelected();
	}
	public Short getTipo() {
		if(rdbtnCole.isSelected()) return Linea.COLECTIVO;
		if(rdbtnTren.isSelected()) return Linea.TREN;
		else return Linea.SUBTERRANEO;
	}

}

class CuadroColor extends JPanel{
	public Color color;
	public CuadroColor(Color c) {
		super();
		this.setSize(new Dimension(20,20));
		this.setMinimumSize(new Dimension(20,20));
		color=c;
	}
	public void setColor(Color c) {
		color=c;
		repaint();
	}
	protected void paintComponent(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(color);
		g2d.fillRect(0, 0, 50, 50);
	}
}
