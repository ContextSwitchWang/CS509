package airport;

public class Airport {
	private double Latitude;
	private double Longitude;
	private String Code;
	private String name;
	public void name(String name2) {
		name = name2;
	}
	public void code(String code2) {
		Code = code2;
	}
	public void latitude(double latitude2) {
		Latitude = latitude2;
	}
	public void longitude(double longitude2) {
		Longitude = longitude2;
	}
	public boolean isValid() {
		if(name != null && Code != null)
			return true;
		return false;
	}
	public String code() {
		return Code;
	}
	
	
}
