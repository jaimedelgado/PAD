package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import modelo.Genero;

public class GeneroMapper extends AbstractMapper<Genero, String>{

	private static final String[] GENERO_KEY_COLUMN_NAMES = new String[]{"TIPO"};
	private static final String[] GENERO_COLUMN_NAMES = new String[]{
		GENERO_KEY_COLUMN_NAMES[0]};
	private static final String GENERO_TABLE_NAME = "GENERO";
	
	public GeneroMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return GENERO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return GENERO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Genero object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getGenero()};
	}

	@Override
	protected Genero buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return new Genero(rs.getString(GENERO_KEY_COLUMN_NAMES[0]));
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return GENERO_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(String key) {
		// TODO Auto-generated method stub
		return new Object[]{key};
	}

	@Override
	protected String getKey(Genero object) {
		// TODO Auto-generated method stub
		return object.getGenero();
	}

}
