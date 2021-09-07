package objetos;

import java.util.Iterator;
import java.util.LinkedList;

import elementosSwing.grafo2D.PanelGrafo;

public class Recorrido {
	
	private LinkedList<Conexion> caminos=new LinkedList<Conexion>();
	
	public Recorrido() {};
	
	public Recorrido(LinkedList<Conexion> list) {
		caminos.addAll(list);
	}
	/*---------------------------------*/
	public Double costoTotal() {
		return caminos.stream().mapToDouble(c -> c.getCosto()).sum();
	}
	public Double distanciaTotal() {
		return caminos.stream().mapToDouble(c -> c.getDistancia()).sum();
	}
	public Double duracionTotal() {
		return caminos.stream().mapToDouble(c -> c.getDuracion()).sum();
	}
	/*---------------------------------*/
	public Boolean addConexion(Conexion c) {
		Boolean ret=caminos.contains(c);
		if (ret) {
			caminos.addLast(c);
		}
		return ret;
	}
	public void pollLast() {
		caminos.pollLast();
	}
	public Iterator<Conexion> getIterator() {
		return caminos.iterator();
	}
	/*---------------------------------*/
	public void select() {
		caminos.forEach(c -> c.select());
		PanelGrafo.repintarGrafo();
	}
	public void unselect() {
		caminos.forEach(c -> c.unselect());
		PanelGrafo.repintarGrafo();
	}
	public Estacion getEstacionInicial() {
		if(!caminos.isEmpty())
			return caminos.getFirst().getE1();
		else return null;
	}
	public Estacion getEstacionFinal() {
		if(!caminos.isEmpty())
			return caminos.getLast().getE2();
		else return null;
	}
	
}
