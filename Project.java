
public class Project {
	
	
		public String projectId;
	    public String name;
	    public String desc;
	    public String goal;
	    public String amountAchived; // We can not store this because it will much of a hassale to do so


	    public Project(String projectId, String name, String desc, String goal, String amountAchived) 
	    {
	        this.projectId = projectId;
	        this.name = name;
	        this.desc = desc;
	        this.goal = goal;
	        this.amountAchived = amountAchived;
	    }
	    public Project(String projectId, String name, String desc, String goal) 
	    {
	        this.projectId = projectId;
	        this.name = name;
	        this.desc = desc;
	        this.goal = goal;
	        this.amountAchived ="0";

	        
	    }
	    
	    // Gets you a string that is ready to be written in the DB
	    public String toCSV(Project project)
	    {
	        String string = String.format("%s,%s,%s,%s,%s", project.projectId,project.name,project.desc,project.goal,project.amountAchived);
	        return string;
	    }
	
	
	

}
