package elementosSwing;

import javax.swing.JPanel;

import elementosSwing.grafo2D.PanelGrafo;
import net.miginfocom.swing.MigLayout;
import objetos.Estacion;
import objetos.Linea;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;

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
	private Boolean admin;
	private JButton btnEdit =   new JButton("EDITAR");
	private JButton btnDelete = new JButton("ELIMINAR");
	private JButton btnAniadir = new JButton("NUEVO");
	private JButton btnAceptarEdit = new JButton("");
	private JButton btnDescartarEdit = new JButton("");
	private JButton btnEstacion = new JButton("");
	private JButton btnLinea = new JButton("");
	private JButton btnConexion = new JButton("");
	private JButton btnRecorrido = new JButton("Ingresar Recorrido");
	
	
	public PanelInfo(Boolean admin) {
		panel=this;
		this.admin=admin;
		setLayout(new MigLayout("", "[grow]", "[][grow][][][][]"));
		this.setMinimumSize(new Dimension(465,555));
		
		JLabel lblNewLabel = new JLabel("Información");
		lblNewLabel.setFont(new Font(null, Font.BOLD, 20));
		lblNewLabel.setIcon(new ImageIcon("./assets/info_icon.png"));
		add(lblNewLabel, "cell 0 0");
		
		JPanel panel = new JPanel();
		add(panel, "cell 0 1,grow");
		

		btnEdit.setIcon(new ImageIcon("./assets/edit_icon.png"));
		btnEdit.setEnabled(false);
		btnEdit.putClientProperty("JButton.buttonType", "roundRect");
		btnEdit.addActionListener(e ->{
			edit=!edit;
			btnAceptarEdit.setVisible(!btnAceptarEdit.isVisible());
			btnDescartarEdit.setVisible(!btnDescartarEdit.isVisible());
			btnEstacion.setVisible(false);
			btnLinea.setVisible(false);
			btnConexion.setVisible(false);
			genLabelInfo();
		});
		add(btnEdit, "flowx,cell 0 2,alignx left");
		
		btnDelete.setIcon(new ImageIcon("./assets/delete_icon.png"));
		btnDelete.setEnabled(false);
		btnDelete.putClientProperty("JButton.buttonType", "roundRect");
		btnDelete.addActionListener(e -> {
			edit=false;
			eliminar();
			setVacio();
			PanelBusqueda.recargar();
		});
		add(btnDelete, "cell 0 3,alignx left");
		
		btnAniadir.setIcon(new ImageIcon("./assets/add_icon.png"));
		btnAniadir.putClientProperty("JButton.buttonType", "roundRect");
		btnAniadir.setEnabled(this.admin);
		btnAniadir.addActionListener(e -> {
			edit=false;
			genLabelInfo();
			btnAceptarEdit.setVisible(false);
			btnDescartarEdit.setVisible(false);
			btnEstacion.setVisible(!btnEstacion.isVisible());
			btnLinea.setVisible(!btnLinea.isVisible());
			btnConexion.setVisible(!btnConexion.isVisible());
		});
		add(btnAniadir, "flowx,cell 0 4,alignx left");
		
		btnAceptarEdit.setIcon(new ImageIcon("./assets/check_icon.png"));
		btnAceptarEdit.putClientProperty("JButton.buttonType", "roundRect");
		btnAceptarEdit.setVisible(false);
		btnAceptarEdit.addActionListener(e -> {
			edit=false;
			btnAceptarEdit.setVisible(false);
			btnDescartarEdit.setVisible(false);
			guardarInfo();
			genLabelInfo();
			PanelBusqueda.recargar();
		});
		add(btnAceptarEdit, "cell 0 2");
		
		btnDescartarEdit.setIcon(new ImageIcon("./assets/cross_icon.png"));
		btnDescartarEdit.putClientProperty("JButton.buttonType", "roundRect");
		btnDescartarEdit.setVisible(false);
		btnDescartarEdit.addActionListener(e -> {
			edit=false;
			btnAceptarEdit.setVisible(false);
			btnDescartarEdit.setVisible(false);
			genLabelInfo();
			PanelBusqueda.recargar();
		});
		add(btnDescartarEdit, "cell 0 2");
		
		btnEstacion.setIcon(new ImageIcon("./assets/estacion_icon.png"));
		btnEstacion.putClientProperty("JButton.buttonType", "roundRect");
		btnEstacion.setToolTipText("Estación");
		btnEstacion.setVisible(false);
		btnEstacion.addActionListener(e -> {
			JDialog jd = new FAniadirEstacion();
			jd.setVisible(true);
		});
		add(btnEstacion, "cell 0 4");
		
		btnLinea.setIcon(new ImageIcon("./assets/linea_icon.png"));
		btnLinea.putClientProperty("JButton.buttonType", "roundRect");
		btnLinea.setToolTipText("Línea");
		btnLinea.setVisible(false);
		btnLinea.addActionListener(e -> {
			JDialog jd = new FAniadirLinea();
			jd.setVisible(true);
		});
		add(btnLinea, "cell 0 4");
		
		btnConexion.setIcon(new ImageIcon("./assets/conexion_icon.png"));
		btnConexion.putClientProperty("JButton.buttonType", "roundRect");
		btnConexion.setToolTipText("Conexión");
		btnConexion.setVisible(false);
		btnConexion.addActionListener(e -> {
			JFrame jf=new FAniadirConexion();
			jf.setVisible(true);
		});
		add(btnConexion, "cell 0 4");
		btnRecorrido.setIcon(new ImageIcon("./assets/recorrido_icon.png"));
		btnRecorrido.putClientProperty("JButton.buttonType", "roundRect");
		btnRecorrido.setToolTipText("Crear un nuevo recorrido y generar ticket");
		btnRecorrido.addActionListener(e -> {
			MainWindow.getInstance().setModoRecorrido();
			setVacio();
		});
		
		add(btnRecorrido, "cell 0 5");
		
		if(!this.admin) {
			btnAniadir.setToolTipText("No eres administrador");
			btnDelete.setToolTipText("No eres administrador");
			btnEdit.setToolTipText("No eres administrador");
			btnAceptarEdit.setToolTipText("No eres administrador");
		}
		
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
			informacion.add(Box.createRigidArea(new Dimension(300, 160)));
			break;
		}
		
		add(informacion, "cell 0 1,grow");
	}
	public void hideBtns() {
		btnAceptarEdit.setVisible(false);
		btnDescartarEdit.setVisible(false);
		btnEstacion.setVisible(false);
		btnLinea.setVisible(false);
		btnConexion.setVisible(false);
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
		switch(tipoSeleccionado) {
		case 1:
			InfoEstacionEDIT p=(InfoEstacionEDIT) informacion;
			estacion.setNombre(p.getNombre());
			estacion.setHorarioApertura(p.getHorarioApertura());
			estacion.setHorarioCierre(p.getHorarioCierre());
			if(p.getEstado()) estacion.setOperativa(); 
			else estacion.setMantenimiento(); 
			Estacion.actualizarDB(estacion);
			break;
		case 2:
			InfoLineaEDIT q=(InfoLineaEDIT) informacion;
			linea.setNombre(q.getNombre());
			linea.setColor(q.getColor());
			if(q.getEstado()) linea.activar(); 
			else linea.inactivar(); 
			linea.setTipo(q.getTipo());
			Linea.actualizarDB(linea);
			break;
		default:
			break;
		}
		PanelGrafo.repintarGrafo();
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
		panel.btnEdit.setEnabled(panel.admin);
		panel.btnDelete.setEnabled(panel.admin);
		panel.edit=false;
		panel.hideBtns();
		panel.tipoSeleccionado=LINEA;
		panel.linea=l;
		panel.estacion=null;
		panel.genLabelInfo();
		//panel.revalidate();
		l.select();
		panel.repaint();
	}
	public static void setEstacion(Estacion e) {
		//panel.quitarPanel();
		if(panel.tipoSeleccionado==ESTACION) panel.estacion.unselect();
		else if(panel.tipoSeleccionado==LINEA) panel.linea.unselect();
		panel.btnEdit.setEnabled(panel.admin);
		panel.btnDelete.setEnabled(panel.admin);
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
		panel.btnEdit.setEnabled(false);
		panel.btnDelete.setEnabled(false);
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
