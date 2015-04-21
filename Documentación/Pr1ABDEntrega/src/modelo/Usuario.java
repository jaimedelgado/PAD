package modelo;

import java.sql.Date;

public class Usuario {
	
	private String nick;
	private String password;
	private Date nacimiento;
	private byte[] imagen;
	
	public Usuario(String nick, String password, Date nacimiento, byte[] imagen) {
		this.nick = nick;
		this.password = password;
		this.nacimiento = nacimiento;
		this.imagen = imagen;
	}
	
	public Usuario(String nick2, String password2, Date nacimiento2) {
		// TODO Auto-generated constructor stub
		this.nick = nick2;
		this.password = password2;
		this.nacimiento = nacimiento2;
	}

	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}
	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}


	
	

}
