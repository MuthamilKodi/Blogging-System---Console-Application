import java.sql.*;
public class ReplyComment extends Comment{
	
	private int reply_to;

	public ReplyComment(){
		
	}

	public ReplyComment(int cmt_id,int reply_to,int post_id,int auth_id,String cmt_text,Timestamp date){
		super(cmt_id,post_id,auth_id,cmt_text,date);
		this.reply_to=reply_to;
	}
	public void setCommentReplyToId(int reply_to){
		this.reply_to=reply_to;
	}
	public int getCommentReplyToId(){
		return reply_to;
	}
	/*public void showComment(){
		System.out.println("\n\t\t"+cmt_id+" - "+user_id+" - "+date+" - "+cmt_text);
			
	}*/
}