package elementosSwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.extras.components.FlatButton;

import elementosSwing.grafo2D.PanelGrafo;
import objetos.Linea;

public class FrameAniadirLinea extends JFrame {
	//Se encarga de preguntar datos, crear las instancia y guardarla en la base de datos
	private JLabel textTop=new JLabel("Ingrese los datos de la nueva línea:", SwingConstants.CENTER);
	private JLabel tNombre=new JLabel("Nombre");
	private JLabel tColor=new JLabel("Color");
	private JTextField nombreTF=new MiTextFieldFactory("",true);
	private FlatButton elegirColor=new FlatButton();
	private FlatButton cancelar=new FlatButton();
	private FlatButton aceptar=new FlatButton();
	private PanelInformacion pinfo;

	private Color color=Color.BLACK;
	
	
	
	public FrameAniadirLinea(PanelInformacion p) {
		super("Añadir nueva línea");
		pinfo=p;
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		//textTop
		textTop.setFont(new Font(null, Font.PLAIN, 16));
		this.add(textTop);
		
		//tNombre y nombreTF
		JPanel jp=new JPanelBoxFactory(false);
		tNombre.setAlignmentX(LEFT_ALIGNMENT);
		nombreTF.setAlignmentX(LEFT_ALIGNMENT);
		jp.add(tNombre);
		jp.add(nombreTF);
		this.add(jp);
		
		//color
		JPanel jp2=new JPanelBoxFactory(false);
		jp2.add(tColor);
		elegirColor.setText("Elegir color");
		elegirColor.addActionListener(e -> {
			JFrame frameColor=new JFrame("Elegir color");
			JColorChooser jcc=new JColorChooser();
			FlatButton can=new FlatButton();
			FlatButton ac=new FlatButton();
			frameColor.setLayout(new BorderLayout());
			frameColor.setBounds(50,50,600,500);
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
		jp2.add(elegirColor);
		this.add(jp2);
		
		//boton cancelar y aceptar
		JPanel jplast=new JPanelBoxFactory(false);
		cancelar.setPreferredSize(new Dimension(100,25));
		cancelar.setMinimumSize(new Dimension(100,25));
		cancelar.setMaximumSize(new Dimension(100,25));
		aceptar.setPreferredSize(new Dimension(100,25));
		aceptar.setMinimumSize(new Dimension(100,25));
		aceptar.setMaximumSize(new Dimension(100,25));
		aceptar.setText("Aceptar");
		cancelar.setText("Cancelar");
		cancelar.setAlignmentX(RIGHT_ALIGNMENT);
		aceptar.setAlignmentX(RIGHT_ALIGNMENT);
		jplast.add(cancelar);
		jplast.add(aceptar);
		cancelar.addActionListener(e -> {
			this.dispose();
		});
		aceptar.addActionListener(e -> {
			PanelInformacion.setLinea(new Linea(nombreTF.getText(), color, true));
			this.dispose();
			PanelGrafo.repintarGrafo();
		});
		this.add(jplast);
		
		this.setBounds(50,20,500,500);
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	
}
