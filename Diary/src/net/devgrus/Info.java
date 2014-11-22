package net.devgrus;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;

public class Info extends JFrame {
	
	Info(){
		init();
	}
	
	private void init() {
		/**
		 * Main View UI ¼³Á¤
		 */
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(100,100,350,200);
		Dimension windowSize = this.getSize();
		int windowX = Math.max(0, (screenSize.width  - windowSize.width ) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		this.setLocation(windowX, windowY);
		
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		getContentPane().add(textPane, BorderLayout.CENTER);
		
		textPane.setText("<div style=\"text-align:center\"><h1>Devgrus Diary</h1><br/>(c) Copyright 2014 Donghyun All rights reserved.<br/>Visit <a href=\"http://devgrus.net\">http://devgrus.net/</a><br/>Version 1.0.0, Build 141123</div>");
	
		setVisible(true);
	}
}
