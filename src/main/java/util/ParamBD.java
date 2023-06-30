package util;

import java.util.PropertyResourceBundle;
/**
 * Clase que valida los parametros del archivo de configuracion
 * @author Pangui
 *
 */
public class ParamBD 
{

	public static final String ARCHIVO_CONF = "ParamBD";	
	public static final PropertyResourceBundle prb = (PropertyResourceBundle)PropertyResourceBundle.getBundle(ARCHIVO_CONF);
	
    static public String 	NOMBRE_BASE_DATOS_DEFECTO = "basica";
    static public String 	NOMBRE_BASE_DATOS_DBA = "basicadba";
	 
	static
	{
		cargaParametros();
	}
	/**
	 * Metodo que carga los parametros del archivo de configuracion
	 * ya sean el nombre de la base de datos, puerto socket y
	 * archivos de log
	 */
	public static void cargaParametros()
	{
		try 
		{
			
			try 
			{
				NOMBRE_BASE_DATOS_DEFECTO = prb.getString("NOMBRE_BASE_DATOS_DEFECTO");
				NOMBRE_BASE_DATOS_DBA = prb.getString("NOMBRE_BASE_DATOS_DBA");
			} 
			catch (RuntimeException e) 
			{
			}
		}
		catch (RuntimeException e) 
		{
		}
	}
	/**
	 * Verifica si el parametros existe o no en el archivo de
	 * configuracion
	 * @param param parametro a validar
	 * @return true si el parametro existe o false en caso contrario
	 */
	public static boolean getBoolean(String param )
	{
		String res;
		
		try
		{
			res = prb.getString(param);
		} 
		catch (RuntimeException e) 
		{
			return false;
		}
		
		if (res.equals("true"))
			return true;
		else
			return false;
		
	}

	public static String getString(String paramName) { 
		return prb.getString(paramName);
	}
	
}
