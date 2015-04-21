package modelo;

public class ParNombreActor {
	
	private String nombreArtistico;
	private String nombreActor;
	public ParNombreActor(String nombreArtistico, String nombreActor) {
		super();
		this.nombreArtistico = nombreArtistico;
		this.nombreActor = nombreActor;
	}
	public String getNombreArtistico() {
		return nombreArtistico;
	}
	public void setNombreArtistico(String nombreArtistico) {
		this.nombreArtistico = nombreArtistico;
	}
	public String getNombreActor() {
		return nombreActor;
	}
	public void setNombreActor(String nombreActor) {
		this.nombreActor = nombreActor;
	}
	
	

}
