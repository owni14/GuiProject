package guiProject;

import java.sql.Date;

public class MemberVo {
	private String mbname;
	private String mbgender;
	private String mbbthdate;
	private String mbid;
	private String mbpw;
	private String mbtel;
	private Date mbrgstdate;
	
	public MemberVo() {
		super();
	}
	
	public MemberVo(String mbid, String mbtel) {
		this.mbid = mbid;
		this.mbtel = mbtel;
	}
	
	

	public MemberVo(String mbname, String mbgender, String mbbthdate, String mbid, String mbpw, String mbtel) {
		super();
		this.mbname = mbname;
		this.mbgender = mbgender;
		this.mbbthdate = mbbthdate;
		this.mbid = mbid;
		this.mbpw = mbpw;
		this.mbtel = mbtel;
	}

	public MemberVo(String mbname, String mbgender, String mbbthdate, String mbid, String mbpw, String mbtel, Date mbrgstdate) {
		super();
		this.mbname = mbname;
		this.mbgender = mbgender;
		this.mbbthdate = mbbthdate;
		this.mbid = mbid;
		this.mbpw = mbpw;
		this.mbtel = mbtel;
		this.mbrgstdate = mbrgstdate;
	}

	public String getMbname() {
		return mbname;
	}

	public void setMbname(String mbname) {
		this.mbname = mbname;
	}

	public String getMbgender() {
		return mbgender;
	}

	public void setMbgender(String mbgender) {
		this.mbgender = mbgender;
	}

	public String getMbbthdate() {
		return mbbthdate;
	}

	public void setMbbthdate(String mbbthdate) {
		this.mbbthdate = mbbthdate;
	}

	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}

	public String getMbpw() {
		return mbpw;
	}

	public void setMbpw(String mbpw) {
		this.mbpw = mbpw;
	}

	public String getMbtel() {
		return mbtel;
	}

	public void setMbtel(String mbtel) {
		this.mbtel = mbtel;
	}

	public Date getMbrgstdate() {
		return mbrgstdate;
	}

	public void setMbrgstdate(Date mbrgstdate) {
		this.mbrgstdate = mbrgstdate;
	}

	@Override
	public String toString() {
		return "MemberVo [mbname=" + mbname + ", mbgender=" + mbgender + ", mbbthdate=" + mbbthdate + ", mbid=" + mbid
				+ ", mbpw=" + mbpw + ", mbtel=" + mbtel + ", mbcrtdate=" + mbrgstdate + "]";
	}
	
}
