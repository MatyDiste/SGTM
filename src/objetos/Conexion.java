package objetos;

import java.awt.Color;
import java.time.LocalTime;

public class Conexion {
	
	public Estacion e1, e2;
	public Linea linea;
	private Boolean habilitado=true;
	public Double distancia;
	public LocalTime duracion;
	
	public void eliminar() {
		e1=null;
		e2=null;
		linea.quitar(this); //TODO
		habilitado=false;
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
		this.habilitado=false;
	}
	public void habilitar() {
		this.habilitado=true;
	}
	public Boolean habilitado() {
		return this.habilitado;
	}
	
	
}
