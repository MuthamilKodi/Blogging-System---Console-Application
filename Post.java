import java.sql.*;
import java.io.*;
import java.util.*;
class Post{
	//CollectionData data=new CollectionData();
		
	private int post_id;
	private int auth_id;
	private int categ_id;
	private String post_title;
	private String post_details;
	private Timestamp date;
	private int view;
	private int like;
	private int comment;
	private UUID uuid;
	 

	Post(){

	}
	
	Post(int post_id,int auth_id,int categ_id,String post_title,String post_details,Timestamp date){
		this.post_id=post_id;
		this.auth_id=auth_id;
		this.categ_id=categ_id;
		this.post_title=post_title;
		this.post_details=post_details;
		this.date=date;
		this.uuid=UUID.randomUUID();
	}

	Post(int post_id,int auth_id,int categ_id,String post_title,String post_details,int view,int like,int commment,Timestamp date){
		this.post_id=post_id;
		this.auth_id=auth_id;
		this.categ_id=categ_id;
		this.post_title=post_title;
		this.post_details=post_details;
		this.date=date;
		this.view=view;
		this.like=like;
		this.comment=comment;
	}

	public int getPostId(){
		return post_id;
	}

	public UUID getPostUUID(){
		return uuid;

	}

	public int getCategoryId(){
		return categ_id;
	}

	public int getAuthId(){
		return auth_id;
	}

	public String getPostTitle(){
		return post_title;
	}

	public String getPostDetails(){
		return post_details;
	}
	public Timestamp getPostDate(){
		return date;
	}
	public int getPostViews(){
		return view;
	}
	public int getPostLikes(){
		return like;
	}
	public int getPostCommentCount(){
		return comment;
	}
	
	public void setPostTitle(String title){
		post_title=title;
	} 
	public void setPostDetails(String details){
		post_details=details;
	}
	public void setCategoryId(int id){
		categ_id=id;
	}
	public void likeFire(int no){
		like+=no;
	}
	public void fireComment(){
		comment++;
	}
	public void shareFire(){
		
	}
	public void viewFire(){
		view++;
	}
}