package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import mappers.PersonajeMapper;
import modelo.Personaje;
import vista.Admin.VistaNuevoPersonajeAdmin;

public class ControladorNuevoPersonaje implements ActionListener{

	private VistaNuevoPersonajeAdmin vista;
	private Personaje personaje;
	private PersonajeMapper mapper;
	
	public ControladorNuevoPersonaje(VistaNuevoPersonajeAdmin vista,
				DataSource ds) {
		super();
		this.vista = vista;
		this.mapper = new PersonajeMapper(ds);
		this.vista.addActionListener(this);
	}

	
	
	public VistaNuevoPersonajeAdmin getVista() {
		return vista;
	}

	public void setVista(VistaNuevoPersonajeAdmin vista) {
		this.vista = vista;
	}

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

	public PersonajeMapper getMapper() {
		return mapper;
	}

	public void setMapper(PersonajeMapper mapper) {
		this.mapper = mapper;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("nuevo")){
			Personaje person = new Personaje(this.vista.getNombre(), null, this.vista.getDescripcion());
			this.mapper.insertObject(person);
			JOptionPane.showMessageDialog(null,  "Personaje creado","", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	
}
