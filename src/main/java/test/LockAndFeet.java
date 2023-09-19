package test;

import java.io.File;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class LockAndFeet {

	public static void main(String[] args)
	 {
        //Display system information
        String os_name = System.getProperty("os.name");
        String os_arch = System.getProperty("os.arch");
        String os_ver = System.getProperty("os.version");
        System.out.println("Current System: " + os_name + " " + os_arch + " " + os_ver);
        String java_name = System.getProperty("java.vm.name");
        String java_vendor = System.getProperty("java.vm.vendor");
        String java_ver = System.getProperty("java.vm.version");
        System.out.println("Current Java VM: " + java_name + " (" + java_vendor + ") " + java_ver);
        System.out.println();

        //initial Look and Feel
        LookAndFeel feel = UIManager.getLookAndFeel();
        System.out.println("Look and Feel initially loaded when Java starts");
        System.out.println("Name: " + feel.getName());
        System.out.println("ID: " + feel.getID());
        System.out.println("Description: " + feel.getDescription());
        System.out.println();
        
        //get a list of all the look and feels installed on this system
        UIManager.LookAndFeelInfo[] info1 = UIManager.getInstalledLookAndFeels();
        System.out.println("List of installed Look and Feels:");
        for (int i = 0; i < info1.length; i++)
        {
            System.out.println(info1[i]);
        }
        System.out.println();
        
        //set Look and feel to what the system says is the default Look and Feel.
        try
        {
            String systemLF = UIManager.getSystemLookAndFeelClassName();
            System.out.println("Default system look and feel: " + systemLF);
            System.out.println("Trying to set Default system look and feel");
            System.out.println();
            UIManager.setLookAndFeel(systemLF);         
        }
        catch(Exception e)
        {
            System.out.println("Error with setting default system Look and Feel");
            System.out.println();
        }

        //new system Look and Feel
        LookAndFeel feel2 = UIManager.getLookAndFeel();
        System.out.println("Look and Feel after trying to set default system Look and Feel");
        System.out.println("Name: " + feel2.getName());
        System.out.println("ID: " + feel2.getID());
        System.out.println("Description: " + feel2.getDescription());
        System.out.println();

        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        //System.out.println(file.getAbsolutePath());
        /*Doesn't work on Mac
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);*/
        final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();       
        Icon icon = fc.getUI().getFileView(fc).getIcon(file); //get filetype icon for chosen file
        JOptionPane.showMessageDialog(null, "This is the file icon", "", JOptionPane.INFORMATION_MESSAGE, icon);
    }
	
}
