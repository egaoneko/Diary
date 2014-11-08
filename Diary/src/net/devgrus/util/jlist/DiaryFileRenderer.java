package net.devgrus.util.jlist;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import net.devgrus.DiaryFile;
import net.devgrus.environment.EnvironmentVariables;
import net.devgrus.util.Utils;

/**
 * Created by SeoDong on 2014-11-08.
 */
public class DiaryFileRenderer extends JLabel implements ListCellRenderer<DiaryFile> {
	
	public DiaryFileRenderer() {
	    setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends DiaryFile> list, DiaryFile diaryFile, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		ImageIcon imageIcon;

		String extension = Utils.getFileExtension(diaryFile.getFilePath());

		if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("gif") || extension.equals("png")) {
			try {
				imageIcon = new ImageIcon(new ImageIcon(diaryFile.getFilePath()).getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH));
			} catch (Exception e) {
				e.printStackTrace();
				imageIcon = new ImageIcon(getClass().getResource(EnvironmentVariables.icons32Path + extension + ".png"));				
			}

		} else {
			try{
				imageIcon = new ImageIcon(getClass().getResource(EnvironmentVariables.icons32Path + extension + ".png"));
			} catch (Exception e) {
				imageIcon = new ImageIcon(getClass().getResource(EnvironmentVariables.icons32Path + "file.png"));
			}
		}

		setIcon(imageIcon);
		setText(diaryFile.getFileName());
		
		if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		
		return this;
	}
}
