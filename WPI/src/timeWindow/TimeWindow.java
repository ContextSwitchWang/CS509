package timeWindow;
import java.time.OffsetDateTime;

public class TimeWindow {
	private OffsetDateTime start;
	private OffsetDateTime end;
	public String getStartDate(){
		return getDate(start);
	}
	
	public String getEndDate(){
		return getDate(end);
	}
	
	public static String getDate(OffsetDateTime day){
		int year = day.getYear();
		int month = day.getMonthValue();
		int date = day.getDayOfMonth();
		return String.format("%04d", year)
				+ String.format("%02d", month)
				+ String.format("%02d", date);
	}
}
