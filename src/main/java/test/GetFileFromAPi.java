package test;

import java.io.IOException;
import util.ClientRSPdf;

/**
* 
* @author eleon
*
*/
public class GetFileFromAPi {

	public static void main(String[] args) throws IOException 
	{
		String url = "http://192.168.10.15:8080/ReportServer/api/fe/";
		String idFe = "228801";
		
			ClientRSPdf.DOWNLOAD_FILE_LOCATION="/home/eleon/tmps/";
			System.out.println(ClientRSPdf.getFilePDF(url, idFe));
		
	}

}
