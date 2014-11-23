package net.devgrus;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import net.devgrus.environment.EnvironmentVariables;
import net.devgrus.util.ControlData;
import net.devgrus.util.ControlDate;
import net.devgrus.util.OpenBrowser;
import net.devgrus.util.Utils;
import net.devgrus.util.html.ContentToHTML;
import net.devgrus.util.html.ControlStyleSheet;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JRadioButton;

/**
 * Created by SeoDong on 2014-11-21.
 */
public class Customize extends JFrame {
	
	private JPanel textPanel;
	private JScrollPane textScrollPane;
	private JPanel buttomPanel;
	private JButton previewButton;
	private JButton saveButton;
	private JButton cancelButton;
	
	private JTextArea textArea;
	private JPanel rdbPanel;
	private JRadioButton rdbtnHtml;
	private JRadioButton rdbtnCss;
	private JPanel buttonPanel;
	private ButtonGroup rdbGrp = new ButtonGroup();
	
	/**
	 * Test Module
	 */
	private String testTitle = "Hello, World!";
	private String testDate = ControlDate.getdateS();
	private String testContent = "Every, Body! <a href=\"http://devgrus.net\">devgrus.net</a>";
	private String[] testTags = {"Java", "Study"};
	private String[] testFiles = null;
	private DiaryContent testModule = new DiaryContent(testTitle, testDate, testContent, testTags, testFiles);

	
	public Customize(){
		init();
		initEvent();
	}
	
	private void init() {
		/**
		 * Customize View UI ¼³Á¤
		 */
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(100,100,600,400);
		Dimension windowSize = this.getSize();
		int windowX = Math.max(0, (screenSize.width  - windowSize.width ) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		this.setLocation(windowX, windowY);
		
		textPanel = new JPanel();
		getContentPane().add(textPanel, BorderLayout.CENTER);
		textPanel.setLayout(new BorderLayout(0, 0));
		
		textScrollPane = new JScrollPane();
		textPanel.add(textScrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		textScrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		buttomPanel = new JPanel();
		getContentPane().add(buttomPanel, BorderLayout.SOUTH);
		buttomPanel.setLayout(new BorderLayout(0, 0));
		
		rdbPanel = new JPanel();
		buttomPanel.add(rdbPanel, BorderLayout.WEST);
		
		rdbtnCss = new JRadioButton("CSS");
		rdbtnCss.setSelected(true);
		rdbPanel.add(rdbtnCss);
		
		rdbtnHtml = new JRadioButton("HTML");
		rdbPanel.add(rdbtnHtml);
		
		
		rdbGrp.add(rdbtnCss);
		rdbGrp.add(rdbtnHtml);
		
		buttonPanel = new JPanel();
		buttomPanel.add(buttonPanel, BorderLayout.CENTER);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		previewButton = new JButton("Preview");
		buttonPanel.add(previewButton);
		previewButton.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		
		saveButton = new JButton("Save");
		buttonPanel.add(saveButton);
		saveButton.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		
		cancelButton = new JButton("Cancel");
		buttonPanel.add(cancelButton);
		cancelButton.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		
		if(ControlData.getCustomize_css()==null || ControlData.getCustomize_css().equals("")){
			for(int i=0; i<ControlStyleSheet.basicStyle.length;i++){
				textArea.append(ControlStyleSheet.basicStyle[i]);
				textArea.append("\n");
			}
		}else {
			textArea.setText(ControlData.getCustomize_css());
		}
		setVisible(true);
	}
	
	private void initEvent() { 
		/* Text Area */
		previewButton.addActionListener(new BtnPreviewAction());		// Preview Button
		saveButton.addActionListener(new BtnSaveAction(this));				// Save Button	
		cancelButton.addActionListener(new BtnCancelAction(this));			// Cancel Button
		
		/* Radio Button */
		rdbtnCss.addItemListener(new RdbBtnItem());
		rdbtnHtml.addItemListener(new RdbBtnItem());
	}
	
	class BtnPreviewAction implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String pathFolder = ".cache/html/";
			
			File folder = new File(pathFolder);
			if(!folder.exists()){
				folder.mkdirs();
			}else{
				Utils.removeDIR(folder.getPath());
				folder.mkdirs();
			}
			
			PrintWriter output;
			try {
				if(rdbtnCss.isSelected()){
					output = new PrintWriter(pathFolder+"style.css");
					output.println(textArea.getText());
					output.close();
					output = new PrintWriter(pathFolder+"preview.html");
					output.println(ContentToHTML.getContentToHTML(testModule));
					output.close();
				}
				
				if(rdbtnHtml.isSelected()){
					output = new PrintWriter(pathFolder+"style.css");
					for(int i=0; i<ControlStyleSheet.basicStyle.length;i++){
						output.println(ControlStyleSheet.basicStyle[i]);
					}
					
					output.close();
					output = new PrintWriter(pathFolder+"preview.html");
					output.println(ContentToHTML.getContentToHTML(testModule, textArea.getText()));
					output.close();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
			OpenBrowser.openURL(folder.getAbsolutePath()+"/preview.html");
		}
		
		private void copyFileUsingFileStreams(File source, File dest)
				throws IOException {
			InputStream input = null;
			OutputStream output = null;
			try {
				input = new FileInputStream(source);
				output = new FileOutputStream(dest);
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buf)) > 0) {
					output.write(buf, 0, bytesRead);
				}
			} finally {
				input.close();
				output.close();
			}
		}
	}
	
	class BtnSaveAction implements ActionListener{
		
		JFrame frame;
		
		BtnSaveAction(JFrame frame){
			this.frame = frame;
		}
		

		@Override
		public void actionPerformed(ActionEvent e) {
			int ret = JOptionPane.showConfirmDialog(null, "If you save it, this application will be closed.", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);

			if(ret == JOptionPane.YES_OPTION) {
				
				if(rdbtnCss.isSelected()){
					ControlData.updateCustomize_css(textArea.getText());
				}
				
				if(rdbtnHtml.isSelected()){
					ControlData.updateCustomize_html(textArea.getText());
				}
				
				Utils.exitDiary();
				frame.dispose();
				System.exit(0);
				
			}	
		}		
	}
	
	class BtnCancelAction implements ActionListener{

		JFrame frame;
		
		BtnCancelAction(JFrame frame){
			this.frame = frame;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
	}
	
	class RdbBtnItem implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.DESELECTED)	return;
			if(rdbtnCss.isSelected()){
				if(ControlData.getCustomize_css()==null || ControlData.getCustomize_css().equals("")){
					for(int i=0; i<ControlStyleSheet.basicStyle.length;i++){
						textArea.append(ControlStyleSheet.basicStyle[i]);
						textArea.append("\n");
					}
				}else {
					textArea.setText(ControlData.getCustomize_css());
				}
			}
			
			if(rdbtnHtml.isSelected()){
				if(ControlData.getCustomize_html()==null || ControlData.getCustomize_html().equals("")){
						textArea.setText(ContentToHTML.html);
				}else {
					textArea.setText(ControlData.getCustomize_html());
				}
			}	
		}
		
	}
	
}
