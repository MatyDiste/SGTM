package elementosSwing;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public interface InfoEstacionInterface {
	public Short getID();
	public String getNombre();
	public LocalTime getHorarioApertura() throws DateTimeParseException;
	public LocalTime getHorarioCierre() throws DateTimeParseException;
	public Boolean getEstado();
	public LocalDate getFechaMantenimiento();
}
