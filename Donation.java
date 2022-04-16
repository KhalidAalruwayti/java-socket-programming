
public class Donation {
	
	 public String donationId; // Will I don't think we need an id for each donation. we will still have it in case we decide to add deleting to the equation. 
	    public String personId; // who ever made said donation (Why bother using an entire person class when person is enough?).
	    public String projectId; // what project got the donation.
	    public String paymentAmount;

	    // default const
	    public Donation(String donationId, String personId, String projectId,String paymentAmount) 
	    {
	        this.donationId = donationId;
	        this.personId = personId;
	        this.projectId = projectId;
	        this.paymentAmount=paymentAmount;
	    }

	    // gets you a string that is ready to be inserted in DB.
	    public  String toCSV(Donation donation)
	    {
	        String string = String.format("%s,%s,%s,%s",donation.donationId,donation.personId,donation.projectId,donation.paymentAmount);
	        return string;
	    }   
}
