package elementosSwing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTable;

import com.formdev.flatlaf.extras.components.FlatButton;


public class PanelBusqueda extends JPanel {
	
	private GridBagConstraints gbc = new GridBagConstraints();
	private JTable tabla;
	private FlatButton btnLineas= new FlatButton();
	private FlatButton btnEstaciones= new FlatButton();
	private Boolean listarEstaciones=true;
	
	public PanelBusqueda() {
		super();
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
		
		btnLineas.addActionListener(e -> {
			 listarEstaciones=false;
			 //TODO redibujar lista
		});
		btnEstaciones.addActionListener(e -> {
			listarEstaciones=true;
			//TODO redibujar lista
		});
		
		//Añadir btnLineas
		gbc.fill=GridBagConstraints.NONE;
		gbc.anchor=GridBagConstraints.CENTER;
		gbc.gridheight=1;
		gbc.gridwidth=2;
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.insets=new Insets(5,5,5,8);
		gbc.weightx=0;
		gbc.weighty=0;
		btnLineas.setPreferredSize(new Dimension(130,90));
		btnLineas.setText("LÍNEAS");
		this.add(btnLineas, gbc);
		
		//Añadir btnEstaciones
		gbc.fill=GridBagConstraints.NONE;
		gbc.anchor=GridBagConstraints.CENTER;
		gbc.gridheight=1;
		gbc.gridwidth=2;
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.insets=new Insets(5,5,5,8);
		gbc.weightx=0;
		gbc.weighty=0;
		btnEstaciones.setPreferredSize(new Dimension(130,90));
		btnEstaciones.setText("ESTACIONES");
		this.add(btnEstaciones, gbc);;
		
		//Añadir tabla
		tabla=new TablaEstaciones();
		gbc.fill=GridBagConstraints.BOTH;
		gbc.anchor=GridBagConstraints.CENTER;
		gbc.gridheight=2;
		gbc.gridwidth=1;
		gbc.gridx=2;
		gbc.gridy=0;
		gbc.insets=new Insets(5,5,5,5);
		gbc.weightx=1;
		gbc.weighty=1;
		this.add(tabla, gbc);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(listarEstaciones) {
			tabla=new TablaEstaciones();
		}
		else {
			tabla=new TablaLineas();
		}
		super.paintComponent(g);
	}
	
}
