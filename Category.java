class Category{
	private int categ_id;
	private int auth_id;
	private String categ_name,categ_desc;
	Category(){
		
	}
	Category(int id,int authid,String name,String desc){
		categ_id=id;
		auth_id=authid;
		categ_name=name;
		categ_desc=desc;
	}
	public void setCategoryId(int categ_id){
		this.categ_id=categ_id;
	}
	public void setUserId(int auth_id){
		this.auth_id=auth_id;
	}
	public void setCategoryName(String name){
		categ_name=name;
	}
	public void setCategoryDesc(String desc){
		categ_desc=desc;
	}
	public int getCategoryId(){
		return categ_id;
	}
	public int getAuthId(){
		return auth_id;
	}
	public String getCategoryName(){
		if(categ_id==0)
			return "Uncategorized";
		else
			return categ_name;
	}
	public String getCategoryDesc(){
		return categ_desc;
	}
}