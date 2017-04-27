package flight;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import airport.Airport;

public class SeatsCollect extends ArrayList<Seats>{
	Comparator<Seats> priceComparator = new PriceComparator();
	Comparator<Seats> depatureTimeComparator = new DepatureTimeComparator();
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
				ans.append(e.get(0));
				for(int j = 1; j < size; j++){
					ans.append(String.format("\n%-5s%s", "Leg" + Integer.toString(j+1), e.get(j)));
				}
			}
			else{
				throw new RuntimeException("Unexpected empty seats");
			}
			ans.append("\n\n");
			i ++;
		}
		return ans.toString();
	}
	
	public SeatsCollect copy(){
		SeatsCollect ssc = new SeatsCollect();
		for(Seats seats : this){
			ssc.add(seats.copy());
		}
		return ssc;
	}
	public void sortOnPrice(boolean ascending){
		Collections.sort(this, priceComparator);
		if(ascending){
			Collections.reverse(this);
		}
	}
	public void sortOnDpartureTime(boolean ascending){
		Collections.sort(this, depatureTimeComparator);
		if(ascending){
			Collections.reverse(this);
		}
	}
}

class PriceComparator implements Comparator<Seats> {
    @Override
    public int compare(Seats a, Seats b) {
    	double pa = 0;
    	for(Seat s : a){
    		pa += s.getPrice();
    	}
    	double pb = 0;
    	for(Seat s : b){
    		pb += s.getPrice();
    	}
        return (int)(pb - pa);
    }
}

class DepatureTimeComparator implements Comparator<Seats> {
    @Override
    public int compare(Seats a, Seats b) {
    	return a.get(0).flight.TimeDepart.compareTo(b.get(0).flight.TimeDepart);
    }
}