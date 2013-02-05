/**
 * @Author = Sam Ratcliff
 */
package GUI.CustomButtons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import Core.qcevolutionbackend;
import Core.CircuitEvolution.SearchEngineState;
import Core.CircuitEvolution.circuitsearchengine;
import GUI.Dialogs.EditProblemPanel;

/**
 * @author Sam Ratcliff
 * 
 */
public class EditProblemSetButton extends JButton implements Observer {
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 6900583099659794670L;
	private final qcevolutionbackend	backend;
	private static final String			buttonText			= "Edit Selected Problem";
	private static final String			toolTipText			= "<html>Click to open the Edit Search Problem<br>dialog to edit the test suite of<br>the search problem selected above.</html>";

	/**
	 * 
	 */
	public EditProblemSetButton(qcevolutionbackend be) {
		super(buttonText);
		setToolTipText(toolTipText);
		backend = be;
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				(new EditProblemPanel(backend, backend.getQproblem()))
						.setVisible(true);
			}
		});
		backend.addObserver(this);
		if ((backend.getQproblem() == null)
				|| (backend.getQproblem().getTestSuite() == null)) {
			this.setEnabled(false);
		} else {
			this.setEnabled(true);
		}
		this.setVisible(true);
		if (backend.getCurrentse() != null) {
			backend.getCurrentse().addObserver(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof circuitsearchengine) {
			if ((backend.getCurrentse() != null)) {
				if (backend.getCurrentse().getState() == SearchEngineState.Searching) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			} else {
				setEnabled(true);
			}
		} else if (arg0 == backend) {
			if ((backend.getQproblem() == null)
					|| (backend.getQproblem().getTestSuite() == null)) {
				this.setEnabled(false);
			} else {
				this.setEnabled(true);
			}
			if (null != backend.getCurrentse()) {
				backend.getCurrentse().removeObserver(this);
				backend.getCurrentse().addObserver(this);
			}
		}
	}

}
