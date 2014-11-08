package net.devgrus;

import net.devgrus.util.Utils;

/**
 * Created by SeoDong on 2014-11-08.
 */
public class DiaryFile {
	private String fileName;
	private String filePath;
	
	public DiaryFile(String fileName, String filePath){
		this.fileName = fileName;
		this.filePath = filePath;
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
}
