package controlador;

import java.sql.Date;
import java.util.Calendar;

public class DateToEdad {
	

	public int calcularEdad(Date date) {
		  Calendar fechaNac = Calendar.getInstance();
		  fechaNac.setTime(date);
		
	    Calendar fechaActual = Calendar.getInstance();
	 
	    // Cálculo de las diferencias.
	    int anios = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
	    int meses = fechaActual.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
	    int dias = fechaActual.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);
	 
	    // Hay que comprobar si el día de su cumpleaños es posterior
	    // a la fecha actual, para restar 1 a la diferencia de años,
	    // pues aún no ha sido su cumpleaños.</code>
	 
	    if(meses < 0 // Aún no es el mes de su cumpleaños
	       || (meses==0 && dias < 0)) { // o es el mes pero no ha llegado el día.
	 
	        anios--;
	    }
	    return anios;
	}

}
