package elementosSwing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

import elementosSwing.grafo2D.PanelGrafo;

public class MainWindow extends JFrame{
	public PanelGrafo pg=new PanelGrafo();
	public MainWindow(String t) {
		super(t);
		this.setBounds(0, 0, 500, 500);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridBagLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(pg);
		this.setVisible(true);
		
	}
}
