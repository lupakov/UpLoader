package ru.magnit.co.tmp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.Vector;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CharsetTest {

    public static void main(String[] args) throws InvalidFormatException, IOException, SQLException, ClassNotFoundException{
    	SrcConnection c = new SrcConnection("MS SQL", "localhost", null, true, null, null);
    	Connection con = c.getConnection();
    	Statement st = con.createStatement();
    	ResultSet rs = st.executeQuery("SELECT TOP (1000) [productid]  ,[productname] ,[supplierid]  ,[categoryid] FROM [TSQL2012].[Production].[Products]");
    	while (rs.next()) {
    		System.out.println(rs.getString("productname"));
    	}
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*Method pleaseIterateCol will read the column of the given workbook and sheet. Method starts reading column from given
    * cell i.e. User provides row and column index for the starting cell – i.e. startWithRow, startWithCol
    * */
    public static void pleaseIterateCol(String fileName, int sheetIndex, int startWithRow, int startWithCol)
    {
	    InputStream inputStream = null;
	    XSSFWorkbook xssfWorkbook;
	    try {
	    inputStream = new FileInputStream(fileName);
	    } catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    }
	    try {
	    xssfWorkbook = new XSSFWorkbook(inputStream);
	    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
	    //XSSFRow xssfRow = xssfSheet.getRow(startWithRow);
	    //XSSFCell xssfCell = xssfRow.getCell(startWithCol);
	    Iterator<Row> rowIterator = xssfSheet.rowIterator();
	    int i = 0;
	    while(rowIterator.hasNext())
	    {
		    XSSFRow row = (XSSFRow) rowIterator.next();
		    Iterator<Cell> cellIterator = row.cellIterator();
		    while(cellIterator.hasNext())
		    {
		    XSSFCell cell = (XSSFCell) cellIterator.next();
		    switch (cell.getCellType()){
		    case XSSFCell.CELL_TYPE_BOOLEAN:
		    if(cell.getColumnIndex()==startWithCol)
		    {
		    if(cell.getRowIndex() >= (int)startWithRow)
		    System.out.println(cell.getBooleanCellValue() + "\t\t");
		    }
		    break;
		    case XSSFCell.CELL_TYPE_NUMERIC:
		    if(cell.getColumnIndex()==startWithCol)
		    {
		    if(cell.getRowIndex() >= (int)startWithRow)
		    System.out.println(cell.getNumericCellValue() + "\t\t");
		    }
		    break;
		    case XSSFCell.CELL_TYPE_STRING:
		    if(cell.getColumnIndex()==startWithCol)
		    {
		    if(cell.getRowIndex() >= (int)startWithRow)
		    System.out.println(cell.getStringCellValue() + "\t\t");
		    }
		    break;
		    }
		    }
		    i++;
		    if (i>8) {
		    	break;
		    }
	    }
	    inputStream.close();
	    } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    }
    }
    
}
