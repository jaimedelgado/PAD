package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import modelo.ComentarioSerie;
import modelo.ComentarioSerieClaves;

public class ComentarioSerieMapper extends AbstractMapper<ComentarioSerie, ComentarioSerieClaves>{

	private static final String[] COMENTARIOSERIE_KEY_COLUMN_NAMES = new String[]{"NICK", "IDSERIE"};
	private static final String[] COMENTARIOSERIE_COLUMN_NAMES = new String[]{
		COMENTARIOSERIE_KEY_COLUMN_NAMES[0],
		"TEXTO",
		"FECHA",
		COMENTARIOSERIE_KEY_COLUMN_NAMES[1]
		};
	private static final String COMENTARIOSERIE_TABLE_NAME = "COMENTA_SERIE";
	
	public ComentarioSerieMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return COMENTARIOSERIE_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return COMENTARIOSERIE_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(ComentarioSerie object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getNick(), 
							object.getTexto(), 
							object.getFecha(), 
							object.getIdSerie()};
	}

	@Override
	protected ComentarioSerie buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return new ComentarioSerie(rs.getString("NICK"), 
									rs.getString("TEXTO"), 
									rs.getTimestamp("FECHA"), 
									rs.getInt("IDSERIE"));
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return COMENTARIOSERIE_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(ComentarioSerieClaves key) {
		// TODO Auto-generated method stub
		return new Object[]{key.getNick(), key.getIdSerie()};
	}

	@Override
	protected ComentarioSerieClaves getKey(ComentarioSerie object) {
		// TODO Auto-generated method stub
		return new ComentarioSerieClaves(object.getNick(), object.getIdSerie());
	}
	
	public ArrayList<ComentarioSerie> getComentarios(int idSerie){
		ArrayList<ComentarioSerie> comments = new ArrayList<ComentarioSerie>();
		QueryCondition[] query = new QueryCondition[]{
				new QueryCondition(COMENTARIOSERIE_KEY_COLUMN_NAMES[1],
									Operator.EQ,
									idSerie)};
		comments = (ArrayList<ComentarioSerie>) findByConditions(query);
		return comments;
		
	}

}
