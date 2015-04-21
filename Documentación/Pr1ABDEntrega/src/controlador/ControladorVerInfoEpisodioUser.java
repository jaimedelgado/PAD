package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.JOptionPane;
import mappers.ApareceMapper;
import mappers.ComentarioEpisodioMapper;
import mappers.PersonajeMapper;
import mappers.VotaEpisodioMapper;
import modelo.Aparece;
import modelo.ComentarioEpisodio;
import modelo.Episodio;
import modelo.ParNombreActor;
import modelo.Personaje;
import modelo.Usuario;
import modelo.VotaEpisodio;
import modelo.VotaEpisodioClaves;
import vista.User.VistaInfoEpisodioUser;

public class ControladorVerInfoEpisodioUser implements ActionListener{

	private VistaInfoEpisodioUser vista;
	private Episodio episodio;
	private Usuario user;
	private VotaEpisodioMapper votaMapper;
	
	public ControladorVerInfoEpisodioUser(VistaInfoEpisodioUser vista,
			Episodio episodio, Usuario user, DataSource dataSource) {
		super();
		this.vista = vista;
		this.vista.addListener(this);
		this.user = user;
		this.episodio = episodio;
		this.votaMapper = new VotaEpisodioMapper(dataSource);
		notaMedia();
		setPersonajes();
		setComentarios();
	}

	public void setComentarios(){
		ComentarioEpisodioMapper map = new ComentarioEpisodioMapper(this.votaMapper.getDataSource());
		ArrayList<ComentarioEpisodio> comments = map.getComentarios(this.episodio.getIdSerie(), 
																	this.episodio.getNumOrden());
		this.vista.publicaComentarios(comments);
		
	}
	
	public void notaMedia(){
		Double nota = votaMapper.notaMedia(this.episodio.getIdSerie(), this.episodio.getNumOrden());
		if(nota!=null)
		this.vista.setNotaMedia(nota);
	}
	
	public void setPersonajes(){
		ApareceMapper apareceMapper = new ApareceMapper(this.votaMapper.getDataSource());
		List<Aparece> aparecen = apareceMapper.findPersonajes(this.episodio.getIdSerie(), this.episodio.getNumOrden());
		PersonajeMapper personajeMapper = new PersonajeMapper(apareceMapper.getDataSource());
		ArrayList<ParNombreActor> par = new ArrayList<ParNombreActor>();
		Iterator<Aparece> it = aparecen.iterator();
		
		while(it.hasNext()){
			Aparece aparece = it.next();
			Personaje person = personajeMapper.findById(aparece.getIdPersonaje());
			ParNombreActor parLocal = new ParNombreActor(aparece.getNombreArtistico(), person.getNombre());
			par.add(parLocal);			
		}
		
		this.vista.setPersonajes(par);
				
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("cerrar")){
			vista.dispose();
		}else if(arg0.getActionCommand().equals("votar")){
			int calif = this.vista.getCalificacion();
			VotaEpisodio vota = new VotaEpisodio(this.episodio.getIdSerie(),this.episodio.getNumOrden(), this.user.getNick(), calif);
			VotaEpisodioClaves votaClaves = new VotaEpisodioClaves(this.episodio.getIdSerie(), this.episodio.getNumOrden(), this.user.getNick());
			if(votaMapper.findById(votaClaves)==null){
				votaMapper.insertObject(vota);
				JOptionPane.showMessageDialog(null, "Su voto se ha registrado");
				notaMedia();
			}else{
				JOptionPane.showMessageDialog(null, "No puedes votar esta serie");
			}
		}else if(arg0.getActionCommand().equals("comentar")){
			String comentario = this.vista.getComentario();
			ComentarioEpisodio comment = new ComentarioEpisodio(this.user.getNick(), 
																comentario, 
																new Timestamp(System.currentTimeMillis()), 
																this.episodio.getIdSerie(), 
																this.episodio.getNumOrden());
			
			ComentarioEpisodioMapper mapper = new ComentarioEpisodioMapper(this.votaMapper.getDataSource());
			mapper.insertObject(comment);
			JOptionPane.showMessageDialog(null, "Comentario añadido");
			setComentarios();
		}
		
	}
	
}
