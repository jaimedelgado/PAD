package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.swing.WindowConstants;

import mappers.SeriesMapper;
import modelo.BBDD;
import modelo.Serie;
import vista.Admin.VerInfoSerieAdmin;
import vista.Admin.VistaAdmin;
import vista.Admin.VistaAnadirActorAdmin;
import vista.Admin.VistaNuevaSerieAdmin;
import vista.Admin.VistaNuevoPersonajeAdmin;

public class ControladorAdmin implements ActionListener{

	private VistaAdmin vista;
	private DataSource ds;
	private Serie serie;
	private SeriesMapper seriesMapper;
	
	public ControladorAdmin(VistaAdmin vista){
		this.vista = vista;
		this.vista.setVisible(true);
		this.ds = new BBDD("AdminP1").getDataSource();
		this.seriesMapper = new SeriesMapper(this.ds);
		this.vista.addActionListener(this);
	}
	
	public Serie getSelectedSerie(){
	if(vista.getSelectedIndex()!=-1){
		
		return (Serie)vista.getSelectedSerie();
	}else
		return null;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("buscar")){
			if(!vista.getBuscarText().equals("")){
				ArrayList<Serie> series = this.seriesMapper.BuscaSeriesNombre(vista.getBuscarText());
				vista.muestraSeries(series);
			}
		}else if(arg0.getActionCommand().equals("infoSerie")){
			Serie serie = this.getSelectedSerie();
			if(serie!=null){
				VerInfoSerieAdmin vistaInfoSerie = new VerInfoSerieAdmin();
				vistaInfoSerie.updateVista(serie);
				vistaInfoSerie.setVisible(true);
				new ControladorVerinfoSerieAdmin(serie, this.seriesMapper, vistaInfoSerie);
			}
		}else if(arg0.getActionCommand().equals("nuevaSerie")){
			VistaNuevaSerieAdmin vistaNuevaSerie = new VistaNuevaSerieAdmin();
			vistaNuevaSerie.setVisible(true);
			vistaNuevaSerie.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			new ControladorNuevaSerie(seriesMapper, serie, vistaNuevaSerie);
			
		}else if(arg0.getActionCommand().equals("newPersonaje")){
			VistaNuevoPersonajeAdmin vistaNuevoPersonaje = new VistaNuevoPersonajeAdmin();
			vistaNuevoPersonaje.setVisible(true);
			vistaNuevoPersonaje.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			new ControladorNuevoPersonaje(vistaNuevoPersonaje, seriesMapper.getDataSource());
			
		}else if(arg0.getActionCommand().equals("newActor")){
			VistaAnadirActorAdmin vistaAnadirActor = new VistaAnadirActorAdmin();
			vistaAnadirActor.setVisible(true);
			vistaAnadirActor.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			new ControladorVistaNuevoActor(vistaAnadirActor, this.seriesMapper.getDataSource());
			
		}
	}

}
