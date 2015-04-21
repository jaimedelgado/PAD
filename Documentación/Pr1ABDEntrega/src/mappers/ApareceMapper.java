package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import modelo.Aparece;

public class ApareceMapper extends AbstractMapper<Aparece, Aparece>{
	
	private static final String[] APARECE_KEY_COLUMN_NAMES = new String[]{"IDSERIE", 
																		"NUM_ORDEN", 
																		"IDPERSONAJE", 
																		"NOMBRE_ARTISTICO"};
	private static final String[] APARECE_COLUMN_NAMES = APARECE_KEY_COLUMN_NAMES;
	private static final String APARECE_TABLE_NAME = "APARECE";

	public ApareceMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return APARECE_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return APARECE_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Aparece object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getIdSerie(), object.getNumOrden(), 
							object.getIdPersonaje(), object.getNombreArtistico()};
	}

	@Override
	protected Aparece buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return new Aparece(rs.getInt("IDSERIE"), rs.getInt("NUM_ORDEN"), 
						rs.getInt("IDPERSONAJE"), rs.getString("NOMBRE_ARTISTICO"));
	}
	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return APARECE_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Aparece key) {
		// TODO Auto-generated method stub
		return new Object[]{key.getIdSerie(), key.getNumOrden(), 
							key.getIdPersonaje(), key.getNombreArtistico()};
	}

	@Override
	protected Aparece getKey(Aparece object) {
		// TODO Auto-generated method stub
		return object;
	}
	
	public List<Aparece> findPersonajes(int idSerie, int numOrden){

		List<Aparece> result    = new ArrayList<Aparece>();
		
		QueryCondition[] query = 
				new QueryCondition[]{new QueryCondition(APARECE_KEY_COLUMN_NAMES[0], Operator.EQ, idSerie),
									new QueryCondition(APARECE_KEY_COLUMN_NAMES[1], Operator.EQ, numOrden)};
		
		result = findByConditions(query);
		
		return result;		
	}
	
	public boolean actorEnCapitulo(int idSerie, int numOrden, String NombreArtistico){
		List<Aparece> result = new ArrayList<Aparece>();
		
		QueryCondition[] query =
				new QueryCondition[]{new QueryCondition(APARECE_KEY_COLUMN_NAMES[0], Operator.EQ, idSerie),
				new QueryCondition(APARECE_KEY_COLUMN_NAMES[1], Operator.EQ, numOrden),
				new QueryCondition(APARECE_KEY_COLUMN_NAMES[3], Operator.EQ, NombreArtistico)};
		
		result = findByConditions(query);
		
		return (result.size()!=0);
		
	}

	public boolean personajeEnCapitulo(int idSerie, Integer numOrden,
			Integer idPersonaje) {
		// TODO Auto-generated method stub
		List<Aparece> result = new ArrayList<Aparece>();
		
		QueryCondition[] query =
				new QueryCondition[]{new QueryCondition(APARECE_KEY_COLUMN_NAMES[0], Operator.EQ, idSerie),
				new QueryCondition(APARECE_KEY_COLUMN_NAMES[1], Operator.EQ, numOrden),
				new QueryCondition(APARECE_KEY_COLUMN_NAMES[2], Operator.EQ, idPersonaje)};
		
		result = findByConditions(query);
		
		return (result.size()!=0);
	}

}
