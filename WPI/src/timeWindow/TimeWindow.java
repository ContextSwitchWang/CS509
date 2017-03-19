package timeWindow;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class TimeWindow {
	public LocalDateTime start;
	public LocalDateTime end;
	public static DateTimeFormatter DBformatter = DateTimeFormatter.ofPattern("yyyy MMM d HH:mm O");
	public static DateTimeFormatter UIformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
	public static DateTimeFormatter URLformatter = DateTimeFormatter.ofPattern("yyyy_MM_d");
	public String getStartDate(){
		return getDate(start);
	}
	
	public String getEndDate(){
		return getDate(end);
	}
	
	public TimeWindow(){
		
	}
	
	public static LocalDateTime parseDate(String s){
		return LocalDateTime.parse(s, UIformatter);
	}
	
	public static String getDate(LocalDateTime day){
		return day.format(URLformatter);
	}
	
	public String toString(){
		if(start == null && end == null){
			return "None";
		}
		if(start == null){
			start = end.minusDays(1);
		}
		else{
			end = start.plusDays(1);
		}
		return String.format("%s to %s", start.format(UIformatter), end.format(UIformatter));
	}
}
