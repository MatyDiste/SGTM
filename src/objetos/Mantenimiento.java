package objetos;

import java.time.LocalDate;

public class Mantenimiento {
	
	private static short contadorId=0;
	private short id;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String descripcion;
	
	public Mantenimiento(String descripcion) {
		this.id=contadorId;
		this.fechaInicio=LocalDate.now();
		this.descripcion= "Inicio mantenimiento: " + descripcion;
		incrementarContador();
	}

	public Mantenimiento() {
		this.id=contadorId;
		this.fechaInicio=LocalDate.now();
		incrementarContador();
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
	
	public static short getContadorId() {
		return contadorId;
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
	}

}
