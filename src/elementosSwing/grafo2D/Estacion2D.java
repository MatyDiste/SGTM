package elementosSwing.grafo2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import objetos.Estacion;


public class Estacion2D {
	
	public static final Integer UNSELECTEDSTROKE=3;
	public static final Integer SELECTEDSTROKE=5;
	public static final Double RADIO=25d;
	
	public Estacion e;
	public ArrayList<Flecha> listFlechasSalida=new ArrayList<Flecha>();
	public ArrayList<Flecha> listFlechasLlegada=new ArrayList<Flecha>();
	public Boolean selected=false;
	public Double posx, posy;
	public Double centrox;
	public Double centroy;
	public String nombre;
	public Ellipse2D.Double circulo;
	public Graphics2D g2d;
	
	
	public Estacion2D(Estacion estacion) {
		circulo= new Ellipse2D.Double(0, 0, RADIO*2, RADIO*2);
		e=estacion;
		//radio=radius;
		centrox= e.posx+RADIO;
		centroy= e.posy+RADIO;
		this.posx=e.posx;
		this.posy=e.posy;
		nombre=e.getNombre();
		
	}
	
	public Boolean puntoDentro(Double x, Double y) {
		/*if(Math.sqrt(Math.pow(centrox-x, 2) + Math.pow(centroy-y, 2))<=radio*2) {
		System.out.println("Centro en: x="+centrox.toString()+" y="+centroy.toString());
		System.out.println("Tiro en: x="+x.toString()+" y="+y.toString());
		}*/
		return Math.sqrt(Math.pow(centrox-x, 2) + Math.pow(centroy-y, 2))<=RADIO;
	}
	
	public void dibujar(Graphics2D g) {
		g2d=g;
		dibujarFlechas();
		
		AffineTransform rst = g2d.getTransform();
		g2d.translate(this.posx, this.posy);
		
		g2d.setColor(new Color(80, 172, 230));
		g2d.fill(circulo);
		
		g2d.setColor(new Color(56, 79, 212));
		g2d.setStroke(new BasicStroke(selected? SELECTEDSTROKE : UNSELECTEDSTROKE));
		g2d.draw(circulo);
			
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", selected? Font.BOLD : Font.PLAIN, 15));
		g2d.drawString(nombre, RADIO.floatValue()/1.7f, RADIO.floatValue()/0.8f);
			
		g2d.setTransform(rst);
		
	}
	
	public void addLlegada(Flecha f) {
		listFlechasLlegada.add(f);
		//System.out.println("Añadida flecha a estacion (llegada)");
	}
	public void addSalida(Flecha f) {
		//System.out.println("Añadida flecha a estacion (salida)");
		listFlechasSalida.add(f);
	}
	
	private void dibujarFlechas() {
		//System.out.println("Dibujando flechas de estacion: "+this.nombre);
		listFlechasLlegada.stream()
				          .forEach(f -> f.dibujar(g2d));
		listFlechasSalida.stream()
						 .forEach(f -> f.dibujar(g2d));
	}
	
	public void select() {
		selected=true;
		//this.dibujar(g2d);
	}
	public void unselect() {
		selected=false;
		//this.dibujar(g2d);
	}
	public void mover(Double x, Double y) {
		centrox=x;
		centroy=y;
		posx=x-RADIO;
		posy=y-RADIO;
		e.posx=x-RADIO;
		e.posy=y-RADIO;
		//dibujarFlechas();
		//this.dibujar(g2d);
		
	}
	
}
