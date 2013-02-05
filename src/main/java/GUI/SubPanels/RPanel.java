/**
 * @Author = Sam Ratcliff
 */
package GUI.SubPanels;

import java.awt.Component;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Core.qcevolutionbackend;
import GUI.MainPanel;
import GUI.CustomButtons.CreateProblemSetButton;
import GUI.CustomButtons.EditProblemSetButton;
import GUI.CustomButtons.LoadProblemSetButton;
import GUI.CustomButtons.SaveProblemSetButton;
import GUI.CustomButtons.StartSearchButton;
import GUI.SubPanels.SelectionPanels.ProblemSelectionPanel;

/**
 * @author Sam Ratcliff
 * 
 */
public class RPanel extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long			serialVersionUID	= -4644786137635040384L;
	private static String				psLabelStr			= "Problem Selection";
	private final qcevolutionbackend	backend;
	private JLabel						psLabel;
	private final ProblemSelectionPanel	prob_select_panel;
	private JButton						cpbutton;
	private JButton						spbutton;
	private JButton						ldbutton;
	private JButton						edbutton;
	private JButton						evbutton;
	private JPanel						labelPanel;
	private JPanel						buttonPanel;
	private JPanel						progressPanel;
	private JPanel						statsPanel;

	/**
	 * 
	 */
	public RPanel(qcevolutionbackend be) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		backend = be;

		backend.addObserver(this);

		if (null != backend.getCurrentse()) {
			backend.getCurrentse().addObserver(this);
		}

		setupLabels();
		setupButtons();

		prob_select_panel = new ProblemSelectionPanel(backend);

		this.add(labelPanel);
		this.add(prob_select_panel);
		this.add(buttonPanel);
	}

	private void setupButtons() {

		cpbutton = new CreateProblemSetButton(backend);
		cpbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
		cpbutton.setAlignmentY(Component.CENTER_ALIGNMENT);

		spbutton = new SaveProblemSetButton(backend);
		spbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
		spbutton.setAlignmentY(Component.CENTER_ALIGNMENT);

		ldbutton = new LoadProblemSetButton(backend);
		ldbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ldbutton.setAlignmentY(Component.CENTER_ALIGNMENT);

		edbutton = new EditProblemSetButton(backend);
		edbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
		edbutton.setAlignmentY(Component.CENTER_ALIGNMENT);

		evbutton = new StartSearchButton(backend);
		evbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
		evbutton.setAlignmentY(Component.CENTER_ALIGNMENT);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

		buttonPanel.add(cpbutton);
		buttonPanel.add(ldbutton);
		buttonPanel.add(spbutton);
		buttonPanel.add(edbutton);
		buttonPanel.add(evbutton);
	}

	private void setupLabels() {

		psLabel = new JLabel(psLabelStr);
		Font fancyFont = new Font(psLabel.getFont().getFontName(), psLabel
				.getFont().getStyle(), MainPanel.titleFontSize);
		psLabel.setFont(fancyFont);

		labelPanel = new JPanel();
		labelPanel.add(psLabel);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof qcevolutionbackend) {
			if (null != backend.getCurrentse()) {
				if (progressPanel != null) {
					this.remove(progressPanel);
				}
				progressPanel = backend.getCurrentse().getProgressBar();
				this.add(progressPanel);
				if (statsPanel != null) {
					this.remove(statsPanel);
				}
				statsPanel = backend.getCurrentse().getSearchStatisticsPanel();
				this.add(statsPanel);
			}
		}
	}
}
