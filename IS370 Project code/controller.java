import java.util.LinkedList;
import java.net.*;
// ground rules.
public interface controller {

	    //TODO: Log in will check the dataBase for the correct password and id(DO not forget that you are dealing with a linkedList for further details check DataBase class).
	    public Person logIn(String id, String password);
	  
	    //TODO: same as logIn but will return a string to declare that a person has been created or not.
	    public String register(String id,String password,String name);

	    // TODO: Create a class called donate that only have the following atributes Person,Project,Amount.  if Donation is accepted then it gets noted in the dataBase 
	    // DO NOT forget that we have to change the project database since it got more money and the donate data Base. 
	    public Boolean donate(Person from,Project to,int amount);

	    // TBD:
	    public Boolean createCharity(Project project);
	    
	    // TODO: Store name and id of chairty in the LinkedList. 
	    public LinkedList<String> seeAvaialableChairtys();

	    // TODO: given a projectId you will get all the available information on it.
	    public String seeFurther(int projectId);

	    //TODO: will get the entire history of donation made by a person and make sure that they are stored to the LinkedList.
	    public LinkedList<String> seeHistory(int personID);

	
	
	
	
	
	
}
