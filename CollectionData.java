import java.util.*;
class CollectionData{
	
	private ArrayList<Category> categories=new ArrayList<Category>();
	private ArrayList<Post> posts=new ArrayList<Post>();
	private ArrayList<Comment> comments=new ArrayList<Comment>();
	private ArrayList<ReplyComment> reply_comments=new ArrayList<ReplyComment>();
	private ArrayList<User> users=new ArrayList<User>();
	private ArrayList<Blogger> bloggers=new ArrayList<Blogger>();
	private ArrayList<Like> likes=new ArrayList<Like>();
	private int last_categ_id=1;
	private int last_post_id=1;
	private int last_comment_id=1;
	private int last_user_id=2;
	int i;
	
	/*public void init(){
		User user=new User(1,"Alagu","alagu121@gmail.com","Alagu121#",new Date());
		User user2=new User(2,"Bama","bama121@gmail.com","Bama121#",new Date());
		users.add(user);
		users.add(user2);
		user.show();
	}*/

	public int getLastCategoryId(){
		return last_categ_id;
	}

	public int getLastPostId(){
		return last_post_id;
	}

	public int getLastCommentId(){
		return last_comment_id;
	}

	public int getLastUserId(){
		return last_user_id;
	}

	public User getUser(String email){
		email=email.toLowerCase();
		User usr=new User();
		for(User user:users){
			if(user.getEmailId().equals(email)){
				usr=user;
				break;
			}
		}
		return usr;
	}

	public Boolean registerUser(User user){
		Boolean status=false;
		status=users.add(user);
		last_user_id++;
		return status;
	}

	public Boolean addBlogger(Blogger blogger,User user){
		if(bloggers.add(blogger)){
			for(User use:users){
				if(use.getUserId()==user.getUserId()){
					use.setUserType(User.Type.BLOGGER);
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	public Boolean verifyEmailId(String email){
		Boolean status=false;
		for(User user:users){
			if(user.getEmailId().equals(email.toLowerCase())){
				status=true;
				break;
			}
		}
		return status;
	}

	public ArrayList<Category> getCategories(){
		return categories;
	}

	public ArrayList<Category> getUserCategories(int auth_id){
		ArrayList<Category> categ=new ArrayList<Category>();
		for(Category cat:categories){
			if(cat.getAuthId()==auth_id){
				categ.add(cat);
			}
		}
		return categ;
	}

	public ArrayList<Post> getPosts(){
		for(Post post:posts){
			Category categ=getCategory(post.getCategoryId());
			//System.out.println(post.getPostId()+" - "+post.getPostTitle()+" - "+categ.getCategoryName());
		}
		return posts;
	}

	public ArrayList<Post> getUserPosts(int auth_id){
		ArrayList<Post> pos=new ArrayList<Post>();
		for(Post post:posts){
			if(post.getAuthId()==auth_id){
				pos.add(post);
			}
		}
		return pos;
	}

	public ArrayList<Comment> getPostComments(Post post){
		ArrayList<Comment> cmts=new ArrayList<Comment>();
		for(Comment cmt:comments){
			if(cmt.getPostId()==post.getPostId()){
				cmts.add(cmt);
			}
		}
		return cmts;
	}

	public ArrayList<ReplyComment> getPostReplyComments(Post post){
		ArrayList<ReplyComment> rcmts=new ArrayList<ReplyComment>();
		for(ReplyComment rcmt:reply_comments){
			if(rcmt.getPostId()==post.getPostId()){
				rcmts.add(rcmt);
			}
		}
		return rcmts;
	}

	public Category getCategory(int id){
		Category category=new Category();
		for(Category categ:categories){
			if(categ.getCategoryId()==id){
				category=categ;
				break;
			}
		}
		return category;
	}
	
	public Post getPost(int id){
		Post post=new Post();
		for(Post pos:posts){
			if(pos.getPostId()==id){
				post=pos;
				break;
			}
		}
		return post;
	}

	public Boolean getUserLike(int post_id,int user_id){
		Boolean status=false;
		for(Like like:likes){
			if((like.getPostId()==post_id)&&(like.getUserId()==user_id)){
				status=true;
				break;
			}
		}
		return status;
	}

	public void likeFire(Like like,Boolean status){
		if(status){
			likes.add(like);
		}
		else{
			likes.remove(like);
		}
	}

	public Boolean addCategory(Category categ){
		Boolean success=categories.add(categ);
		last_categ_id++;
		return success;
	}

	public Boolean addPost(Post post){
		Boolean success=posts.add(post);
		last_post_id++;
		return success;
	}

	public Boolean addComment(Comment comment){
		Boolean success=comments.add(comment);
		last_comment_id++;
		return success;
	}

	public Boolean addReplyComment(ReplyComment rcomment){
		Boolean success=reply_comments.add(rcomment);
		last_comment_id++;
		return success;
	}



	/*public void editCategory(Category categ){
		for(i=0;i<categories.size();i++){
			Category category=categories.get(i);
			if(category.getCategoryId()==categ.getCategoryId()){
				categories.remove(i);
				categories.add(i,categ);
			}
		}
	}

	public void editPost(Post post){
		for(i=0;i<posts.size();i++){
			Post pos=posts.get(i);
			if(pos.getPostId()==post.getPostId()){
				posts.remove(i);
				posts.add(i,pos);
			}
		}
	}*/

	public Boolean deleteCategory(Category categ){
		if(categories.remove(categ)){
			//this.changeToUncategory(categ.getCategoryId());
			return true;
		}
		else {
			return false;
		}
	}

	public Boolean deletePost(Post post){
		if(posts.remove(post)){
			changeToUncategory(post.getCategoryId());
			System.out.println(post.getCategoryId());
			return true;
		}
		else {
			return false;
		}
	}

	public void changeToUncategory(int categ_id){
		for(i=0;i<posts.size();i++){
			Post post=posts.get(i);
			if(post.getCategoryId()==categ_id){
				post.setCategoryId(0);
				System.out.println(post.getCategoryId());
				//posts.remove(i);
				//posts.add(i,post);
			}
		}
	}

	/*=====================================================================================================*/

	public void displayPosts(){

	}

}