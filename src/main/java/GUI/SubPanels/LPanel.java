/**
 * @Author = Sam Ratcliff
 */
package GUI.SubPanels;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Core.qcevolutionbackend;
import GUI.SubPanels.SelectionPanels.FitnessFunctionSelectionPanel;
import GUI.SubPanels.SelectionPanels.SearchEngineSelectionPanel;

/**
 * @author Sam Ratcliff
 * 
 */
public class LPanel extends JPanel {
	/**
	 * 
	 */
	private static final long					serialVersionUID	= 7152600839227512484L;
	private final SearchEngineSelectionPanel	se_select_panel;
	private final FitnessFunctionSelectionPanel	ff_select_panel;

	/**
	 * 
	 */
	public LPanel(qcevolutionbackend be) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		se_select_panel = new SearchEngineSelectionPanel(be);
		ff_select_panel = new FitnessFunctionSelectionPanel(be);

		this.add(se_select_panel);
		this.add(ff_select_panel);
	}
}
