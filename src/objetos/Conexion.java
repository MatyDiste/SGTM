package objetos;

import java.awt.Color;
import java.time.LocalTime;

import elementosSwing.grafo2D.Flecha;

enum EstadoConexion {
	ACTIVA, INACTIVA
};

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
		estado=l.estado().equals("ACTIVA")? EstadoConexion.ACTIVA : EstadoConexion.INACTIVA;
		Flecha f=new Flecha(a, b, this);
		System.out.println("Creada conexion");
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
