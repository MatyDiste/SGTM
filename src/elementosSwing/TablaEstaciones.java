package elementosSwing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import objetos.Estacion;
import objetos.Linea;

public class TablaEstaciones extends JTable {
	
	public TablaEstaciones() {
		super(new MiTableModelEstacion());
		this.setAutoCreateRowSorter(true);
	}
	
}

class MiTableModelEstacion extends AbstractTableModel{

	private List<Estacion> listEst=new ArrayList<Estacion>();
	
	public MiTableModelEstacion() {
		super();
		listEst.addAll(Estacion.listEstaciones.stream().toList());
	}
	
	
	@Override
	public String getColumnName(int col) {
		switch(col) {
		case 0: return "ID";
		case 1: return "Nombre";
		case 2: return "Hora Apertura";
		case 3: return "Hora Cierre";
		case 4: return "Estado";
		case 5: return "Ultimo Mantenimiento";
		case 6: return "Page rank";
		default: return "ERROR";
		}
	}
	
	@Override
	public int getRowCount() {
		return Estacion.listEstaciones.size();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0: return listEst.get(rowIndex).id;
		case 1: return listEst.get(rowIndex).nombre;
		case 2: return listEst.get(rowIndex).horarioApertura;
		case 3: return listEst.get(rowIndex).horarioCierre;
		case 4: if(listEst.get(rowIndex).mantenimiento) return "En mantenimiento"; else return "Activo";
		case 5: return listEst.get(rowIndex).fechaUltimoMantenimiento;
		case 6: return listEst.get(rowIndex).pagerank;
		default: return "ERROR";
		}
	}
}
