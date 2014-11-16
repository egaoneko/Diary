package net.devgrus.util.popup;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Created by SeoDong on 2014-11-14.
 */
public class LJListPopUp extends JPopupMenu{
	private JMenuItem readItem;
	private JMenuItem newItem;
	private JMenuItem editItem;
	private JMenuItem removeItem;
	
	public LJListPopUp(){
		readItem = new JMenuItem("Read");
        add(readItem);
        readItem.setEnabled(false);
		
		newItem = new JMenuItem("New");
        add(newItem);
        newItem.setEnabled(true);
        
        editItem = new JMenuItem("Edit");
        add(editItem);
        editItem.setEnabled(false);
        
        removeItem = new JMenuItem("Remove");
        add(removeItem);
        removeItem.setEnabled(false);
    }

	public void setEnabledItem(boolean bool){
		readItem.setEnabled(bool);
		editItem.setEnabled(bool);
    	removeItem.setEnabled(bool);
    }

	/**
	 * @return newItem
	 */
	public JMenuItem getNewItem() {
		return newItem;
	}

	/**
	 * @return editItem
	 */
	public JMenuItem getEditItem() {
		return editItem;
	}

	/**
	 * @return removeItem
	 */
	public JMenuItem getRemoveItem() {
		return removeItem;
	}

	/**
	 * @return readItem
	 */
	public JMenuItem getReadItem() {
		return readItem;
	}
}
