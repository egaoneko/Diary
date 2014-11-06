package net.devgrus.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.devgrus.DiaryContent;

public class ControlData {
	
	public final static String DATABASE = "./diary.db";
	
	static {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
            Statement stat = conn.createStatement();
            //stat.executeUpdate("drop table if exists diary;");
            stat.executeUpdate("create table if not exists diary ("
            		+ "id integer primary key autoincrement, "
            		+ "title string not null, "
            		+ "date string unique not null, "
            		+ "content string not null, "
            		+ "tags string, "
            		+ "files string"
            		+ ");");
        } catch(Exception e) { e.printStackTrace(); }
    }

	public static void insertDataByString(String title, String date, String content, String tags, String files) {
		String sql = "insert into diary ( title, date, content, tags, files) values (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement psmt = null;
		FileInputStream fis = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, date);
			psmt.setString(3, content);
			psmt.setString(4, tags);
			psmt.setString(5, files);
			//fis = new FileInputStream(files[0]);
			//psmt.setBinaryStream(6, fis);
			psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void insertDataByClass(DiaryContent diary) {
		String sql = "insert into diary ( title, date, content, tags, files) values (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement psmt = null;
		FileInputStream fis = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, diary.getTitle());
			psmt.setString(2, diary.getDate());
			psmt.setString(3, diary.getContent());
			psmt.setString(4, diary.getTags2String());
			psmt.setString(5, diary.getFiles2String());
			//fis = new FileInputStream(files[0]);
			//psmt.setBinaryStream(6, fis);
			psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
