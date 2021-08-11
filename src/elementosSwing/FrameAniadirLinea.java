package elementosSwing;

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
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//textTop
		textTop.setFont(new Font(null, Font.PLAIN, 16));
		this.add(textTop);
		
		//tNombre y nombreTF
		JPanel jp=new JPanelBoxFactory(false);
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
			frameColor.setLayout(new BoxLayout(frameColor, BoxLayout.PAGE_AXIS));
			ac.setText("Aceptar");
			can.setText("Cancelar");
			ac.addActionListener(d -> {
				color=jcc.getColor();
				frameColor.dispose();
			});
			can.addActionListener(d -> {
				frameColor.dispose();
			});
			frameColor.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			frameColor.add(jcc);
			frameColor.add(can);
			frameColor.add(ac);
			frameColor.pack();
			frameColor.setVisible(true);
			
		});
		jp2.add(elegirColor);
		
		//boton cancelar y aceptar
		JPanel jplast=new JPanelBoxFactory(false);
		jplast.add(cancelar);
		jplast.add(aceptar);
		cancelar.addActionListener(e -> {
			this.dispose();
		});
		aceptar.addActionListener(e -> {
			pinfo.setLinea(new Linea(nombreTF.getText(), color, true));
		});
		
		this.setPreferredSize(new Dimension(500,500));
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	
}
