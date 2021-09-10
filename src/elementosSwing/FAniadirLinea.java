package elementosSwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.extras.components.FlatButton;

import elementosSwing.grafo2D.PanelGrafo;
import objetos.Linea;
import objetos.NombreOcupadoException;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class FAniadirLinea extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private Color color=Color.BLACK;
	private JRadioButton rdbtnTren = new JRadioButton("Tren");
	private JRadioButton rdbtnSubte = new JRadioButton("Subterr\u00E1neo");
	private JRadioButton rdbtnCole = new JRadioButton("Colectivo");

	/**
	 * Create the dialog.
	 */
	public FAniadirLinea() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setTitle("A\u00F1adir nueva l\u00EDnea");
		setBounds(100, 100, 400, 175);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Ingrese la informaci\u00F3n de la nueva l\u00EDnea");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel.gridwidth = 4;
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Nombre");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 1;
			gbc_lblNewLabel_1.gridy = 1;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.gridwidth = 2;
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 2;
			gbc_textField.gridy = 1;
			contentPanel.add(textField, gbc_textField);
			textField.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Color");
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_2.gridx = 1;
			gbc_lblNewLabel_2.gridy = 2;
			contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		}
		{
			JButton btnNewButton = new JButton("Elegir color");
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.gridwidth = 2;
			gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
			gbc_btnNewButton.gridx = 2;
			gbc_btnNewButton.gridy = 2;
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
					color=jcc.getColor();
					frameColor.dispose();
				});
				can.addActionListener(d -> {
					frameColor.dispose();
				});
				frameColor.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				
				frameColor.add(jcc, BorderLayout.NORTH);
				frameColor.add(can, BorderLayout.EAST);
				frameColor.add(ac,  BorderLayout.CENTER);
				frameColor.setVisible(true);
			});
			contentPanel.add(btnNewButton, gbc_btnNewButton);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Tipo");
			GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
			gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel_3.gridx = 1;
			gbc_lblNewLabel_3.gridy = 3;
			contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		}
		{
			GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
			gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnNewRadioButton.gridx = 2;
			gbc_rdbtnNewRadioButton.gridy = 3;
			contentPanel.add(rdbtnTren, gbc_rdbtnNewRadioButton);
		
			GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
			gbc_rdbtnNewRadioButton_1.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnNewRadioButton_1.gridx = 3;
			gbc_rdbtnNewRadioButton_1.gridy = 3;
			contentPanel.add(rdbtnSubte, gbc_rdbtnNewRadioButton_1);
		
			rdbtnCole.setSelected(true);
			GridBagConstraints gbc_rdbtnNewRadioButton_2 = new GridBagConstraints();
			gbc_rdbtnNewRadioButton_2.gridx = 4;
			gbc_rdbtnNewRadioButton_2.gridy = 3;
			contentPanel.add(rdbtnCole, gbc_rdbtnNewRadioButton_2);
			ButtonGroup rdbuttons=new ButtonGroup();
			rdbuttons.add(rdbtnTren);
			rdbuttons.add(rdbtnSubte);
			rdbuttons.add(rdbtnCole);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(e -> {
					this.dispose();
				});
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(e -> {
					try {
						Short tipo=0;
						if(rdbtnTren.isSelected()) tipo=Linea.TREN;
						else if(rdbtnSubte.isSelected()) tipo=Linea.SUBTERRANEO;
						else if(rdbtnCole.isSelected()) tipo=Linea.COLECTIVO;
						PanelInfo.setLinea(new Linea(textField.getText(), color, true, tipo));
					} catch (NombreOcupadoException e1) {
						//Nombre ocupado
					}
					PanelGrafo.repintarGrafo();
					PanelBusqueda.recargar();
					this.dispose();
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
