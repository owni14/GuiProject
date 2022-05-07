package guiProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;

@SuppressWarnings("serial")
public class MemberLogin extends JFrame implements ActionListener{
	private final String ADMIN_ID = "admin"; 
	private final String ADMIN_PW = "admin1234";
	
	private Container c = getContentPane();
	
	private MemberDao dao = MemberDao.getInstance();
	
	private JPanel pnlNorth  = new JPanel();
	private JPanel pnlCenter = new JPanel();
	private JPanel pnlSouth  = new JPanel();
	
	private JTextField txtId = new JTextField(15);
	private JTextField txtPw = new JTextField(15);
	
	private JButton btnCheck    = new JButton("Login");
	private JButton btnRegister = new JButton("Register");
	private JButton btnFindId = new JButton("Find your Id");
	private JButton btnFindPw = new JButton("Find your Password");
	
	private JCheckBox adminMode   = new JCheckBox("Administrator Mode");
	
	// Login window
	public MemberLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Login");
		setSize(500,400);
		setUI();
		setVisible(true);
	} // MemberLogin()

	// Set up of UI
	private void setUI() {
		setNorth();
		setCenter();
		setSouth();
	} // setUI()

	// Add panel to north of container
	private void setNorth() {
		pnlNorth.setBackground(Color.WHITE);
		JLabel text = new JLabel("Login Window");
		text.setFont(new Font("Consolas", Font.BOLD, 30));
		pnlNorth.add(text);
		c.add(pnlNorth, BorderLayout.NORTH);
	} // setNorth()
	
	// Add panel to the center of the container
	private void setCenter() {
		pnlCenter.setBackground(Color.WHITE);
		pnlCenter.setLayout(new GridLayout(4, 4, 20, 30));
		JLabel lblId = new JLabel("ID");
		JLabel lblPw = new JLabel("PW");
		lblId.setFont(new Font("Consolas", Font.BOLD, 20));
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPw.setFont(new Font("Consolas", Font.BOLD, 20));
		lblPw.setHorizontalAlignment(SwingConstants.RIGHT);
		
		// Each of the buttons add to listener
		btnFindId.addActionListener(this);
		btnFindPw.addActionListener(this);
		
		// GridLayout setting 
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(lblId);
		pnlCenter.add(txtId);
		pnlCenter.add(btnFindId);
		pnlCenter.add(lblPw);
		pnlCenter.add(txtPw); 
		pnlCenter.add(btnFindPw);
		c.add(pnlCenter, BorderLayout.CENTER);
	} // setCenter()
	
	// Add panel to the south of the container
	private void setSouth() {
		pnlSouth.setBackground(Color.WHITE);
		adminMode.setBackground(Color.WHITE);
		btnCheck.setFont(new Font("Consolas", Font.BOLD, 20));
		btnRegister.setFont(new Font("Consolas", Font.BOLD, 20));
		adminMode.setFont(new Font("Consolas", Font.BOLD, 20));
		
		// Each of the buttons add to listener
		btnCheck.addActionListener(this);
		btnRegister.addActionListener(this);
		
		pnlSouth.add(btnCheck);
		pnlSouth.add(btnRegister);
		pnlSouth.add(adminMode);
		c.add(pnlSouth, BorderLayout.SOUTH);
	} // setSouth()
	
	// Set up of the Action Listener
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String id = txtId.getText();
		String pw = txtPw.getText();
		if (source == btnCheck) {
			if (adminMode.isSelected()) {
				// Admin login result
				if (id.equals(ADMIN_ID) && pw.equals(ADMIN_PW)) {
					JOptionPane.showMessageDialog(this, "Login has been completed for admin");
					MemberList list = new MemberList();
				} else {
					JOptionPane.showMessageDialog(this, "Fail to login for admin, Check admin id and password");
				}
			} else {
				// Login result gets true or false from MemberDao
				Boolean checkLogin = dao.checkLogin(id, pw);
				if (checkLogin) {
					if (id.equals(ADMIN_ID) && pw.equals(ADMIN_PW)) {
						// If you didn't check the administrator mode, we can alert you a message
						JOptionPane.showMessageDialog(this, "Select Administrator Mode");
					} else {
						JOptionPane.showMessageDialog(this, "Login has been completed for user");
						MemberWindow mbWindow = new MemberWindow();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Fail to login, Check your id and password");
				}
			}
		} else if (source == btnRegister) {
			// Convert login window into the Register window
			MemberRegister register = new MemberRegister();
		}  else if (source == btnFindId) {
			// Convert login window into the FindId window
			MemberFindId findId = new MemberFindId();
		} else if (source == btnFindPw) {
			// Convert login window into the FindPw window
			MemberFindPw findPw = new MemberFindPw();
		}
	} // actionPerformed()

	public static void main(String[] args) {
		new MemberLogin();
	} //main()
	
}