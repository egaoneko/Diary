package net.devgrus;

import net.devgrus.util.ControlDate;

/**
 * Created by SeoDong on 2014-10-31.
 */
public class DiaryContent {
	private String title;
	private String date;
	private String content;
	private String[] tags;
	private String[] files;
	private int diary_id=0;
	
	
	public DiaryContent(){
		this.title = "";
		this.date = "";
		this.content = "";
		this.tags = null;
		this.files = null;
	}
	
	public DiaryContent(String title, String date, String content){
		this.title = title;
		this.date = date;
		this.content = content;
		this.tags = null;
		this.files = null;
		
	}
	
	public DiaryContent(String title, String date, String content, String[] tags, String[] files){
		this.title = title;
		this.date = date;
		this.content = content;
		this.tags = tags;
		this.files = files;
	}
	
	public DiaryContent(String title, String date, String content, String[] tags, String[] files, int diary_id){
		this.title = title;
		this.date = date;
		this.content = content;
		this.tags = tags;
		this.files = files;
		this.diary_id = diary_id;
	}
	
	public DiaryContent(DiaryContent dc){
		this.title = dc.title;
		this.date = dc.date;
		this.content = dc.content;
		this.tags = dc.tags;
		this.files = dc.files;
		this.diary_id = dc.diary_id;
	}
	
	@Override
	public String toString() {
		return title + " - " + ControlDate.getdateStoS(date);
	}
	
	
	public boolean isTags(){
		if(tags == null)
				return false;
		return true;
	}
	
	public boolean isFiles(){
		if(files == null)
				return false;
		return true;
	}
	
	/**
	 * @return Tags To String (Java, Study)
	 */
	public String getTags2String(){
		String tag = "";
		
		if(isTags()){
			for(int i=0 ; i < tags.length ; i++){
				if(i != tags.length-1 ) tag = tag + tags[i] + ", ";
				else tag += tags[i];
			}
			return tag;
		}
		return tag;
	}
	
	/**
	 * @return Tags To String (Java, Study)
	 */
	public String getTags2StringNoSpace(){
		String tag = "";
		
		if(isTags()){
			for(int i=0 ; i < tags.length ; i++){
				if(i != tags.length-1 ) tag = tag + tags[i] + ",";
				else tag += tags[i];
			}
			return tag;
		}
		return tag;
	}
	
	/**
	 * @return String To Tags ({"Java", "Study"})
	 */
	public String[] getString2Tags(String stringTags){
		String arrTags[] = stringTags.split(",");
		
		return arrTags;
	}
	
	/**
	 * @return Files To String (Java, Study)
	 */
	public String getFiles2String(){
		String file = "";
		
		if(isFiles()){
			for(int i=0 ; i < files.length ; i++){
				if(i != files.length-1 ) file = file + files[i] + ":";
				else file += files[i];
			}
			return file;
		}
		return file;
	}
	
	/**
	 * @return String To Files ({"Java", "Study"})
	 */
	public String[] getString2Files(String stringFiles){
		String arrFiles[] = stringFiles.split(":");
		
		return arrFiles;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title set title
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
	 * @param date set date
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
	 * @param content set content
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
	 * @param tags set tags
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
	 * @param files set files
	 */
	public void setFiles(String[] files) {
		this.files = files;
	}

	/**
	 * @return diary_id
	 */
	public int getDiary_id() {
		return diary_id;
	}

	/**
	 * @param diary_id set diary_id
	 */
	public void setDiary_id(int diary_id) {
		this.diary_id = diary_id;
	}	
}
