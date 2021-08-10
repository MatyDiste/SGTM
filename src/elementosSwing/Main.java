package elementosSwing;

import javax.swing.JFrame;

import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlContrastIJTheme;


public class Main {
	
	public static Boolean modoAdmin;
	
	@SuppressWarnings("deprecation")
	public static void main(String args[]) {
		FlatNightOwlContrastIJTheme.install();
		FlatInspector.install( "ctrl shift alt T" ); //Con CTRL+SHIFT+ALT+T se activa el modo inspector, ESC para salir
		JFrame vnt=new Home("Sistema de gestion de Transporte Multimodal");
	}
	
	public static void setModoAdmin(Boolean b) {
		modoAdmin=b;
		//System.out.println("Seteo " + b.toString());
	}
	
	public static void openMainWindow() {
		MainWindow mw= new MainWindow("Sistema gestion de Transporte Multimodal");
	}
	
}
