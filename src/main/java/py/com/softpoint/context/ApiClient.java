package py.com.softpoint.context;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import util.MsgApp;

public class ApiClient<T> 
{
	
	private final Client client;
	private final MsgApp msgApp;
		
	
	public ApiClient() 
	{
		this.client = ClientBuilder.newClient();
		this.msgApp = new MsgApp();
	}
	
	
	/**
	* 
	* @param apiUrl
	* @param responseType
	* @return Class<T> responseType
	*/
	public T get(String apiUrl, Class<T> responseType) 
	{
        Response response = client.target(apiUrl)
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == 200) {
            String jsonResponse = response.readEntity(String.class);
            try 
            {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(jsonResponse, responseType);
            } catch (Exception e) {
            	msgApp.ErrorMessage(e);
            	//e.printStackTrace();
                return null;
            }
        } else {
            System.err.println("Error al hacer la solicitud. Código de respuesta: " + response.getStatus());
            return null;
        }
    }

    public void close() {
        client.close();
    }

}
