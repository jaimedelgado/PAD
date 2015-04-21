package mappers;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import modelo.Actor;

public class ActorMapper extends AbstractMapper<Actor, String>{

	private static final String[] ACTOR_KEY_COLUMN_NAMES = new String[]{"NOMBRE_ARTISTICO"};
private static final String[] ACTOR_COLUMN_NAMES = new String[]{"NOMBRE_ARTISTICO", "FECHA", "FOTO"};
private static final String ACTOR_TABLE_NAME = "ACTOR";

	public ActorMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return ACTOR_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return ACTOR_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Actor object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getNombreArtistico(), object.getFecha(), object.getFoto()};
	}

	@Override
	protected Actor buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		Actor actor = new Actor(rs.getString(ACTOR_COLUMN_NAMES[0]), rs.getDate(ACTOR_COLUMN_NAMES[1]));
		Blob foto = rs.getBlob(ACTOR_COLUMN_NAMES[2]);
		if(foto!=null)
			actor.setFoto(foto.getBytes(1, (int) foto.length()));
		return actor;
		
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return ACTOR_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(String key) {
		// TODO Auto-generated method stub
		return new Object[]{key};
	}

	@Override
	protected String getKey(Actor object) {
		// TODO Auto-generated method stub
		return object.getNombreArtistico();
	}

}
