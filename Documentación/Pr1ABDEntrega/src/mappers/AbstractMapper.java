package mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractMapper<T,K> {

	protected DataSource ds;

	protected abstract String getTableName();

	protected abstract String[] getColumnNames();
	
	/**
	 * Divide un objeto dado en sus componentes. Las componentes del array
	 * devuelto deben estar en el orden correspondiente al dado por las
	 * columnas devueltas por getColumnNames() 
	 * 
	 * @param object Objeto a dividir
	 * @return Componentes del objeto dividido
	 */
	protected abstract Object[] serializeObject(T object);

	protected abstract T buildObject(ResultSet rs) throws SQLException;

	/**
	 * Devuelve los nombres de las columnas que forman la clave primaria de
	 * la tabla del mapper concreto.
	 * 
	 * @return Array con nombres de columnas clave
	 */
	protected abstract String[] getKeyColumnNames();
	
	
	/**
	 * Divide una clave primaria en sus componentes. Las componentes del array
	 * devuelto deben estar en el orden correspondiente al dado por las
	 * columnas devueltas por getKeyColumnNames() 
	 * 
	 * @param key Clave a dividir
	 * @return Componentes de la clave pasada como parámetro
	 */
	protected abstract Object[] serializeKey(K key);
	
	/**
	 * Obtiene la clave primaria del objeto pasado como parámetro. 
	 * 
	 * @param object Objeto
	 * @return Clave primera del objeto pasado como parámetro.
	 */
	protected abstract K getKey(T object);

	public AbstractMapper(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Devuelve la lista de objetos que satisfacen todas las condiciones
	 * del array pasado como parámetro
	 * 
	 * @param conditions Objetos de la clase QueryCondition que especifican las condiciones
	 *                   de los objetos a buscar
	 * @return Lista de objetos de la tabla que cumplen las condiciones dadas. 
	 *         Si ninguno de ellos las cumple, se devuelve una lista vacía.
	 */
	protected List<T> findByConditions(QueryCondition[] conditions) {
		/*
		 * Este método debería hacer uso de los métodos abstractos:
		 * 
		 * getTableName()
		 * getColumnNames()
		 * buildObject()
		 * 
		 * Éste método se declara como protegido, para evitar hacer uso de nombres
		 * de columnas explícitos (contenidos dentro de las QueryCondition) desde
		 * el resto del programa.
		 */
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<T> result       = new ArrayList<T>();
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			String[] cadenaInterrogacion = new String[conditions.length];
			
			for(int i=0; i<conditions.length; i++){
					cadenaInterrogacion[i] = conditions[i].getColumnName() +" "+ conditions[i].getOperator() + " ? ";
			}
			
			pst = con.prepareStatement(
					"SELECT " + columnNamesWithCommas + " FROM " + getTableName() +  
					" WHERE " + StringUtils.join(cadenaInterrogacion, " AND ")
					);
			
			for(int i = 0; i < conditions.length; i++)
				pst.setObject(i+1, conditions[i].getValue());
			
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
	
	
	
	
	
	////////////////////////////////////
	public List<T> findAll() {
		/*
		 * Este método debería hacer uso de los métodos abstractos:
		 * 
		 * getTableName()
		 * getColumnNames()
		 * buildObject()
		 * 
		 * Éste método se declara como protegido, para evitar hacer uso de nombres
		 * de columnas explícitos (contenidos dentro de las QueryCondition) desde
		 * el resto del programa.
		 */
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<T> result       = new ArrayList<T>();
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			
			pst = con.prepareStatement(
					"SELECT " + columnNamesWithCommas + " FROM " + getTableName()
					);
			
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
	/////////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	protected int deleteByConditions(QueryCondition[] conditions){
		
		Connection con        = null;
		PreparedStatement pst = null;
		int rs          = 0;
		try {
			con = ds.getConnection();
			String tableName = getTableName();
			String[] cadenaInterrogacion = new String[conditions.length];
			
			for(int i=0; i<conditions.length; i++){
					cadenaInterrogacion[i] = conditions[i].getColumnName() +" "+ conditions[i].getOperator() + " ? ";
			}
			
			pst = con.prepareStatement(
							"DELETE FROM  " + tableName + " WHERE " + 
							StringUtils.join(cadenaInterrogacion, " AND ")
					);
			
			for(int i = 0; i < conditions.length; i++)
				pst.setObject(i+1, conditions[i].getValue());
			
			rs = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return rs;
		
	}
	
	public T findById(K ids) {
		String[] columnNames = getKeyColumnNames();
		QueryCondition[] query = new QueryCondition[columnNames.length];
		Object[] obj = serializeKey(ids);
		for(int i=0; i<columnNames.length; i++){
			query[i]=new QueryCondition(columnNames[i], Operator.EQ, obj[i]);
		}
		
		List<T> resultado = findByConditions(query);
		
		if(!resultado.isEmpty())
			return resultado.get(0);
		else
			return null;
		
	}
	
	public int deleteById(K ids){
		String[] columnNames = getKeyColumnNames();
		QueryCondition[] query = new QueryCondition[columnNames.length];
		Object[] obj = serializeKey(ids);
		for(int i=0; i<columnNames.length; i++){
			query[i]=new QueryCondition(columnNames[i], Operator.EQ, obj[i]);
		}
		
		int resultado = deleteByConditions(query);
		
		return resultado;
	}
	
	public int insertObject(T object) {
		Connection con        = null;
		PreparedStatement pst = null;
		int rs = -1;
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			Object[] values = serializeObject(object);
			String[] conditions = new String[values.length];
			
			for(int i=0; i<columnNames.length; i++){
					conditions[i] = " ? ";
			}
			
			pst = con.prepareStatement(
					"INSERT INTO " + getTableName() + " ( "+ columnNamesWithCommas + " )" + " VALUES ( "  
					 + StringUtils.join(conditions, " , ") + " ) "
					);
			
			//Object[] keyElements = serializeKey(ids);
			
			for(int i = 0; i < conditions.length; i++)
				pst.setObject(i+1, values[i]);
			
			rs = pst.executeUpdate();
			
			} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return rs;
	}
	
	public void update(T object){
		Connection con        = null;
		PreparedStatement pst = null;
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String[] cadenaInterrogacionSet = new String[columnNames.length];
			String[] keyColumnNames = getKeyColumnNames();
			
			Object[] obj = null;
			obj = serializeObject(object);
			
			K clavesValor = getKey(object);
			Object[] valorClaves =  serializeKey(clavesValor);
						
			String[] where = null;
			where = new String[keyColumnNames.length];
			
			for(int i=0; i<columnNames.length; i++){
				cadenaInterrogacionSet[i] = columnNames[i]  + " = ? ";
			}

			for(int i = 0; i<keyColumnNames.length; i++){
				where[i] = keyColumnNames[i] + " = '" + valorClaves[i].toString() + "' ";
			}
			
			pst = con.prepareStatement(
					" UPDATE " + getTableName() +  
					" SET " + StringUtils.join(cadenaInterrogacionSet, " , ") +
					" WHERE " + StringUtils.join(where, " AND ")
					);
			
			for(int cont = 0; cont < columnNames.length; cont++)
				pst.setObject(cont+1, obj[cont]);
			
			pst.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		
	}
	
	public DataSource getDataSource(){
		return this.ds;
	}

}