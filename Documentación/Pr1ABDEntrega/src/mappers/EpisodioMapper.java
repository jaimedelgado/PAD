package mappers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

import modelo.Episodio;
import modelo.EpisodioClaves;

public class EpisodioMapper extends AbstractMapper<Episodio, EpisodioClaves>{

	private static final String[] EPISODIO_KEY_COLUMN_NAMES = new String[]{"IdSerie", "Num_Orden"};
	private static final String[] EPISODIO_COLUMN_NAMES = new String[]{
		EPISODIO_KEY_COLUMN_NAMES[0],
		EPISODIO_KEY_COLUMN_NAMES[1],		
		"Num_Temp", 
		"Sinopsis", 
		"Titulo",
		"Estreno",
		};
	private static final String EPISODIO_TABLE_NAME = "EPISODIO";
	
	public EpisodioMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return EPISODIO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return EPISODIO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Episodio object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getIdSerie(), object.getNumOrden(), 
				object.getNumTemporada(), object.getSinopsis(), 
				object.getTitulo(), object.getEstreno()};
	}

	@Override
	protected Episodio buildObject(ResultSet rs) throws SQLException {
		
		Episodio result;
		
		int idSerie = rs.getInt("IdSerie");
		int numOrden = rs.getInt("Num_Orden");
		int numTemporada = rs.getInt("Num_Temp");
		String sinopsis = rs.getString("Sinopsis");
		String titulo = rs.getString("Titulo");
		Date estreno = rs.getDate("Estreno");
		
		result = new Episodio(idSerie, numOrden, numTemporada, sinopsis, titulo, estreno);
		
		return result;
	}

	public ArrayList<Episodio> getEpisodios(int id){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		ArrayList<Episodio> result    = new ArrayList<Episodio>();
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			pst = con.prepareStatement(
					"SELECT " + columnNamesWithCommas + " FROM " + getTableName() + 
					" WHERE " + EPISODIO_KEY_COLUMN_NAMES[0] + " = ? ORDER BY " + 
							EPISODIO_KEY_COLUMN_NAMES[1] + " , " + EPISODIO_COLUMN_NAMES[2]
					);
			
			pst.setObject(1,id);
			rs = pst.executeQuery();
			while (rs.next()) {
				result.add(buildObject(rs));
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
	
	public ArrayList<Episodio> noVistos(String nick, int idSerie){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		ArrayList<Episodio> result    = new ArrayList<Episodio>();
		try {
			con = ds.getConnection();
			pst = con.prepareStatement(
					"SELECT * FROM " + getTableName() + " LEFT OUTER JOIN VISUALIZA ON " +
					getTableName() + "."
					 + EPISODIO_KEY_COLUMN_NAMES[0] + " = VISUALIZA.IDSERIE AND " + 
					 getTableName() + "." + EPISODIO_KEY_COLUMN_NAMES[1] + 
							" = VISUALIZA.NUM_ORDEN AND VISUALIZA.NICK  = ? " +
							" WHERE " + getTableName() + "." + EPISODIO_KEY_COLUMN_NAMES[0] + 
							" = ? AND VISUALIZA.NICK IS NULL"
					);
			
			pst.setObject(1,nick);
			pst.setObject(2, idSerie);
			rs = pst.executeQuery();
			while (rs.next()) {
				result.add(buildObject(rs));
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
	
	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return new String[]{EPISODIO_KEY_COLUMN_NAMES[0], EPISODIO_KEY_COLUMN_NAMES[1]};
	}

	@Override
	protected Object[] serializeKey(EpisodioClaves key) {
		// TODO Auto-generated method stub
		return new Object[]{key.getIdSerie(), key.getNumOrden()};
	}

	@Override
	protected EpisodioClaves getKey(Episodio object) {
		// TODO Auto-generated method stub
		return new EpisodioClaves(object.getIdSerie(), object.getNumOrden());
	}

}
