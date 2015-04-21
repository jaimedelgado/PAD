package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import modelo.Personaje;

public class PersonajeMapper extends AbstractMapper<Personaje, Integer>{

	private static final String[] PERSONAJE_KEY_COLUMN_NAMES = new String[]{"IDPERSONAJE"};
	private static final String[] PERSONAJE_COLUMN_NAMES = new String[]{
		"NOMBRE",
		PERSONAJE_KEY_COLUMN_NAMES[0],
		"DESCRIPCION"
		};
	private static final String PERSONAJE_TABLE_NAME = "PERSONAJE";
	
	public PersonajeMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return PERSONAJE_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return PERSONAJE_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Personaje object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getNombre(), object.getIdPersonaje(), object.getDescripcion()};
	}

	@Override
	protected Personaje buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return new Personaje(rs.getString("NOMBRE"), rs.getInt("IDPERSONAJE"), rs.getString("DESCRIPCION"));
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return PERSONAJE_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Integer key) {
		// TODO Auto-generated method stub
		return new Object[]{key};
	}

	@Override
	protected Integer getKey(Personaje object) {
		// TODO Auto-generated method stub
		return object.getIdPersonaje();
	}

}
