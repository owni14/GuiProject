package guiProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
public class MemberFindPw extends JFrame implements ActionListener{
	Container c = getContentPane();
	
	private JPanel pnlNorth = new JPanel();
	private JPanel pnlCenter = new JPanel();
	private JPanel pnlSouth = new JPanel();
	
	JTextField tfId = new JTextField(20);

	JButton btnCheck = new JButton("Check");
	JButton btnClose = new JButton("Close");
	
	MemberDao dao = MemberDao.getInstance();
	
	// Find pw windows
	public MemberFindPw() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Find Pw");
		setSize(400, 250);
		setUI();
		setVisible(true);
	} // MemberFindPw()

	// Set up of setUI
	private void setUI() {
		setNorth();
		setCenter();
		setSouth();
	} // setUI()

	// Add panel to the north of the container
	private void setNorth() {
		pnlNorth.setBackground(Color.WHITE);
		JLabel text = new JLabel("Find Password Window");
		text.setFont(new Font("Consolas", Font.BOLD, 30));
		pnlNorth.add(text);
		c.add(pnlNorth, BorderLayout.NORTH);
	} // setNorth()

	// Add panel to the center of the container
	private void setCenter() {
		pnlCenter.setLayout(new GridLayout(3, 4, 15, 15));
		pnlCenter.setBackground(Color.WHITE);
		
		JLabel lblId = new JLabel("Id");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		
		emptySpace();
		pnlCenter.add(lblId);
		pnlCenter.add(tfId);
		emptySpace();
		c.add(pnlCenter, BorderLayout.CENTER);
	} // setCenter()

	// Add panel to the south of the container
	private void setSouth() {
		pnlSouth.setLayout(new FlowLayout());
		pnlSouth.setBackground(Color.WHITE);
		btnCheck.addActionListener(this);
		btnClose.addActionListener(this);
		pnlSouth.add(btnCheck);
		pnlSouth.add(btnClose);
		c.add(pnlSouth, BorderLayout.SOUTH);
	} // setSouth()
	
	// Set up of the Action Listener
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == btnCheck) {
			String Id = tfId.getText().toString();
			
			// Throw id to the findPw method in MemberDao
			String pw = dao.findPw(Id);
			
			// Check the result from findPw method in MemberDao
			if (pw != null) {
				JOptionPane.showMessageDialog(this, "Your id password '" + pw + "'");
			} else {
				JOptionPane.showMessageDialog(this, "We can't find your password, please check your id" );
			}
		} else {
			dispose();
		}
	} // actionPerformed()
	
	private void emptySpace() {
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(new JLabel(""));
	} // emptySpace()

}
