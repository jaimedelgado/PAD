package modelo;

public class GeneroSerie {

	private String genero;
	private int idSerie;
	public GeneroSerie(int idSerie, String string) {
		super();
		this.genero = string;
		this.idSerie = idSerie;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public int getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}
	
	
}
