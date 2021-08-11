package elementosSwing.grafo2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import objetos.Conexion;
import objetos.Linea;



public class Flecha {
	
	
	private static final int SELECTEDWIDTH=4;
	private static final int UNSELECTEDWIDTH=2;
	private static final float[] dash= {0.3f, 0.3f};
	private static final float dashPhase=0f;
	
	public Conexion conect;
	
	public Boolean selected=false;
	public java.lang.Double x1,x2,y1,y2;
	public Color color;
	public Graphics2D g2d;
	public final Estacion2D e1,e2;
	public final java.lang.Double xoffsetRandom=Math.random();
	public final java.lang.Double yoffsetRandom=Math.random();
	public Point.Double puntoBorde=new Point.Double();
	public java.lang.Double anguloOffseted;
	public Boolean mayorAngulo;
	public Path2D.Double curva=new Path2D.Double();
	public Path2D.Double punta=new Path2D.Double();
	public AffineTransform rst;
	public AffineTransform recta;
	
	private BasicStroke getStroke() {
		Integer size=selected? SELECTEDWIDTH : UNSELECTEDWIDTH;
		return conect.estado().equals("ACTIVA")? new BasicStroke(size) : new BasicStroke(size, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, dash, dashPhase); 
	}
	
	public void updateTransform() {
		//Mantiene la transformacion de manera que E1 y E2 esten sobre la recta y=0
		g2d.setStroke(this.getStroke());
		g2d.setColor(color);
		g2d.translate(x1, y1);
		g2d.rotate(angulo());
		
		recta=g2d.getTransform();
		g2d.setTransform(rst);
	}
	
	public void updatePunta() {
		//punta=new Path2D.Double();
		
		punta.moveTo(0, 0);
		punta.lineTo(-14d, -7d);
		punta.lineTo(-14d, 7d);
		punta.closePath();
		
		//java.lang.Double a=angulo();
		puntoBorde.setLocation(Math.cos(Math.PI-anguloOffseted)*Estacion2D.RADIO+getModulo(), Math.sin(Math.PI-anguloOffseted)*Estacion2D.RADIO);
		//puntoBorde.setLocation(Math.cos(anguloOffseted+a)*Estacion2D.RADIO+x2, Math.sin(anguloOffseted+a)*Estacion2D.RADIO+y2);
		//DEBUG
		/*
		g2d.setColor(Color.GREEN);
		g2d.fill(new Ellipse2D.Double(puntoBorde.x, puntoBorde.y,10,10));
		g2d.setColor(color);
		*/
		//DEBUG
	}
	
	public void dibujarPunta() {
		//System.out.println("Dibujando punta");
		
		g2d.setTransform(recta);
		//DEBUG
		/*
		g2d.setColor(Color.ORANGE);
		g2d.fill(new Ellipse2D.Double(0,0,10,10));
		g2d.fill(new Ellipse2D.Double(puntoBorde.x, puntoBorde.y,10,10));
		g2d.setColor(color);
		*/
		//DEBUG
		g2d.translate(puntoBorde.x, puntoBorde.y);
		g2d.rotate(-anguloOffseted);
		g2d.fill(punta);
		
		g2d.setTransform(rst);
	}
	
	public void updateCurva() {
		curva=new Path2D.Double();
		java.lang.Double mod=getModulo()/3;
		curva.moveTo(Math.cos(anguloOffseted)*Estacion2D.RADIO, Math.sin(anguloOffseted)*Estacion2D.RADIO);
		curva.curveTo(Math.cos(anguloOffseted)*(mod), Math.sin(anguloOffseted)*(mod), Math.cos(Math.PI-anguloOffseted)*(mod) + getModulo(), Math.sin(Math.PI-anguloOffseted)*(mod), puntoBorde.x, puntoBorde.y);
		//System.out.println("Curva");
	}
	
	public void dibujarCurva() {
		g2d.setTransform(recta);
		//System.out.println("Dibujando curva");
		//System.out.println("X= "+x1.toString()+" Y="+y1.toString());
		//g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.draw(curva);
		g2d.setTransform(rst);
	}
	
	public java.lang.Double angulo() {
		
		//System.out.println("Entre: x1="+x1.toString()+" y1="+y1.toString());
		//System.out.println("       x2="+x2.toString()+" y2="+y2.toString());
		
		java.lang.Double offset = (x1<x2)? 0 : Math.PI;
		java.lang.Double angulo=Math.atan((y2-y1)/(x2-x1)) + offset;
		//System.out.println("Angulo calculado : alfa="+angulo.toString());
		return angulo;
	}
	
	public java.lang.Double getModulo() {
		return Math.sqrt(Math.pow(x2-x1,2)+ Math.pow(y2-y1,2));
	}
	
	/*private void debugPuntos() {
		System.out.println("Dibujando puntos debug1: x="+e1.centrox.toString()+" y="+e2.centroy.toString());
		System.out.println("Dibujando puntos debug2: x="+e2.centrox.toString()+" y="+e2.centroy.toString());
		g2d.setColor(Color.RED);
		g2d.fill(new Ellipse2D.Double(e1.centrox, e1.centroy, 15, 15));
		g2d.fill(new Ellipse2D.Double(e2.centrox, e2.centroy, 15, 15));
		g2d.setColor(color);
	}*/
	
	/*private void debugLineas() {
		g2d.setColor(new Color(127,127,127, 100));
		g2d.draw(new Line2D.Double(x1, y1, x2, y2));
		g2d.setColor(color);
	}*/
	
	public void updateCoord() {
		//System.out.println("Nodo 1: "+e1.nombre);
		//System.out.println("Nodo 2: "+e2.nombre);
		this.x1=e1.centrox;
		this.y1=e1.centroy;
		this.x2=e2.centrox;
		this.y2=e2.centroy;
		this.color=conect.getColor();
		//java.lang.Double mod=getModulo();
		//System.out.println("Viejas coord1: x="+x1.toString()+" y="+y1.toString());
		//System.out.println("Viejas coord2: x="+x2.toString()+" y="+y2.toString());
		//java.lang.Double mod=getModulo();
		//this.x2=(x2-x1) * (1-(Estacion2D.RADIO/mod))+x1;
		//this.y2=(y2-y1) * (1-(Estacion2D.RADIO/mod))+y1;
		//System.out.println("Nuevas coord2: x="+x2.toString()+" y="+y2.toString());
		//System.out.println("Modulo = "+mod.toString());
		//if(mod.isNaN()) System.out.println("ES NAN!!");
		//System.out.println("---------------");
		updateTransform();
		updatePunta();
		updateCurva();
	}
	
	public Flecha(Estacion2D e1, Estacion2D e2, Conexion c) {
	//public Flecha(Estacion2D e1, Estacion2D e2, Conexion con) {
		this.e1=e1;
		this.e2=e2;
		this.color=c.getColor();
		//this.conect=con;
		//this.color=conect.getColor();
		this.conect=c;
		
		
		this.mayorAngulo=Math.random()<0.5;
		this.anguloOffseted= mayorAngulo? Math.random()*25 +20 : Math.random()*(-25) -20;
		this.anguloOffseted=Math.toRadians(anguloOffseted);
	}
	
	public void dibujar(Graphics2D g) {
		g2d=g;
		rst=g2d.getTransform();
		updateCoord();
		
		dibujarCurva();
		dibujarPunta();
		
		//debugPuntos();
		//debugLineas();
		g2d.setTransform(rst);
		
	}

	public void select() {
		selected=true;
	}
	public void unselect() {
		selected=false;
	}
}
