package modelo;

public class VotaEpisodioClaves {
	private int idSerie;
	private int numOrden;
	private String nick;
	
	public VotaEpisodioClaves(int idSerie, int numOrden, String nick) {
		super();
		this.idSerie = idSerie;
		this.numOrden = numOrden;
		this.nick = nick;
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
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	
}
