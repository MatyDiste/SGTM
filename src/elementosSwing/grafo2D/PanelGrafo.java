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

import elementosSwing.PanelInformacion;
import objetos.Estacion;

public class PanelGrafo extends JPanel {
	public static PanelGrafo pg;
	
	private HashSet<Estacion2D> listEstaciones= new HashSet<Estacion2D>();
	//private HashSet<Flecha> listFlechas= new HashSet<Flecha>();
	private Graphics2D g2d;
	public Optional<Estacion2D> selectedEstacion= Optional.empty();
	
	/*public void debugGenEstaciones() {
		for(Integer i=1; i<=5; i++)
			listEstaciones.add(new Estacion2D("A"+i.toString(), Math.random()*(450), Math.random()*(450)));
	}*/
	
	/*public void debugGenFlechas() {
		listEstaciones.stream()
					  .forEach(e -> {
						  Estacion2D e2=listEstaciones.stream().filter(aux -> !aux.equals(e)).findAny().get();
						  Flecha f=new Flecha(e, e2, new Color((int) (Math.random()*Integer.MAX_VALUE-1)));
						  listFlechas.add(f);
						  e.addSalida(f);
						  e2.addLlegada(f);
					  });
	}*/
	
	
	public PanelGrafo() {
		super();
		pg=this;
		this.setPreferredSize(new Dimension(700, 550));
		this.setMaximumSize(new Dimension(700,550));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//this.setSize(new Dimension(1000, 1000));
		//TODO cargar estaciones y flechas
		//debugGenEstaciones();
		//debugGenFlechas();
		this.setVisible(true);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					selectedEstacion = listEstaciones.stream()
											   .filter(e -> e.puntoDentro((double)event.getX(), (double)event.getY()))
										       .findAny();
					selectedEstacion.get().select();
					PanelInformacion.setEstacion(selectedEstacion.get().e);
				}catch(NoSuchElementException e) {
					selectedEstacion=Optional.empty();
					PanelInformacion.setVacio();
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
						PanelInformacion.setVacio();
					}
					repaint();
						
				}
				catch(NoSuchElementException e) {
					selectedEstacion=Optional.empty();
					PanelInformacion.setVacio();
					//repaint();
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
						PanelInformacion.setVacio();
					}
					repaint();
						
				}
				catch(NoSuchElementException e) {
					selectedEstacion=Optional.empty();
					PanelInformacion.setVacio();
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
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(new Rectangle(900, 900));
		//dibujarFlechas();
		dibujarEstaciones();
	}
	
	public static void repintarGrafo() {
		pg.paintComponent(pg.g2d);
	}
	
}
