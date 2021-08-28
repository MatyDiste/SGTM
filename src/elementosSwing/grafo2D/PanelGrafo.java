package elementosSwing.grafo2D;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import elementosSwing.PanelInfo;
import objetos.Estacion;

public class PanelGrafo extends JPanel {
	public static PanelGrafo pg;
	
	private HashSet<Estacion2D> listEstaciones= new HashSet<Estacion2D>();
	private Graphics2D g2d;
	public Optional<Estacion2D> selectedEstacion= Optional.empty();
	
	
	public PanelGrafo() {
		super();
		pg=this;
		this.setPreferredSize(new Dimension(700, 550));
		this.setMaximumSize(new Dimension(900,550));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//this.setSize(new Dimension(1000, 1000));
		//TODO cargar estaciones y flechas
		this.setVisible(true);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					selectedEstacion = listEstaciones.stream()
											   .filter(e -> e.puntoDentro((double)event.getX(), (double)event.getY()))
										       .findAny();
					selectedEstacion.get().select();
					PanelInfo.setEstacion(selectedEstacion.get().e);
					//PanelInfo.repintar();
				}catch(NoSuchElementException e) {
					selectedEstacion=Optional.empty();
					PanelInfo.setVacio();
					//PanelInfo.repintar();
				}
				
				
			}
			public void mousePressed(MouseEvent event) {
				try {
					if(selectedEstacion.get().puntoDentro((double)event.getX(), (double)event.getY())) {
						selectedEstacion.get().mover((double)event.getX(), (double)event.getY());
					}
					else {
						selectedEstacion.get().unselect();
						selectedEstacion=Optional.empty();
						PanelInfo.setVacio();
						//PanelInfo.repintar();
					}
					repaint();
						
				}
				catch(NoSuchElementException e) {
					selectedEstacion=Optional.empty();
					PanelInfo.setVacio();
					//PanelInfo.repintar();
				}
			}
			public void mouseReleased(MouseEvent event) {
				try {
					//selectedEstacion.get().unselect();
					//selectedEstacion=Optional.empty();
					//PanelInformacion.setVacio();
					repaint();
				}
				catch(NoSuchElementException e) {
					//repaint();
					
				}
			}
		});
		
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent event) {
				try {
					if(selectedEstacion.get().puntoDentro((double)event.getX(), (double)event.getY())) {
						selectedEstacion.get().mover((double)event.getX(), (double)event.getY());
					}
					else {
						selectedEstacion.get().unselect();
						selectedEstacion=Optional.empty();
						PanelInfo.setVacio();
					}
					repaint();
						
				}
				catch(NoSuchElementException e) {
					selectedEstacion=Optional.empty();
					PanelInfo.setVacio();
					//repaint();
				}
			}
		});
		
	}
	
	/*protected void dibujarFlechas() {
		listFlechas.stream()
				   .forEach(f -> f.dibujar(g2d));
	}*/
	
	protected void dibujarEstaciones() {
		listEstaciones.stream()
					  .forEach(est -> est.dibujar(g2d));
	}
	
	protected void recargar() {
		listEstaciones.clear();
		listEstaciones.addAll(Estacion.listEstaciones.stream().map(e->e.getE2d()).collect(Collectors.toList()));
		//System.out.println("Hay "+listEstaciones.size()+" estaciones2D en la lista");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		recargar();
		g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.WHITE);
		g2d.fill(new Rectangle(1000, 1000));
		dibujarEstaciones();
		//dibujarFlechas();
	}
	
	public static void repintarGrafo() {
		pg.repaint();
	}
	
}
