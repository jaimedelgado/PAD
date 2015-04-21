package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import vista.Admin.VistaNuevaSerieAdmin;
import mappers.GeneroMapper;
import mappers.GeneroSerieMapper;
import mappers.SeriesMapper;
import modelo.Genero;
import modelo.GeneroSerie;
import modelo.Serie;

public class ControladorNuevaSerie implements ActionListener {
	private SeriesMapper seriesMapper;
	private GeneroMapper generoMapper;
	private GeneroSerieMapper generoSerieMapper;
	private VistaNuevaSerieAdmin vista;
	
	public ControladorNuevaSerie(SeriesMapper seriesMapper, Serie serie,
			VistaNuevaSerieAdmin vista) {
		super();
		this.seriesMapper = seriesMapper;
		this.generoMapper = new GeneroMapper(this.seriesMapper.getDataSource());
		this.generoSerieMapper = new GeneroSerieMapper(this.seriesMapper.getDataSource());
		this.vista = vista;
		this.vista.setActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("crear")){
			boolean aceptar = true;
			System.out.println("jajajajaja");
			String titulo = vista.getTextoTitulo();
			String titular = vista.getTextoTitular();
			String sinopsis = vista.getTextoSinopsis();
			String genero = vista.getTextoGenero();
			String estreno = vista.getTextoEstreno();
			String finalizacion = vista.getTextoFinalizacion();
			java.sql.Date sqle = null;
			java.sql.Date sqlf = null;
			try {
				 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			     java.util.Date parsed = format.parse(estreno);
			     sqle = new java.sql.Date(parsed.getTime());

			     if(!finalizacion.equals("")){
				 SimpleDateFormat formatf = new SimpleDateFormat("dd/MM/yyyy");
			     java.util.Date parsedf = formatf.parse(finalizacion);
			     sqlf = new java.sql.Date(parsedf.getTime());
			     }
			     
			     
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				aceptar = false;
				JOptionPane.showMessageDialog(null,  "Introducir las fechas correctamente: ej (05/09/2000)","", JOptionPane.INFORMATION_MESSAGE);
			}
			

			
			if(aceptar){
				int id = seriesMapper.maxId();
				id +=1;
				Serie nuevaSerie = new Serie(id, titulo, titular, sinopsis, sqle, sqlf);
				seriesMapper.insertObject(nuevaSerie);
				List<String> parserGenero = Arrays.asList(genero.split(","));
				for(String s: parserGenero){
					if(generoMapper.findById(s)==null)
						generoMapper.insertObject(new Genero(s));
					
					GeneroSerie insertar = new GeneroSerie(id,s);
					generoSerieMapper.insertObject(insertar);
				}
				vista.dispose();
			}
		}else if(arg0.getActionCommand().equals("cancelar")){
			vista.dispose();
		}
		
	}
	
	

}
