package modelo;

import java.sql.Timestamp;

public class ComentarioSerie extends Comentario{
	
	private int idSerie;
	public ComentarioSerie(String nick, String texto, Timestamp fecha, int idSerie) {
		super(nick, texto, fecha);
		// TODO Auto-generated constructor stub
		this.idSerie = idSerie;
	}
	
	public void setIdSerie(int idSerie){
		this.idSerie = idSerie;
	}

	public int getIdSerie(){
		return this.idSerie;
	}

}
