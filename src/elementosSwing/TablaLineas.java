package elementosSwing;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import objetos.Linea;

public class TablaLineas extends JTable {
	
	public TablaLineas() {
		super(new MiTableModelLinea());
		MiTableModelLinea tbl=(MiTableModelLinea) this.getModel();
		TableRowSorter<MiTableModelLinea> trs=new TableRowSorter<MiTableModelLinea>(tbl);
		trs.setSortable(1, false); //No se ordena por color
		this.setRowSorter(trs);
		
		JTable aux=this;
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(aux.getSelectedRow()!=-1 && PanelBusqueda.esSeleccionable()) {
					@SuppressWarnings("unchecked")
					RowSorter<MiTableModelLinea> rs=(RowSorter<MiTableModelLinea>)aux.getRowSorter();
					Linea lin= Linea.getLineaPorNombre((String) aux.getModel().getValueAt(rs.convertRowIndexToModel(aux.getSelectedRow()), 0));
					PanelInfo.setLinea(lin);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
}

class MiTableModelLinea extends AbstractTableModel{
	List<Linea> listLinea=Linea.listLineas.stream().toList();
	
	public MiTableModelLinea() {
		super();
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
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0: return listLinea.get(rowIndex).getNombre();
		case 1: 
			Color lin=listLinea.get(rowIndex).getColor();
			return "R:"+lin.getRed()+" G:"+lin.getGreen()+" B:"+lin.getBlue();
		case 2: 
			switch(listLinea.get(rowIndex).getTipo()) {
			case 0:
				return "Colectivo";
			case 1:
				return "Tren";
			case 2:
				return "Subterráneo";
			default:
				return "ERROR";
				
				
			}
		case 3: return listLinea.get(rowIndex).estado();
		default: return "ERROR";
		}
	}
}

