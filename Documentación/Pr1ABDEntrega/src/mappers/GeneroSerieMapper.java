package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import modelo.GeneroSerie;

public class GeneroSerieMapper extends AbstractMapper<GeneroSerie, GeneroSerie>{

	private static final String[] GENEROSERIE_KEY_COLUMN_NAMES = new String[]{"IDSERIE", "TIPO"};
	private static final String[] GENEROSERIE_COLUMN_NAMES = new String[]{
		GENEROSERIE_KEY_COLUMN_NAMES[0],
		GENEROSERIE_KEY_COLUMN_NAMES[1],};
	private static final String GENEROSERIE_TABLE_NAME = "GENERO_SERIE";
	
	public GeneroSerieMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return GENEROSERIE_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return GENEROSERIE_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(GeneroSerie object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getIdSerie(), object.getGenero()};
	}

	@Override
	protected GeneroSerie buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return new GeneroSerie(rs.getInt(GENEROSERIE_COLUMN_NAMES[0]), 
								rs.getString(GENEROSERIE_COLUMN_NAMES[1]));
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return GENEROSERIE_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(GeneroSerie key) {
		// TODO Auto-generated method stub
		return new Object[]{key.getIdSerie(), key.getGenero()};
	}

	@Override
	protected GeneroSerie getKey(GeneroSerie object) {
		// TODO Auto-generated method stub
		return new GeneroSerie(object.getIdSerie(), object.getGenero());
	}

	public ArrayList<GeneroSerie> findGeneros(int idSerie) {
		// TODO Auto-generated method stub
		List<GeneroSerie> result = new ArrayList<GeneroSerie>();
		
		QueryCondition[] query =
				new QueryCondition[]{new QueryCondition(GENEROSERIE_KEY_COLUMN_NAMES[0], Operator.EQ, idSerie)};
		
		result = findByConditions(query);
		
		return (ArrayList<GeneroSerie>) result;
	}

}
