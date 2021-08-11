package objetos;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

enum EstadoEstacion {
	OPERATIVA, EN_MANTENIMIENTO
}

public class Estacion implements Comparable<Estacion>{
	
	//Todos los atributos y métodos estáticos deberian implementarse ya en la interfaz
	public static HashSet<Estacion> listEstaciones=new HashSet<Estacion>(); //Util para que la clase se encargue de tener toda la lista de estaciones (al ser hashset, debe estar implementado hashcode e equals)
	private static Boolean borrarEstacion(Estacion e) { 
		/* Estos son metodos estaticos que funcionan sobre todas las estaciones, son muy utiles
		 * por ejemplo borrar, buscar.
		 */
		return listEstaciones.remove(e);
		//TODO Comunicar DAO la eliminacion de e
	}

	/*
	 * TODO
	 * Comunicarse con EstacionDAO para cargar todas las estaciones (método estático)
	 */
	
	//Mas funciones sobre la lista, esta vez para buscar de acuerdo a algun atributo
	public static List<Estacion> buscarID(short id){
		return listEstaciones
				.stream()
				.filter( (e) -> e.getId()==id)
				.collect(Collectors.toList());
	}
	public static List<Estacion> buscarNombre(String name){
		return listEstaciones
				.stream()
				.filter( (e) -> e.getNombre().equals(name))
				.collect(Collectors.toList());

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
	
	public Estacion(Short id, String nombre, LocalTime horarioApertura, LocalTime horarioCierre, EstadoEstacion estado) {
		this.id=id;
		this.nombre=nombre;
		this.horarioApertura=horarioApertura;
		this.horarioCierre=horarioCierre;
		this.estado=estado;
		listEstaciones.add(this);
	}
	
	public List<Estacion> subgrafoInmediato() {
		return this.listConexiones.stream()
				  .filter((c) -> c.getE1().equals(this))
		          .map(c -> c.getE2())
		          .collect(Collectors.toList());

	}
	
	
	/*
	 * Terminamos con los metodos y atributos de clase, ahora con los abstractos de instancia
	 * Estos son los getters y setters que debe tener.
	 * Notar que no hay setID(), esto es porque no debe ser posible cambiar el ID, ya que esto va a ser PK en la BD
	 */
	
	private Short id;
	private String nombre;
	private LocalTime horarioApertura;
	private LocalTime horarioCierre;
	private EstadoEstacion estado;
	private java.util.Date fechaUltimoMantenimiento; //Por default, el ultimo mantenimiento es su dia de ingreso
	private HashSet<Conexion> listConexiones=new HashSet<Conexion>();
	private Double pagerank = 1.0; //?
	private Double pesoTotal = 0.0; //?
	private ArrayList<Mantenimiento> listaMantenimientos;
	
	//METODOS GETTERS AND SETTERS
	
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
	public java.util.Date getFechaUltimoMantenimiento() {
		return fechaUltimoMantenimiento;
	}
	public void setFechaUltimoMantenimiento(java.util.Date fechaUltimoMantenimiento) {
		this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
	}
	public HashSet<Conexion> getListConexiones() {
		return listConexiones;
	}
	public void setListConexiones(HashSet<Conexion> listConexiones) {
		this.listConexiones = listConexiones;
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
	
	/*
	 * Metodos que deben implementarse:
	 * equals() <- Para poder describir que una estacion es igual a otra si... (sus nombres son iguales)
	 * hashCode() <- Debe basarse en los mismos atributos que utilice equals(), esto para el HashSet
	 * eliminar() <- Método de instancia de eliminacion. Debe eliminar a la estacion de la listaEstaciones (llamar a Estacion.eliminar(this), y a todos los objetos que mantienen alguna referencia a el mismo)
	 * luego, al dejar de referenciar a esa estacion en donde usemos este metodo, el garbage collector va a eliminarlo porque no deben existir mas referencias.
	 * compareTo() <- Debe devolver -1 si this es menor
	 */
	public boolean equals(Estacion o) { //Deberia utilizar lo mismo que hash (ID, nombre?)
		return this.nombre.equals(o.getNombre());
	}
	@Override
	public int hashCode() { //Debería diferenciar entre estaciones de acuerdo a sus atributos (ID, nombre?)
		return nombre.hashCode();
		
	}
	public void eliminar() { //Debe llamar al metodo estatico con this
		Estacion.borrarEstacion(this);
		listConexiones.stream()
					  .forEach(f ->{
						  f.eliminar();
					  });
		listConexiones.clear();
	}
	@Override
	public int compareTo(Estacion o) {
		return this.getNombre().compareToIgnoreCase(o.getNombre());
	}
}
