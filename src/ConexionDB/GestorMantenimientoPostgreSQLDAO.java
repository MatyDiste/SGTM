package ConexionDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import objetos.Mantenimiento;

public class GestorMantenimientoPostgreSQLDAO extends PostgreSQL{
	
	//private String url = "jdbc:postgresql://localhost:5432/sgtm";
	//private String clave = "benja12345";
	private Connection conex = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	
	public short insertarEntidad(Object o) {
		
		Mantenimiento mantenimiento = (Mantenimiento) o;
		short id=0;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("INSERT INTO mantenimiento VALUES (?,?,?,?)");
			
			pstm.setInt(1, mantenimiento.getId());
			pstm.setDate(2, Date.valueOf(mantenimiento.getFechaInicio()));
			pstm.setDate(3, null);
			pstm.setString(4, mantenimiento.getDescripcion());
			
			Integer tuplaIncertada = pstm.executeUpdate();
			
			if(tuplaIncertada==1) {
				id = mantenimiento.getId();
				System.out.println("Mantenimiento número " + id + " fué añadido correctamente!");
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
		
		Integer cantidadMantenimientosRecuperadas = 0;
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM mantenimiento");
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				if(rs.getDate(3) == null) {
					new Mantenimiento(rs.getShort(1),
							rs.getDate(2).toLocalDate(),
							null,
							rs.getString(4));
				}
				else {
					new Mantenimiento(rs.getShort(1),
							rs.getDate(2).toLocalDate(),
							rs.getDate(3).toLocalDate(),
							rs.getString(4));
				}
				cantidadMantenimientosRecuperadas++;
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
		
		return cantidadMantenimientosRecuperadas;
	}

	@Override
	public boolean actualizarEntidad(Object o) {
		
		Mantenimiento mantenimiento = (Mantenimiento) o;
		Integer tuplaActualizada=0;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("UPDATE mantenimiento SET fecha_inicio = ?,"
					+ "fecha_fin = ?,"
					+ "descripcion = ?"
					+ "WHERE id_mantenimiento = " + mantenimiento.getId());
			
			pstm.setDate(1, Date.valueOf(mantenimiento.getFechaInicio()));
			pstm.setDate(2, Date.valueOf(mantenimiento.getFechaFin()));
			pstm.setString(3, mantenimiento.getDescripcion());

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
			
			//eliminar de la lista de mantenimientos
			
			pstm = conex.prepareStatement("DELETE FROM lista_mantenimientos"
					+ " WHERE id_mantenimiento = " + id);
			
			pstm.executeUpdate();
			
			//eliminar mantenimiento
			
			pstm = conex.prepareStatement("DELETE FROM mantenimiento"
					+ " WHERE id_mantenimiento = " + id);

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
		
		Mantenimiento mantenimientoDB = null;
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT * FROM mantenimiento WHERE id_mantenimiento = " + id);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				
				if(rs.getDate(3) == null) {
					mantenimientoDB = new Mantenimiento(rs.getShort(1),
							rs.getDate(2).toLocalDate(),
							null,
							rs.getString(4));
				}
				else {
					mantenimientoDB = new Mantenimiento(rs.getShort(1),
							rs.getDate(2).toLocalDate(),
							rs.getDate(3).toLocalDate(),
							rs.getString(4));
				}
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
		
		return mantenimientoDB;
	}

}
