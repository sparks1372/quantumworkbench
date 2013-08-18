/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.utils;

import java.awt.*;

/**
 * @author Sam Ratcliff
 */
public class WindowUtils {
    public static void centre(Window jd) {
        jd.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jd.setLocation(screenSize.width / 2 - jd.getWidth() / 2,
                screenSize.height / 2 - jd.getHeight() / 2);
    }
}
