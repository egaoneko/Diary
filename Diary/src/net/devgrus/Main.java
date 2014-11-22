package net.devgrus;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.devgrus.environment.EnvironmentVariables;
import net.devgrus.util.ControlData;
import net.devgrus.util.LookAndFeelUtil;

/**
 * Created by SeoDong on 2014-10-30.
 */
public class Main {

	public static void main(String[] args) {

		LookAndFeelUtil.lookAndFeel(3);			
		login();
	}
	
	public static void login(){
		
		if(!ControlData.isUser()){	
			String pw;
			String rpw;
			
			int ret;
			
			do {
				ret = JOptionPane.showConfirmDialog(null, "Do you want to lock diary?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
			}while(ret == JOptionPane.CLOSED_OPTION);
			
			if(ret == JOptionPane.YES_OPTION) {
				do {
					do{
						pw = JOptionPane.showInputDialog("Enter password.");
						if(pw==null){
							return;
						}
					}while(pw.equals(""));
					
					do{
						rpw= JOptionPane.showInputDialog("Re-enter password.");
						if(rpw==null){
							return;
						}
					}while(rpw.equals(""));
					
				}while (!pw.equals(rpw));
				
				ControlData.insertUser(pw, true);
			}
			else {
				ControlData.insertUser(null, false);
			}
			
			MainView frame = new MainView();
			frame.setVisible(true);
		}
		else {
			
			if(ControlData.isLock()){
				String pw;	
				do{
					pw = JOptionPane.showInputDialog("Enter password.");				
					if(pw == null) return;
				}while(!ControlData.checkPw(pw));
			}
			
			MainView frame = new MainView();
			frame.setVisible(true);
		}
		
	}
}
