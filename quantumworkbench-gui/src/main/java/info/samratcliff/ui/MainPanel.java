/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui;

import com.google.inject.Inject;
import info.samratcliff.core.qcevolutionbackend;
import info.samratcliff.ui.SubPanels.CPanel;
import info.samratcliff.ui.SubPanels.LPanel;
import info.samratcliff.ui.SubPanels.RPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Sam Ratcliff
 */
public class MainPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 8605194316843038889L;
    private final JPanel left_panel;
    private final JPanel centre_panel;
    private final JPanel right_panel;
    private final Dimension screen_size;
    public static final double left_perc = 0.2;
    public static final double centre_perc = 0.59;
    public static final double right_perc = 0.2;
    public static final double height_perc = 0.95;
    public static final int titleFontSize = 25;
    private final qcevolutionbackend backend;

    /**
     *
     */
    @Inject
    public MainPanel(qcevolutionbackend be) {
        backend = be;
        screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        left_panel = new LPanel(backend);
        centre_panel = new CPanel(backend);
        right_panel = new RPanel(backend);

        left_panel.setBorder(BorderFactory.createEtchedBorder());
        centre_panel.setBorder(BorderFactory.createEtchedBorder());
        right_panel.setBorder(BorderFactory.createEtchedBorder());

        left_panel.setPreferredSize(new Dimension(
                (int) (screen_size.width * left_perc),
                (int) (screen_size.height * height_perc)));
        centre_panel.setPreferredSize(new Dimension(
                (int) (screen_size.width * centre_perc),
                (int) (screen_size.height * height_perc)));
        right_panel.setPreferredSize(new Dimension(
                (int) (screen_size.width * right_perc),
                (int) (screen_size.height * height_perc)));

        left_panel.setMaximumSize(new Dimension(
                (int) (screen_size.width * left_perc),
                (int) (screen_size.height * height_perc)));
        centre_panel.setMaximumSize(new Dimension(
                (int) (screen_size.width * centre_perc),
                (int) (screen_size.height * height_perc)));
        right_panel.setMaximumSize(new Dimension(
                (int) (screen_size.width * right_perc),
                (int) (screen_size.height * height_perc)));

        this.add(left_panel, BorderLayout.LINE_START);
        this.add(centre_panel, BorderLayout.CENTER);
        this.add(right_panel, BorderLayout.LINE_END);
    }

}
