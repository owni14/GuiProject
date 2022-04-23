package guiProject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {

	// Set up of singleton
	private static MemberDao instance = new MemberDao();
	private MemberDao() { /* singleton */ }
	public static MemberDao getInstance() {
		return instance;
	}

	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String ID = "c##membermanager";
	private final String PW = "1234";
	
	// Query 
	private final String LOGIN = "select * from member where mbid = ? and mbpw = ?";
	private final String CHECK_ID = "select * from member where mbid = ?";
	private final String CHECK_TEL = "select * from member where mbtel = ?";
	private final String INSERT = "insert into member (mbname, mbbthdate, mbid, mbpw, mbtel, mbgender, mbrgstdate)"
								+ " values(?, ?, ?, ?, ?, ?, ?)";
	private final String FIND_ID = "select * from member where mbname =? and mbbthdate = ? and mbtel = ?";
	private final String FIND_PW = "select * from member where mbid = ?";
	private final String DELETE_DATA = "delete from member where mbpw = ?";
	private final String SEARCH_DATA = "select * from member where mbpw = ?";
	private final String UPDATE_DATA = "update member set"
									 + " mbname = ?, mbbthdate = ?, mbpw = ?, mbtel = ?, mbgender = ?"
									 + " where mbid = ?";
	
	private final int DUPLICATE_ID_RESULT = 1;
	private final int DUPLICATE_TEL_RESULT = 2;

	// Connect Oracle to Java 
	private Connection getConnection() {
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, ID, PW);
			// System.out.println("conn:" + conn);
			return conn;
		} catch (Exception e) {
			e.printStackTrace(); // Trace Error
		}
		return null;

	} // getConnection()

	private void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		if (conn != null) try { conn.close(); } catch (Exception e) { };
		if (pstmt != null) try { pstmt.close(); } catch (Exception e) { };
		if (rs != null) try { rs.close(); } catch (Exception e) { };
	} // closeAll()

	public List<MemberVo> showData() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberVo> list = new ArrayList<>();
		try {
			conn = getConnection();
			String sql = "select * from member";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String mbname = rs.getString("mbname");
				String mbgender = rs.getString("mbgender");
				String mbbthdate = rs.getString("mbbthdate");
				String mbid = rs.getString("mbid");
				String mbpw = rs.getString("mbpw");
				String mbtel = rs.getString("mbtel");
				Date mbrgstdate = rs.getDate("mbrgstdate");

				/**
				* String data = mbname + " | ";
				* data += mbgender + " | ";
				* data += mbbthdate + " | ";
				* data += mbid + " | ";
				* data += mbpw + " | ";
				* data += mbtel + " | ";
				* data += mbrgstdate;
				* System.out.println(data);
				*/
				
				MemberVo vo = new MemberVo(mbname, mbgender, mbbthdate, mbid, mbpw, mbtel, mbrgstdate);
				list.add(vo);
			} // while()
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	} // showData()

	public Boolean insertData(MemberVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, vo.getMbname());
			pstmt.setString(2, vo.getMbbthdate());
			pstmt.setString(3, vo.getMbid());
			pstmt.setString(4, vo.getMbpw());
			pstmt.setString(5, vo.getMbtel());
			pstmt.setString(6, vo.getMbgender());
			pstmt.setDate(7, vo.getMbrgstdate());
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			return false;
		} finally {
			closeAll(conn, pstmt, null);
		}
		return false;
	} // insertInfo()

	public int checkDuplicationId(String id) {
		/**
		* Get number from checkIdPw method
		* if we get number 1, there's existed to duplicate id
		*/
		int resultNum = checkIdPW(CHECK_ID, id, DUPLICATE_ID_RESULT);
		
		return resultNum;
	} // checkDuplicationId

	public int checkDuplicationTel(String tel) {
		/**
		* Get number from checkIdPw method 
		* if we get number 2, there's existed to duplicate tel
		*/
		int resultNum = checkIdPW(CHECK_TEL, tel, DUPLICATE_TEL_RESULT);
		return resultNum;
	} // checkDuplicationTel

	public Boolean checkLogin(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(LOGIN);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return false;
	} // checkLogin

	private int checkIdPW(String sql, String getData, int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, getData);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return num;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return 0;
	} // checkIdPw()
	
	// Find id from the database
	public String findId(String name, String birth, String tel) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(FIND_ID);
			pstmt.setString(1, name);
			pstmt.setString(2, birth);
			pstmt.setString(3, tel);
			rs = pstmt.executeQuery();
			
			String id = null;
			while (rs.next()) {
				 id = rs.getString("mbid");
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	} // findId()
	
	// Find password from database
	public String findPw(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(FIND_PW);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			String pw = null;
			while (rs.next()) {
				pw = rs.getString("mbpw");
			}
			return pw;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	} // findPw()
	
	public Boolean deleteData(String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(DELETE_DATA);
			pstmt.setString(1, pw);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
		return false;
	} // deleteData()
	
	public MemberVo searchData(String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SEARCH_DATA);
			pstmt.setString(1, pw);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String mbname = rs.getString("mbname");
				String mbbthdate = rs.getString("mbbthdate");
				String mbid = rs.getString("mbid");
				String mbpw = rs.getString("mbpw");
				String mbtel = rs.getString("mbtel");
				String mbgender = rs.getString("mbgender");
				MemberVo vo = new MemberVo(mbname, mbgender, mbbthdate, mbid, mbpw, mbtel);
				return vo;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	} // searchData()	
	
	public boolean updateData(MemberVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(UPDATE_DATA);
			pstmt.setString(1, vo.getMbname());
			pstmt.setString(2, vo.getMbbthdate());
			pstmt.setString(3, vo.getMbpw());
			pstmt.setString(4, vo.getMbtel());
			pstmt.setString(5, vo.getMbgender());
			pstmt.setString(6, vo.getMbid());
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
		return false;
	} // updateData()
	
}
