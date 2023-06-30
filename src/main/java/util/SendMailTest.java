package util;

/**
 *
 * @author eleon
 */
public class SendMailTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        
        SendEmail mail = new SendEmail();
        
                                     //-- destino         , Suject                     , texto del correo
        String resp = mail.enviarEmail ( "jlcc.py@gmail.com", "Probando envio desde java", "texto del correo electronico", null);
        
        System.out.println("Resp : "+resp); 
       
       
    }
    
}
