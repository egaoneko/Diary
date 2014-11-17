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
            		+ "date datetime not null, "
            		+ "content text not null, "
            		+ "tags text, "
            		+ "files text"
            		+ ");");
            stat.executeUpdate("create table if not exists file ("
            		+ "id integer primary key autoincrement, "
            		+ "diary_id integer not null, "
            		+ "filename text not null, "
            		+ "filedata text not null,"
            		+ "FOREIGN KEY(diary_id) REFERENCES diary(id)"
            		+ ");");
            stat.executeUpdate("create table if not exists tag ("
            		+ "id integer primary key autoincrement, "
            		+ "title text not null"
            		+ ");");
            stat.executeUpdate("create table if not exists diary_tag ("
            		+ "id integer primary key autoincrement, "
            		+ "diary_id integer not null, "
            		+ "tag_id integer not null,"
            		+ "FOREIGN KEY(diary_id) REFERENCES diary(id),"
            		+ "FOREIGN KEY(tag_id) REFERENCES tag(id)"
            		+ ");");
            stat.executeUpdate("create table if not exists year ("
            		+ "id integer primary key autoincrement, "
            		+ "diary_year integer not null, "
            		+ "diary_id integer not null,"
            		+ "FOREIGN KEY(diary_id) REFERENCES diary(id)"
            		+ ");");
            stat.executeUpdate("create table if not exists user ("
            		+ "id integer primary key autoincrement,"
            		+ "is_lock text not null,"
            		+ "password text,"
            		+ "lookandfeel text,"
            		+ "customize text"
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
	public static int insertDataByString(String title, String date, String content, String tags, String files) {
		String sql = "insert into diary ( title, date, content, tags, files) values (?, ?, ?, ?, ?)";
		String sql2 = "select * from diary order by id desc limit 1";
		String sql3 = "select count(*) from tag where title = ? ";
		String sql4 = "insert into tag ( title ) values ( ? ) ";
		String sql5 = "select * from tag where title = ? ";
		String sql6 = "insert into diary_tag ( diary_id, tag_id ) values ( ?, ? ) ";
		String sql7 = "insert into year ( diary_year, diary_id ) values ( ?, ? ) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs;
		
		int count = -1;
		int diary_id = 0;
		
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
		return diary_id;
	}
	
	/**
	 * Insert Data By Class
	 * @param diary
	 */
	public static int insertDataByClass(DiaryContent diary) {
		String sql = "insert into diary (title, date, content, tags, files) values (?, ?, ?, ?, ?)";
		String sql2 = "select * from diary order by id desc limit 1";
		String sql3 = "select count(*) from tag where title = ? ";
		String sql4 = "insert into tag ( title ) values ( ? ) ";
		String sql5 = "select * from tag where title = ? ";
		String sql6 = "insert into diary_tag ( diary_id, tag_id ) values ( ?, ? ) ";
		String sql7 = "insert into year ( diary_year, diary_id ) values ( ?, ? ) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs;
		
		int count = -1;
		int diary_id = 0;
		
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
			rs = psmt.executeQuery();
			rs.next();
			diary_id = rs.getInt("id");
			
			psmt = conn.prepareStatement(sql7);
			psmt.setInt(1, ControlDate.getyearStoS(diary.getDate()));
			psmt.setInt(2, diary_id);
			psmt.executeUpdate();
			
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
		return diary_id;
	}
	
	/**
	 * Update Data By Class
	 * @param diary
	 * @param diary_id
	 */
	public static boolean updateDataByClass(DiaryContent diary, int diary_id) {
		String sql = "update diary set title = ?, date = ?, content = ?, tags = ?, files= ? where id = "+diary_id;
		String sql2 = "select * from year where diary_id = "+diary_id;
		String sql3 = "delete from year where diary_id = "+diary_id;
		String sql4 = "insert into year ( diary_year, diary_id ) values ( ?, ? )";
		String sql5 = "delete from diary_tag where diary_id = "+diary_id;		
		String sql6 = "select count(*) from tag where title = ? ";
		String sql7 = "insert into tag ( title ) values ( ? ) ";
		String sql8 = "select * from tag where title = ? ";
		String sql9 = "insert into diary_tag ( diary_id, tag_id ) values ( ?, ? ) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs;
		
		int count = -1;
		boolean success = true;
		
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
			rs = psmt.executeQuery();
			rs.next();
			if(ControlDate.getyearStoS(diary.getDate()) != rs.getInt("diary_year")){
				psmt = conn.prepareStatement(sql3);
				psmt.executeUpdate();
				
				psmt = conn.prepareStatement(sql4);
				psmt.setInt(1, ControlDate.getyearStoS(diary.getDate()));
				psmt.setInt(2, diary_id);
				psmt.executeUpdate();
			}
			
			psmt = conn.prepareStatement(sql5);
			psmt.executeUpdate();
			
			for (int i=0; i<diary.getTags().length; i++){
				psmt = conn.prepareStatement(sql6);
				psmt.setString(1, diary.getTags()[i]);
				rs = psmt.executeQuery();
				
				while (rs.next()){
					count = rs.getInt(1);
				}
				
				if(count == 0){
					psmt = conn.prepareStatement(sql7);
					psmt.setString(1, diary.getTags()[i]);
					psmt.executeUpdate();
				}
				
				psmt = conn.prepareStatement(sql8);
				psmt.setString(1, diary.getTags()[i]);
				rs = psmt.executeQuery();
				
				int tag_id = rs.getInt("id");			
				DiaryTag dt = new DiaryTag(diary.getTags()[i], tag_id, diary_id);
				vc.add(dt);
				count = -1;
			}
			
			for( DiaryTag i : vc){
				psmt = conn.prepareStatement(sql9);
				psmt.setInt(1, i.getDiary_id());
				psmt.setInt(2, i.getTag_id());
				psmt.executeUpdate();
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			success = false;
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
		return success;
	}
	
	/**
	 * Insert File
	 * @param date
	 * @param fileVc
	 */
	public static boolean insertFile(int diary_id, Vector<DiaryFile> fileVc) {
		String sql = "insert into file ( diary_id, filename, filedata) values (?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
		byte[] buf = new byte[1024];
        byte[] file = null;
        int check = 0;
		boolean success = true;
		
		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			
			for( DiaryFile i : fileVc){				
				
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, diary_id);
				psmt.setString(2, i.getFileName());
				bos = new ByteArrayOutputStream();
				fis = new FileInputStream(i.getFilePath());
				
				System.out.println(i.getFilePath());
				
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
				file = bos.toByteArray();
				psmt.setBytes(3, file);
				check = psmt.executeUpdate();
				
				if (check > 0)
	            {
	                System.out.println("Image Uploaded");
	            }
			}
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			success = false;
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
		return success;
	}
	
	/**
	 * Update File
	 * @param date
	 * @param fileVc
	 * @param removedFileVc
	 */
	public static boolean updateFile(int diary_id, Vector<DiaryFile> fileVc, Vector<DiaryFile> removedFileVc) {
		String sql = "delete from file where id = ?";
		String sql2 = "insert into file ( diary_id, filename, filedata) values (?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
		byte[] buf = new byte[1024];
        byte[] file = null;
        int check = 0;
		boolean success = true;
		
		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			
			for( DiaryFile i : removedFileVc){
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, i.getFile_id());
				psmt.executeUpdate();
			}
			
			for( DiaryFile i : fileVc){				
				if(i.getFile_id() == 0){
					psmt = conn.prepareStatement(sql2);
					psmt.setInt(1, diary_id);
					psmt.setString(2, i.getFileName());
					bos = new ByteArrayOutputStream();
					fis = new FileInputStream(i.getFilePath());
					
					System.out.println(i.getFilePath());
					
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
					file = bos.toByteArray();
					psmt.setBytes(3, file);
					check = psmt.executeUpdate();
					
					if (check > 0)
		            {
		                System.out.println("Image Uploaded");
		            }
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			success = false;
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
		return success;
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
				DiaryContent diary = new DiaryContent(rs.getString("title"), rs.getString("date"), rs.getString("content"), rs.getString("tags").replace(", ", ",").split(","), rs.getString("files").replace(", ", ",").split(":"), rs.getInt("id"));
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
				DiaryContent diary = new DiaryContent(rs.getString("title"), rs.getString("date"), rs.getString("content"), rs.getString("tags").replace(", ", ",").split(","), rs.getString("files").replace(", ", ",").split(":"), rs.getInt("id"));
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
	public static Vector<DiaryFile> getFileRead(Vector<DiaryFile> vc, int diary_id, String date) {
		String sql = "select * from file  where diary_id=?";
		String path = null;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		FileOutputStream fos = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, diary_id);
			ResultSet rs = psmt.executeQuery();
			
			String pathFolder = ".cache/read/"+ControlDate.convertdateStoS(date)+diary_id;
			
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
			
			while(rs.next()){			
				path = ".cache/read/"+ControlDate.convertdateStoS(date)+diary_id+"/"+rs.getString("filename");			
				
				InputStream is = (InputStream) rs.getBinaryStream("filedata");
				fos = new FileOutputStream(path);

				byte[] b = new byte[BUFFER_SIZE];
				int n;
				while ((n = is.read(b)) > 0) {
					fos.write(b, 0, n);
				}
				
				DiaryFile file = new DiaryFile(rs.getString("filename"), path, rs.getInt("id"), rs.getInt("diary_id"));
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
	 * Get File for Edit
	 * @param Data
	 * @return
	 */
	public static Vector<DiaryFile> getFileEdit(Vector<DiaryFile> vc, int diary_id, String date) {
		String sql = "select * from file  where diary_id=?";
		String path = null;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		FileOutputStream fos = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, diary_id);
			ResultSet rs = psmt.executeQuery();
			
			String pathFolder = ".cache/edit/"+ControlDate.convertdateStoS(date)+diary_id;
			
			File folder = new File(".cache/edit/");
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
			
			while(rs.next()){			
				path = ".cache/edit/"+ControlDate.convertdateStoS(date)+diary_id+"/"+rs.getString("filename");			
				
				InputStream is = (InputStream) rs.getBinaryStream("filedata");
				fos = new FileOutputStream(path);

				byte[] b = new byte[BUFFER_SIZE];
				int n;
				while ((n = is.read(b)) > 0) {
					fos.write(b, 0, n);
				}
				
				DiaryFile file = new DiaryFile(rs.getString("filename"), path, rs.getInt("id"), rs.getInt("diary_id"));
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
	 * Get Diary_ID By Tag
	 * @param tag
	 * @return
	 */
	public static int[] getDiaryIDByTag(String tag) {
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
				DiaryContent diary = new DiaryContent(rs.getString("title"), rs.getString("date"), rs.getString("content"), rs.getString("tags").replace(", ", ",").split(","), rs.getString("files").replace(", ", ",").split(":"), rs.getInt("id"));
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
	 * Get Diary Year
	 * @return
	 */
	public static String[] getDiaryYear() {
		String sql = "select DISTINCT diary_year from year order by diary_year DESC ";
		String sql2 = "select count(DISTINCT diary_year) from year";
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
				data[i] = rs.getString("diary_year");
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
	 * Get Diary_ID By Tag
	 * @param tag
	 * @return
	 */
	public static int[] getDiaryIDByYear(String year) {
		String sql = "select count(*) from year where diary_year = ?";
		String sql2 = "select * from year where diary_year = ?";
		
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
			psmt.setInt(1, Integer.parseInt(year));
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()){
				  count = rs.getInt(1);
			}
			
			diary_id = new int[count];
			
			psmt = conn.prepareStatement(sql2);
			psmt.setInt(1, Integer.parseInt(year));
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
	public static Vector<DiaryContent> getDiaryRead(Vector<DiaryContent> vc, String search) {
		String sql = "SELECT * FROM diary WHERE title LIKE '%"+search+"%' or content Like '%"+search+"%' order by date DESC";

		Connection conn = null;
		Statement smt = null;
		ResultSet rs;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			
			smt = conn.createStatement();		
			rs = smt.executeQuery(sql);
			
			while (rs.next()){
				DiaryContent diary = new DiaryContent(rs.getString("title"), rs.getString("date"), rs.getString("content"), rs.getString("tags").replace(", ", ",").split(","), rs.getString("files").replace(", ", ",").split(":"), rs.getInt("id"));
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
	 * Get Data for Calendar
	 * @return
	 */
	public static Vector<DiaryContent> getDiaryByCalendar(Vector<DiaryContent> vc, String date) {
		String sql = "SELECT * FROM diary WHERE date LIKE '%"+date+"%' order by date DESC";

		Connection conn = null;
		Statement smt = null;
		ResultSet rs;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			
			smt = conn.createStatement();		
			rs = smt.executeQuery(sql);
			
			while (rs.next()){
				DiaryContent diary = new DiaryContent(rs.getString("title"), rs.getString("date"), rs.getString("content"), rs.getString("tags").replace(", ", ",").split(","), rs.getString("files").replace(", ", ",").split(":"), rs.getInt("id"));
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
	 * Get Data for Calendar
	 * @return
	 */
	public static Vector<Integer> getDiaryByCalendarDays(Vector<Integer> vc, String date) {
		String sql = "SELECT DISTINCT substr(date, 9, 2) as date FROM diary WHERE date LIKE '%"+date+"%' order by date";

		Connection conn = null;
		Statement smt = null;
		ResultSet rs;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			
			smt = conn.createStatement();		
			rs = smt.executeQuery(sql);
			
			while (rs.next()){
				Integer day = Integer.parseInt(rs.getString("date"));
				vc.add(day);	
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
	
	public static boolean removeDiary(int diary_id) {
		String sql = "delete from diary where id = "+diary_id;
		String sql2 = "delete from diary_tag where diary_id = "+diary_id;
		String sql3 = "delete from file where diary_id = "+diary_id;
		String sql4 = "delete from year where diary_id = "+diary_id;
		Connection conn = null;
		Statement smt = null;
		
		boolean success = true;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			
			smt = conn.createStatement();		
			smt.executeUpdate(sql);
			
			smt = conn.createStatement();		
			smt.executeUpdate(sql2);
			
			smt = conn.createStatement();		
			smt.executeUpdate(sql3);
			
			smt = conn.createStatement();		
			smt.executeUpdate(sql4);		
			
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			success = false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}
	
	/**
	 * Is User?
	 * @return
	 */
	public static boolean isUser() {
		String sql = "select count(*) from user";

		int count = 0;
		boolean ret = false;

		Connection conn = null;
		Statement smt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			smt = conn.createStatement();
			
			ResultSet rs = smt.executeQuery(sql);			
			while (rs.next()){
				  count = rs.getInt(1);
			}
			if(count != 0) ret = true;
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
		return ret;
	}
	
	/**
	 * Is Lock?
	 * @return
	 */
	public static boolean isLock() {
		String sql = "select is_lock from user";

		boolean ret = false;

		Connection conn = null;
		Statement smt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			smt = conn.createStatement();
			
			ResultSet rs = smt.executeQuery(sql);				
			ret = Boolean.parseBoolean(rs.getString("is_lock"));
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
		return ret;
	}
	
	/**
	 * Check Password
	 * @return
	 */
	public static boolean checkPw(String password) {
		String sql = "select password from user";

		boolean ret = false;

		Connection conn = null;
		Statement smt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			smt = conn.createStatement();
			
			ResultSet rs = smt.executeQuery(sql);			
			
			String pw = rs.getString("password");
			
			if(pw.equals(password))
				ret = true;
			
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
		return ret;
	}
	
	/**
	 * Get Look And Feel
	 * @return
	 */
	public static String getLookAndFeel() {
		String sql = "select lookandfeel from user";

		String ret = "";

		Connection conn = null;
		Statement smt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			smt = conn.createStatement();
			
			ResultSet rs = smt.executeQuery(sql);				
			ret = rs.getString("lookandfeel");
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
		return ret;
	}
	
	/**
	 * Update Password
	 * @return
	 */
	public static int updatePw(String password) {
		String sql = "select id from user";
		String sql2 = "update user set is_lock = 'true', password = ? where id = ?";

		int ret = 0;

		Connection conn = null;
		PreparedStatement psmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);

			psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			ret = rs.getInt("id");	
			
			if(ret != 0){
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, password);
				psmt.setInt(2, ret);
				ret = psmt.executeUpdate();
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
		return ret;
	}
	
	/**
	 * Remove Password
	 * @return
	 */
	public static int removePw() {
		String sql = "select id from user";
		String sql2 = "update user set is_lock = 'false', password = '' where id = ?";

		int ret = 0;

		Connection conn = null;
		PreparedStatement psmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);

			psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			ret = rs.getInt("id");	
			
			if(ret != 0){
				psmt = conn.prepareStatement(sql2);
				psmt.setInt(1, ret);
				ret = psmt.executeUpdate();
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
		return ret;
	}
	
	/**
	 * insertUser
	 * @return
	 */
	public static int insertUser(String password, boolean lock) {
		
		boolean is_Look = lock;
		
		String sql ="";	
		if(is_Look)
			sql = "insert into user ( is_lock, password, lookandfeel ) values ( 'true', '"+password+"', 'Sea Glass')";
		else
			sql = "insert into user ( is_lock, lookandfeel ) values ( 'false' , 'Sea Glass')";

		Connection conn = null;
		Statement smt = null;
		
		String[] tags = null;
		
		int rs = 0;

		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);			
			smt = conn.createStatement();			
			rs = smt.executeUpdate(sql);			
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
		return rs;
	}
}
