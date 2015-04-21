package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sql.DataSource;

import vista.VistaLog;
import vista.User.VistaNuevoUsuario;
import vista.User.VistaUserSiendoUsuario;
import mappers.UsuariosMapper;
import modelo.BBDD;
import modelo.Usuario;

public class ControladorLogin implements ActionListener {

	private VistaLog vista;

	private DataSource ds;
	private UsuariosMapper userMapper;
	
	public ControladorLogin(VistaLog vista){
		this.vista = vista;
		this.ds = new BBDD("UsuarioP1").getDataSource();
		this.userMapper = new UsuariosMapper(this.ds);
		vista.addLoginListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equalsIgnoreCase("login")){
			String nick;
			nick = vista.getUsuarioText();
			if(!nick.equals("")){
				Usuario user = userMapper.findById(nick);
				if(user!=null && user.getPassword().equals(vista.getPasswordText())){
					VistaUserSiendoUsuario vistaUser = new VistaUserSiendoUsuario();
					vistaUser.updateVista(user);
					vistaUser.setVisible(true);
					new ControladorVistaUserSiendoUsuario(vistaUser, this.ds, userMapper, user);
				}
			}
		}
		else if(e.getActionCommand().equalsIgnoreCase("nuevoUsuario")){
			VistaNuevoUsuario vistaNuevoUser = new VistaNuevoUsuario();
			vistaNuevoUser.setVisible(true);
			new ControladorVistaNuevoUser(vistaNuevoUser, this.ds, userMapper);
			
		}
		else{
			vista.loginSuccess();
		}
	}
	
	public void actionPerformed2(ActionEvent e){
		
	}
}
