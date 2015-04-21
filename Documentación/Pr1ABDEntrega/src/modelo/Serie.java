package modelo;

import java.sql.Date;
import java.util.Calendar;

public class Serie {
	
	private int idSerie;
	private String nombre;
	private String titular;
	private String sinopsis;
	private Date estreno;
	private Date finalizacion;

	
	public Serie(int idSerie, String nombre, String titular, String sinopsis,
			Date estreno, Date finalizacion) {
		this.idSerie = idSerie;
		this.nombre = nombre;
		this.titular = titular;
		this.sinopsis = sinopsis;
		this.estreno = estreno;
		this.finalizacion = finalizacion;

	}
	
	public Date getFinalizacion() {
		return finalizacion;
	}

	public void setFinalizacion(Date finalizacion) {
		this.finalizacion = finalizacion;
	}

	public int getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	public Date getEstreno() {
		return estreno;
	}
	public void setEstreno(Date estreno) {
		this.estreno = estreno;
	}

	public String toString(){
		Calendar cal = Calendar.getInstance();
	    cal.setTime(this.estreno);
	    int year = cal.get(Calendar.YEAR);
		String cadena = this.nombre + " ( " + year + " - ";
		if(this.finalizacion==null)
			cadena = cadena + "? )";
		else{
			cal.setTime(this.finalizacion);
		    int year2 = cal.get(Calendar.YEAR);
		    cadena = cadena + year2 + " ) ";
		}
		return cadena;
	}

}
