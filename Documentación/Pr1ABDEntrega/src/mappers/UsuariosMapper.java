package mappers;

import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import modelo.Usuario;


public class UsuariosMapper extends AbstractMapper<Usuario, String> {
	private static final String[] USUARIOS_KEY_COLUMN_NAMES = new String[]{"NICK"};
	private static final String[] USUARIOS_COLUMN_NAMES = new String[] {
		USUARIOS_KEY_COLUMN_NAMES[0],
		"PASSWORD",
		"NACIMIENTO",
		"FOTO"};
	private static final String USUARIOS_TABLE_NAME = "USUARIO";

	public UsuariosMapper(DataSource ds) {
		super(ds);
	}	
	
	/*public boolean insertUser(){
		Connection con        = null;
		PreparedStatement pst = null;
		
		try {
			con = ds.getConnection();
			pst = con.prepareStatement("INSERT INTO USUARIO(NICK, PASSWORD, NACIMIENTO, FOTO) VALUES (?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}*/
	protected String[] getKeyColumnNames() {
		return USUARIOS_KEY_COLUMN_NAMES;
	}

	protected Usuario buildObject(ResultSet rs) throws SQLException {
		Usuario result;
		String nick     = rs.getString("NICK");
		String password  = rs.getString("PASSWORD");
		Date nacimiento        = rs.getDate("NACIMIENTO");
		Blob foto         = rs.getBlob("FOTO");
		result = new Usuario(nick, password, nacimiento);
		if (!rs.wasNull()) {
			result.setImagen(foto.getBytes(1, (int) foto.length()));
		}
		return result;
	}	
	
	protected String[] getColumnNames() {
		return USUARIOS_COLUMN_NAMES;
	}

	protected String getTableName() {
		return USUARIOS_TABLE_NAME;
	}

	@Override
	protected Object[] serializeKey(String key) {
	
		return new Object[]{key};
	}

	@Override
	protected Object[] serializeObject(Usuario object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getNick(), object.getPassword(), object.getNacimiento(), object.getImagen()};
	}

	@Override
	protected String getKey(Usuario object) {
		// TODO Auto-generated method stub
		return object.getNick();
	}

	

}