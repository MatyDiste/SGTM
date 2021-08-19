package elementosSwing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.extras.components.FlatButton;

import elementosSwing.grafo2D.PanelGrafo;
import objetos.Estacion;

public class FrameAniadirEstacion extends JFrame {
	//Se encarga de preguntar los datos de estacion, crear la instancia y guardarlo en la db
	private JLabel textTop=new JLabel("Ingrese los datos de la nueva estación:", SwingConstants.CENTER);
	private JLabel tID=new JLabel("ID");
	private JLabel tNombre=new JLabel("Nombre");
	private JLabel tApertura=new JLabel("Horario apertura");
	private JLabel tCierre=new JLabel("Horario cierre");
	private JTextField IDTF=new MiTextFieldFactory(Short.toString((short)(Math.random()*32000)),true);
	private JTextField nombreTF=new MiTextFieldFactory("",true);
	private JTextField aperturaTF=new MiTextFieldFactory(LocalTime.now().toString(),true);
	private JTextField cierreTF=new MiTextFieldFactory(LocalTime.now().toString(),true);
	private FlatButton cancelar=new FlatButton();
	private FlatButton aceptar=new FlatButton();
	private PanelInformacion pinfo;
	
	
	public FrameAniadirEstacion(PanelInformacion p) {
		super("Añadir nueva estación");
		pinfo=p;
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.setBounds(50,20,500,700);
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//textTop
		textTop.setFont(new Font(null, Font.PLAIN, 16));
		this.add(textTop);
		
		//campos
		IDTF.setPreferredSize(new Dimension(100,12));
		nombreTF.setPreferredSize(new Dimension(100,12));
		aperturaTF.setPreferredSize(new Dimension(100,12));
		cierreTF.setPreferredSize(new Dimension(100,12));
		this.add(jpData(tID, IDTF));
		this.add(jpData(tNombre, nombreTF));
		this.add(jpData(tApertura, aperturaTF));
		this.add(jpData(tCierre, cierreTF));
		
		//botones
		cancelar.addActionListener(e -> {
			this.dispose();
		});
		aceptar.addActionListener(e -> {
			try {
				PanelInformacion.setEstacion(new Estacion(Short.parseShort(IDTF.getText()), nombreTF.getText(), LocalTime.parse(aperturaTF.getText()), LocalTime.parse(cierreTF.getText()), true));
				PanelGrafo.repintarGrafo();
				this.dispose();
			} catch(DateTimeParseException exc) {
				JFrame popup=new JFrame("Error");
				popup.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				popup.setBounds(50,20,100,75);
				popup.setLayout(new BorderLayout());
				popup.add(new JLabel("Error en el formato de la hora"), BorderLayout.NORTH);
				FlatButton ok=new FlatButton();
				ok.setText("Ok");
				ok.setPreferredSize(new Dimension(100,25));
				ok.setMinimumSize(new Dimension(100,25));
				ok.setMaximumSize(new Dimension(100,25));
				ok.addActionListener(d -> {
					popup.dispose();
				});
				popup.add(ok, BorderLayout.CENTER);
				popup.setVisible(true);
			}
		});
		
		cancelar.setText("Cancelar");
		cancelar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		cancelar.setPreferredSize(new Dimension(100,25));
		cancelar.setMinimumSize(new Dimension(100,25));
		cancelar.setMaximumSize(new Dimension(100,25));
		aceptar.setText("Aceptar");
		aceptar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		aceptar.setPreferredSize(new Dimension(100,25));
		aceptar.setMinimumSize(new Dimension(100,25));
		aceptar.setMaximumSize(new Dimension(100,25));
		this.add(jpData(cancelar, aceptar));
		
		
		
	}
	
	
	private static JPanel jpData(Component t, Component tf) {
		JPanel p=new JPanelBoxFactory(false);
		p.add(t);
		p.add(tf);
		return p;
	}
	
}
