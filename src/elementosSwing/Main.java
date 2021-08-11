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
		for(int i=0; i<15; i++) {
			debugGenEstacion();
		}
		for(int i=0; i<5; i++) {
			debugGenLinea();
			System.out.println("Creada linea");
		}
		Linea.listLineas.stream().forEach(l -> {
			//System.out.println("Creada conexion");
			debugGenConexiones(l);
		});
		
		
		JFrame vnt=new Home("Sistema de gestion de Transporte Multimodal");
		
		
	}
	
	public static void setModoAdmin(Boolean b) {
		modoAdmin=b;
		//System.out.println("Seteo " + b.toString());
	}
	
	public static void openMainWindow() {
		MainWindow mw= new MainWindow("Sistema gestion de Transporte Multimodal");
	}
	
	public static void debugGenEstacion() {
		new Estacion((short)(Math.random()*32000), "DBG"+Integer.toString((int)(Math.random()*31)), LocalTime.of((int)(Math.random()*24), 0), LocalTime.of((int)(Math.random()*24), 0), true);
	}
	
	public static void debugGenLinea() {
		new Linea("Ldbg"+Integer.toString((int)(Math.random()*31)), new Color((int)(Math.random()*Integer.MAX_VALUE-1)), true);
	}
	
	public static void debugGenConexiones(Linea l) {
		Integer cantidad=(int)(Math.random()*6);
		Estacion est=Estacion.listEstaciones.stream().findAny().get();
		l.getListEstaciones().add(est);
		for(int i=0; i<cantidad; i++) {
			Estacion est2=Estacion.listEstaciones.stream().findAny().get();
			l.getListConexiones().add(new Conexion(est, est2, l));
			l.getListEstaciones().add(est2);
			est=est2;

		}
		
	}
	
}
