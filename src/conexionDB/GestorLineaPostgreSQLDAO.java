package conexionDB;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import objetos.Conexion;
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
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM linea");
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				new Linea(rs.getShort(1),
						rs.getString(2),
						new Color(rs.getInt(3)),
						rs.getString(4),
						rs.getShort(5));
				
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
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("DELETE FROM linea"
					+ " WHERE id_linea = " + id );
			
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

}
