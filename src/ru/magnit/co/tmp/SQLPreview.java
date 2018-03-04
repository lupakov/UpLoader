package ru.magnit.co.tmp;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SQLPreview extends Preview {
	private JComboBox<String> jcmbServerType;
	private JTextField jtfServerAddress;
	private JComboBox<String> jcmbLogMech;
	private JTextField jtfUser;
	private JPasswordField jpswPassword;
	private JCheckBox jchbTrusted;
	private JTextArea jtxtareaSQLText;
	
	public SQLPreview() {
		jcmbServerType = new JComboBox<String>();
		jcmbServerType.addItem("MS SQL Server");
		jcmbServerType.addItem("Teradata");
		JLabel jlabServerType = new JLabel("Тип сервера");
		JPanel jpnlServerType = new JPanel();
		jpnlServerType.add(jlabServerType);
		jpnlServerType.add(jcmbServerType);
		
		jtfServerAddress = new JTextField(10);
		
		
	}
	
}
