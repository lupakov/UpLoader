package ru.magnit.co.tmp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextControlPanel extends JPanel {


	JPanel jpnlFrmt;
	JPanel jpnlHeaders;
	JPanel jpnlTableName;
	JTextField textField;
	JTextField jtfOtherSep;
	JTextField jtfSkipRows;
	JPanel jpnlOther;
	Box box1;
	Box box2;
	public TextControlPanel() {

		setOpaque(true);
		setPreferredSize(new Dimension(205, 400));
		JPanel jpnlStrSep = new JPanel();
		add(jpnlStrSep);
		jpnlStrSep.setLayout(new GridLayout(4,0,0,0));
		jpnlStrSep.setPreferredSize(new Dimension(200, 112));
		JCheckBox jcbCommaSep = new JCheckBox("Запятая \",\"");

		jpnlStrSep.add(jcbCommaSep);
		JCheckBox jcbDotComSep = new JCheckBox("Точка с запятой \";\"");
		jcbDotComSep.setSelected(true);

		jpnlStrSep.add(jcbDotComSep);
		JCheckBox jcbTabSep = new JCheckBox("Табуляция \"\\t\"");
		jpnlStrSep.add(jcbTabSep);
		
		jpnlOther = new JPanel();
		FlowLayout fl = (FlowLayout) jpnlOther.getLayout();
		fl.setAlignment(FlowLayout.LEFT);
		fl.setVgap(0);
		fl.setHgap(0);
		jpnlStrSep.add(jpnlOther);
		JCheckBox jcbOtherSep = new JCheckBox("Другой");
		jcbOtherSep.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				jtfOtherSep.setEditable(!jtfOtherSep.isEditable());

			}
	
			
	
		});
		jpnlOther.add(jcbOtherSep);
		jtfOtherSep = new JTextField();
		jtfOtherSep.setEditable(false);
		jpnlOther.add(jtfOtherSep);
		jtfOtherSep.setColumns(2);

		

		
		
		jpnlStrSep.setBorder(BorderFactory.createTitledBorder("Разделитель в строке"));
		
		
		jpnlFrmt = new JPanel();
		jpnlFrmt.setLayout(new GridLayout(3,0,0,0));
		jpnlFrmt.setPreferredSize(new Dimension(200, 120));
		
		JLabel jlabDigSep = new JLabel("Разделитель в числе");
		JLabel jlabDateFrmt = new JLabel("Дата");
		JLabel jlabTimestampFrmt = new JLabel("Метка");
		JComboBox jcmbDigSep = new JComboBox();
		jcmbDigSep.addItem(".");
		jcmbDigSep.addItem(",");
		JComboBox jcmbDateFrmt = new JComboBox();
		jcmbDateFrmt.addItem("yyyy-mm-dd");
		jcmbDateFrmt.addItem("m/d/yyyy");
		JComboBox jcmbTimestampFrmt = new JComboBox();
		jcmbTimestampFrmt.addItem("yyyy-mm-dd HH:mi:ss");
		JPanel jpnlDS = new JPanel();
		FlowLayout flDS = (FlowLayout)jpnlDS.getLayout();
		flDS.setAlignment(FlowLayout.LEADING);
		flDS.setVgap(1);
		flDS.setHgap(1);
		jpnlDS.add(jlabDigSep);
		jpnlDS.add(jcmbDigSep);
		
		jpnlFrmt.add(jpnlDS);

		JPanel jpnlDF = new JPanel();
		FlowLayout flDF = (FlowLayout)jpnlDF.getLayout();
		flDF.setAlignment(FlowLayout.LEADING);
		flDF.setVgap(1);
		flDF.setHgap(1);
		jpnlDF.add(jlabDateFrmt);
		jpnlDF.add(jcmbDateFrmt);
		
		
		jpnlFrmt.add(jpnlDF);
		
		JPanel jpnlTF = new JPanel();
		FlowLayout flTF = (FlowLayout)jpnlTF.getLayout();
		jpnlTF.add(jlabTimestampFrmt);
		jpnlTF.add(jcmbTimestampFrmt);
		flTF.setAlignment(FlowLayout.LEADING);
		flTF.setVgap(1);
		flTF.setHgap(1);		
		
		jpnlFrmt.add(jpnlTF);
		
		jpnlFrmt.setBorder(BorderFactory.createTitledBorder("Формат данных"));
		this.add(jpnlFrmt);
		
		jpnlHeaders = new JPanel();
		jpnlHeaders.setLayout(new GridLayout(2,0,0,0));
		jpnlHeaders.setPreferredSize(new Dimension(200, 70));
		JCheckBox jcbSkipRows = new JCheckBox("Пропустить строк:");
		jcbSkipRows.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				jtfSkipRows.setEditable(!jtfSkipRows.isEditable());
			}
		});
		jtfSkipRows = new JTextField(2);
		jtfSkipRows.setEditable(false);
		JPanel jpnlSkipRows = new JPanel();
		FlowLayout flSkipRows = (FlowLayout)jpnlSkipRows.getLayout();
		flSkipRows.setAlignment(FlowLayout.LEFT);
		flSkipRows.setHgap(0);
		flSkipRows.setVgap(0);
		jpnlSkipRows.add(jcbSkipRows);
		jpnlSkipRows.add(jtfSkipRows);
		
		JCheckBox jcbHeaders = new JCheckBox("Заголовки в первой");
		jcbHeaders.setSelected(true);
		jpnlHeaders.add(jpnlSkipRows);
		jpnlHeaders.add(jcbHeaders);
		jpnlHeaders.setBorder(BorderFactory.createTitledBorder("Заголовки полей"));
		this.add(jpnlHeaders);
		
		
		
	
		
	}
}
