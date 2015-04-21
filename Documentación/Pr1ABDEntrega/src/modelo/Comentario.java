package modelo;

import java.sql.Timestamp;

public abstract class Comentario {

	private String nick;
	private String texto;
	private Timestamp fecha;
	
	public Comentario(String nick, String texto, Timestamp fecha) {
		super();
		this.nick = nick;
		this.texto = texto;
		this.fecha = fecha;
	}
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

}
