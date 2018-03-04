package ru.magnit.co.tmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;



public class XLSrcData implements SrcData {

	ArrayList<ArrayList<ArrayList<String>>> sheetRecords;
	ArrayList<String> sheets;
	Vector<String> headers;
	ArrayList<String> record;
	Vector<Vector<String>> data;
	ArrayList<ArrayList<String>> curRecords;
	File file;
	int rowCounter;
	
	XLSrcData(){
		sheetRecords = new ArrayList<ArrayList<ArrayList<String>>>();
		headers = new Vector<String>();
		data = new Vector<Vector<String>>();
	}
	XLSrcData(File file) throws IOException, InvalidFormatException{
		this();
		readRecords( file, 0);
		processRecords(true,0,0);
	}
	@Override
	public Vector<String> getHeaders() {
		// TODO Auto-generated method stub
		return headers;
	}

	@Override
	public Vector<Vector<String>> getData() {
		// TODO Auto-generated method stub
		return data;
	}

	public void readRecords(File file,  int skipRows) throws IOException, InvalidFormatException {
		readRecords(file.toPath(), skipRows);
	}
	public void readRecords(Path path, int skipRows) throws IOException, InvalidFormatException {
		this.file = path.toFile();
		readRecords( skipRows);
	}
	
	public void readRecords(int skipRows) throws IOException, InvalidFormatException {
		sheets = new ArrayList<String>();
		
		
		ArrayList<String> list = new ArrayList<String>();
		OPCPackage pk = OPCPackage.open(file);
		XSSFReader xssfReader = null;
		try {
			xssfReader = new XSSFReader(pk);
		    StylesTable styles = xssfReader.getStylesTable();
		    
		    ReadOnlySharedStringsTable strings = null;
		    try {
		        strings = new ReadOnlySharedStringsTable(pk);
		        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		        XMLReader parser = XMLReaderFactory.createXMLReader();  
		            while (iter.hasNext()) {
		            
		                    InputStream stream=iter.next();
		                    sheets.add(iter.getSheetName());
		                    System.out.println(iter.getSheetName());
		                    curRecords = new ArrayList<ArrayList<String>>();
		                    sheetRecords.add(curRecords);
		                    ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new XSSFSheetXMLHandler.SheetContentsHandler() {
		                        @Override
		                        public void startRow(int i) {
		                        	record = new ArrayList<String>();
		                        }

		                        @Override
		                        public void endRow(int i) {
		                        	curRecords.add( record);
		                        	rowCounter++;
		                        	if (rowCounter>30) {
		                        		throw new RuntimeException("пора");
		                        	}
		                        	
		                        }


		                        @Override
		                        public void headerFooter(String s, boolean b, String s1) {

		                        }

								@Override
								public void cell(String arg0, String arg1, XSSFComment arg2) {
									record.add(arg1);
									
								}
				
		                    }, true);


		                    parser.setContentHandler(handler);
		                    System.out.println("Start parse");
		                    rowCounter=0;
		                    parser.parse(new InputSource(stream));
	
		                    System.out.println("Stop parse");


		                   

		        }
		            } catch (SAXException e) {
		                e.printStackTrace();
		            }

		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (OpenXML4JException e) {
		            e.printStackTrace();
		        } catch (RuntimeException e) {
		        	
		        }


		    }

	
	public void processRecords(boolean isHeaders, int skipRows, int sheet) throws IOException {
		headers = new Vector<String>();
		data = new Vector<Vector<String>>();
		int head = 0;
		int colNum;
		
	
		
		if(isHeaders) {
			head = 1;
			headers.addAll(sheetRecords.get(sheet).get(skipRows));
			
		}else {
			colNum = sheetRecords.get(sheet).get(skipRows).size();
			for(int i = 0; i<colNum; i++) {
				headers.add("Column_"+i);
			}
		}
		int fLen = sheetRecords.get(sheet).size();
		for(int i = 0; i<fLen-skipRows-head; i++) {
			data.addElement(new Vector<String>( sheetRecords.get(sheet).get(head+skipRows + i)));
		}
	}
	public void updateData( boolean isHeaders, int skipRows, int sheet) throws IOException {

		processRecords(isHeaders, skipRows, sheet );
	}
	@Override
	public Vector<Integer> getTypes(){
		Vector<Integer> types = new Vector<Integer>();
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
		return types;
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
					} catch (NumberFormatException excep){
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
	public ArrayList<String> getSheetNames(){
		return this.sheets;
	}


}
