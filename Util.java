import java.util.*;
import java.io.*;
import java.sql.*;
import java.time.Instant;
class Util{
	
	Scanner scan=new Scanner(System.in);
	DataInputStream d=new DataInputStream(System.in);
	CollectionData data=new CollectionData();
	Database dataObject=new Database();
	Connection conn=dataObject.connect();
		
	
	// public void init(){
	// 	data.init();
	// }

	public boolean isValidUserName(String userName){
		return userName.matches("^[a-zA-Z._\\s*-]{3,}$");
	}
	
	public boolean isValidEmailId(String emailId){
		return emailId.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@"+"(?:[a-zA-Z0-9-]+\\.)+[a-z"+"A-Z]{2,7}$");
	}
	
	public boolean isValidPassword(String password){
		return password.matches("^(?=.*[0-9])"+"(?=.*[a-z])(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$).{8,20}$");
	}
	public User signUp(){	
		boolean valid=true,validUser=true,validPhone=true,vaildEmailId=true,validPassword=true,validConfirmPassword=true;
		String userName="",phoneNumber="",emailId="",password="",confirmPassword="";
		while(valid){
			while (validUser) {
				System.out.print("\n\tEnter User Name : ");
				userName=scan.nextLine();			
				validUser=isValidUserName(userName);
				if(validUser)
					validUser=false;
				else{
					System.out.println("\n\tInvalid User Name");
					validUser=true;
				}
			}
		   while (vaildEmailId) {
				System.out.print("\n\tEnter Email ID : ");
				emailId=scan.nextLine();
				vaildEmailId=isValidEmailId(emailId);
				if(vaildEmailId){
					vaildEmailId=dataObject.verifyEmailId(emailId,conn);
					if(vaildEmailId)
						System.out.println("\n\tEmail ID is Already Registered");
				}
				else{
					System.out.println("\n\tInvalid Email ID");
					vaildEmailId=true;
				}
			}
			while (validPassword) {
				System.out.print("\n\tEnter Password : ");
				password=scan.nextLine();
				validPassword=isValidPassword(password);
				if(validPassword)
					validPassword=false;
				else{
					System.out.println("\n\tInvalid Password");
					validPassword=true;
				}
			}
			while (validConfirmPassword) {
				System.out.print("\n\tEnter Confirm Password : ");
				confirmPassword=scan.nextLine();
				validConfirmPassword=isValidPassword(password);
				if(validConfirmPassword)
					if(password.equals(confirmPassword))
						validConfirmPassword=false;
				else{
					System.out.println("\n\tInvalid Confirm Password");
					validConfirmPassword=true;
				}
			}
			if(!validUser && !vaildEmailId && !validPassword &&!validConfirmPassword)
				valid=false;
		}
		Timestamp instant=Timestamp.from(Instant.now());
		return new User(1,userName,emailId.toLowerCase(),password,instant);
	}

	public Boolean registerUser(User user){
		Boolean success=false;
		success=dataObject.registerUser(user,conn);
		return success;
	}
	
	public User signIn(){
		User CurrentUser=null;
		boolean valid=true,vaildEmailId=true;
		while (valid) {
			System.out.print("\n\tEnter Email ID : ");
			String email=scan.nextLine();
			System.out.print("\n\tEnter Password : ");
			String password=scan.nextLine();
			vaildEmailId=isValidEmailId(email);
			if(vaildEmailId){
				vaildEmailId=dataObject.verifyEmailId(email,conn);
				if(vaildEmailId){	
					CurrentUser=dataObject.getUser(email,conn);
					if(CurrentUser.getPassword().equals(password))
						valid=false;
					else{
						System.out.println("\n\tInvalid Email ID or Password");
						valid=isContinueOrBack();
					}
				}
				else{
					System.out.println("\n\tInvalid Email ID or Password");
					valid=isContinueOrBack();
				}
			}
			else{
				System.out.println("\n\tInvalid Email ID or Password");
				valid=isContinueOrBack();
			}
		}
		return CurrentUser;
	}

	public void setUserType(int user_id,int role_id){
		dataObject.setUserType(user_id,role_id,conn);
	}
	
	public Category createCategory(int auth_id) throws IOException{
		int authid=auth_id;
		String name,desc;
		System.out.println("Enter Category Name: ");
		name=d.readLine();
		System.out.println("Enter Category Description");
		desc=d.readLine();
		Category categ=new Category(1,auth_id,name,desc);
		return categ;
	}

	public Boolean verifyCategory(Category categ,int auth_id){
		Boolean status=true;
		for(Category category:dataObject.getUserCategories(auth_id,conn)){
			String name1,name2;
			name1=category.getCategoryName();
			name2=categ.getCategoryName();
			if(name1.equalsIgnoreCase(name2)){
				status=false;
				break;
			}
		}
		return status;
	}

	public void addCategory(Category categ){
		Boolean success=false;
		success=dataObject.addCategory(categ,conn);
		if(success){
			System.out.println("\tCAEGORY IS SUCCESSFULLY ADDED");
		}
		else
		{
			System.out.println("\tCAEGORY IS NOT ADDED SUCCESSFULLY");
		}
	}

	public void getUserCategories(int auth_id){
		for(Category categ:dataObject.getUserCategories(auth_id,conn)){
			System.out.println("\t"+categ.getCategoryId()+" - "+categ.getCategoryName()+" "+categ.getCategoryDesc());
		}
	}

	public Category getCategory(int id){
		Category categ=dataObject.getCategory(id,conn);
		return categ;
	}

	public void editCategory(Category categ) throws IOException{
		Boolean yn1;
		String name,desc;
		System.out.println("Do you want to edit category name? \n\tYes:true\n\t2.No:false");
		yn1=scan.nextBoolean();
		scan.nextLine();
		if(yn1){
			System.out.println("Enter category name: ");
			name=d.readLine();
			categ.setCategoryName(name);
		}
		System.out.println("Do you want to edit category description? \n\tYes:true\n\tno:false");
		yn1=scan.nextBoolean();
		scan.nextLine();
		if(yn1){
			System.out.println("Enter category description: ");
			desc=d.readLine();
			categ.setCategoryDesc(desc);
		}

		if(dataObject.editCategory(categ,conn))
			System.out.println("\tCATEGORY UPDATION\n\t"+categ.getCategoryId()+" - "+categ.getCategoryName()+" - "+categ.getCategoryDesc());
		else
			System.out.println("Category UPDATION not SUCCESSFULL");
		System.out.println("\t=====================================================");
	}

	public void deleteCategory(Category categ){
		if(dataObject.deleteCategory(categ,conn)){
			System.out.println("CATEGORY IS SUCCESSFULLY DELETED");
		}
		else {
			System.out.println("Try Again...");
		}
	}

	public Post createPost(int auth_id) throws IOException{
		int categ_id,authid=auth_id;
		String title,details;
		System.out.println("Enter Post Title: ");
		title=d.readLine();
		System.out.println("Choose the Category Id: ");
		getUserCategories(auth_id);
		categ_id=scan.nextInt();
		System.out.println("Enter Post Details: ");
		details=d.readLine();
		Timestamp instant=Timestamp.from(Instant.now());
		Post post=new Post(1,auth_id,categ_id,title,details,instant);
		return post;
	}

	public void addPost(Post post){
		Boolean success=false;
		success=dataObject.addPost(post,conn);
		if(success){
			System.out.println("\tPOST IS SUCCESSFULLY ADDED");
		}
		else
		{
			System.out.println("\tPOST IS NOT ADDED SUCCESSFULLY");
		}
	}

	public ArrayList<Post> getUserPosts(){
		ArrayList<Post> posts=new ArrayList<Post>();
		posts=dataObject.getPosts(conn);
		for(Post post:posts){
			Category categ=getCategory(post.getCategoryId());
			System.out.println(post.getPostId()+" - "+post.getPostTitle()+" - "+categ.getCategoryName());
		}
		return posts;
	}

	public ArrayList<Post> getUserPosts(int auth_id){
		ArrayList<Post> posts=new ArrayList<Post>();
		posts=dataObject.getUserPosts(auth_id,conn);
		for(Post post:posts){
			Category categ=data.getCategory(post.getCategoryId());
			System.out.println(post.getPostId()+" - "+post.getPostTitle()+" - "+categ.getCategoryName());
		}
		return posts;
	}

	public void showDemoPost(Post post){
		System.out.println("Post Id : "+post.getPostId());
		System.out.println("Post Title : "+post.getPostTitle());
		Category categ=getCategory(post.getCategoryId());
		System.out.println("Category : "+categ.getCategoryName());
	}

	public Post getPost(int id,ArrayList<Post> posts){
		Post post=new Post();
		for(Post pos:posts){
			if(pos.getPostId()==id){
				post=pos;
				break;
			}
		}
		return post;
	}

	public void editPost(Post post,int auth_id) throws IOException{
		Boolean yn1;
		String title,details;
		int categ_id;
		System.out.println("Do you want to edit Post Title? \n\tYes:true\n\t2.No:false");
		yn1=scan.nextBoolean();
		scan.nextLine();
		if(yn1){
			System.out.println("Enter post title: ");
			title=d.readLine();
			post.setPostTitle(title);
		}
		System.out.println("Do you want to edit category? \n\tYes:true\n\tno:false");
		yn1=scan.nextBoolean();
		scan.nextLine();
		if(yn1){
			getUserCategories(auth_id);
			System.out.println("Enter category Id: ");
			categ_id=scan.nextInt();
			post.setCategoryId(categ_id);
		}
		System.out.println("Do you want to edit Post Details? \n\tYes:true\n\t2.No:false");
		yn1=scan.nextBoolean();
		scan.nextLine();
		if(yn1){
			System.out.println("Enter post Details: ");
			details=d.readLine();
			post.setPostDetails(details);
		}
		categ_id=post.getCategoryId();
		Category category=dataObject.getCategory(categ_id,conn);
		if(dataObject.editPost(post,conn))
			System.out.println("\tPOST UPDATION\n\t"+post.getPostId()+" - "+post.getPostTitle()+" - "+category.getCategoryName()+" - "+post.getPostDetails());
		else
			System.out.println("POST UPDATION NOT SUCCESSFULL");
		System.out.println("\t=====================================================");
	}

	public void deletePost(Post post){
		if(dataObject.deletePost(post,conn)){
			System.out.println("POST IS SUCCESSFULLY DELETED");
		}
		else{
			System.out.println("Try Again...");
		}
	}

	public void showPost(int auth_id,Post post){
		System.out.println("Post Id: "+post.getPostId());
		System.out.println("Post Title : "+post.getPostTitle());
		Category categ=data.getCategory(post.getCategoryId());
		System.out.println("Category Name : "+categ.getCategoryName());
		System.out.println("Author Id : "+post.getAuthId());
		System.out.println("Post Details : "+post.getPostDetails());
		System.out.println("Date Created : "+post.getPostDate());
		System.out.println("Views : "+post.getPostViews());
		System.out.println("Likes : "+post.getPostLikes());
		System.out.println("Comments : "+post.getPostCommentCount());
		//ArrayList<Comment> cmts=getPostComments(post);
		ArrayList<ReplyComment> rcmts=getPostReplyComments(post);
		System.out.println("\tComments\n\t========");
		if(rcmts.isEmpty()){ 
			System.out.println("No Comments Yet");
		}
		else
		{
			ArrayList<Comment>cmts=new ArrayList<Comment>();
			ArrayList<ReplyComment>rcmtss=new ArrayList<ReplyComment>();
			for(ReplyComment rcmt:rcmts){
				if(rcmt.getCommentReplyToId()==0){
					Comment comment=rcmt;
					cmts.add(comment);
				}
				else if(rcmt.getCommentReplyToId()>0){
					rcmtss.add(rcmt);
				}
			}
			for(Comment cmt:cmts){
				cmt.showComment();
				for(ReplyComment rcmtt:rcmtss){
					if(cmt.getCommentId()==rcmtt.getCommentReplyToId()){
						System.out.println("\t\t");
						rcmtt.showComment();
					}
				}
			}
		}
		if(auth_id!=post.getAuthId()){
			post.viewFire();
			dataObject.viewFire(post,conn);
		}
	}

	public Comment createComment(int post_id,int auth_id) throws IOException{
	 	String username,cmt_text;
	    System.out.println("Enter the Comment: ");
	    cmt_text=d.readLine();
	    Timestamp instant=Timestamp.from(Instant.now());
	    Comment comment=new Comment(1,post_id,auth_id,cmt_text,instant);
	    return comment;
	}

	public void addComment(Comment comment){
		Boolean success=false;
		success=dataObject.addComment(comment,conn);
		if(success){
			System.out.println("\tCOMMENT IS SUCCESSFULLY ADDED");
		}
		else
		{
			System.out.println("\tCOMMENT IS NOT ADDED SUCCESSFULLY");
		}
	}

	public ReplyComment createReplyComment(int post_id,int auth_id) throws IOException{
	 	String username,cmt_text;
	    int reply_to;
	    System.out.println("Enter the comment Id to reply: ");
	    reply_to=scan.nextInt();
	    System.out.println("Enter the Reply Comment: ");
	    cmt_text=d.readLine();
	    Timestamp instant=Timestamp.from(Instant.now());
	    ReplyComment rcomment=new ReplyComment(1,reply_to,post_id,auth_id,cmt_text,instant);
	    return rcomment;
	}

	public void addReplyComment(ReplyComment rcomment){
		Boolean success=false;
		success=dataObject.addComment(rcomment,conn);
		if(success){
			System.out.println("\tReply COMMENT IS SUCCESSFULLY ADDED");
		}
		else
		{
			System.out.println("\tREPLY COMMENT IS NOT ADDED SUCCESSFULLY");
		}
	}


	

	/*public String getCategoryName(int categ_id){
		Category categ=data.getCategory(categ_id);
		if(categ.getCategoryId()!=0){
			return categ.getCategoryName();
		}
		else{
			return "Uncategorized";
		}
	}*/

	
	

	
	public ArrayList<Comment> getPostComments(Post post){
		return dataObject.getPostComments(post,conn);
	}

	public ArrayList<ReplyComment> getPostReplyComments(Post post){
		return dataObject.getPostReplyComments(post,conn);
	}

	public Boolean loginUser(User user){
		if(user==null){
			return false;
		}
		else{
			return true;
		}
	}

	public Boolean addBlogger(Blogger blogger,User user){
		return data.addBlogger(blogger,user);
	}

	public Boolean getUserLike(int postid,int userid){
		return dataObject.getUserLike(postid,userid,conn);
	}

	public void likeFire(Post post,int userid,Boolean status){
		Like like=new Like(post.getPostId(),userid);
		dataObject.likeFire(like,status,post,userid,conn);
	}

	public void fireComment(Post post){
		dataObject.fireComment(post,conn);
	}
	

	/*===============================================================================================*/
	Boolean isContinueOrBack(){
		Boolean status;
		System.out.println("Do You Want to continue(true) or back(false)");
		status=scan.nextBoolean();
		scan.nextLine();
		return status;
	}

}