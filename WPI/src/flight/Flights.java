package flight;
import java.util.ArrayList;


/**
 * this represents multiple flights
 * */
public class Flights extends ArrayList<Flight> {

	/**
	 * this is a mandatory field, don't worry about it
	 */
	private static final long serialVersionUID = 1L;
	public String toString(){
		StringBuilder ans = new StringBuilder();
		ans.append(Flight.Header());
		ans.append("\n");
		for(Flight e: this){
			ans.append(e.toString());
			ans.append("\n");
		}
		return ans.toString();
	}
}
