package ConexionDB;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import objetos.Conexion;
import objetos.Estacion;
import objetos.Linea;

public class GestorLineaPostgreSQLDAO extends PostgreSQL{
	
	//private String url = "jdbc:postgresql://localhost:5432/sgtm";
	//private String clave = "benja12345";
	private Connection conex = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	
	public short insertarEntidad(Object o) {
		
		Linea linea = (Linea) o;
		short id=0;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("INSERT INTO linea VALUES (?,?,?,?,?)");
			
			pstm.setInt(1, linea.getId());
			pstm.setString(2, linea.getNombre());
			pstm.setInt(3, linea.getColor().getRGB());
			pstm.setString(4, linea.estado());
			pstm.setShort(5, linea.getTipo());
			
			Integer tuplaIncertada = pstm.executeUpdate();
			
			if(tuplaIncertada==1) {
				id = linea.getId();
				System.out.println("Linea número " + id + " fué añadida correctamente!");
			}
			
			Integer cantidadDeConexiones=0;
			for(Conexion c: linea.getListConexiones()) {
				pstm = conex.prepareStatement("INSERT INTO lista_conexiones_lineas VALUES (?,?)");
				pstm.setInt(1, linea.getId());
				pstm.setInt(2, c.getId());
				pstm.executeUpdate();
				cantidadDeConexiones++;
			}
			System.out.println(cantidadDeConexiones 
					+ " conexiones añadidas a la tabla 'LISTA_CONEXIONES_LINEA' "
					+ "pertenecientes a la estacion " + linea.getNombre());
			
			Integer cantidadDeEstaciones=0;
			for(Estacion e: linea.getListEstaciones()) {
				pstm = conex.prepareStatement("INSERT INTO lista_estaciones VALUES (?,?)");
				pstm.setInt(1, linea.getId());
				pstm.setInt(2, e.getId());
				pstm.executeUpdate();
				cantidadDeEstaciones++;
			}
			System.out.println(cantidadDeEstaciones 
					+ " conexiones añadidas a la tabla 'LISTA_ESTACIONES' "
					+ "pertenecientes a la estacion " + linea.getNombre());
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
		
		Integer cantidadLineasRecuperadas = 0;
		GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM linea");
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				ResultSet rsAux = null;
				ArrayList<Estacion> listEstacionesAux=new ArrayList<Estacion>();
				ArrayList<Conexion> listConexionesAux=new ArrayList<Conexion>();
				
				pstm = conex.prepareStatement("SELECT * FROM lista_conexiones_lineas "
						+ "WHERE id_linea = " + rs.getInt(1));
				
				rsAux = pstm.executeQuery();
				
				while(rsAux.next()) {
					Conexion cn = (Conexion) gestorConexion.recuperarEntidad(rsAux.getShort(2));
					listConexionesAux.add(cn);
				}
				
				pstm = conex.prepareStatement("SELECT * FROM lista_estaciones "
										+ "WHERE id_linea = " + rs.getInt(1));
				
				rsAux = pstm.executeQuery();
				
				while(rsAux.next()) {
					Estacion en = (Estacion) gestorEstacion.recuperarEntidad(rsAux.getShort(2));
					listEstacionesAux.add(en);
				}
				
				new Linea(rs.getShort(1),
						rs.getString(2),
						new Color(rs.getInt(3)),
						rs.getString(4),
						rs.getShort(5),
						listEstacionesAux,
						listConexionesAux);
				
				cantidadLineasRecuperadas++;
				
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
		
		return cantidadLineasRecuperadas;
	}

	@Override
	public boolean actualizarEntidad(Object o) {
		
		Linea linea = (Linea) o;
		Integer tuplaActualizada = 0;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			if (linea.getListConexiones().size() > linea.getCantidadDeConexionesEnDB()) {
				for(int i=linea.getCantidadDeConexionesEnDB(); i<linea.getListConexiones().size(); i++) {
					pstm = conex.prepareStatement("INSERT INTO lista_conexiones_lineas VALUES (?,?)");
					pstm.setShort(1, linea.getId());
					pstm.setShort(2, linea.getListConexiones().get(i).getId());
					pstm.executeUpdate();
				}
				linea.setCantidadDeConexionesEnDB(linea.getListConexiones().size());
			}
			
			if (linea.getListEstaciones().size() > linea.getCantidadDeEstacionesEnDB()) {
				for(int i=linea.getCantidadDeEstacionesEnDB(); i<linea.getListEstaciones().size(); i++) {
					pstm = conex.prepareStatement("INSERT INTO lista_estaciones VALUES (?,?)");
					pstm.setShort(1, linea.getId());
					pstm.setShort(2, linea.getListEstaciones().get(i).getId());
					pstm.executeUpdate();
				}
				linea.setCantidadDeEstacionesEnDB(linea.getListEstaciones().size());
			}
			
			pstm = conex.prepareStatement("UPDATE linea SET nombre = ?,"
					+ "color = ?,"
					+ "estado = ?,"
					+ "tipo = ?"
					+ "WHERE id_linea = " + linea.getId());
			
			pstm.setString(1, linea.getNombre());
			pstm.setInt(2, linea.getColor().getRGB());
			pstm.setString(3, linea.estado());
			pstm.setShort(4, linea.getTipo());
			
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
		GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			// eliminar linea de la tabla de estaciones
			
			pstm = conex.prepareStatement("DELETE FROM lista_estaciones"
					+ " WHERE id_linea = " + id);
			
			pstm.executeUpdate();
			
			//eliminar conexiones
			
			pstm = conex.prepareStatement("SELECT id_conexion FROM lista_conexiones_lineas"
					+ " WHERE id_linea = " + id);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				gestorConexion.eliminarEntidad(rs.getShort(1));
				
			}
			
			//eliminar linea
			
			pstm = conex.prepareStatement("DELETE FROM linea"
					+ " WHERE id_linea = " + id);
			
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
		
		Linea lineaDB = null;
		GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM linea WHERE id_linea = " + id);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				ResultSet rsAux = null;
				ArrayList<Estacion> listEstacionesAux=new ArrayList<Estacion>();
				ArrayList<Conexion> listConexionesAux=new ArrayList<Conexion>();
				
				pstm = conex.prepareStatement("SELECT * FROM lista_conexiones_lineas "
						+ "WHERE id_linea = " + rs.getInt(1));
				
				rsAux = pstm.executeQuery();
				
				while(rsAux.next()) {
					Conexion cn = (Conexion) gestorConexion.recuperarEntidad(rsAux.getShort(2));
					listConexionesAux.add(cn);
				}
				
				pstm = conex.prepareStatement("SELECT * FROM lista_estaciones "
										+ "WHERE id_linea = " + rs.getInt(1));
				
				rsAux = pstm.executeQuery();
				
				while(rsAux.next()) {
					Estacion en = (Estacion) gestorEstacion.recuperarEntidad(rsAux.getShort(2));
					listEstacionesAux.add(en);
				}
				
				lineaDB = new Linea(rs.getShort(1),
						rs.getString(2),
						new Color(rs.getInt(3)),
						rs.getString(4),
						rs.getShort(5),
						listEstacionesAux,
						listConexionesAux);
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
		
		return lineaDB;
	}
	
	public Object traerEntidad(short id) {
		
		Linea lineaDB = null;
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM linea WHERE id_linea = " + id);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				lineaDB = new Linea(rs.getShort(1),
						rs.getString(2),
						new Color(rs.getInt(3)),
						rs.getString(4),
						rs.getShort(5));
				
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
		
		return lineaDB;
	}

}
