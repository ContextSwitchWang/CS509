package airport;

public class Airport {
	public double Latitude;
	public double Longitude;
	public String Code;
	public String name;
	
	public boolean isValid() {
		if(name != null && Code != null)
			return true;
		return false;
	}
	
	
}
