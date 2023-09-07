package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

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
		
		} catch (ParseException e) 
		{
			StringBuilder sbEx = new StringBuilder();
			sbEx.append(e.getClass().getName()+"\n");
			sbEx.append(StringTools.cortarString(e.getMessage(), 80) );
				
			JOptionPane.showMessageDialog(null, sbEx.toString(), " [ ERROR ] ", 
						JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	
	/**
	* 
	* @param date
	* @param inputFmt
	* @return
	*/
	public static final String formatString(String strDate, String inputFmt) 
	{
		SimpleDateFormat inputFormat = new SimpleDateFormat(inputFmt);
		SimpleDateFormat outputFormat = new SimpleDateFormat(DEFAULT_FORMAT);
		
			try 
			{
				Date date = inputFormat.parse(inputFmt);
				String resp = outputFormat.format(date);
				return resp;
				
			} catch (ParseException e) {
				
				StringBuilder sbEx = new StringBuilder();
				sbEx.append(e.getClass().getName()+"\n");
				sbEx.append(StringTools.cortarString(e.getMessage(), 80) );
					
				JOptionPane.showMessageDialog(null, sbEx.toString(), " [ ERROR ] ", 
							JOptionPane.ERROR_MESSAGE);
			}

		return null;
	}
	
}
