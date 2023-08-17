package test;

import java.io.IOException;

import util.ClientRSPdf;

public class GetFileFromAPi {

	public static void main(String[] args) throws IOException 
	{
		String url = "http://localhost:8080/ReportServer/api/fe/";
		String idFe = "228079";
		
			System.out.println(ClientRSPdf.getFilePDF(url, idFe));
		
	}

}
