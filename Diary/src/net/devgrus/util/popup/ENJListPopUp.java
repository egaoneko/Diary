package net.devgrus.util.popup;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Created by SeoDong on 2014-11-07.
 */
public class ENJListPopUp extends JPopupMenu {
	private JMenuItem openItem;
	private JMenuItem addItem;
    private JMenuItem removeItem;
    private JMenuItem removeAllItem;
    
    public ENJListPopUp(){
    	openItem = new JMenuItem("Open");
        add(openItem);
        openItem.setEnabled(false);
        
    	addItem = new JMenuItem("Add");
        add(addItem);
        
        removeItem = new JMenuItem("Remove");
        add(removeItem);
        removeItem.setEnabled(false);
        
        removeAllItem = new JMenuItem("Remove All");
        add(removeAllItem);
    }
    
    public void setEnabledItem(boolean bool){
    	openItem.setEnabled(bool);
    	removeItem.setEnabled(bool);
    }

	/**
	 * @return openItem
	 */
	public JMenuItem getOpenItem() {
		return openItem;
	}

	/**
	 * @return addItem
	 */
	public JMenuItem getAddItem() {
		return addItem;
	}

	/**
	 * @return removeItem
	 */
	public JMenuItem getRemoveItem() {
		return removeItem;
	}

	/**
	 * @return removeAllItem
	 */
	public JMenuItem getRemoveAllItem() {
		return removeAllItem;
	}
}