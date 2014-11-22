package net.devgrus.util.html;

import net.devgrus.DiaryContent;
import net.devgrus.util.ControlData;
import net.devgrus.util.ControlDate;

/**
 * Created by SeoDong on 2014-10-31.
 */
public class ContentToHTML {

	private static String title;
	private static String date;
	private static String content;
	private static String tag = "Tag : ";
	private static String[] tags;
	
	public final static String html = "<!DOCTYPE html>\n"
									+"<html>\n"
										+"<head>\n"
											+"<title>[Page Title]</title>\n"
											+"<link rel=\"stylesheet\" href=\"style.css\" type=\"text/css\" />\n"
										+"</head>\n"
										+"<body>\n"
											+"<div id=\"content\">\n"
													+"<div class=\"diary_entry\">\n"
														+"<h3 class=\"diary_title\">[Diary Title]</h3>\n"
														+"<p class=\"diary_info\">[Diary Date]</p>\n"
														+"<div class=\"content_box\">[Diary Content]</div>\n"
														+"<br/><br/>\n"
														+"<div class=\"diary_meta\">[Diary Tag]</div>\n"
													+"</div>\n"
												+"</div>\n"
										+"</body>\n"
									+"</html>\n";
	
	
	public static String getContentToHTML(DiaryContent dContent){
		String HTML = html;
		
		if(ControlData.getCustomize_html()==null || ControlData.getCustomize_html().equals("")){
			HTML = html;
		}else {
			HTML = ControlData.getCustomize_html();
		}
		
		tag = "Tag : ";
		title = dContent.getTitle();
		date = dContent.getDate();
		content = dContent.getContent().replace("\n", "<br/>");

		if(dContent.isTags()){
			tag += dContent.getTags2String();
		}
		
		if(dContent.isTags()){
			HTML = HTML.replace("[Page Title]", title).replace("[Diary Title]", title).replace("[Diary Date]", date).replace("[Diary Content]", content).replace("[Diary Tag]", tag);
		}
		else {
			HTML = HTML.replace("[Page Title]", title).replace("[Diary Title]", title).replace("[Diary Date]", date).replace("[Diary Content]", content);
		}
		return HTML;
	}
	
	public static String getContentToHTML(DiaryContent dContent, String cHtml){
		String HTML = cHtml;
		tag = "Tag : ";
		title = dContent.getTitle();
		date = dContent.getDate();
		content = dContent.getContent().replace("\n", "<br/>");

		if(dContent.isTags()){
			tag += dContent.getTags2String();
		}
		
		if(dContent.isTags()){
			HTML = HTML.replace("[Page Title]", title).replace("[Diary Title]", title).replace("[Diary Date]", date).replace("[Diary Content]", content).replace("[Diary Tag]", tag);
		}
		else {
			HTML = HTML.replace("[Page Title]", title).replace("[Diary Title]", title).replace("[Diary Date]", date).replace("[Diary Content]", content);
		}
		return HTML;
	}
}
