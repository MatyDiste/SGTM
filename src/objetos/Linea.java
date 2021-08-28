package objetos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import elementosSwing.grafo2D.Flecha;
import elementosSwing.grafo2D.PanelGrafo;

enum EstadoLinea {
	ACTIVA, INACTIVA
}

public class Linea implements Comparable<Linea>{
	
	//TODO NO TERMINADO!
	public static HashSet<Linea> listLineas=new HashSet<Linea>();
	private static Short contadorId=1001;
	public static Boolean borrarLinea(Linea e) {
		return listLineas.remove(e);
		//TODO Comunicar DAO la eliminacion de e
	}
	private static void incrementarContador() {
		contadorId++;
	}
	public static Short getContadorId() {
		return contadorId;
	}
	
	private List<Estacion> listEstaciones=new ArrayList<Estacion>();
	private HashSet<Conexion> listConexiones=new HashSet<Conexion>();
	private Short id;
	private String nombre;
	private Color color;
	private EstadoLinea estado;
	
	public Linea(String nombre, Color color, Boolean estado) {
		this.id=contadorId;
		incrementarContador();
		this.nombre = nombre;
		this.color = color;
		this.estado = (estado)? EstadoLinea.ACTIVA : EstadoLinea.INACTIVA;
		listLineas.add(this);
	}
	
	//METODOS GETTERS AND SETTERS
	public void addConexion(Conexion c) {
		if(listEstaciones.isEmpty()) {
			listEstaciones.add(c.getE1());
		}
		listEstaciones.add(c.getE2());
		listConexiones.add(c);
	}

	public static Linea getLineaPorNombre(String n) {
		return listLineas.stream().filter(lin -> lin.getNombre().equals(n)).findAny().get();
	}
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
		PanelGrafo.repintarGrafo();
		//TODO Fijarse que las conexiones actualicen bien sus colores
	}
	public void inactivar() {
		this.estado=EstadoLinea.INACTIVA;
		PanelGrafo.repintarGrafo();
		//TODO lo mismo que activar()
	}
	public void quitarRecorrido() {
		HashSet<Conexion> aux= (HashSet<Conexion>)listConexiones.clone();
		aux.forEach(c -> c.eliminar());
		listConexiones.clear();
		aux.clear();
		aux=null;
		
		listEstaciones.clear();
	}
	public void quitarConexion(Conexion c) {
		listConexiones.remove(c);
		this.inactivar();
	}
	public void quitarEstacion(Estacion e) {
		listEstaciones.remove(e);
		this.inactivar();
	}
	
	public void select() {
		listConexiones.forEach(f -> f.select());
		PanelGrafo.repintarGrafo();
	}
	public void unselect() {
		listConexiones.forEach(f -> f.unselect());
		PanelGrafo.repintarGrafo();
	}
	
	public int compareTo(Linea l) {
		return this.nombre.compareTo(l.nombre);
	}
	public void eliminar() {
		listConexiones.forEach(c -> c.eliminar());
		listConexiones.clear();
		listEstaciones.clear();
		estado=EstadoLinea.INACTIVA;
		Linea.borrarLinea(this);
	}
	public String toString() {
		return this.nombre;
	}
	public boolean equals(Linea l) {
		return this.nombre.equalsIgnoreCase(l.nombre);
	}
}
