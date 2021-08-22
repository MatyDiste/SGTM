package elementosSwing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatButton.ButtonType;

import objetos.Estacion;
import objetos.Linea;

public class PanelInformacion extends JPanel {
	private static PanelInformacion panel;
	
	private JPanel titulo, tipo, info, aniadir, editar, eliminar;
	private Short tipoSeleccionado=VACIO;
	private Boolean edit=false;
	private Linea linea;
	private Estacion estacion;
	private GridBagConstraints gbc=new GridBagConstraints();
	
	public static final Short VACIO=0;
	public static final Short ESTACION=1;
	public static final Short LINEA=2;
	
	public PanelInformacion() {
		super();
		panel=this;
		this.setPreferredSize(new Dimension(280,600));
		this.setMinimumSize(new Dimension(280,600));
		//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
		
		//jpanel titulo
		titulo=new JPanelBoxFactory(false);
		titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel textInformacion= new JLabel(" Información");
		textInformacion.setFont(new Font(null, Font.BOLD, 18));
		textInformacion.setIcon(new ImageIcon("./assets/info_icon.png"));
		textInformacion.setIconTextGap(10);
		textInformacion.setAlignmentX(Component.LEFT_ALIGNMENT);
		textInformacion.setSize(new Dimension(200,20));
		titulo.add(textInformacion);
		titulo.setSize(new Dimension(270,50));
		titulo.setMaximumSize(new Dimension(270,50));
		titulo.setPreferredSize(new Dimension(270,50));
		titulo.setMinimumSize(new Dimension(270,50));
		gbc.insets=new Insets(20,30,5,5);
		gbc.ipadx=30;
		gbc.ipady=20;
		gbc.anchor=GridBagConstraints.WEST;
		gbc.gridx=0;
		gbc.gridy=0;
		this.add(titulo, gbc);
		
		//jpanel tipo
		genLabelTipo();
		gbc.gridy=1;
		this.add(tipo, gbc);
		
		//jpanel info
		genLabelInfo();
		gbc.gridy=2;
		this.add(info, gbc);
		
		//jpanel aniadir
		aniadir=new JPanelBoxFactory(false);
		FlatButton btnAniadir=new FlatButton();
		FlatButton btnAniadirE=new FlatButton();
		FlatButton btnAniadirL=new FlatButton();
		btnAniadir.setText("Nuevo");
		btnAniadir.setIcon(new ImageIcon("./assets/add_icon.png"));
		btnAniadirE.setIcon(new ImageIcon("./assets/estacion_icon.png"));
		btnAniadirL.setIcon(new ImageIcon("./assets/linea_icon.png"));
		btnAniadir.setButtonType(ButtonType.roundRect);
		btnAniadirE.setButtonType(ButtonType.roundRect);
		btnAniadirL.setButtonType(ButtonType.roundRect);
		btnAniadir.setPreferredSize(new Dimension(150, 50));
		btnAniadir.setMinimumSize(new Dimension(150, 50));
		btnAniadir.setMaximumSize(new Dimension(150, 50));
		btnAniadirE.setPreferredSize(new Dimension(50, 50));
		btnAniadirE.setMinimumSize(new Dimension(50, 50));
		btnAniadirE.setMaximumSize(new Dimension(50, 50));
		btnAniadirL.setPreferredSize(new Dimension(50, 50));
		btnAniadirL.setMinimumSize(new Dimension(50, 50));
		btnAniadirL.setMaximumSize(new Dimension(50, 50));
		btnAniadirE.setVisible(false);
		btnAniadirL.setVisible(false);
		btnAniadir.addActionListener(e -> {
			btnAniadirE.setVisible(!btnAniadirE.isVisible());
			btnAniadirL.setVisible(!btnAniadirL.isVisible());
		});
		btnAniadirE.addActionListener(e -> {
			JDialog jd = new FAniadirEstacion();
			jd.setVisible(true);
		});
		btnAniadirL.addActionListener(e -> {
			JDialog jd = new FAniadirLinea();
			jd.setVisible(true);
		});
		aniadir.add(btnAniadir);
		aniadir.add(btnAniadirE);
		aniadir.add(btnAniadirL);
		aniadir.setAlignmentX(Component.LEFT_ALIGNMENT);
		aniadir.setSize(new Dimension(270,60));
		aniadir.setMaximumSize(new Dimension(270,60));
		aniadir.setMinimumSize(new Dimension(270,60));
		aniadir.setPreferredSize(new Dimension(270,60));
		gbc.gridy=3;
		this.add(aniadir, gbc);
		
		
	}
	
	private void genLabelTipo() {
		tipo=new JPanelBoxFactory(false);
		tipo.setAlignmentX(Component.LEFT_ALIGNMENT);
		tipo.setAlignmentY(Component.CENTER_ALIGNMENT);
		JLabel textTipo= new JLabel("No seleccionado");
		textTipo.setIconTextGap(10);
		switch(tipoSeleccionado) {
		case 1:
			textTipo.setText("Estación");
			textTipo.setIcon(new ImageIcon("./assets/estacion_icon.png"));
			textTipo.setFont(new Font(null, Font.PLAIN, 16));
			break;
		case 2:
			textTipo.setText("Línea");
			textTipo.setIcon(new ImageIcon("./assets/linea_icon.png"));
			textTipo.setFont(new Font(null, Font.PLAIN, 16));
			break;
		default:
			textTipo.setFont(new Font(null, Font.ITALIC, 16));
			break;
		}
		
		tipo.add(textTipo);
		
		this.addTipo();
		//this.revalidate();
	}
	
	private void genLabelInfo() {
		info = new JPanelBoxFactory(true);
		switch(tipoSeleccionado) {
		case 1:
			info.add(new MiTextFieldFactory(estacion.getId().toString(), edit));
			info.add(new MiTextFieldFactory(estacion.getNombre(), edit));
			info.add(new MiTextFieldFactory(estacion.getHorarioApertura().toString(), edit));
			info.add(new MiTextFieldFactory(estacion.getHorarioCierre().toString(), edit));
			info.add(new MiTextFieldFactory(estacion.getEstado(), edit));
			info.add(new MiTextFieldFactory(estacion.getFechaUltimoMantenimiento().toString(), edit));
			
			break;
		case 2:
			info.add(new MiTextFieldFactory(linea.getNombre(), edit));
			info.add(new MiTextFieldFactory(linea.getColor().toString(), edit));
			info.add(new MiTextFieldFactory(linea.estado(), edit));
			break;
		default:
			info.add(Box.createRigidArea(new Dimension(300, 180)));
			break;
		}
		this.addInfo();
		//this.revalidate();
		
	}
	
	private void addTipo() {
		gbc.insets=new Insets(20,30,5,5);
		gbc.ipadx=30;
		gbc.ipady=20;
		gbc.anchor=GridBagConstraints.WEST;
		gbc.gridx=0;
		gbc.gridy=1;
		this.add(tipo, gbc);
	}
	
	private void addInfo() {
		gbc.insets=new Insets(20,30,5,5);
		gbc.ipadx=30;
		gbc.ipady=20;
		gbc.anchor=GridBagConstraints.WEST;
		gbc.gridx=0;
		gbc.gridy=2;
		this.add(info, gbc);
	}
	
	public void quitarPaneles() {
		this.remove(tipo);
		this.remove(info);
		this.revalidate();
	}
	
	public static void setLinea(Linea l) {
		panel.quitarPaneles();
		panel.tipoSeleccionado=LINEA;
		panel.linea=l;
		panel.estacion=null;
		panel.genLabelTipo();
		panel.genLabelInfo();
		//panel.revalidate();
		//panel.repaint();
	}
	public static void setEstacion(Estacion e) {
		panel.quitarPaneles();
		panel.tipoSeleccionado=ESTACION;
		panel.estacion=e;
		panel.linea=null;
		panel.genLabelTipo();
		panel.genLabelInfo();
		//panel.revalidate();
		//panel.repaint();
	}
	public static void setVacio() {
		panel.quitarPaneles();
		panel.linea=null;
		panel.estacion=null;
		panel.tipoSeleccionado=VACIO;
		panel.genLabelTipo();
		panel.genLabelInfo();
		//panel.revalidate();
		//panel.repaint();
	}
	
	protected void paintComponent(Graphics g) {
		this.quitarPaneles();
		genLabelTipo();
		genLabelInfo();
		//panel.revalidate();
		super.paintComponent(g);
		//System.out.println("Repintando panelInfo");
	}
	
	public static void repintar() {
		panel.repaint();
	}
	
}



