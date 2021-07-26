package elementosSwing;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.extras.components.*;

public class Home extends JFrame {
	
	private FlatButton btnuser;
	private FlatButton btnadmin;
	private JLabel texto;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public Home(String t) {
		super(t);
		this.setLayout(new GridBagLayout());
		this.setBounds(0, 0, 500, 350);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//añadir cosas
		btnuser=new BtnPrincipal("Usuario");
		btnuser.addActionListener(e -> {
			Main.setModoAdmin(false);
			Main.openMainWindow();
			this.dispose();
			//TODO eliminar ventana y crear proxima (llamar Main)
		});
		btnuser.setPreferredSize(new Dimension(175,175));
		btnuser.setIcon(new ImageIcon("./assets/user_icon.png"));
		btnadmin=new BtnPrincipal("Administrador");
		btnadmin.addActionListener(e -> {
			Main.setModoAdmin(true);
			Main.openMainWindow();
			this.dispose();
			//TODO eliminar ventana y crear proxima (llamar Main)
		});
		btnadmin.setPreferredSize(new Dimension(175,175));
		btnadmin.setIcon(new ImageIcon("./assets/admin_icon.png"));
		texto=new JLabel("Bienvenid@! Por favor ingrese al sistema.", SwingConstants.CENTER);
		texto.setPreferredSize(new Dimension(400,20));
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridheight=1;
		gbc.gridwidth=2;
		gbc.weightx=0.0;
		gbc.weighty=0.0;
		gbc.insets=new Insets( 10, 0, 10, 0);
		gbc.anchor=GridBagConstraints.CENTER;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(texto, gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridheight=1;
		gbc.gridwidth=1;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.insets=new Insets( 10, 0, 10, 5);
		gbc.fill=GridBagConstraints.NONE;
		this.add(btnuser, gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.gridheight=1;
		gbc.gridwidth=1;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		gbc.insets=new Insets( 10, 5, 10, 0);
		gbc.fill=GridBagConstraints.NONE;
		this.add(btnadmin, gbc);
		
		this.pack();
		this.setVisible(true);
	}
	
}
