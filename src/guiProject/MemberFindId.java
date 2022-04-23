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
import javax.swing.plaf.metal.MetalMenuBarUI;

@SuppressWarnings("serial")
public class MemberFindId extends JFrame implements ActionListener{
	Container c = getContentPane();
	
	private JPanel pnlNorth = new JPanel();
	private JPanel pnlCenter = new JPanel();
	private JPanel pnlSouth = new JPanel();
	
	JTextField tfName = new JTextField(10);
	JTextField tfBirth = new JTextField(10);
	JTextField tfTel = new JTextField(20);

	JButton btnCheck = new JButton("Check");
	JButton btnClose = new JButton("Close");
	
	MemberDao dao = MemberDao.getInstance();
	
	// Find id windows
	public MemberFindId() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Find Id");
		setSize(400, 300);
		setUI();
		setVisible(true);
	} // MemberFindId()

	// Set up of setUI
	private void setUI() {
		setNorth();
		setCenter();
		setSouth();
	} // setUI()

	// Add panel to the north of the container
	private void setNorth() {
		pnlNorth.setBackground(Color.WHITE);
		JLabel text = new JLabel("Find Id Window");
		text.setFont(new Font("Consolas", Font.BOLD, 30));
		pnlNorth.add(text);
		c.add(pnlNorth, BorderLayout.NORTH);
	} // setNorth()

	// Add panel to the center of the container
	private void setCenter() {
		pnlCenter.setLayout(new GridLayout(5, 4, 15, 15));
		pnlCenter.setBackground(Color.WHITE);
		
		JLabel lblName = new JLabel("Name");
		JLabel lblBirth = new JLabel("Date of birth");
		JLabel lblTel = new JLabel("Tel");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBirth.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		emptySpace();
		pnlCenter.add(lblName);
		pnlCenter.add(tfName);
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(lblBirth);
		pnlCenter.add(tfBirth);
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(new JLabel(""));
		pnlCenter.add(lblTel);
		pnlCenter.add(tfTel);
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
			String name = tfName.getText().toString();
			String birth = tfBirth.getText().toString();
			String tel = tfTel.getText().toString();
			
			// Throw texts to the findId method in MemberDao
			String id = dao.findId(name, birth, tel);
			
			// Check the result from findId method in MemberDao
			if (id != null) {
				JOptionPane.showMessageDialog(this, "Your id is '" + id + "'");
			} else {
				JOptionPane.showMessageDialog(this, "We can't find your id" );
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
