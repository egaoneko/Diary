package net.devgrus.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import net.devgrus.DiaryContent;
import net.devgrus.DiaryFile;
import net.devgrus.DiaryTag;

/**
 * Created by SeoDong on 2014-11-06.
 */
public class ControlData {
	
	private static final int BUFFER_SIZE = 1024;
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
            		+ "date datetime unique not null, "
            		+ "content text not null, "
            		+ "tags text, "
            		+ "files text"
            		+ ");");
            stat.executeUpdate("create table if not exists file ("
            		+ "id integer primary key autoincrement, "
            		+ "date datetime not null, "
            		+ "filename text not null, "
            		+ "filedata text not null"
            		+ ");");
            stat.executeUpdate("create table if not exists tag ("
            		+ "id integer primary key autoincrement, "
            		+ "title text not null"
            		+ ");");
            stat.executeUpdate("create table if not exists diary_tag ("
            		+ "id integer primary key autoincrement, "
            		+ "diary_id text not null, "
            		+ "tag_id text not null"
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
		String sql2 = "select * from diary where date = ? ";
		String sql3 = "select count(*) from tag where title = ? ";
		String sql4 = "insert into tag ( title ) values ( ? ) ";
		String sql5 = "select * from tag where title = ? ";
		String sql6 = "insert into diary_tag ( diary_id, tag_id ) values ( ?, ? ) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs;
		
		int count = -1;
		int diary_id;
		
		String[] arrTags = tags.split(",");
		
		Vector<DiaryTag> vc = new Vector<>();
		
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
			
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, date);
			rs = psmt.executeQuery();
			rs.next();
			diary_id = rs.getInt("id");
			
			for (int i=0; i<arrTags.length; i++){
				psmt = conn.prepareStatement(sql3);
				psmt.setString(1, arrTags[i]);
				rs = psmt.executeQuery();
				
				while (rs.next()){
					count = rs.getInt(1);
				}
				
				if(count == 0){
					psmt = conn.prepareStatement(sql4);
					psmt.setString(1, arrTags[i]);
					psmt.executeUpdate();
				}
				
				psmt = conn.prepareStatement(sql5);
				psmt.setString(1, arrTags[i]);
				rs = psmt.executeQuery();
				
				int tag_id = rs.getInt("id");			
				DiaryTag dt = new DiaryTag(arrTags[i], tag_id, diary_id);
				vc.add(dt);
				count = -1;
			}
			
			for( DiaryTag i : vc){
				psmt = conn.prepareStatement(sql6);
				psmt.setInt(1, i.getDiary_id());
				psmt.setInt(2, i.getTag_id());
				psmt.executeUpdate();
			}
			
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
		String sql2 = "select * from diary where date = ? ";
		String sql3 = "select count(*) from tag where title = ? ";
		String sql4 = "insert into tag ( title ) values ( ? ) ";
		String sql5 = "select * from tag where title = ? ";
		String sql6 = "insert into diary_tag ( diary_id, tag_id ) values ( ?, ? ) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs;
		
		int count = -1;
		int diary_id;
		
		Vector<DiaryTag> vc = new Vector<>();
		
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
			
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, diary.getDate());
			rs = psmt.executeQuery();
			rs.next();
			diary_id = rs.getInt("id");
			
			for (int i=0; i<diary.getTags().length; i++){
				psmt = conn.prepareStatement(sql3);
				psmt.setString(1, diary.getTags()[i]);
				rs = psmt.executeQuery();
				
				while (rs.next()){
					count = rs.getInt(1);
				}
				
				if(count == 0){
					psmt = conn.prepareStatement(sql4);
					psmt.setString(1, diary.getTags()[i]);
					psmt.executeUpdate();
				}
				
				psmt = conn.prepareStatement(sql5);
				psmt.setString(1, diary.getTags()[i]);
				rs = psmt.executeQuery();
				
				int tag_id = rs.getInt("id");			
				DiaryTag dt = new DiaryTag(diary.getTags()[i], tag_id, diary_id);
				vc.add(dt);
				count = -1;
			}
			
			for( DiaryTag i : vc){
				psmt = conn.prepareStatement(sql6);
				psmt.setInt(1, i.getDiary_id());
				psmt.setInt(2, i.getTag_id());
				psmt.executeUpdate();
			}
			
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
	
	/**
	 * Insert File (Mysql)
	 * @param date
	 * @param fileVc
	 */
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
	
	/**
	 * Get Data for List
	 * @return
	 */
	public static String[] getDiaryList() {
		String sql = "select * from diary order by date DESC";
		String sql2 = "select count(*) from diary";
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
				data[i] = rs.getString("title") + " - " + ControlDate.getdateStoS(rs.getString("date"));
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
	
	/**
	 * Get Data for Read
	 * @return
	 */
	public static Vector<DiaryContent> getDiaryRead(Vector<DiaryContent> vc) {
		String sql = "select * from diary order by date DESC Limit 100";

		Connection conn = null;
		Statement smt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			smt = conn.createStatement();
			
			ResultSet rs = smt.executeQuery(sql);			
			while(rs.next()){				
				DiaryContent diary = new DiaryContent(rs.getString("title"), rs.getString("date"), rs.getString("content"), rs.getString("tags").replace(", ", ",").split(","), rs.getString("files").replace(", ", ",").split(":"));
				vc.add(diary);
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
		return vc;
	}
	
	/**
	 * Get Data for Read
	 * @return
	 */
	public static Vector<DiaryContent> getDiaryRead(Vector<DiaryContent> vc, int limit) {
		String sql = "select * from diary order by date DESC Limit "+limit;

		Connection conn = null;
		Statement smt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			smt = conn.createStatement();
			
			ResultSet rs = smt.executeQuery(sql);	
			while(rs.next()){				
				DiaryContent diary = new DiaryContent(rs.getString("title"), rs.getString("date"), rs.getString("content"), rs.getString("tags").replace(", ", ",").split(","), rs.getString("files").replace(", ", ",").split(":"));
				vc.add(diary);
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
		return vc;
	}
	
	/**
	 * Get File for Read
	 * @param Data
	 * @return
	 */
	public static Vector<DiaryFile> getFileRead(Vector<DiaryFile> vc, String date) {
		String sql = "select * from file  where date=?";
		String path = null;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		FileOutputStream fos = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, date);
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()){
				String pathFolder = ".cache/read/"+ControlDate.convertdateStoS(date);
				path = ".cache/read/"+ControlDate.convertdateStoS(date)+"/"+rs.getString("filename");
				
				File folder = new File(".cache/read/");
				if(!folder.exists()){
					folder.mkdirs();
				}else{
					Utils.removeDIR(folder.getPath());
					folder.mkdirs();
				}
					
				folder = new File(pathFolder);
				if(!folder.exists()){
					folder.mkdirs();
				}
				
				InputStream is = (InputStream) rs.getBinaryStream("filedata");
				fos = new FileOutputStream(path);

				byte[] b = new byte[BUFFER_SIZE];
				int n;
				while ((n = is.read(b)) > 0) {
					fos.write(b, 0, n);
				}
				
				DiaryFile file = new DiaryFile(rs.getString("filename"), path);
				vc.add(file);
				fos.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		return vc;
	}
	
	/**
	 * Get Tags
	 * @return
	 */
	public static String[] getTags() {
		String sql = "select * from tag order by title";
		String sql2 = "select count(*) from tag";
		int i = 0;
		int count = 0;

		Connection conn = null;
		Statement smt = null;
		
		String[] tags = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			smt = conn.createStatement();
			
			ResultSet rs = smt.executeQuery(sql2);			
			while (rs.next()){
				  count = rs.getInt(1);
			}
			
			tags = new String[count];

			rs = smt.executeQuery(sql);			
			while(rs.next()){				
				tags[i++] = rs.getString("title");
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
		return tags;
	}
	
	/**
	 * Get Diary_ID From Tag
	 * @param tag
	 * @return
	 */
	public static int[] getDiaryIDFromTag(String tag) {
		String sql = "select * from tag where title = ?";
		String sql2 = "select count(*) from diary_tag where tag_id = ?";
		String sql3 = "select * from diary_tag where tag_id = ?";
		
		int i = 0;
		int count = 0;
		int[] diary_id=null;

		Connection conn = null;
		PreparedStatement psmt = null;


		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);	
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, tag);
			ResultSet rs = psmt.executeQuery();
			
			rs.next();
			int tag_id = rs.getInt("id");
			
			psmt = conn.prepareStatement(sql2);
			psmt.setInt(1, tag_id);
			rs = psmt.executeQuery();
			
			while (rs.next()){
				  count = rs.getInt(1);
			}
			
			diary_id = new int[count];
			
			psmt = conn.prepareStatement(sql3);
			psmt.setInt(1, tag_id);
			rs = psmt.executeQuery();
	
			while(rs.next()){				
				diary_id[i++] = rs.getInt("diary_id");
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
		return diary_id;
	}
	
	/**
	 * Get Data for Read
	 * @return
	 */
	public static Vector<DiaryContent> getDiaryRead(Vector<DiaryContent> vc, int[] diary_id) {
		String sql = "select * from diary where id = ? order by date DESC";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			
			for(int i=0; i<diary_id.length; i++){			
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, diary_id[i]);
				rs = psmt.executeQuery();
				rs.next();
				DiaryContent diary = new DiaryContent(rs.getString("title"), rs.getString("date"), rs.getString("content"), rs.getString("tags").replace(", ", ",").split(","), rs.getString("files").replace(", ", ",").split(":"));
				vc.add(diary);	
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
		return vc;
	}
}
