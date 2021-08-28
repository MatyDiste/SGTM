package elementosSwing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListDataListener;

import elementosSwing.grafo2D.PanelGrafo;
import net.miginfocom.swing.MigLayout;
import objetos.Conexion;
import objetos.Estacion;
import objetos.Linea;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

public class FAniadirConexion extends JFrame {
	private static FAniadirConexion frame;
	
	private JPanel contentPane;
	private JPanel panelContent=new JPanel();
	private JScrollPane scrollPane = new JScrollPane(panelContent);
	private Stack<PanelConexion> listPC=new Stack<PanelConexion>();
	private JLabel lblNewLabel_2 = new JLabel("Esta l\u00EDnea ya tiene un recorrido asignado");
	private JComboBox<Linea> comboBox = new JComboBox<Linea>();
	/**
	 * Create the frame.
	 */
	public FAniadirConexion() {
		frame=this;
		setTitle("Nuevo recorrido");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][grow][]", "[][][grow][]"));
		
		JLabel lblNewLabel = new JLabel("Ingrese los datos para el nuevo recorrido");
		contentPane.add(lblNewLabel, "cell 0 0");
		
		JLabel lblNewLabel_1 = new JLabel("Seleccione L\u00EDnea:");
		contentPane.add(lblNewLabel_1, "cell 1 0,alignx trailing");
		
		
		Linea[] aux= new Linea[Linea.listLineas.size()];
		comboBox.setModel(new DefaultComboBoxModel<Linea>(Linea.listLineas.toArray(aux)));
		comboBox.setMaximumRowCount(10);
		comboBox.addActionListener(e -> {
			actualizar();
		});
		contentPane.add(comboBox, "cell 2 0,alignx left");
		
		
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setToolTipText("Esta l\u00EDnea ya tiene un recorrido asignado, si sigue puede reemplazar el recorrido actual");
		lblNewLabel_2.setVisible(!((Linea)comboBox.getSelectedItem()).getListConexiones().isEmpty());
		contentPane.add(lblNewLabel_2, "cell 3 0");
		
		JButton btnNewButton = new JButton("");
		JButton btnNewButton_1 = new JButton("");
		btnNewButton.setToolTipText("A\u00F1adir conexi\u00F3n");
		btnNewButton.setIcon(new ImageIcon("./assets/add_icon.png"));
		btnNewButton.putClientProperty("JButton.buttonType", "roundRect");
		btnNewButton.addActionListener(e -> {
			addConexion(false);
			btnNewButton_1.setEnabled(true);
		});
		contentPane.add(btnNewButton, "flowx,cell 0 1");
		
		btnNewButton_1.putClientProperty("JButton.buttonType", "roundRect");
		btnNewButton_1.setToolTipText("Eliminar \u00FAltima conexi\u00F3n");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setIcon(new ImageIcon("./assets/delete_icon.png"));
		btnNewButton_1.addActionListener(e -> {
			deleteConexion();
			if(listPC.size()==2) btnNewButton_1.setEnabled(false);
		});
		contentPane.add(btnNewButton_1, "cell 0 1");
		
		panelContent.setLayout(new MigLayout("","","[grow]"));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		//scrollPane.setLayout(new ScrollPaneLayout());
		addConexion(true); addConexion(false);
		contentPane.add(scrollPane, "flowx,cell 0 2 4 1, grow");
		
		JButton btnNewButton_3 = new JButton("Cancelar");
		btnNewButton_3.addActionListener(e -> {
			this.dispose();
		});
		
		JLabel lblNewLabel_3 = new JLabel("Algunos de los datos est\u00E1 mal");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setToolTipText("El recuadro que tenga informaci\u00F3n incorrecta estar\u00E1 resaltado");
		lblNewLabel_3.setVisible(false);
		contentPane.add(lblNewLabel_3, "cell 2 3,alignx trailing");
		contentPane.add(btnNewButton_3, "flowx,cell 3 3,alignx trailing");
		
		JButton btnNewButton_2 = new JButton("Aceptar");
		btnNewButton_2.addActionListener(e -> {
			try {
				listPC.forEach(p -> p.check());
				if(lblNewLabel_2.isVisible()) {
					//La línea ya tenía un recorrido, remover el recorrido anterior
					((Linea)comboBox.getSelectedItem()).quitarRecorrido();
				}
				for(int i=0; i<listPC.size()-1; i++) {
					PanelConexion p1=listPC.elementAt(i);
					PanelConexion p2=listPC.elementAt(i+1);
					Estacion e1=p1.getEstacion();
					Estacion e2=p2.getEstacion();
					Linea lin=(Linea)this.comboBox.getSelectedItem();
					Double dist=p2.getDistancia();
					Double dur=p2.getDuracion();
					Integer pasajeros=p2.getPasajeros();
					Double costo=p2.getCosto();
					new Conexion(e1,e2,lin,dist,dur,pasajeros,costo);
				}
				this.dispose();
				PanelGrafo.repintarGrafo();
				PanelBusqueda.recargar();
			}
			catch(NumberFormatException exc) {
				lblNewLabel_3.setVisible(true);
				listPC.forEach(p -> p.check());
			}
		});
		contentPane.add(btnNewButton_2, "cell 3 3,alignx trailing");
		getRootPane().setDefaultButton(btnNewButton_2);
	}
	
	public void addConexion(Boolean esInicial) {
		PanelConexion nuevo= new PanelConexion(esInicial);
		panelContent.add(nuevo);
		listPC.add(nuevo);
		panelContent.revalidate();
		this.repaint();
	}
	public void deleteConexion() {
		PanelConexion ultimo=listPC.pop();
		panelContent.remove(ultimo);
		panelContent.revalidate();
		this.repaint();
	}
	public void actualizar() {
		lblNewLabel_2.setVisible(!((Linea)comboBox.getSelectedItem()).getListConexiones().isEmpty());
		repaint();
	}
	public static void repintar() {
		frame.repaint();
	}

}

class PanelConexion extends JPanel{
	private JComboBox<Estacion> comboEstacion=new JComboBox<Estacion>();
	private JTextField distancia=new JTextField("0.0");
	private JTextField duracion=new JTextField("5.0");
	private JTextField cantPasajeros=new JTextField("0");
	private JTextField costo=new JTextField("0");
	
	public PanelConexion(Boolean esInicial) {
		this.setLayout(new MigLayout("","",""));
		if(esInicial) this.setBorder(new LineBorder(null)); 
		else this.setBorder(BorderFactory.createTitledBorder("Conexión"));
		JLabel icono=new JLabel();
		icono.setIcon(esInicial? new ImageIcon("./assets/start_icon.png") : new ImageIcon("./assets/flecha_icon.png"));
		add(icono);
		
		Estacion[] aux=new Estacion[Estacion.listEstaciones.size()];
		comboEstacion.setModel(new DefaultComboBoxModel<Estacion>(Estacion.listEstaciones.toArray(aux)));
		add(comboEstacion, "wrap");
		
		if(esInicial) {
			
		}
		else {
			
			JLabel t1=new JLabel("Distancia (km):");
			distancia.setColumns(10);
			add(t1, "alignx trailing"); add(distancia, "wrap");
			
			JLabel t2=new JLabel("Duración (minutos):");
			duracion.setColumns(10);
			add(t2, "alignx trailing"); add(duracion, "wrap");
			
			JLabel t3=new JLabel("Cantidad máx. pasajeros:");
			cantPasajeros.setColumns(10);
			add(t3, "alignx trailing"); add(cantPasajeros, "wrap");
			
			JLabel t4=new JLabel("Costo:");
			costo.setColumns(10);
			add(t4, "alignx trailing"); add(costo, "wrap");
		}
	}
	
	public Estacion getEstacion() {
		return (Estacion)comboEstacion.getSelectedItem();
	}
	public Double getDistancia() throws NumberFormatException{
		return Double.parseDouble(distancia.getText());
	}
	public Double getDuracion() throws NumberFormatException{
		return Double.parseDouble(duracion.getText());
	}
	public Integer getPasajeros() throws NumberFormatException{
		return Integer.parseInt(cantPasajeros.getText());
	}
	public Double getCosto() throws NumberFormatException{
		return Double.parseDouble(costo.getText());
	}
	public Boolean check() {
		try {
			this.getEstacion();
			this.getDistancia();
			this.getDuracion();
			this.getPasajeros();
			this.getCosto();
			return true;
		} 
		catch(NumberFormatException exc) {
			this.setBorder(new LineBorder(Color.RED));
			this.setBorder(BorderFactory.createTitledBorder(this.getBorder(), "Conexión"));
			this.repaint();
			return false;
		}
	}
	
	
}
