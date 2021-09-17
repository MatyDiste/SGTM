package objetos;

import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import conexionDB.GestorLineaPostgreSQLDAO;
import elementosSwing.grafo2D.PanelGrafo;

enum EstadoLinea {
	ACTIVA, INACTIVA
}

public class Linea implements Comparable<Linea>{

	private static GestorLineaPostgreSQLDAO gestorLinea = new GestorLineaPostgreSQLDAO();
	public static final Short COLECTIVO=0;
	public static final Short TREN=1;
	public static final Short SUBTERRANEO=2;
	public static HashSet<Linea> listLineas=new HashSet<Linea>();
	private static short contadorId;
	
	public static Linea buscarID(short id) throws NoSuchElementException{
		System.out.println("Buscando id "+id);
		return listLineas.stream().filter(l -> l.id==id).findAny().get();
	}
	
	public static void cargarDB() {
		gestorLinea.recuperarEntidades();
	}
	public static void actualizarDB(Linea l) {
		gestorLinea.actualizarEntidad(l);
	}
	
	public static Boolean borrarLinea(Linea e) {
		gestorLinea.eliminarEntidad(e.id);
		return listLineas.remove(e);
	}
	private static void incrementarContador() {
		contadorId++;
	}
	public static short getContadorId() {
		return contadorId;
	}
	public static void setContadorId(short id) {
		contadorId=id;
	}
	public static Boolean nombreDisponible(String s) {
		return !listLineas.stream().anyMatch(l -> l.getNombre().equals(s));
	}
	
	
	private LinkedList<Conexion> listConexiones=new LinkedList<Conexion>();
	private short id;
	private String nombre;
	private Color color;
	private EstadoLinea estado;
	private Short tipo=0;
	private Integer cantidadDeEstacionesEnDB = 0;
	private Integer cantidadDeConexionesEnDB = 0;
	
	public Linea(String nombre, Color color, Boolean estado, Short tipo) 
			throws NombreOcupadoException {
		//Programa
		if (nombreDisponible(nombre)) {
			if (tipo>-1 && tipo<3) {
				this.id = contadorId;
				incrementarContador();
				this.nombre = nombre;
				this.color = color;
				this.estado = (estado) ? EstadoLinea.ACTIVA : EstadoLinea.INACTIVA;
				this.tipo=tipo;
				listLineas.add(this);
				gestorLinea.insertarEntidad(this);
			}
			else throw new NombreOcupadoException("Mal argumento tipo. Debe estar entre 0 y 2");
		}
		else throw new NombreOcupadoException(nombre);
	}
	
	public Linea(short id, String nombre, Color color, String estado, short tipo) {
		//DB
		this.id = id;
		this.nombre = nombre;
		this.color = color;
		if(estado.equals("ACTIVA")) {
			this.estado = EstadoLinea.ACTIVA;
		}
		else {
			this.estado = EstadoLinea.INACTIVA;
		}
		this.tipo=tipo;
		boolean repetido = false;
		for(Linea l: Linea.listLineas) {
			if(this.equals(l)) {
				repetido=true;
			}
		}
		if(!repetido) {
			listLineas.add(this);
		}
	}
	
	public void addConexionNODB(Conexion c) {
		listConexiones.add(c);
	}
	public void addConexion(Conexion c) {
		listConexiones.add(c);
		gestorLinea.actualizarEntidad(this);
	}
	
	//METODOS GETTERS AND SETTERS

	public static Linea getLineaPorNombre(String n) {
		return listLineas.stream().filter(lin -> lin.getNombre().equals(n)).findAny().get();
	}
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public static HashSet<Linea> getListaLineas() {
		return listLineas;
	}
	public static void setListaLineas(HashSet<Linea> listaLineas) {
		Linea.listLineas = listaLineas;
	}
	public LinkedList<Conexion> getListConexiones() {
		return listConexiones;
	}
	public void setListConexiones(LinkedList<Conexion> listConexiones) {
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
	public Short getTipo() {
		return tipo;
	}
	public void setTipo(Short tipo) {
		this.tipo = tipo;
	}
	public Integer getCantidadDeEstacionesEnDB() {
		return cantidadDeEstacionesEnDB;
	}
	public void setCantidadDeEstacionesEnDB(Integer cantidadDeEstacionesEnDB) {
		this.cantidadDeEstacionesEnDB = cantidadDeEstacionesEnDB;
	}
	public Integer getCantidadDeConexionesEnDB() {
		return cantidadDeConexionesEnDB;
	}
	public void setCantidadDeConexionesEnDB(Integer cantidadDeConexionesEnDB) {
		this.cantidadDeConexionesEnDB = cantidadDeConexionesEnDB;
	}
	
	//Otros métodos
	
	public void activar() {
		this.estado=EstadoLinea.ACTIVA;
		gestorLinea.actualizarEntidad(this);
		PanelGrafo.repintarGrafo();
		//TODO Fijarse que las conexiones actualicen bien sus colores
	}
	public void inactivar() {
		this.estado=EstadoLinea.INACTIVA;
		gestorLinea.actualizarEntidad(this);
		PanelGrafo.repintarGrafo();
		//TODO lo mismo que activar()
	}
	public void quitarRecorrido() {
		@SuppressWarnings("unchecked")
		LinkedList<Conexion> aux= (LinkedList<Conexion>)listConexiones.clone();
		aux.forEach(c -> c.eliminar());
		listConexiones.clear();
		aux.clear();
		aux=null;
		gestorLinea.actualizarEntidad(this);
	}
	public void quitarConexion(Conexion c) {
		listConexiones.remove(c);
		this.inactivar();
		gestorLinea.actualizarEntidad(this);
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
		estado=EstadoLinea.INACTIVA;
		Linea.borrarLinea(this);
	}
	public String toString() {
		return this.nombre;
	}
	public boolean equals(Linea l) {
		return this.nombre.equalsIgnoreCase(l.nombre) || this.id==l.id;
	}
	@Override
	public boolean equals(Object o) {
		Linea l = (Linea) o;
		if(id == l.id) {
			return true;
		}
		else {
			return false;
		}
	}
}
