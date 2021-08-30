package elementosSwing.grafo2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import objetos.Conexion;
import objetos.Estacion;
import objetos.Linea;



public class Flecha {
	
	
	private static final int SELECTEDWIDTH=6;
	private static final int UNSELECTEDWIDTH=2;
	private static final float[] dash= {3f, 3f};
	private static final float dashPhase=0f;
	
	public Conexion conect;
	public Boolean selected=false;
	public java.lang.Double x1,x2,y1,y2;
	public Color color;
	public Graphics2D g2d;
	public Estacion2D e1,e2;
	public final java.lang.Double xoffsetRandom=Math.random();
	public final java.lang.Double yoffsetRandom=Math.random();
	public Point.Double puntoBorde=new Point.Double();
	public java.lang.Double anguloOffseted;
	public Boolean mayorAngulo;
	public Path2D.Double curva=new Path2D.Double();
	public Path2D.Double punta=new Path2D.Double();
	public AffineTransform rst;
	public AffineTransform recta;
	
	public void eliminar() {
		conect=null;
		selected=false;
		x1=0d; x2=0d; y1=0d; y2=0d;
		color=null;
		g2d=null;
		e1.quitarFlechaSalida(this);
		e2.quitarFlechaLlegada(this);
		e1=null;
		e2=null;
		curva=null;
		punta=null;
		rst=null;
		recta=null;
		//System.out.println("Eliminando flecha");
	}
	
	private BasicStroke getStroke() {
		Integer size=selected? SELECTEDWIDTH : UNSELECTEDWIDTH;
		return conect.estado().equals("ACTIVA")? new BasicStroke(size) : new BasicStroke(size, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, dash, dashPhase); 
		//return new BasicStroke(size);
	}
	
	public void updateTransform() {
		//Mantiene la transformacion de manera que E1 y E2 esten sobre la recta y=0
		
		this.color=conect.getColor();
		g2d.setStroke(this.getStroke());
		g2d.setColor(color);
		g2d.translate(x1, y1);
		//System.out.println("Traslacion x:"+x1+" | y:"+y1);
		g2d.rotate(angulo());
		recta=g2d.getTransform();
		
		
		
		g2d.setTransform(rst);
	}
	
	public void updatePunta() {
		punta=new Path2D.Double();
		
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
		g2d.translate(puntoBorde.x, puntoBorde.y); //System.out.println("Puntoborde x:"+puntoBorde.x+" y: "+puntoBorde.y);
		g2d.rotate(-anguloOffseted);
		g2d.fill(punta);
		
		
		
		g2d.setTransform(rst);
	}
	
	public void updateCurva() {
		curva=new Path2D.Double();
		java.lang.Double mod=getModulo()/2.3;
		curva.moveTo(Math.cos(anguloOffseted)*Estacion2D.RADIO, Math.sin(anguloOffseted)*Estacion2D.RADIO);
		curva.curveTo(Math.cos(anguloOffseted)*(mod), Math.sin(anguloOffseted)*(mod), Math.cos(Math.PI-anguloOffseted)*(mod) + getModulo(), Math.sin(Math.PI-anguloOffseted)*(mod), puntoBorde.x, puntoBorde.y);
		//System.out.println("Curva x:"+Math.cos(anguloOffseted)*Estacion2D.RADIO +" | y:"+Math.sin(anguloOffseted)*Estacion2D.RADIO);
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
		
		java.lang.Double offset = (x1<=x2)? 0 : Math.PI;
		java.lang.Double angulo=Math.atan((y2-y1)/(x2-x1)) + offset;
		//System.out.println("Angulo calculado : alfa="+angulo.toString());
		return angulo;
	}
	
	public java.lang.Double getModulo() {
		return Math.sqrt(Math.pow(x2-x1,2)+ Math.pow(y2-y1,2));
	}
	
	
	public void updateCoord() {
		//System.out.println("Nodo 1: "+e1.nombre);
		//System.out.println("Nodo 2: "+e2.nombre);
		this.x1=e1.centrox;
		this.y1=e1.centroy;
		this.x2=e2.centrox;
		this.y2=e2.centroy;
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
		updatePunta();
		updateCurva();
	}
	
	public Flecha(Estacion a, Estacion b, Conexion c) {
	//public Flecha(Estacion2D e1, Estacion2D e2, Conexion con) {
		this.e1=a.getE2d();
		this.e2=b.getE2d();
		this.color=c.getColor();
		//this.conect=con;
		//this.color=conect.getColor();
		this.conect=c;
		
		
		this.mayorAngulo=Math.random()<0.5;
		this.anguloOffseted= mayorAngulo? Math.random()*55 +10 : Math.random()*(-55) -10;
		this.anguloOffseted=Math.toRadians(anguloOffseted);
		
		e1.addSalida(this);
		e2.addLlegada(this);
		//System.out.println("Añadida flecha");
	}
	
	public void dibujar(Graphics2D g) {
		//System.out.println("Mostrando flecha");
		g2d=g;
		rst=g2d.getTransform();
		updateCoord();
		updateTransform();
		
		dibujarCurva();
		dibujarPunta();
		
		
		//debugPuntos();
		//debugLineas();
		g2d.setTransform(rst);
		
	}

	public void select() {
		selected=true;
		PanelGrafo.repintarGrafo();
	}
	public void unselect() {
		selected=false;
		PanelGrafo.repintarGrafo();
	}
}
