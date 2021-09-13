package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import objetos.Conexion;
import objetos.Estacion;
import objetos.Linea;
import objetos.Mantenimiento;

public abstract class PostgreSQL {
	
	private static String url = "jdbc:postgresql://localhost:5432/sgtm";
	private static String clave = "benja12345";
	private static Connection conex = null;
	private static PreparedStatement pstm = null;
	private static ResultSet rs = null;
	
	public abstract short insertarEntidad(Object o); //Numero ID de la entidad insertada
	public abstract Integer recuperarEntidades(); //Cantidad de tuplas recuperadas de la DB
	public abstract boolean actualizarEntidad(Object o); //TRUE: Éxito -- FALSE:Fracaso
	public abstract boolean eliminarEntidad(Object o); //TRUE: Éxito -- FALSE:Fracaso
	public abstract Object recuperarEntidad(short id); //Objeto casteable a Conexion, 
											//Estacion, Linea o Mantenimiento según el caso.
	
	public static void setearContadoresId() {
		
		try {
			
			Class.forName("org.postgresql.Driver");
			
			conex = DriverManager.getConnection(url, "postgres", clave);
			
			pstm = conex.prepareStatement("SELECT MAX (id_conexion) FROM conexion");
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				
				if(rs.getString(1) != null) {
					Conexion.setContadorId((short) (Short.parseShort(rs.getString(1)) + 1));
				}
				else {
					Conexion.setContadorId((short) 1);
				}
			}

			pstm = conex.prepareStatement("SELECT MAX (id_estacion) FROM estacion");
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				
				if(rs.getString(1) != null) {
					Estacion.setContadorId((short) (Short.parseShort(rs.getString(1)) + 1));
				}
				else {
					Estacion.setContadorId((short) 1001);
				}
			}
			
			pstm = conex.prepareStatement("SELECT MAX (id_linea) FROM linea");
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				
				if(rs.getString(1) != null) {
					Linea.setContadorId((short) (Short.parseShort(rs.getString(1)) + 1));
				}
				else {
					Linea.setContadorId((short) 1001);
				}
			}
			
			pstm = conex.prepareStatement("SELECT MAX (id_mantenimiento) FROM mantenimiento");
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				
				if(rs.getString(1) != null) {
					Mantenimiento.setContadorId((short) (Short.parseShort(rs.getString(1)) + 1));
				}
				else {
					Mantenimiento.setContadorId((short) 1);
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
		
	}
	
}
