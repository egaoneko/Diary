package net.devgrus.util;

import javax.swing.text.html.StyleSheet;

/**
 * Created by SeoDong on 2014-10-31.
 */
public class ControlStyleSheet {
	
	private static String[] basicStyle={
		"body {font-size: 18px; font-family: '³ª´® °íµñ','Nanum Gothic','¸¼Àº °íµñ','Malgun Gothic','µ¸¿ò','Dotum','±¼¸²','Gulim', Times New Roman, Times, serif;}",
		"a {text-decoration: none; color: #28A9FF;}",
		"a:hover {color: #2087CC;}",
		"#content {width: 600px;}",
		".diary_entry {border: 2px solid #543224; margin: 10px 0 10px; color: #666; padding: 10px;}",
		".diary_title {font-size: 20px; margin: 0 0 5px 0; background-color: #FFDCD2; color:#FF4B16; padding: 2px 3px;}",
		".diary_title a {color: #FF7255; text-decoration: none;}",
		".diary_title a:hover {color: #CC3C12;}",
		".diary_info {font-size: 12px; margin: 0; border-bottom: 2px solid #FF5A00; text-align:right;}",
		".content_box {margin: 25px 0; padding: 0 2em;}",
		".diary_meta {font-size: 12px; color: #757575; margin: 10px 0 0 10px; padding: 0; text-align:right;}",
		".diary_meta li {display: inline; margin-right: 30px;}"
	};
	
	public static StyleSheet setStyleSheet(StyleSheet styleSheet){
		for(int i=0 ; i < basicStyle.length ; i++ ){
			styleSheet.addRule(basicStyle[i]);
		}
		return styleSheet;
	}
	
	public static StyleSheet setStyleSheet(StyleSheet styleSheet, String[] styleRules){
		for(int i=0 ; i < styleRules.length ; i++ ){
			styleSheet.addRule(styleRules[i]);
		}
		return styleSheet;
	}
}
