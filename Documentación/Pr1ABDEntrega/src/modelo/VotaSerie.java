package modelo;

public class VotaSerie {
	
	private String nick;
	private int nota;
	private int idSerie;
	
	public VotaSerie(String nick, int nota, int idSerie) {
		super();
		this.nick = nick;
		this.nota = nota;
		this.idSerie = idSerie;
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
	public int getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}
	
}
