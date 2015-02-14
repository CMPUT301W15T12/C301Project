package ca.ualberta.cs.cmput301w15t12;

public class Tags {
	String name;
	String description;
	public Tags(String name, String description) {
		this.name = name;
		this.description = description;
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return this.name;
	}
	public String getDescrpition(){
		return this.description;
	}
	public void setName(String new_name) {
		this.name = new_name;
	}
	 public void setDescription(String description){
		 this.description = description;
	 }
}
