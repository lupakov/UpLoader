package ru.magnit.co.tmp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;

public class TableNamePanel extends JPanel {
	TableNamePanel(Vector<String> headers, Vector<Vector<String>> data){
		setOpaque(true);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(640, 200));
		JPanel jpnlTableName = new JPanel();
		jpnlTableName.setMinimumSize(new Dimension(400, 50));
		GridBagLayout gblName = new GridBagLayout();
		jpnlTableName.setLayout(gblName);
		GridBagConstraints cName = new GridBagConstraints();
		cName.gridx = 0;
		cName.gridy = 0;
		cName.weightx = 0.0;
		cName.anchor = GridBagConstraints.WEST;
		JLabel jlabName = new JLabel("Наименование таблицы");
		gblName.setConstraints(jlabName, cName);
		jpnlTableName.add(jlabName);
		JTextField jtfName = new JTextField(30);
		jtfName.setText("PRICING_SALE._IMPORT_TABLE_"+new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
		cName.gridx = 1;
		cName.gridy = 0;
		cName.weightx = 1.0;
		cName.insets = new Insets(0, 10, 1, 1);
		cName.fill = GridBagConstraints.HORIZONTAL;
		cName.anchor = GridBagConstraints.WEST;
		gblName.setConstraints(jtfName, cName);
		jpnlTableName.add(jtfName);
		add(jpnlTableName, BorderLayout.NORTH);
		int fieldCount = headers.size();
		JPanel pnlFields = new JPanel();
		pnlFields.setBorder(BorderFactory.createTitledBorder("Поля таблицы"));
		Vector<Integer> fieldTypes = getTypes(data);

		GridBagLayout gbl = new GridBagLayout();
		
		pnlFields.setLayout(gbl);

		GridBagConstraints c = new GridBagConstraints();
		Vector<String> tv = new Vector<String>();
		tv.add("VARCHAR(256)");
		tv.add("FLOAT");
		tv.add("INTEGER");
		tv.add("DATE");
		tv.add("TIMESTAMP(0)");
		ArrayList<JTextField> arTf = new ArrayList<JTextField>();
		ArrayList<JComboBox> arCmb = new ArrayList<JComboBox>();
		ArrayList<JCheckBox> arCb = new ArrayList<JCheckBox>();
		ArrayList<GridBagConstraints> arGbc = new ArrayList<GridBagConstraints>();
		for (int i = 0; i<fieldCount; i++) {
			arTf.add(i, new JTextField(headers.get(i),10));
			arTf.get(i).setSize(50, 20);
			arGbc.add(i*3 + 0, new GridBagConstraints() );
			arGbc.get(i*3 +0).insets = new Insets(0, 0, 1, 1);
			arGbc.get(i*3 +0).fill = GridBagConstraints.HORIZONTAL;
			arGbc.get(i*3 +0).gridx = i;
			arGbc.get(i*3 +0).gridy = 0;
			arGbc.get(i*3 +0).gridwidth = 1;
			arGbc.get(i*3 +0).gridheight = 1;
			arGbc.get(i*3 +0).weighty = 0.0;
			arGbc.get(i*3 +0).weightx = 1.0;
			arGbc.get(i*3 +0).anchor = GridBagConstraints.NORTH;
			


			gbl.setConstraints(arTf.get(i), arGbc.get(i*3 +0));
			pnlFields.add(arTf.get(i));
	
			arCmb.add(i,new JComboBox(tv));
			arCmb.get(i).setSelectedIndex(fieldTypes.get(i)-1);
			arGbc.add(i*3 + 1, new GridBagConstraints() );
			arGbc.get(i*3 +1).insets = new Insets(0, 0, 1, 1);
			arGbc.get(i*3 +1).fill = GridBagConstraints.HORIZONTAL;
			arGbc.get(i*3 +1).gridx = i;
			arGbc.get(i*3 +1).gridy = 1;
			arGbc.get(i*3 +1).gridwidth = 1;
			arGbc.get(i*3 +1).gridheight = 1;
			arGbc.get(i*3 +1).weighty = 0.0;
			arGbc.get(i*3 +1).weightx = 1.0;
			arGbc.get(i*3 +1).anchor = GridBagConstraints.NORTH;
			gbl.setConstraints(arCmb.get(i), arGbc.get(i*3 +1));
			pnlFields.add(arCmb.get(i));
			
			
			arCb.add(i,new JCheckBox("index"));
			arCb.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK));
			arGbc.add(i*3 + 2, new GridBagConstraints() );
			arGbc.get(i*3 +2).insets = new Insets(0, 0, 1, 1);
			arGbc.get(i*3 +2).fill = GridBagConstraints.HORIZONTAL;
			arGbc.get(i*3 +2).gridx = i;
			arGbc.get(i*3 +2).gridy = 2;
			arGbc.get(i*3 +2).gridwidth = 1;
			arGbc.get(i*3 +2).gridheight = 1;
			arGbc.get(i*3 +2).weighty = 0.0;
			arGbc.get(i*3 +2).weightx = 1.0;
			arGbc.get(i*3 +2).anchor = GridBagConstraints.CENTER;
			gbl.setConstraints(arCb.get(i), arGbc.get(i*3 +2));
			pnlFields.add(arCb.get(i));
		}
		System.out.println(gbl.columnWidths);
		System.out.println(gbl.rowHeights);
		TextTablePanel jpnlTbl = new TextTablePanel( data,headers);
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 3;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.weightx = 1.0;
		
		gbl.setConstraints(jpnlTbl,c);
		pnlFields.add(jpnlTbl);
		JScrollPane jscrFielsdsTable = new JScrollPane(pnlFields);
		add(jscrFielsdsTable);
		
		
	}
	public Vector<Integer> getTypes(Vector<Vector<String>> data){
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
