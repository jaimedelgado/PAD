package main;

import vista.VistaLog;
import controlador.ControladorLogin;

public class MainUser {

	public static void main(String[] args) {
		
		VistaLog vista = new VistaLog();
		new ControladorLogin(vista);
		
	}

}
