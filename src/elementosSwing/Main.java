package elementosSwing;

import java.awt.Color;
import java.time.LocalTime;

import javax.swing.JFrame;

import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlContrastIJTheme;

import objetos.Conexion;
import objetos.Estacion;
import objetos.Linea;


public class Main {
	
	public static Boolean modoAdmin;
	
	@SuppressWarnings("deprecation")
	public static void main(String args[]) {
		FlatNightOwlContrastIJTheme.install();
		FlatInspector.install( "ctrl shift alt T" ); //Con CTRL+SHIFT+ALT+T se activa el modo inspector, ESC para salir
		
		
		@SuppressWarnings("unused")
		JFrame vnt=new Home("Sistema de gestion de Transporte Multimodal");
		
		
	}
	
	public static void setModoAdmin(Boolean b) {
		modoAdmin=b;
		//System.out.println("Seteo " + b.toString());
	}
	
	public static void openMainWindow() {
		@SuppressWarnings("unused")
		MainWindow mw= new MainWindow("Sistema gestion de Transporte Multimodal");
		for(int i=0; i<9; i++) {
			debugGenEstacion();
		}
		for(int i=0; i<4; i++) {
			debugGenLinea();
			//System.out.println("Creada linea");
		}
		Linea.listLineas.stream().forEach(l -> {
			//System.out.println("Creada conexion");
			debugGenConexiones(l);
		});
	}
	
	public static void debugGenEstacion() {
		new Estacion("DBG"+Integer.toString((int)(Math.random()*31)), LocalTime.of((int)(Math.random()*24), 0), LocalTime.of((int)(Math.random()*24), 0), true);
	}
	
	public static void debugGenLinea() {
		new Linea("Ldbg"+Integer.toString((int)(Math.random()*31)), new Color((int)(Math.random()*Integer.MAX_VALUE-1)), true);
		//System.out.println("Linea "+l.getNombre()+" | Color : "+l.getColor().toString());
	}
	
	public static void debugGenConexiones(Linea l) {
		Integer cantidad=(int)(5);
		Estacion est=Estacion.listEstaciones.stream().toList().get((int)(Math.random()*Estacion.listEstaciones.size()));
		l.getListEstaciones().add(est);
		for(int i=0; i<cantidad; i++) {
			Estacion est2=Estacion.listEstaciones.stream().toList().get((int)(Math.random()*Estacion.listEstaciones.size()));
			l.getListConexiones().add(new Conexion(est, est2, l));
			l.getListEstaciones().add(est2);
			est=est2;

		}
		
	}
	
}
