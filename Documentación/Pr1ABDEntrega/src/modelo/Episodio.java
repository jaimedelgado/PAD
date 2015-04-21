package modelo;

import java.sql.Date;

public class Episodio {

	private int idSerie;
	private int numOrden;
	private int numTemporada;
	private String sinopsis;
	private String titulo;
	private Date estreno;
	
	
	public Episodio(int idSerie, int numOrden, int numTemporada,
			String sinopsis, String titulo, Date estreno) {
		super();
		this.idSerie = idSerie;
		this.numOrden = numOrden;
		this.numTemporada = numTemporada;
		this.sinopsis = sinopsis;
		this.titulo = titulo;
		this.estreno = estreno;
	}
	
	public int getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}
	public Integer getNumOrden() {
		return numOrden;
	}
	public void setNumOrden(int numOrden) {
		this.numOrden = numOrden;
	}
	public Integer getNumTemporada() {
		return numTemporada;
	}
	public void setNumTemporada(int numTemporada) {
		this.numTemporada = numTemporada;
	}
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Date getEstreno() {
		return estreno;
	}
	public void setEstreno(Date estreno) {
		this.estreno = estreno;
	}
	public String toString(){
		return this.titulo;
	}
	public String toString2(){
		return this.titulo + " ( " + this.estreno.toString() + " ) ";
	}
}
