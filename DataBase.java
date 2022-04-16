
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
 


/*
 *  This will be the dataBase. It will 100% trust any command from any class. 
 *  Please DO THIS PRIMARY KEY,PASSWORD(If needed),anything else.
 *  when it asks about type it is asking you what dataBase you should store it in. 1 is for personal 2 for project 3 is donation..................
 */ 
public class DataBase
{   
    public File file = new File("PersonRecord.csv"); 
    public File file1 = new File("ProjectRecord.csv");
    public File file2 = new File("DonationRecord.csv");


  
    // Creat A CSV dataBase  with no header. (greate for debugging at the start.)
    public  Boolean createDB(String name) throws Exception 
    {
        File file = new File(name);
        boolean flag = file.createNewFile();
        return flag;
    }

    // Gets an entire col of data  returns as LinkedList of strings.  
    public LinkedList<String> getColDB(int type,int col) 
    {
        try 
        {   

           
            
            Scanner input = new Scanner(whatFile(type));

            LinkedList<String> a = new LinkedList<String>();


            while(input.hasNext())
            {
                String line = input.next(); // entire line
                String[] values = line.split(",");
                a.add(values[col]);
            }
            input.close();
            return a;
            

            
        } 
        catch (Exception e) {
            System.out.println("Probably asked for a col that does not exist.");
            System.out.println(e);
            return null; // if this method returns null then we got a problem.
        }


    }
   
    // gets the entire database as a row every row is stored in the ls. first is row 0 second is row 1 etc...
    public LinkedList<String> getAllRowsDB(int type) throws Exception
    {
        LinkedList<String> a = new LinkedList<String>();
        
        
        Scanner input = new Scanner(whatFile(type));
        while(input.hasNext())
        {
            String line = input.next(); // entire line
            a.add(line);
        }
        input.close();

        return a;
    }
    
    // writes an entire row of data // type 1 is for Personal db type 2 for Project db etc.
    // Since the client does not have direct access to the DB we are trusting the other classes to make sure that the entries are not corrupted. 
    public void writeToDB(int type, String data) throws Exception
    {   

    
        try(PrintWriter writer = new PrintWriter(new FileWriter(whatFile(type),true)))
        {
            StringBuilder sb = new StringBuilder();
            sb.append(data);
            sb.append("\n");

            writer.write(sb.toString());

        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
        


    }

    //used to determine what kind of data base should it run at.
    private File whatFile(int type)
    {
        if(type == 1)
            return file;
        else if (type == 2)
            return file1;
        else 
            return file2;
    }

    // Will check the entire data base for anything that matches your entry will check a single col (if you need to check 2 things you can do it twice.)
    public Boolean hasIn(int type,int col,String entry) throws Exception
    {   
        try 
        {
            DataBase db = new DataBase();

            LinkedList<String> a = db.getAllRowsDB(type);

            while(!a.isEmpty())
            {
                String b = a.getFirst();
                String[] as = b.split(",");
                if(as[col].equals(entry))
                {
                    return true;
                }
                a.removeFirst();
            }
            return false;
        } 
        catch (Exception e) 
        {
            return false;
            //TODO: handle exception
        }
        
    }

    // Gets a row by it primary key.  It may return null be wary.
    public String[] getARow(int type,int col,String id) throws Exception
    {

        if(hasIn(type, col, id) == false)
        {
        return null;
        }
        DataBase db = new DataBase();
        LinkedList<String> a = db.getAllRowsDB(type);
        while(!a.isEmpty())
        {
            String b = a.getFirst();
            String[] as = b.split(",");
            if(as[col].equals(id))
            {
                return as;
            }
            a.removeFirst();
        }
        return null;
        
    } 

    // given correct info this method will return the entire specifed row. be wary it will return the first encouter.(assuming you search on the primary key will not matter).
    public LinkedList<String> getSpecificRowsDB(int type,int col,String ID) throws Exception
    {
        LinkedList<String> a = new LinkedList<String>();


        Scanner input = new Scanner(whatFile(type));
        while(input.hasNext())
        {
            String line = input.next(); // entire line
            String[] values = line.split(",");

           if(values[col].equalsIgnoreCase(ID))
            a.add(line);
        }
        input.close();

        return a;
    }

    //update for the change you want to implement. colToUpdate is the  col you want to chane(col is the col of the primary key while colToUpdate is the attribute to be changed).
    public Boolean updateAttributeDB(int type, int col,String ID,String update,int colToUpdate) throws Exception
    {
        LinkedList<String> a = new LinkedList<String>();
        
        Boolean flag = true;
        Scanner input = new Scanner(whatFile(type));
        while(input.hasNext())
        {
            
            String line = input.next(); // entire line
            String[] values = line.split(",");

           if(values[col].equalsIgnoreCase(ID)) // found row to be changed
           {
                
                values[colToUpdate] = update;
                StringBuffer sb = new StringBuffer();
                for(int i = 0; i < values.length; i++) 
                {
                    sb.append(values[i]);
                    if((i<values.length - 1))
                    {
                        sb.append(",");
                    }
                    else
                    {
                        ;
                    }
                    
                }
                
                flag = true; 
                String str = sb.toString();
                line = str;
           }
            a.add(line);
        }
        FileWriter fw = new FileWriter(whatFile(type),false); // delete file
        while(!a.isEmpty())
        {
            writeToDB(type, a.getFirst());
            a.removeFirst();
        }
        return flag;
    }
    

    
    
    
    
    
    
    
    // Dead code was used to debug.
    
    // public static void main(String[] args) throws Exception 
    // {
    //     DataBase db = new DataBase();
    //     // db.writeToDB(2, "10,Ahma9d");
    //     // db.writeToDB(2, "15,A2hmad");
    //     // db.writeToDB(2, "11,Ahm5ad");
    //     // db.writeToDB(2, "13,Ah7mad");
    //     // db.writeToDB(2, "13,A1hmad");
    //     // db.writeToDB(2, "177,Ahm2ad");
    //     System.out.println(db.updateAttributeDB(2, 0, "15", "sad", 1));


        


    // }

}