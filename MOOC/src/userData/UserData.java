package userData;

import android.app.Activity;

public class UserData extends Activity {
	private String userName;
	private String userPwd;
	private String userEmail;
	private int userId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	public String getUserEmail(){
		return userEmail;
	}
	
	public void setUserEmail(String userEmail){
		this.userEmail=userEmail;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserData(String userName, String userPwd, String userEmail,int userId) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
		this.userEmail=userEmail;
		this.userId = userId;
	}

	public UserData(String userName, String userPwd,String userEmail) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
		this.userEmail=userEmail;
	}
}
