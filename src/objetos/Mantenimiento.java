package objetos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;

import conexionDB.GestorMantenimientoPostgreSQLDAO;

public class Mantenimiento {
	
	private static GestorMantenimientoPostgreSQLDAO gestorMantenimiento = new GestorMantenimientoPostgreSQLDAO();
	public static HashSet<Mantenimiento> listMantenimientos=new HashSet<Mantenimiento>(); 
	private static short contadorId;
	private short id;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String descripcion;
	
	public static void borrarMantenimiento(Mantenimiento m) {
		listMantenimientos.remove(m);
	}
	public static Mantenimiento buscarID(short id) throws NoSuchElementException{
		System.out.println("Buscando id "+id);
		return listMantenimientos.stream().filter(e -> e.id==id).findAny().get();
	}
	
	public static void cargarDB() {
		gestorMantenimiento.recuperarEntidades();
	}
	public static void actualizarDB(Mantenimiento m) {
		gestorMantenimiento.actualizarEntidad(m);
	}
	
	public Mantenimiento(String descripcion, Estacion e) {
		this.id=contadorId;
		this.fechaInicio=LocalDate.now();
		this.descripcion= "Inicio mantenimiento: " + descripcion;
		incrementarContador();
		listMantenimientos.add(this);
		gestorMantenimiento.insertarEntidad(this);
		gestorMantenimiento.insertarEstacionMantenimiento(this, e);
	}

	public Mantenimiento(Estacion e) {
		this.id=contadorId;
		this.fechaInicio=LocalDate.now();
		incrementarContador();
		listMantenimientos.add(this);
		gestorMantenimiento.insertarEntidad(this);
		gestorMantenimiento.insertarEstacionMantenimiento(this, e);
	}
	
	public Mantenimiento(short id, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
		this.id=id;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.descripcion=descripcion;
		listMantenimientos.add(this);
	}
	
	public void finMantenimiento (String descripcion) {
		this.fechaFin=LocalDate.now();
		this.descripcion = this.descripcion + " Fin mantenimiento: " + descripcion;
		gestorMantenimiento.actualizarEntidad(this);
	}

	public void finMantenimiento() {
		this.fechaFin=LocalDate.now();
		gestorMantenimiento.actualizarEntidad(this);
	}
	
	private void incrementarContador() {
		contadorId++;
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

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		gestorMantenimiento.actualizarEntidad(this);
	}
	
	public boolean equals(Mantenimiento m) {
		return id == m.id;	
	}
}
