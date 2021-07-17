package elementosSwing;

import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;

public class BigWindow extends JFrame {
	
	public static ArrayList<BigWindow> listBW = new ArrayList<BigWindow>();
	
	public String nombre;
	
	public BigWindow(String title) throws HeadlessException {
		super();
		this.setContentPane(new JPanel());
		this.getContentPane().setLayout(new GridBagLayout());
		this.setTitle(title);
		nombre=title;
		
		listBW.add(this);
	}
	
	

}
