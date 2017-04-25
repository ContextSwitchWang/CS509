package flight;
import java.util.ArrayList;

public class Seats extends ArrayList<Seat>{
	public String toString(){
		String sep = "----------------------------------------";
		sep += sep;
		if(this.size() == 1){
			return this.get(0).toString();
		}
		StringBuilder ans = new StringBuilder() ;
		//ans.append(sep);
		for(Seat seat: this){
			ans.append(seat.toString() + "\n");
		}
		//ans.append(sep);
		ans.append("\n");
		return ans.toString();	
	}
	public Seats copy(){
		Seats seats = new Seats();
		for(Seat seat: this){
			seats.add(seat.copy());
		}
		return seats;
	}
}
