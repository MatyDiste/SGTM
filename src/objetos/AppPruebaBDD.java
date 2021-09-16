package objetos;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import conexionDB.GestorConexionPostgreSQLDAO;
import conexionDB.GestorEstacionPostgreSQLDAO;
import conexionDB.GestorLineaPostgreSQLDAO;
import conexionDB.GestorMantenimientoPostgreSQLDAO;
import conexionDB.PostgreSQL;
import elementosSwing.grafo2D.Estacion2D;
import elementosSwing.grafo2D.Flecha;

@SuppressWarnings("unused")
public class AppPruebaBDD {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		//-------------PRUEBA INSERCION DE DATOS----------------//
		
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		GestorLineaPostgreSQLDAO gestorLinea = new GestorLineaPostgreSQLDAO();
		GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
		
		PostgreSQL.setearContadoresId();
		
		Mantenimiento m1 = new Mantenimiento("Primer Mantenimiento"); 
		Mantenimiento m2 = new Mantenimiento("Segundo Mantenimiento"); 
		Mantenimiento m3 = new Mantenimiento("Tercero Mantenimiento"); 
		Mantenimiento m4 = new Mantenimiento("Cuarto Mantenimiento"); 
		Mantenimiento m5 = new Mantenimiento("Quinto Mantenimiento");
		Mantenimiento m6 = new Mantenimiento("Sexto Mantenimiento"); 
		Mantenimiento m7 = new Mantenimiento("Septimo Mantenimiento"); 
		Mantenimiento m8 = new Mantenimiento("Octavo Mantenimiento"); 
		Mantenimiento m9 = new Mantenimiento("Noveno Mantenimiento"); 
		Mantenimiento m10 = new Mantenimiento("Decimo Mantenimiento");
		Mantenimiento m11 = new Mantenimiento("Onceavo Mantenimiento"); 
		Mantenimiento m12 = new Mantenimiento("Doceavo Mantenimiento"); 
		Mantenimiento m13 = new Mantenimiento("Treceavo Mantenimiento");
		
		HashSet<Conexion> listConexionesEstaciones1=new HashSet<Conexion>();
		HashSet<Conexion> listConexionesEstaciones2=new HashSet<Conexion>();
		LinkedList<Mantenimiento> listaMantenimientos1=new LinkedList<Mantenimiento>();
		LinkedList<Mantenimiento> listaMantenimientos2=new LinkedList<Mantenimiento>();
		List<Estacion> listEstaciones=new ArrayList<Estacion>();
		HashSet<Conexion> listConexionesLineas=new HashSet<Conexion>();
		
		listaMantenimientos2.add(m13);
		listaMantenimientos1.add(m12);
		listaMantenimientos2.add(m11);
		listaMantenimientos1.add(m10);
		listaMantenimientos2.add(m9);
		listaMantenimientos1.add(m8);
		listaMantenimientos2.add(m7);
		listaMantenimientos1.add(m6);
		listaMantenimientos2.add(m5);
		listaMantenimientos1.add(m4);
		listaMantenimientos2.add(m3);
		listaMantenimientos1.add(m2);
		listaMantenimientos2.add(m1);
		
		Estacion e1 = null;
		Estacion e2 = null;
		Estacion e3 = null;
		Estacion e4 = null;
		try {
			e1 = new Estacion("Estacion Belgrano", LocalTime.now(), LocalTime.now().plusHours(8), true);
			e2 = new Estacion("Estacion Mitre", LocalTime.now(), LocalTime.now().plusHours(10), true);
			e3 = new Estacion("Estacion Candiotti", LocalTime.now(), LocalTime.now().plusHours(7), true);
			e4 = new Estacion("Estacion Roca", LocalTime.now(), LocalTime.now().plusHours(9), true);
			e3.setListaMantenimientos(listaMantenimientos1);
			e4.setListaMantenimientos(listaMantenimientos2);
		} catch (NombreOcupadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear las estaciones");
		}
		gestorEstacion.insertarEntidad(e1);
		gestorEstacion.insertarEntidad(e2);
		
		Linea l1 = null;
		Linea l2 = null;
		try {
			l1 = new Linea("Linea verde", Color.GREEN, true, (short) 2);
			l2 = new Linea("Linea azul", Color.BLUE, true, (short) 0);
		} catch (NombreOcupadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al crear la linea");
		}
		gestorLinea.insertarEntidad(l1);
		
		Conexion c1=new Conexion(e1,e2,l1,150.0,45.0,50,200.0);
		Conexion c2=new Conexion(e1,e2,l1,150.0,45.0,50,200.0);
		Conexion c3=new Conexion(e1,e2,l1,150.0,45.0,50,200.0);
		Conexion c4=new Conexion(e1,e2,l1,150.0,45.0,50,200.0);
		Conexion c5=new Conexion(e1,e2,l1,150.0,45.0,50,200.0);
		Conexion c6=new Conexion(e1,e2,l1,150.0,45.0,50,200.0);
		gestorConexion.insertarEntidad(c1);
		gestorConexion.insertarEntidad(c2);
		gestorConexion.insertarEntidad(c3);
		gestorConexion.insertarEntidad(c4);
		gestorConexion.insertarEntidad(c5);
		gestorConexion.insertarEntidad(c6);
		
		
		listConexionesEstaciones1.add(c1);
		listConexionesEstaciones1.add(c2);
		listConexionesEstaciones1.add(c3);
		listConexionesEstaciones2.add(c4);
		listConexionesEstaciones2.add(c5);
		listConexionesEstaciones2.add(c6);
		e3.setListConexiones(listConexionesEstaciones1);
		e4.setListConexiones(listConexionesEstaciones2);
		
		listEstaciones.add(e1);
		listEstaciones.add(e2);
		l2.setListEstaciones(listEstaciones);
		
		listConexionesLineas.add(c1);
		listConexionesLineas.add(c3);
		listConexionesLineas.add(c5);
		listConexionesLineas.add(c6);
		l2.setListConexiones(listConexionesLineas);
		
		gestorEstacion.insertarEntidad(e3);
		gestorEstacion.insertarEntidad(e4);
		
		gestorLinea.insertarEntidad(l2); 
		*/
		//------------FIN PRUEBA INSERCION DE DATOS---------------//
		
		
		//-------------PRUEBA RECUPERACION DE DATOS POR ID----------------//
		/*
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		Estacion estacion1 = (Estacion) gestorEstacion.recuperarEntidad(1004);
		
		System.out.println(" Estacion número " + estacion1.getId() + " recuperada de la base de datos."
				+ "\n Se detallan los datos a continuacion."
				+ "\n Nombre: " + estacion1.getNombre() 
				+ "\n Horario de apertura: " + estacion1.getHorarioApertura()
				+ "\n Horario de cierre: " + estacion1.getHorarioCierre()
				+ "\n Estado: " + estacion1.getEstado()
				+ "\n Fecha del último mantenimiento: " + estacion1.getFechaUltimoMantenimiento()
				+ "\n PageRank: " + estacion1.getPagerank()
				+ "\n Peso Total: " + estacion1.getPesoTotal()
				+ "\n Posicion en X: " + estacion1.getPosx()
				+ "\n Posicion en Y: " + estacion1.getPosy()
				+ "\n Lista de conexiones pertenecientes a esta estacion: ");
		
		for(Conexion c: estacion1.getListConexiones()) {
			System.out.println(" + Conexion nro: " + c.getId());
		}
		
		System.out.println(" Lista de mantenimientos pertenecientes a esta estacion: ");
		
		for(Mantenimiento m: estacion1.getListaMantenimientos()) {
			System.out.println(" + Mantenimiento nro: " + m.getId());
		}
		
		System.out.println();
		
		GestorLineaPostgreSQLDAO gestorLinea = new GestorLineaPostgreSQLDAO();
		Linea linea1 = (Linea) gestorLinea.recuperarEntidad(1002);
		
		System.out.println(" Linea número " + linea1.getId() + " recuperada de la base de datos."
				+ "\n Se detallan los datos a continuacion."
				+ "\n Nombre: " + linea1.getNombre() 
				+ "\n Color: " + linea1.getColor()
				+ "\n Estado: " + linea1.estado()
				+ "\n Lista de conexiones pertenecientes a esta linea: ");
		
		for(Conexion c: linea1.getListConexiones()) {
			System.out.println(" + Conexion nro: " + c.getId());
		}
		
		System.out.println(" Lista de estaciones pertenecientes a esta estacion: ");
		
		for(Estacion e: linea1.getListEstaciones()) {
			System.out.println(" + Estacion nro: " + e.getId());
		}
		
		System.out.println();
		
		GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
		Conexion conexion1 = (Conexion) gestorConexion.recuperarEntidad(1);
		
		System.out.println(" Conexion número " + conexion1.getId() + " recuperada de la base de datos."
				+ "\n Se detallan los datos a continuacion."
				+ "\n Estado: " + conexion1.estado()
				+ "\n Distancia: " + conexion1.getDistancia()
				+ "\n Duracion: " + conexion1.getDuracion()
				+ "\n Costo: " + conexion1.getCosto()
				+ "\n Cantidad máxima de pasajeros: " + conexion1.getCantMaxPasajeros()
				+ "\n ID de la primera estacion: " + conexion1.getE1().getId()
				+ "\n ID de la segunda estacion: " + conexion1.getE2().getId()
				+ "\n ID de la linea: " + conexion1.getLinea().getId());
		
		System.out.println();
		
		GestorMantenimientoPostgreSQLDAO gestorMantenimiento = new GestorMantenimientoPostgreSQLDAO();
		Mantenimiento mantenimiento1 = (Mantenimiento) gestorMantenimiento.recuperarEntidad(5);
		
		System.out.println(" Mantenimiento número " + mantenimiento1.getId() + " recuperado de la base de datos."
				+ "\n Se detallan los datos a continuacion."
				+ "\n Fecha de inicio: " + mantenimiento1.getFechaInicio()
				+ "\n Fecha de fin: " + mantenimiento1.getFechaFin()
				+ "\n Descripcion: " + mantenimiento1.getDescripcion());
		*/
		//-------------FIN PRUEBA RECUPERACION DE DATOS POR ID----------------//
		
		//-------------PRUEBA RECUPERACION DE TODOS LOS DATOS ----------------//
		/*
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		GestorLineaPostgreSQLDAO gestorLinea = new GestorLineaPostgreSQLDAO();
		GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
		GestorMantenimientoPostgreSQLDAO gestorMantenimiento = new GestorMantenimientoPostgreSQLDAO();
		
		Integer estacionesRecuperadas = gestorEstacion.recuperarEntidades();
		Integer linesasRecuperadas = gestorLinea.recuperarEntidades();
		Integer conexionesRecuperadas = gestorConexion.recuperarEntidades();
		Integer mantenimientosRecuperados = gestorMantenimiento.recuperarEntidades();
		
		System.out.println(" Estaciones Recuperadas: " + estacionesRecuperadas
				+ "\n Linesas Recuperadas: " + linesasRecuperadas
				+ "\n Conexiones Recuperadas: " + conexionesRecuperadas
				+ "\n Mantenimientos Recuperados: " + mantenimientosRecuperados);
		
		System.out.println();
		
		for (Estacion estacion1: Estacion.listEstaciones) {
			
			System.out.println(" Estacion número " + estacion1.getId() + " recuperada de la base de datos."
					+ "\n Se detallan los datos a continuacion."
					+ "\n Nombre: " + estacion1.getNombre() 
					+ "\n Horario de apertura: " + estacion1.getHorarioApertura()
					+ "\n Horario de cierre: " + estacion1.getHorarioCierre()
					+ "\n Estado: " + estacion1.getEstado()
					+ "\n Fecha del último mantenimiento: " + estacion1.getFechaUltimoMantenimiento()
					+ "\n PageRank: " + estacion1.getPagerank()
					+ "\n Peso Total: " + estacion1.getPesoTotal()
					+ "\n Posicion en X: " + estacion1.getPosx()
					+ "\n Posicion en Y: " + estacion1.getPosy()
					+ "\n Lista de conexiones pertenecientes a esta estacion: ");
			
			for(Conexion c: estacion1.getListConexiones()) {
				System.out.println(" + Conexion nro: " + c.getId());
			}
			
			System.out.println(" Lista de mantenimientos pertenecientes a esta estacion: ");
			
			for(Mantenimiento m: estacion1.getListaMantenimientos()) {
				System.out.println(" + Mantenimiento nro: " + m.getId());
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-//");
		System.out.println();
		
		for(Linea linea1: Linea.listLineas) {
			System.out.println(" Linea número " + linea1.getId() + " recuperada de la base de datos."
					+ "\n Se detallan los datos a continuacion."
					+ "\n Nombre: " + linea1.getNombre() 
					+ "\n Color: " + linea1.getColor()
					+ "\n Estado: " + linea1.estado()
					+ "\n Lista de conexiones pertenecientes a esta linea: ");
			
			for(Conexion c: linea1.getListConexiones()) {
				System.out.println(" + Conexion nro: " + c.getId());
			}
			
			System.out.println(" Lista de estaciones pertenecientes a esta estacion: ");
			
			for(Estacion e: linea1.getListEstaciones()) {
				System.out.println(" + Estacion nro: " + e.getId());
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-//");
		System.out.println();
		
		for(Conexion conexion1: Conexion.listConexiones) {
			System.out.println(" Conexion número " + conexion1.getId() + " recuperada de la base de datos."
					+ "\n Se detallan los datos a continuacion."
					+ "\n Estado: " + conexion1.estado()
					+ "\n Distancia: " + conexion1.getDistancia()
					+ "\n Duracion: " + conexion1.getDuracion()
					+ "\n Costo: " + conexion1.getCosto()
					+ "\n Cantidad máxima de pasajeros: " + conexion1.getCantMaxPasajeros()
					+ "\n ID de la primera estacion: " + conexion1.getE1().getId()
					+ "\n ID de la segunda estacion: " + conexion1.getE2().getId()
					+ "\n ID de la linea: " + conexion1.getLinea().getId());
			System.out.println();
		}
		
		System.out.println();
		System.out.println("//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-//");
		System.out.println();
		
		for(Mantenimiento mantenimiento1: Mantenimiento.listMantenimientos) {
			System.out.println(" Mantenimiento número " + mantenimiento1.getId() + " recuperado de la base de datos."
					+ "\n Se detallan los datos a continuacion."
					+ "\n Fecha de inicio: " + mantenimiento1.getFechaInicio()
					+ "\n Fecha de fin: " + mantenimiento1.getFechaFin()
					+ "\n Descripcion: " + mantenimiento1.getDescripcion());
			System.out.println();
		}
		
		System.out.println();
		System.out.println("//-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-//");
		System.out.println();
		*/
		//-------------FIN PRUEBA RECUPERACION DE TODOS LOS DATOS ----------------//
		
		//-------------PRUEBA DE ACTUALIZACION DE DATOS ----------------//
		/*
		GestorMantenimientoPostgreSQLDAO gestorMantenimiento = new GestorMantenimientoPostgreSQLDAO();
		Mantenimiento.setContadorId(5);                
		Mantenimiento m1 = new Mantenimiento("Mantenimiento actualizado");
		m1.setFechaFin(LocalDate.now().plusDays(3));
		gestorMantenimiento.actualizarEntidad(m1);
		
		GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		Estacion e1 = (Estacion) gestorEstacion.recuperarEntidad(1004);
		Estacion e2 = (Estacion) gestorEstacion.recuperarEntidad(1003);
		GestorLineaPostgreSQLDAO gestorLinea = new GestorLineaPostgreSQLDAO();
		Linea l1 = (Linea) gestorLinea.recuperarEntidad(1002);
		Conexion c1 = new Conexion(3, 100.35, 46.7, 
			46, "INACTIVA", 350.6, e1, e2, l1);
		gestorConexion.actualizarEntidad(c1);
		
		GestorLineaPostgreSQLDAO gestorLinea = new GestorLineaPostgreSQLDAO();
		Linea l1 = (Linea) gestorLinea.recuperarEntidad((short) 1008);
		l1.setNombre("Benja");
		l1.setColor(Color.BLUE);
		l1.setTipo((short) 2);
		gestorLinea.actualizarEntidad(l1);
		
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		Estacion e1 = (Estacion) gestorEstacion.recuperarEntidad((short) 1017);
		e1.setNombre("Benja");
		e1.setHorarioApertura(LocalTime.now());
		e1.setHorarioCierre(LocalTime.now().plusHours(8));
		e1.setPagerank(1.87);
		e1.setPesoTotal(15.6);
		e1.setPosx(250.65);
		e1.setPosy(364.98);
		gestorEstacion.actualizarEntidad(e1);
		*/
		//-------------FIN PRUEBA DE ACTUALIZACION DE DATOS ----------------//
		
		//-------------PRUEBA DE ELIMINACION DE DATOS ----------------//
		/* 
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		Estacion e1 = (Estacion) gestorEstacion.recuperarEntidad((short) 1070);
		gestorEstacion.eliminarEntidad(e1);
		
		GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
		gestorConexion.eliminarEntidad((short) 6);
		
		GestorMantenimientoPostgreSQLDAO gestorMantenimiento = new GestorMantenimientoPostgreSQLDAO();
		gestorMantenimiento.eliminarEntidad((short) 10);
		
		GestorLineaPostgreSQLDAO gestorLinea = new GestorLineaPostgreSQLDAO();
		gestorLinea.eliminarEntidad((short) 1002);
		*/
		//-------------FIN DE ELIMINACION DE DATOS ----------------//
	}
}
