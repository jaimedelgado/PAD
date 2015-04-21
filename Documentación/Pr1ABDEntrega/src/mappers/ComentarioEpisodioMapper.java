package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import modelo.ComentarioEpisodio;
import modelo.ComentarioEpisodioClaves;

public class ComentarioEpisodioMapper extends AbstractMapper<ComentarioEpisodio, ComentarioEpisodioClaves>{

	private static final String[] COMENTARIOEPISODIO_KEY_COLUMN_NAMES = new String[]{"IDSERIE", "NUM_ORDEN", "NICK"};
	private static final String[] COMENTARIOEPISODIO_COLUMN_NAMES = new String[]{
		COMENTARIOEPISODIO_KEY_COLUMN_NAMES[0],
		COMENTARIOEPISODIO_KEY_COLUMN_NAMES[1],
		COMENTARIOEPISODIO_KEY_COLUMN_NAMES[2],
		"TEXTO",
		"FECHA"
		
		};
	private static final String COMENTARIOEPISODIO_TABLE_NAME = "COMENTA_EPISODIO";
	
	public ComentarioEpisodioMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return COMENTARIOEPISODIO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return COMENTARIOEPISODIO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(ComentarioEpisodio object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getIdSerie(), object.getNumOrden(), object.getNick(),
				object.getTexto(), object.getFecha()};
	}

	@Override
	protected ComentarioEpisodio buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return new ComentarioEpisodio(rs.getString(COMENTARIOEPISODIO_KEY_COLUMN_NAMES[2]),
											rs.getString(COMENTARIOEPISODIO_COLUMN_NAMES[3]),
											rs.getTimestamp(COMENTARIOEPISODIO_COLUMN_NAMES[4]),
											rs.getInt(COMENTARIOEPISODIO_KEY_COLUMN_NAMES[0]),
											rs.getInt(COMENTARIOEPISODIO_KEY_COLUMN_NAMES[1]));
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return COMENTARIOEPISODIO_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(ComentarioEpisodioClaves key) {
		// TODO Auto-generated method stub
		return new Object[]{key.getIdSerie(), key.getNumOrden(), key.getNick()};
	}

	@Override
	protected ComentarioEpisodioClaves getKey(ComentarioEpisodio object) {
		// TODO Auto-generated method stub
		return new ComentarioEpisodioClaves(object.getIdSerie(), 
											object.getNumOrden(),
											object.getNick());
	}
	
	public ArrayList<ComentarioEpisodio> getComentarios(int idSerie, int numTemp){
		ArrayList<ComentarioEpisodio> comments = new ArrayList<ComentarioEpisodio>();
		QueryCondition[] query = new QueryCondition[]{
				new QueryCondition(COMENTARIOEPISODIO_KEY_COLUMN_NAMES[0],
									Operator.EQ,
									idSerie),
				new QueryCondition(COMENTARIOEPISODIO_KEY_COLUMN_NAMES[1], 
									Operator.EQ,
									numTemp)};
		comments = (ArrayList<ComentarioEpisodio>) findByConditions(query);
		return comments;
		
	}

}
