/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui.CustomButtons;

import info.samratcliff.core.qcevolutionbackend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Sam Ratcliff
 */
public class SaveProblemSetButton extends JButton implements Observer {
    /**
     *
     */
    private static final long serialVersionUID = 5196443454098530681L;
    private final qcevolutionbackend backend;
    private static final String buttonText = "Save Problem Set";
    private static final String toolTipText = "<html>Click to save the currently registered search<br>problems to file. This will cause all<br>currently registered search problems to be re-registered<br>when the software is next restarted.</html>";

    /**
     *
     */
    public SaveProblemSetButton(qcevolutionbackend be) {
        super(buttonText);
        setToolTipText(toolTipText);
        backend = be;
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                backend.getProbmanager().saveToFile();
            }
        });
        backend.getProbmanager().addObserver(this);
        if (backend.getProbmanager().isSaved()) {
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
        }
        this.setVisible(true);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (backend.getProbmanager().isSaved()) {
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
        }
    }

}
