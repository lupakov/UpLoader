package ru.magnit.co.tmp;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.*;

public class TextTablePanel extends JPanel {
	TextTablePanel(Vector data, Vector headers){
		setOpaque(true);
		setLayout(new GridLayout(1,0));
		setPreferredSize(new Dimension(600, 400));
		JTable jtblTxt = new JTable(data, headers);

		jtblTxt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//JScrollPane jscrlp = new JScrollPane(jtblTxt);
		add(jtblTxt);
	}
}
