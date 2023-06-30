package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class UtilPOS {

	/**
	 * 
	 * @param s
	 * @param n
	 * @param c
	 * @param paddingLeft
	 * @return String
	 * 
	 * Este metodo tiene como objetivo devolver una cadena "s" rellenada
	 * por la izquierda (paddingLeft = true) o por la derecha (paddingLeft = false)
	 * a "n" posiciones con el caracter "c"
	 */
	public static String paddingString(String s, int n, char c, boolean paddingLeft) {
		 if (s == null) {
		   return s;
		 }
		 int add = n - s.length(); // may overflow int size... should not be a problem in real life
		 if(add <= 0){
		   return s;
		 }
		 StringBuffer str = new StringBuffer(s);
		 char[] ch = new char[add];
		 Arrays.fill(ch, c);
		 if(paddingLeft){
		   str.insert(0, ch);
		 } else {
		   str.append(ch);
		 }
		 return str.toString();
		}	

	/**
	 * 
	 * @param s
	 * @param n
	 * @param c
	 * @param paddingLeft
	 * @return String
	 * 
	 * Este metodo tiene como objetivo devolver una cadena "s" centrada
	 * dentro de una longitud "n"
	 */
	public static String centerString(String s, int n) {
		 if (s == null) {
		   return s;
		 }
		 // si la longitud de la cadena supera la capacidad de la cadena,
		 // se devuelve la porcion de la cadena correspondiente a la
		 // capacidad de la linea empezando por el caracter mas a la izquierda
		 if ( s.length() >= n ) {
			 return s.substring(0, n-1);
		 }
		 int fillerQty = (n - s.length()) / 2;
		 if(fillerQty <= 0){
			   return s;
		 }
		 char[] ch = new char[fillerQty];
		 Arrays.fill(ch, ' ');
		 StringBuffer str = new StringBuffer(s);
		 str.insert(0, ch);
		 return str.toString();
		 
		}	
	
	/**
	 * @param fechaDate
	 * @return java.util.Calendar
	 * 
	 * Este metodo tiene como objetivo devolver el equivalente Calendar de
	 * una fecha que es instancia de Date
	 */
	public static java.util.Calendar dateToCalendar ( java.util.Date fechaDate) {
		java.util.Calendar fecha = Calendar.getInstance();
		fecha.setTime(fechaDate);
		return fecha;
	}
	
	public static java.util.Date getFirstDay ( java.util.Date xDate ) {
		java.util.Date result = null;		
		//
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String dateText = sdf.format(xDate);
		String month = dateText.substring(2, 4);
		String year = dateText.substring(4);
		//
		dateText = "01" + month + year;
		try {
		    result = sdf.parse(dateText);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
        return result;
	}
	
	public static long hoursBetweenDates ( java.util.Date fromDate, java.util.Date toDate ) {
		// Get msec from each, and subtract.
		long diff = toDate.getTime() - fromDate.getTime();
		long diffSeconds = diff / 1000;         
		long diffMinutes = diff / (60 * 1000);         
		long diffHours = diff / (60 * 60 * 1000);                      
		System.out.println("Time in seconds: " + diffSeconds + " seconds.");         
		System.out.println("Time in minutes: " + diffMinutes + " minutes.");         
		System.out.println("Time in hours: " + diffHours + " hours."); 	
		return diffHours;
	}
	
	public static java.util.Date getLastDay ( java.util.Date xDate ) {
		java.util.Date result = null;		
		String lastDay = "30";
		//
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String dateText = sdf.format(xDate);
		String month = dateText.substring(2, 4);
		String year = dateText.substring(4);
		//
		if (Integer.valueOf(month) == 1 | Integer.valueOf(month) == 3 |
			Integer.valueOf(month) == 5 | Integer.valueOf(month) == 7 |
			Integer.valueOf(month) == 8 | Integer.valueOf(month) == 10 |
			Integer.valueOf(month) == 1 ) {
			lastDay = "31";
		}
		if (Integer.valueOf(month) == 2 ) {
			lastDay = "28";
			if ((Integer.valueOf(year) % 4) == 0 & (Integer.valueOf(year) % 100) != 0) {
				lastDay = "29";
			}
		}
		//
		dateText = lastDay + month + year;
		try {
		    result = sdf.parse(dateText);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
        return result;
	}
	
	public static boolean isRunning () {
		try {
		    ServerSocket socket = new ServerSocket(9999, 10, InetAddress.getLocalHost());
		    return false;
		} catch(java.net.BindException b){
		    System.out.println("Already Running...");
		    return true;
		} catch(Exception e){
		    e.printStackTrace();
		    return true;
		}		
	}
	
	public static int genRandomNumber ( int min, int max ) {
	    //Generate random int value from min to max 
	    System.out.println("Random value in int from "+min+" to "+max+ ":");
	    int randomInt = (int)Math.floor(Math.random()*(max-min+1)+min);
	    System.out.println(randomInt);
	    return randomInt;
	}
	
	public static int calcCheckDigit ( String numero, int baseMax ) {
        int base;
		char c;
		String cadena = "";
		String s;
		int i, n, idx;
		int numeroAux;
		int total;
		int dv;
		
		if ( baseMax == 0 ) {
			base = 11;
		} else {
			base = baseMax;
		}

		// Cambia la ultima letra por ascii en caso que la cedula termine en letra
		for ( i=0, n=numero.length(); i<n; i++) {
			c   = numero.charAt(i);
			idx = (int)c;
			if ( (int)c < 48 || (int)c > 57 ) {
				cadena = cadena + String.valueOf(idx);
			} else {
				cadena = cadena + String.valueOf(c);
			}
		}
		// Calcula el DV 
		int k = 2;
		total = 0;
		for ( i=cadena.length()-1; i>=0; i--) {
			if ( k > base ) {
				k = 2;
			}
			s = "" + cadena.charAt(i);
			numeroAux = Integer.parseInt(s);
			total     = total + ( numeroAux * k);
			k++;
		}
		
		int resto = total % 11;
		if ( resto > 1 ) {
			dv = 11 - resto;
		} else {
			dv = 0;
		}
		return dv;
	}
	
	public static double appRound ( double v, int scale ) {
		/*
		double dbVal = Math.round(v * 100000000) / 100000000D;	
		return dbVal;
		*/
		/*
        //double number = 123.13698;
        BigDecimal bigDecimal = new BigDecimal(v);
        BigDecimal roundedWithScale = bigDecimal.setScale(8, BigDecimal.ROUND_HALF_UP);
        return roundedWithScale.doubleValue();
        */
		//System.out.println("antes redondeo: " + v);
		BigDecimal bd = new BigDecimal(Double.toString(v));
	    bd = bd.setScale(scale, RoundingMode.HALF_UP);
		//System.out.println("valor redondeado: " + bd);
	    return bd.doubleValue();
	}

	public static java.util.Date addDaysToDate ( java.util.Date initialDate, int daysToAdd ){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(initialDate); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);  // numero de días a añadir, o restar en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }

}
