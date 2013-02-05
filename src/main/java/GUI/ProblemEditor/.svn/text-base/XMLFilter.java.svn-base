package GUI.ProblemEditor;

import java.io.File;

import javax.swing.filechooser.FileFilter;

class Utils {

	public final static String	xml	= "xml";

	/*
	 * Get the extension of a file.
	 */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if ((i > 0) && (i < s.length() - 1)) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}
}

public class XMLFilter extends FileFilter {

	// Accept all directories and all gif, jpg, tiff, or png files.
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = Utils.getExtension(f);
		if (extension != null) {
			return extension.equals(Utils.xml);
		}

		return false;
	}

	// The description of this filter
	@Override
	public String getDescription() {
		return "XML Files";
	}
}