package modelo;

public class Aparece {
	private int idSerie;
	private int numOrden;
	private int idPersonaje;
	private String nombreArtistico;
	
	public Aparece(int idSerie, int numOrden, int idPersonaje,
			String nombreArtistico) {
		super();
		this.idSerie = idSerie;
		this.numOrden = numOrden;
		this.idPersonaje = idPersonaje;
		this.nombreArtistico = nombreArtistico;
	}
	public int getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}
	public int getNumOrden() {
		return numOrden;
	}
	public void setNumOrden(int numOrden) {
		this.numOrden = numOrden;
	}
	public int getIdPersonaje() {
		return idPersonaje;
	}
	public void setIdPersonaje(int idPersonaje) {
		this.idPersonaje = idPersonaje;
	}
	public String getNombreArtistico() {
		return nombreArtistico;
	}
	public void setNombreArtistico(String nombreArtistico) {
		this.nombreArtistico = nombreArtistico;
	}
	
	
}
