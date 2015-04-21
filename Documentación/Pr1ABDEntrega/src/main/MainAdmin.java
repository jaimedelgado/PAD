package main;

import controlador.ControladorAdmin;
import vista.Admin.VistaAdmin;

public class MainAdmin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VistaAdmin vista = new VistaAdmin();
		new ControladorAdmin(vista);
		
	}

}
