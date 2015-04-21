package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.StringUtils;

import mappers.ComentarioSerieMapper;
import mappers.EpisodioMapper;
import mappers.GeneroSerieMapper;
import mappers.SeguidorMapper;
import mappers.VotaSerieMapper;
import modelo.ComentarioSerie;
import modelo.Episodio;
import modelo.GeneroSerie;
import modelo.SeguidorSerie;
import modelo.Serie;
import modelo.Usuario;
import modelo.VotaSerie;
import modelo.VotaSerieClaves;
import vista.User.VistaInfoEpisodioUser;
import vista.User.VistaSerieUser;

public class ControladorVistaSerieUser implements ActionListener {

	private VistaSerieUser vista;
	private Serie serie;
	private Usuario user;
	private SeguidorMapper seguidorMapper;
	private SeguidorSerie seguidor;
	private ControladorVistaUserSiendoUsuario controladorOld;
	private EpisodioMapper episodioMapper;
	private VotaSerieMapper votaSerieMapper;
	private GeneroSerieMapper generoSerieMapper;
	
	
	public ControladorVistaSerieUser(ControladorVistaUserSiendoUsuario controladorOld, VistaSerieUser vista, Serie serie, Usuario user, DataSource ds){
		this.vista = vista;
		this.serie = serie;
		this.user = user;
		this.vista.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.vista.updateVista(serie.getNombre(), serie.getSinopsis(), serie.getTitular());
		vista.addListener(this);
		this.seguidorMapper = new SeguidorMapper(ds);
		this.generoSerieMapper = new GeneroSerieMapper(ds);
		updateGeneros();
		this.initSeguidor();
		this.episodioMapper = new EpisodioMapper(ds);
		this.votaSerieMapper = new VotaSerieMapper(ds);
		this.controladorOld = controladorOld;
		episodiosSerie();
		notaMedia();
		publicarComments();
	}
	
	private void updateGeneros(){
		ArrayList<GeneroSerie> lista = this.generoSerieMapper.findGeneros(this.serie.getIdSerie());
		String[] generos = new String[lista.size()];
		int i = 0;
		for(GeneroSerie o : lista){
			generos[i] = o.getGenero();
			i++;
		}
		
		String cadena = StringUtils.join(generos, ", ");
		
		this.vista.updateGeneros(cadena);
			
		
	}

	public void publicarComments(){
		ComentarioSerieMapper map = new ComentarioSerieMapper(this.votaSerieMapper.getDataSource());
		ArrayList<ComentarioSerie> comments = map.getComentarios(this.serie.getIdSerie());
		this.vista.publicaComentarios(comments);
		
	}
	public void initSeguidor(){
		SeguidorSerie seguidor = new SeguidorSerie(serie.getIdSerie(), user.getNick());
		if(this.seguidorMapper.findById(seguidor)!=null){
			cambiaSeguidorBoton();
			this.seguidor = seguidor;
		}
	}
	
	public void episodiosSerie(){
		ArrayList<Episodio> lista = null;
		lista = (ArrayList<Episodio>) this.episodioMapper.getEpisodios(this.serie.getIdSerie());
		this.vista.setEpisodios(lista);
	}
	
	public void notaMedia(){
		Double nota = votaSerieMapper.notaMedia(this.serie.getIdSerie());
		if(nota!=null)
		this.vista.setNotaMedia(nota);
	}
	
	public void cambiaSeguidorBoton(){
    	if(vista.getSeguidor().equals("Seguir"))
    		vista.setSeguidorText("No Seguir");
    	else
    		vista.setSeguidorText("Seguir");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		
		if(ev.getActionCommand().equals("cerrar")){
			vista.dispose();
			this.controladorOld.initMisSeries();
		
		}else if(ev.getActionCommand().equals("seguidor")){
			if(vista.getSeguidor().equals("Seguir")){
					SeguidorSerie seguidor = new SeguidorSerie(serie.getIdSerie(), user.getNick());
					seguidorMapper.insertObject(seguidor);
					cambiaSeguidorBoton();
					this.seguidor = seguidor;
					
			}
			else{
				seguidorMapper.deleteById(this.seguidor);
				cambiaSeguidorBoton();
				this.seguidor = null;
			}
		}else if(ev.getActionCommand().equals("infoEpisodio")){
			if(this.vista.getSelectedEpisodio()!=null){
				Episodio episodio = this.vista.getSelectedEpisodio();
				VistaInfoEpisodioUser vistaInfoEpisodio = new VistaInfoEpisodioUser(episodio);
				vistaInfoEpisodio.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				vistaInfoEpisodio.setVisible(true);
				new ControladorVerInfoEpisodioUser(vistaInfoEpisodio, episodio, user, this.episodioMapper.getDataSource());
				
			}
		}else if(ev.getActionCommand().equals("votar")){
			int calif = this.vista.getCalificacion();
			VotaSerie vota = new VotaSerie(this.user.getNick(), calif, this.serie.getIdSerie());
			VotaSerieMapper votaMapper = new VotaSerieMapper(this.seguidorMapper.getDataSource());
			VotaSerieClaves votaClaves = new VotaSerieClaves(this.user.getNick(), this.serie.getIdSerie());
			if(votaMapper.findById(votaClaves)==null){
				votaMapper.insertObject(vota);
				JOptionPane.showMessageDialog(null, "Su voto se ha registrado");
				notaMedia();
			}else{
				JOptionPane.showMessageDialog(null, "No puedes votar esta serie");
			}
			
		}else if(ev.getActionCommand().equals("comentar")){
			String comentario = this.vista.getComentario();
			ComentarioSerie comment = new ComentarioSerie(this.user.getNick(), comentario, new Timestamp(System.currentTimeMillis()), this.serie.getIdSerie());
			ComentarioSerieMapper mapper = new ComentarioSerieMapper(this.episodioMapper.getDataSource());
			mapper.insertObject(comment);
			JOptionPane.showMessageDialog(null, "Comentario añadido");
			publicarComments();
		}
	}

}
