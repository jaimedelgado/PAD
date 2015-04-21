package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import mappers.EpisodioMapper;
import modelo.Episodio;
import vista.Admin.NuevoEpisodioAdmin;

public class ControladorNuevoEpisodio implements ActionListener {

	private NuevoEpisodioAdmin view;
	private EpisodioMapper mapper;
	private int idSerie;
	private ControladorVerinfoSerieAdmin cont;

	public ControladorNuevoEpisodio(NuevoEpisodioAdmin vista,
			 EpisodioMapper mapper, int idSerie, ControladorVerinfoSerieAdmin cont) {

		this.view = vista;
		this.mapper = mapper;
		this.idSerie = idSerie;
		this.view.setActionListener(this);
		this.cont = cont;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("aceptar")){
			java.sql.Date sql = null;
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		    java.util.Date parsed;
			try {
				parsed = format.parse(this.view.getEstreno());
				sql = new java.sql.Date(parsed.getTime());
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,  "Introducir la fecha correctamente: ej (05/09/2000)","", JOptionPane.INFORMATION_MESSAGE);

			}
		    
			Episodio episodio = new Episodio(idSerie, this.view.getNumOrden(),
					this.view.getNumTemp(), this.view.getSinopsis(), 
					this.view.getTitulo(), sql);
			boolean aceptar = false;
			if(mapper.insertObject(episodio)==1){
				JOptionPane.showMessageDialog(null,  "Episodio introducido","", JOptionPane.INFORMATION_MESSAGE);
				aceptar=true;
				this.cont.episodiosSerie();
			}else{
					JOptionPane.showMessageDialog(null,  "Error","", JOptionPane.INFORMATION_MESSAGE);
			}
			if(aceptar)
				this.view.dispose();
		}else if(arg0.getActionCommand().equals("cancelar")){
			this.view.dispose();
		}
	}
	
}
