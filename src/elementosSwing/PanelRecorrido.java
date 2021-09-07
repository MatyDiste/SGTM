package elementosSwing;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import elementosSwing.grafo2D.PanelGrafo;
import net.miginfocom.swing.MigLayout;
import objetos.Estacion;
import objetos.Recorrido;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

public class PanelRecorrido extends JPanel {
	public static PanelRecorrido pr;
	
	private JPanel panelEstaciones = new JPanel();
	private PanelEstacion pedesde=new PanelEstacion();
	private PanelEstacion pehasta=new PanelEstacion();
	private PanelEstacion panelSeleccionado=pedesde;
	private JButton btnRecorridos = new JButton("Comprar ticket");
	private JTextField tfBarato;
	private JTextField tfRapido;
	private JTextField tfCorto;
	private JRadioButton rdbtnBarato = new JRadioButton("Más barato");
	private JRadioButton rdbtnRapido = new JRadioButton("Más rápido");
	private JRadioButton rdbtnCorto = new JRadioButton("Más corto");
	private Recorrido recorridoSeleccionado;
	private ActionListener baratoBtn;
	private ActionListener rapidoBtn;
	private ActionListener cortoBtn;
	private ButtonGroup radioButtons=new ButtonGroup();
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
		
		panelEstaciones.setLayout(new MigLayout("", "[grow][grow]", "[][][][][][][][][]"));
		
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
		rdbtnBarato.setEnabled(false);
		panelEstaciones.add(rdbtnBarato, "cell 0 6");
		
		tfBarato = new JTextField();
		tfBarato.setEditable(false);
		panelEstaciones.add(tfBarato, "cell 1 6,growx");
		tfBarato.setColumns(10);
		
		rdbtnRapido.setEnabled(false);
		panelEstaciones.add(rdbtnRapido, "cell 0 7");
		
		tfRapido = new JTextField();
		tfRapido.setEditable(false);
		panelEstaciones.add(tfRapido, "cell 1 7,growx");
		tfRapido.setColumns(10);
		
		rdbtnCorto.setEnabled(false);
		panelEstaciones.add(rdbtnCorto, "cell 0 8");
		
		radioButtons.add(rdbtnBarato);
		radioButtons.add(rdbtnRapido);
		radioButtons.add(rdbtnCorto);
		
		tfCorto = new JTextField();
		tfCorto.setEditable(false);
		panelEstaciones.add(tfCorto, "cell 1 8,growx");
		tfCorto.setColumns(10);
		
		btnRecorridos.setEnabled(false);
		btnRecorridos.setToolTipText("Comprar ticket para el recorrido seleccionado");
		btnRecorridos.setIcon(new ImageIcon("./assets/buy_icon.png"));
		btnRecorridos.addActionListener(e -> {
			JFrame jf=new FGenerarTicket(recorridoSeleccionado);
			jf.setVisible(true);
		});
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
			if(pr.pedesde!=null) {
				pr.pehasta.unselectEstacion();
				pr.pehasta.unselect();
				pr.panelEstaciones.remove(pr.pehasta);
				pr.panelEstaciones.revalidate();
				pr.pehasta=new PanelEstacion();
				pr.panelEstaciones.add(pr.pehasta, "cell 0 4 2 1,alignx center,aligny top");
			}
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
		else {
			btnRecorridos.setEnabled(false);
			eliminarRecorridos();
		}
	}
	
	public void eliminarRecorridos() {
		rdbtnBarato.setSelected(false);
		rdbtnCorto.setSelected(false);
		rdbtnRapido.setSelected(false);
		rdbtnBarato.setEnabled(false);
		rdbtnCorto.setEnabled(false);
		rdbtnRapido.setEnabled(false);
		if(recorridoSeleccionado!=null) {
			recorridoSeleccionado.unselect();
			recorridoSeleccionado=null;
		}
		btnRecorridos.setEnabled(false);
		tfBarato.setText("");
		tfRapido.setText("");
		tfCorto.setText("");
		radioButtons.clearSelection();
		btnRecorridos.setEnabled(false);
	}
	
	public void generarRecorridos() {
		//TODO generar recorridos, setear radiocheckboxes y completar campos de informacion
		//También habilitar el botón ComprarTicket
		
		Estacion e1=this.pedesde.getEstacion();
		Estacion e2=this.pehasta.getEstacion();
		LinkedList<Recorrido> recorridos=e1.getRecorridos(e2);
		if (!recorridos.isEmpty()) {
			Recorrido masCorto = recorridos.stream().reduce((acum, rec) -> {
				return (acum.distanciaTotal() < rec.distanciaTotal()) ? acum : rec;
			}).get();
			Recorrido masBarato = recorridos.stream().reduce((acum, rec) -> {
				return (acum.costoTotal() < rec.costoTotal()) ? acum : rec;
			}).get();
			Recorrido masRapido = recorridos.stream().reduce((acum, rec) -> {
				return (acum.duracionTotal() < rec.duracionTotal()) ? acum : rec;
			}).get();
			if (baratoBtn != null)
				rdbtnBarato.removeActionListener(baratoBtn);
			if (cortoBtn != null)
				rdbtnCorto.removeActionListener(cortoBtn);
			if (rapidoBtn != null)
				rdbtnRapido.removeActionListener(rapidoBtn);
			if (recorridoSeleccionado != null) {
				recorridoSeleccionado.unselect();
				recorridoSeleccionado = null;
			}
			baratoBtn = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (recorridoSeleccionado != null)
						recorridoSeleccionado.unselect();
					recorridoSeleccionado = masBarato;
					recorridoSeleccionado.select();
					btnRecorridos.setEnabled(true);
				}
			};
			rapidoBtn = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (recorridoSeleccionado != null)
						recorridoSeleccionado.unselect();
					recorridoSeleccionado = masRapido;
					recorridoSeleccionado.select();
					btnRecorridos.setEnabled(true);
				}
			};
			cortoBtn = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (recorridoSeleccionado != null)
						recorridoSeleccionado.unselect();
					recorridoSeleccionado = masCorto;
					recorridoSeleccionado.select();
					btnRecorridos.setEnabled(true);
				}
			};
			rdbtnBarato.addActionListener(baratoBtn);
			rdbtnCorto.addActionListener(cortoBtn);
			rdbtnRapido.addActionListener(rapidoBtn);
			rdbtnBarato.setEnabled(true);
			rdbtnCorto.setEnabled(true);
			rdbtnRapido.setEnabled(true);
			
			DecimalFormat df=new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.FLOOR);
			tfBarato.setText("$"+df.format(masBarato.costoTotal())+" pesos");
			tfRapido.setText(""+df.format(masRapido.duracionTotal())+" minutos");
			tfCorto.setText(""+df.format(masCorto.distanciaTotal())+" metros");
			btnRecorridos.setEnabled(false);
			
		}
		else {
			eliminarRecorridos();
		}
		
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
		setLayout(new MigLayout("", "[][grow]", "[grow][grow][grow][grow]"));
		
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
