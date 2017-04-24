package util;
import flight.SeatsCollect;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
public class Util {
	static NumberFormat USDollar = NumberFormat.getCurrencyInstance(Locale.US);
	
	public static String printUSDollar(Number n){
		return USDollar.format(n);
	}
	
	public static Number parseUSDollar(String s) throws ParseException{
		return USDollar.parse(s);
	}
	
	void sortOnPrice(SeatsCollect collect, boolean ascending){
		
	}
	void sortOnDpartureTime(SeatsCollect collect, boolean ascending){
		
	}
	
}
