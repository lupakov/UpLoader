package ru.magnit.co.tmp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class SwingFrame {

	JMenuBar jmb;
	JMenu jmFileMenu;
	JMenu jmOpenMenu;
	JMenuItem jmXlsxItem;
	JMenuItem jmTextItem;
	JMenuItem jmExitItem;
	JToolBar jtb;
	int loadType;
	
	DestConnnection con;
	DestTable tbl;
	Preview jpnlView;
	
	Iterator<String[]> src;
	
	JFrame jfrm;
	JPanel jpnlMain;
	SwingFrame() throws FileNotFoundException, IOException{
		loadType = 0;
		con = new DestConnnection();
		tbl  = new DestTable();
		jpnlMain = new JPanel();
		jpnlMain.setLayout(new BorderLayout());
		jfrm = new JFrame("UpLoader");
		jfrm.setSize(740, 580);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setLayout(new BorderLayout());

		jmb = new JMenuBar();
		jmFileMenu = new JMenu("Файл");

		jmExitItem = new JMenuItem("Выход") ;

	
		//jmFileMenu.add(jmExitItem);

		jmb.add(jmFileMenu);
		jtb = new JToolBar();
		jtb.setFloatable(false);
		jfrm.add(jpnlMain);
		Icon openTxtIcon = new ImageIcon(this.getClass().getResource("txt.png"));
		JButton jbOpenTxt = new JButton(openTxtIcon);

		
		
		jbOpenTxt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File txtFile = getFile();
				try {
					jpnlView = new TextPreview(txtFile);
					jpnlMain.removeAll();
					jpnlMain.add(jpnlView);
					//jfrm.add(jpnlView);
					jfrm.revalidate();
					jfrm.repaint();
					loadType = 1;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		jtb.add(jbOpenTxt);
		Icon openXlIcon = new ImageIcon(this.getClass().getResource("xl.png"));
		JButton jbOpenXL = new JButton(openXlIcon);
		
		jbOpenXL.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File xlFile = getFile();
				try {
					jpnlView = new XLPreview(xlFile);
					jpnlMain.removeAll();
					jpnlMain.add(jpnlView);
					//jfrm.add(jpnlView);
					jfrm.revalidate();
					jfrm.repaint();
					loadType = 2;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		jtb.add(jbOpenXL);
		Icon uploadIcon = new ImageIcon(this.getClass().getResource("upload.png"));
		JButton jbLoad = new JButton(uploadIcon);
		jbLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (loadType == 1 ) {
				tbl.setConnection(con);
				tbl.setTableName(jpnlView.getTableName());
				tbl.setFieldNames(jpnlView.getFieldNames());
				tbl.setFieldTypes(jpnlView.getFieldTypes());
				tbl.setIndexes(jpnlView.getIndexes());
				tbl.setRewriteType(jpnlView.getRewriteType());
				TextPreview txView = (TextPreview)jpnlView;
				try {
					src = new TextSrcIterator(txView.getFile(), txView.getSeparator(), txView.getSkipRows(), txView.getCharsetString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					tbl.loadData(src, jpnlView.getDigSeparator(), jpnlView.getFrmtDate(), jpnlView.getFrmtTimestamp());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if (loadType == 2) {
				tbl.setConnection(con);
				tbl.setTableName(jpnlView.getTableName());
				tbl.setFieldNames(jpnlView.getFieldNames());
				tbl.setFieldTypes(jpnlView.getFieldTypes());
				tbl.setIndexes(jpnlView.getIndexes());
				tbl.setRewriteType(jpnlView.getRewriteType());
				XLPreview xlView = (XLPreview)jpnlView;
				SAXSrcHandler srcHandler = new SAXSrcHandler(xlView.getFile(), xlView.getSheetName(), xlView.getSkipRows());
				try {
					tbl.loadSAXData(srcHandler, jpnlView.getDigSeparator(), jpnlView.getFrmtDate(), jpnlView.getFrmtTimestamp());
				} catch (SQLException | InvalidFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			}
			
		});
		jtb.add(jbLoad);
		JButton jbLoadXL = new JButton("LoadXL");
		jbLoadXL.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tbl.setConnection(con);
				tbl.setTableName(jpnlView.getTableName());
				tbl.setFieldNames(jpnlView.getFieldNames());
				tbl.setFieldTypes(jpnlView.getFieldTypes());
				tbl.setIndexes(jpnlView.getIndexes());
				tbl.setRewriteType(jpnlView.getRewriteType());
				XLPreview xlView = (XLPreview)jpnlView;
				SAXSrcHandler srcHandler = new SAXSrcHandler(xlView.getFile(), xlView.getSheetName(), xlView.getSkipRows());
				try {
					tbl.loadSAXData(srcHandler, jpnlView.getDigSeparator(), jpnlView.getFrmtDate(), jpnlView.getFrmtTimestamp());
				} catch (SQLException | InvalidFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	//	jtb.add(jbLoadXL);
		
		jfrm.setJMenuBar(jmb);
		jfrm.add(jtb,BorderLayout.NORTH);

		jfrm.add(jpnlMain,BorderLayout.CENTER);

		


		//jfrm.add(jpnlTbl,BorderLayout.CENTER);
		//jfrm.add(jpnlCntr, BorderLayout.WEST);

		jfrm.setVisible(true);
	}
	public static File getFile() {
		File f = null;
		JFileChooser fileopen = new JFileChooser();
		int ret = fileopen.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			f = fileopen.getSelectedFile();
		}
		return f;
	}

}
