package objetos;

import java.util.HashSet;

public interface Linea {
	//TODO NO TERMINADO!
	public static HashSet<Linea> listaLineas=new HashSet<Linea>();
	public static boolean borrarLinea(Linea e) {
		return listaLineas.remove(e);
		//TODO Comunicar DAO la eliminacion de e
	}
	public static short AZUL=0;
	public static short ROJO=1;
	public static short AMARILLO=2;
	public static short VERDE=3;
	public static short VIOLETA=4;
	public static short NARANJA=5;
	public static short NEGRO=6;
	public static short BLANCO=7;
	
	public String getNombre();
	public short getColor();
	public boolean estaActiva();
	
	
	
	
}