package elementosSwing;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;

public class PanelBusqueda extends JPanel {
	private static PanelBusqueda panelstatic;
	private JTable tabla;
	private JScrollPane panel = new JScrollPane();
	private Boolean esEstacion=true;
	/**
	 * Create the panel.
	 */
	public PanelBusqueda() {
		panelstatic=this;
		setLayout(new MigLayout("", "[][grow]", "[120!][120!]"));
		
		JButton btnNewButton = new JButton("Estaciones");
		btnNewButton.setIcon(new ImageIcon("./assets/estacion_icon.png"));
		btnNewButton.setIconTextGap(20);
		btnNewButton.addActionListener(e -> {
			if(!esEstacion) {
				tabla=new TablaEstaciones();
				recrearTabla(tabla);
				esEstacion=true;
			}
		});
		add(btnNewButton, "cell 0 0,grow");
		
		JButton btnNewButton_1 = new JButton("Líneas");
		btnNewButton_1.setIcon(new ImageIcon("./assets/linea_icon.png"));
		btnNewButton_1.setIconTextGap(20);
		btnNewButton_1.addActionListener(e -> {
			if(esEstacion) {
				tabla=new TablaLineas();
				recrearTabla(tabla);
				esEstacion=false;
			}
		});
		add(btnNewButton_1, "cell 0 1,grow");
		tabla=new TablaEstaciones();
		recrearTabla(tabla);
		add(panel, "cell 1 0 1 2,grow");
	}
	
	private void recrearTabla(JTable tabla) {
		panel.removeAll();
		this.remove(panel);
		this.revalidate();
		panel = new JScrollPane(tabla);
		panel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(panel, "cell 1 0 1 2,grow");
		this.revalidate();
		this.repaint();
	}
	public static void recargar() {
		if(panelstatic.esEstacion) {
			panelstatic.tabla=new TablaEstaciones();
		}
		else {
			panelstatic.tabla=new TablaLineas();
		}
		panelstatic.recrearTabla(panelstatic.tabla);
	}
	
	public static Boolean esSeleccionable() {
		return !MainWindow.getInstance().getModoRecorrido();
	}
	
}
