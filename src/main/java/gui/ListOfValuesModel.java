package gui;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import util.ListOfValuesItem;

public class ListOfValuesModel extends AbstractListModel {
	
	/**
	 * Esta clase tiene como objetivo servir de modelo de datos
	 * para el objeto JList de una lista de valores
	 */
	
	private static final long serialVersionUID = 1L;
	private ArrayList<ListOfValuesItem> itemsList;

	public ListOfValuesModel(ArrayList<ListOfValuesItem> data) {
		itemsList = data;
	}
	
	public int getSize() {
		return itemsList.size();
	}
	
	public ListOfValuesItem getElementAt(int index) {
		if (index < 0 || index >= getSize())
			return null;
		return itemsList.get(index);
	}
	
}
