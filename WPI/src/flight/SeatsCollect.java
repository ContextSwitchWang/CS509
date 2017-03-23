package flight;
import java.util.ArrayList;

import airport.Airport;

public class SeatsCollect extends ArrayList<Seats>{
	public String toString(){
		StringBuilder ans = new StringBuilder();
		ans.append(String.format("%-5s%s", "No.", Seat.Header()));
		ans.append("\n");
		int i = 1;
		for(Seats e: this){
			ans.append(String.format("%-5s", i));
			int size = e.size();
			if(size == 1){
				ans.append(e.get(0).toString());
			}
			else if(size > 1){
				ans.append(e.get(1));
				for(int j = 1; j < size; j++){
					ans.append(String.format("%-5s%s", "Leg" + Integer.toString(j), e.get(j)));
				}
			}
			else{
				throw new RuntimeException("Unexpected empty seats");
			}
			ans.append("\n");
			i ++;
		}
		return ans.toString();
	}
	
	void sortOnPrice(boolean ascending){
		
	}
	void sortOnDpartureTime(boolean ascending){
		
	}
}
