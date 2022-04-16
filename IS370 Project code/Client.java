import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	
	
public static int getPayment(int amountToPay,Scanner scan) {
	
	
	
	
	System.out.println("Enter the amount you want to Pay ");
	
	int x=0;
	
	do {
		try {
		System.out.println("Note, The range of payment is between 1 to "+amountToPay);
		System.out.println("*Enter 0 to stop ");
		
		x=scan.nextInt();
		}
		catch (Exception e){
			System.out.println("Please enter integer only");
		}
		
			

	}
	while(x!=0 &&(x>amountToPay || x<0));
	
	return x;
}
	
	
	
	
	
public static void main(String[] args) throws UnknownHostException, IOException {
	
		Socket socket = new Socket("localhost", 5555);
		System.out.println("Client Created");
        DataOutputStream cdout = new DataOutputStream(socket.getOutputStream());
        DataInputStream cdis = new DataInputStream(socket.getInputStream());
		Scanner scan = new Scanner(System.in);
		
		
		boolean remain=true;
		boolean login=false;
		
		
		String personID=null;
		

		while(remain) {
			System.out.println("----------------------------------");
			System.out.println("What do you want to do?" +
		                       "\n1- login." +
					           "\n2- register."+
		                       "\n3- close System");
			
			System.out.println("Enter a number from list -> ");
			String choice = scan.next();
			String isFound =null;

			
			switch(choice){
			case "1":
				
				System.out.println("----------------------------------");
				 cdout.writeUTF("1");
				 System.out.println(cdis.readUTF());
				 personID = scan.next();
				 cdout.writeUTF(personID); 

				
				System.out.println(cdis.readUTF());
				String pass= scan.next(); 
				cdout.writeUTF(pass); 

				
				isFound = cdis.readUTF();
				
//First senario
				
				if (isFound.equalsIgnoreCase("Yes")) // Have an account
				{
					System.out.println("you sign in sucssfully ");
					login=true;
					System.out.println("----------------------------------");
					break;
					
				}                                        
				
					System.out.println("You are not register, do you want to register? (N/Y)");
					
					do {

						choice = scan.next();
						if (!(choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")))
							System.out.println("------------\nError, try again\n------------");
						
					} while (!(choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")));
					
					
//Second senario
					if (choice.equalsIgnoreCase("N")) // Not have an account and don't want to register
					{
						System.out.println("Thanks, Goodbye :)");
						cdout.writeUTF("Not want to create account");
						System.out.println("----------------------------------");
						break;
					}
//Third senario
					
					// Not have an account and want to register
					
					
				case "2":	
					System.out.println("----------------------------------");
				     cdout.writeUTF("2");
				
				     String id1=null;
					 boolean still=true;
				     while(still){
						System.out.println("Enter ID");
						id1 = scan.next();
						try{
							int yourNumber = Integer.parseInt(id1);
							still = false;
						}
						catch (NumberFormatException ex) {
							System.out.println("please enter integers");
						}
					}
		
				     String pass1;
				     System.out.println("Enter password");
				     pass1 = scan.next();

				     String name1;
				     System.out.println("Enter name");
				     name1 = scan.next();
		
				     cdout.writeUTF(id1);
				     cdout.writeUTF(pass1);
				     cdout.writeUTF(name1);
				
			         System.out.println("=== Account created ===");
						System.out.println("----------------------------------");

			         break;
			         
				case "3":
					System.out.println("----------------------------------");

					remain=false;
					cdout.writeUTF("8");
					System.out.println(cdis.readUTF()); // Thanks, Goodbye :)
					socket.close();
					System.out.println("----------------------------------");

					break;
			     
			 }
			         
			         
 
    

 
			while(login) 
			{
				
				System.out.println("What do you want to do?" 
						  +"\n3- Donate"
				          +"\n4- Create a charity"
						  +"\n5- see Avaialable Chairtys"
						  +"\n6- see Project info"
						  +"\n7- see Donation History"
				          +"\n8- Exit");
				
				System.out.println("Enter a number from list -> ");
				
				String services = scan.next();
				
				switch(services) {
				
						
					
				         
					case "3":
						
						System.out.println("----------------------------------");
						
					     cdout.writeUTF("3");
					     
						
						
					     
					     String projectID;
					     System.out.println(cdis.readUTF());
						 projectID=scan.next();	
						 			 
						 
						cdout.writeUTF(personID);
						cdout.writeUTF(projectID);

						
						isFound = cdis.readUTF();
						
						if(isFound.equalsIgnoreCase("project not been found "))
						{
							System.out.println(isFound);
							System.out.println("----------------------------------");
							break;
						}
						
					   String projectOpen=cdis.readUTF();
					   
					   if(projectOpen.equalsIgnoreCase("Project has already reached the goal !! "))
						{
							System.out.println(projectOpen);
							System.out.println("----------------------------------");
							break;
						}
						
					       
					   
					   int amountToPay=Integer.parseInt(cdis.readUTF());

					   
					  cdout.writeUTF(Integer.toString(getPayment(amountToPay,scan)));
					  
					  String path=cdis.readUTF();
					  if(path.equalsIgnoreCase("break")) {
						  System.out.println("----------------------------------");
						  break;
					  }

				      System.out.println(path);

						System.out.println("----------------------------------");
						break;

					   
						
						
						
						

						
						
					case "4":
						System.out.println("----------------------------------");

						cdout.writeUTF("4");
					     
					
					     String projectName;
					     System.out.println("Enter name");
					     projectName=scan.next();
					     
					     String projectDese;
					     System.out.println("Enter project Description");
					     projectDese=scan.next();
					     
					     String projectGoal=null;
						 boolean still1=true;
						 while(still1){
						    System.out.println("Enter Project goal");
					    	projectGoal=scan.next();
							try{
								int yourNumber = Integer.parseInt(projectGoal);
								if(yourNumber>0)
									still1 = false;
								else
									System.out.println("please enter postive number");


							}
							catch (NumberFormatException ex) {
								System.out.println("please enter integers");
							}
						}
					     
						
						cdout.writeUTF(projectName);
						cdout.writeUTF(projectDese);
						cdout.writeUTF(projectGoal);

						
						System.out.println(cdis.readUTF());
						System.out.println("----------------------------------");
						break;
						
					case "5":	
						System.out.println("----------------------------------");

					try {
						cdout.writeUTF("5");
						
						String stringLength=cdis.readUTF();
																  //.parseInt(Stirng a) is used to convert a String to integer 
						int length=Integer.parseInt(stringLength);//we convert the stringLength to integer so we can use as a condition in the loop 
						
						System.out.print("Project id\t");
						System.out.print("Project name\t");
						System.out.print("Project desc\t");
						System.out.print("Project goal\t");
						System.out.println("Project amountAchived");
						
						
						for(int i=0; i<length;i++)
						{
							String cs5=cdis.readUTF();
							
							String newcs5=cs5.replace(",","\t \t");
							
							System.out.println(newcs5);
						}
					}
					catch(Exception e){
						
						System.out.println("there are no charities available");
					}
					System.out.println("----------------------------------");
					break;
						
					case "6":
						System.out.println("----------------------------------");

					cdout.writeUTF("6");
                    	System.out.println(cdis.readUTF());
                    	String proID=scan.next();

						cdout.writeUTF(proID);

						isFound = cdis.readUTF();

						if(isFound.equalsIgnoreCase("yes")){
							System.out.print("Project id\t");
							System.out.print("Project name\t");
							System.out.print("Project desc\t");
							System.out.print("Project goal\t");
							System.out.println("Project amountAchived");

							System.out.print(cdis.readUTF()+"\t \t");
							System.out.print(cdis.readUTF()+"\t \t");
							System.out.print(cdis.readUTF()+"\t \t");
							System.out.print(cdis.readUTF()+"\t \t");
							System.out.println(cdis.readUTF());
							System.out.println("----------------------------------");
							break;
						}
						if(isFound.equalsIgnoreCase("no")){
							System.out.println(cdis.readUTF());
							System.out.println("----------------------------------");
							break;
						}
					case "7":
						System.out.println("----------------------------------");

						try {
							
							cdout.writeUTF("7");
							
							
							cdout.writeUTF(personID);

																				
							String stringLength=cdis.readUTF();
							
							
																	  //.parseInt(Stirng a) is used to convert a String to integer 
							int length=Integer.parseInt(stringLength);//we convert the stringLength to integer so we can use as a condition in the loop 
							
							System.out.print("Donation ID\t");
							System.out.print("person ID\t");
							System.out.print("Project ID\t");
							System.out.print("payment amont\t");
							System.out.println("");
							for(int i=0; i<length;i++) {
								
								String cs5=cdis.readUTF();
								String newcs5=cs5.replace(",","\t \t");
								System.out.println(newcs5);

							}
						}
						catch(Exception e){
							
							System.out.println("History donation is empty ");
						}
						
						System.out.println("----------------------------------");

						break;	
						
					case "8":
						System.out.println("----------------------------------");
						login=false;
						System.out.println("----------------------------------");

						break;
						
			        default:
						System.out.println("----------------------------------");
						System.out.println("------------\nError, enter number from 3 to 8\n------------");
						System.out.println("----------------------------------");

					
					
				}	
			}

			

		
		}
	}
}
	




