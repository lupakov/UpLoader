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
		this.driver = getDriverName();
		
	}
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection con = null;
		String connectionString = getUrl();
		Class.forName(this.driver);
		con = DriverManager.getConnection(connectionString);
		return con;
		
	}
	
	private String getUrl() {
		switch (this.serverType) {
		case "MS SQL":
			return getMsSqlUrl();
		case "Teradata":
			return getTeradataUrl();
		}
		return "";
	}
	
	private String getMsSqlUrl() {
		StringBuilder sUrl = new StringBuilder();
		sUrl.append("jdbc:sqlserver://");
		sUrl.append(this.serverAddress);
		sUrl.append(";");
		if(this.trusted) {
			sUrl.append("integratedSecurity=true;");
		} else {
			sUrl.append("user=");
			sUrl.append(this.user);
			sUrl.append(";");
			sUrl.append("password=");
			sUrl.append(this.password);
			sUrl.append(";");
		}
		return sUrl.toString();
	}
	private String getTeradataUrl() {
		StringBuilder sUrl = new StringBuilder();
		sUrl.append("jdbc:teradata://" );
		sUrl.append(this.serverAddress);
		sUrl.append("/");
		sUrl.append("LOGMECH=");
		sUrl.append(this.logMech);
		sUrl.append(",");
		sUrl.append("USER=");
		sUrl.append(this.user);
		sUrl.append(",");
		sUrl.append("PASSWORD=");
		sUrl.append(this.password);
		
		return sUrl.toString();
	}
	
	private String getDriverName() {
		switch (this.serverType) {
		case "MS SQL":
			return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		case "Teradata":
			return "com.teradata.jdbc.TeraDriver";
		}
		return "";
	}

}
