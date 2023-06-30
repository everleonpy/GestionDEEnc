package business;

import pojo.InvOptions;

public class EDocsMenuController {
    /* 
     * +-------------------------------------------------------------+
     * |                                                             | 
     * |                                                             |
     * | Atributos de la clase de control                            |
     * |                                                             |
     * |                                                             | 
     * +-------------------------------------------------------------+	
     */
	private String [] menuItems = {" Facturas y Notas", " Remisiones", " Salir"};
	private String selectedOption;

	public String[] getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(String[] menuItems) {
		this.menuItems = menuItems;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	// getters and setters	
	public ApplicationMessage initForm() {
        return null;
    }   

	public boolean isConnectionAvailable () {
	    try {
       	    return TestSocket.isSocketAlive(AppConfig.serverIPAddress, AppConfig.serverPort);
	    } catch (Exception e) {
	 		return false;   			
	    }
	}

}
