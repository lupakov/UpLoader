package ru.magnit.co.tmp;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;



public class XLPreview extends Preview {
	private File fl;
	private JComboBox<String> jcmbSheetName;
	private JCheckBox jcbIsHeaders;
	private JSpinner jspnSkipRows;
	XLPreview(File file) throws IOException{
		super(file);
		fl=file;
		JPanel jpnlChoseSheet = new JPanel();
		jpnlSrsControls.add(jpnlChoseSheet);
		jpnlChoseSheet.setLayout(new GridLayout(3,0,0,0));
		jpnlChoseSheet.setPreferredSize(new Dimension(200, 120));
		JPanel jpnlSheet = new JPanel();
		JLabel jlabSheet = new JLabel("Лист");
		FlowLayout flSeparator = (FlowLayout)jpnlSheet.getLayout();
		flSeparator.setAlignment(FlowLayout.LEFT);
		jcmbSheetName = new JComboBox<String>();
		XLSrcData xld = (XLSrcData)srcData;
		for (String s :xld.getSheetNames()) {
			jcmbSheetName.addItem(s);
		}
		jcmbSheetName.addActionListener(this);
		jpnlSheet.add(jlabSheet);
		jpnlSheet.add(jcmbSheetName);

		jpnlChoseSheet.add(jpnlSheet);
		
		jcbIsHeaders = new JCheckBox("Заголовки");
		jcbIsHeaders.setSelected(true);
		jcbIsHeaders.addActionListener(this);
		jpnlChoseSheet.add(jcbIsHeaders);
		JPanel jpnlSkipRows = new JPanel();
		FlowLayout flSkipRows = (FlowLayout)jpnlSkipRows.getLayout();
		flSkipRows.setAlignment(FlowLayout.LEFT);
		JLabel jlabSkipRows = new JLabel("Пропустить строк");
		jspnSkipRows = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		jspnSkipRows.addChangeListener(this);
		jpnlSkipRows.add(jlabSkipRows);
		jpnlSkipRows.add(jspnSkipRows);
		jpnlChoseSheet.add(jpnlSkipRows);
		jpnlChoseSheet.setBorder(BorderFactory.createTitledBorder("Выбор листа"));
		
	}
	@Override
	protected void prepareSrc(File file) throws IOException {
		try {
			srcData = new XLSrcData(file);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void stateChanged(ChangeEvent e)  {
		// TODO Auto-generated method stub
		try {
			updateSrcData();
			prepareTableControls();
			this.revalidate();
			this.repaint();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			updateSrcData();
			prepareTableControls();
			this.revalidate();
			this.repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateSrcData() throws IOException {
		XLSrcData tmp =(XLSrcData)srcData;
		tmp.updateData( jcbIsHeaders.isSelected(), (int)jspnSkipRows.getValue(),jcmbSheetName.getSelectedIndex() );		
	}
	public File getFile() {
		return fl;
	}
	public String getSheetName() {
		return jcmbSheetName.getSelectedItem().toString();
	}
	public int getSkipRows() {
		return (int)jspnSkipRows.getValue() + (jcbIsHeaders.isSelected() ? 1:0) ;
	}

	
	
	
	
	
	
	
}
