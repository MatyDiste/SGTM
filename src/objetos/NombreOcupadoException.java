package objetos;

public class NombreOcupadoException extends Exception {
	public NombreOcupadoException(String nombre) {
		super("Nombre ocupado: "+nombre);
	}
}
