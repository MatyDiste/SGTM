package elementosSwing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import objetos.Estacion;

public class TablaEstaciones extends JTable {
	
	public TablaEstaciones() {
		super(new MiTableModelEstacion());
		this.setAutoCreateRowSorter(true);
	}
	
}

class MiTableModelEstacion extends AbstractTableModel{
	
	public MiTableModelEstacion() {
		super();
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
		List<Estacion> listEst=new ArrayList<Estacion>();
		listEst.addAll(Estacion.listEstaciones.stream().toList());
		switch(columnIndex) {
		case 0: return listEst.get(rowIndex).getId();
		case 1: return listEst.get(rowIndex).getNombre();
		case 2: return listEst.get(rowIndex).getHorarioApertura();
		case 3: return listEst.get(rowIndex).getHorarioCierre();
		case 4: if(!listEst.get(rowIndex).getEstado().equals("OPERATIVA")) return "En mantenimiento"; else return "Activo";
		case 5: return listEst.get(rowIndex).getFechaUltimoMantenimiento();
		case 6: return listEst.get(rowIndex).getPagerank();
		default: return "ERROR";
		}
	}
}
