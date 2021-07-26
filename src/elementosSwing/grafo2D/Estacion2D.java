package elementosSwing.grafo2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;


public class Estacion2D {
	
	public static Integer UNSELECTEDSTROKE=3;
	public static Integer SELECTEDSTROKE=5;
	
	public Boolean redraw=true;
	public Boolean selected=false;
	public java.lang.Double posx, posy;
	public java.lang.Double radio;
	public java.lang.Double centrox;
	public java.lang.Double centroy;
	public String nombre;
	public Ellipse2D.Double circulo;
	public Graphics2D g2d;
	
	public Estacion2D(String name, java.lang.Double radius, java.lang.Double posx, java.lang.Double posy) {
		circulo= new Ellipse2D.Double(0, 0, radius*2, radius*2);
		radio=radius;
		centrox= posx+radius;
		centroy= posy+radius;
		this.posx=posx;
		this.posy=posy;
		nombre=name;
		
	}
	
	public Boolean puntoDentro(Double x, Double y) {
		if(Math.sqrt(Math.pow(centrox-x, 2) + Math.pow(centroy-y, 2))<=radio*2) {
		System.out.println("Centro en: x="+centrox.toString()+" y="+centroy.toString());
		System.out.println("Tiro en: x="+x.toString()+" y="+y.toString());
		}
		return Math.sqrt(Math.pow(centrox-x, 2) + Math.pow(centroy-y, 2))<=radio*2;
	}
	
	public void dibujar(Graphics2D g) {
		g2d=g;
		//if(redraw) {
			AffineTransform rst = g2d.getTransform();
			g2d.translate(this.posx, this.posy);
		
			g2d.setColor(new Color(80, 172, 230));
			g2d.fill(circulo);
		
			g2d.setColor(new Color(56, 79, 212));
			g2d.setStroke(new BasicStroke(selected? SELECTEDSTROKE : UNSELECTEDSTROKE));
			g2d.draw(circulo);
			
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Arial", selected? Font.BOLD : Font.PLAIN, 15));
			g2d.drawString(nombre, radio.floatValue()/1.7f, radio.floatValue()/0.8f);
			
			g2d.setTransform(rst);
			redraw=false;
		//}
	}
	
	public void select() {
		selected=true;
		redraw=true;
		//this.dibujar(g2d);
	}
	public void unselect() {
		selected=false;
		redraw=true;
		//this.dibujar(g2d);
	}
	public void setRedraw() {
		redraw=true;
		//this.dibujar(g2d);
	}
	public void mover(Double x, Double y) {
		centrox=x;
		centroy=y;
		posx=x-radio;
		posy=y-radio;
		redraw=true;
		//this.dibujar(g2d);
	}
	
}
