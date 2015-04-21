package mappers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

import modelo.Serie;

public class SeriesMapper extends AbstractMapper<Serie, Integer>{

	private static final String[] SERIES_KEY_COLUMN_NAMES = new String[]{"IdSerie"};
	private static final String[] SERIES_COLUMN_NAMES = new String[]{
		SERIES_KEY_COLUMN_NAMES[0],
		"Nombre", 
		"Titular", 
		"Sinopsis", 
		"Estreno", 
		"Finalizacion"};
	private static final String SERIES_TABLE_NAME = "SERIES";
	
	public SeriesMapper(DataSource ds) {
		super(ds);
	}
	protected Serie buildObject(ResultSet rs) throws SQLException {
		Serie result;
		int idSerie = rs.getInt(SERIES_KEY_COLUMN_NAMES[0]);
		String nombre = rs.getString("Nombre");
		String titular = rs.getString("Titular");
		String sinopsis = rs.getString("Sinopsis");
		Date estreno = rs.getDate("Estreno");
		Date finalizacion = rs.getDate("Finalizacion");

		
		result = new Serie(idSerie, nombre, titular, sinopsis, estreno, finalizacion);
		
		return result;
	}
	protected String getTableName() {
		return SERIES_TABLE_NAME;
	}
	protected String[] getColumnNames() {
		return SERIES_COLUMN_NAMES;
	}
	protected String[] getKeyColumnName() {
		return SERIES_KEY_COLUMN_NAMES;
	}

	public ArrayList<Serie> BuscaSeriesNombre(String cadena){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		ArrayList<Serie> result    = new ArrayList<Serie>();
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			pst = con.prepareStatement(
					"SELECT " + columnNamesWithCommas + " FROM " + getTableName() +  
					" WHERE NOMBRE LIKE " + " ?"
					);
			cadena = "%"+cadena+"%";
			pst.setObject(1,cadena);
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
		return SERIES_KEY_COLUMN_NAMES;
	}
	
	@Override
	protected Object[] serializeKey(Integer key) {
		// TODO Auto-generated method stub
		return new Object[]{key};
	}
	@Override
	protected Object[] serializeObject(Serie object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getIdSerie(), object.getNombre(), 
				object.getTitular(), object.getSinopsis(), object.getEstreno(),
				object.getFinalizacion()};
	}
	@Override
	protected Integer getKey(Serie object) {
		// TODO Auto-generated method stub
		return object.getIdSerie();
	}
	
	public int maxId(){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		int result			  = -1;
		try {
			con = ds.getConnection();
			pst = con.prepareStatement(
					"SELECT MAX( " + getKeyColumnNames()[0] + " ) FROM " + getTableName() 
					);
			
			rs = pst.executeQuery();
			if (rs.next()) {
				result = Integer.parseInt(rs.getString(1));
			}else return 0; 
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
	
	public ArrayList<Serie> misSeries(String nick){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		ArrayList<Serie> result    = new ArrayList<Serie>();
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			pst = con.prepareStatement(
					"SELECT " + columnNamesWithCommas + " FROM " + getTableName() + 
					" NATURAL JOIN SEGUIDOR WHERE NICK = ? "
					);
			
			pst.setObject(1,nick);
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
	
}
