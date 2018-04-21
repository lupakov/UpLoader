package ru.magnit.co.tmp;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class SQLPreview extends Preview {
	private JComboBox<String> jcmbServerType;
	private JTextField jtfServerAddress;
	private JComboBox<String> jcmbLogMech;
	private JTextField jtfUser;
	private JPasswordField jpswPassword;
	private JCheckBox jchbTrusted;
	private JTextArea jtxtareaSQLText;
	private JButton jbtRefreshSQL;
	
	public SQLPreview() {
		super();
		jcmbServerType = new JComboBox<String>();
		jcmbServerType.addItem("MS SQL");
		jcmbServerType.addItem("Teradata");
		JLabel jlabServerType = new JLabel("Тип сервера");
		JPanel jpnlServerType = new JPanel();
		jpnlServerType.add(jlabServerType);
		jpnlServerType.add(jcmbServerType);
		FlowLayout flServerType = (FlowLayout)jpnlServerType.getLayout();
		flServerType.setAlignment(FlowLayout.LEFT);		
		
		jtfServerAddress = new JTextField(10);
		JLabel jlabServerAddress = new JLabel("Сервер");
		JPanel jpnlServerAddress = new JPanel();
		jpnlServerAddress.add(jlabServerAddress);
		jpnlServerAddress.add(jtfServerAddress);
		FlowLayout flServerAddress = (FlowLayout)jpnlServerAddress.getLayout();
		flServerAddress.setAlignment(FlowLayout.LEFT);
		
		jchbTrusted = new JCheckBox("Trusted");
		
		jcmbLogMech = new JComboBox<>();
		jcmbLogMech.addItem("LDAP");
		jcmbLogMech.addItem("TD2");
		JLabel jlabLogMech = new JLabel("Механизм");
		JPanel jpnlLogMech = new JPanel();
		jpnlLogMech.add(jlabLogMech);
		jpnlLogMech.add(jcmbLogMech);
		FlowLayout flLogMech = (FlowLayout)jpnlLogMech.getLayout();
		flLogMech.setAlignment(FlowLayout.LEFT);
		
		
		jtfUser = new JTextField(10);
		JLabel jlabUser = new JLabel("Login");
		JPanel jpnlUser = new JPanel();
		jpnlUser.add(jlabUser);
		jpnlUser.add(jtfUser);
		FlowLayout flUser = (FlowLayout)jpnlUser.getLayout();
		flUser.setAlignment(FlowLayout.LEFT);
		
		
		jpswPassword = new JPasswordField(10);
		JLabel jlabPassword = new JLabel("Password");
		JPanel jpnlPassword = new JPanel();
		jpnlPassword.add(jlabPassword);
		jpnlPassword.add(jpswPassword);
		FlowLayout flPassword = (FlowLayout)jpnlPassword.getLayout();
		flPassword.setAlignment(FlowLayout.LEFT);
		
		
		JPanel jpnlServerSettings = new JPanel();
		jpnlServerSettings.setLayout(new GridLayout(6, 0));
		jpnlServerSettings.setPreferredSize(new Dimension(205, 205));
		jpnlServerSettings.add(jpnlServerType);
		jpnlServerSettings.add(jpnlServerAddress);
		jpnlServerSettings.add(jchbTrusted);
		jpnlServerSettings.add(jpnlLogMech);
		jpnlServerSettings.add(jpnlUser);
		jpnlServerSettings.add(jpnlPassword);
		jpnlServerSettings.setBorder(BorderFactory.createTitledBorder("Настройки сервера"));
		
		jtxtareaSQLText = new JTextArea(20,10);
		JScrollPane jscrpText = new JScrollPane(jtxtareaSQLText);
		JPanel jpnlSQLText = new JPanel();
		jpnlSQLText.setLayout(new GridLayout(1,0));
		jpnlSQLText.setPreferredSize(new Dimension(205, 100));
		jpnlSQLText.add(jscrpText);
		jpnlSQLText.setBorder(BorderFactory.createTitledBorder("SQL Text"));
		
		jpnlSrsControls.add(jpnlServerSettings);
		jpnlSrsControls.add(jpnlSQLText);
		jbtRefreshSQL = new JButton("Refresh");
		jpnlSrsControls.add(jbtRefreshSQL);
		jbtRefreshSQL.addActionListener(this);
		
		
		
	}
	@Override
	protected void prepareSrc(){
	
			srcData = new SQLSrcData();
	
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateSrcData() throws IOException, ClassNotFoundException, SQLException {
		SQLSrcData tmp =(SQLSrcData)srcData;
		tmp.updateData( jcmbServerType.getSelectedItem().toString(), jtfServerAddress.getText(), jcmbLogMech.getSelectedItem().toString() , jchbTrusted.isSelected(), jtfUser.getText(), jpswPassword.getPassword().toString(), jtxtareaSQLText.getText() );		
	}
	public String getServerType() {
		return jcmbServerType.getSelectedItem().toString();
	}
	public String getServerAddress() {
		return jtfServerAddress.getText();
	}
	
	public String getLogMech() {
		return jcmbLogMech.getSelectedItem().toString();
	}
	
	public boolean getTrusted() {
		return jchbTrusted.isSelected();
	}
	
	public String getUser() {
		return jtfUser.getText();
	}
	
	public String getPassword() {
		return jpswPassword.getPassword().toString();
	}
	
	public String getSQLText() {
		return jtxtareaSQLText.getText();
	}
}
