package modelo;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class BBDD {
	
	//HACER PATRÓN SINGLETON
	
	private ComboPooledDataSource cpds;
	
	public BBDD(){
	cpds = new ComboPooledDataSource();
	
	try {
		cpds.setDriverClass("org.gjt.mm.mysql.Driver");
		cpds.setJdbcUrl("jdbc:mysql://localhost/Practica1");
		cpds.setUser("root");
		cpds.setPassword("");
		cpds.setAcquireRetryAttempts(1);
		cpds.setAcquireRetryDelay(1);
		cpds.setBreakAfterAcquireFailure(true);
		} catch (PropertyVetoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
		}
	
	}
	
	public BBDD(String string){
		cpds = new ComboPooledDataSource();
		
		try {
			cpds.setDriverClass("org.gjt.mm.mysql.Driver");
			cpds.setJdbcUrl("jdbc:mysql://localhost/Practica1");
			cpds.setUser(string);
			cpds.setPassword(string);
			cpds.setAcquireRetryAttempts(1);
			cpds.setAcquireRetryDelay(1);
			cpds.setBreakAfterAcquireFailure(true);
			} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			}
		
	}
	
	public DataSource getDataSource(){
		return this.cpds;
	}
	
	
}
