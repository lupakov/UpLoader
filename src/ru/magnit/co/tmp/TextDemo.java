package ru.magnit.co.tmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import com.opencsv.CSVReader;

public class TextDemo {
	ArrayList<String[]> records;
	Vector<String> headers;
	Vector<Vector<String>> data;
	File file;

	
	TextDemo(File file) throws IOException{
		records = new ArrayList<String[]>();
		this.file = file;

		try (
				Reader reader = Files.newBufferedReader(file.toPath());
				CSVReader csvReader = new CSVReader(reader);
		){
			String[] nextRecord;
			int recordCounter = 0;
			while ((nextRecord = csvReader.readNext())!= null & recordCounter < 30) {
				records.add(nextRecord);
			}
		}
		separate(0,true,",");

	}
	TextDemo(String path) throws IOException{
		this(new File(path));
	}
	public void separate(int skipRows, boolean isHeaders, String sep ) throws IOException {
		headers = new Vector<String>();
		data = new Vector<Vector<String>>();
		int head = 0;
		int colNum;
		
	
		
		if(isHeaders) {
			head = 1;
			headers.addAll(Arrays.asList(records.get(skipRows)));
			
		}else {
			colNum = records.get(skipRows).length;
			for(int i = 0; i<colNum; i++) {
				headers.add("Column_"+i);
			}
		}
		int fLen = records.size();
		for(int i = 0; i<fLen-skipRows-head; i++) {
			data.addElement(new Vector<String>( Arrays.asList(records.get(skipRows + head + i))));
		}
	}
	public Vector<String> getHeaders() {
		return headers;
	}
	public Vector<Vector<String>> getData(){
		return data;
	}

}
