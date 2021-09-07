package elementosSwing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import objetos.Recorrido;
import objetos.Ticket;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FGenerarTicket extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FGenerarTicket frame = new FGenerarTicket();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public FGenerarTicket(Recorrido r) {
		setAlwaysOnTop(true);
		setTitle("Comprar ticket");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 390, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Confirme la compra de su ticket");
		lblNewLabel.setFont(new Font(null, Font.BOLD, 11));
		contentPane.add(lblNewLabel, "cell 0 0 3 1,alignx center");
		
		JLabel lblNewLabel_1 = new JLabel("Desde estación: ");
		contentPane.add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		textField = new JTextField(r.getEstacionInicial().getNombre());
		textField.setEditable(false);
		contentPane.add(textField, "cell 1 1,alignx left");
		textField.setColumns(20);
		
		JLabel lblNewLabel_2 = new JLabel("Hasta estación: ");
		contentPane.add(lblNewLabel_2, "cell 0 2,alignx trailing");
		
		textField_1 = new JTextField(r.getEstacionFinal().getNombre());
		textField_1.setEditable(false);
		contentPane.add(textField_1, "cell 1 2,alignx left");
		textField_1.setColumns(20);
		
		JLabel lblNewLabel_3 = new JLabel("Costo:");
		lblNewLabel_3.setFont(new Font(null, Font.PLAIN, 18));
		contentPane.add(lblNewLabel_3, "cell 0 3 1 2,alignx trailing");
		
		DecimalFormat df=new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.FLOOR);
		textField_2 = new JTextField(df.format(r.costoTotal()));
		textField_2.setEditable(false);
		textField_2.setFont(new Font(null, Font.BOLD, 18));
		contentPane.add(textField_2, "cell 1 3 1 2,alignx left");
		textField_2.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addActionListener(e -> {
			this.dispose();
		});
		contentPane.add(btnNewButton_1, "flowx,cell 1 6,alignx center");
		
		JButton btnNewButton = new JButton("Comprar");
		btnNewButton.addActionListener(e -> {
			new Ticket(r);
			MainWindow.getInstance().unsetModoRecorrido();
			this.dispose();
		});
		btnNewButton.setIcon(new ImageIcon("./assets/minibuy_icon.png"));
		contentPane.add(btnNewButton, "cell 1 6,alignx center");
	}

}
