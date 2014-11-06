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
		this.title = "";
		this.date = "";
		this.content = "";
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
