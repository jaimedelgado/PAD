package controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import mappers.UsuariosMapper;
import modelo.Usuario;
import vista.User.VistaModificarDatosUsuario;
import vista.User.VistaUserSiendoUsuario;

public class ControladorVistaModUsuario implements ActionListener{

	private Usuario user;
	private VistaModificarDatosUsuario vista;
	private UsuariosMapper mapper;
	private VistaUserSiendoUsuario vistaUser;
	
	public ControladorVistaModUsuario(Usuario user, 
			VistaModificarDatosUsuario vista, 
			UsuariosMapper mapper,
			VistaUserSiendoUsuario vistaUser){
		this.user = user;
		this.vista = vista;
		this.mapper = mapper;
		this.vista.addActionListener(this);
		this.vista.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.vistaUser = vistaUser;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("aceptar")){
			boolean aceptar = true;
			if(!this.vista.getPasswordField().equals("")){
				user.setPassword(this.vista.getPasswordField());
			}
			if(!this.vista.getFecha().equals("")){
				String string = this.vista.getFecha();
				try {
					 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				     java.util.Date parsed = format.parse(string);
				     java.sql.Date sql = new java.sql.Date(parsed.getTime());
					
					user.setNacimiento(sql);
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
					this.user.setImagen(fileContent);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					aceptar=false;
				}
			}
			if(aceptar){
				mapper.update(this.user);
				this.vistaUser.updateVista(this.user);
				this.vista.dispose();
			}
		}else if(e.getActionCommand().equalsIgnoreCase("imagen")){

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
		
		}
		
	}

}
