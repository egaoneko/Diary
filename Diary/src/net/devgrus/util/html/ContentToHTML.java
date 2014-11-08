package net.devgrus.util.html;

import net.devgrus.DiaryContent;
import net.devgrus.util.Date;

/**
 * Created by SeoDong on 2014-10-31.
 */
public class ContentToHTML {

	private static String header = 			"<!DOCTYPE html><html><body>";
	private static String beforeContent = 			"<div id=\"content\">";
	private static String beforeDiary_Entry = 			"<div class=\"diary_entry\">";
	private static String beforeTitle = 					"<h3 class=\"diary_title\">";
	private static String title;
	private static String afterTitle = 					"</h3>";
	private static String beforeDate = 					"<p class=diary_info\">";
	private static String date;
	private static String afterDate = 						"</p>";
	private static String beforeContent_Box = 				"<div class=\"content_box\">";
	private static String content;
	private static String afterContent_Box = 				"</div><br/><br/>";
	private static String beforeTags = 						"<div class=\"diary_meta\">";
	private static String tag = "Tag : ";
	private static String[] tags;
	private static String afterTags = 						"</div>";
	private static String afterDiary_Entry = 			"</div>";
	private static String afterContent = 			"</div>";
	private static String footer = 			"</body></html>";
	
	public static String getContentToHTML(DiaryContent dContent){
		String HTML="";
		title = dContent.getTitle();
		date = Date.getdateStoS(dContent.getDate());
		content = dContent.getContent();

		if(dContent.isTags()){
			tag += dContent.getTags2String();
		}
		
		if(dContent.isTags()){
			HTML = header + beforeContent + beforeDiary_Entry 
					+ beforeTitle+title+afterTitle
					+ beforeDate + date + afterDate
					+ beforeContent_Box + content + afterContent_Box
					+ beforeTags+tag +afterTags
					+ afterDiary_Entry+afterContent+footer;
		}
		else {
			HTML = header + beforeContent + beforeDiary_Entry 
					+ beforeTitle+title+afterTitle
					+ beforeDate + date + afterDate
					+ beforeContent_Box + content + afterContent_Box
					+ afterDiary_Entry+afterContent+footer;
		}		
		return HTML;
	}
}