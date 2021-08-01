package objetos;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

public class Estacion implements Comparable<Estacion>{
	
	//Todos los atributos y métodos estáticos deberian implementarse ya en la interfaz
	public static HashSet<Estacion> listaEstaciones=new HashSet<Estacion>(); //Util para que la clase se encargue de tener toda la lista de estaciones (al ser hashset, debe estar implementado hashcode e equals)
	public static Boolean borrarEstacion(Estacion e) { 
		/* Estos son metodos estaticos que funcionan sobre todas las estaciones, son muy utiles
		 * por ejemplo borrar, buscar.
		 */
		return listaEstaciones.remove(e);
		//TODO Comunicar DAO la eliminacion de e
	}
	
	public static final Boolean EN_MANTENIMIENTO=true;
	public static final Boolean OPERATIVA=false;
	/*
	 * TODO
	 * Comunicarse con EstacionDAO para cargar todas las estaciones (método estático)
	 */
	
	//Mas funciones sobre la lista, esta vez para buscar de acuerdo a algun atributo
	public static List<Estacion> buscarID(short id){
		return listaEstaciones
				.stream()
				.filter( (e) -> e.getID()==id)
				.toList();
	}
	public static List<Estacion> buscarNombre(String name){
		return listaEstaciones
				.stream()
				.filter( (e) -> e.getNombre().equals(name))
				.toList();
	}
	public static List<Estacion> buscarHorarioApertura(LocalTime lt){
		return listaEstaciones
				.stream()
				.filter( (e) -> e.getHorarioApertura().equals(lt))
				.toList();
	}
	public static List<Estacion> buscarHorarioCierre(LocalTime lt){
		return listaEstaciones
				.stream()
				.filter( (e) -> e.getHorarioCierre().equals(lt))
				.toList();
	}
	
	
	public List<Estacion> subgrafoInmediato(Estacion desde) {
		
	}
	
	
	/*
	 * Terminamos con los metodos y atributos de clase, ahora con los abstractos de instancia
	 * Estos son los getters y setters que debe tener.
	 * Notar que no hay setID(), esto es porque no debe ser posible cambiar el ID, ya que esto va a ser PK en la BD
	 */
	
	public Short id;
	public String nombre;
	public Integer pagerank; //?
	public Double pesoTotal; //?
	public LocalTime horarioApertura;
	public LocalTime horarioCierre;
	public java.util.Date fechaUltimoMantenimiento; //Por default, el ultimo mantenimiento es su dia de ingreso
	public Boolean mantenimiento;
	public HashSet<Conexion> listConexiones=new HashSet<Conexion>();
	
	
	
	public Short getID() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public LocalTime getHorarioApertura() {
		return horarioApertura;
	}
	public LocalTime getHorarioCierre() {
		return horarioCierre;
	}
	public Boolean enMantenimiento() {
		return mantenimiento;
	}
	
	public void setNombre(String nuevo) {
		nombre=nuevo;
	}
	public void setHorarioApertura(LocalTime lt) {
		horarioApertura=lt;
	}
	public void setHorarioCierre(LocalTime lt) {
		horarioCierre=lt;
	}
	public void setMantenimiento(Boolean enMante) {
		mantenimiento=enMante;
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
	public int compareTo(Estacion o);
}
