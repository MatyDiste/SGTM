package elementosSwing;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import net.miginfocom.swing.MigLayout;
import objetos.Estacion;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class InfoEstacionEDIT extends JPanel implements InfoEstacionInterface {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JCheckBox chckbxNewCheckBox = new JCheckBox("Habilitado");

	/**
	 * Create the panel.
	 */
	public InfoEstacionEDIT(Estacion e) {
		//System.out.println("Creand infoestacion EDIT");
		setLayout(new MigLayout("", "[][grow]", "[][][][][][][]"));
		
		JLabel lblNewLabel_8 = new JLabel("ESTACIÓN");
		lblNewLabel_8.setFont(new Font(null,Font.BOLD, 16));
		lblNewLabel_8.setIcon(new ImageIcon("./assets/estacion_icon.png"));
		add(lblNewLabel_8, "cell 1 0");
		
		JLabel lblNewLabel = new JLabel("ID");
		add(lblNewLabel, "cell 0 1,alignx trailing");
		
		textField = new JTextField(e.getId().toString());
		textField.setEditable(false);
		add(textField, "cell 1 1,alignx left");
		textField.setColumns(15);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		add(lblNewLabel_1, "cell 0 2,alignx trailing");
		
		textField_1 = new JTextField(e.getNombre());
		add(textField_1, "cell 1 2,alignx left");
		textField_1.setColumns(15);
		
		JLabel lblNewLabel_2 = new JLabel("Horario apertura");
		add(lblNewLabel_2, "cell 0 3,alignx trailing");
		
		textField_2 = new JTextField(e.getHorarioApertura().format(DateTimeFormatter.ISO_TIME));
		textField_2.setPreferredSize(new Dimension(100,10));
		add(textField_2, "cell 1 3,alignx left");
		textField_2.setColumns(15);
		
		JLabel lblNewLabel_3 = new JLabel("Horario cierre");
		add(lblNewLabel_3, "cell 0 4,alignx trailing");
		
		textField_3 = new JTextField(e.getHorarioCierre().format(DateTimeFormatter.ISO_TIME));
		textField_3.setPreferredSize(new Dimension(100,10));
		add(textField_3, "cell 1 4,alignx left");
		textField_3.setColumns(15);
		
		JLabel lblNewLabel_4 = new JLabel("Estado");
		add(lblNewLabel_4, "cell 0 5,alignx trailing");
		
		textField_4 = new JTextField(e.getEstado());
		textField_4.setEditable(false);
		textField_4.setPreferredSize(new Dimension(100,10));
		add(textField_4, "flowx,cell 1 5,alignx left");
		textField_4.setColumns(15);
		
		JLabel lblNewLabel_5 = new JLabel("Fecha ultimo mantenimiento");
		add(lblNewLabel_5, "cell 0 6,alignx trailing");
		
		chckbxNewCheckBox.setSelected(e.getEstado().equals("OPERATIVA"));
		chckbxNewCheckBox.addChangeListener(event -> {
			//System.out.println("Escribiendo en el campo...");
			textField_4.setText((chckbxNewCheckBox.isSelected())? "OPERATIVA" : "EN MANTENIMIENTO");
		});
		add(chckbxNewCheckBox, "cell 1 5");
		
		textField_5 = new JTextField(e.getFechaUltimoMantenimiento().format(DateTimeFormatter.ISO_LOCAL_DATE));
		textField_5.setPreferredSize(new Dimension(150,10));
		textField_5.setColumns(15);
		textField_5.setEditable(false);
		add(textField_5, "cell 1 6,alignx left");

	}
	public Short getID() {
		return Short.parseShort(textField.getText());
	}
	public String getNombre() {
		return textField_1.getText();
	}
	public LocalTime getHorarioApertura() throws DateTimeParseException{
		return LocalTime.parse(textField_2.getText());
	}
	public LocalTime getHorarioCierre() throws DateTimeParseException{
		return LocalTime.parse(textField_3.getText());
	}
	public Boolean getEstado() {
		return chckbxNewCheckBox.isSelected();
	}
	public LocalDate getFechaMantenimiento() {
		return LocalDate.parse(textField_5.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
	}

}
