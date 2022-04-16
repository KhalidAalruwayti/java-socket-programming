import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Random;


public class Server {
	
	public static String amountToDonate(String [] projectInfo) {
		
		int goal=Integer.parseInt(projectInfo[3]);
		int amountAchived=Integer.parseInt(projectInfo[4]);
		
		if(goal==amountAchived)
			return "0";
		
		int amountToPay=goal-amountAchived;
		
		return Integer.toString(amountToPay);
		
		}
	
	public static boolean checkDataBase(DataBase db,String projectID,String  personID) throws Exception {
		
		if(db.hasIn(1,0,personID)&&db.hasIn(2,0,projectID))
    		return true;
	
  
		
		
		
		
		return false;
	}
	
	public static void updateAmountAchived(String amountAchived ,String payment,DataBase db,String projectID) throws Exception {
		
		int updatedAmountAchived=Integer.parseInt(payment)+Integer.parseInt(amountAchived);
    	
		String   x=Integer.toString(updatedAmountAchived);
   	
   	 	db.updateAttributeDB(2, 0, projectID,x, 4);
		
	}
	
	
	public static void main(String[] args) throws Exception {
		 
		
		ServerSocket serversocket = new ServerSocket(5555);
    	System.out.println("The server is running");
        Socket socket = serversocket.accept();
        System.out.println("Connection Created ");
        DataInputStream sdis = new DataInputStream(socket.getInputStream());
        DataOutputStream sdout = new DataOutputStream(socket.getOutputStream());
    
         String isFound = "No";
         DataBase db = new DataBase();
		 Random random = new Random();

         boolean remain=true;
         
         while(remain){
        	 
	        int num = Integer.valueOf(sdis.readUTF());
	        
	        switch(num){
		        case 1:
		        	sdout.writeUTF("Enter id ->");
	                String id = sdis.readUTF(); 
	                
	                sdout.writeUTF("Enter password ->");
	                String pass = sdis.readUTF(); 
	        
                    if(db.hasIn(1,0,id)&&db.hasIn(1,1,pass))
	                		isFound = "yes";
	                else 
	                	isFound = "No";
	    
	 //First senario
                    sdout.writeUTF(isFound);
                    
	                if(isFound=="yes"){//The account is found in the database 
	                    break;
	                }
	 //Second  senario
                        String response = sdis.readUTF(); //the respone will tell the server to creat an account or not 
                                               
						if (response.equalsIgnoreCase("Not want to create account")) 
						break;
						
	 //Third senario
						
						// Not have an account and want to register
						//we do't need to write any thing in here just let go to case 2
	                
                   

                case 2:
                	String col_0_1 = sdis.readUTF(); 
                	String col_1_1 = sdis.readUTF(); 
                	String col_2_1 = sdis.readUTF(); 
                                
                	Person p = new Person(col_0_1, col_1_1, col_2_1);
                	db.writeToDB(1,Person.toCSV(p));
                
                	break;
                	
                	
                
                case 3:
                	
                    sdout.writeUTF("Enter project ID");  //ask the client for project he wants to donate for 

					
                    
                	String personID = sdis.readUTF(); // receive person ID
                	String projectID = sdis.readUTF(); // receive project id
                	
                	//Befor we made this donation we need to check tow things :
                	//1 - check weather this project does exist in the data base or not 
                    //2 - check weather the project still open or not (did't reach it's goal)
                	
                	
                	
                	//First check
					if(!checkDataBase(db,projectID,personID)) //this method check the data base if it has thoose ID or not 
					{
	                    sdout.writeUTF("project not been found ");  //if the ID is not in the data base s
	                                                                //ever will send this method and then stop the case 
	                    break;
	                    
					}else
	                    sdout.writeUTF("go");  

					
					
					
					String projectInfo[]=db.getARow(2, 0, projectID); 
					
					String amountToPay=amountToDonate(projectInfo);// this method will retruned the maximum amount that a donater can pay
					                                                // amountToPay = goal-amountAchived , if it equal to 0 that means the project close 
					
					
					//second check
					if(amountToPay.equalsIgnoreCase("0"))              //check weather it is close or not 
					{
	                    sdout.writeUTF("Project has already reached the goal !! ");  
	                    break;
	                    
					}else
	                    sdout.writeUTF("go");  

					
					
					
					sdout.writeUTF(amountToPay);
					
					
					String payment=sdis.readUTF();// if the Client sent 0 the server will stop 
					
					
					
					if(payment.equalsIgnoreCase("0")) {
						sdout.writeUTF("break");
						break;
					
					}
					
					
					int rand_int1 = random.nextInt(1000);           //here we create a random number for  donationID 
                	String donationID = Integer.toString(rand_int1); // convert the donationID to String 
					
					
					Donation donation=new Donation(donationID, personID, projectID,payment);
					
                	db.writeToDB(3,donation.toCSV(donation));
                	
                	
                    sdout.writeUTF("Donation has been made \nDonation id: "+donationID);  
                    
                    updateAmountAchived(projectInfo[4],payment,db,projectID);//befor we break this use case we should update 
                                                                             //the amountAchived in the data base 
                                                                             //updated amountAchived=old amountAchived+payment

                    break;
                    
                	
					
					
                	
                case 4:
					int rand_int2 = random.nextInt(1000);

                	String projectID4 = Integer.toString(rand_int2); // 

                	String porjectname = sdis.readUTF(); // project name
                	String projectDese = sdis.readUTF(); // project dese
                	String projectGoal= sdis.readUTF(); // project goal
                	
                	
                	Project pro=new Project(projectID4, porjectname, projectDese, projectGoal);
                	
                	db.writeToDB(2,pro.toCSV(pro));
                	
                    sdout.writeUTF("charity has been made \ncharity id:" +projectID4);                              	
                	break;
               
                case 5:
				try { 
					LinkedList list=db.getAllRowsDB(2);
				
					int length=list.size();          
															 //toString method is used to convert an interger to a String 
					String stringLength=Integer.toString(length);//here we convert it to a String so we can send it throgth a socket 
				
					sdout.writeUTF(stringLength);  
				
					list.getFirst();

					for(int i=0; i<length;i++) {			//.get(int i) method used to retirive the data in the node 
						sdout.writeUTF((String)list.get(i));// here we send the data in the node to the 

					}                		                	
			
				}
				catch(Exception e){
					
					 sdout.writeUTF("Error");   
					 
				}
					break;
                case 6:
                	
                    sdout.writeUTF("Enter project ID");                    
                	String projectID6=sdis.readUTF();

					if(db.hasIn(2,0,projectID6)){
						isFound = "Yes";
					}
					else {
						isFound = "No";
					}
					sdout.writeUTF(isFound);

					if(isFound.equalsIgnoreCase("yes")){
						String project[] =db.getARow(2, 0, projectID6);

						sdout.writeUTF(project[0]);
						sdout.writeUTF(project[1]);
						sdout.writeUTF(project[2]); 
						sdout.writeUTF(project[3]); 
						sdout.writeUTF(project[4]); 

						break;
					}
                	if(isFound.equalsIgnoreCase("no")){
						sdout.writeUTF("Project id not found try again ");  
						break;	
					}
                
                case 7:
                	try { 
                		
                        
                        String personID7=sdis.readUTF();

    					LinkedList list=db.getSpecificRowsDB(3, 1, personID7);///we anly need to change this method to a method that returned the history in a liked list 
    				
    					int length=list.size();          
    															 //toString method is used to convert an interger to a String 
    					String stringLength=Integer.toString(length);//here we convert it to a String so we can send it throgth a socket 
    				
    					sdout.writeUTF(stringLength);  
    				
    					list.getFirst();

    					for(int i=0; i<length;i++) {										//.get(int i) method used to retirive the data in the node 
    						sdout.writeUTF((String)list.get(i));// here we send the data in the node to the 

    					}                		                	
    			
    				}
    				catch(Exception e){
    					
    					 sdout.writeUTF("Error");   
    					 
    				}
                	
           
                	break;

				case 8:
					remain=false;
					sdout.writeUTF("Thanks, Goodbye :)");
                	break;
		        default:	
		        	
        

    }
}

}
    

}
