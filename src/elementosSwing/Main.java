package elementosSwing;

import javax.swing.JFrame;

import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlContrastIJTheme;

public class Main {
	
	
	public static void main(String args[]) {
		FlatNightOwlContrastIJTheme.install();
		FlatInspector.install( "ctrl shift alt T" );
		JFrame vnt=new Home("Prueba");
	}
	
}
