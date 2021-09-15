package objetos;

import java.awt.Color;
import java.util.HashSet;

import ConexionDB.GestorConexionPostgreSQLDAO;
import elementosSwing.grafo2D.Flecha;

enum EstadoConexion {
	ACTIVA, INACTIVA
};

public class Conexion{
	
	private static GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
	public static HashSet<Conexion> listConexiones=new HashSet<Conexion>(); 
	private static short contadorId;
	private short id;
	private Estacion e1, e2;
	private Flecha flecha;
	private Double distancia;
	private Double duracion;
	private Integer cantMaxPasajeros;
	private EstadoConexion estado;
	private Double costo;
	private Linea linea;
	private Boolean seleccionado=false;
	
	public static void cargarDB() {
		gestorConexion.recuperarEntidades();
	}
	public static void actualizarDB(Conexion c) {
		gestorConexion.actualizarEntidad(c);
	}

	//Constructor DEBUG!!!!
	
	public Conexion(Estacion a, Estacion b, Linea l) {
		a.addConexion(this);
		b.addConexion(this);
		setId(contadorId);
		incrementarContador();
		e1=a;
		e2=b;
		linea=l;
		estado=l.estado().equals("ACTIVA")? EstadoConexion.ACTIVA : EstadoConexion.INACTIVA;
		flecha =new Flecha(a, b, this);
		distancia=Math.random()*100;
		duracion=Math.random()*50;
		cantMaxPasajeros=(int)(Math.random()*200);
		costo=Math.random()*100;
		listConexiones.add(this);
		gestorConexion.insertarEntidad(this);
		//System.out.println("Creada conexion entre "+a.getNombre()+" --> "+b.getNombre());
	}
	
	//END Constructor DEBUG!!!!
	public Conexion(Estacion a, Estacion b, Linea l, Double dist, Double durMinutos, Integer cantPasajeros, Double precio) {
		setId(contadorId);
		incrementarContador();
		e1=a;
		e2=b;
		linea=l;
		estado=l.estado().equals("ACTIVA")? EstadoConexion.ACTIVA : EstadoConexion.INACTIVA;
		flecha =new Flecha(a, b, this);
		distancia=dist;
		duracion=durMinutos;
		cantMaxPasajeros=cantPasajeros;
		costo=precio;
		listConexiones.add(this);
		gestorConexion.insertarEntidad(this);
		e1.addConexion(this);
		e2.addConexion(this);
		linea.addConexion(this);
		//System.out.println("Creada conexion entre "+a.getNombre()+" --> "+b.getNombre());
	}
	
	public Conexion(short idConexion, Double distancia, Double duracion, 
			Integer capacidadMaxPasajeros, String estado, Double costo, 
			Estacion estacion1, Estacion estacion2, Linea linea) {
		this.id=idConexion;
		this.distancia=distancia;
		this.duracion=duracion;
		this.cantMaxPasajeros=capacidadMaxPasajeros;
		if (estado.equals("ACTIVA")) {
			this.estado = EstadoConexion.ACTIVA;
		}
		else {
			this.estado = EstadoConexion.INACTIVA;
		}
		this.costo=costo;
		this.e1 = estacion1;
		this.e2 = estacion2;
		this.linea = linea;
		this.flecha = new Flecha(estacion1, estacion2, this);
		boolean repetido = false;
		for(Conexion c: Conexion.listConexiones) {
			if(this.equals(c)) {
				repetido=true;
			}
		}
		if(!repetido) {
			listConexiones.add(this);
		}
		this.e1.getListConexiones().add(this);
		this.e2.getListConexiones().add(this);
		this.linea.getListConexiones().add(this);
		this.linea.getListEstaciones().add(e1);
		this.linea.getListEstaciones().add(e2);
		System.out.println("Añadida conexion a estacion " + e1.getNombre());
		System.out.println("Añadida conexion a estacion " + e2.getNombre());
	}
	
	private static void incrementarContador() {
		contadorId++;
	}
	
	public void eliminar() {
		gestorConexion.eliminarEntidad(this.id);
		e1.quitarConexion(this);
		e2.quitarConexion(this);
		flecha.eliminar();
		flecha=null;
		e1=null;
		e2=null;
		this.deshabilitar();
		listConexiones.remove(this);
		Estacion.generarPageRank(200);
	}
	
	public Color getColor() {
		return linea.getColor();
	}
	
	//METODOS GETTERS AND SETTERS
	
	public static short getContadorId() {
		return contadorId;
	}
	
	public static void setContadorId(short id) {
		contadorId=id;
	}
	
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}
	
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
		if(this.estado==EstadoConexion.ACTIVA && this.linea.estado().equals("ACTIVA") && this.e1.getEstado().equals("OPERATIVA") && this.e2.getEstado().equals("OPERATIVA")) {
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
	@Override
	public boolean equals(Object o) {
		Conexion c = (Conexion) o;
		if(id == c.id) {
			return true;
		}
		else {
			return false;
		}
	}
	public Short getTipo() {
		return linea.getTipo();
	}
}
