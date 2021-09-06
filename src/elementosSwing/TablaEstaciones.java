package elementosSwing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import objetos.Estacion;

public class TablaEstaciones extends JTable {
	
	public TablaEstaciones() {
		super(new MiTableModelEstacion());
		MiTableModelEstacion tme=(MiTableModelEstacion) this.getModel();
		TableRowSorter<MiTableModelEstacion> trs=new TableRowSorter<MiTableModelEstacion>(tme);
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
					RowSorter<MiTableModelEstacion> tm=(RowSorter<MiTableModelEstacion>)aux.getRowSorter();
					Estacion est= Estacion.buscarID((short) aux.getModel().getValueAt(tm.convertRowIndexToModel(aux.getSelectedRow()), 0));
					PanelInfo.setEstacion(est);
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

class MiTableModelEstacion extends AbstractTableModel{
	List<Estacion> listEst=Estacion.listEstaciones.stream().toList();
	
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
