package net.devgrus;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import net.devgrus.environment.AquaBarTabbedPaneUI;
import net.devgrus.environment.EnvironmentVariables;
import net.devgrus.util.ContentToHTML;
import net.devgrus.util.ControlData;
import net.devgrus.util.Date;
import net.devgrus.util.OpenBrowser;
import net.devgrus.util.ControlStyleSheet;

import java.awt.SystemColor;
import java.awt.Font;

import org.jdatepicker.impl.*;

/**
 * Created by SeoDong on 2014-10-30.
 */
public class MainView extends JFrame {

	/**
	 * MenuBar
	 */
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnOption;
	private JMenu mnHelp;
	private JMenuItem mntmExport;
	private JMenuItem mntmExit;
	private JMenuItem mntmSetting;
	private JMenuItem mntmCustomize;
	private JMenuItem mntmHelp;
	private JMenuItem mntmInfo;
	private JMenu mnReport;
	private JMenuItem mntmBug;
	private JMenuItem mntmEmail;
	private JMenuItem mntmHomepage;
	
	/**
	 *  Content Pane
	 */
	private JTabbedPane contentPane;	// Content Pane
	
	/* 
	 * Read Tab
	 */
	private JPanel readPane;			// Read

	/* Text Area */
	private JScrollPane readScrollPane;	// Read-Tab ScrollPane
	private JTextPane readTxtArea;		// Read-Tab Text Area
	private HTMLEditorKit kit;			// HTML Editor

	/* File Area */
	private JScrollPane readFileScrollPane;	// File ScrollPane
	private JTextPane readFileTxtArea;	// File Text Area
	
	/* HTMLEditor */
	private StyleSheet styleSheet;		// Style Sheet for HTML
	private Document doc;				// Document for HTML
	
	/*
	 * Edit Tab
	 */
	private JPanel editPane;			// Edit
	
	/* Text Area */
	private JTextField editTxtTitle;	// Title TextField
	private JScrollPane editTxtScrollPane;	// Text ScrollPane
	private JTextArea eidtTxtArea;		// Text Area
	private JTextField editTagTxtField;	// Tag Text Field
	private JPanel editDateJPanel;		// Date Panel
	
	/* Edit JDatePicker */
	private UtilCalendarModel editModel;
	private JDatePanelImpl editDatePanel;
	private JDatePickerImpl editDatePicker;
	
	/* File Area */
	private JScrollPane editFileScrollPane;	// File ScrollPane
	private JTextPane eidtFileTxtArea;	// File Text Area
	private JButton editBtnFile;		// Attached file

	/* Button Area */
	private JPanel editBtnControlPane;	// Button Control Pane
	private JButton editBtnCancle;		// Cancle Button
	private JButton editBtnPreview;		// Preview Button
	private JButton editBtnSave;		// Save Button
	
	/*
	 * New Tab
	 */
	private JPanel newPane;				// New
	
	/* Text Area */
	private JTextField newTxtTitle;		// Title TextField
	private JScrollPane newTxtScrollPane;	// Text ScrollPane
	private JTextArea newTxtArea;		//  Text Area
	private JTextField newTagTxtField;	// Tag Text Field
	private JPanel newJDatePanel;		// Date Panel
	
	/* new JDatePicker */
	private UtilCalendarModel newModel;
	private JDatePanelImpl newDatePanel;
	private JDatePickerImpl newDatePicker;
	
	/* File Area */
	private JScrollPane newFileScrollPane;	// File ScrollPane
	private JTextPane newFileTxtArea;	// File Text Area
	private JButton newBtnFile;			// Attached file

	/* Button Area */
	private JPanel newBtnControlPane;	// Button Control Pane
	private JButton newBtnCancle;		// Cancle Button
	private JButton newBtnPreview;		// Preview Button
	private JButton newBtnSave;			// Save Button

	/** 
	 * List Pane
	 */
	private JPanel listPane;			// List
	private JScrollPane listScrollPane;	// List ScrollPane
	private JList list;					// Diary List
	private JTextField searchTxtField;	// Search Field
	private JButton btnSearch;			// Search Button
	private JComboBox listCbBox1;		// List Combo Box1
	private JComboBox listCbBox2;		// List Combo Box2
	
	/** 
	 * Calendar
	 */
	private JPanel calPanel;			// Calendar Pane
	
	/* JDatePicker */
	private UtilCalendarModel model;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	/**
	 * Test Module
	 */
	private String title = "Hello, World!";
	private String date = Date.getdateS();
	private String content = "Every, Body! <a href=\"http://devgrus.net\">devgrus.net</a>";
	private String[] tags = {"Java", "Study"};
	private String[] files = null;
	private DiaryContent testModule = new DiaryContent(title, date, content, tags, files);
	
	public MainView() {
		setTitle("Diary");
		init();
	}

	/**
	 * Initialization Function
	 */
	private void init() {
		/**
		 * Main View UI ¼³Á¤
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(100,100,1350,750);
		Dimension windowSize = this.getSize();
		int windowX = Math.max(0, (screenSize.width  - windowSize.width ) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		this.setLocation(windowX, windowY);
		
		/**
		 * MenuBar
		 */
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmExport = new JMenuItem("Export");
		mnFile.add(mntmExport);
		
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		mnOption = new JMenu("Option");
		menuBar.add(mnOption);
		
		mntmSetting = new JMenuItem("Setting");
		mnOption.add(mntmSetting);
		
		mntmCustomize = new JMenuItem("Customize");
		mnOption.add(mntmCustomize);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmHelp = new JMenuItem("Help");
		mnHelp.add(mntmHelp);
		
		mnReport = new JMenu("Report");
		mnHelp.add(mnReport);
		
		mntmBug = new JMenuItem("Bug");
		mnReport.add(mntmBug);
		
		mntmEmail = new JMenuItem("Email");
		mnReport.add(mntmEmail);
		
		mntmHomepage = new JMenuItem("Homepage");
		mnHelp.add(mntmHomepage);
		
		mntmInfo = new JMenuItem("Info");
		mnHelp.add(mntmInfo);
		
		/**
		 *  Content Pane
		 */
		contentPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		readPane = new JPanel();
		readPane.setBackground(SystemColor.controlHighlight);
		editPane = new JPanel();
		editPane.setBackground(SystemColor.controlHighlight);
		newPane = new JPanel();
		newPane.setBackground(SystemColor.controlHighlight);
		
		/* Content Pane - Add Tab */
		contentPane.addTab("Read", readPane);
		contentPane.addTab("Edit", editPane);
		contentPane.addTab("New", newPane);
		contentPane.setUI(new AquaBarTabbedPaneUI());	// Custom Tab UI
		
		/* 
		 * Read Tab
		 */
		/* Text Area */
		readScrollPane = new JScrollPane();
		readTxtArea = new JTextPane();
		readTxtArea.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		readTxtArea.setEditable(false);
		kit = new HTMLEditorKit();	// Add HTMLEditor
		readTxtArea.setEditorKit(kit);
		readScrollPane.setViewportView(readTxtArea);
		
		/* File Area */
		readFileScrollPane = new JScrollPane();
		readFileTxtArea = new JTextPane();
		readFileTxtArea.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		readFileTxtArea.setEditable(false);
		readFileScrollPane.setViewportView(readFileTxtArea);
		
		/* 
		 * Edit Tab
		 */
		/* Text Area */		
		editTxtTitle = new JTextField();
		editTxtTitle.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		editTxtTitle.setToolTipText("Title");
		editTxtTitle.setColumns(10);
		
		editTxtScrollPane = new JScrollPane();
		editTxtScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		eidtTxtArea = new JTextArea();
		eidtTxtArea.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		editTxtScrollPane.setViewportView(eidtTxtArea);
		eidtTxtArea.setLineWrap(true);
		eidtTxtArea.setWrapStyleWord(true);
		
		editTagTxtField = new JTextField();
		editTagTxtField.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		editTagTxtField.setToolTipText("Tag");
		editTagTxtField.setColumns(10);
		
		editDateJPanel = new JPanel();
		editDateJPanel.setBackground(SystemColor.controlHighlight);
		
		/* Edit JDatePicker */
		editModel = new UtilCalendarModel();
		editModel.setDate(Date.getYear(), Date.getMonth()-1, Date.getDay());
		editModel.setSelected(true);
		
		editDatePanel = new JDatePanelImpl(editModel);
		editDatePicker = new JDatePickerImpl(editDatePanel);
		 
		editDateJPanel.add(editDatePicker);
		
		/* File Area */
		editFileScrollPane = new JScrollPane();
		eidtFileTxtArea = new JTextPane();
		eidtFileTxtArea.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		eidtFileTxtArea.setEditable(false);
		editFileScrollPane.setViewportView(eidtFileTxtArea);
		
		editBtnFile = new JButton("Attach a file");
		editBtnFile.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		
		
		/* Button Area */
		editBtnControlPane = new JPanel();
		editBtnControlPane.setBackground(SystemColor.controlHighlight);

		editBtnPreview = new JButton("Preview");
		editBtnPreview.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		editBtnControlPane.add(editBtnPreview);
		
		editBtnSave = new JButton("Save");
		editBtnSave.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		editBtnControlPane.add(editBtnSave);
		
		editBtnCancle = new JButton("Cancle");
		editBtnCancle.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		editBtnControlPane.add(editBtnCancle);
		
		
		/*
		 *  New Tab 
		 */
		/* Text Area */		
		newTxtTitle = new JTextField();
		newTxtTitle.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		newTxtTitle.setToolTipText("Title");
		newTxtTitle.setColumns(10);
		
		newTxtScrollPane = new JScrollPane();
		newTxtArea = new JTextArea();
		newTxtArea.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		newTxtScrollPane.setViewportView(newTxtArea);
		newTxtArea.setLineWrap(true);
		newTxtArea.setWrapStyleWord(true);
		
		newTagTxtField = new JTextField();
		newTagTxtField.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		newTagTxtField.setColumns(10);
		
		newJDatePanel = new JPanel();
		newJDatePanel.setBackground(SystemColor.controlHighlight);
		
		/* new JDatePicker */
		newModel = new UtilCalendarModel();
		newModel.setDate(Date.getYear(), Date.getMonth()-1, Date.getDay());
		newModel.setSelected(true);
		
		newDatePanel = new JDatePanelImpl(newModel);
		newDatePicker = new JDatePickerImpl(newDatePanel);
		 
		newJDatePanel.add(newDatePicker);
		
		/* File Area */
		newFileScrollPane = new JScrollPane();
		newFileTxtArea = new JTextPane();
		newFileTxtArea.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		newFileTxtArea.setEditable(false);
		newFileScrollPane.setViewportView(newFileTxtArea);
		
		newBtnFile = new JButton("Attach a file");
		newBtnFile.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		
		
		/* Button Area */
		newBtnControlPane = new JPanel();
		newBtnControlPane.setBackground(SystemColor.controlHighlight);

		newBtnPreview = new JButton("Preview");
		newBtnPreview.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		newBtnControlPane.add(newBtnPreview);
		
		newBtnSave = new JButton("Save");
		newBtnSave.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		newBtnControlPane.add(newBtnSave);
		
		newBtnCancle = new JButton("Cancle");
		newBtnCancle.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		newBtnControlPane.add(newBtnCancle);
		
		
		/**
		 *  List Pane
		 */
		listPane = new JPanel();
		listScrollPane = new JScrollPane();
		list = new JList();
		list.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 13));
		listScrollPane.setViewportView(list);
		
		searchTxtField = new JTextField();
		searchTxtField.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		searchTxtField.setToolTipText("Search");
		searchTxtField.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.setToolTipText("Search");
		btnSearch.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		
		listCbBox1 = new JComboBox(EnvironmentVariables.cbBoxMenu);
		listCbBox1.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		listCbBox2 = new JComboBox();
		listCbBox2.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		
		/** 
		 * Calendar
		 */
		calPanel = new JPanel();
		calPanel.setBackground(SystemColor.controlHighlight);
		
		/* JDatePicker */
		model = new UtilCalendarModel();
		model.setDate(Date.getYear(), Date.getMonth()-1, Date.getDay());
		model.setSelected(true);
		
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		 
		calPanel.add(datePicker);
		
		/**
		 * HTML & CSS
		 */
		/* add some styles to the html */
		styleSheet = kit.getStyleSheet();
		ControlStyleSheet.setStyleSheet(styleSheet);
		
		// create a document, set it on the jeditorpane, then add the html
		doc = kit.createDefaultDocument();
		readTxtArea.setDocument(doc);
		readTxtArea.setContentType("text/html");
		readTxtArea.setText(ContentToHTML.getContentToHTML(testModule));
		
		ControlData.insertDataByClass(testModule);
		
		/* Add HyperLink */
		readTxtArea.addHyperlinkListener(new HyperlinkListener() {
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
		
		/**
		 * ReadPane
		 */
		GroupLayout gl_readPane = new GroupLayout(readPane);
		gl_readPane.setHorizontalGroup(
			gl_readPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_readPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(readScrollPane, GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(readFileScrollPane, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_readPane.setVerticalGroup(
			gl_readPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_readPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_readPane.createParallelGroup(Alignment.LEADING)
						.addComponent(readFileScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
						.addComponent(readScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE))
					.addContainerGap())
		);
		readPane.setLayout(gl_readPane);
		
		/**
		 * Edit Pane
		 */
		GroupLayout gl_editPane = new GroupLayout(editPane);
		gl_editPane.setHorizontalGroup(
			gl_editPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_editPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_editPane.createParallelGroup(Alignment.LEADING)
						.addComponent(editBtnControlPane, GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
						.addComponent(editTxtScrollPane)
						.addComponent(editTagTxtField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
						.addComponent(editTxtTitle, GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_editPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_editPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(editBtnFile, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
							.addComponent(editFileScrollPane, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
						.addComponent(editDateJPanel, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_editPane.setVerticalGroup(
			gl_editPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_editPane.createParallelGroup(Alignment.LEADING)
						.addComponent(editTxtTitle, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(editDateJPanel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_editPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_editPane.createSequentialGroup()
							.addComponent(editTxtScrollPane, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(editTagTxtField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addComponent(editFileScrollPane, GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_editPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(editBtnControlPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(editBtnFile, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		editPane.setLayout(gl_editPane);
		
		/**
		 * New Pane
		 */
		GroupLayout gl_newPane = new GroupLayout(newPane);
		gl_newPane.setHorizontalGroup(
			gl_newPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_newPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_newPane.createParallelGroup(Alignment.LEADING)
						.addComponent(newBtnControlPane, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
						.addComponent(newTagTxtField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
						.addComponent(newTxtTitle, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
						.addComponent(newTxtScrollPane, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_newPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_newPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(newBtnFile, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
							.addComponent(newFileScrollPane, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
						.addComponent(newJDatePanel, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_newPane.setVerticalGroup(
			gl_newPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_newPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_newPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(newJDatePanel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(newTxtTitle, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_newPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_newPane.createSequentialGroup()
							.addComponent(newTxtScrollPane, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(newTagTxtField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addComponent(newFileScrollPane, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_newPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(newBtnControlPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(newBtnFile, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		newPane.setLayout(gl_newPane);
		
		/**
		 * List Pane
		 */
		GroupLayout gl_listPane = new GroupLayout(listPane);
		gl_listPane.setHorizontalGroup(
			gl_listPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_listPane.createSequentialGroup()
					.addComponent(searchTxtField, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSearch))
				.addGroup(Alignment.LEADING, gl_listPane.createSequentialGroup()
					.addComponent(listCbBox1, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listCbBox2, 0, 115, Short.MAX_VALUE))
				.addComponent(listScrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
		);
		gl_listPane.setVerticalGroup(
			gl_listPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_listPane.createSequentialGroup()
					.addGroup(gl_listPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchTxtField, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_listPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(listCbBox1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(listCbBox2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(listScrollPane, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
		);
		listPane.setLayout(gl_listPane);
		
		/**
		 * MainView
		 */
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(contentPane, GroupLayout.DEFAULT_SIZE, 905, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(calPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(listPane, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(contentPane, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(listPane, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
							.addGap(7)
							.addComponent(calPanel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);	
		getContentPane().setLayout(groupLayout);
	}
}
