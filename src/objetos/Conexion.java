package objetos;

import java.awt.Color;

import elementosSwing.grafo2D.Flecha;

enum EstadoConexion {
	ACTIVA, INACTIVA
};

public class Conexion {
	
	private Estacion e1, e2;
	private Flecha flecha;
	private Double distancia;
	private Double duracion;
	private Integer cantMaxPasajeros;
	private EstadoConexion estado;
	private Double costo;
	private Linea linea;
	private Boolean seleccionado=false;

	//Constructor DEBUG!!!!
	
	public Conexion(Estacion a, Estacion b, Linea l) {
		a.addConexion(this);
		b.addConexion(this);
		e1=a;
		e2=b;
		linea=l;
		estado=l.estado().equals("ACTIVA")? EstadoConexion.ACTIVA : EstadoConexion.INACTIVA;
		flecha =new Flecha(a, b, this);
		distancia=Math.random()*100;
		duracion=Math.random()*50;
		cantMaxPasajeros=(int)(Math.random()*200);
		costo=Math.random()*100;
		//System.out.println("Creada conexion entre "+a.getNombre()+" --> "+b.getNombre());
	}
	//END Constructor DEBUG!!!!
	public Conexion(Estacion a, Estacion b, Linea l, Double dist, Double durMinutos, Integer cantPasajeros, Double precio) {
		a.addConexion(this);
		b.addConexion(this);
		e1=a;
		e2=b;
		linea=l;
		estado=l.estado().equals("ACTIVA")? EstadoConexion.ACTIVA : EstadoConexion.INACTIVA;
		flecha =new Flecha(a, b, this);
		distancia=dist;
		duracion=durMinutos;
		cantMaxPasajeros=cantPasajeros;
		costo=precio;
		
		linea.addConexion(this);
		//System.out.println("Creada conexion entre "+a.getNombre()+" --> "+b.getNombre());
	}
	
	public void eliminar() {
		e1.quitarConexion(this);
		e2.quitarConexion(this);
		flecha.eliminar();
		flecha=null;
		e1=null;
		e2=null;
		this.deshabilitar();
		Estacion.generarPageRank(200);
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

	public Double getDuracion() {
		return duracion;
	}

	public void setDuracion(Double duracion) {
		this.duracion = duracion;
	}

	public Integer getCantMaxPasajeros() {
		return cantMaxPasajeros;
	}

	public void setCantMaxPasajeros(Integer cantMaxPasajeros) {
		this.cantMaxPasajeros = cantMaxPasajeros;
	}
	
	public String estado() {
		if(this.estado==EstadoConexion.ACTIVA && this.linea.estado().equals("ACTIVA")) {
			return "ACTIVA";
		}
		else {
			return "INACTIVA";
		}
		
		
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
	public Boolean getSeleccionado() {
		return seleccionado;
	}
	public void select() {
		seleccionado=true;
		//flecha.select();
	}
	public void unselect() {
		seleccionado=false;
		//flecha.unselect();
	}
	
	
}
