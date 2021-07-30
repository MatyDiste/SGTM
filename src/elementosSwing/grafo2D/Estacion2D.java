package elementosSwing.grafo2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;


public class Estacion2D {
	
	public static Integer UNSELECTEDSTROKE=3;
	public static Integer SELECTEDSTROKE=5;
	public static Double RADIO=25d;
	
	public ArrayList<Flecha> listFlechasSalida=new ArrayList<Flecha>();
	public ArrayList<Flecha> listFlechasLlegada=new ArrayList<Flecha>();
	public Boolean selected=false;
	public Double posx, posy;
	public Double centrox;
	public Double centroy;
	public String nombre;
	public Ellipse2D.Double circulo;
	public Graphics2D g2d;
	
	
	public Estacion2D(String name, Double posx, Double posy) {
		circulo= new Ellipse2D.Double(0, 0, RADIO*2, RADIO*2);
		//radio=radius;
		centrox= posx+RADIO;
		centroy= posy+RADIO;
		this.posx=posx;
		this.posy=posy;
		nombre=name;
		
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
	}
	public void addSalida(Flecha f) {
		listFlechasSalida.add(f);
	}
	
	private void dibujarFlechas() {
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
		//dibujarFlechas();
		//this.dibujar(g2d);
		
	}
	
}
