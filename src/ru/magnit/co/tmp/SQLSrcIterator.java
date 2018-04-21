package ru.magnit.co.tmp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class SQLSrcIterator implements Iterator<String[]> {
	private SrcConnection connection;
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private int columnCount;
	String[] rec;
	
	public SQLSrcIterator(String serverType, String serverAddress, String logMech, boolean trusted, String user, String password, String sql) throws ClassNotFoundException, SQLException {
		connection = new SrcConnection(serverType, serverAddress, logMech, trusted, user, password);
		con = connection.getConnection();
		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		columnCount = rs.getMetaData().getColumnCount();
		
	}

	@Override
	public boolean hasNext() {
		try {
			return this.rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Cannot chek next");
		}
		
	}

	@Override
	public String[] next() {
		String[] record = new String[this.columnCount];
		for (int i = 1; i<=this.columnCount; i++) {
			try {
				record[i-1] = this.rs.getString(i);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("Cannot get value from column"+i);
			}
		}
		return record;
	}

}
