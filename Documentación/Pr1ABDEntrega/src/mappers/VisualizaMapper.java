package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import modelo.Visualiza;

public class VisualizaMapper extends AbstractMapper<Visualiza, Visualiza>{

	
	public VisualizaMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}
	private static final String[] VISUALIZA_KEY_COLUMN_NAMES = new String[]{"IDSERIE", "NUM_ORDEN", "NICK"};
	private static final String[] VISUALIZA_COLUMN_NAMES = new String[]{
		VISUALIZA_KEY_COLUMN_NAMES[0],
		VISUALIZA_KEY_COLUMN_NAMES[1],
		VISUALIZA_KEY_COLUMN_NAMES[2]};
	private static final String VISUALIZA_TABLE_NAME = "VISUALIZA";
	

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return VISUALIZA_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return VISUALIZA_COLUMN_NAMES;
	}

	@Override
	protected Visualiza buildObject(ResultSet rs) throws SQLException {
		Visualiza result;
		String nick = rs.getString("Nick");
		int idSerie = rs.getInt("IdSerie");
		int num_orden = rs.getInt("Num_Orden");
		
		result = new Visualiza(idSerie,num_orden, nick);
		
		return result;
	}

	@Override
	protected String[] getKeyColumnNames() {
		
		return VISUALIZA_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Visualiza key) {
		// TODO Auto-generated method stub
		return new Object[]{key.getIdSerie(), key.getNumOrden(), key.getNick()};
	}

	@Override
	protected Object[] serializeObject(Visualiza object) {
		// TODO Auto-generated method stub
		
		return new Object[]{object.getIdSerie(), object.getNumOrden(), object.getNick()};
	}
	@Override
	protected Visualiza getKey(Visualiza object) {
		// TODO Auto-generated method stub
		return object;
	}

}
