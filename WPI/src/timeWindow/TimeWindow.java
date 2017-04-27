package timeWindow;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class TimeWindow {
	public LocalDateTime start;
	public LocalDateTime end;

	public static DateTimeFormatter DBformatter = DateTimeFormatter.ofPattern("yyyy MMM d HH:mm z");
	public static DateTimeFormatter UIformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
	public static DateTimeFormatter URLformatter = DateTimeFormatter.ofPattern("yyyy_MM_d", Locale.US);
	
	public boolean inBetween(LocalDateTime t){
		return t.isBefore(end) && t.isAfter(start);
	}
	

	public String getStartDateURL(){
		return getDate(start, URLformatter);
	}
	
	public String getEndDateURL(){
		return getDate(end, URLformatter);
	}
	
	public String getStartDateUI(){
		if(end == null && start == null){
			return "None";
		}
		if(end != null && start == null){
			start = end.minusDays(1);
		}
		return getDate(start, UIformatter);
	}
	
	public String getEndDateUI(){
		if(end == null && start == null){
			return "None";
		}
		if(end == null && start != null){
			end = start.plusDays(1);
		}
		return getDate(end, UIformatter);
	}
	
	public TimeWindow(){
		
	}
	
	/**
	 * Parse date from string in UI format
	 * @param s the String to parse date from
	 * @return a new LocalDateTime object
	 */
	public static LocalDateTime parseDateUI(String s){
		return LocalDateTime.parse(s, UIformatter);
	}
	
	public static LocalDateTime parseDateXML(String s){
		return LocalDateTime.parse(s, DBformatter);
	}
	
	public static String getDate(LocalDateTime day){
		return day.format(URLformatter);
	}
	
	public TimeWindow relaxDays(){
		TimeWindow t = new TimeWindow();
		t.start = this.start.minusDays(1);
		t.end = this.end.plusDays(5);
		return t;
	}
	
	/**
	 * @param day could be start or end
	 * @param f a formatter to use
	 * @return return string representation of day in format f
	 */
	private String getDate(LocalDateTime day, DateTimeFormatter f){
		return day.format(f);
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
