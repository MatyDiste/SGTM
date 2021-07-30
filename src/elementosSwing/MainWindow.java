package elementosSwing;

import java.awt.GridBagLayout;

import javax.swing.JFrame;

import elementosSwing.grafo2D.PanelGrafo;

public class MainWindow extends JFrame{
	public PanelGrafo pg=new PanelGrafo();
	public MainWindow(String t, Boolean modoAdmin) {
		super(t);
		this.setBounds(0, 0, 500, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(pg);
		this.setVisible(true);
	}
}
