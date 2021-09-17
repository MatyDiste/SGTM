package conexionDB;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import objetos.Ticket;



public class GestorTicketPostgreSQLDAO extends PostgreSQL{

	private Connection conex = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;



	@Override
	public short insertarEntidad(Object o) {

		Ticket ticket = (Ticket) o;

		try {

			Class.forName("org.postgresql.Driver");

			conex = DriverManager.getConnection(url, "postgres", clave);

			pstm = conex.prepareStatement("INSERT INTO ticket VALUES (null,?,?,?,?,?)");

			pstm.setDouble(1, ticket.getCosto());
			pstm.setDouble(2, ticket.getDistancia());
			pstm.setDate(3, Date.valueOf(ticket.getFechaEmision()));
			pstm.setShort(4, ticket.getInicio().getId());
			pstm.setShort(5, ticket.getFin().getId());

			pstm.executeUpdate();
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

		return 0;
	}



	@Override
	public Integer recuperarEntidades() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean actualizarEntidad(Object o) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean eliminarEntidad(short id) {
		// TODO Auto-generated method stub
		return false;
	}



}