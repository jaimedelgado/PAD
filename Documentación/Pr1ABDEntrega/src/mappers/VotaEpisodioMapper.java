package mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import modelo.VotaEpisodio;
import modelo.VotaEpisodioClaves;

public class VotaEpisodioMapper extends AbstractMapper<VotaEpisodio, VotaEpisodioClaves>{

	private static final String[] VOTAEPISODIO_KEY_COLUMN_NAMES = new String[]{"IDSERIE", "NUM_ORDEN", "NICK"};
	private static final String[] VOTAEPISODIO_COLUMN_NAMES = new String[]{
		VOTAEPISODIO_KEY_COLUMN_NAMES[0],
		VOTAEPISODIO_KEY_COLUMN_NAMES[1],
		VOTAEPISODIO_KEY_COLUMN_NAMES[2],
		"NOTA"};
	private static final String VOTAEPISODIO_TABLE_NAME = "VOTA_EPISODIO";
	
	
	public VotaEpisodioMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return VOTAEPISODIO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return VOTAEPISODIO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(VotaEpisodio object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getIdSerie(), object.getNumOrden(), object.getNick(), object.getNota()};
	}

	@Override
	protected VotaEpisodio buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		VotaEpisodio result;
		String nick = rs.getString("Nick");
		int nota = rs.getInt("Nota");
		int idSerie = rs.getInt("IdSerie");
		int numOrden = rs.getInt("Num_Orden");
		result = new VotaEpisodio(idSerie, numOrden, nick, nota);
		
		return result;
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return VOTAEPISODIO_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(VotaEpisodioClaves key) {
		// TODO Auto-generated method stub
		return new Object[]{key.getIdSerie(), key.getNumOrden(), key.getNick()};
	}

	@Override
	protected VotaEpisodioClaves getKey(VotaEpisodio object) {
		// TODO Auto-generated method stub
		VotaEpisodioClaves claves = new VotaEpisodioClaves(object.getIdSerie(),
				object.getNumOrden(), object.getNick());
		return claves;
	}
	
	public Double notaMedia(int idSerie, int num_orden){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		Double result = null;
		try {
			con = ds.getConnection();
			pst = con.prepareStatement(
					"SELECT avg( "+ VOTAEPISODIO_COLUMN_NAMES[3] + " ) NOTA FROM " + getTableName() +
							" WHERE " + getTableName() + "." + VOTAEPISODIO_KEY_COLUMN_NAMES[0] + 
							" = ? AND " + VOTAEPISODIO_KEY_COLUMN_NAMES[1] + 
							" = ? ");
			
			pst.setObject(1,idSerie);
			pst.setObject(2, num_orden);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getDouble(VOTAEPISODIO_COLUMN_NAMES[3]);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return result;		
		
	}

}
