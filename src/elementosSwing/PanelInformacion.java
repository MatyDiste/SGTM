package elementosSwing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatButton.ButtonType;

import objetos.Estacion;
import objetos.Linea;

public class PanelInformacion extends JPanel {

	private JPanel titulo, tipo, info, aniadir, editar, eliminar;
	private Short tipoSeleccionado=VACIO;
	private Boolean edit=false;
	private Linea linea;
	private Estacion estacion;
	
	public static final Short VACIO=0;
	public static final Short ESTACION=1;
	public static final Short LINEA=2;
	
	public PanelInformacion(Container c) {
		super();
		this.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
		this.setVisible(true);
		
		//jpanel titulo
		titulo=new JPanelBoxFactory(false);
		titulo.setAlignmentX(LEFT_ALIGNMENT);
		titulo.setAlignmentY(CENTER_ALIGNMENT);
		JLabel textInformacion= new JLabel(" Información");
		textInformacion.setFont(new Font(null, Font.BOLD, 18));
		textInformacion.setIcon(new ImageIcon("./assets/info_icon.png"));
		textInformacion.setIconTextGap(10);
		textInformacion.setAlignmentX(LEFT_ALIGNMENT);
		textInformacion.setAlignmentY(CENTER_ALIGNMENT);
		titulo.add(textInformacion);
		this.add(titulo);
		
		//jpanel tipo
		genLabelTipo();
		this.add(tipo);
		
		//jpanel info
		genLabelInfo();
		this.add(info);
		
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
		btnAniadir.setPreferredSize(new Dimension(180, 40));
		btnAniadirE.setPreferredSize(new Dimension(40, 40));
		btnAniadirL.setPreferredSize(new Dimension(40, 40));
		btnAniadirE.setVisible(false);
		btnAniadirL.setVisible(false);
		btnAniadir.addActionListener(e -> {
			btnAniadirE.setVisible(!btnAniadirE.isVisible());
			btnAniadirL.setVisible(!btnAniadirL.isVisible());
		});
		btnAniadirE.addActionListener(e -> {
			setEstacion(popupAniadirEstacion());
		});
		btnAniadirL.addActionListener(e -> {
			setLinea(popupAniadirLinea());
		});
		
		
		
	}
	
	private void genLabelTipo() {
		tipo=new JPanelBoxFactory(false);
		tipo.setAlignmentX(LEFT_ALIGNMENT);
		tipo.setAlignmentY(CENTER_ALIGNMENT);
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
		
	}
	
	private void genLabelInfo() {
		info = new JPanelBoxFactory(true);
		switch(tipoSeleccionado) {
		case 1:
			info.add(new MiTextFieldFactory(estacion.getID().toString(), edit));
			info.add(new MiTextFieldFactory(estacion.getNombre(), edit));
			info.add(new MiTextFieldFactory(estacion.getHorarioApertura().toString(), edit));
			info.add(new MiTextFieldFactory(estacion.getHorarioCierre().toString(), edit));
			info.add(new MiTextFieldFactory("Estado                    ", edit));
			info.add(new MiTextFieldFactory("Fecha último mantenimiento", edit));
			
			break;
		case 2:
			info.add(new MiTextFieldFactory("Nombre  ", edit));
			info.add(new MiTextFieldFactory("Color   ", edit));
			info.add(new MiTextFieldFactory("Activo  ", edit));
			break;
		default:
			info.add(Box.createRigidArea(new Dimension(300, 180)));
			break;
		}
		
	}
	
	public void setLinea(Linea l) {
		
		tipoSeleccionado=LINEA;
		linea=l;
		estacion=null;
		genLabelTipo();
		genLabelInfo();
	}
	public void setEstacion(Estacion e) {
		
		tipoSeleccionado=ESTACION;
		estacion=e;
		linea=null;
		genLabelTipo();
		genLabelInfo();
	}
	public void setVacio() {
		
		linea=null;
		estacion=null;
		tipoSeleccionado=VACIO;
		genLabelTipo();
		genLabelInfo();
	}
	private Linea popupAniadirLinea() {
		
	}
	private Estacion popupAniadirEstacion() {
		
	}
}



