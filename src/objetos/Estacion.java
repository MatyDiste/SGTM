package objetos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import elementosSwing.grafo2D.Estacion2D;
import elementosSwing.grafo2D.PanelGrafo;

enum EstadoEstacion {
	OPERATIVA, EN_MANTENIMIENTO
}

public class Estacion implements Comparable<Estacion>{
	
	public static HashSet<Estacion> listEstaciones=new HashSet<Estacion>(); 
	public static Short contadorId=1001;
	public static final Double D_PAGERANK=0.5d;
	private static Boolean borrarEstacion(Estacion e) { 
		PanelGrafo.quitarEstacion(e.getE2d());
		return listEstaciones.remove(e);
		//TODO Comunicar DAO la eliminacion de e
	}

	/*
	 * TODO
	 * Comunicarse con EstacionDAO para cargar todas las estaciones (método estático)
	 */
	public static Boolean nombreDisponible(String s) {
		return !listEstaciones.stream().anyMatch(e -> e.getNombre().equals(s));
	}
	
	private static void incrementarContador() {
		contadorId++;
	}
	public static Short getContadorId() {
		return contadorId;
	}
	public static Estacion buscarID(short id) throws NoSuchElementException{
		return listEstaciones.stream().filter(e -> e.id==id).findAny().get();
	}
	public static Estacion buscarNombre(String name) throws NoSuchElementException{
		return listEstaciones.stream().filter(e -> e.nombre.equals(name)).findAny().get();
	}
	public static List<Estacion> buscarHorarioApertura(LocalTime lt){
		return listEstaciones
				.stream()
				.filter( (e) -> e.getHorarioApertura().equals(lt))
				.collect(Collectors.toList());
	}
	public static List<Estacion> buscarHorarioCierre(LocalTime lt){
		return listEstaciones
				.stream()
				.filter( (e) -> e.getHorarioCierre().equals(lt))
				.collect(Collectors.toList());
	}
	public static void generarPageRank(Integer iteraciones) {
		listEstaciones.forEach(e -> e.pagerank=1d);
		for(int i=0; i<iteraciones; i++) {
			listEstaciones.forEach(e ->{
				e.calculatePageRank();
			});
		}
	}
	/*---------------------------------------------------*/
	private Short id;
	private String nombre;
	private LocalTime horarioApertura;
	private LocalTime horarioCierre;
	private EstadoEstacion estado;
	private LocalDate fechaUltimoMantenimiento=LocalDate.now();
	private HashSet<Conexion> listConexiones=new HashSet<Conexion>();
	private Double pagerank = 1.0;
	private Double pesoTotal = 0.0;
	private ArrayList<Mantenimiento> listaMantenimientos=new ArrayList<Mantenimiento>();
	private Estacion2D e2d;
	public Double posx=Math.random()*600+50;
	public Double posy=Math.random()*450+50;
	
	public Estacion(String nombre, LocalTime horarioApertura, LocalTime horarioCierre, Boolean estado) throws NombreOcupadoException {
		
		if(nombreDisponible(nombre)) {
			this.id = contadorId;
			incrementarContador();
			this.nombre = nombre;
			this.horarioApertura = horarioApertura;
			this.horarioCierre = horarioCierre;
			this.estado = (estado) ? EstadoEstacion.OPERATIVA : EstadoEstacion.EN_MANTENIMIENTO;
			this.e2d = new Estacion2D(this);
			this.posx = Math.random() * 500 + 50;
			this.posy = Math.random() * 500 + 50;
			listEstaciones.add(this);
		}
		else throw new NombreOcupadoException(nombre);
		
	}
	/*--------------------------------------------------*/

	
	
	//METODOS GETTERS AND SETTERS
	public Estacion2D getE2d() {
		return e2d;
	}
	
	public void setE2d(Estacion2D e2d) {
		this.e2d = e2d;
	}
	
	public Short getId() {
		return id;
	}
	public void setId(Short id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public LocalTime getHorarioApertura() {
		return horarioApertura;
	}
	public void setHorarioApertura(LocalTime horarioApertura) {
		this.horarioApertura = horarioApertura;
	}
	public LocalTime getHorarioCierre() {
		return horarioCierre;
	}
	public void setHorarioCierre(LocalTime horarioCierre) {
		this.horarioCierre = horarioCierre;
	}
	public String getEstado() {
		return estado.name();
	}
	public void setMantenimiento(String descripcion) {
		this.estado = EstadoEstacion.EN_MANTENIMIENTO;
		listaMantenimientos.add(new Mantenimiento(descripcion));
	}
	public void setMantenimiento() {
		this.estado = EstadoEstacion.EN_MANTENIMIENTO;
		listaMantenimientos.add(new Mantenimiento());
	}
	public void setOperativa(String descripcion) {
		Mantenimiento ultimoMantenimiento = listaMantenimientos.get(listaMantenimientos.size()-1);
		ultimoMantenimiento.finMantenimiento(descripcion);
		this.estado = EstadoEstacion.OPERATIVA;
	}
	public void setOperativa() {
		Mantenimiento ultimoMantenimiento = listaMantenimientos.get(listaMantenimientos.size()-1);
		ultimoMantenimiento.finMantenimiento();
		this.estado = EstadoEstacion.OPERATIVA;
	}
	public LocalDate getFechaUltimoMantenimiento() {
		return fechaUltimoMantenimiento;
	}
	public void setFechaUltimoMantenimiento(LocalDate fechaUltimoMantenimiento) {
		this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
	}
	public HashSet<Conexion> getListConexiones() {
		return listConexiones;
	}
	
	public Double getPagerank() {
		return pagerank;
	}
	public void setPagerank(Double pagerank) {
		this.pagerank = pagerank;
	}
	public Double getPesoTotal() {
		return pesoTotal;
	}
	public void setPesoTotal(Double pesoTotal) {
		this.pesoTotal = pesoTotal;
	}
	public void addConexion(Conexion c) {
		listConexiones.add(c);
	}
	public void quitarConexion(Conexion c) {
		listConexiones.remove(c);
	}
	/*--------------------------------------------------*/
	public Integer cantSalientes() {
		return (int)listConexiones.stream()
				.filter(c -> c.getE1().equals(this))
				.count();
	}
	public HashSet<Estacion> listEstacionesEntrantes(){
		return (HashSet<Estacion>)listConexiones.stream()
				.filter(c -> c.getE2().equals(this))
				.map(c -> c.getE1())
				.collect(Collectors.toSet());
	}
	public void calculatePageRank() {
		this.pagerank = D_PAGERANK;
		
		this.listEstacionesEntrantes().forEach(e -> {
			this.pagerank += D_PAGERANK * (e.pagerank/e.cantSalientes());
		});
	}
	
	public LinkedList<Recorrido> getRecorridos(Estacion hasta) {
		LinkedList<Recorrido> ret=new LinkedList<Recorrido>();
		depthFirst(ret, hasta, new LinkedList<Conexion>());
		return ret;
	}
	private void depthFirst(LinkedList<Recorrido> lista, Estacion hasta, LinkedList<Conexion> conexiones) {
		
		if(this.equals(hasta)) {
			lista.addLast(new Recorrido(conexiones));
		}
		else {
			listConexiones.forEach(c -> {
				if(c.getE1().equals(this) && c.estado().equals("ACTIVA") && !conexiones.contains(c)) { 
					conexiones.addLast(c);
					c.getE2().depthFirst(lista, hasta, conexiones);
					conexiones.removeLast();
				}
			});
		}
		
	}
	
	public void select() {
		this.e2d.select();
	}
	public void unselect() {
		this.e2d.unselect();
	}
	
	public boolean equals(Estacion o) { 
		return this.nombre.equals(o.getNombre()) || this.id==o.id;
	}
	@Override
	public int hashCode() { 
		return nombre.hashCode();
		
	}
	public void eliminar() { 
		@SuppressWarnings("unchecked")
		HashSet<Conexion> aux= (HashSet<Conexion>)listConexiones.clone();
		aux.stream()
					  .forEach(c ->{
						  c.getLinea().quitarConexion(c);
						  c.eliminar();
					  });
		listConexiones.clear();
		listConexiones=null;
		Estacion.borrarEstacion(this);
	}
	@Override
	public int compareTo(Estacion o) {
		return this.getNombre().compareToIgnoreCase(o.getNombre());
	}
	public String toString() {
		return this.nombre;
	}
}
