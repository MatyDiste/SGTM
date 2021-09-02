package elementosSwing;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import elementosSwing.grafo2D.PanelGrafo;
import net.miginfocom.swing.MigLayout;
import objetos.Estacion;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.JRadioButton;

public class PanelRecorrido extends JPanel {
	public static PanelRecorrido pr;
	
	private JPanel panelEstaciones = new JPanel();
	private PanelEstacion pedesde=new PanelEstacion();
	private PanelEstacion pehasta=new PanelEstacion();
	private PanelEstacion panelSeleccionado=pedesde;
	private JButton btnRecorridos = new JButton("Comprar ticket");
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	/**
	 * Create the panel.
	 */
	public PanelRecorrido() {
		pr=this;
		setLayout(new BorderLayout(5, 5));
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.setIcon(null);
		btnAtras.setToolTipText("Volver al cuadro anterior");
		btnAtras.addActionListener(e -> {
			MainWindow.getInstance().unsetModoRecorrido();
		});
		add(btnAtras, BorderLayout.NORTH);
		
		panelEstaciones.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][]"));
		
		JLabel lblNewLabel_3 = new JLabel("Nuevo recorrido");
		lblNewLabel_3.setIcon(new ImageIcon("./assets/recorrido_icon.png"));
		lblNewLabel_3.setFont(new Font(null, Font.PLAIN, 16));
		panelEstaciones.add(lblNewLabel_3, "cell 0 0");
		
		JLabel lblDesde = new JLabel("Desde:");
		lblDesde.setFont(new Font(null, Font.BOLD, 14));
		panelEstaciones.add(lblDesde, "cell 0 1,alignx center,aligny center");
		panelEstaciones.add(pedesde, "cell 0 2 2 1,alignx center,aligny top");
		
		JLabel lblHasta = new JLabel("Hasta:");
		lblHasta.setFont(new Font(null, Font.BOLD, 14));
		panelEstaciones.add(lblHasta, "cell 0 3,alignx center,aligny center");
		panelEstaciones.add(pehasta, "cell 0 4 2 1,alignx center,aligny top");
		add(panelEstaciones, BorderLayout.CENTER);
		JRadioButton rdbtnNewRadioButton = new JRadioButton("M\u00E1s barato");
		rdbtnNewRadioButton.setEnabled(false);
		panelEstaciones.add(rdbtnNewRadioButton, "cell 0 6");
		
		textField = new JTextField();
		textField.setEditable(false);
		panelEstaciones.add(textField, "cell 1 6,growx");
		textField.setColumns(10);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("M\u00E1s r\u00E1pido");
		rdbtnNewRadioButton_1.setEnabled(false);
		panelEstaciones.add(rdbtnNewRadioButton_1, "cell 0 7");
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		panelEstaciones.add(textField_1, "cell 1 7,growx");
		textField_1.setColumns(10);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Menor distancia");
		rdbtnNewRadioButton_2.setEnabled(false);
		panelEstaciones.add(rdbtnNewRadioButton_2, "cell 0 8");
		
		ButtonGroup radioButtons=new ButtonGroup();
		radioButtons.add(rdbtnNewRadioButton);
		radioButtons.add(rdbtnNewRadioButton_1);
		radioButtons.add(rdbtnNewRadioButton_2);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		panelEstaciones.add(textField_2, "cell 1 8,growx");
		textField_2.setColumns(10);
		
		btnRecorridos.setEnabled(false);
		btnRecorridos.setToolTipText("Comprar ticket para el recorrido seleccionado");
		btnRecorridos.setIcon(new ImageIcon("./assets/buy_icon.png"));
		btnRecorridos.putClientProperty("JButton.buttonType", "roundRect");
		
		JPanel panel_1 = new JPanel();
		panel_1.add(btnRecorridos);
		add(panel_1, BorderLayout.SOUTH);

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if((e.getComponent()) instanceof PanelEstacion) {
					panelSeleccionado.unselect();
					panelSeleccionado=(PanelEstacion)e.getComponent();
					panelSeleccionado.select();
				}
			}
		});
		
		pedesde.select();
	}
	
	public Boolean checkEstaciones() {
		return pedesde.getEstacion()!=null &&
				   pehasta.getEstacion()!=null &&
				   !pedesde.getEstacion().equals(pehasta.getEstacion());
	}
	
	public static void setEstacion(Estacion e) {
		
		if(pr.panelSeleccionado.equals(pr.pedesde)) {
			//es el primer panel
			pr.pedesde.unselectEstacion();
			pr.setBorder(new EmptyBorder(0, 0, 0, 0));
			
			pr.panelEstaciones.remove(pr.pedesde);
			pr.panelEstaciones.revalidate();
			pr.pedesde=new PanelEstacion(e);
			pr.panelEstaciones.add(pr.pedesde, "cell 0 2 2 1,alignx center,aligny top");
			pr.repaint();
			pr.panelSeleccionado=pr.pehasta;
			pr.panelSeleccionado.select();
		}
		else {
			//es el segundo panel
			pr.pehasta.unselectEstacion();
			
			pr.panelEstaciones.remove(pr.pehasta);
			pr.panelEstaciones.revalidate();
			pr.pehasta=new PanelEstacion(e);
			pr.panelEstaciones.add(pr.pehasta, "cell 0 4 2 1,alignx center,aligny top");
			pr.repaint();
			pr.panelSeleccionado=pr.pedesde;
			pr.setBorder(new LineBorder(Color.CYAN, 1));
		}
		pr.checkBtns();
		PanelGrafo.repintarGrafo();
	}
	
	public void checkBtns() {
		if(checkEstaciones()) {
			//Si los paneles desde y hasta son validos y son distintas estaciones...
			btnRecorridos.setEnabled(true);	
			generarRecorridos();
		}
	}
	
	public void generarRecorridos() {
		//TODO generar recorridos, setear radiocheckboxes y completar campos de informacion
		//También habilitar el botón ComprarTicket
	}
	
	public static Boolean primerEstacion() {
		return pr.panelSeleccionado.equals(pr.pedesde);
	}

}

/*----------------------------------------------------*/
/*----------------------------------------------------*/
/*----------------------------------------------------*/

class PanelEstacion extends JPanel {
	private Estacion estacion;
	private JTextField txtDefault;
	private JTextField txtDefault_1;
	private Boolean selected=false;
	
	/**
	 * Panel tipo tarjeta que muestra la estación y su información
	 */
	public PanelEstacion(Estacion e) {
		this.estacion=e;
		setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		JLabel lblNewLabel = new JLabel("Estación "+e.getNombre());
		lblNewLabel.setIcon(new ImageIcon("./assets/estacion_icon.png"));
		add(lblNewLabel, "cell 0 0 2 1,alignx center");
		
		JLabel lblNewLabel_1 = new JLabel("Abierto desde: ");
		add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		txtDefault_1 = new JTextField();
		txtDefault_1.setText(estacion.getHorarioApertura().format(DateTimeFormatter.ISO_LOCAL_TIME));
		txtDefault_1.setEditable(false);
		add(txtDefault_1, "cell 1 1,alignx left");
		txtDefault_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Hasta las: ");
		add(lblNewLabel_2, "cell 0 2,alignx trailing");
		
		txtDefault = new JTextField();
		txtDefault.setText(estacion.getHorarioCierre().format(DateTimeFormatter.ISO_LOCAL_TIME));
		txtDefault.setEditable(false);
		add(txtDefault, "cell 1 2,alignx left");
		txtDefault.setColumns(10);

		this.setBorder(new LineBorder(Color.BLACK, 2));
		e.select();
	}
	
	public PanelEstacion() {
		this.estacion=null;
		setLayout(new MigLayout("","[][grow]","[][][][]"));
		JLabel lblNewLabel = new JLabel("Estaci\u00F3n ");
		lblNewLabel.setIcon(new ImageIcon("./assets/estacion_icon.png"));
		add(lblNewLabel, "cell 0 0 2 1,alignx center");
		
		JLabel lblNewLabel_1 = new JLabel("Abierto desde: ");
		add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		txtDefault_1 = new JTextField("No seleccionado");
		txtDefault_1.setFont(new Font(null, Font.ITALIC, 11));
		txtDefault_1.setEditable(false);
		add(txtDefault_1, "cell 1 1,alignx left");
		txtDefault_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Hasta las: ");
		add(lblNewLabel_2, "cell 0 2,alignx trailing");
		
		txtDefault = new JTextField("No seleccionado");
		txtDefault.setFont(new Font(null, Font.ITALIC, 11));
		txtDefault.setEditable(false);
		add(txtDefault, "cell 1 2,alignx left");
		txtDefault.setColumns(10);
		this.setBorder(new LineBorder(Color.RED, 3));
	}
	
	public void select() {
		selected=true;
		this.setBorder(new LineBorder(Color.CYAN, 4));
	}
	public void unselect() {
		selected=false;
		this.setBorder((estacion==null)? new LineBorder(Color.RED, 3) : new LineBorder(Color.BLACK, 2));
	}
	public Boolean isSelected() {
		return selected;
	}
	public Boolean esValido() {
		return estacion!=null;
	}
	public void selectEstacion() {
		if(estacion!=null) estacion.select();
	}
	public void unselectEstacion() {
		if(estacion!=null) estacion.unselect();
	}
	
	public Estacion getEstacion() {
		return estacion;
	}

}
