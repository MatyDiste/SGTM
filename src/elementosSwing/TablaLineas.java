package elementosSwing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import objetos.Linea;

public class TablaLineas extends JTable {
	
	public TablaLineas() {
		super(new MiTableModelLinea());
		this.setAutoCreateRowSorter(true);
	}
	
}

class MiTableModelLinea extends AbstractTableModel{

	private List<Linea> listLinea=new ArrayList<Linea>();
	
	public MiTableModelLinea() {
		super();
		listLinea.addAll(Linea.listLineas.stream().toList());
	}
	
	
	@Override
	public String getColumnName(int col) {
		switch(col) {
		case 0: return "Nombre";
		case 1: return "Color";
		case 2: return "Estado";
		default: return "ERROR";
		}
	}
	
	@Override
	public int getRowCount() {
		return Linea.listLineas.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0: return listLinea.get(rowIndex).getNombre();
		case 1: return listLinea.get(rowIndex).getColor().toString();
		case 2: return listLinea.get(rowIndex).estado();
		default: return "ERROR";
		}
	}
}
