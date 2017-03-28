package flight;
import java.util.ArrayList;

import conf.Saps;

import java.time.temporal.ChronoUnit;

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
	public boolean isValid(){
		long size = this.size();
		if(size > 1){
			long min = Saps.MIN_OVERLAYTIME;
			long max = Saps.MAX_OVERLAYTIME;
			Flight flight = this.get(0);
			for(int i = 1; i < size; i++){
				Flight flight2 = this.get(i);
				long diff = ChronoUnit.MINUTES.between(flight.TimeArrival, flight2.TimeDepart);
				if(diff < min || diff > max){
					return false;
				}
				flight = flight2;
			}
		}
		return true;
	}
}
