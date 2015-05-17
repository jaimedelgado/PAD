package com.example.boxrun;

public class Jugador {
	private String nombre;
	private int puntuacion;
	public Jugador(String nombre, int p){
		this.nombre=nombre;
		this.puntuacion=p;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
}
