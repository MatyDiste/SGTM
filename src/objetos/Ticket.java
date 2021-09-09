package objetos;

import java.time.LocalDate;

public class Ticket {
	private Double costo=0d;
	private Double distancia=0d;
	private LocalDate fechaEmision=LocalDate.now();
	private Estacion inicio;
	private Estacion fin;
	
	public Ticket(Recorrido rec) {
		costo=rec.costoTotal();
		distancia=rec.distanciaTotal();
		inicio=rec.getEstacionInicial();
		fin=rec.getEstacionFinal();
		
		//TODO guardar en DB
		//Comunicar a DAO
	}
	
	
}
