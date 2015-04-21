package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.sql.DataSource;
import javax.swing.JComboBox;
import mappers.EpisodioMapper;
import mappers.SeriesMapper;
import mappers.UsuariosMapper;
import mappers.VisualizaMapper;
import modelo.Episodio;
import modelo.Serie;
import modelo.Usuario;
import modelo.Visualiza;
import vista.User.VistaInfoEpisodioUser;
import vista.User.VistaModificarDatosUsuario;
import vista.User.VistaSerieUser;
import vista.User.VistaUserSiendoUsuario;

public class ControladorVistaUserSiendoUsuario implements ActionListener, MouseListener, ItemListener{
	
	private VistaUserSiendoUsuario vista;
	private Usuario user;

	private UsuariosMapper userMapper;
	private SeriesMapper seriesMapper;
	private EpisodioMapper episodioMapper;
	
	public ControladorVistaUserSiendoUsuario(VistaUserSiendoUsuario vista,
			DataSource ds,
			UsuariosMapper userMapper,
			Usuario user){
		this.vista = vista;
		this.userMapper = userMapper;
		this.seriesMapper = new SeriesMapper(ds);
		this.episodioMapper = new EpisodioMapper(ds);
		vista.addUserListener(this);
		this.user=user;
		vista.setLabelUsuario(user.getNick());
		if(user.getImagen()!=null)
			vista.setFoto(user.getImagen());
		else
			vista.sinFoto();
		DateToEdad conv = new DateToEdad();
		vista.setLabelEdad(conv.calcularEdad(user.getNacimiento()));
		vista.addMouseListener(this);
		vista.addItemListener(this);
		initMisSeries();

	}

	
	public void initMisSeries() {
		// TODO Auto-generated method stub
		ArrayList<Serie> series = seriesMapper.misSeries(user.getNick());
		vista.muestraMisSeries(series);
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("buscar")){
			if(!vista.getTextoBusqueda().equals("")){
				ArrayList<Serie> series = this.seriesMapper.BuscaSeriesNombre(vista.getTextoBusqueda());
				vista.muestraSeries(series);
			}
		}else if(e.getActionCommand().equals("verInfoSerie")){
			Serie serie = this.getSelectedSerie();
			if(serie!=null){
				VistaSerieUser vistaSerie = new VistaSerieUser();
				//vistaSerie.updateVista(serie.getNombre(), serie.getSinopsis(), serie.getTitular());
				vistaSerie.setVisible(true);
				new ControladorVistaSerieUser(this, vistaSerie, serie, this.user, this.userMapper.getDataSource());
			}
		}else if(e.getActionCommand().equals("combo")){
			JComboBox com = (JComboBox) e.getSource();
			updateEpisodios(com);
			
		}else if(e.getActionCommand().equals("visto")){
			Episodio epi = vista.getSelectedEpisodio();
			if(epi!=null){
				VisualizaMapper visualizaMapper = new VisualizaMapper(seriesMapper.getDataSource());
				Visualiza visualiza = new Visualiza(epi.getIdSerie(), epi.getNumOrden(), user.getNick());
				visualizaMapper.insertObject(visualiza);
				JComboBox com = vista.getComboBox();
				updateEpisodios(com);
			}
		}else if(e.getActionCommand().equals("infoEpisodio")){
			Episodio episodio = vista.getSelectedEpisodio();
			if(episodio!=null){
				VistaInfoEpisodioUser vistaEpisodio = new VistaInfoEpisodioUser(episodio);
				vistaEpisodio.setVisible(true);
				new ControladorVerInfoEpisodioUser(vistaEpisodio, episodio, user, this.seriesMapper.getDataSource());
			}
		}
		
		
	}


	private void updateEpisodios(JComboBox com) {
		Object selected = com.getSelectedItem();
		Serie serieSeleccionada = (Serie) selected;
		if(serieSeleccionada!=null){
		ArrayList<Episodio> episodios = episodioMapper.noVistos(user.getNick(), serieSeleccionada.getIdSerie());
		vista.setEpisodios(episodios);
		}
	}
	
	public Serie getSelectedSerie(){
	if(vista.getSelectedIndex()!=-1){
		
		return (Serie)vista.getSelectedSerie();
	}else
		return null;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

		VistaModificarDatosUsuario vista = new VistaModificarDatosUsuario();
		vista.setVisible(true);
		
		new ControladorVistaModUsuario(user, vista, userMapper, this.vista);
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void itemStateChanged(ItemEvent event) {
		// TODO Auto-generated method stub
	}

	
}
