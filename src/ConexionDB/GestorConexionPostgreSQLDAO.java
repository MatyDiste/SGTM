package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import objetos.Conexion;
import objetos.Estacion;
import objetos.Linea;

public class GestorConexionPostgreSQLDAO extends PostgreSQL{
	
	private String url = "jdbc:postgresql://localhost:5432/sgtm";
	private String clave = "benja12345";
	private Connection conex = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	
	public short insertarEntidad(Object o) {
		
		Conexion conexion = (Conexion) o;
		short id=0;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("INSERT INTO conexion VALUES (?,?,?,?,?,?,?,?,?)");
			
			pstm.setInt(1, conexion.getId());
			pstm.setDouble(2, conexion.getDistancia());
			pstm.setDouble(3, conexion.getDuracion());
			pstm.setInt(4, conexion.getCantMaxPasajeros());
			pstm.setString(5, conexion.estado());
			pstm.setDouble(6, conexion.getCosto());
			pstm.setInt(7, conexion.getE1().getId());
			pstm.setInt(8, conexion.getE2().getId());
			pstm.setInt(9, conexion.getLinea().getId());
			
			Integer tuplaIncertada = pstm.executeUpdate();
			
			if(tuplaIncertada==1) {
				id = conexion.getId();
				System.out.println("Conexión número " + id + " fué añadida correctamente!");
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
		
		return id;
	}

	@Override
	public Integer recuperarEntidades() {
		
		Integer cantidadConexionesRecuperadas = 0;
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		GestorLineaPostgreSQLDAO gestorLinea = new GestorLineaPostgreSQLDAO();
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM conexion");
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				Estacion estacion1 = (Estacion) gestorEstacion.recuperarEntidad(rs.getShort(7));
				Estacion estacion2 = (Estacion) gestorEstacion.recuperarEntidad(rs.getShort(8));
				Linea linea = (Linea) gestorLinea.recuperarEntidad(rs.getShort(9));
				
				new Conexion(rs.getShort(1), 
						rs.getDouble(2),
						rs.getDouble(3),
						rs.getInt(4),
						rs.getString(5),
						rs.getDouble(6),
						estacion1,
						estacion2,
						linea);
				
				cantidadConexionesRecuperadas++;
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
		
		return cantidadConexionesRecuperadas;
	}

	@Override
	public boolean actualizarEntidad(Object o) {
		
		Conexion conexion = (Conexion) o;
		Integer tuplaIncertada = 0;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("UPDATE conexion SET distancia = ?,"
					+ "duracion = ?,"
					+ "capacidad_pasajeros = ?,"
					+ "estado = ?,"
					+ "costo = ?,"
					+ "id_estacion_1 = ?,"
					+ "id_estacion_2 = ?,"
					+ "id_linea = ?"
					+ "WHERE id_conexion = " + conexion.getId());
			
			pstm.setDouble(1, conexion.getDistancia());
			pstm.setDouble(2, conexion.getDuracion());
			pstm.setInt(3, conexion.getCantMaxPasajeros());
			pstm.setString(4, conexion.estado());
			pstm.setDouble(5, conexion.getCosto());
			pstm.setInt(6, conexion.getE1().getId());
			pstm.setInt(7, conexion.getE2().getId());
			pstm.setInt(8, conexion.getLinea().getId());
			
			tuplaIncertada = pstm.executeUpdate();
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
		
		if(tuplaIncertada==1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean eliminarEntidad(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object recuperarEntidad(short id) {
		
		Conexion conexionDB = null;
		GestorEstacionPostgreSQLDAO gestorEstacion = new GestorEstacionPostgreSQLDAO();
		GestorLineaPostgreSQLDAO gestorLinea = new GestorLineaPostgreSQLDAO();
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM conexion WHERE id_conexion = " + id);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				Estacion estacion1 = (Estacion) gestorEstacion.recuperarEntidad(rs.getShort(7));
				Estacion estacion2 = (Estacion) gestorEstacion.recuperarEntidad(rs.getShort(8));
				Linea linea = (Linea) gestorLinea.recuperarEntidad(rs.getShort(9));
				
				conexionDB = new Conexion(rs.getShort(1), 
						rs.getDouble(2),
						rs.getDouble(3),
						rs.getInt(4),
						rs.getString(5),
						rs.getDouble(6),
						estacion1,
						estacion2,
						linea);
				
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
		
		return conexionDB;
	}

}
