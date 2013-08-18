/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui.CustomButtons;

import info.samratcliff.core.qcevolutionbackend;
import info.samratcliff.ui.Dialogs.CreateProblemPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Sam Ratcliff
 */
public class CreateProblemSetButton extends JButton {
    /**
     *
     */
    private static final long serialVersionUID = 3283339198359310469L;
    private final qcevolutionbackend backend;
    private static final String buttonText = "Create Search Problem";
    private static final String toolTipText = "<html>Click to open the Create Search Problem<br>dialog to create a search problem from<br>scratch, not from a pre-defined test suite.</html>";

    /**
     *
     */
    public CreateProblemSetButton(qcevolutionbackend be) {
        super(buttonText);
        setToolTipText(toolTipText);
        backend = be;
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                (new CreateProblemPanel(backend)).setVisible(true);
            }
        });
        this.setVisible(true);
    }

}
