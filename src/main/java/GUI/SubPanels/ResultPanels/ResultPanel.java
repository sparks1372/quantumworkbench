/**
 * @Author = Sam Ratcliff
 */
package GUI.SubPanels.ResultPanels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import Core.qcevolutionbackend;
import Core.Algorithms.QuantumAlgorithm;
import Core.Circuit.Circuit;
import GUI.Dialogs.StepByStepEvaluationDialog;

/**
 * @author Sam Ratcliff
 * 
 */
public class ResultPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long			serialVersionUID		= -4677073408891376351L;
	private final qcevolutionbackend	backend;
	private final JTabbedPane			tabPanel;
	private final AlgorithmPanel		algoPanel;
	private final QCircuitPanel			qcircuitPanel;
	private final CircuitParentPanel	circuitPanel;
	private final String				numQubitsLabelStr		= "Number of Qubits";
	private final String				buildCircuitButtonStr	= "Rebuild Circuit";
	private final String				debugCircuitButtonStr	= "Step-By-Step Evaluation";
	private final JLabel				numQubitsLabel;
	private final JButton				buildCircuitButton;
	private final JButton				debugCircuitButton;
	private final JTextArea				numQubits;
	private int							numOfQubits				= 1;
	private final JPanel				qubitPanel;
	private final int					resultIndex;

	/**
	 * 
	 */
	public ResultPanel(qcevolutionbackend be, int index_in) {
		backend = be;

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		tabPanel = new JTabbedPane();

		algoPanel = new AlgorithmPanel();
		qcircuitPanel = new QCircuitPanel();
		circuitPanel = new CircuitParentPanel();
		resultIndex = index_in;

		tabPanel.add(algoPanel);
		tabPanel.add(circuitPanel);
		tabPanel.add(qcircuitPanel);

		numQubitsLabel = new JLabel(numQubitsLabelStr);
		numQubits = new JTextArea();
		numQubits.setSize(20, 10);
		numQubits.setText(Integer.toString(numOfQubits));

		buildCircuitButton = new JButton();
		buildCircuitButton.setText(buildCircuitButtonStr);
		buildCircuitButton.addActionListener(this);

		debugCircuitButton = new JButton();
		debugCircuitButton.setText(debugCircuitButtonStr);
		debugCircuitButton.addActionListener(this);

		qubitPanel = new JPanel();
		qubitPanel.setLayout(new FlowLayout());
		qubitPanel.add(numQubitsLabel);
		qubitPanel.add(numQubits);
		qubitPanel.add(buildCircuitButton);
		qubitPanel.add(debugCircuitButton);

		circuitPanel.setPreferredSize(algoPanel.getSize());
		init();
		this.add(qubitPanel);
		this.add(tabPanel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == buildCircuitButton) {
			try {
				numOfQubits = Integer.parseInt(this.numQubits.getText());
				init();
			} catch (NumberFormatException e) {
				JOptionPane
						.showMessageDialog(
								this,
								"The number of qubits cannot be correctly parsed. Please try again.",
								"Message", JOptionPane.ERROR_MESSAGE);
			}
		} else if (arg0.getSource() == debugCircuitButton) {
			// new StepByStepEvaluationDialog(backend.getCurrentse()
			// .getBestAlgorithm(), backend.getCurrentse().getCbuilder(),
			// backend.getCireval()).setVisible(true);
			StepByStepEvaluationDialog sbsevald = new StepByStepEvaluationDialog(
					backend.getCurrentse().getResults()[resultIndex].getQa(),
					backend.getCurrentse().getCbuilder(), backend.getCireval());
			sbsevald.setTitle("Step-by-Step Evaluation for iteration "
					+ resultIndex);
			sbsevald.setVisible(true);
		}

	}

	private void init() {

		QuantumAlgorithm qa = backend.getCurrentse().getResults()[resultIndex]
				.getQa();
		algoPanel.update(qa);

		Circuit cir = backend.getCirbui().Build(qa, numOfQubits);

		qcircuitPanel.update(cir.toLatex(numOfQubits));

		circuitPanel.update(cir, numOfQubits);

	}

	// @Override
	// public void update(Observable o, Object arg) {
	//
	// if (o instanceof qcevolutionbackend) {
	// if (null != backend.getCurrentse()) {
	// backend.getCurrentse().removeObserver(this);
	// backend.getCurrentse().addObserver(this);
	// }
	// }
	// //
	// // if ((backend.getCurrentse() != null)
	// // && (backend.getCurrentse().getState() ==
	// // SearchEngineState.SearchCompleteResultAvailable)
	// // && (backend.getCurrentse().getBestAlgorithm() != null)) {
	// // QuantumAlgorithm qa = backend.getCurrentse().getBestAlgorithm();
	//
	// if ((backend.getCurrentse() != null)
	// && (backend.getCurrentse().getState() ==
	// SearchEngineState.SearchCompleteResultAvailable)
	// && (backend.getCurrentse().getResults() != null)
	// && (backend.getCurrentse().getResults()[resultIndex].getQa() != null)) {
	// QuantumAlgorithm qa = backend.getCurrentse().getResults()[resultIndex]
	// .getQa();
	// algoPanel.update(qa);
	//
	// Circuit cir = backend.getCirbui().Build(qa, numOfQubits);
	//
	// qcircuitPanel.update(cir.toLatex(numOfQubits));
	//
	// circuitPanel.update(cir, numOfQubits);
	// }
	// }
}
