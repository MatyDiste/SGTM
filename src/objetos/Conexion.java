package objetos;

public class Conexion {
	
	public Estacion e1, e2;
	public Linea linea;
	
	
	
	public Conexion(Estacion a, Estacion b, Linea l) {
		e1=a;
		e2=b;
		linea=l;
	}
	
	public Color getColor() {
		return linea.getColor();
	}
	
	
}
