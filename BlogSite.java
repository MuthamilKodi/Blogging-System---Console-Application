import java.io.*;
import java.util.*;
class BlogSite{

	static Scanner scan=new Scanner(System.in);
	static Util util=new Util();
	
	static void repeat(int auth_id) throws IOException{
		int choice2=1;
		while(choice2!=3){
			System.out.println("\t1.Dashboard");
			System.out.println("\t2.Display Posts");
			System.out.println("\t3.Logout");
			choice2=scan.nextInt();
			if(choice2==1)//Dashboard
			 	dashboard(auth_id);
			else if(choice2==2)
				displayPosts(auth_id);
		}
	}
	static void dashboard(int auth_id) throws IOException{
	    int choice=1;
		while(choice!=5){
			System.out.println("\t=============");
			System.out.println("\tDashboard");
			System.out.println("\t=============");
			System.out.println("\t1.Add Category");
			System.out.println("\t2.Add Post");
			System.out.println("\t3.Manage Categories");
			System.out.println("\t4.Manage Posts");
			System.out.println("\t5.Back");
			System.out.println("\nEnter your choice");
			choice=scan.nextInt();
			switch(choice){
			case 1://Add category
				Category categ=util.createCategory(auth_id);
				if(util.verifyCategory(categ,auth_id)==true){
					util.addCategory(categ);
				}
				else{
					System.out.println("\t"+categ.getCategoryName()+" IS ALREADY EXIST...TRY ANOTHER NAME");
				}
				break;
			case 2://Add post
				Post post=util.createPost(auth_id);
				util.addPost(post);
				break;
			case 3://Mansge Categories
				int choice1=1;
				while (choice1!=3) {
					System.out.println("\tMANAGE CATEGORIES\n\t==============\n\tID-NAME-DESCRIPTION");
					util.getUserCategories(auth_id);
					int categ_id;
					System.out.println("Choose the categoryId: ");
					categ_id=scan.nextInt();
					Category category=util.getCategory(categ_id);
					System.out.println("\t======");
					System.out.println("\t1.Edit");
					System.out.println("\t2.Delete");
					System.out.println("\t3.Back\n\t======");
					choice1=scan.nextInt();
					switch(choice1){
					case 1://edit category
						util.editCategory(category);
						break;
					case 2://delete category
						Boolean yn;
						System.out.println("Do you want to delete the category : "+category.getCategoryName());
						yn=scan.nextBoolean();
						if(yn){
							util.deleteCategory(category);
						}
						break;
					}
				}
				break;
			case 4://Manage Posts
				int choice5=1;
				while (choice5!=4) {
					System.out.println("\tMANAGE POSTS\n\t==============\n\tID-TITLE-CATEGORY");
					ArrayList<Post> posts=util.getUserPosts(auth_id);
					int post_id;
					System.out.println("Choose the PostId: ");
					post_id=scan.nextInt();
					post=util.getPost(post_id,posts);
					System.out.println("\t======");
					System.out.println("\t1.Edit");
					System.out.println("\t2.Delete");
					System.out.println("\t3.View");
					System.out.println("\t4.Back\n\t======");
					choice5=scan.nextInt();
					switch(choice5){
						case 1://edit post
							util.editPost(post,auth_id);
							break;
						case 2://delete post
							Boolean yn;
							util.showDemoPost(post);
							System.out.println("Do you want to delete the post? ");
							yn=scan.nextBoolean();
							if(yn){
								util.deletePost(post);
							}
							break;
						case 3://view post
							int choi=1;
							while(choi!=3){
								util.showPost(auth_id,post);
								System.out.println("1.Comment");
								System.out.println("2.Reply Comment");
								System.out.println("3.Back");
								choi=scan.nextInt();
								switch(choi){
								case 1://Comment
									Comment comment=util.createComment(post.getPostId(),auth_id);
									util.addComment(comment);
									post.fireComment();
									util.fireComment(post);
									break;
								case 2://Reply Comment
									ReplyComment rcomment=util.createReplyComment(post.getPostId(),auth_id);
									util.addReplyComment(rcomment);
									break;
								}
							}
							break;
						}
					}//MANAGE POSTS
					break;
			}//SWITCH DASHBOARD
		}//WHILE-DASHBOARD
	}
	static void displayPosts(int auth_id) throws IOException{
		int ch2=1;
		while(ch2!=2){//Display Posts
			System.out.println("Display Posts\n==============");
			ArrayList<Post> posts;
			posts=util.getUserPosts();
			System.out.println("1.View");
			System.out.println("2.Back");
			ch2=scan.nextInt();
			if(ch2==1){//View
				int post_id,ch;
				System.out.println("Choose the PostId : ");
				post_id=scan.nextInt();
				Post post=util.getPost(post_id,posts);
				util.showPost(auth_id,post);
				//System.out.println(post.getCategoryId());
				if(auth_id!=0)
					options(auth_id,post_id,posts);
			}
		}
	}

	public static void options(int user_id,int post_id,ArrayList<Post> posts) throws IOException{ 
		Post post=util.getPost(post_id,posts);
			while(true){
					if(util.getUserLike(post_id,user_id))
						System.out.println("\t1.Unlike");
					else
						System.out.println("\t1.Like");
					System.out.println("\t2.Share");
					System.out.println("\t3.Comment");
					System.out.println("\t4.Back");
					int ch1=scan.nextInt();
					switch(ch1){
					case 1:
						if(util.getUserLike(post_id,user_id)){
							post.likeFire(-1);
							util.likeFire(post,user_id,false);
						}
						else{
							post.likeFire(1);
							util.likeFire(post,user_id,true);
						}
						break;
					case 2:
						post.shareFire();
						break;
					case 3:
						Comment comment=util.createComment(post_id,user_id);
						util.addComment(comment);
						post.fireComment();
						util.fireComment(post);
						break;
					}
					if(ch1==4)
						break;
				}	
	}

	public static void main(String[] args) throws IOException
	{
		DataInputStream d=new DataInputStream(System.in);
		int choice3=1,choice2=1,choice1=1;
		//util.init();
		while(choice1!=4){
			int auth_id=0;
			System.out.println("=============================");
			System.out.println("\t1.Sign Up");
			System.out.println("\t2.Sign In");
			System.out.println("\t3.Reader - Display Posts");
			System.out.println("\t4.Exit");
			System.out.println("=============================");
			System.out.println("Enter your choice: ");
			choice1=scan.nextInt();
			scan.nextLine();
			while(choice1==1){//sign up
				User user=util.signUp();
				Boolean success=util.registerUser(user);
				if(success){
					while(true){
						System.out.println("SUCCESSFULLY REGISTERED");
						System.out.println("\t1.Sign In");
						System.out.println("\t2.Back");
						try{
							choice2=scan.nextInt();
							scan.nextLine();
						}
						catch(Exception e){
							scan.nextLine();
						}
						if(choice2==1){
							choice1=2;
							break;
						}
						else{
							choice1=5;
							break;
						}
					}//while
				}
				else{
					System.out.println("REGISTRATION FAILED");
					continue;
				}
			}
			while(choice1==2){//SIGN IN
				User user=util.signIn();
				Boolean success=util.loginUser(user);
				if(success){
					System.out.println("LOGIN SUCCESSFULL");
					auth_id=user.getUserId();
					if(user.getUserType()==3){//Blogger
						repeat(auth_id);
					}//Blogger-if
					else if(user.getUserType()==2){//viewer
						int choice=1;
						while(true){
							System.out.println("\t1.Create Blog");
							System.out.println("\t2.Display Posts");
							System.out.println("\t3.Logout");
							choice=scan.nextInt();
							if(choice==1){//CREATE BLOG
								if(user.getUserType()==2){
									// System.out.println("Enter something about yourself as a blogger : ");
									// String about=d.readLine();
									// user.setUserType(User.Type.BLOGGER);
									// Blogger blogger=new Blogger(user.getUserId(),about);
								   // if(util.addBlogger(blogger,user)){
										System.out.println("Blog is Created");
										util.setUserType(user.getUserId(),3);
										repeat(auth_id);
										choice1=1;
										break;
									//}
								}
							}
							else if(choice==2){
								displayPosts(auth_id);
							}
							else{//LOGOUT
								choice1=1;
								break;
							}
						}
					}//viewe-else
				}//LOGIN SUCCESS-If
				else{
					System.out.println("LOGIN FAILED");
				}
			}//SIGN-IN-WHILE
			if(choice1==3){
				displayPosts(auth_id);
			}
		}//MAIN-WHILE
	}//MAIN
}//CLASS

