package ru.magnit.co.tmp;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

public class TextPreview extends Preview {
	private JComboBox<Character> jcmbSeparator;
	private JComboBox<String> jcmbCharset;
	private JCheckBox jcbIsHeaders;
	private JSpinner jspnSkipRows;
	private File fl;
	
	TextPreview(File file) throws IOException{
		super(file);
		fl=file;
		JPanel jpnlFileSettings = new JPanel();
		jpnlSrsControls.add(jpnlFileSettings);
		jpnlFileSettings.setLayout(new GridLayout(4,0,0,0));
		jpnlFileSettings.setPreferredSize(new Dimension(210, 150));
		JPanel jpnlSeparator = new JPanel();
		JLabel jlabSeparator = new JLabel("Разделитель");
		FlowLayout flSeparator = (FlowLayout)jpnlSeparator.getLayout();
		flSeparator.setAlignment(FlowLayout.LEFT);
		jcmbSeparator = new JComboBox<Character>();
		jcmbSeparator.addItem(';');
		jcmbSeparator.addItem(',');
		jcmbSeparator.addItem('\t');
		jcmbSeparator.addItem('|');
		jcmbSeparator.addActionListener(this);
		jpnlSeparator.add(jlabSeparator);
		jpnlSeparator.add(jcmbSeparator);
		jpnlFileSettings.add(jpnlSeparator);
		
		JPanel jpnlCharset = new JPanel();
		FlowLayout flCharset = (FlowLayout)jpnlCharset.getLayout();
		flCharset.setAlignment(FlowLayout.LEFT);
		JLabel jlabCharset = new JLabel("Кодировка");
		jcmbCharset = new JComboBox<String>();

		jcmbCharset.addItem("UTF-8");
		jcmbCharset.addItem("windows-1251");
		jcmbCharset.addActionListener(this);
		jpnlCharset.add(jlabCharset);
		jpnlCharset.add(jcmbCharset);
		jpnlFileSettings.add(jpnlCharset);
		jcbIsHeaders = new JCheckBox("Заголовки");
		jcbIsHeaders.setSelected(true);
		jcbIsHeaders.addActionListener(this);
		jpnlFileSettings.add(jcbIsHeaders);
		JPanel jpnlSkipRows = new JPanel();
		FlowLayout flSkipRows = (FlowLayout)jpnlSkipRows.getLayout();
		flSkipRows.setAlignment(FlowLayout.LEFT);
		JLabel jlabSkipRows = new JLabel("Пропустить строк");
		jspnSkipRows = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		jspnSkipRows.addChangeListener(this);
		jpnlSkipRows.add(jlabSkipRows);
		jpnlSkipRows.add(jspnSkipRows);
		jpnlFileSettings.add(jpnlSkipRows);

		
		jpnlFileSettings.setBorder(BorderFactory.createTitledBorder("Настроики файла"));
		
	}
	@Override
	protected void prepareSrc(File file) throws IOException {
		srcData = new TextSrcData(file);
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
		TextSrcData tmp =(TextSrcData)srcData;
		tmp.updateData((char)jcmbSeparator.getSelectedItem(), (int)jspnSkipRows.getValue(), jcmbCharset.getSelectedItem().toString(), jcbIsHeaders.isSelected());
	}
	public File getFile() {
		return fl;
	}
	public char getSeparator() {
		return (char) jcmbSeparator.getSelectedItem();
	}
	public int getSkipRows() {
		return (int)jspnSkipRows.getValue() + (jcbIsHeaders.isSelected() ? 1:0) ;
	}
	public String getCharsetString() {
		return jcmbCharset.getSelectedItem().toString();
	}

}
