package ru.magnit.co.tmp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Preview extends JPanel implements ActionListener , ChangeListener{
	protected JComboBox<String> jcmbFrmtDate;
	protected JComboBox<Character> jcmbFrmtDigit;
	protected JComboBox<String> jcmbFrmtTimestamp;
	
	protected SrcData srcData;
	
	protected JTextField jtfTableName;
	protected ArrayList<JTextField> jtfFieldNames;
	protected ArrayList<JComboBox<String>> jcmbFieldTypes;
	protected ArrayList<JCheckBox> jcbIndexes;
	
	protected JTable jtblDataView;
	
	protected JPanel jpnlSrsControls;
	protected JPanel jpnlTableControls;
	
	public Preview() {
		prepareView();
	
		
	}
	public Preview(File file) throws IOException {
		prepareSrc(file);
		prepareView();
	}
	

	
	protected void prepareSrc(File file) throws IOException {
		
	}
	
	protected void prepareSrcControls() {
		jpnlSrsControls.removeAll();
		JPanel jpnlFrmt = new JPanel();
		jpnlFrmt.setLayout(new GridLayout(3,0,0,0));
		jpnlFrmt.setPreferredSize(new Dimension(210, 120));
		
		JLabel jlabFrmtDigit = new JLabel("Разделитель в числе");
		JLabel jlabFrmtDate = new JLabel("Дата");
		JLabel jlabFrmtTimestamp = new JLabel("Метка");
		jcmbFrmtDigit = new JComboBox<Character>();
		jcmbFrmtDigit.addItem('.');
		jcmbFrmtDigit.addItem(',');
		jcmbFrmtDate = new JComboBox<String>();
		jcmbFrmtDate.addItem("yyyy-MM-dd");
		jcmbFrmtDate.addItem("M/d/yyyy");
		jcmbFrmtDate.addItem("M/d/yy");
		jcmbFrmtTimestamp = new JComboBox<String>();
		jcmbFrmtTimestamp.addItem("yyyy-MM-dd HH:mm:ss");
		JPanel jpnlDS = new JPanel();
		FlowLayout flDS = (FlowLayout)jpnlDS.getLayout();
		flDS.setAlignment(FlowLayout.LEADING);
		flDS.setVgap(1);
		flDS.setHgap(1);
		jpnlDS.add(jlabFrmtDigit);
		jpnlDS.add(jcmbFrmtDigit);
		
		jpnlFrmt.add(jpnlDS);

		JPanel jpnlDF = new JPanel();
		FlowLayout flDF = (FlowLayout)jpnlDF.getLayout();
		flDF.setAlignment(FlowLayout.LEADING);
		flDF.setVgap(1);
		flDF.setHgap(1);
		jpnlDF.add(jlabFrmtDate);
		jpnlDF.add(jcmbFrmtDate);
		
		
		jpnlFrmt.add(jpnlDF);
		
		JPanel jpnlTF = new JPanel();
		FlowLayout flTF = (FlowLayout)jpnlTF.getLayout();
		jpnlTF.add(jlabFrmtTimestamp);
		jpnlTF.add(jcmbFrmtTimestamp);
		flTF.setAlignment(FlowLayout.LEADING);
		flTF.setVgap(1);
		flTF.setHgap(1);		
		
		jpnlFrmt.add(jpnlTF);
		
		jpnlFrmt.setBorder(BorderFactory.createTitledBorder("Формат данных"));
		jpnlSrsControls.add(jpnlFrmt);
		
		
	}
	
	protected void prepareTableControls() {
		jpnlTableControls.removeAll();
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
		jtfTableName = new JTextField(30);
		jtfTableName.setText("PRICING_SALE._IMPORT_TABLE_"+new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
		jtfTableName.setHorizontalAlignment(JTextField.LEFT);

		cName.gridx = 1;
		cName.gridy = 0;
		cName.weightx = 1.0;
		cName.insets = new Insets(0, 10, 1, 1);
		cName.fill = GridBagConstraints.HORIZONTAL;
		cName.anchor = GridBagConstraints.WEST;
		gblName.setConstraints(jtfTableName, cName);
		jpnlTableName.add(jtfTableName);
		jpnlTableControls.add(jpnlTableName, BorderLayout.NORTH);
		
		int fieldCount = srcData.getHeaders().size();
		JPanel pnlFields = new JPanel();
		pnlFields.setBorder(BorderFactory.createTitledBorder("Поля таблицы"));
		Vector<Integer> fieldTypes = srcData.getTypes();

		GridBagLayout gbl = new GridBagLayout();
		
		pnlFields.setLayout(gbl);

		GridBagConstraints c = new GridBagConstraints();
		Vector<String> tv = new Vector<String>();
		tv.add("VARCHAR(256)");
		tv.add("FLOAT");
		tv.add("INTEGER");
		tv.add("DATE");
		tv.add("TIMESTAMP(0)");
		jtfFieldNames = new ArrayList<JTextField>();
		jcmbFieldTypes = new ArrayList<JComboBox<String>>();
		jcbIndexes = new ArrayList<JCheckBox>();
		ArrayList<GridBagConstraints> arGbc = new ArrayList<GridBagConstraints>();
		for (int i = 0; i<fieldCount; i++) {
			jtfFieldNames.add(i, new JTextField(srcData.getHeaders().get(i),10));
			jtfFieldNames.get(i).setSize(50, 20);
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
			


			gbl.setConstraints(jtfFieldNames.get(i), arGbc.get(i*3 +0));
			pnlFields.add(jtfFieldNames.get(i));
	
			jcmbFieldTypes.add(i,new JComboBox<String>(tv));
			jcmbFieldTypes.get(i).setSelectedIndex(fieldTypes.get(i)-1);
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
			gbl.setConstraints(jcmbFieldTypes.get(i), arGbc.get(i*3 +1));
			pnlFields.add(jcmbFieldTypes.get(i));
			
			
			jcbIndexes.add(i,new JCheckBox("index"));
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
			gbl.setConstraints(jcbIndexes.get(i), arGbc.get(i*3 +2));
			pnlFields.add(jcbIndexes.get(i));
		}
		System.out.println(gbl.columnWidths);
		System.out.println(gbl.rowHeights);
		TextTablePanel jpnlTbl = new TextTablePanel( srcData.getData(),srcData.getHeaders());
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 3;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.weightx = 1.0;
		
		gbl.setConstraints(jpnlTbl,c);
		pnlFields.add(jpnlTbl);
		JScrollPane jscrFielsdsTable = new JScrollPane(pnlFields);
		jpnlTableControls.add(jscrFielsdsTable);
		
	}
	
	protected void prepareView() {
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		jpnlSrsControls = new JPanel();
		jpnlSrsControls.setPreferredSize(new Dimension(215, 480));
		this.add(jpnlSrsControls, BorderLayout.WEST);
		jpnlTableControls = new JPanel();
		jpnlTableControls.setLayout(new BorderLayout());
		this.add(jpnlTableControls,BorderLayout.CENTER);
		
		prepareSrcControls();
	
		prepareTableControls();
		
		
		


	
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
	public String getTableName() {
		return jtfTableName.getText();
	}
	public ArrayList<String> getFieldNames(){
		ArrayList<String> fn = new ArrayList<String>();
		for (JTextField tf :jtfFieldNames) {
			fn.add(tf.getText());
		}
		return fn;
	}
	public ArrayList<String> getFieldTypes(){
		ArrayList<String> ft = new ArrayList<String>();
		for (JComboBox<String> tf :jcmbFieldTypes) {
			ft.add(tf.getSelectedItem().toString());
		}
		return ft;
	}
	public ArrayList<Boolean> getIndexes(){
		ArrayList<Boolean> ind = new ArrayList<Boolean>();
		for(JCheckBox chb : jcbIndexes) {
			ind.add(chb.isSelected());
		}
		return ind;
		
	}
	public char getRewriteType() {
		return 'w';
	}
	public char getDigSeparator() {
		return (char) jcmbFrmtDigit.getSelectedItem();
	}
	
	public String getFrmtDate() {
		return jcmbFrmtDate.getSelectedItem().toString();
	}
	
	public String getFrmtTimestamp() {
		return jcmbFrmtTimestamp.getSelectedItem().toString();
	}
	

}
