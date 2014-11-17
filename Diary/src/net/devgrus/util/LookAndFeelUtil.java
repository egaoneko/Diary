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
			
			if(style == 10){
				UIManager.setLookAndFeel(EnvironmentVariables.lookandfeel[style+1][1]);
			}
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
		
		if(style == 1 || style == 6){
			len = 190;
		}
		else if(style == 5 || style == 9){
			len = 200;
		}
		
		try {
			UIManager.setLookAndFeel(EnvironmentVariables.lookandfeel[style][1]);
			
			if(style == 10){
				UIManager.setLookAndFeel(EnvironmentVariables.lookandfeel[style+1][1]);
			}
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		for(int i=0; i<panels.length; i++){
			panels[i].setPreferredSize(new java.awt.Dimension(200, len));
		}
	}
	
	public static void lookAndFeel(int style, JDatePanelImpl[] panels, JFrame frame) {
		int len = 180;
		
		if(style == 1 || style == 6){
			len = 190;
		}
		else if(style == 5 || style == 9){
			len = 200;
		}
		else if(style == 8 || style == 10){
			len = 200;
		}
		
		try {
			UIManager.setLookAndFeel(EnvironmentVariables.lookandfeel[style][1]);
			
			if(style == 10){
				UIManager.setLookAndFeel(EnvironmentVariables.lookandfeel[style+1][1]);
			}
			
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
		
		if(style == 1 || style == 6){
			len = 190;
		}
		else if(style == 5 || style == 9){
			len = 200;
		}
		
		for(int i=0; i<panels.length; i++){
			panels[i].setPreferredSize(new java.awt.Dimension(200, len));
		}
	}
	
	public static int lookAndFeelNumber(String style){
		int ret = 1;
		
		switch(style){
		case "Metal" :
			ret = 0;
			break;
		case "Nimbus" :
			ret = 1;
			break;
		case "CDE/Motif" :
			ret = 2;
			break;
		case "Windows" :
			ret = 3;
			break;
		case "Windows Classic" :
			ret = 4;
			break;
		case "Quaqua" :
			ret = 5;
			break;
		case "Liquid" :
			ret = 6;
			break;
		case "InfoNode" :
			ret = 7;
			break;
		case "Sea Glass" :
			ret = 8;
			break;
		case "Napkin Laf" :
			ret = 9;
			break;
		case "JTattoo" :
			ret = 10;
			break;
		}
		return ret;
	}
}
