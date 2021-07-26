package elementosSwing.grafo2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;


public class Flecha {
	
	public static BasicStroke SELECTEDSTROKE=new BasicStroke(4);
	public static BasicStroke UNSELECTEDSTROKE=new BasicStroke(2);
	
	public Boolean selected=false;
	public Double x1,x2,y1,y2;
	public Color color;
	public Graphics2D g2d;
	public Estacion2D e1,e2;
	
	public Double angulo() {
		return Math.atan((y2-y1)/(x2-x1));
	}
	
	public Double getModulo() {
		return Math.sqrt(Math.pow(x2-x1,2)+ Math.pow(y2-y1,2));
	}
	
	public void updateCoord() {
		this.x1=e1.centrox;
		this.y1=e1.centroy;
		this.x2=e2.centrox;
		this.y2=e2.centroy;
		Double mod=getModulo();
		this.x2=Math.cos(angulo())*mod;
		this.y2=Math.sin(angulo())*mod;
		
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
		
		/*g2d.translate(x2, y2);
		g2d.rotate(angulo());
		Path2D.Double path= new Path2D.Double();
		path.moveTo(0, 0);
		path.lineTo(-14d, -7d);
		path.lineTo(-14d, 7d);
		path.closePath();
		g2d.fill(path);
		*/
		g2d.setTransform(rst);
		
	}

	public void select() {
		selected=true;
	}
	public void unselect() {
		selected=false;
	}
}
