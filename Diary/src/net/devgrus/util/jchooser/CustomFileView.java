package net.devgrus.util.jchooser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

import net.devgrus.environment.EnvironmentVariables;

/**
 * Created by SeoDong on 2014-11-07.
 */
public class CustomFileView extends FileView {
	
	private final Map<String, Icon> icons = new HashMap<String, Icon>();
    private final Map<File, String> fileDescriptions =
            new HashMap<File, String>();
    private final Map<String, String> typeDescriptions =
            new HashMap<String, String>();

    /**
     * The name of the file.  Do nothing special here. Let
     * the system file view handle this.
     * @see FileView#getName
     */
    @Override
    public String getName(File f) {
        return null;
    }

    /**
     * Adds a human readable description of the file.
     */
    public void putDescription(File f, String fileDescription) {
        fileDescriptions.put(f, fileDescription);
    }

    /**
     * A human readable description of the file.
     *
     * @see FileView#getDescription
     */
    @Override
    public String getDescription(File f) {
        return fileDescriptions.get(f);
    }

    /**
     * Adds a human readable type description for files. Based on "dot"
     * extension strings, e.g: ".gif". Case is ignored.
     */
    public void putTypeDescription(String extension, String typeDescription) {
        typeDescriptions.put(extension, typeDescription);
    }

    /**
     * Adds a human readable type description for files of the type of
     * the passed in file. Based on "dot" extension strings, e.g: ".gif".
     * Case is ignored.
     */
    public void putTypeDescription(File f, String typeDescription) {
        putTypeDescription(getExtension(f), typeDescription);
    }

    /**
     * A human readable description of the type of the file.
     *
     * @see FileView#getTypeDescription
     */
    @Override
    public String getTypeDescription(File f) {
        return typeDescriptions.get(getExtension(f));
    }

    /**
     * Convenience method that returns the "dot" extension for the
     * given file.
     */
    private String getExtension(File f) {
        String name = f.getName();
        if (name != null) {
            int extensionIndex = name.lastIndexOf('.');
            if (extensionIndex < 0) {
                return null;
            }
            return name.substring(extensionIndex + 1).toLowerCase();
        }
        return null;
    }

    /**
     * Adds an icon based on the file type "dot" extension
     * string, e.g: ".gif". Case is ignored.
     */
    public void putIcon(String extension, Icon icon) {
        icons.put(extension, icon);
    }

    /**
     * Icon that reperesents this file. Default implementation returns
     * null. You might want to override this to return something more
     * interesting.
     *
     * @see FileView#getIcon
     */
    @Override
    public Icon getIcon(File f) {
        Icon icon = null;
        String extension = getExtension(f);
        if (extension != null) {
            icon = icons.get(extension);
        }
        return icon;
    }

    /**
     * Whether the directory is traversable or not. Generic implementation
     * returns true for all directories and special folders.
     *
     * You might want to subtype ExampleFileView to do somethimg more interesting,
     * such as recognize compound documents directories; in such a case you might
     * return a special icon for the directory that makes it look like a regular
     * document, and return false for isTraversable to not allow users to
     * descend into the directory.
     *
     * @see FileView#isTraversable
     */
    @Override
    public Boolean isTraversable(File f) {
        // if (some_reason) {
        //    return Boolean.FALSE;
        // }
        return null;    // Use default from FileSystemView
    }
    
    public void setIcons(){
    	
    	for(int i = 0; i < EnvironmentVariables.fileIcons.length ; i++ ){
    		putIcon(EnvironmentVariables.fileIcons[i], new ImageIcon(getClass().getResource(
    				EnvironmentVariables.icons16Path+EnvironmentVariables.fileIcons[i]+".png")));
    	}
    }
}
