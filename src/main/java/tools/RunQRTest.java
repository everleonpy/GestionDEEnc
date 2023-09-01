
package tools;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
*
* @author eleon
*/
public class RunQRTest 
{

    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) 
    {   
         JFrame frame = new JFrame( "Tutorial de Java, Swing" );
            frame.addWindowListener( new WindowAdapter() {
              public void windowClosing( WindowEvent evt ){
                System.exit( 0 );
              }
            });
            frame.getContentPane().add( new TestQR(),BorderLayout.CENTER );
            frame.setSize( 819, 510 );
            frame.setVisible( true );
    }
                
 }
    

