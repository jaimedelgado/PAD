package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;
import javax.swing.JFileChooser;

import mappers.UsuariosMapper;
import modelo.BBDD;
import modelo.Usuario;
import vista.User.VistaNuevoUsuario;
import vista.User.VistaUserSiendoUsuario;

public class ControladorVistaNuevoUser implements ActionListener, MouseListener{
	private VistaNuevoUsuario nuevoUsuario;
	private DataSource ds;
	private UsuariosMapper userMapper;

	public ControladorVistaNuevoUser(VistaNuevoUsuario nuevoUsuario, DataSource ds, UsuariosMapper userMapper){
		this.nuevoUsuario = nuevoUsuario;
		this.ds = new BBDD().getDataSource();
		this.userMapper = new UsuariosMapper(this.ds);
		nuevoUsuario.addLoginListener(this);
		nuevoUsuario.addMouseListener(this);
		
		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equalsIgnoreCase("aceptar")){
			String nick;
			nick = nuevoUsuario.getUsuarioText();
			if(!nick.equals("")){
				Usuario user = this.userMapper.findById(nick);
				if(user!=null){//el usuario ya exist�a
					nuevoUsuario.usuarioYaCreado();
					
				}
				else{
					String password = nuevoUsuario.getPasswordText();
					byte fileContent[] = null;
					if(nuevoUsuario.getFile()!=null){
						fileContent = new byte[(int)nuevoUsuario.getFile().length()];
						try {
							nuevoUsuario.getFileInputStream().read(fileContent);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					java.sql.Date sql = null;
					if(!nuevoUsuario.getNacimiento().equals("")){
						 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					     java.util.Date parsed;
						try {
							parsed = format.parse(nuevoUsuario.getNacimiento());
							sql = new java.sql.Date(parsed.getTime());
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					
					
					Usuario user2 = new Usuario(nick, password, sql, fileContent);
					nuevoUsuario.nuevoUsuario();
					nuevoUsuario.cerrar();
					userMapper.insertObject(user2);
					VistaUserSiendoUsuario vistaUser = new VistaUserSiendoUsuario();
					vistaUser.updateVista(user2);
					vistaUser.setVisible(true);
					new ControladorVistaUserSiendoUsuario(vistaUser, this.ds, this.userMapper, user2);//aqui hay que ver si hay que poner esto o no
				}
				
			}
		}
		else if(e.getActionCommand().equalsIgnoreCase("cancelar")){
			nuevoUsuario.cerrar();
		}
		else{
			JFileChooser jf = new JFileChooser();
			if (jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File f = jf.getSelectedFile();
				nuevoUsuario.setFile(f);
				FileInputStream is = null;
				try {
					is = new FileInputStream(f);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				nuevoUsuario.setFileInputStream(is);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		this.nuevoUsuario.limpiarCampo();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
