package airport;
import java.util.ArrayList;
import java.lang.StringBuilder;
public class Airports extends ArrayList<Airport>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8937265924404789024L;
	public String toString(){
		StringBuilder ans = new StringBuilder();
		
		ans.append(Airport.Header());
		ans.append("\n");
		for(Airport e: this){
			ans.append(e.toString());
			ans.append("\n");
		}
		return ans.toString();
	}
}
