import java.sql.*;
import java.util.*;
import java.time.Instant;

class Database{
	private final String url = "jdbc:postgresql://localhost:5432/blogsite";
    private final String user = "postgres";
    private final String password = "admin";

	private String sql;
	private PreparedStatement pstmt;
	Timestamp instant=Timestamp.from(Instant.now());

	
	public Connection connect() {
        Connection conn = null;
        try {
        	Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        }
        catch (ClassNotFoundException e) {
         	System.out.println("Couldn't load driver "+e.getMessage());
         } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
	
	public boolean verifyEmailId(String emailid,Connection conn){
		String sql="select user_id from user_details where email=?";
		Boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,emailid.toLowerCase());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
				status=true;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public User getUser(String emailid,Connection conn){
		String sql="select user_id,password,name,role_id from user_details where email=?";
		User user=new User();
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,emailid.toLowerCase());
			ResultSet rs=pstmt.executeQuery();
			rs.next();
			user.setUserType(rs.getInt(4));
			user.setUserId(rs.getInt(1));
			user.setUserPassword(rs.getString(2));
			user.setUserName(rs.getString(3));
			user.setUserEmail(emailid);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return user;
	}

	public boolean registerUser(User user,Connection conn){
		String sql="insert into user_details(email,password,name,role_id,created_date) values(?,?,?,?,?)";
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,user.getEmailId());
			pstmt.setString(2,user.getPassword());
			pstmt.setString(3,user.getUserName());
			pstmt.setInt(4,2);
			pstmt.setTimestamp(5,user.getDate());
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows==1){
				status=true;
			}
			else {
				status=false;
			}
			
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public void setUserType(int user_id,int role_id,Connection conn){
		String sql="update user_details set role_id=? where user_id=?";
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,role_id);
			pstmt.setInt(2,user_id);
			pstmt.executeUpdate();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<Category> getUserCategories(int auth_id,Connection conn){
		ArrayList<Category> categories=new ArrayList<Category>();
		String sql="select category_id,category_name,description from category where user_id=?";
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,auth_id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Category category=new Category(rs.getInt(1),auth_id,rs.getString(2),rs.getString(3));
				categories.add(category);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return categories;
	}

	public boolean addCategory(Category category,Connection conn){
		String sql="insert into category(user_id,category_name,description) values(?,?,?)";
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,category.getAuthId());
			pstmt.setString(2,category.getCategoryName());
			pstmt.setString(3,category.getCategoryDesc());
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows==1){
				status=true;
			}
			else {
				status=false;
			}
			
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public boolean addPost(Post post,Connection conn){
		boolean status=false;
		String sql="insert into post(user_id,category_id,title,content,sharelink,view_count,like_count,comment_count,created_date) values(?,?,?,?,?,?,?,?,?)";
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,post.getAuthId());
			pstmt.setInt(2,post.getCategoryId());
			pstmt.setString(3,post.getPostTitle());
			pstmt.setString(4,post.getPostDetails());
			pstmt.setObject(5,post.getPostUUID());
			pstmt.setInt(6,post.getPostViews());
			pstmt.setInt(7,post.getPostLikes());
			pstmt.setInt(8,post.getPostCommentCount());
			pstmt.setTimestamp(9,post.getPostDate());
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows==1){
				status=true;
			}
			else {
				status=false;
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public Category getCategory(int categ_id,Connection conn){
		String sql="select user_id,category_name,description from category where category_id=?";
		Category category=new Category();
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,categ_id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				category.setCategoryName(rs.getString(2));
				category.setUserId(rs.getInt(1));
				category.setCategoryDesc(rs.getString(3));
				category.setCategoryId(categ_id);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return category;

	}

	public boolean editCategory(Category category,Connection conn){
		String sql="update category set category_name=?,description=? where category_id=?";
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,category.getCategoryName());
			pstmt.setString(2,category.getCategoryDesc());
			pstmt.setInt(3,category.getCategoryId());
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows==1){
				status=true;
			}
			else {
				status=false;
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public boolean deleteCategory(Category category,Connection conn){
		String sql1="update post set category_id=? where category_id=?";
		String sql2="delete from category where category_id=?";
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql1);
			pstmt.setInt(1,0);
			pstmt.setInt(2,category.getCategoryId());
			pstmt.executeUpdate();
			pstmt=conn.prepareStatement(sql2);
			pstmt.setInt(1,category.getCategoryId());
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows==1){
				status=true;
			}
			else {
				status=false;
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public ArrayList<Post> getUserPosts(int auth_id,Connection conn){
		ArrayList<Post> posts=new ArrayList<Post>();
		String sql="select post_id,category_id,title,content,view_count,like_count,comment_count,created_date from post where user_id=?";
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,auth_id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Post post=new Post(rs.getInt(1),auth_id,rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getTimestamp(8));
				posts.add(post);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return posts;
	}

	public ArrayList<Post> getPosts(Connection conn){
		ArrayList<Post> posts=new ArrayList<Post>();
		String sql="select post_id,user_id,category_id,title,content,view_count,like_count,comment_count,created_date from post";
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Post post=new Post(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getTimestamp(9));
				posts.add(post);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return posts;
	}

	public boolean editPost(Post post,Connection conn){
		String sql="update post set category_id=?,title=?,content=? where post_id=?";
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,post.getCategoryId());
			pstmt.setString(2,post.getPostTitle());
			pstmt.setString(3,post.getPostDetails());
			pstmt.setInt(4,post.getPostId());
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows==1){
				status=true;
			}
			else {
				status=false;
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public boolean deletePost(Post post,Connection conn){
		String sql="delete from post where post_id=?";
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,post.getPostId());
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows==1){
				status=true;
			}
			else {
				status=false;
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public boolean addComment(Comment comment,Connection conn){
		String sql="insert into comment(user_id,post_id,reply_to,comment_text,date_created) values(?,?,?,?,?)";
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,comment.getAuthId());
			pstmt.setInt(2,comment.getPostId());
			pstmt.setInt(3,0);
			pstmt.setString(4,comment.getComment());
			pstmt.setTimestamp(5,comment.getDate());
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows==1){
				status=true;
			}
			else {
				status=false;
			}
			
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public boolean addComment(ReplyComment comment,Connection conn){
		String sql="insert into comment(user_id,post_id,reply_to,comment_text,date_created) values(?,?,?,?,?)";
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,comment.getAuthId());
			pstmt.setInt(2,comment.getPostId());
			pstmt.setInt(3,comment.getCommentReplyToId());
			pstmt.setString(4,comment.getComment());
			pstmt.setTimestamp(5,comment.getDate());
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows==1){
				status=true;
			}
			else {
				status=false;
			}
			
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public void fireComment(Post post,Connection conn){
		String sql="update post set comment_count=? where post_id=?";
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,post.getPostCommentCount());
			pstmt.setInt(2,post.getPostId());
			pstmt.executeUpdate();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public void viewFire(Post post,Connection conn){
		String sql="update post set view_count=? where post_id=?";
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,post.getPostViews());
			pstmt.setInt(2,post.getPostId());
			pstmt.executeUpdate();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public boolean getUserLike(int post_id,int user_id,Connection conn){
		String sql="select user_id from post_like where user_id=? and post_id=?";
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,user_id);
			pstmt.setInt(2,post_id);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				status=true;
			}
			else{
				status=false;
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public boolean likeFire(Like like,boolean likeValue,Post post,int user_id, Connection conn){
		String sql1="update post set like_count=? where post_id=?";
		String sql2="insert into post_like values(?,?)";
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql1);
			pstmt.setInt(1,post.getPostLikes());
			pstmt.setInt(2,post.getPostId());
			pstmt.executeUpdate();
			if(likeValue)
			{
				pstmt=conn.prepareStatement(sql2);
				pstmt.setInt(1,post.getPostId());
				pstmt.setInt(2,user_id);
				int affectedRows=pstmt.executeUpdate();
				if(affectedRows==1){
					status=true;
				}
				else {
					status=false;
				}
			}
			else
				status=deleteLike(post.getPostId(),user_id,conn);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public boolean deleteLike(int post_id,int user_id,Connection conn){
		String sql1="delete from post_like where post_id=? and user_id=?" ;
		boolean status=false;
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql1);
			pstmt=conn.prepareStatement(sql1);
			pstmt.setInt(1,post_id);
			pstmt.setInt(2,user_id);
			int affectedRows=pstmt.executeUpdate();
			if(affectedRows==1){
				status=true;
			}
			else {
				status=false;
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return status;
	}

	public ArrayList<Comment> getPostComments(Post post,Connection conn){
		String sql="select user_id,comment_text,date_created from comment where post_id=?";
		ArrayList<Comment> comments=new ArrayList<Comment>();
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,post.getPostId());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				Comment comment=new Comment(rs.getInt(1),rs.getString(2),rs.getTimestamp(3));
				comments.add(comment);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return comments;
	}

	public ArrayList<ReplyComment> getPostReplyComments(Post post,Connection conn){
		String sql="select comment_id,user_id,reply_to,comment_text,date_created from comment where post_id=?";
		ArrayList<ReplyComment> comments=new ArrayList<ReplyComment>();
		try{
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,post.getPostId());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				ReplyComment comment=new ReplyComment();
				comment.setCommentId(rs.getInt(1));
				comment.setAuthId(rs.getInt(2));
				comment.setComment(rs.getString(4));
				comment.setDate(rs.getTimestamp(5));
				comment.setCommentReplyToId(rs.getInt(3));
				comments.add(comment);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return comments;
	}



}
