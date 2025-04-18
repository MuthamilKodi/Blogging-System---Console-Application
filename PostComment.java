import java.util.*;
public class PostComment{

	int cmt_id;
	int post_id;
	int user_id;
    String cmt_text;
	Date date;

	PostComment(int cmt_id,int post_id,int user_id,String cmt_text,Date date){
		this.cmt_id=cmt_id;
		this.post_id=post_id;
		this.user_id=user_id;
		this.cmt_text=cmt_text;
		this.date=date;
		
	}

	public int getPostId(){
		return post_id;
	}
	public int getCommentId(){
		return cmt_id;
	}
	public void showComment(){
		System.out.println("\n\tc-"+cmt_id+" - "+user_id+" - "+date+" - "+cmt_text);
	}
}