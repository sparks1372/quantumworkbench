/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui.CustomButtons;

import info.samratcliff.core.qcevolutionbackend;
import info.samratcliff.ui.Dialogs.LoadProblemPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Sam Ratcliff
 */
public class LoadProblemSetButton extends JButton {
    /**
     *
     */
    private static final long serialVersionUID = 5727289359855727318L;
    private final qcevolutionbackend backend;
    private static final String buttonText = "Load Pre-Defined Test Suite";
    private static final String toolTipText = "<html>Click to open the Load Search Problem<br>dialog to create a search problem from<br> a predefined test suite XML file.</html>";

    /**
     *
     */
    public LoadProblemSetButton(qcevolutionbackend be) {
        super(buttonText);
        setToolTipText(toolTipText);
        backend = be;
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                (new LoadProblemPanel(backend)).setVisible(true);
            }
        });
        this.setVisible(true);
    }

}
