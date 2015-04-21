package modelo;

public class Visualiza {

	private int idSerie;
	private int numOrden;
	private String Nick;
	
	public Visualiza(int idSerie, int numOrden, String nick) {
		super();
		this.idSerie = idSerie;
		this.numOrden = numOrden;
		Nick = nick;
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
	public String getNick() {
		return Nick;
	}
	public void setNick(String nick) {
		Nick = nick;
	}

	
}
