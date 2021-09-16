package conexionDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import org.postgresql.util.PSQLException;

import objetos.Conexion;
import objetos.Estacion;
import objetos.Mantenimiento;

public class GestorEstacionPostgreSQLDAO extends PostgreSQL{
	
	//private String url = "jdbc:postgresql://localhost:5432/sgtm";
	//private String clave = "benja12345";
	private Connection conex = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	
	@Override
	public short insertarEntidad(Object o) {
		
		Estacion estacion = (Estacion) o;
		short id=0;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("INSERT INTO estacion VALUES (?,?,?,?,?,?,?,?,?,?)");
			
			pstm.setInt(1, estacion.getId());
			pstm.setString(2, estacion.getNombre());
			pstm.setTime(3, Time.valueOf(estacion.getHorarioApertura()));
			pstm.setTime(4, Time.valueOf(estacion.getHorarioCierre()));
			pstm.setString(5, estacion.getEstado());
			pstm.setDate(6, Date.valueOf(estacion.getFechaCreacion()));
			pstm.setDouble(7, estacion.getPagerank());
			pstm.setDouble(8, estacion.getPesoTotal());
			pstm.setDouble(9, estacion.posx);
			pstm.setDouble(10, estacion.posy);
			
			Integer tuplaIncertada = pstm.executeUpdate();
			
			if(tuplaIncertada==1) {
				id = estacion.getId();
				System.out.println("Estacion número " + id + " fué añadida correctamente!");
			}
			
			Integer cantidadDeConexiones=0;
			for(Conexion c: estacion.getListConexiones()) {
				pstm = conex.prepareStatement("INSERT INTO lista_conexiones_estaciones VALUES (?,?)");
				pstm.setInt(1, estacion.getId());
				pstm.setInt(2, c.getId());
				pstm.executeUpdate();
				cantidadDeConexiones++;
			}
			System.out.println(cantidadDeConexiones 
					+ " conexiones añadidas a la tabla 'LISTA_CONEXIONES_ESTACIONES' "
					+ "pertenecientes a la estacion " + estacion.getNombre());
			
			Integer cantidadDeMantenimientos=0;
			for(Mantenimiento m: estacion.getListaMantenimientos()) {
				pstm = conex.prepareStatement("INSERT INTO lista_mantenimientos VALUES (?,?)");
				pstm.setInt(1, estacion.getId());
				pstm.setInt(2, m.getId());
				pstm.executeUpdate();
				cantidadDeMantenimientos++;
			}
			System.out.println(cantidadDeMantenimientos 
					+ " mantenimientos añadidos a la tabla 'LISTA_MANTENIMIENTOS' "
					+ "pertenecientes a la estacion " + estacion.getNombre());
		} 
		
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error driver");
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error SQL");
		}
		
		finally {
			if(rs!=null) try { rs.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
			if(pstm!=null) try { pstm.close(); } 
			catch (SQLException e) {e.printStackTrace(); }
			if(conex!=null) try { conex.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
		}
		
		return id;
	}

	@Override
	public Integer recuperarEntidades() {

		Integer cantidadEstacionesRecuperadas = 0;
		GestorMantenimientoPostgreSQLDAO gestorMantenimiento = new GestorMantenimientoPostgreSQLDAO();
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM estacion");
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				ResultSet rsAux = null;
				ArrayList<Conexion> listaDeConexionesAux = new ArrayList<Conexion>();
				ArrayList<Mantenimiento> listaDeMantenimientosAux = new ArrayList<Mantenimiento>();
				/*
				pstm = conex.prepareStatement("SELECT * FROM lista_conexiones_estaciones "
						+ "WHERE id_estacion = " + rs.getInt(1));
				
				rsAux = pstm.executeQuery();
				
				while(rsAux.next()) {
					Conexion cn = (Conexion) gestorConexion.recuperarEntidad(rsAux.getShort(2));
					listaDeConexionesAux.add(cn);
				}*/
				
				pstm = conex.prepareStatement("SELECT * FROM lista_mantenimientos "
						+ "WHERE id_estacion = " + rs.getInt(1));
				rsAux = pstm.executeQuery();
				while(rsAux.next()) {
					Mantenimiento mn = (Mantenimiento) gestorMantenimiento.recuperarEntidad(rsAux.getShort(2));
					listaDeMantenimientosAux.add(mn);
				}
				
				new Estacion(rs.getShort(1), 
							rs.getString(2),
							rs.getTime(3).toLocalTime(),
							rs.getTime(4).toLocalTime(),
							rs.getString(5),
							rs.getDate(6).toLocalDate(),
							rs.getDouble(7),
							rs.getDouble(8),
							rs.getDouble(9),
							rs.getDouble(10),
							listaDeConexionesAux,
							listaDeMantenimientosAux);
				cantidadEstacionesRecuperadas++;
			
			}
		}
		
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error driver");
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error SQL");
		}
		
		finally {
			if(rs!=null) try { rs.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
			if(pstm!=null) try { pstm.close(); } 
			catch (SQLException e) {e.printStackTrace(); }
			if(conex!=null) try { conex.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
		}
		
		return cantidadEstacionesRecuperadas;
	}

	@Override
	public boolean actualizarEntidad(Object o) {
		
		Estacion estacion = (Estacion) o;
		Integer tuplaActualizada = 0;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			if (estacion.getListConexiones().size() > estacion.getCantidadDeConexionesEnDB()) {
				for(int i=estacion.getCantidadDeConexionesEnDB(); i<estacion.getListConexiones().size(); i++) {
					pstm = conex.prepareStatement("INSERT INTO lista_conexiones_estaciones VALUES (?,?)");
					pstm.setShort(1, estacion.getId());
					pstm.setShort(2, estacion.getListConexiones().get(i).getId());
					try {
						pstm.executeUpdate();
					} catch(PSQLException e) {System.out.println("Probablemente ya esté cargado este dato. Error PSQL");}
				}
				estacion.setCantidadDeConexionesEnDB(estacion.getListConexiones().size());
			}
			
			if (estacion.getListaMantenimientos().size() > estacion.getCantidadDeMantenimientosEnDB()) {
				for(int i=estacion.getCantidadDeMantenimientosEnDB(); i<estacion.getListaMantenimientos().size(); i++) {
					pstm = conex.prepareStatement("INSERT INTO lista_mantenimientos VALUES (?,?)");
					pstm.setShort(1, estacion.getId());
					pstm.setShort(2, estacion.getListaMantenimientos().get(i).getId());
					try {
						pstm.executeUpdate();
					} catch(PSQLException e) {System.out.println("Probablemente ya esté cargado este dato. Error PSQL");}
				}
				estacion.setCantidadDeMantenimientosEnDB(estacion.getListaMantenimientos().size());
			}
			
			pstm = conex.prepareStatement("UPDATE estacion SET nombre = ?,"
					+ "horario_apertura = ?,"
					+ "horario_cierre = ?,"
					+ "estado = ?,"
					+ "pagerank = ?,"
					+ "peso_total = ?,"
					+ "posicionx = ?,"
					+ "posiciony = ?"
					+ "WHERE id_estacion = " + estacion.getId());
			
			pstm.setString(1, estacion.getNombre());
			pstm.setTime(2, Time.valueOf(estacion.getHorarioApertura()));
			pstm.setTime(3, Time.valueOf(estacion.getHorarioCierre()));
			pstm.setString(4, estacion.getEstado());
			pstm.setDouble(5, estacion.getPagerank());
			pstm.setDouble(6, estacion.getPesoTotal());
			pstm.setDouble(7, estacion.posx);
			pstm.setDouble(8, estacion.posy);
			
			tuplaActualizada = pstm.executeUpdate();
		}
		
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error driver");
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error SQL");
		}
		
		finally {
			if(rs!=null) try { rs.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
			if(pstm!=null) try { pstm.close(); } 
			catch (SQLException e) {e.printStackTrace(); }
			if(conex!=null) try { conex.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
		}
		
		if(tuplaActualizada==1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean eliminarEntidad(short id) {
		
		Integer tuplaEliminada = 0;
		GestorMantenimientoPostgreSQLDAO gestorMantenimiento = new GestorMantenimientoPostgreSQLDAO();
		GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			// eliminar estacion de la tabla de estaciones
			
			pstm = conex.prepareStatement("DELETE FROM lista_estaciones"
					+ " WHERE id_estacion = " + id);
			
			pstm.executeUpdate();
			
			//eliminar conexiones
			
			pstm = conex.prepareStatement("SELECT id_conexion FROM lista_conexiones_estaciones"
					+ " WHERE id_estacion = " + id);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				gestorConexion.eliminarEntidad(rs.getShort(1));
				
			}
			
			//eliminar mantenimientos
			
			pstm = conex.prepareStatement("SELECT id_mantenimiento FROM lista_mantenimientos"
					+ " WHERE id_estacion = " + id);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				gestorMantenimiento.eliminarEntidad(rs.getShort(1));
				
			}
			
			//eliminar estacion
			
			pstm = conex.prepareStatement("DELETE FROM estacion"
					+ " WHERE id_estacion = " + id);
			
			tuplaEliminada = pstm.executeUpdate();
			
		}
		
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error driver");
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error SQL");
		}
		
		finally {
			if(rs!=null) try { rs.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
			if(pstm!=null) try { pstm.close(); } 
			catch (SQLException e) {e.printStackTrace(); }
			if(conex!=null) try { conex.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
		}
		
		if(tuplaEliminada==1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Object recuperarEntidad(short id) {
		
		Estacion estacionDB = null;
		GestorMantenimientoPostgreSQLDAO gestorMantenimiento = new GestorMantenimientoPostgreSQLDAO();
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM estacion WHERE id_estacion = " + id);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				ResultSet rsAux = null;
				ArrayList<Conexion> listaDeConexionesAux = new ArrayList<Conexion>();
				ArrayList<Mantenimiento> listaDeMantenimientosAux = new ArrayList<Mantenimiento>();
				/*
				pstm = conex.prepareStatement("SELECT * FROM lista_conexiones_estaciones "
												+ "WHERE id_estacion = " + rs.getInt(1));
				rsAux = pstm.executeQuery();
				while(rsAux.next()) {
					Conexion cn = (Conexion) gestorConexion.recuperarEntidad(rsAux.getShort(2));
					listaDeConexionesAux.add(cn);
				}
				*/
				pstm = conex.prepareStatement("SELECT * FROM lista_mantenimientos "
												+ "WHERE id_estacion = " + rs.getInt(1));
				rsAux = pstm.executeQuery();
				while(rsAux.next()) {
					Mantenimiento mn = (Mantenimiento) gestorMantenimiento.recuperarEntidad(rsAux.getShort(2));
					listaDeMantenimientosAux.add(mn);
				}
				
				estacionDB = new Estacion(rs.getShort(1), 
										rs.getString(2),
										rs.getTime(3).toLocalTime(),
										rs.getTime(4).toLocalTime(),
										rs.getString(5),
										rs.getDate(6).toLocalDate(),
										rs.getDouble(7),
										rs.getDouble(8),
										rs.getDouble(9),
										rs.getDouble(10),
										listaDeConexionesAux,
										listaDeMantenimientosAux);
				
			}
		}
		
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error driver");
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error SQL");
		}
		
		finally {
			if(rs!=null) try { rs.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
			if(pstm!=null) try { pstm.close(); } 
			catch (SQLException e) {e.printStackTrace(); }
			if(conex!=null) try { conex.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
		}
		
		return estacionDB;
	}
	
	public Object traerEntidad(short id) {
		
		Estacion estacionDB = null;
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM estacion WHERE id_estacion = " + id);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				estacionDB = new Estacion(rs.getShort(1), 
										rs.getString(2),
										rs.getTime(3).toLocalTime(),
										rs.getTime(4).toLocalTime(),
										rs.getString(5),
										rs.getDate(6).toLocalDate(),
										rs.getDouble(7),
										rs.getDouble(8),
										rs.getDouble(9),
										rs.getDouble(10));
			}
		}
		
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error driver");
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error SQL");
		}
		
		finally {
			if(rs!=null) try { rs.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
			if(pstm!=null) try { pstm.close(); } 
			catch (SQLException e) {e.printStackTrace(); }
			if(conex!=null) try { conex.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
		}
		
		return estacionDB;
	}
}
