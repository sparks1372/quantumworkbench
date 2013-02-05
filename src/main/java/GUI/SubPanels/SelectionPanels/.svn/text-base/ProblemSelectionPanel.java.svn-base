/**
 * @Author = Sam Ratcliff
 */
package GUI.SubPanels.SelectionPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import Core.qcevolutionbackend;
import Core.CircuitEvolution.SearchEngineState;
import Core.Problem.Problem_Manager;
import GUI.MainPanel;

/**
 * @author Sam Ratcliff
 * 
 */
public class ProblemSelectionPanel extends JPanel implements ActionListener,
		Observer {
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 5703666410120826874L;
	private final JComboBox				selection;
	private ComboBoxModel				selection_model;
	private final JTextPane				description;
	private final JScrollPane			description_scroller;
	private final qcevolutionbackend	backend;

	/**
	 * 
	 */
	public ProblemSelectionPanel(qcevolutionbackend be) {
		this.setLayout(new BorderLayout());
		backend = be;
		Set<String> probs = backend.getProbmanager().getAvailableProblems();
		String[] options = new String[probs.size() + 1];
		options[0] = "Please Select Problem";
		int index = 1;
		Iterator<String> iter = probs.iterator();
		while (iter.hasNext()) {
			options[index++] = iter.next();
		}
		selection_model = new DefaultComboBoxModel(options);

		description = new JTextPane();
		description.setEditable(false);
		description_scroller = new JScrollPane(description);
		description_scroller
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		description_scroller.setPreferredSize(new Dimension(250, 155));
		description_scroller.setMinimumSize(new Dimension(10, 10));

		if (backend.getCurrentse() != null) {
			String key = backend.getQproblem().getName();
			selection_model.setSelectedItem(key);
			description.setText(backend.getProbmanager().getSearchEngineDesc(
					key));
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		selection = new JComboBox(selection_model);
		selection.addActionListener(this);
		selection.setPreferredSize(new Dimension(
				(int) (screenSize.width * MainPanel.right_perc), 30));
		selection.setMaximumSize(new Dimension(
				(int) (screenSize.width * MainPanel.right_perc), 30));

		be.addObserver(this);
		be.getProbmanager().addObserver(this);
		if (null != backend.getCurrentse()) {
			backend.getCurrentse().addObserver(this);
		}

		this.add(selection, BorderLayout.NORTH);
		this.add(description_scroller, BorderLayout.CENTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		if (cb.getSelectedIndex() != 0) {
			String prob_key = (String) cb.getSelectedItem();
			description.setText(backend.getProbmanager().getSearchEngineDesc(
					prob_key));
			backend.setQproblem(backend.getProbmanager().getProblem(prob_key));
		}

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
				backend.getCurrentse().addObserver(this);
			}
		} else if (o instanceof Problem_Manager) {
			Set<String> probs = backend.getProbmanager().getAvailableProblems();
			String[] options = new String[probs.size() + 1];
			options[0] = "Please Select Problem";
			int index = 1;
			Iterator<String> iter = probs.iterator();
			while (iter.hasNext()) {
				options[index++] = iter.next();
			}
			selection_model = new DefaultComboBoxModel(options);
			if (backend.getQproblem() != null) {
				String key = backend.getQproblem().getName();
				selection_model.setSelectedItem(key);
				description.setText(backend.getProbmanager()
						.getSearchEngineDesc(key));
			}
			selection.setModel(selection_model);
		} else {
			if (backend.getCurrentse() != null) {
				if (backend.getCurrentse().getState() == SearchEngineState.Searching) {
					selection.setEnabled(false);
				} else {
					selection.setEnabled(true);
				}
				validate();
			}
		}
	}

}
