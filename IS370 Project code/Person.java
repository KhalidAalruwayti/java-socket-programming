
public class Person {

	
	    public String id;
	    public String password;
	    public String name;
	    public Person(String id, String password, String name) 
	    {
	        
	        this.id = id;
	        this.password = password;
	        this.name = name;
	    }   
		// Given a person it will output it in the proper format for storage.
	   public static   String toCSV(Person person)
	   {
		   
	    String string = String.format("%s,%s,%s", person.id,person.password,person.name);
	    return string;
	    
	   }
	
	
	
}
