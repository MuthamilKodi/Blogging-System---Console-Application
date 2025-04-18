import java.sql.*;

class User{
	private int user_id;
	private String username;
	private String email;
	private String password;
	private Timestamp date;
	private Type user_type; 
	
	public enum Type{
		ADMIN,
		VIEWER,
		BLOGGER
	}

	public User(){

	}

	public User(int user_id,String username,String email,String password,Timestamp date){
		this.user_id=user_id;
		this.username=username;
		this.email=email;
		this.password=password;
		this.date=date;
		this.user_type=Type.ADMIN;
	}

	// public User(int user_id,String password,String name,int role_id,String email){
	// 	this.user_id=user_id;
	// 	this.username=name;
	// 	this.email=email;
	// 	this.password=password;
	// 	this.user_type=Type.ADMIN;		
	// }

	public int getUserId(){
		return user_id;
	}

	public String getEmailId(){
		return email;
	}

	public String getPassword(){
		return password;
	}

	public String getUserName(){
		return username;
	}

	public Timestamp getDate(){
		return date;
	}

	public int getUserType(){
		int type=0;
		switch (this.user_type){
		case ADMIN:
			type=1;
			break;
		case VIEWER:
			type=2;
			break;
		case BLOGGER:
			type=3;
			break;
		}
		return type;
	}

	public void setUserType(Type user_type){
		this.user_type=user_type;
	}

	public void setUserType(int user_type){
		switch (user_type){
		case 1:
			this.user_type=Type.ADMIN;
			break;
		case 2:
			this.user_type=Type.VIEWER;
			break;
		case 3:
			this.user_type=Type.BLOGGER;
			break;
		}
	}

	public void setUserId(int user_id)
	{
		this.user_id=user_id;
	}
	public void setUserName(String username){
		this.username=username;
	}
	public void setUserPassword(String password){
		this.password=password;
	}
	public void setUserEmail(String email){
		this.email=email;
	}
	public void show(){
		System.out.println(this.user_id+this.username);
	}
}