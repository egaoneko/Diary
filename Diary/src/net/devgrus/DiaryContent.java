package net.devgrus;

/**
 * Created by SeoDong on 2014-10-31.
 */
public class DiaryContent {
	private String title;
	private String date;
	private String content;
	private String[] tags;
	private String[] files;
	
	
	DiaryContent(){
		this.title = null;
		this.date = null;
		this.content = null;
		this.tags = null;
		this.files = null;
	}
	
	DiaryContent(String title, String date, String content){
		this.title = title;
		this.date = date;
		this.content = content;
		this.tags = null;
		this.files = null;
		
	}
	
	DiaryContent(String title, String date, String content, String[] tags, String[] files){
		this.title = title;
		this.date = date;
		this.content = content;
		this.tags = tags;
		this.files = files;
	}
	
	public boolean isTags(){
		if(tags == null)
				return false;
		return true;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title 설정할 title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date 설정할 date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content 설정할 content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return tags
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * @param tags 설정할 tags
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/**
	 * @return files
	 */
	public String[] getFiles() {
		return files;
	}

	/**
	 * @param files 설정할 files
	 */
	public void setFiles(String[] files) {
		this.files = files;
	}	
}
