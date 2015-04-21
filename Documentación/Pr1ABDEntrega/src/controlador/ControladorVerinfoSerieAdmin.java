package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.WindowConstants;

import org.apache.commons.lang3.StringUtils;

import vista.Admin.NuevoEpisodioAdmin;
import vista.Admin.VerInfoSerieAdmin;
import vista.Admin.VistaModificarCapituloAdmin;
import vista.Admin.VistaPersonajeActor;
import mappers.EpisodioMapper;
import mappers.GeneroMapper;
import mappers.GeneroSerieMapper;
import mappers.SeriesMapper;
import modelo.Episodio;
import modelo.EpisodioClaves;
import modelo.Genero;
import modelo.GeneroSerie;
import modelo.Serie;

public class ControladorVerinfoSerieAdmin implements ActionListener{

	private Serie serie;
	private SeriesMapper mapper;
	private VerInfoSerieAdmin vista;
	private EpisodioMapper episodioMapper;
	private GeneroSerieMapper generoSerieMapper;
	
	
	public ControladorVerinfoSerieAdmin(Serie serie, SeriesMapper mapper,
			VerInfoSerieAdmin vista) {
		super();
		this.serie = serie;
		this.mapper = mapper;
		this.generoSerieMapper = new GeneroSerieMapper(mapper.getDataSource());
		this.vista = vista;
		setGeneros();
		this.vista.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		vista.addActionListener(this);
		this.episodioMapper = new EpisodioMapper(mapper.getDataSource());
		episodiosSerie();
	}
	
	
	private void setGeneros(){
		ArrayList<GeneroSerie> lista = this.generoSerieMapper.findGeneros(this.serie.getIdSerie());
		String[] generos = new String[lista.size()];
		int i = 0;
		for(GeneroSerie o : lista){
			generos[i] = o.getGenero();
			i++;
		}
		
		String cadena = StringUtils.join(generos, ", ");
		
		this.vista.updateGenero(cadena);
			
		
	}
	

	public void episodiosSerie(){
		ArrayList<Episodio> lista = null;
		lista = (ArrayList<Episodio>) this.episodioMapper.getEpisodios(this.serie.getIdSerie());
		this.vista.setEpisodios(lista);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("personaje")){
			Episodio seleccionado = this.vista.getSelectedEpisodio();
			if(seleccionado!=null){
				VistaPersonajeActor vistaPjActor = new VistaPersonajeActor();
				vistaPjActor.setVisible(true);
				ControladorVistaNuevoAparece controllerNuevo = 
						new ControladorVistaNuevoAparece(this.mapper.getDataSource(),
														  seleccionado,
														  vistaPjActor);
				
			}
			
		
		}else if(arg0.getActionCommand().equals("guardar")){

			this.serie.setTitular(this.vista.getTitular());
			this.serie.setSinopsis(this.vista.getSinopsis());
			mapper.update(this.serie);
			List<String> parserGenero = Arrays.asList(this.vista.getGenero().split(","));
			for(String s: parserGenero){
				GeneroMapper generoMapper= new GeneroMapper(this.mapper.getDataSource());
				if(generoMapper.findById(s)==null)
					generoMapper.insertObject(new Genero(s));
				
				GeneroSerie insertar = new GeneroSerie(this.serie.getIdSerie(),s);
				generoSerieMapper.insertObject(insertar);
			}
		}else if(arg0.getActionCommand().equals("nuevoEpisodio")){
			NuevoEpisodioAdmin nuevo = new NuevoEpisodioAdmin();
			nuevo.setVisible(true);
			new ControladorNuevoEpisodio(nuevo, episodioMapper, this.serie.getIdSerie(), this);
			
		}else if(arg0.getActionCommand().equals("borrarEpisodio")){
			Episodio ep =this.vista.getSelectedEpisodio();
			if(ep != null){
				EpisodioClaves claves = new EpisodioClaves(ep.getIdSerie(), ep.getNumOrden());
				episodioMapper.deleteById(claves);
				episodiosSerie();
			}
		}else if(arg0.getActionCommand().equals("modificarEpisodio")){
			Episodio ep =this.vista.getSelectedEpisodio();
			if(ep != null){
				VistaModificarCapituloAdmin view = new VistaModificarCapituloAdmin(ep);
				view.setVisible(true);
				new ControladorVistaModEpisodio(view, ep, this.episodioMapper, this);
			}
			
		}
		
	}
	

}
