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
import java.util.ArrayList;
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
	
	JStatusBar jpnlStatus;
	ArrayList<LoadEngineListener> listeners = new ArrayList<>();
	JButton jbLoad;
	SwingFrame() throws FileNotFoundException, IOException{
		loadType = 0;
		con = new DestConnnection();
		tbl  = new DestTable();
		jpnlMain = new JPanel();
		jpnlMain.setLayout(new BorderLayout());
		jfrm = new JFrame("UpLoader");
		jfrm.setSize(1024, 768);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setLayout(new BorderLayout());

		jmb = new JMenuBar();
		jmFileMenu = new JMenu("Файл");

		jmExitItem = new JMenuItem("Выход") ;
		jpnlStatus = new JStatusBar();
		jfrm.add(jpnlStatus, BorderLayout.SOUTH);
		listeners.add(jpnlStatus);
		//jmFileMenu.add(jmExitItem);

		jmb.add(jmFileMenu);
		jtb = new JToolBar();
		jtb.setFloatable(false);
		jfrm.add(jpnlMain);
		Icon openTxtIcon = new ImageIcon(this.getClass().getResource("/res/csv_6.png"));
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
		Icon openXlIcon = new ImageIcon(this.getClass().getResource("/res/xlsx_6.png"));
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
		
		Icon openSqlIcon = new ImageIcon(this.getClass().getResource("/res/sql_6.png"));
		JButton jbOpenSQL = new JButton(openSqlIcon);
		jbOpenSQL.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
		
			
					jpnlView = new SQLPreview();
					jpnlMain.removeAll();
					jpnlMain.add(jpnlView);
					//jfrm.add(jpnlView);
					jfrm.revalidate();
					jfrm.repaint();
					loadType = 3;
			
				
				
			}
		});
		jtb.add(jbOpenSQL);
		Icon uploadIcon = new ImageIcon(this.getClass().getResource("/res/load_6.png"));
		jbLoad = new JButton(uploadIcon);
		jbLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Runnable runner = new Runnable() {
					
					@Override
					public void run() {
						jbLoad.setEnabled(false);
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
								tbl.loadData(src,listeners, jpnlView.getDigSeparator(), jpnlView.getFrmtDate(), jpnlView.getFrmtTimestamp());
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
						else if (loadType == 3) {
							tbl.setConnection(con);
							tbl.setTableName(jpnlView.getTableName());
							tbl.setFieldNames(jpnlView.getFieldNames());
							tbl.setFieldTypes(jpnlView.getFieldTypes());
							tbl.setIndexes(jpnlView.getIndexes());
							tbl.setRewriteType(jpnlView.getRewriteType());
							SQLPreview sqlView = (SQLPreview)jpnlView;
							try {
								src = new SQLSrcIterator(sqlView.getServerType(), sqlView.getServerAddress(), sqlView.getLogMech(), sqlView.getTrusted(), sqlView.getUser(), sqlView.getPassword(), sqlView.getSQLText());
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								tbl.loadData(src,listeners, jpnlView.getDigSeparator(), jpnlView.getFrmtDate(), jpnlView.getFrmtTimestamp());
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
						}
						}
						jbLoad.setEnabled(true);
					}
				};
				(new Thread (runner)).start();
			
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
