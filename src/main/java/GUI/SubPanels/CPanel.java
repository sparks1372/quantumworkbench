/**
 * @Author = Sam Ratcliff
 */
package GUI.SubPanels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import Core.qcevolutionbackend;
import Core.CircuitEvolution.SearchResult;
import Core.CircuitEvolution.circuitsearchengine;
import GUI.SubPanels.ResultPanels.ResultPanel;
import GUI.SubPanels.StatePanels.finalStateVisualiserTabPanel;
import GUI.SubPanels.StatePanels.stateVisualiserTabPanel;

/**
 * @author Sam Ratcliff
 * 
 */
public class CPanel extends JPanel implements Observer, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4172206248272080881L;
	private final qcevolutionbackend backend;
	private final stateVisualiserTabPanel stateExpectedTabPanel;
	private final JPanel resultPanel;
	private final JPanel resultCollectionPanel;
	private final JComboBox resSelector;
	private finalStateVisualiserTabPanel stateFinalTabPanel;
	private ResultPanel resPanel;
	private final DefaultComboBoxModel aModel;
	private final JButton statsButton;
	private static final String statsButtonStr = "Statistics";
	private final JPanel selectAndStatPanel;

	public CPanel(qcevolutionbackend be) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		backend = be;
		stateExpectedTabPanel = new stateVisualiserTabPanel(backend);
		resultPanel = new JPanel();
		resultPanel.setLayout(new BorderLayout());
		resultCollectionPanel = new JPanel();
		resultCollectionPanel.setLayout(new BoxLayout(resultCollectionPanel,
				BoxLayout.PAGE_AXIS));

		aModel = new DefaultComboBoxModel();
		resSelector = new JComboBox();
		resSelector.setModel(aModel);
		resSelector.addActionListener(this);
		resSelector.setMaximumRowCount(15);

		statsButton = new JButton(statsButtonStr);
		statsButton.addActionListener(this);

		selectAndStatPanel = new JPanel();
		selectAndStatPanel.setLayout(new FlowLayout());
		selectAndStatPanel.add(resSelector);
		selectAndStatPanel.add(statsButton);

		resultPanel.add(selectAndStatPanel, BorderLayout.NORTH);
		resultPanel.add(resultCollectionPanel, BorderLayout.CENTER);
		selectAndStatPanel.setVisible(false);
		resultCollectionPanel.setVisible(false);

		add(stateExpectedTabPanel);
		add(resultPanel);
		backend.addObserver(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == resSelector) {
			resultCollectionPanel.setVisible(false);
			resultCollectionPanel.removeAll();
			if (resSelector.getSelectedIndex() >= 0) {
				stateFinalTabPanel = new finalStateVisualiserTabPanel(backend,
						resSelector.getSelectedIndex());
				resPanel = new ResultPanel(backend,
						resSelector.getSelectedIndex());
				backend.addObserver(this);
				if (null != backend.getCurrentse()) {
					backend.getCurrentse().addObserver(this);
				}
				resultCollectionPanel.add(stateFinalTabPanel);
				resultCollectionPanel.add(resPanel);
				resultCollectionPanel.setVisible(true);
			}
		} else if (arg0.getSource() == statsButton) {
			JDialog statdia = backend.getCurrentse().getStatsDialog();
			statdia.setVisible(true);
		}
		validate();
	}

	@Override
	public synchronized void update(Observable arg0, Object arg1) {
		if (arg0 instanceof qcevolutionbackend
				&& null != backend.getCurrentse()) {
			backend.getCurrentse().addObserver(this);
		} else if (arg1 instanceof circuitsearchengine) {

			switch (backend.getCurrentse().getState()) {
			case SearchCompleteResultAvailable:
				if (backend.getCurrentse().getResults().length > 0) {
					SearchResult[] results = backend.getCurrentse()
							.getResults();
					aModel.removeAllElements();
					for (int i = 0; i < results.length; i++) {
						aModel.addElement("Result " + i);
					}
					resSelector.setModel(aModel);
					resSelector.setSelectedIndex(0);

					selectAndStatPanel.setVisible(true);
					resultCollectionPanel.setVisible(true);
					System.err
							.println(backend.getCurrentse().getResults().length
									+ " Results");
				} else {
					System.err.println("No Results");
				}
				break;
			case Searching:
				selectAndStatPanel.setVisible(false);
				resultCollectionPanel.setVisible(false);
				break;

			default:
				break;
			}
		}
		validate();
	}

}
