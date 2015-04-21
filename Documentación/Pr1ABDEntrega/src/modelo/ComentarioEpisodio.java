package modelo;

import java.sql.Timestamp;

public class ComentarioEpisodio extends Comentario{

	private int idSerie;
	private int numOrden;
	public ComentarioEpisodio(String nick, String texto, Timestamp fecha, int idSerie, int numOrden) {
		super(nick, texto, fecha);
		// TODO Auto-generated constructor stub
		this.idSerie=idSerie;
		this.numOrden=numOrden;
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
