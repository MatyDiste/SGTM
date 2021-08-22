package elementosSwing;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import objetos.Estacion;
import objetos.Linea;

public class PanelInfo extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelInfo() {
		
		Component rigidArea = Box.createRigidArea(new Dimension(300, 180));
		add(rigidArea);

	}
	
	public void setEstacion(Estacion e) {
		this.removeAll();
		InfoEstacion jp= new InfoEstacion(e);
		this.add(jp);
	}
	public void setLinea(Linea l) {
		this.removeAll();
		
	}
}
