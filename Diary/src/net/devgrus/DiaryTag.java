package net.devgrus;

/**
 * Created by SeoDong on 2014-11-14.
 */
public class DiaryTag {
	private String tag;
	private int tag_id;
	private int diary_id;
	
	public DiaryTag(String tag, int tag_id, int diary_id){
		this.tag = tag;
		this.tag_id = tag_id;
		this.diary_id = diary_id;
	}

	/**
	 * @return tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag set tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return tag_id
	 */
	public int getTag_id() {
		return tag_id;
	}

	/**
	 * @param tag_id set tag_id
	 */
	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
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
