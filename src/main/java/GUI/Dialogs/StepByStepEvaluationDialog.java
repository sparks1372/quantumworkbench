/**
 * @Author = Sam Ratcliff
 */
package GUI.Dialogs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import Core.Algorithms.QuantumAlgorithm;
import Core.Circuit.Circuit;
import Core.CircuitBuilder.CircuitBuilder;
import Core.CircuitEvaluator.CircuitEvaluator;
import Core.Problem.testset;
import GUI.SubPanels.ResultPanels.CircuitParentPanel;
import GUI.SubPanels.StatePanels.currectStateVisualiserTab;
import Utils.WindowUtils;

/**
 * @author Sam Ratcliff
 * 
 * @version $Revision: 1.0 $
 */
public class StepByStepEvaluationDialog extends JDialog implements
		ActionListener {

	/**
	 * 
	 */
	private static final long			serialVersionUID		= 6431859424563396257L;
	private final JPanel				stateHolderPanel;
	private currectStateVisualiserTab	statePanel;
	private final CircuitParentPanel	circuitPanel;
	private JButton						nextButton;
	private static final String			nextButtonStr			= "Next";
	private static final String			nextToolTipStr			= "<html>Click here to move the step-by-step<br>evaluation to the next state.</html>";
	private JButton						prevButton;
	private static final String			prevButtonStr			= "Previous";
	private static final String			prevToolTipStr			= "<html>Click here to move the step-by-step<br>evaluation to the previous state.</html>";
	private JButton						cancButton;
	private static final String			cancButtonStr			= "Close";
	private static final String			cancToolTipStr			= "<html>Click here to close the step-by-step<br>evaluation dialog.</html>";
	private static final String			numQubitsLabelStr		= "Number of Qubits";
	private static final String			buildCircuitButtonStr	= "Rebuild Circuit";
	private static final String			buildCircuitToolTipStr	= "<html>Click here to rebuild and re-evaluate the<br>step-by-step evaluation for the selected<br>system size.</html>";
	private JLabel						numQubitsLabel;
	private JButton						buildCircuitButton;
	private JComboBox					numQubits;
	private int							numOfQubits				= 1;
	private JPanel						qubitPanel;

	private final QuantumAlgorithm		quantAlg;
	private final CircuitBuilder		cirBuilder;
	private final CircuitEvaluator		cirEval;
	private List<testset>				testSuiteList			= new LinkedList<testset>();
	private testset						expTestSet;
	private int							position				= 0;
	private static final Logger			logger					= Logger.getLogger(StepByStepEvaluationDialog.class
																		.getClass());

	/**
	 * Constructor for StepByStepEvaluationDialog.
	 * 
	 * @param quantAlg
	 *            QuantumAlgorithm
	 * @param cirBuilder
	 *            circuitBuilder
	 * @param circuitEval
	 *            circuitevaluator
	 */
	public StepByStepEvaluationDialog(QuantumAlgorithm quantAlg,
			CircuitBuilder cirBuilder, CircuitEvaluator circuitEval) {
		super();
		this.quantAlg = quantAlg;
		this.cirBuilder = cirBuilder;
		this.cirEval = circuitEval;
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setUndecorated(true);
		stateHolderPanel = new JPanel();
		circuitPanel = new CircuitParentPanel();
		circuitPanel.setVisible(false);
		setupButtons();

		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

		this.getContentPane().add(stateHolderPanel);
		this.getContentPane().add(circuitPanel);
		this.getContentPane().add(qubitPanel);
		this.pack();
		WindowUtils.centre(this);

	}

	/**
	 * Method actionPerformed.
	 * 
	 * @param arg0
	 *            ActionEvent
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == buildCircuitButton) {
			try {
				numOfQubits = Integer.parseInt((String) this.numQubits
						.getSelectedItem());
				updateNumOfQubits();
				nextButton.setEnabled(true);
				prevButton.setEnabled(true);
				stateHolderPanel.setEnabled(true);
				circuitPanel.setVisible(true);
				validate();
				pack();
				WindowUtils.centre(this);
			} catch (NumberFormatException e) {
				logger.error("Number of qubits could not be parsed");
				JOptionPane
						.showMessageDialog(
								this,
								"The number of qubits cannot be correctly parsed. Please try again.",
								"Message", JOptionPane.ERROR_MESSAGE);
			}
		} else if (arg0.getSource() == nextButton) {
			position = circuitPanel.circuitNextPosition();
			stateHolderPanel.removeAll();
			int index = statePanel.getSelectedIndex();

			statePanel = new currectStateVisualiserTab(
					testSuiteList.get(position), "At Position " + position,
					expTestSet);
			statePanel.setSelectedIndex(index);
			stateHolderPanel.add(statePanel);
			validate();
			pack();
			WindowUtils.centre(this);
		} else if (arg0.getSource() == prevButton) {
			position = circuitPanel.circuitPreviousPosition();
			stateHolderPanel.removeAll();
			int index = statePanel.getSelectedIndex();
			statePanel = new currectStateVisualiserTab(
					testSuiteList.get(position), "At Position " + position,
					expTestSet);
			statePanel.setSelectedIndex(index);
			stateHolderPanel.add(statePanel);
			validate();
			pack();
			WindowUtils.centre(this);
		} else if (arg0.getSource() == cancButton) {
			this.setVisible(false);
		}

	}

	private void setupButtons() {
		nextButton = new JButton(nextButtonStr);
		nextButton.setToolTipText(nextToolTipStr);

		nextButton.addActionListener(this);
		nextButton.setEnabled(false);

		prevButton = new JButton(prevButtonStr);
		prevButton.setToolTipText(prevToolTipStr);

		prevButton.addActionListener(this);
		prevButton.setEnabled(false);

		numQubitsLabel = new JLabel(numQubitsLabelStr);
		numQubits = new JComboBox();
		Iterator<Integer> keyiter = cirEval.getQproblem().getTestSuite()
				.getKeys().iterator();
		while (keyiter.hasNext()) {
			numQubits.addItem(Integer.toString(keyiter.next()));
		}

		buildCircuitButton = new JButton();
		buildCircuitButton.setText(buildCircuitButtonStr);
		buildCircuitButton.setToolTipText(buildCircuitToolTipStr);
		buildCircuitButton.addActionListener(this);

		cancButton = new JButton(cancButtonStr);
		cancButton.setToolTipText(cancToolTipStr);
		cancButton.addActionListener(this);

		qubitPanel = new JPanel();
		qubitPanel.setLayout(new FlowLayout());

		qubitPanel.add(numQubitsLabel);
		qubitPanel.add(numQubits);
		qubitPanel.add(buildCircuitButton);
		qubitPanel.add(prevButton);
		qubitPanel.add(nextButton);
		qubitPanel.add(cancButton);
	}

	private void updateNumOfQubits() {
		Circuit cir = cirBuilder.Build(quantAlg, numOfQubits);
		circuitPanel.update(cir, numOfQubits);
		testSuiteList = cirEval.getTrace(quantAlg, numOfQubits);
		testSuiteList.get(0).getTestcases();
		expTestSet = cirEval.getQproblem().getTestSuite()
				.getTestcases(numOfQubits);
		statePanel = new currectStateVisualiserTab(testSuiteList.get(0),
				"Starting State", expTestSet);
		stateHolderPanel.removeAll();
		stateHolderPanel.add(statePanel);
		validate();
		pack();
	}
}
