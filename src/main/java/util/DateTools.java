package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools 
{
	public static final String DEFAULT_FORMAT = "dd/MM/yyyy";
	
	
	public static String getString(Date pDate, String pFormat) 
	{
		DateFormat dateFormat = new SimpleDateFormat(pFormat != null ? pFormat : DEFAULT_FORMAT);
		
		return dateFormat.format(pDate);
	}

}
