package net.devgrus;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import net.devgrus.environment.AquaBarTabbedPaneUI;
import net.devgrus.environment.EnvironmentVariables;
import net.devgrus.util.ControlData;
import net.devgrus.util.ControlDate;
import net.devgrus.util.LookAndFeelUtil;
import net.devgrus.util.OpenBrowser;
import net.devgrus.util.Utils;
import net.devgrus.util.html.ContentToHTML;
import net.devgrus.util.html.ControlStyleSheet;
import net.devgrus.util.jchooser.CustomFileView;
import net.devgrus.util.jchooser.PreFileViewer;
import net.devgrus.util.jlist.DiaryFileRenderer;
import net.devgrus.util.popup.ENJListPopUp;
import net.devgrus.util.popup.LJListPopUp;
import net.devgrus.util.popup.RJListPopUp;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimeZone;
import java.util.Vector;

import org.jdatepicker.impl.*;


/**
 * Created by SeoDong on 2014-10-30.
 */
public class MainView extends JFrame {

	/**
	 * MenuBar
	 */
	private JMenuBar menuBar;
	
	/* File Menu */
	private JMenu mnFile;
	private JMenuItem mntmExport;
	private JMenuItem mntmExit;
	
	/* Option Menu */
	private JMenu mnOption;
	private JMenu mnSetting;
	private JMenuItem mntmChangePassword;
	private JMenuItem mntmRemovePassword;
	private JMenuItem mntmSetPassword;
	private JMenuItem mntmCustomize;
	
	/* Help Menu */
	private JMenu mnHelp;
	private JMenuItem mntmHelp;
	private JMenuItem mntmInfo;
	private JMenuItem mntmHomepage;
	private JMenuItem mntmUpdate;
	
	/**
	 *  Content Pane
	 */
	private JTabbedPane contentPane;	// Content Pane
	private JDatePanelImpl[] datepanels = new JDatePanelImpl[3];	// Date Panels
	
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
	private JList<DiaryFile> readFileList;	// File Text Area
	private DefaultListModel<DiaryFile> readListModel;	// List Model
	private Vector<DiaryFile> readVc = new Vector<>();	// File Vector
	private RJListPopUp readListMenu = new RJListPopUp();	// List Mouse Click Menu
	private String readOpenFilePath = "";		// Open File Path
	
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
	private JTextArea editTxtArea;		// Text Area
	private JTextField editTxtTag;	// Tag Text Field
	private JPanel editDateJPanel;		// Date Panel
	private String editCurrentDate = "";// Edit Current Date
	private int editDiary_Id = 0;		// Edit diary_id
	
	/* Edit JDatePicker */
	private UtilCalendarModel editModel;
	private JDatePanelImpl editDatePanel;
	private JDatePickerImpl editDatePicker;
	
	/* File Area */
	private JScrollPane editFileScrollPane;	// File ScrollPane
	private JList<DiaryFile> editFileList;	// File List Area
	private JButton editBtnFile;		// Attached file
	private DefaultListModel<DiaryFile> editListModel;	// List Model
	private Vector<DiaryFile> editVc = new Vector<>();	// File Vector
	private Vector<DiaryFile> removedFileVc = new Vector<>();	// Removed File
	private ENJListPopUp editListMenu = new ENJListPopUp();	// List Mouse Click Menu
	private String editOpenFilePath = "";	// Open File Path
	private String editRemoveFilePath = "";	// Remove File Path

	/* Button Area */
	private JPanel editBtnControlPane;	// Button Control Pane
	private JButton editBtnCancel;		// Cancel Button
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
	private JTextField newTxtTag;	// Tag Text Field
	private JPanel newJDatePanel;		// Date Panel
	
	/* new JDatePicker */
	private UtilCalendarModel newModel;
	private JDatePanelImpl newDatePanel;
	private JDatePickerImpl newDatePicker;
	
	/* File Area */
	private JScrollPane newFileScrollPane;	// File ScrollPane
	private JList<DiaryFile> newFileList;	// File List Area
	private JButton newBtnFile;			// Attached file
	private DefaultListModel<DiaryFile> newListModel;	// List Model
	private Vector<DiaryFile> newVc = new Vector<>();	// File Vector
	private ENJListPopUp newListMenu = new ENJListPopUp();	// List Mouse Click Menu
	private String newOpenFilePath = "";	// Open File Path
	private String newRemoveFilePath = "";	// Remove File Path

	/* Button Area */
	private JPanel newBtnControlPane;	// Button Control Pane
	private JButton newBtnCancel;		// Cancel Button
	private JButton newBtnPreview;		// Preview Button
	private JButton newBtnSave;			// Save Button

	/** 
	 * List Pane
	 */
	private JPanel listPane;			// List
	private JScrollPane listScrollPane;	// List ScrollPane
	private JList<DiaryContent> list;	// Diary List
	private JTextField searchTxtField;	// Search Field
	private JButton btnSearch;			// Search Button
	
	private JComboBox<String> listCbBox1;		// List Combo Box1
	private JComboBox<String> listCbBox2;		// List Combo Box2
	
	private Vector<DiaryContent> vc = new Vector<>();	// List Vector
	private LJListPopUp listMenu = new LJListPopUp();	// List PopUp
	private DiaryContent listSelected;			// List Right Mouse Select
	
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
	private String testTitle = "Hello, World!";
	private String testDate = ControlDate.getdateS();
	private String testContent = "Every, Body! <a href=\"http://devgrus.net\">devgrus.net</a>";
	private String[] testTags = {"Java", "Study"};
	private String[] testFiles = null;
	private DiaryContent testModule = new DiaryContent(testTitle, testDate, testContent, testTags, testFiles);
	
	public MainView() {
		setTitle("Devgrus Diary");
		init();
		initEvent();
		initRead();
		initEdit();
		//initLookAndFeel();
		updateNewList();
		//testModule();
	}
	
	private void testModule(){
		newTxtTitle.setText(testModule.getTitle());;		// Title TextField
		newTxtArea.setText(testModule.getContent());;		//  Text Area
		newTxtTag.setText(testModule.getTags2String());	// Tag Text Field
	}

	/**
	 * Initialization Function
	 */
	private void init() {
		/**
		 * Main View UI
		 */
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		/* File Menu */
		mnFile = new JMenu("File");
		mnFile.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"file.png")));
		menuBar.add(mnFile);
		
		mntmExport = new JMenuItem("Export");
		mntmExport.setEnabled(false);
		mntmExport.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"export.png")));
		mnFile.add(mntmExport);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"exit.png")));
		mnFile.add(mntmExit);
		
		/* Option Menu */
		mnOption = new JMenu("Option");
		mnOption.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"option.png")));
		menuBar.add(mnOption);
		
		mnSetting = new JMenu("SettingS");
		mnSetting.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"settings.png")));
		mnOption.add(mnSetting);
		
		mntmSetPassword = new JMenuItem("Set Password");
		mntmSetPassword.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"setpw.png")));
		mnSetting.add(mntmSetPassword);
		
		mntmChangePassword = new JMenuItem("Change Password");
		mntmChangePassword.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"changepw.png")));
		mnSetting.add(mntmChangePassword);
		
		mntmRemovePassword = new JMenuItem("Remove Password");
		mntmRemovePassword.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"removepw.png")));
		mnSetting.add(mntmRemovePassword);
		
		mntmCustomize = new JMenuItem("Customize");
		mntmCustomize.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"customize.png")));
		mnOption.add(mntmCustomize);
		
		/* Help Menu */
		mnHelp = new JMenu("Help");
		mnHelp.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"help.png")));
		menuBar.add(mnHelp);
		
		mntmHelp = new JMenuItem("Help");
		mntmHelp.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"help.png")));
		mnHelp.add(mntmHelp);
		
		mntmHomepage = new JMenuItem("Homepage");
		mntmHomepage.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"homepage.png")));
		mnHelp.add(mntmHomepage);
		
		mntmUpdate = new JMenuItem("Update");
		mntmUpdate.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"update.png")));
		mnHelp.add(mntmUpdate);
		
		mntmInfo = new JMenuItem("Info");
		mntmInfo.setIcon(new ImageIcon(getClass().getResource(EnvironmentVariables.menuIconsPath+"info.png")));
		mnHelp.add(mntmInfo);
		
		/**
		 *  Content Pane
		 */
		contentPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 14));
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
		//contentPane.setUI(new AquaBarTabbedPaneUI());	// Custom Tab UI
		
		/* 
		 * Read Tab
		 */
		/* Text Area */
		readScrollPane = new JScrollPane();
		readTxtArea = new JTextPane();
		readTxtArea.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		readTxtArea.setEditable(false);
		kit = new HTMLEditorKit();	// Add HTMLEditor
		readTxtArea.setEditorKit(kit);
		readScrollPane.setViewportView(readTxtArea);
		
		/* File Area */
		readFileScrollPane = new JScrollPane();
		readListModel = new DefaultListModel<>();
		readFileList = new JList<>(readListModel);
		readFileList.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 12));
		readFileScrollPane.setViewportView(readFileList);
		readFileList.setCellRenderer(new DiaryFileRenderer());
		
		/* 
		 * Edit Tab
		 */
		/* Text Area */		
		editTxtTitle = new JTextField();
		editTxtTitle.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		editTxtTitle.setToolTipText("Title");
		editTxtTitle.setColumns(10);
		
		editTxtScrollPane = new JScrollPane();
		editTxtScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		editTxtArea = new JTextArea();
		editTxtArea.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		editTxtScrollPane.setViewportView(editTxtArea);
		editTxtArea.setLineWrap(true);
		editTxtArea.setWrapStyleWord(true);
		
		editTxtTag = new JTextField();
		editTxtTag.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		editTxtTag.setToolTipText("Tag");
		editTxtTag.setColumns(10);
		
		editDateJPanel = new JPanel();
		editDateJPanel.setBackground(SystemColor.controlHighlight);
		
		/* Edit JDatePicker */
		editModel = new UtilCalendarModel();
		editModel.setDate(ControlDate.getYear(), ControlDate.getMonth()-1, ControlDate.getDay());
		editModel.setSelected(true);
		
		editDatePanel = new JDatePanelImpl(editModel);
		editDatePicker = new JDatePickerImpl(editDatePanel);
		editDatePanel.setPreferredSize(new java.awt.Dimension(200, 190));
		 
		editDateJPanel.add(editDatePicker);
		
		/* File Area */
		editFileScrollPane = new JScrollPane();
		editListModel = new DefaultListModel<>();
		editFileList = new JList<> (editListModel);
		editFileList.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 12));
		editFileScrollPane.setViewportView(editFileList);
		editFileList.setCellRenderer(new DiaryFileRenderer());
		
		editBtnFile = new JButton("Attach a file");
		editBtnFile.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 16));
		
		
		/* Button Area */
		editBtnControlPane = new JPanel();
		editBtnControlPane.setBackground(SystemColor.controlHighlight);

		editBtnPreview = new JButton("Preview");
		editBtnPreview.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 14));
		editBtnControlPane.add(editBtnPreview);
		
		editBtnSave = new JButton("Save");
		editBtnSave.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 14));
		editBtnControlPane.add(editBtnSave);
		
		editBtnCancel = new JButton("Cancle");
		editBtnCancel.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 14));
		editBtnControlPane.add(editBtnCancel);
		
		
		/*
		 *  New Tab 
		 */
		/* Text Area */		
		newTxtTitle = new JTextField();
		newTxtTitle.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		newTxtTitle.setToolTipText("Title");
		newTxtTitle.setColumns(10);
		
		newTxtScrollPane = new JScrollPane();
		newTxtArea = new JTextArea();
		newTxtArea.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		newTxtScrollPane.setViewportView(newTxtArea);
		newTxtArea.setLineWrap(true);
		newTxtArea.setWrapStyleWord(true);
		
		newTxtTag = new JTextField();
		newTxtTag.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		newTxtTag.setColumns(10);
		
		newJDatePanel = new JPanel();
		newJDatePanel.setBackground(SystemColor.controlHighlight);
		
		/* new JDatePicker */
		newModel = new UtilCalendarModel();
		newModel.setDate(ControlDate.getYear(), ControlDate.getMonth()-1, ControlDate.getDay());
		newModel.setSelected(true);
		
		newDatePanel = new JDatePanelImpl(newModel);
		newDatePicker = new JDatePickerImpl(newDatePanel);
		newDatePanel.setPreferredSize(new java.awt.Dimension(200, 190));
		 
		newJDatePanel.add(newDatePicker);
		
		/* File Area */
		newFileScrollPane = new JScrollPane();
		newListModel = new DefaultListModel<>();
		newFileList = new JList<>(newListModel);
		newFileList.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 12));
		newFileScrollPane.setViewportView(newFileList);
		newFileList.setCellRenderer(new DiaryFileRenderer());
		
		newBtnFile = new JButton("Attach a file");
		newBtnFile.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 16));
		
		
		/* Button Area */
		newBtnControlPane = new JPanel();
		newBtnControlPane.setBackground(SystemColor.controlHighlight);

		newBtnPreview = new JButton("Preview");
		newBtnPreview.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 14));
		newBtnControlPane.add(newBtnPreview);
		
		newBtnSave = new JButton("Save");
		newBtnSave.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 14));
		newBtnControlPane.add(newBtnSave);
		
		newBtnCancel = new JButton("Cancle");
		newBtnCancel.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 14));
		newBtnControlPane.add(newBtnCancel);
		
		
		/**
		 *  List Pane
		 */
		listPane = new JPanel();
		listScrollPane = new JScrollPane();
		list = new JList<>();
		list.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 13));
		listScrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		searchTxtField = new JTextField();
		searchTxtField.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		searchTxtField.setToolTipText("Search");
		searchTxtField.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.setToolTipText("Search");
		btnSearch.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 12));
		
		listCbBox1 = new JComboBox<>();
		listCbBox1.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));	
		Utils.inputComboBox(listCbBox1, EnvironmentVariables.cbBoxMenu);
		
		listCbBox2 = new JComboBox<>();
		listCbBox2.setFont(new Font(EnvironmentVariables.defaultFont, Font.BOLD, 15));
		
		/** 
		 * Calendar
		 */
		calPanel = new JPanel();
		calPanel.setBackground(SystemColor.controlHighlight);
		
		/* JDatePicker */
		model = new UtilCalendarModel();
		model.setDate(ControlDate.getYear(), ControlDate.getMonth()-1, ControlDate.getDay());
		model.setSelected(true);
		
		datePanel = new JDatePanelImpl(model, this);	
		datePicker = new JDatePickerImpl(datePanel);
		
		/* JDate Resize */
		datePanel.setPreferredSize(new java.awt.Dimension(200, 200));
		newDatePanel.setPreferredSize(new java.awt.Dimension(200, 200));
		editDatePanel.setPreferredSize(new java.awt.Dimension(200, 200));
		 
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
		
		/* Test */
		//readTxtArea.setText(ContentToHTML.getContentToHTML(testModule));
		
		/* Add HyperLink */
		readTxtArea.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent hle) {
				if (HyperlinkEvent.EventType.ACTIVATED.equals(hle
						.getEventType())) {
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
						.addComponent(editBtnControlPane, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
						.addComponent(editTxtScrollPane)
						.addComponent(editTxtTag, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
						.addComponent(editTxtTitle, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_editPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_editPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(editBtnFile, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
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
							.addComponent(editTxtTag, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
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
							.addComponent(newTxtScrollPane)
							.addComponent(newTxtTag, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
							.addComponent(newTxtTitle, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_newPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_newPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(newBtnFile, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
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
							.addComponent(newTxtTag, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
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
			gl_listPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_listPane.createSequentialGroup()
					.addComponent(searchTxtField, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSearch))
				.addGroup(gl_listPane.createSequentialGroup()
					.addComponent(listCbBox1, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listCbBox2, 0, 115, Short.MAX_VALUE))
				.addComponent(listScrollPane, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
		);
		gl_listPane.setVerticalGroup(
			gl_listPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_listPane.createSequentialGroup()
					.addGroup(gl_listPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchTxtField, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_listPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(listCbBox1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(listCbBox2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(listScrollPane, GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE))
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
	
	/**
	 * Add Event Method 
	 */
	private void initEvent() { 
		this.addWindowListener(new WindowEventHandler());	// Exit Diary
		
		/*
		 * Menu Event Listener
		 */
		/* File Menu */
		mntmExport.addActionListener(new mntmExportAction());
		mntmExit.addActionListener(new mntmExitAction(this));
		
		/* Option Menu */
		mntmChangePassword.addActionListener(new mntmChangePasswordAction());
		mntmRemovePassword.addActionListener(new mntmRemovePasswordAction());
		mntmSetPassword.addActionListener(new mntmSetPasswordAction());
		mntmCustomize.addActionListener(new mntmCustomizeAction());
		
		/* Help Menu */
		mntmHelp.addActionListener(new mntmHelpAction());
		mntmInfo.addActionListener(new mntmInfoAction());
		mntmHomepage.addActionListener(new mntmHomepageAction());
		mntmUpdate.addActionListener(new mntmUpdateAction());
		
		/*
		 * New Tab Event Listener
		 */
		/* Text Area */
		newBtnCancel.addActionListener(new BtnCancelAction());		// Cancel Button
		newBtnPreview.addActionListener(new BtnPreviewAction());		// Preview Button
		newBtnSave.addActionListener(new BtnSaveAction());			// Save Button		
		
		/* File Area */
		newBtnFile.addActionListener(new BtnFileAction());	// Attach a File
		newFileList.addMouseMotionListener(new FileListMouseMotion());	// List MouseMotion Listener
		newFileList.addMouseListener(new FileListMouse());	// List Mouse Listener
		newListMenu.getOpenItem().addActionListener(new FileListOpenAction());	// List Right Click Open
		newListMenu.getAddItem().addActionListener(new FileListAddAction());	// List Right Click Add
		newListMenu.getRemoveItem().addActionListener(new FileListRemoveAction());	// List Right Click Remove
		newListMenu.getRemoveAllItem().addActionListener(new FileListRemoveAllAction());	// List Right Click Remove All
		
		/*
		 * Edit Tab Event Listener
		 */
		/* Text Area */
		editBtnCancel.addActionListener(new BtnCancelAction());		// Cancel Button
		editBtnPreview.addActionListener(new BtnPreviewAction());		// Preview Button
		editBtnSave.addActionListener(new BtnSaveAction());			// Save Button	
		
		/* File Area */
		editBtnFile.addActionListener(new BtnFileAction());	// Attach a File
		editFileList.addMouseMotionListener(new FileListMouseMotion());	// List MouseMotion Listener
		editFileList.addMouseListener(new FileListMouse());	// List Mouse Listener
		editListMenu.getOpenItem().addActionListener(new FileListOpenAction());	// List Right Click Open
		editListMenu.getAddItem().addActionListener(new FileListAddAction());	// List Right Click Add
		editListMenu.getRemoveItem().addActionListener(new FileListRemoveAction());	// List Right Click Remove
		editListMenu.getRemoveAllItem().addActionListener(new FileListRemoveAllAction());	// List Right Click Remove All
		
		/*
		 * List Pane Event Listener
		 */
		list.addMouseListener(new ListMouse());	// List Mouse Listener
		list.addKeyListener(new ListKey());		// List Key Listener
		listMenu.getReadItem().addActionListener(new ListReadAction());	// List Right Click Read
		listMenu.getNewItem().addActionListener(new ListNewAction());	// List Right Click New
		listMenu.getEditItem().addActionListener(new ListEditAction());	// List Right Click Edit
		listMenu.getRemoveItem().addActionListener(new ListRemoveAction());	// List Right Click Remove
		
		/* Combo Box Event Listener */
		listCbBox1.addActionListener(new CbBox1Action());	// Combo Box1 Listener
		listCbBox2.addActionListener(new CbBox2Action());	// Combo Box2 Listener
		
		/* Search Event Listener */
		btnSearch.addActionListener(new SearchBtnAction());	// Search Button Listener
		searchTxtField.addKeyListener(new SearchTxtKey());	// Search TextField Listener
			
		/*
		 * Read Pane Event Listener
		 */
		readFileList.addMouseMotionListener(new FileListMouseMotion());	// List MouseMotion Listener
		readFileList.addMouseListener(new ReadFileListMouse());	// List Mouse Listener
		readListMenu.getOpenItem().addActionListener(new FileListOpenAction());	// List Right Click Open
	}
	
	/* initSize() */
	public void pack() {
		super.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(100,100,1350,750);
		Dimension windowSize = this.getSize();
		int windowX = Math.max(0, (screenSize.width  - windowSize.width ) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		this.setLocation(windowX, windowY);
	}
	
	/* Init Read */
	private void initRead(){
		vc.removeAllElements();
		vc = ControlData.getDiaryRead(vc, 1);	
		
		if(!vc.isEmpty()){
	    	readTxtArea.setText(ContentToHTML.getContentToHTML(vc.get(0)));
	    	readListModel.removeAllElements();
	    	readVc.removeAllElements();
	    	ControlData.getFileRead(readVc, vc.get(0).getDiary_id(), vc.get(0).getDate());
	    	for( DiaryFile i : readVc){
	    		readListModel.addElement(i);
	    	}
    	}
	}
	
	/* Init Edit */
	private void initEdit(){
		
		if(!vc.isEmpty()){
			editTxtTitle.setText(vc.get(0).getTitle());
	    	editTxtArea.setText(vc.get(0).getContent());
	    	editTxtTag.setText(vc.get(0).getTags2String());
	    	editListModel.removeAllElements();
	    	editVc.removeAllElements();
	    	ControlData.getFileEdit(editVc, vc.get(0).getDiary_id(), vc.get(0).getDate());
	    	editModel.setDate(ControlDate.getdateForSettingCalender(vc.get(0).getDate(),0)
	    			, ControlDate.getdateForSettingCalender(vc.get(0).getDate(),1)-1
	    			, ControlDate.getdateForSettingCalender(vc.get(0).getDate(),2));
	    	editDiary_Id = vc.get(0).getDiary_id();
	    	for( DiaryFile i : editVc){
	    		editListModel.addElement(i);
	    	}
    	}
	}
	
	/* Init Edit */
	private void initLookAndFeel(){
		datepanels[0] = datePanel;
		datepanels[1] = newDatePanel;
		datepanels[2] = editDatePanel;
		
		LookAndFeelUtil.lookAndFeel(LookAndFeelUtil.lookAndFeelNumber(ControlData.getLookAndFeel()), datepanels, this);
	}
	
	/* Update New List */
	private void updateNewList() {
		vc.removeAllElements();
		vc = ControlData.getDiaryRead(vc);	
		
		DiaryContent[] arr = new DiaryContent[vc.size()];
		arr = (DiaryContent[])vc.toArray(arr);
		
		list.setListData(arr);
	}
	
	/* Update List By ID */
	private void updateListByID(int[] diary_id) {
		vc.removeAllElements();
		vc = ControlData.getDiaryRead(vc, diary_id);	
		
		Collections.sort(vc, new Comparator<DiaryContent>() {
		    public int compare(DiaryContent m1, DiaryContent m2) {
		    	Calendar c1=Calendar.getInstance();
		    	Calendar c2=Calendar.getInstance();;
				TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
				c1.setTimeZone(tz);
				c2.setTimeZone(tz);
		    	
		    	c1.set(ControlDate.getdateForSettingCalender(m1.getDate(),0)
		    			, ControlDate.getdateForSettingCalender(m1.getDate(),1)
		    			, ControlDate.getdateForSettingCalender(m1.getDate(),2)
		    			, ControlDate.getdateForSettingCalender(m1.getDate(),3)
		    			, ControlDate.getdateForSettingCalender(m1.getDate(),4)
		    			, ControlDate.getdateForSettingCalender(m1.getDate(),5)
		    			);
		    	c2.set(ControlDate.getdateForSettingCalender(m2.getDate(),0)
		    			, ControlDate.getdateForSettingCalender(m2.getDate(),1)
		    			, ControlDate.getdateForSettingCalender(m2.getDate(),2)
		    			, ControlDate.getdateForSettingCalender(m2.getDate(),3)
		    			, ControlDate.getdateForSettingCalender(m2.getDate(),4)
		    			, ControlDate.getdateForSettingCalender(m2.getDate(),5)
		    			);

		        return -c1.compareTo(c2);
		    }
		});
		
		DiaryContent[] arr = new DiaryContent[vc.size()];
		arr = (DiaryContent[])vc.toArray(arr);
		
		list.setListData(arr);
	}
	
	/* Update List By Search */
	private void updateListBySearch(String search) {
		vc.removeAllElements();
		vc = ControlData.getDiaryRead(vc, search);	
		
		DiaryContent[] arr = new DiaryContent[vc.size()];
		arr = (DiaryContent[])vc.toArray(arr);
		
		list.setListData(arr);
	}
	
	/* Update List By Calendar */
	public void updateListByCalendar(String date) {
		vc.removeAllElements();
		vc = ControlData.getDiaryByCalendar(vc, date);	
		
		DiaryContent[] arr = new DiaryContent[vc.size()];
		arr = (DiaryContent[])vc.toArray(arr);
		
		list.setListData(arr);
	}
	public void updateListByCalendarClear() {
		vc.removeAllElements();
		
		DiaryContent[] arr = new DiaryContent[vc.size()];
		arr = (DiaryContent[])vc.toArray(arr);
		
		list.setListData(arr);
	}
	public int[] updateListByCalendarDays(String date) {
		Vector<Integer> days = new Vector<>();
		ControlData.getDiaryByCalendarDays(days, date);
		
		int[] arr = new int[days.size()];
		
		for (int i = 0; i<arr.length; i++){
			arr[i] = days.elementAt(i);
		}
		return arr;
	}
	
	/* Exit Diary Event */
	class WindowEventHandler implements WindowListener{
		public WindowEventHandler(){}	
		public void windowActivated(WindowEvent e){}	
		public void windowClosed(WindowEvent e){}	
		public void windowClosing(WindowEvent e){
			Utils.exitDiary();
			JFrame frm=(JFrame)e.getWindow();
			frm.dispose();
			System.exit(0);
		}
		public void windowDeactivated(WindowEvent e){}	
		public void windowDeiconified(WindowEvent e){}	
		public void windowIconified(WindowEvent e){}	
		public void windowOpened(WindowEvent e){} 
	}
	
	/* Attach a File Event */
	class BtnFileAction implements ActionListener
	{
		private JFileChooser chooser;
		private PreFileViewer prf;
		private CustomFileView fileView;
		
		BtnFileAction() {
			chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(true);	//	Choose Multi
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setDragEnabled(true);
			
			/* preview image */
			prf = new PreFileViewer(chooser);
			chooser.setAccessory(prf);
			chooser.addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, prf);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newBtnFile || e.getSource() == editBtnFile) 
			{
				String fileName = "";
				String filePath = "";

				/* Add FileFilter */
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif", "bmp"));
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Audios", "mp3", "ogg", "wav"));
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Documents", "doc", "xls", "ppt", "rtf", "docx", "xlsx", "pptx", "pdf"));
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Zips", "zip", "rar", "tar", "7z", "gz", "bz2"));
				
				/* FileView */
				fileView = new CustomFileView();
		        fileView.setIcons();
		        chooser.setFileView(fileView);
				
				int ret = chooser.showOpenDialog(null);
				if(ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "User cancelled operation. No file was chosen.","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				for(int i=0;i<chooser.getSelectedFiles().length;i++){
					filePath = chooser.getSelectedFiles()[i].getPath();
					fileName = Utils.getFileName(filePath);
					
					/* Check Same File */
					if(e.getSource() == newBtnFile){
						if(isSameFile(fileName, newListModel)) return;
					}
					else{
						if(isSameFile(fileName, editListModel)) return;
					}
					
					/* Add diaryFile */
					DiaryFile diaryFile = new DiaryFile(fileName, filePath);
					
					if(e.getSource() == newBtnFile){
						newListModel.addElement(diaryFile);		// To List
						newVc.add(diaryFile);					// To Vector
					} else {
						editListModel.addElement(diaryFile);	// To List
						editVc.add(diaryFile);					// To Vector
					}
				}
			}
		}
		
		private boolean isSameFile(String fileName, DefaultListModel<DiaryFile> listModel){
			
			for(int i=0; i<listModel.getSize(); i++){
				if(listModel.getElementAt(i).getFileName().equals(fileName)) return true;
			}	
			return false;
		}
	}
	
	/* JList Tool Tip */
	class FileListMouseMotion implements MouseMotionListener {
		@Override
        public void mouseMoved(MouseEvent e) {
			JList<DiaryFile> source = (JList<DiaryFile>) e.getSource();
            ListModel<DiaryFile> m = source.getModel();
            int index = source.locationToIndex(e.getPoint());
            if (index > -1) {
            	String extension = Utils.getFileExtension(m.getElementAt(index).getFilePath());

        		if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("gif") || extension.equals("png")) {
	            	
        			String path="";
        			
        			if(e.getSource() == newFileList) {
        				path = m.getElementAt(index).getFilePath();
        			}
        			else if(e.getSource() == readFileList || e.getSource() == editFileList){
        				File tempFile = new File(m.getElementAt(index).getFilePath());
        				path = tempFile.getAbsolutePath();
        			}
        			
	            	source.setToolTipText(String.format("<html><body><img src='file:\\%s'></body></html>", 
	            			path));
        		} else {
        			source.setToolTipText(m.getElementAt(index).toString());
        		}
            }
        }

		@Override
		public void mouseDragged(MouseEvent e) {		
		}
	}
	
	/* List Right Click Pop Up */
	class FileListMouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger())
	            doPop(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()){
	            doPop(e);
			}
		}
		
		private void doPop(MouseEvent e){
			
			JList<DiaryFile> source = (JList<DiaryFile>) e.getSource();
            ListModel<DiaryFile> m = source.getModel();
            int index = source.locationToIndex(e.getPoint());
            if (index > -1) {
            	
            	if(e.getSource() == newFileList){
            		newRemoveFilePath = m.getElementAt(index).getFilePath();
            		newOpenFilePath = m.getElementAt(index).getFilePath();
            		newListMenu.setEnabledItem(true);
            		newListMenu.show(e.getComponent(), e.getX(), e.getY());            		
            	}
            	else {
            		editRemoveFilePath = m.getElementAt(index).getFilePath();
            		editOpenFilePath = m.getElementAt(index).getFilePath();
            		editListMenu.setEnabledItem(true);
            		editListMenu.show(e.getComponent(), e.getX(), e.getY());
            	}
            } else {
            	if(e.getSource() == newFileList){
            		newRemoveFilePath = "";
            		newOpenFilePath = "";
            		newListMenu.setEnabledItem(false);
            		newListMenu.show(e.getComponent(), e.getX(), e.getY());            		
            	}
            	else {
            		editRemoveFilePath = "";
            		editListMenu.setEnabledItem(false);
            		editOpenFilePath = "";
            		editListMenu.show(e.getComponent(), e.getX(), e.getY());
            	}
            }
	    }	
	}
	
	/* List Right Click Add */
	class FileListAddAction implements ActionListener {
		private JFileChooser chooser;
		private PreFileViewer prf;
		private CustomFileView fileView;
		
		FileListAddAction() {
			chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(true);	//	Choose Multi
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setDragEnabled(true);
			
			/* preview image */
			prf = new PreFileViewer(chooser);
			chooser.setAccessory(prf);
			chooser.addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, prf);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newListMenu.getAddItem() || e.getSource() == editListMenu.getAddItem() ) 
			{
				String fileName = "";
				String filePath = "";

				/* Add FileFilter */
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif", "bmp"));
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Audios", "mp3", "ogg", "wav"));
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Documents", "doc", "xls", "ppt", "rtf", "docx", "xlsx", "pptx", "pdf"));
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Zips", "zip", "rar", "tar", "7z", "gz", "bz2"));
				
				/* FileView */
				fileView = new CustomFileView();
		        fileView.setIcons();
		        chooser.setFileView(fileView);
				
				int ret = chooser.showOpenDialog(null);
				if(ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "User cancelled operation. No file was chosen.","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				for(int i=0;i<chooser.getSelectedFiles().length;i++){
					filePath = chooser.getSelectedFiles()[i].getPath();
					fileName = Utils.getFileName(filePath);
					
					/* Check Same File */
					if(e.getSource() == newListMenu.getAddItem()){
						if(isSameFile(fileName, newListModel)) return;
					}
					else{
						if(isSameFile(fileName, editListModel)) return;
					}
					
					/* Add diaryFile */
					DiaryFile diaryFile = new DiaryFile(fileName, filePath);
					
					if(e.getSource() == newListMenu.getAddItem()){
						newListModel.addElement(diaryFile);		// To List
						newVc.add(diaryFile);					// To Vector
					} else {
						editListModel.addElement(diaryFile);	// To List
						editVc.add(diaryFile);					// To Vector
					}
				}
			}	
		}
		
		private boolean isSameFile(String fileName, DefaultListModel<DiaryFile> listModel){
			
			for(int i=0; i<listModel.getSize(); i++){
				if(listModel.getElementAt(i).getFileName().equals(fileName)){
					return true;
				}
			}	
			return false;
		}		
	}

	/* List Right Click Remove */
	class FileListRemoveAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == newListMenu.getRemoveItem()){
				if(!newRemoveFilePath.equals("")){
					removeList(Utils.getFileName(newRemoveFilePath), newListModel, newVc, 0);
					if(newOpenFilePath.equals(newRemoveFilePath)){
						newOpenFilePath = "";
					}
					newRemoveFilePath = "";
				}
			}
			else{
				if(!editRemoveFilePath.equals("")){
					removeList(Utils.getFileName(editRemoveFilePath), editListModel, editVc, 1);
					if(editOpenFilePath.equals(editRemoveFilePath)){
						editOpenFilePath = "";
					}
					editRemoveFilePath = "";
				}
			}	
		}
		
		private void removeList(String fileName, DefaultListModel<DiaryFile> listModel, Vector<DiaryFile> vc, int set){
			
			for(int i=0; i<listModel.getSize(); i++){
				if(listModel.getElementAt(i).getFileName().equals(fileName)){
					listModel.removeElementAt(i);
					break;
				}
			}
			
			for(DiaryFile i : vc){
				if(i.getFileName().equals(fileName)){
					if(set == 1){
						removedFileVc.add(i);
					}
					vc.removeElement(i);			
					break;
				}
			}
		}
	}

	/* List Right Click Remove All */
	class FileListRemoveAllAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == newListMenu.getRemoveAllItem()) {
				removeAllList(newListModel, newVc);
				newOpenFilePath = "";
				newRemoveFilePath = "";
			} else {
				removeAllList(editListModel, editVc);
				editOpenFilePath = "";
				editRemoveFilePath = "";
			}
		}

		private void removeAllList(DefaultListModel<DiaryFile> listModel, Vector<DiaryFile> vc) {
			listModel.removeAllElements();
			vc.removeAllElements();	
		}
	}
	
	/* List Right Click Open */
	class FileListOpenAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newListMenu.getOpenItem()) {
				Utils.fileExecute(newOpenFilePath);
				newOpenFilePath = "";
			} else if (e.getSource() == editListMenu.getOpenItem()){
				Utils.fileExecute(editOpenFilePath);
				editOpenFilePath = "";
			} else if (e.getSource() == readListMenu.getOpenItem()){
				Utils.fileExecute(readOpenFilePath);
				readOpenFilePath = "";
			}
		}
		
	}
	
	/* Cancel Button */
	class BtnCancelAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == newBtnCancel){
				newTxtTitle.setText("");
				newTxtArea.setText("");
				newTxtTag.setText("");
				newModel.setDate(ControlDate.getYear(), ControlDate.getMonth()-1, ControlDate.getDay());
				newListModel.removeAllElements();
				newVc.removeAllElements();
			}
			else{
				editTxtTitle.setText("");
				editTxtArea.setText("");
				editTxtTag.setText("");
				editModel.setDate(ControlDate.getYear(), ControlDate.getMonth()-1, ControlDate.getDay());
				editListModel.removeAllElements();
				editVc.removeAllElements();
			}
		}	
	}
	
	/* Preview Button */
	class BtnPreviewAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == newBtnPreview){
				Calendar selectedValue = (Calendar) newDatePicker.getModel().getValue();
				
				String title = newTxtTitle.getText();
				String date = ControlDate.getdateS(selectedValue);
				String content = newTxtArea.getText();
				String[] tags = newTxtTag.getText().replace(", ", ",").split(",");
				String[] files = new String[newVc.size()];
				
				for(int i=0; i<newVc.size(); i++){
					files[i] = newVc.get(i).getFileName();
				}
				
				DiaryContent diary = new DiaryContent(title, date, content, tags, files);
				
				Preview prv = new Preview(diary);
			}
			else{
				Calendar selectedValue = (Calendar) editDatePicker.getModel().getValue();
				
				String title = editTxtTitle.getText();
				
				String date;
				if(ControlDate.getdateStoS(editCurrentDate).equals(ControlDate.getdateStoS(ControlDate.getdateS(selectedValue)))){
					date = editCurrentDate;
				}
				else{
					date = ControlDate.getdateS(selectedValue);
				}
				String content = editTxtArea.getText();
				String[] tags = editTxtTag.getText().replace(", ", ",").split(",");
				String[] files = new String[editVc.size()];
				
				for(int i=0; i<editVc.size(); i++){
					files[i] = editVc.get(i).getFileName();
				}
				
				DiaryContent diary = new DiaryContent(title, date, content, tags, files);
				
				Preview prv = new Preview(diary);
			}
		}
		
	}
	
	/* Save Button */
	class BtnSaveAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == newBtnSave){
				int diary_id = 0;
				boolean boolFile = true;
				
				Calendar selectedValue = (Calendar) newDatePicker.getModel().getValue();
				
				String title = newTxtTitle.getText();
				String date = ControlDate.getdateS(selectedValue);
				String content = newTxtArea.getText();
				String[] tags = newTxtTag.getText().replace(", ", ",").split(",");
				String[] files = new String[newVc.size()];
				
				for(int i=0; i<newVc.size(); i++){
					files[i] = newVc.get(i).getFileName();
				}
				
				DiaryContent diary = new DiaryContent(title, date, content, tags, files);
				
				diary_id = ControlData.insertDataByClass(diary);
				
				if(diary_id != 0)
					boolFile = ControlData.insertFile(diary_id, newVc);
				
				newTxtTitle.setText("");
				newTxtArea.setText("");
				newTxtTag.setText("");
				newModel.setDate(ControlDate.getYear(), ControlDate.getMonth()-1, ControlDate.getDay());
				newListModel.removeAllElements();
				newVc.removeAllElements();
				updateNewList();
				
				if(diary_id == 0) {
					JOptionPane.showMessageDialog(null, "Occurrence of error when you save a diary.","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				else if(!boolFile) {
					JOptionPane.showMessageDialog(null, "Occurrence of error when you save a fo;e.","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			else {
				boolean boolDiary = false;
				boolean boolFile = false;
				
				Calendar selectedValue = (Calendar) editDatePicker.getModel().getValue();
				
				String title = editTxtTitle.getText();
				
				String date;
				if(ControlDate.getdateStoS(editCurrentDate).equals(ControlDate.getdateStoS(ControlDate.getdateS(selectedValue)))){
					date = editCurrentDate;
				}
				else{
					date = ControlDate.getdateS(selectedValue);
				}
				String content = editTxtArea.getText();
				String[] tags = editTxtTag.getText().replace(", ", ",").split(",");
				String[] files = new String[editVc.size()];
				
				for(int i=0; i<editVc.size(); i++){
					files[i] = editVc.get(i).getFileName();
				}
				
				DiaryContent diary = new DiaryContent(title, date, content, tags, files, editDiary_Id);
				
				boolDiary = ControlData.updateDataByClass(diary, editDiary_Id);
				
				if(boolDiary)
					boolFile = ControlData.updateFile(editDiary_Id, editVc, removedFileVc);
				
				editTxtTitle.setText("");
				editTxtArea.setText("");
				editTxtTag.setText("");
				editModel.setDate(ControlDate.getYear(), ControlDate.getMonth()-1, ControlDate.getDay());
				editListModel.removeAllElements();
				editVc.removeAllElements();
				removedFileVc.removeAllElements();
				editDiary_Id = 0;
				updateNewList();
				
				if(!boolDiary) {
					JOptionPane.showMessageDialog(null, "Occurrence of error when you save a diary.","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				else if(!boolFile) {
					JOptionPane.showMessageDialog(null, "Occurrence of error when you save a fo;e.","Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
			}			
		}
	}
	
	/* List Mouse Select */
	class ListMouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
	
			if(e.getButton() == MouseEvent.BUTTON1){
				JList<DiaryContent> source = (JList<DiaryContent>) e.getSource();
	            ListModel<DiaryContent> m = source.getModel();
	            int index = source.locationToIndex(e.getPoint());
	            if (index > -1) {
	            	readTxtArea.setText("");
	            	readTxtArea.setText(ContentToHTML.getContentToHTML(m.getElementAt(index)));
	            	readListModel.removeAllElements();
	            	readVc.removeAllElements();
	            	ControlData.getFileRead(readVc, m.getElementAt(index).getDiary_id(), m.getElementAt(index).getDate());
	            	for( DiaryFile i : readVc){
	            		readListModel.addElement(i);
	            	}
	            	
	            	editTxtTitle.setText(m.getElementAt(index).getTitle());
	    	    	editTxtArea.setText(m.getElementAt(index).getContent());
	    	    	editTxtTag.setText(m.getElementAt(index).getTags2String());
	    	    	editListModel.removeAllElements();
	    	    	editVc.removeAllElements();
	    	    	editModel.setDate(ControlDate.getdateForSettingCalender(m.getElementAt(index).getDate(),0)
	    	    			, ControlDate.getdateForSettingCalender(m.getElementAt(index).getDate(),1)-1
	    	    			, ControlDate.getdateForSettingCalender(m.getElementAt(index).getDate(),2));
	    	    	editCurrentDate = m.getElementAt(index).getDate();
	    	    	editDiary_Id = m.getElementAt(index).getDiary_id();
	    	    	ControlData.getFileEdit(editVc, m.getElementAt(index).getDiary_id(), m.getElementAt(index).getDate());
	    	    	for( DiaryFile i : editVc){
	    	    		editListModel.addElement(i);
	    	    	}
	            }
		    }	    
		    else if(e.getButton() == MouseEvent.BUTTON3){
		    	JList<DiaryContent> source = (JList<DiaryContent>) e.getSource();
	            ListModel<DiaryContent> m = source.getModel();
	            int index = source.locationToIndex(e.getPoint());
	            if (index > -1) {
	            	if(e.getSource() == list){
	            		listSelected = m.getElementAt(index);
	            		listMenu.setEnabledItem(true);
	            		listMenu.show(e.getComponent(), e.getX(), e.getY());            		
	            	}
	            	list.setSelectedIndex(index);
	            } else {
	            	if(e.getSource() == list){
	            		listSelected = null;
	            		listMenu.setEnabledItem(false);
	            		listMenu.show(e.getComponent(), e.getX(), e.getY());            		
	            	}
	            }
		    }	
		}

		@Override
		public void mouseReleased(MouseEvent e) {			
	    }		
	}
	
	/* List Key Listener */
	class ListKey implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP){
				JList<DiaryContent> source = (JList<DiaryContent>) e.getSource();
	            ListModel<DiaryContent> m = source.getModel();
	            int index = source.getSelectedIndex()-1;
	            
	            if (index > -1) {
	            	readTxtArea.setText("");
	            	readTxtArea.setText(ContentToHTML.getContentToHTML(m.getElementAt(index)));
	            	readListModel.removeAllElements();
	            	readVc.removeAllElements();
	            	ControlData.getFileRead(readVc, m.getElementAt(index).getDiary_id(), m.getElementAt(index).getDate());
	            	for( DiaryFile i : readVc){
	            		readListModel.addElement(i);
	            	}
	            	
	            	editTxtTitle.setText(m.getElementAt(index).getTitle());
	    	    	editTxtArea.setText(m.getElementAt(index).getContent());
	    	    	editTxtTag.setText(m.getElementAt(index).getTags2String());
	    	    	editListModel.removeAllElements();
	    	    	editVc.removeAllElements();
	    	    	editModel.setDate(ControlDate.getdateForSettingCalender(m.getElementAt(index).getDate(),0)
	    	    			, ControlDate.getdateForSettingCalender(m.getElementAt(index).getDate(),1)-1
	    	    			, ControlDate.getdateForSettingCalender(m.getElementAt(index).getDate(),2));
	    	    	editCurrentDate = m.getElementAt(index).getDate();
	    	    	editDiary_Id = m.getElementAt(index).getDiary_id();
	    	    	ControlData.getFileEdit(editVc, m.getElementAt(index).getDiary_id(), m.getElementAt(index).getDate());
	    	    	for( DiaryFile i : editVc){
	    	    		editListModel.addElement(i);
	    	    	}
	            }
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				JList<DiaryContent> source = (JList<DiaryContent>) e.getSource();
	            ListModel<DiaryContent> m = source.getModel();
	            int index = source.getSelectedIndex()+1;
	            
	            if(index > m.getSize()-1){
	            	return;
	            }
	            
	            if (index > -1) {
	            	readTxtArea.setText("");
	            	readTxtArea.setText(ContentToHTML.getContentToHTML(m.getElementAt(index)));
	            	readListModel.removeAllElements();
	            	readVc.removeAllElements();
	            	ControlData.getFileRead(readVc, m.getElementAt(index).getDiary_id(), m.getElementAt(index).getDate());
	            	for( DiaryFile i : readVc){
	            		readListModel.addElement(i);
	            	}
	            	
	            	editTxtTitle.setText(m.getElementAt(index).getTitle());
	    	    	editTxtArea.setText(m.getElementAt(index).getContent());
	    	    	editTxtTag.setText(m.getElementAt(index).getTags2String());
	    	    	editListModel.removeAllElements();
	    	    	editVc.removeAllElements();
	    	    	editModel.setDate(ControlDate.getdateForSettingCalender(m.getElementAt(index).getDate(),0)
	    	    			, ControlDate.getdateForSettingCalender(m.getElementAt(index).getDate(),1)-1
	    	    			, ControlDate.getdateForSettingCalender(m.getElementAt(index).getDate(),2));
	    	    	editCurrentDate = m.getElementAt(index).getDate();
	    	    	editDiary_Id = m.getElementAt(index).getDiary_id();
	    	    	ControlData.getFileEdit(editVc, m.getElementAt(index).getDiary_id(), m.getElementAt(index).getDate());
	    	    	for( DiaryFile i : editVc){
	    	    		editListModel.addElement(i);
	    	    	}
	            }
			}			
		}

		@Override
		public void keyReleased(KeyEvent e) {			
		}

		@Override
		public void keyTyped(KeyEvent e) {			
		}
		
	}
	
	/* Read Right Click Pop Up */
	class ReadFileListMouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger())
	            doPop(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()){
	            doPop(e);
			}
		}
		
		private void doPop(MouseEvent e){
			
			JList<DiaryFile> source = (JList<DiaryFile>) e.getSource();
            ListModel<DiaryFile> m = source.getModel();
            int index = source.locationToIndex(e.getPoint());
            if (index > -1) {            	
            	if(e.getSource() == readFileList){
            		readOpenFilePath = m.getElementAt(index).getFilePath();
            		readListMenu.setEnabledItem(true);
            		readListMenu.show(e.getComponent(), e.getX(), e.getY());            		
            	} 
            } else {
            	readOpenFilePath = "";
            	readListMenu.setEnabledItem(false);
            	readListMenu.show(e.getComponent(), e.getX(), e.getY());  
            }
	    }	
	}
	
	/* CbBox1 Action Listener */
	class CbBox1Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> cb = (JComboBox<String>) e.getSource();
			listCbBox2.removeAllItems();
			searchTxtField.setText("");
			
			if(cb.getSelectedItem().equals("The New")){
				updateNewList();
				
				if(listCbBox2.getItemCount() != 0)
					listCbBox2.removeAllItems();
			}
			else if(cb.getSelectedItem().equals("Tags")){
				String[] tags = ControlData.getTags();
				
				for(int i=0; i<tags.length;i++)
					listCbBox2.addItem(tags[i]);
			}
			else if(cb.getSelectedItem().equals("Year")){
				String[] years = ControlData.getDiaryYear();
				
				for(int i=0; i<years.length; i++)
					listCbBox2.addItem(years[i]);
			}
			else {
				updateNewList();
				listCbBox2.removeAllItems();
			}	
		}
	}
	
	/* CbBox2 Action Listener */
	class CbBox2Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JComboBox<String> cb = (JComboBox<String>) e.getSource();
			
			if(cb.getItemCount() != 0){
				if(listCbBox1.getSelectedItem().equals("Tags")){
					int[] diary_id = ControlData.getDiaryIDByTag((String)cb.getSelectedItem());
					updateListByID(diary_id);				
				}
				else if(listCbBox1.getSelectedItem().equals("Year")){
					int[] diary_id = ControlData.getDiaryIDByYear((String)cb.getSelectedItem());
					updateListByID(diary_id);
				}
				else {
					updateNewList();
					listCbBox2.removeAllItems();
				}
			}
		}
	}
	
	/* Search Button Action Listener */
	class SearchBtnAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			updateListBySearch(searchTxtField.getText());
		}
	}
	
	/* Search Text Field Key Listener */
	class SearchTxtKey implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				updateListBySearch(searchTxtField.getText());
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {			
		}

		@Override
		public void keyTyped(KeyEvent e) {			
		}
	}
	
	/* List Right Mouse Read Listener */
	class ListReadAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
	        contentPane.setSelectedIndex(contentPane.getTabCount()-3);
	        
	        readTxtArea.setText("");
        	readTxtArea.setText(ContentToHTML.getContentToHTML(listSelected));
        	readListModel.removeAllElements();
        	readVc.removeAllElements();
        	ControlData.getFileRead(readVc, listSelected.getDiary_id(), listSelected.getDate());
        	for( DiaryFile i : readVc){
        		readListModel.addElement(i);
        	}
		}
	}
	
	/* List Right Mouse New Listener */
	class ListNewAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
	        contentPane.setSelectedIndex(contentPane.getTabCount()-1);
		}
	}
	
	/* List Right Mouse New Listener */
	class ListEditAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			contentPane.setSelectedIndex(contentPane.getTabCount()-2);
			
			editTxtTitle.setText(listSelected.getTitle());
	    	editTxtArea.setText(listSelected.getContent());
	    	editTxtTag.setText(listSelected.getTags2String());
	    	editListModel.removeAllElements();
	    	editVc.removeAllElements();
	    	editModel.setDate(ControlDate.getdateForSettingCalender(listSelected.getDate(),0)
	    			, ControlDate.getdateForSettingCalender(listSelected.getDate(),1)-1
	    			, ControlDate.getdateForSettingCalender(listSelected.getDate(),2));
	    	editCurrentDate = listSelected.getDate();
	    	editDiary_Id = listSelected.getDiary_id();
	    	ControlData.getFileEdit(editVc, listSelected.getDiary_id(), listSelected.getDate());
	    	for( DiaryFile i : editVc){
	    		editListModel.addElement(i);
	    	}
		}
	}
	
	/* List Right Mouse New Listener */
	class ListRemoveAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			ControlData.removeDiary(listSelected.getDiary_id());
			listSelected = null;
			updateNewList();
		}
	}
	
	/* Exit Menu Event Listener */
	class mntmExitAction implements ActionListener{
		JFrame frm;
		
		mntmExitAction(JFrame frm){
			this.frm=frm;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mntmExit ) 
			{
				Utils.exitDiary();
				frm.dispose();
				System.exit(0);
			}
		}
	}

	/* Export Menu Event Listener */
	class mntmExportAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 자동 생성된 메소드 스텁
			
		}	
	}
	
	/* SetPassword Menu Event Listener */
	class mntmSetPasswordAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!ControlData.isLock()){	
				String pw;
				String rpw;
				
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
				
				int ret = ControlData.updatePw(pw);
				
				if(ret != 0)
					JOptionPane.showMessageDialog(null, "Setting password is success.","Information",JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Setting password is fail.","Warning",JOptionPane.WARNING_MESSAGE);
			}
			else {			
				JOptionPane.showMessageDialog(null, "You already set your password.","Warning",JOptionPane.WARNING_MESSAGE);
			}	
		}		
	}
	
	/* ChangePassword Menu Event Listener */
	class mntmChangePasswordAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!ControlData.isLock()){	
				String pw;
				String rpw;
				
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
				
				int ret = ControlData.updatePw(pw);
				
				if(ret != 0)
					JOptionPane.showMessageDialog(null, "Setting password is success.","Information",JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Setting password is fail.","Warning",JOptionPane.WARNING_MESSAGE);
			}
			else {			
				String pw;
				String rpw;
				do{
					pw = JOptionPane.showInputDialog("Enter current password.");				
					if(pw == null) return;
				}while(!ControlData.checkPw(pw));
				
				do {
					do{
						pw = JOptionPane.showInputDialog("Enter changed password.");
						if(pw==null){
							return;
						}
					}while(pw.equals(""));				
					do{
						rpw= JOptionPane.showInputDialog("Re-enter changed password.");
						if(rpw==null){
							return;
						}
					}while(rpw.equals(""));				
				}while (!pw.equals(rpw));
				
				int ret = ControlData.updatePw(pw);
				
				if(ret != 0)
					JOptionPane.showMessageDialog(null, "Chaging password is success.","Information",JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Chaging password is fail.","Warning",JOptionPane.WARNING_MESSAGE);
			}
		}		
	}
	
	/* RemovePassword Menu Event Listener */
	class mntmRemovePasswordAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!ControlData.isLock()){	
				JOptionPane.showMessageDialog(null, "You didn't set password.","Warning",JOptionPane.WARNING_MESSAGE);
			}
			else {			
				String pw;
				do{
					pw = JOptionPane.showInputDialog("Enter current password.");				
					if(pw == null) return;
				}while(!ControlData.checkPw(pw));
				
				int ret = ControlData.removePw();
				
				if(ret != 0)
					JOptionPane.showMessageDialog(null, "Removing password is success.","Information",JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Removing password is fail.","Warning",JOptionPane.WARNING_MESSAGE);
			}
		}		
	}
	
	/* Customize Menu Event Listener */
	class mntmCustomizeAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Customize custo = new Customize();
		}		
	}
	
	/* Help Menu Event Listener */
	class mntmHelpAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Help help = new Help();
		}		
	}
	
	/* Info Menu Event Listener */
	class mntmInfoAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Info info = new Info();
		}		
	}
	
	/* Homepage Menu Event Listener */
	class mntmHomepageAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			OpenBrowser.openURL("http://devgrus.net/%EC%86%8C%EA%B7%9C%EB%AA%A8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-java-diary/");
			
		}	
	}

	/* Homepage Menu Event Listener */
	class mntmUpdateAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			OpenBrowser.openURL("https://github.com/egaoneko/Diary");	
		}
		
	}

}
