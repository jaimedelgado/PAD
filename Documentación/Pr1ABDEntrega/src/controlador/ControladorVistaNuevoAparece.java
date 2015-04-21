package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import vista.Admin.VistaPersonajeActor;
import mappers.ActorMapper;
import mappers.ApareceMapper;
import mappers.PersonajeMapper;
import modelo.Actor;
import modelo.Aparece;
import modelo.Episodio;
import modelo.Personaje;

public class ControladorVistaNuevoAparece implements ActionListener{

	private ApareceMapper mapper;
	private Episodio episodio;
	private VistaPersonajeActor vista;
	private PersonajeMapper personajeMapper;
	private ActorMapper actorMapper;
	
	public ControladorVistaNuevoAparece(DataSource ds, Episodio episodio, VistaPersonajeActor vista){
		this.mapper = new ApareceMapper(ds);
		this.personajeMapper = new PersonajeMapper(ds);
		this.actorMapper = new ActorMapper(ds);
		this.episodio = episodio;
		this.vista = vista;
		this.vista.setActionListener(this);
		inicializaListas();
	}
	
	private void inicializaListas() {
		// TODO Auto-generated method stub
		List<Personaje> personajes = personajeMapper.findAll();
		List<Actor> actores = actorMapper.findAll();
		this.vista.muestraActores((ArrayList<Actor>) actores);
		this.vista.muestraPersonajes((ArrayList<Personaje>) personajes);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("aceptar")){
			Personaje pj = (Personaje) this.vista.getSelectedPersonaje();
			Actor actor = (Actor) this.vista.getSelectedActor();
			if(pj != null && actor != null){
				if(!this.mapper.actorEnCapitulo(this.episodio.getIdSerie(), 
												this.episodio.getNumOrden(), 
												actor.getNombreArtistico())
												&&
					!this.mapper.personajeEnCapitulo(this.episodio.getIdSerie(),
												this.episodio.getNumOrden(),
												pj.getIdPersonaje())){
				this.mapper.insertObject(new Aparece(this.episodio.getIdSerie(), 
													this.episodio.getNumOrden(),
													pj.getIdPersonaje(), 
													actor.getNombreArtistico()));
				JOptionPane.showMessageDialog(null,  "ACTOR-PERSONAJE AÑADIDO AL EPISODIO","", JOptionPane.INFORMATION_MESSAGE);
				
				}
				else
					JOptionPane.showMessageDialog(null,  "EL ACTOR/PERSONAJE SELECCIONADO YA APARECE EN EL EPISODIO","", JOptionPane.INFORMATION_MESSAGE);
					
			}
		}else if(arg0.getActionCommand().equals("cerrar")){
			this.vista.dispose();
		}
		
	}

}
