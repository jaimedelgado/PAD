package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import mappers.ActorMapper;
import modelo.Actor;
import vista.Admin.VistaAnadirActorAdmin;

public class ControladorVistaNuevoActor implements ActionListener{

	private VistaAnadirActorAdmin vista;
	private ActorMapper mapper;
	private Actor actor;
	
	
	
	public ControladorVistaNuevoActor(VistaAnadirActorAdmin vista,
			DataSource ds) {
		super();
		this.vista = vista;
		this.mapper = new ActorMapper(ds);
		this.vista.setActionListener(this);
		this.actor = new Actor(null, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		if(arg0.getActionCommand().equals("foto")){
			JFileChooser jf = new JFileChooser();
			jf.setVisible(true);
			if (jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File f = jf.getSelectedFile();
				vista.setFile(f);
				FileInputStream is = null;
				try {
					is = new FileInputStream(f);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				vista.setFileInputStream(is);
			}
		}else if(arg0.getActionCommand().equals("nuevo")){
			boolean aceptar = true;
			if(!this.vista.getNombreArtistico().equals(""))
				this.actor.setNombreArtistico(this.vista.getNombreArtistico());
			else
				aceptar=false;
			if(!this.vista.getFecha().equals("")){
				String string = this.vista.getFecha();
				try {
					 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				     java.util.Date parsed = format.parse(string);
				     java.sql.Date sql = new java.sql.Date(parsed.getTime());
					
					actor.setNacimiento(sql);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					aceptar = false;
					JOptionPane.showMessageDialog(null,  "Introducir la fecha de nacimiento correctamente: ej (05/09/2000)","", JOptionPane.INFORMATION_MESSAGE);
				}

			}
			if(this.vista.getFileInputStream()!=null){
				byte fileContent[] = new byte[(int)vista.getFile().length()];
				try {
					vista.getFileInputStream().read(fileContent);
					this.actor.setFoto(fileContent);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					aceptar=false;
				}
			}
			if(aceptar){
				mapper.insertObject(actor);
				this.vista.dispose();
			}
		} else if(arg0.getActionCommand().equals("cerrar")){
			
		}
		
	}

}
