package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class VistaLog extends JFrame implements ActionListener{
	
	
	private JFrame frame;
	

	private JPanel panel;
	private JLabel usuarioLabel;
	private JTextField usuarioText;
	private JLabel passwordLabel;
	private JPasswordField passwordText;
	private JButton loginButton;
	private JButton newUserButton;
	
	
	public VistaLog(){
		this.frame = new JFrame("Ventana Login");
		this.frame.setSize(300, 150);
		this.frame.setResizable(true);
		
		this.panel = new JPanel();
		this.frame.add(panel);
		this.panel.setLayout(null);
		initFields();
		this.frame.setVisible(true);
		this.frame.setLocationRelativeTo(null);
		
	}
	
	private void initFields(){
		this.usuarioLabel = new JLabel("Usuario: ");
		this.usuarioLabel.setBounds(10, 10, 90, 30);
		this.usuarioText = new JTextField(30);
		this.usuarioText.setBounds(110, 10, 180, 25);
		this.passwordLabel = new JLabel("Password: ");
		this.passwordLabel.setBounds(10, 40, 90, 30);
		this.passwordText = new JPasswordField(30);
		this.passwordText.setBounds(110, 40, 180, 25);
		this.loginButton = new JButton("Login");
		this.loginButton.setBounds(10, 80, 100, 30);
		this.loginButton.setActionCommand("login");
		this.newUserButton = new JButton("Registrar");
		this.newUserButton.setActionCommand("nuevoUsuario");
		this.newUserButton.setBounds(190, 80, 100, 30);
		this.panel.add(usuarioLabel);
		this.panel.add(usuarioText);
		this.panel.add(passwordLabel);
		this.panel.add(passwordText);
		this.panel.add(loginButton);
		this.panel.add(newUserButton);

	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	public void addLoginListener(ActionListener al){
		this.loginButton.addActionListener(al);
		this.newUserButton.addActionListener(al);
	}
	
	public String getUsuarioText() {
		return usuarioText.getText();
	}

	public void setUsuarioText(JTextField usuarioText) {
		this.usuarioText = usuarioText;
	}

	public String getPasswordText() {
		 return new String(this.passwordText.getPassword());
	}

	public void setPasswordText(JPasswordField passwordText) {
		this.passwordText = passwordText;
	}
	
	public void cerrar(){
		this.frame.dispose();
	}
	
	public void loginSuccess() {
		JOptionPane.showMessageDialog(null,  "Logueado","", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
