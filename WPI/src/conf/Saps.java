package conf;

/**
 * @author dom
 *	System parameters
 */
public class Saps {
	public static String ticketAgency = "TeamA"; 

	public static long minLayover = 30;
	public static long maxLayover = 240;
	public static int legs = 2;
	public static int numberSearchResult = 20;
	public static int cacheSize = 100;
	public static boolean clearCacheEachSearch = false;
	public static boolean searchReturnCopy = false;

		
	public static final double MAX_LATITUDE = 90.0;
	public static final double MIN_LATITUDE = -90.0;
	public static final double MAX_LONGITUDE = 180.0;
	public static final double MIN_LONGITUDE = -180.0;
}
