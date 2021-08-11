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

package objetos;

import java.awt.Color;
import java.time.LocalTime;

enum EstadoConexion {
	ACTIVA, INACTIVA
}

public class Conexion {
	
	private Estacion e1, e2;
	private Double distancia;
	private LocalTime duracion;
	private Integer cantMaxPasajeros;
	private EstadoConexion estado;
	private Double costo;
	private Linea linea;

	public Conexion(Estacion a, Estacion b, Linea l) {
		e1=a;
		e2=b;
		linea=l;
	}
	
	public void eliminar() {
		e1=null;
		e2=null;
		this.deshabilitar();
	}
	
	public Color getColor() {
		return linea.getColor();
	}
	
	//METODOS GETTERS AND SETTERS
	
	public Estacion getE1() {
		return e1;
	}

	public void setE1(Estacion e1) {
		this.e1 = e1;
	}

	public Estacion getE2() {
		return e2;
	}

	public void setE2(Estacion e2) {
		this.e2 = e2;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public LocalTime getDuracion() {
		return duracion;
	}

	public void setDuracion(LocalTime duracion) {
		this.duracion = duracion;
	}

	public Integer getCantMaxPasajeros() {
		return cantMaxPasajeros;
	}

	public void setCantMaxPasajeros(Integer cantMaxPasajeros) {
		this.cantMaxPasajeros = cantMaxPasajeros;
	}
	
	public String estado() {
		return this.estado.name();
	}
	
	public void deshabilitar() {
		this.estado=EstadoConexion.INACTIVA;
	}
	public void habilitar() {
		this.estado=EstadoConexion.ACTIVA;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}
	
	
}
