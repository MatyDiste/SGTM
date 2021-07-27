package elementosSwing.grafo2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;


public class Flecha {
	
	public static BasicStroke SELECTEDSTROKE=new BasicStroke(4);
	public static BasicStroke UNSELECTEDSTROKE=new BasicStroke(2);
	
	public Boolean selected=false;
	public java.lang.Double x1,x2,y1,y2;
	public Color color;
	public Graphics2D g2d;
	public Estacion2D e1,e2;
	
	public java.lang.Double angulo() {
		return Math.atan((y2-y1)/(x2-x1));
	}
	
	public java.lang.Double getModulo() {
		return Math.sqrt(Math.pow(x2-x1,2)+ Math.pow(y2-y1,2));
	}
	
	private void debugPuntos() {
		g2d.setColor(Color.RED);
		g2d.fill(new Ellipse2D.Double(e1.centrox, e1.centroy, 10, 10));
		g2d.fill(new Ellipse2D.Double(e2.centrox, e2.centroy, 10, 10));
		g2d.setColor(color);
	}
	
	public void updateCoord() {
		//System.out.println("Nodo 1: "+e1.nombre);
		//System.out.println("Nodo 2: "+e2.nombre);
		this.x1=e1.centrox;
		this.y1=e1.centroy;
		this.x2=e2.centrox;
		this.y2=e2.centroy;
		java.lang.Double mod=getModulo();
		//System.out.println("Viejas coord1: x="+x1.toString()+" y="+y1.toString());
		//System.out.println("Viejas coord2: x="+x2.toString()+" y="+y2.toString());
		this.x2=(x2-x1) * (1-(e2.radio/mod))+x1;
		this.y2=(y2-y1) * (1-(e2.radio/mod))+y1;
		//System.out.println("Nuevas coord2: x="+x2.toString()+" y="+y2.toString());
		//System.out.println("Modulo = "+mod.toString());
		//if(mod.isNaN()) System.out.println("ES NAN!!");
		//System.out.println("---------------");
	}
	
	public Flecha(Estacion2D e1, Estacion2D e2, Color c) {
		
		this.e1=e1;
		this.e2=e2;
		this.color=c;
		updateCoord();
	}
	
	public void dibujar(Graphics2D g) {
		updateCoord();
		g2d=g;
		AffineTransform rst=g2d.getTransform();
		
		g2d.setColor(color);
		g2d.setStroke(selected? SELECTEDSTROKE : UNSELECTEDSTROKE);
		g2d.draw(new Line2D.Double(x1,y1,x2,y2));
		//debugPuntos();
		
		g2d.translate(x2, y2);
		g2d.rotate((x1 < x2)? angulo() : angulo()-Math.PI);
		Path2D.Double path= new Path2D.Double();
		path.moveTo(0, 0);
		path.lineTo(-14d, -7d);
		path.lineTo(-14d, 7d);
		path.closePath();
		g2d.fill(path);
		
		g2d.setTransform(rst);
		
	}

	public void select() {
		selected=true;
	}
	public void unselect() {
		selected=false;
	}
}
