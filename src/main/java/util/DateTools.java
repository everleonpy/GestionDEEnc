package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools 
{
	public static final String DEFAULT_FORMAT = "dd/MM/yyyy";
	
	/**
	* 
	* @param pDate
	* @param pFormat
	* @return
	*/
	public static String getString(Date pDate, String pFormat) 
	{
		DateFormat dateFormat = new SimpleDateFormat(pFormat != null ? pFormat : DEFAULT_FORMAT);
		
		return dateFormat.format(pDate);
	}


	
	/**
	* 
	* @param date
	* @param pFormat
	* @return
	*/
	public static final Date getDate(String date, String pFormat) 
	{
	
		try 
		{
		
			DateFormat dateFormat = new SimpleDateFormat(pFormat != null ? pFormat : DEFAULT_FORMAT);
			return dateFormat.parse(date);
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
