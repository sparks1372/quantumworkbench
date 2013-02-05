/**
 * @Author = Sam Ratcliff
 */
package GUI.SubPanels.StatePanels;

import java.awt.BorderLayout;
import java.util.Iterator;

import javax.swing.event.ListSelectionEvent;

import Core.Problem.testcase;
import Core.Problem.testset;
import GUI.StateVisualiser.columnchartvisualiser;

/**
 * @author Sam Ratcliff
 * 
 */
public class currectStateVisualiserTab extends stateVisualiserTab {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 4768335710034349656L;

	private final testset			ets;
	protected columnchartvisualiser	expectedFinalStateVisualiser;

	/**
	 * @param ts
	 * @param title
	 */
	public currectStateVisualiserTab(testset ts, String title, testset expts) {
		super(ts, title);
		startStateSelector.removeListSelectionListener(this);
		ets = expts;
		Iterator<testcase> iter = ets.getTestcases();
		if (iter.hasNext()) {
			testcase tc = iter.next();
			int labelLength = (int) (Math.log(tc.getFinalStateCopy()
					.getRowDimension()) / Math.log(2));
			expectedFinalStateVisualiser = new columnchartvisualiser(
					tc.getFinalStateCopy(), labelLength, "Expected Final State");
			valueChanged(null);
		} else {
			throw new IllegalArgumentException(
					"Test set for tab does not contain any test cases");
		}
		startStateSelector.addListSelectionListener(this);
		statePanel.add(expectedFinalStateVisualiser, BorderLayout.CENTER);
	}

	@Override
	protected void addCorrectPanel() {
		statePanel.add(finalStateVisualiser);
		this.add(statePanel, BorderLayout.CENTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * GUI.SubPanels.stateVisualiserTab#setUpVisualising(Core.Problem.testcase,
	 * java.lang.String)
	 */
	@Override
	protected void setUpVisualising(testcase tc, String title) {
		int labelLength = (int) (Math.log(tc.getFinalStateCopy()
				.getRowDimension()) / Math.log(2));
		finalStateVisualiser = new columnchartvisualiser(
				tc.getFinalStateCopy(), labelLength, title);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if ((tset != null) && (ets != null)) {
			int index;
			index = startStateSelector.getSelectedIndex();
			Iterator<testcase> iter = tset.getTestcases();
			testcase tc = null;
			while ((tc == null) && iter.hasNext()) {
				testcase temp = iter.next();
				if (temp.getId() == index) {
					tc = temp;
				}

			}

			int labelLength = (int) (Math.log(tc.getFinalStateCopy()
					.getRowDimension()) / Math.log(2));
			finalStateVisualiser.updateState(tc.getFinalStateCopy(),
					labelLength);

			iter = ets.getTestcases();
			tc = null;
			while ((tc == null) && iter.hasNext()) {
				testcase temp = iter.next();
				if (temp.getId() == index) {
					tc = temp;
				}

			}

			labelLength = (int) (Math.log(tc.getFinalStateCopy()
					.getRowDimension()) / Math.log(2));
			expectedFinalStateVisualiser.updateState(tc.getFinalStateCopy(),
					labelLength);
		}
	}
}
