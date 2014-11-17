package net.devgrus.util.popup;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Created by SeoDong on 2014-11-14.
 */
public class RJListPopUp extends JPopupMenu{
	private JMenuItem openItem;
	
	public RJListPopUp(){
    	openItem = new JMenuItem("Open");
        add(openItem);
        openItem.setEnabled(false);
    }

	public void setEnabledItem(boolean bool){
    	openItem.setEnabled(bool);
    }
	
	/**
	 * @return openItem
	 */
	public JMenuItem getOpenItem() {
		return openItem;
	}
}
