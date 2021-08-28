package elementosSwing;

import javax.swing.JPanel;

import com.formdev.flatlaf.extras.components.FlatButton.ButtonType;

import elementosSwing.grafo2D.PanelGrafo;
import net.miginfocom.swing.MigLayout;
import objetos.Estacion;
import objetos.Linea;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class PanelInfo extends JPanel {
	private static PanelInfo panel;

	public static final Short VACIO=0;
	public static final Short ESTACION=1;
	public static final Short LINEA=2;
	
	private JPanel informacion;
	private Short tipoSeleccionado=VACIO;
	private Boolean edit=false;
	private Linea linea;
	private Estacion estacion;
	private JButton btnNewButton =   new JButton("EDITAR");
	private JButton btnNewButton_1 = new JButton("ELIMINAR");
	private JButton btnNewButton_2 = new JButton("NUEVO");
	private JButton btnNewButton_3 = new JButton("");
	private JButton btnNewButton_4 = new JButton("");
	private JButton btnNewButton_5 = new JButton("");
	private JButton btnNewButton_6 = new JButton("");
	private JButton btnNewButton_7 = new JButton("");
	
	
	public PanelInfo() {
		panel=this;
		setLayout(new MigLayout("", "[grow]", "[][grow][][][]"));
		
		JLabel lblNewLabel = new JLabel("Información");
		lblNewLabel.setFont(new Font(null, Font.BOLD, 20));
		lblNewLabel.setIcon(new ImageIcon("./assets/info_icon.png"));
		add(lblNewLabel, "cell 0 0");
		
		JPanel panel = new JPanel();
		add(panel, "cell 0 1,grow");
		

		btnNewButton.setIcon(new ImageIcon("./assets/edit_icon.png"));
		btnNewButton.setEnabled(false);
		btnNewButton.putClientProperty("JButton.buttonType", "roundRect");
		btnNewButton.addActionListener(e ->{
			edit=!edit;
			btnNewButton_3.setVisible(!btnNewButton_3.isVisible());
			btnNewButton_4.setVisible(!btnNewButton_4.isVisible());
			btnNewButton_5.setVisible(false);
			btnNewButton_6.setVisible(false);
			btnNewButton_7.setVisible(false);
			genLabelInfo();
		});
		add(btnNewButton, "flowx,cell 0 2,alignx left");
		
		btnNewButton_1.setIcon(new ImageIcon("./assets/delete_icon.png"));
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.putClientProperty("JButton.buttonType", "roundRect");
		btnNewButton_1.addActionListener(e -> {
			edit=false;
			eliminar();
			setVacio();
			PanelBusqueda.recargar();
		});
		add(btnNewButton_1, "cell 0 3,alignx left");
		
		btnNewButton_2.setIcon(new ImageIcon("./assets/add_icon.png"));
		btnNewButton_2.putClientProperty("JButton.buttonType", "roundRect");
		btnNewButton_2.addActionListener(e -> {
			edit=false;
			genLabelInfo();
			btnNewButton_3.setVisible(false);
			btnNewButton_4.setVisible(false);
			btnNewButton_5.setVisible(!btnNewButton_5.isVisible());
			btnNewButton_6.setVisible(!btnNewButton_6.isVisible());
			btnNewButton_7.setVisible(!btnNewButton_7.isVisible());
		});
		add(btnNewButton_2, "flowx,cell 0 4,alignx left");
		
		btnNewButton_3.setIcon(new ImageIcon("./assets/check_icon.png"));
		btnNewButton_3.putClientProperty("JButton.buttonType", "roundRect");
		btnNewButton_3.setVisible(false);
		btnNewButton_3.addActionListener(e -> {
			edit=false;
			btnNewButton_3.setVisible(false);
			btnNewButton_4.setVisible(false);
			guardarInfo();
			genLabelInfo();
			PanelBusqueda.recargar();
		});
		add(btnNewButton_3, "cell 0 2");
		
		btnNewButton_4.setIcon(new ImageIcon("./assets/cross_icon.png"));
		btnNewButton_4.putClientProperty("JButton.buttonType", "roundRect");
		btnNewButton_4.setVisible(false);
		btnNewButton_4.addActionListener(e -> {
			edit=false;
			btnNewButton_3.setVisible(false);
			btnNewButton_4.setVisible(false);
			genLabelInfo();
			PanelBusqueda.recargar();
		});
		add(btnNewButton_4, "cell 0 2");
		
		btnNewButton_5.setIcon(new ImageIcon("./assets/estacion_icon.png"));
		btnNewButton_5.putClientProperty("JButton.buttonType", "roundRect");
		btnNewButton_5.setToolTipText("Estación");
		btnNewButton_5.setVisible(false);
		btnNewButton_5.addActionListener(e -> {
			JDialog jd = new FAniadirEstacion();
			jd.setVisible(true);
		});
		add(btnNewButton_5, "cell 0 4");
		
		btnNewButton_6.setIcon(new ImageIcon("./assets/linea_icon.png"));
		btnNewButton_6.putClientProperty("JButton.buttonType", "roundRect");
		btnNewButton_6.setToolTipText("Línea");
		btnNewButton_6.setVisible(false);
		btnNewButton_6.addActionListener(e -> {
			JDialog jd = new FAniadirLinea();
			jd.setVisible(true);
		});
		add(btnNewButton_6, "cell 0 4");
		
		btnNewButton_7.setIcon(new ImageIcon("./assets/conexion_icon.png"));
		btnNewButton_7.putClientProperty("JButton.buttonType", "roundRect");
		btnNewButton_7.setToolTipText("Conexión");
		btnNewButton_7.setVisible(false);
		btnNewButton_7.addActionListener(e -> {
			JFrame jf=new FAniadirConexion();
			jf.setVisible(true);
		});
		add(btnNewButton_7, "cell 0 4");
		
		genLabelInfo();

	}
	
	private void genLabelInfo() {
		panel.quitarPanel();
		switch(tipoSeleccionado) {
		case 1:
			//ESTACION
			informacion= (!edit)? new InfoEstacion(estacion) : new InfoEstacionEDIT(estacion);
			break;
		case 2:
			//LINEA
			informacion= (!edit)? new InfoLinea(linea) : new InfoLineaEDIT(linea);
			break;
		default:
			//VACIO
			informacion=new JPanel();
			informacion.setLayout(new BoxLayout(informacion, BoxLayout.PAGE_AXIS));
			JLabel nosel=new JLabel("No seleccionado");
			nosel.setFont(new Font(null, Font.ITALIC, 16));
			informacion.add(nosel);
			informacion.add(Box.createRigidArea(new Dimension(300, 180)));
			break;
		}
		
		add(informacion, "cell 0 1,grow");
	}
	public void hideBtns() {
		btnNewButton_3.setVisible(false);
		btnNewButton_4.setVisible(false);
		btnNewButton_5.setVisible(false);
		btnNewButton_6.setVisible(false);
		btnNewButton_7.setVisible(false);
	}
	public void quitarPanel() {
		if(informacion != null) {
			this.remove(informacion);
			//System.out.println("Elimino informacion");
		}
		this.revalidate();
	}
	
	private void guardarInfo() {
		//TODO guardar info de la estacion o linea y actualizar con genLabelInfo()
	}
	
	private void eliminar() {
		switch(tipoSeleccionado) {
		case 1:
			//System.out.println("Eliminando estacion...");
			estacion.unselect();
			estacion.eliminar();
			break;
		case 2:
			linea.unselect();
			linea.eliminar();
			break;
		default:
			break;
		}
		PanelGrafo.repintarGrafo();
	}
	
	public static void setLinea(Linea l) {
		if(panel.tipoSeleccionado==ESTACION) panel.estacion.unselect();
		else if(panel.tipoSeleccionado==LINEA) panel.linea.unselect();
		panel.btnNewButton.setEnabled(true);
		panel.btnNewButton_1.setEnabled(true);
		panel.edit=false;
		panel.hideBtns();
		panel.tipoSeleccionado=LINEA;
		panel.linea=l;
		panel.estacion=null;
		panel.genLabelInfo();
		//panel.revalidate();
		panel.repaint();
		l.select();
	}
	public static void setEstacion(Estacion e) {
		//panel.quitarPanel();
		if(panel.tipoSeleccionado==ESTACION) panel.estacion.unselect();
		else if(panel.tipoSeleccionado==LINEA) panel.linea.unselect();
		panel.btnNewButton.setEnabled(true);
		panel.btnNewButton_1.setEnabled(true);
		panel.edit=false;
		panel.hideBtns();
		panel.tipoSeleccionado=ESTACION;
		panel.estacion=e;
		panel.linea=null;
		panel.genLabelInfo();
		//panel.revalidate();
		e.select();
		panel.repaint();
	}
	public static void setVacio() {
		//panel.quitarPanel();
		if(panel.tipoSeleccionado==ESTACION) panel.estacion.unselect();
		else if(panel.tipoSeleccionado==LINEA) panel.linea.unselect();
		panel.btnNewButton.setEnabled(false);
		panel.btnNewButton_1.setEnabled(false);
		panel.edit=false;
		panel.hideBtns();
		panel.linea=null;
		panel.estacion=null;
		panel.tipoSeleccionado=VACIO;
		panel.genLabelInfo();
		//panel.revalidate();
		panel.repaint();
	}
	
	public static void repintar() {
		panel.repaint();
	}
}
