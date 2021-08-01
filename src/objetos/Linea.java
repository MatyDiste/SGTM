package objetos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Linea implements Comparable<Linea>{
	//TODO NO TERMINADO!
	public static HashSet<Linea> listaLineas=new HashSet<Linea>();
	public static Boolean borrarLinea(Linea e) {
		return listaLineas.remove(e);
		//TODO Comunicar DAO la eliminacion de e
	}
	
	/*
	public static short AZUL=0;
	public static short ROJO=1;
	public static short AMARILLO=2;
	public static short VERDE=3;
	public static short VIOLETA=4;
	public static short NARANJA=5;
	public static short NEGRO=6;
	public static short BLANCO=7;
	*/
	
	private List<Estacion> listEstaciones=new ArrayList<Estacion>();
	private HashSet<Conexion> listConexiones=new HashSet<Conexion>();
	public String nombre;
	public Color color;
	private Boolean activo=true;
	
	public Linea(String nombre, Color color, Boolean activo) {
		this.nombre = nombre;
		this.color = color;
		this.activo = activo;
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
