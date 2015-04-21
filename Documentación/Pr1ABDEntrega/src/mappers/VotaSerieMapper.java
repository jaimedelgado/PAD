package mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import modelo.VotaSerie;
import modelo.VotaSerieClaves;

public class VotaSerieMapper extends AbstractMapper<VotaSerie, VotaSerieClaves>{

	public VotaSerieMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	private static final String[] VOTASERIE_KEY_COLUMN_NAMES = new String[]{"NICK", "IDSERIE"};
	private static final String[] VOTASERIE_COLUMN_NAMES = new String[]{
		VOTASERIE_KEY_COLUMN_NAMES[0],
		"NOTA",
		VOTASERIE_KEY_COLUMN_NAMES[1]};
	private static final String VOTASERIE_TABLE_NAME = "VOTA_SERIE";
	

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return VOTASERIE_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return VOTASERIE_COLUMN_NAMES;
	}

	@Override
	protected VotaSerie buildObject(ResultSet rs) throws SQLException {
		VotaSerie result;
		String nick = rs.getString("Nick");
		int nota = rs.getInt("Nota");
		int idSerie = rs.getInt("IdSerie");
		
		result = new VotaSerie(nick,nota, idSerie);
		
		return result;
	}

	@Override
	protected String[] getKeyColumnNames() {
		
		return VOTASERIE_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(VotaSerieClaves key) {
		// TODO Auto-generated method stub
		return new Object[]{ key.getNick(),key.getIdSerie()};
	}

	@Override
	protected Object[] serializeObject(VotaSerie object) {
		// TODO Auto-generated method stub
		
		return new Object[]{object.getNick(), object.getNota(), object.getIdSerie()};
	}
	@Override
	protected VotaSerieClaves getKey(VotaSerie object) {
		// TODO Auto-generated method stub
		VotaSerieClaves claves = new VotaSerieClaves(object.getNick(), object.getIdSerie());
		return claves;
	}
	
	public double notaMedia(int idSerie){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		int result = 0;
		try {
			con = ds.getConnection();
			pst = con.prepareStatement(
					"SELECT avg( "+ VOTASERIE_COLUMN_NAMES[1] +" ) NOTA FROM " + getTableName() +
							" WHERE " + getTableName() + "." + VOTASERIE_KEY_COLUMN_NAMES[1] + 
							" = ? ");
			
			pst.setObject(1,idSerie);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getInt(VOTASERIE_COLUMN_NAMES[1]);
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
