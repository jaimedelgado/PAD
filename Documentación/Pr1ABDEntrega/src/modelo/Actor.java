package modelo;

import java.sql.Date;

public class Actor {


	private String nombreArtistico;
	private Date fecha;
	private byte[] foto;
	
		public Actor(String nombreArtistico, Date fecha) {
		super();
		this.nombreArtistico = nombreArtistico;

	}
		
	public String getNombreArtistico() {
		return nombreArtistico;
	}
	public void setNombreArtistico(String nombreArtistico) {
		this.nombreArtistico = nombreArtistico;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public void setNacimiento(Date sql) {
		// TODO Auto-generated method stub
		this.fecha=sql;
		
	}
	
	public String toString(){
		return "Nombre artistico: " + this.getNombreArtistico();
	}
}
