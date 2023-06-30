package business;

import pojo.InvOptions;

public class InventoryMenuController {
    /* 
     * +-------------------------------------------------------------+
     * |                                                             | 
     * |                                                             |
     * | Atributos de la clase de control                            |
     * |                                                             |
     * |                                                             | 
     * +-------------------------------------------------------------+	
     */
	private InvOptions invOptions;
	private String [] menuItems = {" Enviar Remisiones", " Evento Cancelacion", " Evento Inutilizacion", " Enviar a Cliente", " Consultar Lotes", " Salir"};
	private String selectedOption;

	public InvOptions getInvOptions() {
		return invOptions;
	}

	public void setInvOptions(InvOptions invOptions) {
		this.invOptions = invOptions;
	}

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
