class Like{
	private int post_id;
	private int user_id;

	public Like(int post_id,int user_id){
		this.post_id=post_id;
		this.user_id=user_id;
	}

	public int getUserId(){
		return user_id;
	}

	public int getPostId(){
		return post_id;
	}
}