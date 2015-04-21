package modelo;

public class VotaEpisodio {

	private int idSerie;
	private int numOrden;
	private String nick;
	private int nota;
	
	public VotaEpisodio(int idSerie, int numOrden, String nick, int nota) {
		super();
		this.idSerie = idSerie;
		this.numOrden = numOrden;
		this.nick = nick;
		this.nota = nota;
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

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}
	
}
