package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
* 
* @author eleon
*
*/
public class ClientRSPdf 
{
	 public static final String DOWNLOAD_FILE_LOCATION = "/tmp/";
	
	 private static ClientConfig clientConfig = null;
     private static Client client = null;
     private static WebTarget webTarget = null;
     private static Invocation.Builder invocationBuilder = null;
     private static Response response = null;
     private static InputStream inputStream = null;
     private static OutputStream outputStream = null;
     private static int responseCode;
     private static String responseMessageFromServer = null;
     private static String responseString = null;
     private static String qualifiedDownloadFilePath = null;
     
     
     
    /**
    * 
    * @param urlApi
    * @return
    * @throws IOException
    */
	public static String getFilePDF(String urlApi, String fileName) throws IOException 
	{
			try 
			{
				// Invocar el servicio después de configurar los parámetros necesarios
				clientConfig = new ClientConfig();
				clientConfig.register(MultiPartFeature.class);
				client = ClientBuilder.newClient(clientConfig);
				client.property("accept", "application/pdf");
				webTarget = client.target(urlApi).path(fileName);
	
				// Invocamos al service
				invocationBuilder = webTarget.request();
				// invocationBuilder.header("Authorization", "Basic " + authorization); //
				// Sitenemos algun tipo de seguridad Basico
				response = invocationBuilder.get();
	
				// Obtenemos el código de respuesta
				responseCode = response.getStatus();
				System.out.println("Response code: " + responseCode);
	
				if (response.getStatus() != 200) {
					throw new RuntimeException("Error Codigo de respuesta HTTP : " + responseCode);
				}
	
				// Obtenemos el mensaje de respuesta
				responseMessageFromServer = response.getStatusInfo().getReasonPhrase();
				System.out.println("ResponseMessageFromServer: " + responseMessageFromServer);
	
				// Leemos la cadena de Respuesta
				inputStream = response.readEntity(InputStream.class);
				qualifiedDownloadFilePath = DOWNLOAD_FILE_LOCATION + fileName+".pdf";
				outputStream = new FileOutputStream(qualifiedDownloadFilePath);
				byte[] buffer = new byte[1024];
				int bytesRead;
	
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
	
				// Preparamos la respuesta de proceeso exitoso con informacion sobre el archivo descargado
				responseString = "downloaded successfully at " + qualifiedDownloadFilePath;
	
				return responseString;
			} catch (Exception ex) 
			{
				ex.printStackTrace();
			} finally 
			{
				// Liberamos los recursos
				if( outputStream != null ) 
				{
					outputStream.close();
				}
				if( response != null ) 
				{
					response.close();
				}
				if( client != null ) 
				{
					client.close();
				}
			}

		return responseString;

	}

}
