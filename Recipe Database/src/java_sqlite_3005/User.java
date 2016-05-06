package java_sqlite_3005;

public class User {

	private String username ="mark";
	private String password ="a"; 
	private int    userID;
	
	public User(){}
	
	public String getUsername() {return username;}
	public String getPassword() {return password;}//Most secure 
	public int    getUserId()   {return userID;  }
	public void   setUsername(String u){username=u;}
	public void   setPassword(String p){password=p;}
	public void   setUserID(int i)     {userID  =i;}
}
