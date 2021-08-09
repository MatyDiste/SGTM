package objetos;

import java.awt.Color;
import java.time.LocalTime;

public class Conexion {
	
	public Estacion e1, e2;
	public Linea linea;
	public Double distancia;
	public LocalTime duracion;
	
	public static void borrarConexion(Conexion c) {
		
		//Comunicar DAO eliminacion
	}
	
	public void eliminar() {
		borrarConexion(this);
		e1=null;
		e2=null;
	}
	
	public Conexion(Estacion a, Estacion b, Linea l) {
		e1=a;
		e2=b;
		linea=l;
	}
	
	public Color getColor() {
		return linea.color;
	}
	
	public Boolean habilitado() {
		return this.linea.activo() && this.e1.enMantenimiento() && this.e2.enMantenimiento(); 
	}
	
	
}
