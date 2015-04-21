package modelo;

public class SeguidorSerie {
	private String nickUser;
	private int idSerie;

	public SeguidorSerie(int id, String nick){
		this.nickUser = nick;
		this.idSerie = id;
	}

	public String getNickUser() {
		return this.nickUser;
	}

	public void setNickUser(String nickUser) {
		this.nickUser = nickUser;
	}

	public int getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}
	
	
	
}
