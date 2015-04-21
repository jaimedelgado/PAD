package modelo;

public class EpisodioClaves {
	
	private int idSerie;
	private int numOrden;
	
	public EpisodioClaves(int idSerie, int numOrden) {
		super();
		this.idSerie = idSerie;
		this.numOrden = numOrden;
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
	
	
}
