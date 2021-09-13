package ConexionDB;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import objetos.Conexion;
import objetos.Estacion;
import objetos.Linea;

public class GestorLineaPostgreSQLDAO extends PostgreSQL{
	
	private String url = "jdbc:postgresql://localhost:5432/sgtm";
	private String clave = "benja12345";
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
		Linea lineaDB = null;
		GestorConexionPostgreSQLDAO gestorConexion = new GestorConexionPostgreSQLDAO();
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM linea");
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				ResultSet rsAux = null;
				List<Estacion> listEstacionesAux=new ArrayList<Estacion>();
				HashSet<Conexion> listConexionesAux=new HashSet<Conexion>();
				
				lineaDB = new Linea(rs.getShort(1),
						rs.getString(2),
						new Color(rs.getInt(3)),
						rs.getString(4),
						rs.getShort(5));
				
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
				
				lineaDB.setListConexiones(listConexionesAux);
				lineaDB.setListEstaciones(listEstacionesAux);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminarEntidad(Object o) {
		// TODO Auto-generated method stub
		return false;
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
				List<Estacion> listEstacionesAux=new ArrayList<Estacion>();
				HashSet<Conexion> listConexionesAux=new HashSet<Conexion>();
				
				lineaDB = new Linea(rs.getShort(1),
						rs.getString(2),
						new Color(rs.getInt(3)),
						rs.getString(4),
						rs.getShort(5));
				
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
				
				lineaDB.setListConexiones(listConexionesAux);
				lineaDB.setListEstaciones(listEstacionesAux);
				
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
