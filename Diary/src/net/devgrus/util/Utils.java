package net.devgrus.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.devgrus.MainView;

/**
 * Created by SeoDong on 2014-11-07.
 */
public class Utils {

	public static Icon loadIcon(String path) throws IOException {
        InputStream stream = MainView.class.getResourceAsStream(path);
        BufferedImage image = ImageIO.read(stream);
        return new ImageIcon(image);
    }
	
	public static String getFileExtension(String path) {
		String[] fileName = path.split("\\.");
		return fileName[fileName.length-1];
	}
	
	public static String getFileName(String path) {
		return new File(path).getName();
	}
	
	public static void fileExecute(String path) {
		//String command = "cmd /c start " + "\"" + path + "\"";
	    try {
			Process child = Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", path});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
