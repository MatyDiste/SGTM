package objetos;

import java.awt.Color;
import java.time.LocalTime;

enum EstadoConexion {
	ACTIVA, INACTIVA
}

public class Conexion {
	
	public Estacion e1, e2;
	public Double distancia;
	public LocalTime duracion;
	private Integer cantMaxPasajeros;
	private EstadoConexion estado;
	private Double costo;
	public Linea linea;
	
	public void eliminar() {
		e1=null;
		e2=null;
		linea.quitar(this); //TODO
		this.deshabilitar();
	}
	
	public Conexion(Estacion a, Estacion b, Linea l) {
		e1=a;
		e2=b;
		linea=l;
	}
	
	public Color getColor() {
		return linea.color;
	}
	
	public void deshabilitar() {
		this.estado=EstadoConexion.INACTIVA;
	}
	public void habilitar() {
		this.estado=EstadoConexion.ACTIVA;
	}
	public String estado() {
		return this.estado.name();
	}
	
	
}
