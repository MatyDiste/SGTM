package objetos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

enum EstadoLinea {
	ACTIVA, INACTIVA
}

public class Linea implements Comparable<Linea>{
	
	//TODO NO TERMINADO!
	public static HashSet<Linea> listLineas=new HashSet<Linea>();
	public static Boolean borrarLinea(Linea e) {
		return listLineas.remove(e);
		//TODO Comunicar DAO la eliminacion de e
	}
	
	private List<Estacion> listEstaciones=new ArrayList<Estacion>();
	private HashSet<Conexion> listConexiones=new HashSet<Conexion>();
	private String nombre;
	private Color color;
	private EstadoLinea estado;
	
	public Linea(String nombre, Color color, Boolean estado) {
		this.nombre = nombre;
		this.color = color;
		this.estado = (estado)? EstadoLinea.ACTIVA : EstadoLinea.INACTIVA;
	}
	
	//METODOS GETTERS AND SETTERS

	public static HashSet<Linea> getListaLineas() {
		return listLineas;
	}
	public static void setListaLineas(HashSet<Linea> listaLineas) {
		Linea.listLineas = listaLineas;
	}
	public List<Estacion> getListEstaciones() {
		return listEstaciones;
	}
	public void setListEstaciones(List<Estacion> listEstaciones) {
		this.listEstaciones = listEstaciones;
	}
	public HashSet<Conexion> getListConexiones() {
		return listConexiones;
	}
	public void setListConexiones(HashSet<Conexion> listConexiones) {
		this.listConexiones = listConexiones;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String estado() {
		return estado.name();

	}
	public void activar() {
		this.estado=EstadoLinea.ACTIVA;
		//TODO Fijarse que las conexiones actualicen bien sus colores
	}
	public void inactivar() {
		this.estado=EstadoLinea.INACTIVA;
		//TODO lo mismo que activar()
	}
	
	//METODOS A IMPLEMENTAR
	
	public int compareTo(Linea l) {
		//???? Ni idea xd
		return 0;
	}
}
