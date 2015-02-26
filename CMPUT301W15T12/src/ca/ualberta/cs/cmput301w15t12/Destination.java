package ca.ualberta.cs.cmput301w15t12;

public class Destination
{
	private String Destination;
	private String Description;

	//for printing the list of destinations
	public String toFullString()
	{
		String string = Destination+": "+Description+"\n";
		return string;
	}	
	
	//getters and setters
	public String getDestination(){
		return Destination;
	}

	public void setDestination(String destination){
		Destination = destination;
	}
	
	public String getDescription(){
		return Description;
	}

	public void setDescription(String description){
		Description = description;
	}
}
