package ru.magnit.co.tmp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class SQLSrcData extends CommonSrcData {
	Vector<Vector<String>> data;
	Vector<String> headers;
	Vector<Integer> types;
	private String url = null;
	private String server = null;
	private String user = null;
	private String password = null;
	private boolean trusted = false;
	private String logMech = null;
	
	
	public SQLSrcData() {
		headers = new Vector<>();
		headers.add("NoName");
		data = new Vector<Vector<String>>();
		types = new Vector<>();
		types.add(1);
	}
	@Override
	public Vector<String> getHeaders() {

		return this.headers;
	}

	@Override
	public Vector<Vector<String>> getData() {
		// TODO Auto-generated method stub
		return this.data;
	}

	@Override
	public Vector<Integer> getTypes() {
		// TODO Auto-generated method stub
		return this.types;
	}
	public void updateData(String serverType, String serverAddress, String logMech, boolean trusted, String user, String password, String sql) throws ClassNotFoundException, SQLException {
		headers = new Vector<>();
		data = new Vector<Vector<String>>();
		types = new Vector<>();
		SrcConnection connection = new SrcConnection(serverType, serverAddress, logMech, trusted, user, password);
		try(Connection con = connection.getConnection()){
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			int recordCount = 0;
			int columnCount = rs.getMetaData().getColumnCount();
			for (int c = 1 ; c<= columnCount; c++) {
				headers.add(rs.getMetaData().getColumnName(c));
			}
			Vector<String> rec = new Vector<String>();
			while(rs.next() & recordCount < 30) {
				for (int i = 1; i <= columnCount; i++) {
					rec.addElement(rs.getString(i));
					
				}
				data.add(rec);
				rec = new Vector<String>();
				recordCount++;
			}
		}
		types = new Vector<Integer>();
		int i = 0;
		int k = 0;
		for (Vector<String> row: data) {
			int j = 0;
			for (String cell : row) {
				k = getType(cell);
				if(i == 0) {
					types.add(j,k);
				} else {
					if (k < types.get(j)) {
						types.set(j, k);
					}
				}
				
				j++;
			}
			i++;
		}
		
	}
	public int getType(String str) {
		SimpleDateFormat formatterT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		SimpleDateFormat formatterD = new SimpleDateFormat("dd.MM.yyyy");
		try
		{
			Date t = formatterT.parse(str);
			return 5;
		} catch (Exception e) {
			try {
				Date d = formatterD.parse(str);
				return 4;
			} catch(Exception ex) {
				try
				{
				//System.out.println("we int try " + str);
				int in = Integer.parseInt(str);
				//System.out.println("int done " + str);
				return 3;
				} catch (NumberFormatException exc) {
					try 
					{
						double dou = Double.parseDouble(str);
						return 2;
					} catch (Exception excep){
						return 1;
					}
				}
			}
		}
		
		
		

	}
	
	public String intToType(int typeCode) {
		switch (typeCode){
		case 1:return "VARCHAR(256)";
		case 2:return "FLOAT";
		case 3:return "INTEGER";
		case 4:return "DATE";
		case 5:return "TIMESTAMP(0)";
		}
		return "VARCHAR(256)";
	}

}
