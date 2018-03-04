package ru.magnit.co.tmp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;

public class UploadEngine {
	private String tableName;
	private char[] fieldTypes;
	private Iterator<String[]> srcIterator;
	private char digitSeparator;
	private String dateFormat;
	private String timestampFormat;
	private int batchFreq;
	private SimpleDateFormat dateFormatter;
	private SimpleDateFormat timestampFormatter;
	private NumberFormat doubleFormat;
	

	public UploadEngine(String tableName, char[] fieldTypes, Iterator<String[]> srcIterator ) {
		this.setTableName(tableName);
		this.setFieldTypes(fieldTypes);
		this.setSrcIterator(srcIterator);
		this.setBatchFreq(50000);
		this.setDigitSeparator('.');
		this.setDateFormat("yyyy-mm-dd");
		this.setTimestampFormat("yyyy-mm-dd HH:mm:ss");
		
	}
	public void upload(Connection con) throws SQLException {
		String[] record;
		String queryForInsert = getQueryForInsert();
		int recCounter = 0;
		int fieldCount = fieldTypes.length;
		int batchCounter = 0;
		
		con.clearWarnings();
		try (
			PreparedStatement pStmtDest = con.prepareStatement(queryForInsert);
		) 
		{
			SQLWarning w = con.getWarnings();
            while(w != null) {
                System.out.println("*** SQLWarning caught ***");
                StringWriter sw = new StringWriter();
                w.printStackTrace(new PrintWriter(sw,true));
                System.out.println("SQL State = " + w.getSQLState()
                + ", Error Code = "+ w.getErrorCode()
                + "\n" + sw.toString());
                w.getNextWarning();
                
            }
            
            con.setAutoCommit(false);
            while (srcIterator.hasNext()) {
            	record = srcIterator.next();
            	for (int i = 0 ; i< fieldCount; i++) {
            		switch(fieldTypes[i]) {
            		case 's':
            			pStmtDest.setString(i+1, record[i]);
            			break;
            		case 'i':
            			pStmtDest.setInt(i+1, Integer.parseInt(record[i]));
            			break;
            		case 'f':
            			try {
							pStmtDest.setDouble(i+1, (double) doubleFormat.parse(record[i]));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			break;
            		case 'd':
            			try {
							pStmtDest.setDate(i + 1,new java.sql.Date(dateFormatter.parse(record[i]).getTime()) );
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			break;
            		case 't':
            			try {
							pStmtDest.setTimestamp(i +1 , new java.sql.Timestamp(timestampFormatter.parse(record[i]).getTime()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			break;
            		}
            	}
            	
            	pStmtDest.addBatch();
            	recCounter ++;
            	if(recCounter == batchFreq ) {
            		w = pStmtDest.getWarnings();
                    while(w != null){
                        System.out.println("*** SQL Warnings caught ***");
                        StringWriter sw = new StringWriter();
                        w.printStackTrace(new PrintWriter(sw,true));
                        System.out.println("SQL State = " + w.getSQLState()
                        + ", Error Code = " + w.getErrorCode()
                        + "\n" + sw.toString());
                        w = w.getNextWarning();
                    }
                    try {
                        int arrInsert[] = pStmtDest.executeBatch();
                        }
                        catch (BatchUpdateException e){
                            System.out.println("Our ex");
                             for (SQLException ex = e ; ex != null ; ex = ex.getNextException())
                                    if(ex.getErrorCode() != 1339) ex.printStackTrace () ;
                        }
                        catch(Exception e){
                            System.out.println("Other Exception");
                            System.out.println(e.getMessage());
                        }        
                    batchCounter ++;
                    System.out.println("add batch "+batchCounter);

            	}
            	
            }
            w = pStmtDest.getWarnings();
            while(w != null){
                System.out.println("*** SQL Warnings caught ***");
                StringWriter sw = new StringWriter();
                w.printStackTrace(new PrintWriter(sw,true));
                System.out.println("SQL State = " + w.getSQLState()
                + ", Error Code = " + w.getErrorCode()
                + "\n" + sw.toString());
                w = w.getNextWarning();
            }
            try {
            int arrInsert[] = pStmtDest.executeBatch();
            }
            catch (BatchUpdateException e){
                System.out.println("Our ex");
                 for (SQLException ex = e ; ex != null ; ex = ex.getNextException())
                        if(ex.getErrorCode() != 1339) ex.printStackTrace () ;
            }
            catch(Exception e){
                System.out.println("Other Exception");
                System.out.println(e.getMessage());
            }
            con.commit();
            
            w = pStmtDest.getWarnings();
            while(w != null){
                System.out.println("*** SQL Warnings caught ***");
                StringWriter sw = new StringWriter();
                w.printStackTrace(new PrintWriter(sw,true));
                System.out.println("SQL State = " + w.getSQLState()
                + ", Error Code = " + w.getErrorCode()
                + "\n" + sw.toString());
                w = w.getNextWarning();
            }
            
			
		}
		
	}
	
	private String getQueryForInsert() {
		String query = "insert into "+ tableName
        + " values (" +getQuestionMark(fieldTypes.length)+")";  
		return query;
	}

	public char getDigitSeparator() {
		return digitSeparator;
	}
	public void setDigitSeparator(char digitSeparator) {
		this.digitSeparator = digitSeparator;
		switch(digitSeparator) {
		case ',':
			doubleFormat = NumberFormat.getInstance(Locale.FRANCE);
			break;
		case '.':
			doubleFormat = NumberFormat.getInstance(Locale.US);
			break;
		}
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
		this.dateFormatter = new SimpleDateFormat(dateFormat);
	}
	public String getTimestampFormat() {
		return timestampFormat;
	}
	public void setTimestampFormat(String timestampFormat) {
		this.timestampFormat = timestampFormat;
		this.timestampFormatter = new SimpleDateFormat(timestampFormat);
	}
	public int getBatchFreq() {
		return batchFreq;
	}
	public void setBatchFreq(int batchFreq) {
		this.batchFreq = batchFreq;
	}
	public Iterator<String[]> getSrcIterator() {
		return srcIterator;
	}
	public void setSrcIterator(Iterator<String[]> srcIterator) {
		this.srcIterator = srcIterator;
	}
	public char[] getFieldTypes() {
		return fieldTypes;
	}
	public void setFieldTypes(char[] fieldTypes) {
		this.fieldTypes = fieldTypes;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public SimpleDateFormat getDateFormatter() {
		return dateFormatter;
	}
	public void setDateFormatter(String frmt) {
		this.dateFormatter = new SimpleDateFormat(frmt);
	}
	public SimpleDateFormat getTimestampFormatter() {
		return timestampFormatter;
	}
	public void setTimestampFormatter(String frmt) {
		this.timestampFormatter = new SimpleDateFormat(frmt);
	}
	
	public static String getQuestionMark (int cnt) {
            String resString = "";
            for (int i = 1; i <= cnt; i++) {
                    resString = resString + "?,";
            }
            resString = resString.substring(0, resString.length()-1);

            return resString;
    }

	

}
