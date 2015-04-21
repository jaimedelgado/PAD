package modelo;

public class Personaje {

	
	private String nombre;
	private Integer idPersonaje;
	private String descripcion;
	
	public Personaje(String nombre, Integer idPersonaje, String descripcion) {
		super();
		this.nombre = nombre;
		this.idPersonaje = idPersonaje;
		this.descripcion = descripcion;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getIdPersonaje() {
		return idPersonaje;
	}
	public void setIdPersonaje(Integer idPersonaje) {
		this.idPersonaje = idPersonaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String toString(){
		return "ID: " + this.getIdPersonaje() + " Nombre: " + this.getNombre(); 
	}

}
