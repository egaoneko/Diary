package net.devgrus;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import net.devgrus.environment.EnvironmentVariables;
import net.devgrus.util.OpenBrowser;
import net.devgrus.util.html.ContentToHTML;
import net.devgrus.util.html.ControlStyleSheet;

import java.awt.BorderLayout;

/**
 * Created by SeoDong on 2014-11-09.
 */
public class Preview extends JFrame {
	
	private JScrollPane txtScrollPane;	// Text ScrollPane
	private JTextPane txtArea;			// Text Pane
	private HTMLEditorKit kit;			// HTML Editor
	private StyleSheet styleSheet;		// Style Sheet for HTML
	private Document doc;				// Document for HTML
	
	private DiaryContent content;
	
	public Preview(DiaryContent content) {
		this.content = content;
		init();
	}
	
	/**
	 * Initialization Function
	 */
	private void init() {
		/**
		 * Main View UI ¼³Á¤
		 */
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(100,100,820,650);
		Dimension windowSize = this.getSize();
		int windowX = Math.max(0, (screenSize.width  - windowSize.width ) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		this.setLocation(windowX, windowY);
		
		/**
		 * Text Area
		 */
		txtScrollPane = new JScrollPane();
		txtArea = new JTextPane();
		txtArea.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		txtArea.setEditable(false);
		kit = new HTMLEditorKit();	// Add HTMLEditor
		txtArea.setEditorKit(kit);
		txtScrollPane.setViewportView(txtArea);
		getContentPane().add(txtScrollPane, BorderLayout.CENTER);
		
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
		txtArea.setText(ContentToHTML.getContentToHTML(content));
		
		/* Add HyperLink */
		txtArea.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent hle) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                    try {
                        OpenBrowser.openURL(hle.getURL().toURI().toString());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
		});
		setVisible(true);
	}
}
