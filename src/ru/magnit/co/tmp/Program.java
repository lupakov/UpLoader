package ru.magnit.co.tmp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.*;

public class Program {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		//writeProperties();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new SwingFrame();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		

	}
	public static void writeProperties() throws FileNotFoundException, IOException {
		Properties prop = new Properties();

		try (FileOutputStream output = new FileOutputStream("config.properties")){
			prop.setProperty("url", "jdbc:teradata://192.168.0.105/CHARSET=UTF16,DATABASE=PRICING_SALE,TMODE=ANSI,TYPE=FASTLOAD");
			prop.setProperty("login","dbc");
			prop.setProperty("password", "dbc");
			prop.store(output, null);
		}
		
	}


}
