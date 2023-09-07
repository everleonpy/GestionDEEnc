package util;

public class StringTools 
{
	
	/**
	* Divide un String muy largo en variuas lineas con una longitud maxima pasada por le marametro maxAncho
	* @param str
	* @param maxAncho
	* @return
	*/
	public static String cortarString(String str, int maxAncho) 
	{
		int init = 0;
		StringBuilder resp = new StringBuilder();
		while (init < str.length() ) 
		{
			
			  int fin = Math.min(init + maxAncho, str.length());

	            // Extraer una línea de texto
	            String linea = str.substring(init, fin);

	            // Agregar la línea al resultado
	            resp.append(linea);

	            // Agregar un salto de línea si no es la última línea
	            if (fin < str.length()) {
	                resp.append("\n");
	            }

	            // Mover el índice de inicio
	            init = fin;
			
		}
		
		
		return resp.toString();
	}

}
