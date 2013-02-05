/**
 * @Author = Sam Ratcliff
 */
package Utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * @author Sam Ratcliff
 * 
 */
public class WindowUtils {
	public static void centre(Window jd) {
		jd.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		jd.setLocation(screenSize.width / 2 - jd.getWidth() / 2,
				screenSize.height / 2 - jd.getHeight() / 2);
	}
}
