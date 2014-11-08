package net.devgrus.util.jlist;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalToolTipUI;

/**
 * Created by SeoDong on 2014-11-08.
 */
public class ImageToolTip extends JToolTip {
	public ImageToolTip(String path) {
		setUI(new ImageToolTipUI(path));
	}
}

class ImageToolTipUI extends MetalToolTipUI {
	
	private String path;
	
	ImageToolTipUI(String path){
		super();
		this.path = path;
	}
	
	public void paint(Graphics g, JComponent c) {
		FontMetrics metrics = c.getFontMetrics(g.getFont());
		g.setColor(c.getForeground());
		g.drawString(((JToolTip) c).getTipText(), 1, 1);
		g.drawImage(new ImageIcon(path).getImage(), 1, metrics.getHeight(), c);
	}

	public Dimension getPreferredSize(JComponent c) {
		FontMetrics metrics = c.getFontMetrics(c.getFont());
		String tipText = ((JToolTip) c).getTipText();
		if (tipText == null) {
			tipText = "";
		}
		Image image = new ImageIcon(path).getImage();
		int width = SwingUtilities.computeStringWidth(metrics, tipText);
		int height = metrics.getHeight() + image.getHeight(c);

		if (width < image.getWidth(c)) {
			width = image.getWidth(c);
		}
		return new Dimension(width, height);
	}
}
