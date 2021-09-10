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
import javax.swing.border.LineBorder;

import elementosSwing.PanelInfo;
import elementosSwing.PanelRecorrido;
import objetos.Estacion;
import objetos.Linea;

public class PanelGrafo extends JPanel {
	public static PanelGrafo pg;
	
	private HashSet<Estacion2D> listEstaciones= new HashSet<Estacion2D>();
	private Graphics2D g2d;
	public Optional<Estacion2D> selectedEstacion= Optional.empty();
	public Optional<Estacion2D> selectedEstacion2= Optional.empty();
	private Boolean modoRecorrido=false;
	private Boolean editable;
	
	
	public PanelGrafo(Boolean b) {
		super();
		pg=this;
		editable=b;
		this.setBorder(new LineBorder(Color.GRAY, 2));
		this.setOpaque(true);
		this.setPreferredSize(new Dimension(700, 550));
		this.setMaximumSize(new Dimension(900,550));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setVisible(true);
		recargar();
		this.addMouseListener(new MouseAdapter() {
			/*public void mousePressed(MouseEvent event) {
				mouseClicked(event);
			}*/
			public void mouseClicked(MouseEvent event) {
				if (!modoRecorrido) {
					try {
						selectedEstacion = getClickeado((double)event.getX(), (double)event.getY());
						selectedEstacion.get().select();
						PanelInfo.setEstacion(selectedEstacion.get().e);
						//PanelInfo.repintar();
					} catch (NoSuchElementException e) {
						selectedEstacion = Optional.empty();
						PanelInfo.setVacio();
						//PanelInfo.repintar();
					} finally {
						repaint();
					} 
				}
				else {
					try {
						setearEstacion(getClickeado((double)event.getX(), (double)event.getY()).get());
					} catch(NoSuchElementException exc) {
						System.out.println("No clickeaste una estacion");
					}
					
				}
				
			}
			
		});
		
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent event) {
				if(editable) {
					try {
						
						if (selectedEstacion.get().puntoDentro((double)event.getX(), (double)event.getY(), 100d)) {
							selectedEstacion.get().mover((double) event.getX(), (double) event.getY());
							paintImmediately(0, 0, 700, 760);
							event.consume();
						}
					}
					catch(NoSuchElementException e) {
						selectedEstacion=Optional.empty();
						PanelInfo.setVacio();
					}
				}	
			}
		});
	}
	
	protected void dibujarEstaciones() {
		listEstaciones.forEach(est -> est.dibujar(g2d));
	}
	
	public static void quitarEstacion(Estacion2D e) {
		pg.listEstaciones.remove(e);
	}
	
	protected void recargar() {
		//listEstaciones.clear();
		listEstaciones.addAll(Estacion.listEstaciones.stream().map(e->e.getE2d()).collect(Collectors.toList()));
		//listEstaciones.addAll(Estacion.listEstaciones.stream().map(e->e.getE2d()).collect(Collectors.toList()));
		//System.out.println("Hay "+listEstaciones.size()+" estaciones2D en la lista");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(new Rectangle(700, 760));
	    dibujarEstaciones();
		//dibujarFlechas();
	    //System.out.println("PaintComponent "+LocalTime.now());
	}
	
	public static void repintarGrafo() {
		pg.recargar();
		pg.repaint();
	}
	public static void setEditable(Boolean e) {
		pg.editable=e;
	}
	public static void unsetModoRecorrido() {
		pg.modoRecorrido=false;
		pg.selectedEstacion.ifPresentOrElse(e -> e.unselect(), ()->{});
		pg.selectedEstacion2.ifPresentOrElse(e -> e.unselect(), ()->{});
		pg.selectedEstacion=Optional.empty();
		pg.selectedEstacion2=Optional.empty();
		Linea.listLineas.forEach(l -> l.unselect());
	}
	public static void setModoRecorrido() {
		try {
			pg.selectedEstacion.get().unselect();
		} catch (NoSuchElementException e) {}
		pg.selectedEstacion=Optional.empty();
		pg.modoRecorrido=true;
	}
	
	private Optional<Estacion2D> getClickeado(double x, double y) {
		return listEstaciones.stream()
							 .filter(e -> e.puntoDentro(x,y)).findAny();
	}
	
	public static void setearEstacion(Estacion2D e) {
		try {
			if(PanelRecorrido.primerEstacion()) {
				pg.selectedEstacion.get().unselect();
				pg.selectedEstacion=Optional.of(e);
			}
			else {
				pg.selectedEstacion2.get().unselect();
				pg.selectedEstacion=Optional.of(e);
			}
		} catch (NoSuchElementException e1) {
			pg.selectedEstacion=Optional.of(e);
		} finally {
			pg.selectedEstacion.get().select();
			PanelRecorrido.setEstacion(pg.selectedEstacion.get().e);
		}
		
		PanelGrafo.repintarGrafo();
	}
	
}
