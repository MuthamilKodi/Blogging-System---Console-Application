import java.sql.*;
public class Comment{

	int cmt_id;
	int post_id;
	int user_id;
    String cmt_text;
	Timestamp date;

	public Comment(){
		
	}

	public Comment(int cmt_id,int post_id,int user_id,String cmt_text,Timestamp date){
		this.cmt_id=cmt_id;
		this.post_id=post_id;
		this.user_id=user_id;
		this.cmt_text=cmt_text;
		this.date=date;
		
	}

	public Comment(int user_id,String cmt_text,Timestamp date){
		this.user_id=user_id;
		this.cmt_text=cmt_text;
		this.date=date;
	}

	public int getAuthId(){
		return user_id;
	}
	public int getPostId(){
		return post_id;
	}
	public String getComment(){
		return cmt_text;
	}
	public int getCommentId(){
		return cmt_id;
	}
	public Timestamp getDate(){
		return date;
	}
	public void setCommentId(int cmt_id){
		this.cmt_id=cmt_id;
	}
	public void setComment(String cmt_text){
		this.cmt_text=cmt_text;
	}
	public void setAuthId(int user_id){
		this.user_id=user_id;
	}
	public void setDate(Timestamp date){
		this.date=date;
	}
	public void showComment(){
		System.out.println("\n\tc-"+cmt_id+" - "+user_id+" - "+date+" - "+cmt_text);
	}
}