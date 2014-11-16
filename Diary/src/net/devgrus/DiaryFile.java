package net.devgrus;

import net.devgrus.util.Utils;

/**
 * Created by SeoDong on 2014-11-08.
 */
public class DiaryFile {
	private String fileName;
	private String filePath;
	private int file_id = 0;
	private int diary_id = 0;
	
	public DiaryFile(String fileName, String filePath){
		this.fileName = fileName;
		this.filePath = filePath;
	}
	
	public DiaryFile(String fileName, String filePath, int file_id, int diary_id){
		this.fileName = fileName;
		this.filePath = filePath;
		this.file_id = file_id;
		this.diary_id = diary_id;
	}
	
	public DiaryFile(DiaryFile df){
		this.fileName = df.fileName;
		this.filePath = df.filePath;
		this.file_id = df.file_id;
		this.diary_id = df.diary_id;
	}
	
	@Override
	public String toString() {
		return fileName;
	}
	
	/**
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName set fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath set filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return file_id
	 */
	public int getFile_id() {
		return file_id;
	}

	/**
	 * @param file_id set file_id
	 */
	public void setFile_id(int file_id) {
		this.file_id = file_id;
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
