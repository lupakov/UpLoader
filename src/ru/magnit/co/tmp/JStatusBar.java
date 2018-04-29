package ru.magnit.co.tmp;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class JStatusBar extends JPanel implements LoadEngineListener {
	JLabel jlabInfo;
	JProgressBar jprogLoadBar;
	int currentRow = 0;
	int rowsCount = 0;
	
	JStatusBar(){
		this.setPreferredSize(new Dimension(600, 30));

		this.setLayout(new GridLayout(1, 2));
		jlabInfo = new JLabel("");
		this.add(jlabInfo);
		jprogLoadBar = new JProgressBar();

		this.add(jprogLoadBar);

		jprogLoadBar.setVisible(rowsCount>0);

	}

	@Override
	public void onStateChanged(int typeMessage, int valueMessage) {
		// TODO Auto-generated method stub
		switch(typeMessage){
			case 1:
				setCurrentRow(valueMessage);
				break;
			case 2:
				setRowsCount(valueMessage);
				jprogLoadBar.setMaximum(valueMessage);
				break;
		}
		showInfo();
		
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}
	public void showInfo() {
		if(currentRow>0) {
			jlabInfo.setText("Upload row number "+currentRow);
			if(rowsCount>0) {
				jprogLoadBar.setValue(currentRow);
			}
			jprogLoadBar.setVisible(rowsCount>0);
		} else {
			jlabInfo.setText("");
			jprogLoadBar.setVisible(false);
		}
		
	}

}
