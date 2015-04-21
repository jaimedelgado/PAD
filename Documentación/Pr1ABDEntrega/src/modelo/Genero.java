package modelo;

public class Genero {
	private String genero;

	public Genero(String genero) {
		super();
		this.genero = genero;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public String toString(){
		return this.genero;
	}
}
