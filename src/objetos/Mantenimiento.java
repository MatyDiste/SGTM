package objetos;

import java.time.LocalDate;
import java.util.HashSet;

import ConexionDB.GestorMantenimientoPostgreSQLDAO;

public class Mantenimiento {
	
	private static GestorMantenimientoPostgreSQLDAO gestorMantenimiento = new GestorMantenimientoPostgreSQLDAO();
	public static HashSet<Mantenimiento> listMantenimientos=new HashSet<Mantenimiento>(); 
	private static Integer contadorId;
	private Integer id;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String descripcion;
	
	public Mantenimiento(String descripcion) {
		this.id=contadorId;
		this.fechaInicio=LocalDate.now();
		this.descripcion= "Inicio mantenimiento: " + descripcion;
		incrementarContador();
		listMantenimientos.add(this);
		gestorMantenimiento.insertarEntidad(this);
	}

	public Mantenimiento() {
		this.id=contadorId;
		this.fechaInicio=LocalDate.now();
		incrementarContador();
		listMantenimientos.add(this);
		gestorMantenimiento.insertarEntidad(this);
	}
	
	public Mantenimiento(Integer id, LocalDate fechaInicio, LocalDate fechaFin, String descripcion) {
		this.id=id;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.descripcion=descripcion;
		boolean repetido = false;
		for(Mantenimiento m: Mantenimiento.listMantenimientos) {
			if(this.equals(m)) {
				repetido=true;
			}
		}
		if(!repetido) {
			listMantenimientos.add(this);
		}
	}
	
	public void finMantenimiento (String descripcion) {
		this.fechaFin=LocalDate.now();
		this.descripcion = this.descripcion + " Fin mantenimiento: " + descripcion;
	}

	public void finMantenimiento() {
		this.fechaFin=LocalDate.now();
	}
	
	private void incrementarContador() {
		contadorId++;
	}

	//METODOS GETTERS AND SETTERS
	
	public static Integer getContadorId() {
		return contadorId;
	}
	
	public static void setContadorId(Integer id) {
		contadorId=id;
	}

	public Integer getId() {
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
	}
	@Override
	public boolean equals(Object o) {
		Mantenimiento m = (Mantenimiento) o;
		if(id == m.id) {
			return true;
		}
		else {
			return false;
		}
	}
}
