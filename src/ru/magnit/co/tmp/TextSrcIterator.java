package ru.magnit.co.tmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Iterator;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class TextSrcIterator implements Iterator<String[]> {
	private CSVParser parser;
	private BufferedReader br;
	private CSVReader csvReader;
	private Charset cs;
	private String[] currentRecord;

	public TextSrcIterator(File file, char separator, int skipRows,  String charsetStr) throws IOException{
		parser = new CSVParserBuilder().withSeparator(separator).build();
		cs = Charset.forName(charsetStr);
		br = Files.newBufferedReader(file.toPath(), cs );
		csvReader = new CSVReaderBuilder(br).withCSVParser(parser).withSkipLines(skipRows).build();
	}
	@Override
	public boolean hasNext() {
		try {
			if ((currentRecord = csvReader.readNext())!= null) {
				return true;
			}
			else return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public String[] next() {
		// TODO Auto-generated method stub
		return currentRecord;
	}

}
