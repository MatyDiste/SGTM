package objetos;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

public interface Estacion extends Comparable<Estacion>{
	/*
	 * Asi son las interfaces que debemos agregar antes de hacer la implementacion de cada objeto
	 * Este es un ejemplo, luego mas funcionalidades se van a ir agregando de acuerdo se necesiten
	 * Para agregar modificaciones en Estacion, se debe modificar Estacion (interfaz) y EstacionImpl (implementacion)
	 */
	
	//Todos los atributos y métodos estáticos deberian implementarse ya en la interfaz
	public static HashSet<Estacion> listaEstaciones=new HashSet<Estacion>(); //Util para que la clase se encargue de tener toda la lista de estaciones (al ser hashset, debe estar implementado hashcode e equals)
	public static boolean borrarEstacion(Estacion e) { 
		/* Estos son metodos estaticos que funcionan sobre todas las estaciones, son muy utiles
		 * por ejemplo borrar, buscar.
		 */
		return listaEstaciones.remove(e);
		//TODO Comunicar DAO la eliminacion de e
	}
	
	//Estos son atributos estaticos que reemplazarian el uso de alguna enumeracion.
	//En lugar de crear un Enum para esto, se crea algun tipo de dato que sea liviano (bool, byte, short)
	//y se le asignan valores diferentes para cada valor de la enumeracion
	//En este caso como son dos estados, decidi que sea true o false, pero por ejemplo Linea
	//tiene colores de tipo short, que cada color se representa por un numero del 1-7.
	//Esto se usa para despues poder llamar a instancia.setMantenimiento(Estacion.OPERATIVA), en lugar de instancia.setColor(3) y acordarte de que 3 significa Azul por ejemplo
	public static boolean EN_MANTENIMIENTO=true;
	public static boolean OPERATIVA=false;
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
	
	
	/*
	 * Terminamos con los metodos y atributos de clase, ahora con los abstractos de instancia
	 * Estos son los getters y setters que debe tener.
	 * Notar que no hay setID(), esto es porque no debe ser posible cambiar el ID, ya que esto va a ser PK en la BD
	 */
	public short getID();
	public String getNombre();
	public LocalTime getHorarioApertura();
	public LocalTime getHorarioCierre();
	public boolean enMantenimiento();
	
	public void setNombre(String nuevo);
	public void setHorarioApertura(LocalTime lt);
	public void setHorarioCierre(LocalTime lt);
	public void setMantenimiento(boolean enMante);
	
	/*
	 * Metodos que deben implementarse:
	 * equals() <- Para poder describir que una estacion es igual a otra si... (en este caso, debe ser si sus ID son iguales unicamente, aunque podrian ser mas cosas tambien)
	 * hashCode() <- Debe basarse en los mismos atributos que utilice equals(), esto para el HashSet
	 * eliminar() <- Método de instancia de eliminacion. Debe eliminar a la estacion de la listaEstaciones (llamar a Estacion.eliminar(this), y a todos los objetos que mantienen alguna referencia a el mismo)
	 * luego, al dejar de referenciar a esa estacion en donde usemos este metodo, el garbage collector va a eliminarlo porque no deben existir mas referencias.
	 * compareTo() <- Debe devolver -1 si this es menor
	 */
	public boolean equals(Estacion o); //Deberia utilizar lo mismo que hash (ID, nombre?)
	@Override
	public int hashCode(); //Debería diferenciar entre estaciones de acuerdo a sus atributos (ID, nombre?)
	public boolean eliminar(); //Debe llamar al metodo estatico con this
	@Override
	public int compareTo(Estacion o);
}
