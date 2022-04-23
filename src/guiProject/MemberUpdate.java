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
public class MemberUpdate extends JFrame implements ActionListener {
	private Container c = getContentPane();
	
	private final int NAME = 0;
	private final int BIRTH = 1;
	private final int ID = 2;
	private final int PW = 3;
	private final int TEL = 4;
	
	MemberDao dao = MemberDao.getInstance();
	MemberVo vo;

	private JPanel pnlNorth = new JPanel();
	private JPanel pnlCenter = new JPanel();
	private JPanel pnlSouth = new JPanel();

	private String[] updateInfo = { "Name", "Date of Birth", "Id", "Pw", "Tel" };
	private JLabel[] lblupdateInfo = new JLabel[updateInfo.length];
	private JTextField[] tfUpdateInfo = new JTextField[updateInfo.length];
	private String existingTel;
	
	private JRadioButton rdoMale = new JRadioButton("Male");
	private JRadioButton rdoFemale = new JRadioButton("Female");

	private JButton btnCheck = new JButton("Check");
	private JButton btnClose = new JButton("Close");

	// Update windows
	public MemberUpdate(MemberVo vo) {
		this.vo = vo;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Update");
		setSize(500, 400);
		setUI();
		setVisible(true);
	}
	// Set up of the setUI
	private void setUI() {
		setNorth();
		setCenter();
		setSouth();
	}

	// Add Panel to the North of the Container
	private void setNorth() {
		pnlNorth.setBackground(Color.WHITE);
		JLabel text = new JLabel("Update Window");
		text.setFont(new Font("Consolas", Font.BOLD, 30));
		pnlNorth.add(text);
		c.add(pnlNorth, BorderLayout.NORTH);
	} // setNorth()

	// Add Panel to the Center of the Container
	private void setCenter() {
		String[] memberInfo = { vo.getMbname(), vo.getMbbthdate(), vo.getMbid(), vo.getMbpw(), vo.getMbtel()};
		pnlCenter.setLayout(new GridLayout(6, 4, 15, 15));
		pnlCenter.setBackground(Color.WHITE);
		rdoMale.setBackground(Color.WHITE);
		rdoFemale.setBackground(Color.WHITE);
		ButtonGroup btngroup = new ButtonGroup();
		btngroup.add(rdoMale);
		btngroup.add(rdoFemale);

		// GridLayout Setting
		for (int i = 0; i < updateInfo.length; i++) {
			// Make Texts
			lblupdateInfo[i] = new JLabel(updateInfo[i]);

			// Make TextFields
			tfUpdateInfo[i] = new JTextField(memberInfo[i]);
			lblupdateInfo[i].setHorizontalAlignment(SwingConstants.CENTER);
			pnlCenter.add(new JLabel(""));
			pnlCenter.add(lblupdateInfo[i]);
			pnlCenter.add(tfUpdateInfo[i]);
			pnlCenter.add(new JLabel(""));
		}
		existingTel = tfUpdateInfo[TEL].getText().toString();
		tfUpdateInfo[ID].setEditable(false);
		rdoMale.setHorizontalAlignment(SwingConstants.CENTER);
		rdoFemale.setHorizontalAlignment(SwingConstants.CENTER);
		pnlCenter.add(new JLabel(""));
		
		if (vo.getMbgender().equals("³²")) {
			rdoMale.setSelected(true);
		} else {
			rdoFemale.setSelected(true);
		}
		
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
			Boolean getRs = getData();
			if (getRs == false) {
				return;
			}
			// Check updateResult
			Boolean updateRs = dao.updateData(vo);
			if (updateRs == true) {
				JOptionPane.showMessageDialog(this, "Complete update");
			} else {
				JOptionPane.showMessageDialog(this, "Fail to update");
			}
			dispose();
		} else {
			dispose();
		}
	} // actionPerformed()
	
	// Get Data from each of TextFields
		private Boolean getData() {
			String[] get = new String[tfUpdateInfo.length];
			String getGender = null;

			// Get text from TextFields about name, birth, id, pw, tel
			for (int i = 0; i < get.length; i++) {
				get[i] = tfUpdateInfo[i].getText();
			}
			
			if (rdoMale.isSelected()) {
				getGender = "³²";
			} else if (rdoFemale.isSelected()) {
				getGender = "¿©";
			}
			vo = new MemberVo(get[NAME], getGender, get[BIRTH], get[ID], get[PW], get[TEL]);
			
			// Check the id and tel because we don't allow to make duplication
			int checkDplId = dao.checkDuplicationTel(get[ID]);
			int checkDplTel = 0;
			
			if (!(tfUpdateInfo[TEL].getText().equals(existingTel))) {
				checkDplTel = dao.checkDuplicationTel(get[TEL]);
			}
			
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
