package ru.magnit.co.tmp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SrcConnection {
	private String serverType;
	private String serverAddress;
	private String logMech;
	private boolean trusted;
	private String user;
	private String password;
	private String driver;
	
	public SrcConnection(String serverType, String serverAddress, String logMech, boolean trusted, String user, String password) {
		this.serverType = serverType;
		this.serverAddress = serverAddress;
		this.logMech = logMech;
		this.trusted = trusted;
		this.user = user;
		this.password = password;
		
	}
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection con = null;
		String connectionString = "jdbc:sqlserver://localhost;integratedSecurity=true;";
		driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		Class.forName(driver);
		con = DriverManager.getConnection(connectionString);
		return con;
		
	}

}
