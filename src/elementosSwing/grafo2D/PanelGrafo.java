package elementosSwing.grafo2D;

import java.awt.Color;
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

import javax.swing.JPanel;

public class PanelGrafo extends JPanel {
	
	private HashSet<Estacion2D> listEstaciones= new HashSet<Estacion2D>();
	private HashSet<Flecha> listFlechas= new HashSet<Flecha>();
	private Graphics2D g2d;
	public Optional<Estacion2D> selectedEstacion= Optional.empty();
	
	public void debugGenEstaciones() {
		Double rad=25d;
		for(Integer i=1; i<=13; i++)
			listEstaciones.add(new Estacion2D("A"+i.toString(), rad, Math.random()*(450-rad), Math.random()*(450-rad)));
	}
	
	public void debugGenFlechas() {
		listEstaciones.stream()
					  .forEach(e -> {
						  Estacion2D e2=listEstaciones.stream().filter(aux -> !aux.equals(e)).findAny().get();
						  Flecha f=new Flecha(e, e2, Color.BLACK);
						  listFlechas.add(f);
						  e.addSalida(f);
						  e2.addLlegada(f);
					  });
	}
	
	
	public PanelGrafo() {
		super();
		this.setPreferredSize(new Dimension(700, 550));
		//this.setSize(new Dimension(1000, 1000));
		//TODO cargar estaciones y flechas
		debugGenEstaciones();
		debugGenFlechas();
		this.setVisible(true);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					selectedEstacion = listEstaciones.stream()
											   .filter(e -> e.puntoDentro((double)event.getX(), (double)event.getY()))
										       .findAny();
					selectedEstacion.get().select();
				}catch(NoSuchElementException e) {
					selectedEstacion=Optional.empty();
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
					}
					repaint();
						
				}
				catch(NoSuchElementException e) {
					selectedEstacion=Optional.empty();
					repaint();
				}
			}
			public void mouseReleased(MouseEvent event) {
				try {
					selectedEstacion.get().unselect();
					selectedEstacion=Optional.empty();
					repaint();
				}
				catch(NoSuchElementException e) {
					repaint();
					
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
					}
					repaint();
						
				}
				catch(NoSuchElementException e) {
					selectedEstacion=Optional.empty();
					repaint();
				}
			}
		});
		
	}
	
	protected void dibujarFlechas() {
		listFlechas.stream()
				   .forEach(f -> f.dibujar(g2d));
	}
	
	protected void dibujarEstaciones() {
		listEstaciones.stream()
					  .forEach(est -> est.dibujar(g2d));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(new Rectangle(700, 550));
		dibujarFlechas();
		dibujarEstaciones();
	}
	
	
}
