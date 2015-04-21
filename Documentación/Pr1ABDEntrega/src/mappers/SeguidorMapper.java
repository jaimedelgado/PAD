package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import modelo.SeguidorSerie;

public class SeguidorMapper extends AbstractMapper<SeguidorSerie, SeguidorSerie>{

	private static final String[] SEGUIDOR_KEY_COLUMN_NAMES = new String[]{"IDSERIE", "NICK"};
	private static final String[] SEGUIDOR_COLUMN_NAMES = new String[]{
		SEGUIDOR_KEY_COLUMN_NAMES[0],
		SEGUIDOR_KEY_COLUMN_NAMES[1]};
	private static final String SEGUIDOR_TABLE_NAME = "SEGUIDOR";
	
	public SeguidorMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return SEGUIDOR_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return SEGUIDOR_COLUMN_NAMES;
	}

	@Override
	protected SeguidorSerie buildObject(ResultSet rs) throws SQLException {
		SeguidorSerie result;
		String nick = rs.getString("Nick");
		int idSerie = rs.getInt("IdSerie");
		
		result = new SeguidorSerie(idSerie,nick);
		
		return result;
	}

	@Override
	protected String[] getKeyColumnNames() {
		
		return SEGUIDOR_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(SeguidorSerie key) {
		// TODO Auto-generated method stub
		return new Object[]{key.getIdSerie(),key.getNickUser()};
	}

	@Override
	protected Object[] serializeObject(SeguidorSerie object) {
		// TODO Auto-generated method stub
		
		return new Object[]{object.getIdSerie(), object.getNickUser()};
	}
	@Override
	protected SeguidorSerie getKey(SeguidorSerie object) {
		// TODO Auto-generated method stub
		return object;
	}
}
