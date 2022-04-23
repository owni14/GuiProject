package guiProject;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class MemberList extends JFrame{
	Container c = getContentPane();
	
	JPanel pnlNorth  = new JPanel();
	JPanel pnlCenter = new JPanel();
	JPanel pnlSouth  = new JPanel();
	
	DefaultTableModel model;
	JTable table;
	Vector<String> vector;
	
	MemberDao dao = MemberDao.getInstance();
	
	// MemberList windows
	public MemberList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Member List");
		setSize(800, 400);
		setUI();
		setVisible(true);
	} // MemberList()

	// Set up of UI
	private void setUI() {
		setNorth();
		setCenter();
		setSouth();
	} // setUI()

	// Add panel to the north of the container
	private void setNorth() {
		JLabel text = new JLabel("Member List Window");
		text.setFont(new Font("Consolas", Font.BOLD, 20));
		pnlNorth.add(text);
		c.add(pnlNorth, BorderLayout.NORTH);
	} // setNorth()
	
	// Add panel to the center of the container
	private void setCenter() {
		pnlCenter.setLayout(new BorderLayout());
		String[] listInfo = {
				"Name", "Gender", "BirthDate", "Id", "Pw", "Tel", "Registration Date"
		};
		vector = new Vector<String>();
		for(int i = 0; i < 7; i++) {
			vector.add(listInfo[i]);
		}
		
		// Create Table
		model = new DefaultTableModel(vector, 0);
		table = new JTable(model);
		table.setFont(new Font("Dialog", Font.TRUETYPE_FONT, 15));
		JScrollPane sclPan = new JScrollPane(table);
		
		pnlCenter.add(sclPan);
		
		// Get data about Member list from MemberDao 
		List<MemberVo> list = dao.showData();
		
		// Each of data input a column and add next a row
		String[] info = new String[listInfo.length];
		for (MemberVo vo : list) {
			info[0] = vo.getMbname();
			info[1] = vo.getMbgender();
			info[2] = vo.getMbbthdate();
			info[3] = vo.getMbid();
			info[4] = vo.getMbpw();
			info[5] = vo.getMbtel();
			info[6] = String.valueOf(vo.getMbrgstdate());
			model.addRow(info);
		}
		c.add(pnlCenter, BorderLayout.CENTER);
	} // setNorth()

	// Add panel to the north of the container
	private void setSouth() {
		JButton btnClose = new JButton("Close");
		pnlSouth.add(btnClose);
		pnlSouth.setLayout(new FlowLayout());
		c.add(pnlSouth, BorderLayout.SOUTH);
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	} // setSouth()
	
}
