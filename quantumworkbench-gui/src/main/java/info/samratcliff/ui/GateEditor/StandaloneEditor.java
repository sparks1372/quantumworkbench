/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui.GateEditor;

import info.samratcliff.utils.WindowUtils;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Sam Ratcliff
 */
public class StandaloneEditor extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -7370618660262825246L;

    public static void main(final String[] args) {

        PropertyConfigurator.configure("log4j.properties");

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                StandaloneEditor se = new StandaloneEditor(
                        new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                frame.setVisible(false);
                                System.exit(0);
                            }
                        }, frame);
                frame.getContentPane().add(se);
                frame.pack();
                WindowUtils.centre(frame);
                frame.setVisible(true);
            }
        });

    }

    private final JPanel lowerButtonPanel;

    private final MatrixEditor med;

    private JButton closeButton;

    private static final String closeButtonStr = "Close";

    public StandaloneEditor(ActionListener closeListener) {

        this(closeListener, null);

    }

    /**
     *
     */
    public StandaloneEditor(ActionListener closeListener, Window p) {
        this.setLayout(new BorderLayout());
        lowerButtonPanel = new JPanel();
        lowerButtonPanel.setLayout(new FlowLayout());

        setupButtons(closeListener);
        med = new MatrixEditor(p);

        add(med.getControlButtonPanel(), BorderLayout.NORTH);
        add(med.getEditor(), BorderLayout.CENTER);
        add(lowerButtonPanel, BorderLayout.SOUTH);
    }

    /**
     * @param cancelListener
     * @param okayListener
     */
    private void setupButtons(ActionListener closeListener) {

        closeButton = new JButton(closeButtonStr);
        closeButton.addActionListener(closeListener);

        lowerButtonPanel.add(closeButton);

    }

}
