package elementosSwing.grafo2D;

import java.awt.geom.Line2D;

@SuppressWarnings("serial")
public class Flecha extends Line2D.Double {
	
	
	public java.lang.Double getModulo() {
		return Math.sqrt(Math.pow(this.getX2()-this.getX1(),2)+ Math.pow(this.getY2()-this.getY1(),2));
	}
	
	
	public Flecha(Estacion2D e1, Estacion2D e2) {
		
		super();
		this.x1=e1.centrox;
		this.y1=e1.centroy;
		this.x2=e2.centrox;
		this.y2=e2.centroy;
		java.lang.Double mod=getModulo();
		this.x2=e2.centrox-e2.centrox*(1-e2.radio/mod);
		this.y2=e2.centroy-e2.centroy*(1-e2.radio/mod);
		setLine(x1,y1,x2,y2);
		
		
		
	}

}
