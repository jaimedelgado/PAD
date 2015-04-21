package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.WindowConstants;

import mappers.EpisodioMapper;
import modelo.Episodio;
import vista.Admin.VistaModificarCapituloAdmin;

public class ControladorVistaModEpisodio implements ActionListener {

	private VistaModificarCapituloAdmin vista;
	private Episodio episodio;
	private EpisodioMapper mapper;
	private ControladorVerinfoSerieAdmin oldCont;

	
	public ControladorVistaModEpisodio(VistaModificarCapituloAdmin vista,
			Episodio episodio, EpisodioMapper mapper, ControladorVerinfoSerieAdmin controladorVerinfoSerieAdmin) {
		super();
		this.vista = vista;
		this.vista.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.episodio = episodio;
		this.mapper = mapper;
		this.vista.addActionListener(this);
		this.oldCont = controladorVerinfoSerieAdmin;
	}



	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("guardar")){
			this.episodio.setNumTemporada(this.vista.getnumTemp());
			this.episodio.setTitulo(this.vista.getTitulo());
			this.episodio.setSinopsis(this.vista.getSinopsis());
			mapper.update(episodio);
			this.oldCont.episodiosSerie();
			this.vista.dispose();
			
			//this.episodio.setTitulo(titulo);
		}else if(e.getActionCommand().equals("cerrar")){
			this.vista.dispose();
		}
		
	}

}
