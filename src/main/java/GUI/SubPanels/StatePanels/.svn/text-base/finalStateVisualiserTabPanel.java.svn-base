/**
 * @Author = Sam Ratcliff
 */
package GUI.SubPanels.StatePanels;

import java.awt.Component;
import java.awt.Font;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Core.qcevolutionbackend;
import Core.Problem.testsuite;
import GUI.MainPanel;

/**
 * @author Sam Ratcliff
 * 
 */
public class finalStateVisualiserTabPanel extends JPanel {
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 3404079585649633405L;
	private final qcevolutionbackend	backend;
	private static String				qubits_string		= " Qubit(s)";
	private static String				psLabelStr			= "Circuit Produced State Visualisation";
	private JLabel						psLabel;
	private JPanel						labelPanel;
	private final JTabbedPane			tabPane;
	private final int					resultIndex;

	/**
	 * 
	 */
	public finalStateVisualiserTabPanel(qcevolutionbackend be, int index_in) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		backend = be;
		if (backend == null) {
			System.out.println("WHAT THE HELL");
		}
		// update(null, null);
		resultIndex = index_in;

		tabPane = new JTabbedPane();
		tabPane.setVisible(false);
		setupLabels();
		init();
		this.add(labelPanel);
		this.add(tabPane);
	}

	private void init() {
		int x = 0;
		if (backend == null) {
			System.out.println(x);
		}
		x++;
		if (backend.getCurrentse() == null) {
			System.out.println(x);
		}
		x++;
		if (backend.getCurrentse().getResults() == null) {
			System.out.println(x);
		}
		x++;
		if (backend.getCurrentse().getResults()[resultIndex] == null) {
			System.out.println(x);
		}
		x++;
		if (backend.getCurrentse().getResults()[resultIndex].getQa() == null) {
			System.out.println(x);
		}
		x++;
		if (backend.getCireval() == null) {
			System.out.println(x);
		}
		x++;
		if (backend.getCireval().getResults(
				backend.getCurrentse().getResults()[resultIndex].getQa()) == null) {
			System.out.println(x);
		}
		x++;
		testsuite results = backend.getCireval().getResults(
				backend.getCurrentse().getResults()[resultIndex].getQa());

		Set<Integer> keys = results.getKeys();

		Iterator<Integer> iter = keys.iterator();

		Component tab;
		while (iter.hasNext()) {
			int numofqubits = iter.next();
			tab = new stateVisualiserTab(results.getTestcases(numofqubits), "");
			tabPane.add(Integer.toString(numofqubits).concat(qubits_string),
					tab);
		}
		if (tabPane.getTabCount() > 0) {
			tabPane.setVisible(true);
		} else {
			tabPane.setVisible(false);
		}
		this.add(tabPane);
		this.validate();
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
	// @Override
	// public void update(Observable o, Object arg) {
	//
	// if (o instanceof qcevolutionbackend) {
	// if (null != backend.getCurrentse()) {
	// backend.getCurrentse().removeObserver(this);
	// backend.getCurrentse().addObserver(this);
	// }
	// } else if (arg instanceof circuitsearchengine) {
	//
	// // if ((backend.getCurrentse().getState() ==
	// // SearchEngineState.SearchCompleteResultAvailable)
	// // && (backend.getCurrentse().getBestAlgorithm() != null)) {
	// if ((backend.getCurrentse().getState() ==
	// SearchEngineState.SearchCompleteResultAvailable)
	// && (backend.getCurrentse().getResults()[resultIndex] != null)
	// && (backend.getCurrentse().getResults()[resultIndex]
	// .getQa() != null)) {
	// this.remove(tabPane);
	// this.validate();
	// tabPane.removeAll();
	// testsuite results = backend.getCireval().getResults(
	// backend.getCurrentse().getResults()[resultIndex]
	// .getQa());
	//
	// while (results.getKeys().isEmpty()) {
	// try {
	// wait(500);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// results = backend.getCireval().getResults(
	// backend.getCurrentse().getResults()[resultIndex]
	// .getQa());
	// }
	//
	// Set<Integer> keys = results.getKeys();
	//
	// Iterator<Integer> iter = keys.iterator();
	//
	// Component tab;
	// while (iter.hasNext()) {
	// int numofqubits = iter.next();
	// tab = new stateVisualiserTab(
	// results.getTestcases(numofqubits), "");
	// tabPane.add(
	// Integer.toString(numofqubits).concat(qubits_string),
	// tab);
	// }
	// if (tabPane.getTabCount() > 0) {
	// tabPane.setVisible(true);
	// } else {
	// tabPane.setVisible(false);
	// }
	// this.add(tabPane);
	// this.validate();
	// } else {
	// tabPane.setVisible(false);
	// }
	// }
	// }
}
