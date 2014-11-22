package net.devgrus.util;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.jdatepicker.impl.JDatePanelImpl;

import net.devgrus.environment.EnvironmentVariables;

/**
 * Created by SeoDong on 2014-11-07.
 */
public class LookAndFeelUtil extends JFrame {
	
	public static void lookAndFeelDefault(String style) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if (style.equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}
	
	public static void lookAndFeel(int style) {
		try {
			UIManager.setLookAndFeel(EnvironmentVariables.lookandfeel[style][1]);
			
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}
			
	public static void lookAndFeel(String style) {
		try {
			UIManager.setLookAndFeel(style);
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}
	
	public static void lookAndFeel(int style, JDatePanelImpl[] panels) {
		int len = 180;
		
		try {
			UIManager.setLookAndFeel(EnvironmentVariables.lookandfeel[style][1]);
			
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		for(int i=0; i<panels.length; i++){
			panels[i].setPreferredSize(new java.awt.Dimension(200, len));
		}
	}
	
	public static void lookAndFeel(int style, JDatePanelImpl[] panels, JFrame frame) {
		int len = 180;
		
		try {
			UIManager.setLookAndFeel(EnvironmentVariables.lookandfeel[style][1]);
			
			SwingUtilities.updateComponentTreeUI(frame);
			frame.pack();
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		for(int i=0; i<panels.length; i++){
			panels[i].setPreferredSize(new java.awt.Dimension(200, len));
		}
	}
	
	public static void lookAndFeelDatePanel(int style, JDatePanelImpl[] panels) {
		int len = 180;
		
		for(int i=0; i<panels.length; i++){
			panels[i].setPreferredSize(new java.awt.Dimension(200, len));
		}
	}
	
	public static int lookAndFeelNumber(String style){
		int ret = 3;
		
		switch(style){
		case "Nimbus" :
			ret = 0;
			break;
		case "Windows" :
			ret = 1;
			break;
		case "Quaqua" :
			ret = 2;
			break;
		case "Sea Glass" :
			ret = 3;
			break;
		}
		return ret;
	}
}
