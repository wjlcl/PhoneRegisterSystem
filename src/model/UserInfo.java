package model;

public class UserInfo {
	public final static String USERNAME = "userName";
	public final static String USERREALNAME = "userRealName";
	public final static String CHECKLEVEL = "checklevel";

	private String userName;
	private String userPwd;
	private String userRealName;
	private String checkLevel;
	private String userMobilePhone;
	private String userId;
	private String userEmail;

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getcheckLevel() {
		return checkLevel;
	}

	public void setcheckLevel(String checkLevel) {
		this.checkLevel = checkLevel;
	}

	public String getuserName() {
		return userName;
	}

	public void setuserName(String userName) {
		this.userName = userName;
	}

	public String getuserPwd() {
		return userPwd;
	}

	public void setuserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getuserMobilePhone() {
		return userMobilePhone;
	}

	public void setuserMobilePhone(String userMobilePhone) {
		this.userMobilePhone = userMobilePhone;
	}

	public String getuserId() {
		return userId;
	}

	public void setuserId(String userId) {
		this.userId = userId;
	}

	public String getuserEmail() {
		return userEmail;
	}

	public void setuserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
