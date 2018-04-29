package ru.magnit.co.tmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class TextSrcData extends CommonSrcData {
	ArrayList<String[]> records;
	Vector<String> headers;
	Vector<Vector<String>> data;
	File file;
	TextSrcData(){
		records = new ArrayList<String[]>();
		headers = new Vector<String>();
		data = new Vector<Vector<String>>();
	}
	TextSrcData(File file) throws IOException{
		this();
		readRecords( file, ';', 0, "UTF-8");
		processRecords(true);
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

	public void readRecords(File file, char separator, int skipRows, String charsetStr) throws IOException {
		readRecords(file.toPath(),separator , skipRows, charsetStr);
	}
	public void readRecords(Path path, char separator, int skipRows, String charsetStr) throws IOException {
		this.file = path.toFile();
		readRecords(separator, skipRows, charsetStr);
	}
	
	public void readRecords(char separator, int skipRows, String charsetStr) throws IOException {
		records = new ArrayList<String[]>();
		Charset cs = Charset.forName(charsetStr);
		CSVParser parser = new CSVParserBuilder().withSeparator(separator).build();
		try (
				BufferedReader br = Files.newBufferedReader(file.toPath(), cs );
				 CSVReader csvReader = new CSVReaderBuilder(br).withCSVParser(parser).withSkipLines(skipRows)
	                        .build()
		){
			String[] nextRecord;
			int recordCounter = 0;
			while ((nextRecord = csvReader.readNext())!= null & recordCounter < 30) {
				records.add(nextRecord);
				recordCounter++;
			}
		}
	}
	
	public void processRecords(boolean isHeaders) throws IOException {
		headers = new Vector<String>();
		data = new Vector<Vector<String>>();
		int head = 0;
		int colNum;
		
	
		
		if(isHeaders) {
			head = 1;
			headers.addAll(Arrays.asList(records.get(0)));
			
		}else {
			colNum = records.get(0).length;
			for(int i = 0; i<colNum; i++) {
				headers.add("Column_"+i);
			}
		}
		int fLen = records.size();
		for(int i = 0; i<fLen-head; i++) {
			data.addElement(new Vector<String>( Arrays.asList(records.get(head + i))));
		}
	}
	public void updateData(char separator, int skipRows, String charsetStr, boolean isHeaders) throws IOException {
		readRecords(separator, skipRows, charsetStr);
		processRecords(isHeaders);
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

}
