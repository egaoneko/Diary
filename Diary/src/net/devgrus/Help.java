package net.devgrus;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextPane;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import net.devgrus.environment.EnvironmentVariables;
import net.devgrus.environment.HelpVariables;
import net.devgrus.util.html.ControlStyleSheet;

/**
 * Created by SeoDong on 2014-11-22.
 */
public class Help extends JFrame{
	
	private JScrollPane listScrollPane;
	private JList<String> list;
	
	private JScrollPane txtScrollPane;	// Text ScrollPane
	private JTextPane txtArea;			// Text Pane
	private HTMLEditorKit kit;			// HTML Editor
	private StyleSheet styleSheet;		// Style Sheet for HTML
	private Document doc;				// Document for HTML

	Help(){
		init();
	}
	
	private void init() {
		/**
		 * Main View UI ¼³Á¤
		 */
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(100,100,920,600);
		Dimension windowSize = this.getSize();
		int windowX = Math.max(0, (screenSize.width  - windowSize.width ) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		this.setLocation(windowX, windowY);
		
		listScrollPane = new JScrollPane();
		getContentPane().add(listScrollPane, BorderLayout.WEST);
		list = new JList<>();
		list.setListData(HelpVariables.helpListMenu);
		list.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		listScrollPane.setViewportView(list);
		list.addListSelectionListener(new listListSelection());
		
		/**
		 * Text Area
		 */
		txtScrollPane = new JScrollPane();
		getContentPane().add(txtScrollPane, BorderLayout.CENTER);
		txtArea = new JTextPane();
		txtArea.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		txtArea.setEditable(false);
		kit = new HTMLEditorKit();	// Add HTMLEditor
		txtArea.setEditorKit(kit);
		txtScrollPane.setViewportView(txtArea);
		
		/**
		 * HTML & CSS
		 */
		/* add some styles to the html */
		styleSheet = kit.getStyleSheet();
		
		ControlStyleSheet.setStyleSheet(styleSheet);
		
		// create a document, set it on the jeditorpane, then add the html
		doc = kit.createDefaultDocument();
		txtArea.setDocument(doc);
		txtArea.setContentType("text/html");
		txtArea.setText(HelpVariables.helpListContent[0]);
		setVisible(true);
	}
	
	class listListSelection implements ListSelectionListener
	{	
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource() == list ) 
			{
				txtArea.setText(HelpVariables.helpListContent[list.getSelectedIndex()]);
			}
		}
	}
	
}
