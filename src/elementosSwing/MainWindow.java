package elementosSwing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;

import elementosSwing.grafo2D.PanelGrafo;

public class MainWindow extends JFrame{
	private PanelGrafo pg=new PanelGrafo();
	private PanelBusqueda pb=new PanelBusqueda();
	private PanelInfo pi=new PanelInfo();
	public static Integer grafoSizeX;
	public static Integer grafoSizeY;
	
	public MainWindow(String t) {
		super(t);
		this.setBounds(0, 0, 1200, 800);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(pg, BorderLayout.EAST);
		this.add(pi, BorderLayout.WEST);
		this.add(pb, BorderLayout.PAGE_END);
		this.setVisible(true);
		JFrame aux=this;
		this.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				pg.repaint();
				aux.repaint();
			}

			@Override
			public void focusLost(FocusEvent e) {
				aux.repaint();
			}
			
		});
		
		grafoSizeX=pg.getWidth();
		grafoSizeY=pg.getHeight();
		/*
		 * Cambia el grafo cada 15 segundos, DEBUG
		new Thread(() ->{
			while(true) {
				this.remove(pg);
				this.revalidate();
				pg=new PanelGrafo();
				this.add(pg, BorderLayout.EAST);
				this.repaint();
				System.out.println("Cambiando de panel...");
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}).start();*/
	}
}
