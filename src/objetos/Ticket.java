package objetos;

import java.time.LocalDate;

import conexionDB.GestorTicketPostgreSQLDAO;

@SuppressWarnings("unused")
public class Ticket {
	private Double costo=0d;
	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public LocalDate getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Estacion getInicio() {
		return inicio;
	}

	public void setInicio(Estacion inicio) {
		this.inicio = inicio;
	}

	public Estacion getFin() {
		return fin;
	}

	public void setFin(Estacion fin) {
		this.fin = fin;
	}

	private Double distancia=0d;
	private LocalDate fechaEmision=LocalDate.now();
	private Estacion inicio;
	private Estacion fin;
	
	public Ticket(Recorrido rec) {
		costo=rec.costoTotal();
		distancia=rec.distanciaTotal();
		inicio=rec.getEstacionInicial();
		fin=rec.getEstacionFinal();
		new GestorTicketPostgreSQLDAO().insertarEntidad(this);
	}
	
	
}
