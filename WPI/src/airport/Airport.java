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
	
	public String toString(){
		return String.format("%-50s%s\n", name, Code);
	}
	

	public static String Header(){
		return String.format("%-50s%s\n", "Name", "Code");
	}
}