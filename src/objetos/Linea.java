package objetos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Linea implements Comparable<Linea>{
	//TODO NO TERMINADO!
	public static HashSet<Linea> listLineas=new HashSet<Linea>();
	public static Boolean borrarLinea(Linea e) {
		return listLineas.remove(e);
		//TODO Comunicar DAO la eliminacion de e
	}
	
	private List<Estacion> listEstaciones=new ArrayList<Estacion>();
	private HashSet<Conexion> listConexiones=new HashSet<Conexion>();
	public String nombre;
	public Color color;
	private Boolean activo=true;
	
	public Linea(String nombre, Color color, Boolean activo) {
		this.nombre = nombre;
		this.color = color;
		this.activo = activo;
		listLineas.add(this);
	}
	
	public Boolean activo() {
		return activo;
	}
	public void activar() {
		this.activo=true;
		//TODO Fijarse que las conexiones actualicen bien sus colores
	}
	public void inactivar() {
		this.activo=false;
		//TODO lo mismo que activar()
	}
	public int compareTo(Linea l) {
		//???? Ni idea xd
		return 0;
	}
	public void setRecorrido(List<Estacion> ordenEstaciones) {
		this.listEstaciones=ordenEstaciones;
		
	}
	public void setConexiones(HashSet<Conexion> conex) {
		this.listConexiones=conex;
		
	}
	
	
}
