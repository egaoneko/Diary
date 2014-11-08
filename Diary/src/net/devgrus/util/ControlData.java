package net.devgrus.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import net.devgrus.DiaryContent;
import net.devgrus.DiaryFile;

/**
 * Created by SeoDong on 2014-11-06.
 */
public class ControlData {
	
	public final static String DATABASE = "./diary.db";
	
	/* init */
	static {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
            Statement stat = conn.createStatement();
            //stat.executeUpdate("drop table if exists diary;");
            stat.executeUpdate("create table if not exists diary ("
            		+ "id integer primary key autoincrement, "
            		+ "title text not null, "
            		+ "date text unique not null, "
            		+ "content text not null, "
            		+ "tags text, "
            		+ "files text"
            		+ ");");
            stat.executeUpdate("create table if not exists file ("
            		+ "id integer primary key autoincrement, "
            		+ "date text not null, "
            		+ "filename text not null, "
            		+ "filedata text not null"
            		+ ");");
        } catch(Exception e) { e.printStackTrace(); }
    }

	/**
	 * Insert Data By String
	 * @param title
	 * @param date
	 * @param content
	 * @param tags
	 * @param files
	 */
	public static void insertDataByString(String title, String date, String content, String tags, String files) {
		String sql = "insert into diary ( title, date, content, tags, files) values (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement psmt = null;
		
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
		}
	}
	
	/**
	 * Insert Data By Class
	 * @param diary
	 */
	public static void insertDataByClass(DiaryContent diary) {
		String sql = "insert into diary ( title, date, content, tags, files) values (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, diary.getTitle());
			psmt.setString(2, diary.getDate());
			psmt.setString(3, diary.getContent());
			psmt.setString(4, diary.getTags2StringNoSpace());
			psmt.setString(5, diary.getFiles2String());
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
		}
	}
	
	/**
	 * Insert File
	 * @param date
	 * @param fileVc
	 */
	public static void insertFile(String date, Vector<DiaryFile> fileVc) {
		String sql = "insert into file ( date, filename, filedata) values (?, ?, ?)";
		Connection conn = null;
		PreparedStatement psmt = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        byte[] image = null;
        int check = 0;
		
		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			
			for( DiaryFile i : fileVc){	
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, date);
				psmt.setString(2, i.getFileName());
				fis = new FileInputStream(i.getFilePath());
				
				try {
		            for (int readNum; (readNum = fis.read(buf)) != -1;)
		            {
		                bos.write(buf, 0, readNum);
		                //no doubt here is 0
		                /*Writes len bytes from the specified byte array starting at offset
		                off to this byte array output stream.*/
		                System.out.println("read " + readNum + " bytes,");
		            }
		        } catch (IOException ex) {
		            System.err.println(ex.getMessage());
		        }
				image = bos.toByteArray();
				psmt.setBytes(3, image);
				check = psmt.executeUpdate();
				
				if (check > 0)
	            {
	                System.out.println("Image Uploaded");
	            }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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
	
	public static void insertFileMysql(String date, Vector<DiaryFile> fileVc) {
		String sql = "insert into file ( date, filename, filedata) values (?, ?, ?)";
		Connection conn = null;
		PreparedStatement psmt = null;
		FileInputStream fis = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
			
			for( DiaryFile i : fileVc){	
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, date);
				psmt.setString(2, i.getFileName());
				fis = new FileInputStream(i.getFilePath());
				psmt.setBinaryStream(3, fis);
				psmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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
	
	public static String[] getDiaryList() {
		String sql = "select * from diary order by date";
		String sql2 = "select count(*) from diary";
		String path = null;
		String data[]=null;
		int i = 0;
		int count = 0;

		Connection conn = null;
		Statement smt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			
			smt = conn.createStatement();
			
			ResultSet rs = smt.executeQuery(sql2);
			
			while (rs.next()){
				  count = rs.getInt(1);
			}
			data=new String[count];

			rs = smt.executeQuery(sql);
			
			while(rs.next()){
				data[i] = rs.getString("title") + " - " + Date.getdateStoSS(rs.getString("date"));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}
	
}
