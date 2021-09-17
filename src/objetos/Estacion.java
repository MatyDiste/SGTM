package objetos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import conexionDB.GestorEstacionPostgreSQLDAO;
import elementosSwing.grafo2D.Estacion2D;
import elementosSwing.grafo2D.PanelGrafo;

enum EstadoEstacion {
	OPERATIVA, EN_MANTENIMIENTO
}

public class Estacion implements Comparable<Estacion>{
	
	private static GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
	public static HashSet<Estacion> listEstaciones=new HashSet<Estacion>(); 
	public static short contadorId;
	public static final Double D_PAGERANK=0.5d;
	
	private static Boolean borrarEstacion(Estacion e) { 
		PanelGrafo.quitarEstacion(e.getE2d());
		gestorEstacion.eliminarEntidad(e.id);
		return listEstaciones.remove(e);
	}
	
	public static void cargarDB() {
		gestorEstacion.recuperarEntidades();
	}
	public static void actualizarDB(Estacion e) {
		gestorEstacion.actualizarEntidad(e);
	}

	/*
	 * TODO
	 * Comunicarse con EstacionDAO para cargar todas las estaciones (mÃ©todo estÃ¡tico)
	 */
	public static Boolean nombreDisponible(String s) {
		return !listEstaciones.stream().anyMatch(e -> e.getNombre().equals(s));
	}
	
	private static void incrementarContador() {
		contadorId++;
	}
	public static short getContadorId() {
		return contadorId;
	}
	
	public static void setContadorId(short id) {
		contadorId=id;
	}
	
	public static Estacion buscarID(short id) throws NoSuchElementException{
		System.out.println("Buscando id "+id);
		return listEstaciones.stream().filter(e -> e.id==id).findAny().get();
	}
	public static Estacion buscarNombre(String name) throws NoSuchElementException{
		return listEstaciones.stream().filter(e -> e.nombre.equals(name)).findAny().get();
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
	public static void generarPageRank(Integer iteraciones) {
		listEstaciones.forEach(e -> e.pagerank=1d);
		for(int i=0; i<iteraciones; i++) {
			listEstaciones.forEach(e ->{
				e.calculatePageRank();
			});
		}
	}
	/*---------------------------------------------------*/
	private short id;
	private String nombre;
	private LocalTime horarioApertura;
	private LocalTime horarioCierre;
	private EstadoEstacion estado;
	private LocalDate fechaCreacion=LocalDate.now();
	private LinkedList<Conexion> listConexiones=new LinkedList<Conexion>();
	private Double pagerank = 1.0;
	private Double pesoTotal = 0.0;
	private LinkedList<Mantenimiento> listaMantenimientos=new LinkedList<Mantenimiento>();
	private Estacion2D e2d;
	public Double posx=Math.random()*600+50;
	public Double posy=Math.random()*450+50;
	private Integer cantidadDeMantenimientosEnDB = 0;
	private Integer cantidadDeConexionesEnDB = 0;

	public Estacion(String nombre, LocalTime horarioApertura, LocalTime horarioCierre, Boolean estado) throws NombreOcupadoException {
		
		if(nombreDisponible(nombre)) {
			this.id = contadorId;
			incrementarContador();
			this.nombre = nombre;
			this.horarioApertura = horarioApertura;
			this.horarioCierre = horarioCierre;
			this.estado = (estado) ? EstadoEstacion.OPERATIVA : EstadoEstacion.EN_MANTENIMIENTO;
			this.e2d = new Estacion2D(this);
			this.posx = Math.random() * 500 + 50;
			this.posy = Math.random() * 500 + 50;
			listEstaciones.add(this);
			gestorEstacion.insertarEntidad(this);
		}
		else throw new NombreOcupadoException(nombre);
		System.out.println("Se creó estación "+id+" (constructor programa)");
	}
	
	public Estacion(short id, String nombre, LocalTime horarioApertura, LocalTime horarioCierre, 
			String estado, LocalDate fechaCreacion, Double pagerank, Double pesoTotal, 
			Double posicionX, Double posicionY) {
		this.id = id;
		this.nombre = nombre;
		this.horarioApertura = horarioApertura;
		this.horarioCierre = horarioCierre;
		if (estado.equals("OPERATIVA")) {
			this.estado = EstadoEstacion.OPERATIVA;
		}
		else {
			this.estado = EstadoEstacion.EN_MANTENIMIENTO;
		}
		this.fechaCreacion = fechaCreacion;
		this.pagerank = pagerank;
		this.pesoTotal = pesoTotal;
		this.posx = posicionX;
		this.posy = posicionY;
		this.e2d = new Estacion2D(this);
	}
	
	public Estacion(short id, String nombre, LocalTime horarioApertura, LocalTime horarioCierre, 
			String estado, LocalDate fechaCreacion, Double pagerank, Double pesoTotal, 
			Double posicionX, Double posicionY,	LinkedList<Mantenimiento> listaMantenimientos) {
		
		this.id = id;
		this.nombre = nombre;
		this.horarioApertura = horarioApertura;
		this.horarioCierre = horarioCierre;
		if (estado.equals("OPERATIVA")) {
			this.estado = EstadoEstacion.OPERATIVA;
		}
		else {
			this.estado = EstadoEstacion.EN_MANTENIMIENTO;
		}
		this.fechaCreacion = fechaCreacion;
		this.pagerank = pagerank;
		this.pesoTotal = pesoTotal;
		this.posx = posicionX;
		this.posy = posicionY;
		this.e2d = new Estacion2D(this);
		this.listaMantenimientos = listaMantenimientos;
		boolean repetido = false;
		for(Estacion e: Estacion.listEstaciones) {
			if(this.equals(e)) {
				repetido=true;
			}
		}
		if(!repetido) {
			listEstaciones.add(this);
		}
		System.out.println("Se creó estación "+id);
	}
	
	/*--------------------------------------------------*/

	
	
	//METODOS GETTERS AND SETTERS
	
	public Estacion2D getE2d() {
		return e2d;
	}
	
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public void setE2d(Estacion2D e2d) {
		this.e2d = e2d;
	}
	
	public Short getId() {
		return id;
	}
	public void setId(short id) {
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
	
	public Integer getCantidadDeMantenimientosEnDB() {
		return cantidadDeMantenimientosEnDB;
	}

	public void setCantidadDeMantenimientosEnDB(Integer cantidadDeMantenimientosEnDB) {
		this.cantidadDeMantenimientosEnDB = cantidadDeMantenimientosEnDB;
	}

	public Integer getCantidadDeConexionesEnDB() {
		
		return cantidadDeConexionesEnDB;
	}

	public void setCantidadDeConexionesEnDB(Integer cantidadDeConexionesEnDB) {
		this.cantidadDeConexionesEnDB = cantidadDeConexionesEnDB;
	}
	
	public String getEstado() {
		return estado.name();
	}
	public void setMantenimiento() {
		if (this.estado==EstadoEstacion.OPERATIVA) {
			
			JFrame dialog=new JFrame();
			JPanel rootp=new JPanel();
			dialog.setLocationRelativeTo(null);
			dialog.setAlwaysOnTop(true);
			dialog.setTitle("Comentario inicio mantenimiento");
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			rootp.setLayout(new BoxLayout(rootp, BoxLayout.Y_AXIS));
			rootp.add(new JLabel("Ingrese comentario de comienzo de mantenimiento"));
			JTextArea jt=new JTextArea(10,51);
			jt.setLineWrap(true);
			rootp.add(jt);
			JButton btnAceptar=new JButton("Aceptar");
			btnAceptar.addActionListener(e -> {
				String comentarioMant=((JTextArea)(rootp.getComponent(1))).getText();
				listaMantenimientos.add(new Mantenimiento(comentarioMant, this));
				dialog.dispose();
			});
			rootp.add(btnAceptar);
			dialog.setContentPane(rootp);
			dialog.pack();
			dialog.setVisible(true);
			this.estado = EstadoEstacion.EN_MANTENIMIENTO;
			PanelGrafo.repintarGrafo();
		}
	}
	public void setOperativa(String descripcion) {
		Mantenimiento ultimoMantenimiento = listaMantenimientos.getLast();
		ultimoMantenimiento.finMantenimiento(descripcion);
		this.estado = EstadoEstacion.OPERATIVA;
	}
	public void setOperativa() {
		if(this.estado==EstadoEstacion.EN_MANTENIMIENTO) {
			Mantenimiento ultimoMantenimiento = listaMantenimientos.getLast();
			JFrame dialog=new JFrame();
			JPanel rootp=new JPanel();
			dialog.setLocationRelativeTo(null);
			dialog.setAlwaysOnTop(true);
			dialog.setTitle("Comentario fin mantenimiento");
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			rootp.setLayout(new BoxLayout(rootp, BoxLayout.Y_AXIS));
			rootp.add(new JLabel("Ingrese comentario de finalización de mantenimiento"));
			JTextArea jt=new JTextArea(10,51);
			jt.setLineWrap(true);
			rootp.add(jt);
			JButton btnAceptar=new JButton("Aceptar");
			btnAceptar.addActionListener(e -> {
				String comentarioMant="";
				comentarioMant=((JTextArea)(rootp.getComponent(1))).getText();
				ultimoMantenimiento.finMantenimiento(comentarioMant);
				dialog.dispose();
			});
			rootp.add(btnAceptar);
			dialog.setContentPane(rootp);
			dialog.pack();
			dialog.setVisible(true);
			this.estado = EstadoEstacion.OPERATIVA;
			PanelGrafo.repintarGrafo();
		}
	}
	public LocalDate getFechaUltimoMantenimiento() {
		if(listaMantenimientos.isEmpty())
			return fechaCreacion;
		else
			return listaMantenimientos.get(listaMantenimientos.size()-1).getFechaInicio();
	}
	public LinkedList<Conexion> getListConexiones() {
		return listConexiones;
	}
	public void setListConexiones (LinkedList<Conexion> listaConexiones) {
		this.listConexiones = listaConexiones;
	}

	public LinkedList<Mantenimiento> getListaMantenimientos() {
		return listaMantenimientos;
	}

	public void setListaMantenimientos(LinkedList<Mantenimiento> listaMantenimientos) {
		this.listaMantenimientos = listaMantenimientos;
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
	public Double getPosx() {
		return posx;
	}

	public void setPosx(Double posx) {
		this.posx = posx;
	}

	public Double getPosy() {
		return posy;
	}

	public void setPosy(Double posy) {
		this.posy = posy;
	}
	
	public void addConexionNODB(Conexion c) {
		listConexiones.add(c);
	}

	public void addConexion(Conexion c) {
		listConexiones.add(c);
		gestorEstacion.actualizarEntidad(this);
	}
	public void quitarConexion(Conexion c) {
		listConexiones.remove(c);
		gestorEstacion.actualizarEntidad(this);
	}
	/*--------------------------------------------------*/
	public Integer cantSalientes() {
		return (int)listConexiones.stream()
				.filter(c -> c.getE1().equals(this))
				.count();
	}
	public HashSet<Estacion> listEstacionesEntrantes(){
		return (HashSet<Estacion>)listConexiones.stream()
				.filter(c -> c.getE2().equals(this))
				.map(c -> c.getE1())
				.collect(Collectors.toSet());
	}
	public void calculatePageRank() {
		this.pagerank = D_PAGERANK;
		
		this.listEstacionesEntrantes().forEach(e -> {
			this.pagerank += D_PAGERANK * (e.pagerank/e.cantSalientes());
		});
	}
	
	public LinkedList<Recorrido> getRecorridos(Estacion hasta) {
		LinkedList<Recorrido> ret=new LinkedList<Recorrido>();
		depthFirst(ret, hasta, new LinkedList<Conexion>());
		return ret;
	}
	private void depthFirst(LinkedList<Recorrido> lista, Estacion hasta, LinkedList<Conexion> conexiones) {
		
		if(this.equals(hasta)) {
			lista.addLast(new Recorrido(conexiones));
		}
		else {
			listConexiones.forEach(c -> {
				if(c.getE1().equals(this) && c.estado().equals("ACTIVA") && !conexiones.contains(c)) { 
					conexiones.addLast(c);
					c.getE2().depthFirst(lista, hasta, conexiones);
					conexiones.removeLast();
				}
			});
		}
		
	}
	
	public void select() {
		this.e2d.select();
	}
	public void unselect() {
		this.e2d.unselect();
	}
	
	public boolean equals(Estacion o) { 
		return this.nombre.equals(o.getNombre()) || this.id==o.id;
	}
	@Override
	public int hashCode() { 
		return nombre.hashCode();
		
	}
	public void eliminar() { 
		@SuppressWarnings("unchecked")
		LinkedList<Conexion> aux= (LinkedList<Conexion>)listConexiones.clone();
		aux.stream()
					  .forEach(c ->{
						  c.getLinea().quitarConexion(c);
						  c.eliminar();
					  });
		listConexiones.clear();
		listConexiones=null;
		listaMantenimientos.forEach(m -> Mantenimiento.borrarMantenimiento(m));
		listaMantenimientos.clear();
		Estacion.borrarEstacion(this);
	}
	@Override
	public int compareTo(Estacion o) {
		return this.getNombre().compareToIgnoreCase(o.getNombre());
	}
	public String toString() {
		return this.nombre;
	}
	@Override
	public boolean equals(Object o) {
		Estacion e = (Estacion) o;
		if(id == e.id) {
			return true;
		}
		else {
			return false;
		}
	}
}
