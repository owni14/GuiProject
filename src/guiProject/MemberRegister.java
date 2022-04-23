package guiProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MemberRegister extends JFrame implements ActionListener {
	private final int NAME = 0;
	private final int BIRTH = 1;
	private final int ID = 2;
	private final int PW = 3;
	private final int TEL = 4;
	
	private Container c = getContentPane();

	private MemberDao dao = MemberDao.getInstance();
	private MemberVo vo;

	private JPanel pnlNorth = new JPanel();
	private JPanel pnlCenter = new JPanel();
	private JPanel pnlSouth = new JPanel();

	private String[] registerInfo = { "Name", "Date of Birth", "Id", "Pw", "Tel" };
	private JLabel[] lblregisterInfo = new JLabel[registerInfo.length];
	private JTextField[] tfregisterInfo = new JTextField[registerInfo.length];

	private JRadioButton rdoMale = new JRadioButton("Male");
	private JRadioButton rdoFemale = new JRadioButton("Female");

	private JButton btnCheck = new JButton("Check");
	private JButton btnClose = new JButton("Close");


	// Register windows
	public MemberRegister() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Register");
		setSize(500, 400);
		setUI();
		setVisible(true);
	} // MemberRegister()

	// Set up of the setUI
	private void setUI() {
		setNorth();
		setCenter();
		setSouth();
	} // setUI()

	// Add Panel to the North of the Container
	private void setNorth() {
		pnlNorth.setBackground(Color.WHITE);
		JLabel text = new JLabel("Register Window");
		text.setFont(new Font("Consolas", Font.BOLD, 30));
		pnlNorth.add(text);
		c.add(pnlNorth, BorderLayout.NORTH);
	} // setNorth()

	// Add Panel to the Center of the Container
	private void setCenter() {
		pnlCenter.setLayout(new GridLayout(6, 4, 15, 15));
		pnlCenter.setBackground(Color.WHITE);
		rdoMale.setBackground(Color.WHITE);
		rdoFemale.setBackground(Color.WHITE);
		ButtonGroup btngroup = new ButtonGroup();
		btngroup.add(rdoMale);
		btngroup.add(rdoFemale);

		// GridLayout Setting
		for (int i = 0; i < registerInfo.length; i++) {
			// Make Texts
			lblregisterInfo[i] = new JLabel(registerInfo[i]);

			// Make TextFields
			tfregisterInfo[i] = new JTextField(30);
			lblregisterInfo[i].setHorizontalAlignment(SwingConstants.CENTER);
			pnlCenter.add(new JLabel(""));
			pnlCenter.add(lblregisterInfo[i]);
			pnlCenter.add(tfregisterInfo[i]);
			pnlCenter.add(new JLabel(""));
		}
		rdoMale.setHorizontalAlignment(SwingConstants.CENTER);
		rdoFemale.setHorizontalAlignment(SwingConstants.CENTER);
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(rdoMale);
		pnlCenter.add(rdoFemale);
		pnlCenter.add(new JLabel(""));
		c.add(pnlCenter);
	} // setCenter()

	// Add Panel to the South of the Container
	private void setSouth() {
		pnlSouth.setBackground(Color.WHITE);
		pnlSouth.add(btnCheck);
		pnlSouth.add(btnClose);
		btnCheck.addActionListener(this);
		btnClose.addActionListener(this);
		c.add(pnlSouth, BorderLayout.SOUTH);
	} // setSouth()

	// Set up of the Action Listener
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == btnCheck) {
			// Check TextField result
			Boolean getRs = getData();
			if (getRs == false) {
				return;
			}
			// Check insertResult
			Boolean insertRs = dao.insertData(vo);
			if (insertRs == true) {
				JOptionPane.showMessageDialog(this, "Completion of membership registration");
			} else {
				JOptionPane.showMessageDialog(this, "Fail of membership registration, Write all of text and selection that male or female");
			}
			dispose(); // return to native windows
		} else {
			dispose();
		}
	} // actionPerformed()

	// Get Data from each of TextFields
	private Boolean getData() {
		String[] get = new String[tfregisterInfo.length];

		// Get text from TextFields about name, birth, id, pw, tel
		for (int i = 0; i < get.length; i++) {
			get[i] = tfregisterInfo[i].getText();
		}
		String getGender = null;
		LocalDate now = LocalDate.now();
		Date today = Date.valueOf(now);

		if (rdoMale.isSelected()) {
			getGender = "³²";
		} else if (rdoFemale.isSelected()) {
			getGender = "¿©";
		}
		vo = new MemberVo(get[NAME], getGender, get[BIRTH], get[ID], get[PW], get[TEL], today);
		 
		// Check the id and tel because we don't allow to make duplication
		int checkDplId = dao.checkDuplicationTel(get[ID]);
		int checkDplTel = dao.checkDuplicationTel(get[TEL]);
		if (checkDplId == 1) {
			JOptionPane.showMessageDialog(this, "Exist same id");
			return false;
		} else if (checkDplTel == 2) {
			JOptionPane.showMessageDialog(this, "Exist same phone number");
			return false;
		}
		return true;
	} // getData()
	
}