package elementosSwing;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;

import elementosSwing.grafo2D.PanelGrafo;

public class MainWindow extends JFrame{
	private static MainWindow mw;
	private PanelGrafo pg=new PanelGrafo(Main.modoAdmin);
	private PanelBusqueda pb=new PanelBusqueda();
	private PanelInfo pi=new PanelInfo(Main.modoAdmin);
	private PanelRecorrido pr;
	public Integer grafoSizeX;
	public Integer grafoSizeY;
	public Boolean modoRecorrido=false;
	
	public MainWindow(String t) {
		super(t);
		mw=this;
		this.setBounds(0, 0, 1200, 870);
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
	}
	
	public void setModoRecorrido() {
		modoRecorrido=true;
		pr=new PanelRecorrido();
		PanelGrafo.setModoRecorrido();
		this.remove(pi);
		this.revalidate();
		this.add(pr, BorderLayout.WEST);
		this.revalidate();
		this.repaint();
	}
	public void unsetModoRecorrido() {
		modoRecorrido=false;
		pi=new PanelInfo(Main.modoAdmin);
		PanelGrafo.unsetModoRecorrido();
		this.remove(pr);
		this.revalidate();
		this.add(pi, BorderLayout.WEST);
		this.revalidate();
		this.repaint();
	}
	public Boolean getModoRecorrido() {
		return modoRecorrido;
	}
	public static MainWindow getInstance() {
		return mw;
	}
}
