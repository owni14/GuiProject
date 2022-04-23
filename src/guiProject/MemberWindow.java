package guiProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MemberWindow extends JFrame implements ActionListener {
	Container c = getContentPane();

	MemberDao dao = MemberDao.getInstance();

	private JPanel pnlNorth = new JPanel();
	private JPanel pnlCenter = new JPanel();
	private JPanel pnlSouth = new JPanel();

	JButton btnUd = new JButton("Update");
	JButton btnWd = new JButton("Withdraw");
	JButton btnClose = new JButton("Close");

	JTextField tfPw = new JTextField(20);

	// Window for member
	public MemberWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Member Window");
		setSize(900, 400);
		setUI();
		setVisible(true);
	} // MemberWindow()

	private void setUI() {
		setNorth();
		setCenter();
		setSouth();
	} // setUI()

	private void setNorth() {
		pnlNorth.setBackground(Color.WHITE);
		JLabel text = new JLabel("Member Window");
		text.setFont(new Font("Consolas", Font.BOLD, 30));
		pnlNorth.add(text);
		c.add(pnlNorth, BorderLayout.NORTH);
	} // setNorth()

	private void setCenter() {
		pnlCenter.setLayout(null);
		pnlCenter.setBackground(Color.WHITE);
		JLabel welcomeText = new JLabel("WELCOME TO WINDOW FOR MEMBER!!!");
		welcomeText.setLocation(0, 62);
		JLabel welcomeText2 = new JLabel("Thank you for visit window for member");
		welcomeText2.setLocation(0, 104);
		JLabel announceInfo = new JLabel("If you want to withdraw or update your information");
		JLabel announceInfo2 = new JLabel("fill out below the blank and then click withdraw or update button");
		announceInfo2.setLocation(0, 170);
		announceInfo.setLocation(0, 146);

		welcomeText.setFont(new Font("Consolas", Font.PLAIN, 20));
		welcomeText2.setFont(new Font("Consolas", Font.PLAIN, 20));
		announceInfo.setFont(new Font("Consolas", Font.PLAIN, 20));
		announceInfo2.setFont(new Font("Consolas", Font.PLAIN, 20));

		welcomeText.setSize(882, 30);
		welcomeText2.setSize(882, 30);
		announceInfo.setSize(882, 30);
		announceInfo2.setSize(882, 30);

		welcomeText.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeText2.setHorizontalAlignment(SwingConstants.CENTER);
		announceInfo.setHorizontalAlignment(SwingConstants.CENTER);
		announceInfo2.setHorizontalAlignment(SwingConstants.CENTER);

		pnlCenter.add(welcomeText);
		pnlCenter.add(welcomeText2);
		pnlCenter.add(announceInfo);
		pnlCenter.add(announceInfo2);
		c.add(pnlCenter, BorderLayout.CENTER);
	} // setCenter()

	private void setSouth() {
		pnlSouth.setLayout(new FlowLayout());
		pnlSouth.setBackground(Color.WHITE);
		JLabel pw = new JLabel("pw");
		pnlSouth.add(pw);
		pnlSouth.add(tfPw);
		pnlSouth.add(btnUd);
		pnlSouth.add(btnWd);
		pnlSouth.add(btnClose);
		btnUd.addActionListener(this);
		btnWd.addActionListener(this);
		btnClose.addActionListener(this);
		c.add(pnlSouth, BorderLayout.SOUTH);
	} // setSouth()

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String pw = tfPw.getText().toString();
		if (source == btnUd) {
			MemberVo vo = dao.searchData(pw);
			MemberUpdate update = new MemberUpdate(vo);
		} else if (source == btnWd) {
			// Throw pw to the deleteData method in MemberDao
			Boolean deleteRs = dao.deleteData(pw);

			// Check the result from deleteData method in MemberDao
			if (deleteRs) {
				JOptionPane.showMessageDialog(this, "The withdrawal has been completed");
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "The withdrawal has been not completed, Check your password");
			}
		} else {
			dispose();
		}
	} // actionPerformed()

}
